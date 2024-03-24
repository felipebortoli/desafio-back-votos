package com.votacao.sistemavotacao.infra.service;

import com.votacao.sistemavotacao.domain.ResultadoResponse;
import com.votacao.sistemavotacao.domain.VotoRequest;
import com.votacao.sistemavotacao.domain.VotoResponse;
import com.votacao.sistemavotacao.infra.entity.AssociadoEntity;
import com.votacao.sistemavotacao.infra.entity.PautaEntity;
import com.votacao.sistemavotacao.infra.entity.VotoEntity;
import com.votacao.sistemavotacao.infra.enums.VotoEnum;
import com.votacao.sistemavotacao.infra.exceptions.BusinessException;
import com.votacao.sistemavotacao.infra.message.producer.VotoApiProducer;
import com.votacao.sistemavotacao.infra.repository.VotoRepository;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
public class VotoService {

    private final VotoRepository repository;
    private final PautaService pautaService;
    private final AssociadoService associadoService;
    private final VerificaCPFService verificaCPFService;
    private final VotoApiProducer producer;

    public VotoService(VotoRepository repository, PautaService pautaService, AssociadoService associadoService, VerificaCPFService verificaCPFService, VotoApiProducer producer) {
        this.repository = repository;
        this.pautaService = pautaService;
        this.associadoService = associadoService;
        this.verificaCPFService = verificaCPFService;
        this.producer = producer;
    }

    public VotoResponse computaVoto(VotoRequest votoRequest) throws Exception {
        PautaEntity pauta = pautaService.findPauta(votoRequest.nomePauta());
        if (pauta.getSessao() == null) {
            producer.enviaRespostaCadastroProdutos("Não foi criada sessão para essa pauta");
            throw new BusinessException("Não foi criada sessão para essa pauta");
        }
        LocalDateTime dataHoraVoto = LocalDateTime.now();
        LocalDateTime dataHoraInicioSessao = pauta.getSessao().getDataAbertura();
        LocalDateTime dataHoraFimSessao = pauta.getSessao().getDataFechamento();

        if (dataHoraVoto.isAfter(dataHoraFimSessao) || dataHoraVoto.isBefore(dataHoraInicioSessao)) {
            producer.enviaRespostaCadastroProdutos("Sessão da pauta: " + pauta.getNome() + " está fechada");
            throw new BusinessException("Sessão da pauta: " + pauta.getNome() + " está fechada");
        }

        AssociadoEntity associado = associadoService.findByCPF(votoRequest.cpf());
        if(associado == null){
            producer.enviaRespostaCadastroProdutos("Associado não está vinculado a esta pauta");
            throw new BusinessException("Associado não está vinculado a esta pauta");
        }

        var votoExistente = repository.findVotoByPautaAndAssociado(pauta.getId(),associado.getId());
        if(!votoExistente.isEmpty()){
            producer.enviaRespostaCadastroProdutos("Associado já votou nessa pauta");
            throw new BusinessException("Associado já votou nessa pauta");
        }

//        String verificaCpf = verificaCPFService.verificaCPFpodeVotar(votoRequest.cpf());
//        if(verificaCpf.equals("UNABLE_TO_VOTE")){
//            producer.enviaRespostaCadastroProdutos("Associado não tem permissão para votar");
//            throw new BusinessException("Associado não tem permissão para votar");
//        }

       try {
            VotoEntity voto = new VotoEntity();
            voto.setId(UUID.randomUUID());
            voto.setAssociado(associado);
            voto.setPauta(pauta);
            voto.setVoto(VotoEnum.valueOf(votoRequest.voto().toUpperCase()));
            repository.save(voto);
            producer.enviaRespostaCadastroProdutos("Voto computado com sucesso");
            return new VotoResponse("Voto computado com sucesso");
        } catch (Exception e) {
            log.error("Erro ao salvar o voto: " + e.getMessage());
            producer.enviaRespostaCadastroProdutos("Voto não foi computado");
            throw new Exception("Voto não foi computado");
        }
    }

    public ResultadoResponse findResultado(String nomePauta) throws Exception {
        PautaEntity pauta = pautaService.findPauta(nomePauta);
        if (pauta.getSessao() == null) {
            throw new Exception("Não foi criada sessão para essa pauta");
        }
        LocalDateTime dataHoraVoto = LocalDateTime.now();
        LocalDateTime dataHoraInicioSessao = pauta.getSessao().getDataAbertura();
        LocalDateTime dataHoraFimSessao = pauta.getSessao().getDataFechamento();

        if (dataHoraVoto.isAfter(dataHoraInicioSessao) && dataHoraVoto.isBefore(dataHoraFimSessao)) {
            throw new Exception("Sessão da pauta: " + pauta.getNome() + " ainda está aberta");
        }

        var result = repository.countVotosByPauta(pauta.getId());

        Long countSim = (Long) result.get(0).get("voto_sim");
        Long countNao = (Long) result.get(0).get("voto_nao");
        String vencedor = countSim > countNao ? "SIM venceu" : "NAO venceu";
        vencedor = countSim == countNao ? "Empatou a votação" : vencedor;

        return new ResultadoResponse(countSim,countNao,vencedor);
    }

}
