package com.votacao.sistemavotacao.service;


import com.votacao.sistemavotacao.SistemaVotacaoApplication;
import com.votacao.sistemavotacao.domain.AssociadoDTO;
import com.votacao.sistemavotacao.domain.PautaDTO;
import com.votacao.sistemavotacao.infra.entity.PautaEntity;
import com.votacao.sistemavotacao.infra.repository.PautaRepository;
import com.votacao.sistemavotacao.infra.service.PautaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(classes = {SistemaVotacaoApplication.class})
public class PautaServiceTest {


    @Autowired
    private PautaRepository repository;
    @Autowired
    private PautaService pautaService;

    @BeforeEach
    void clearDatabase() {
        repository.deleteAll();
    }

    @Test
    public void createPauta() throws Exception {
        List<AssociadoDTO> associadoDTOList = new ArrayList<>();
        associadoDTOList.add(new AssociadoDTO("felipe teste", "07788490903"));
        associadoDTOList.add(new AssociadoDTO("joao teste", "07788490563"));
        associadoDTOList.add(new AssociadoDTO("felipe teste", "0778849033"));
        associadoDTOList.add(new AssociadoDTO("joao teste", "0778843463"));
        PautaDTO pauta = new PautaDTO("pauta teste", associadoDTOList);
        PautaEntity pautaSave = (PautaEntity) pautaService.createPauta(pauta);
        assertEquals("pauta teste", pautaSave.getNome());
        assertEquals(4, pautaSave.getAssociados().size());
    }

    @Test
    public void pautaJaExistente() throws Exception {
        List<AssociadoDTO> associadoDTOList = new ArrayList<>();
        associadoDTOList.add(new AssociadoDTO("felipe teste", "07788490903"));
        associadoDTOList.add(new AssociadoDTO("joao teste", "07788490563"));
        associadoDTOList.add(new AssociadoDTO("felipe teste", "0778849033"));
        associadoDTOList.add(new AssociadoDTO("joao teste", "0778843463"));
        PautaDTO pauta = new PautaDTO("pauta teste", associadoDTOList);
        PautaEntity pautaSave = (PautaEntity) pautaService.createPauta(pauta);
        assertEquals("pauta teste", pautaSave.getNome());
        assertEquals(4, pautaSave.getAssociados().size());
        PautaDTO pauta2 = new PautaDTO("pauta teste", associadoDTOList);
        try {
            pautaService.createPauta(pauta);
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Pauta já cadastrada. Nome: " + pauta2.nome());
        }

    }

}
