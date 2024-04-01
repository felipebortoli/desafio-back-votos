package com.votacao.sistemavotacao.service;


import com.votacao.sistemavotacao.SistemaVotacaoApplication;
import com.votacao.sistemavotacao.domain.AssociadoDTO;
import com.votacao.sistemavotacao.infra.repository.AssociadoRepository;
import com.votacao.sistemavotacao.infra.repository.PautaRepository;
import com.votacao.sistemavotacao.infra.repository.SessaoRepository;
import com.votacao.sistemavotacao.infra.repository.VotoRepository;
import com.votacao.sistemavotacao.infra.service.AssociadoService;
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
public class AssociadoServiceTest {

    @Autowired
    private AssociadoRepository associadoRepository;
    @Autowired
    private PautaRepository pautaRepository;
    @Autowired
    private SessaoRepository sessaoRepository;
    @Autowired
    private VotoRepository votoRepository;
    @Autowired
    private AssociadoService service;

    @BeforeEach
    void clearDatabase() {
        votoRepository.deleteAll();
        pautaRepository.deleteAll();
        associadoRepository.deleteAll();
        sessaoRepository.deleteAll();
    }

    @Test
    void create() throws Exception {
        List<AssociadoDTO> associadoDTOList = new ArrayList<>();
        associadoDTOList.add(new AssociadoDTO("felipe teste", "07788490903"));
        associadoDTOList.add(new AssociadoDTO("joao teste", "07788490563"));
        var listaSalva = service.save(associadoDTOList);
        assertEquals(2, listaSalva.size());
    }

    @Test
    void createSemRepeticao() throws Exception {
        List<AssociadoDTO> associadoDTOList = new ArrayList<>();
        associadoDTOList.add(new AssociadoDTO("felipe teste", "07788490903"));
        associadoDTOList.add(new AssociadoDTO("joao teste", "07788490563"));
        var listaSalva = service.save(associadoDTOList);
        assertEquals(2, listaSalva.size());
        List<AssociadoDTO> novosAssociados = new ArrayList<>();
        associadoDTOList.add(new AssociadoDTO("felipe teste", "07788490903"));
        associadoDTOList.add(new AssociadoDTO("joao teste", "07788490563"));
        var listanovos = service.save(associadoDTOList);
        assertEquals(0, listanovos.size());
    }

}
