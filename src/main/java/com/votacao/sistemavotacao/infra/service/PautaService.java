package com.votacao.sistemavotacao.infra.service;

import com.votacao.sistemavotacao.domain.PautaDTO;
import com.votacao.sistemavotacao.infra.entity.AssociadoEntity;
import com.votacao.sistemavotacao.infra.entity.PautaEntity;

import com.votacao.sistemavotacao.infra.exceptions.BusinessException;
import com.votacao.sistemavotacao.infra.message.producer.VotoApiProducer;
import com.votacao.sistemavotacao.infra.repository.PautaRepository;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
public class PautaService {

    private final PautaRepository repository;
    private final AssociadoService associadoService;
    private final VotoApiProducer producer;

    public PautaService(PautaRepository repository, AssociadoService associadoService, VotoApiProducer producer) {
        this.repository = repository;
        this.associadoService = associadoService;
        this.producer = producer;
    }

    public Object createPauta(PautaDTO pautaDTO) throws Exception {
        var opt = repository.findByNome(pautaDTO.nome());
        if(opt.isPresent()) {
            throw new Exception("Pauta já cadastrada. Nome: " + pautaDTO.nome());
        }
        PautaEntity entity = new PautaEntity();
        entity.setId(UUID.randomUUID());
        entity.setNome(pautaDTO.nome());
        entity.setDataInclusao(LocalDateTime.now());

        try{
            List<AssociadoEntity> associados =  associadoService.save(pautaDTO.associados());
            entity.setAssociados(associados);
            this.repository.save(entity);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new Exception("Pauta não foi criada.");
        }
        return entity;
    }

    public PautaEntity findPauta(String name) throws Exception {
        var opt = repository.findByNome(name);
        if(!opt.isPresent()) {
            producer.enviaRespostaCadastroProdutos("Pauta não existe. Nome: " + name);
            throw new BusinessException("Pauta não existe. Nome: " + name);
        }
        return opt.get();
    }

    public void saveSessaoPauta(PautaEntity entity) throws Exception {
        try{
            repository.save(entity);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new Exception("Sessão não foi criada.");
        }

    }

}
