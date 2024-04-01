package com.votacao.sistemavotacao.service;


import com.votacao.sistemavotacao.SistemaVotacaoApplication;
import com.votacao.sistemavotacao.domain.*;
import com.votacao.sistemavotacao.infra.entity.PautaEntity;
import com.votacao.sistemavotacao.infra.entity.SessaoEntity;
import com.votacao.sistemavotacao.infra.repository.AssociadoRepository;
import com.votacao.sistemavotacao.infra.repository.PautaRepository;
import com.votacao.sistemavotacao.infra.repository.SessaoRepository;
import com.votacao.sistemavotacao.infra.repository.VotoRepository;
import com.votacao.sistemavotacao.infra.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = {SistemaVotacaoApplication.class})
public class VotoServiceTest {

    @Autowired
    private VotoRepository votoRepository;
    @Autowired
    private AssociadoRepository associadoRepository;
    @Autowired
    private PautaRepository pautaRepository;
    @Autowired
    private SessaoRepository sessaoRepository;
    @Autowired
    private PautaService pautaService;
    @Autowired
    private SessaoService sessaoService;
    @Autowired
    private VotoService votoService;
    @MockBean
    private VerificaCPFService verificaCPFService;

    @BeforeEach
    void clearDatabase() {
        MockitoAnnotations.initMocks(this);
        votoRepository.deleteAll();
        pautaRepository.deleteAll();
        associadoRepository.deleteAll();
        sessaoRepository.deleteAll();
    }

    @Test
    public void computaVoto() throws Exception {
        List<AssociadoDTO> associadoDTOList = new ArrayList<>();
        associadoDTOList.add(new AssociadoDTO("felipe teste", "07788490903"));
        associadoDTOList.add(new AssociadoDTO("joao teste", "07788490563"));
        associadoDTOList.add(new AssociadoDTO("felipe teste", "0778849033"));
        associadoDTOList.add(new AssociadoDTO("joao teste", "0778843463"));
        PautaDTO pauta = new PautaDTO("pauta teste", associadoDTOList);
        pautaService.createPauta(pauta);
        SessaoDTO sessao = new SessaoDTO(3L,"pauta teste");
        sessaoService.abrirSessao(sessao);

        VotoRequest votoRequest = new VotoRequest("pauta teste","07788490903","SIM");

        when(verificaCPFService.verificaCPFpodeVotar(votoRequest.cpf())).thenReturn("ABLE_TO_VOTE");
        VotoResponse response = votoService.computaVoto(votoRequest);

        assertEquals("Voto computado com sucesso",response.message());
    }

    @Test
    public void computaVotoAssociadoJaVotou() throws Exception {
        List<AssociadoDTO> associadoDTOList = new ArrayList<>();
        associadoDTOList.add(new AssociadoDTO("felipe teste", "07788490903"));
        associadoDTOList.add(new AssociadoDTO("joao teste", "07788490563"));
        associadoDTOList.add(new AssociadoDTO("felipe teste", "0778849033"));
        associadoDTOList.add(new AssociadoDTO("joao teste", "0778843463"));
        PautaDTO pauta = new PautaDTO("pauta teste", associadoDTOList);
        pautaService.createPauta(pauta);
        SessaoDTO sessao = new SessaoDTO(3L,"pauta teste");
        sessaoService.abrirSessao(sessao);

        VotoRequest votoRequest = new VotoRequest("pauta teste","07788490903","SIM");

        when(verificaCPFService.verificaCPFpodeVotar(votoRequest.cpf())).thenReturn("ABLE_TO_VOTE");
        VotoResponse response = votoService.computaVoto(votoRequest);

        assertEquals("Voto computado com sucesso",response.message());

        VotoRequest votoRequest2 = new VotoRequest("pauta teste","07788490903","SIM");

        when(verificaCPFService.verificaCPFpodeVotar(votoRequest2.cpf())).thenReturn("ABLE_TO_VOTE");
        try {
            VotoResponse response2 = votoService.computaVoto(votoRequest);
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Associado já votou nessa pauta");
        }

    }
}
