package com.votacao.sistemavotacao.service;

import com.votacao.sistemavotacao.SistemaVotacaoApplication;
import com.votacao.sistemavotacao.domain.AssociadoDTO;
import com.votacao.sistemavotacao.domain.PautaDTO;
import com.votacao.sistemavotacao.domain.SessaoDTO;
import com.votacao.sistemavotacao.infra.entity.PautaEntity;
import com.votacao.sistemavotacao.infra.entity.SessaoEntity;
import com.votacao.sistemavotacao.infra.repository.SessaoRepository;
import com.votacao.sistemavotacao.infra.service.PautaService;
import com.votacao.sistemavotacao.infra.service.SessaoService;
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
public class SessaoServiceTest {

    @Autowired
    private SessaoRepository repository;
    @Autowired
    private PautaService pautaService;
    @Autowired
    private SessaoService sessaoService;

    private final static Long DURACAO_DEFAULT = 1L;

    @BeforeEach
    void clearDatabase() {
        repository.deleteAll();
    }

    @Test
    public void abrirSessao() throws Exception {
        List<AssociadoDTO> associadoDTOList = new ArrayList<>();
        associadoDTOList.add(new AssociadoDTO("felipe teste", "07788490903"));
        associadoDTOList.add(new AssociadoDTO("joao teste", "07788490563"));
        associadoDTOList.add(new AssociadoDTO("felipe teste", "0778849033"));
        associadoDTOList.add(new AssociadoDTO("joao teste", "0778843463"));
        PautaDTO pauta = new PautaDTO("pauta teste", associadoDTOList);
        pautaService.createPauta(pauta);
        SessaoDTO sessao = new SessaoDTO(3L,"pauta teste");
        SessaoEntity entity = (SessaoEntity) sessaoService.abrirSessao(sessao);
        assertEquals(3L, entity.getDuracao());
        assertEquals(entity.getDataFechamento().getMinute(), entity.getDataAbertura().plusMinutes(3).getMinute());
    }

    @Test
    public void abrirSessaoPautaInexistente() throws Exception {
        SessaoDTO sessao = new SessaoDTO(3L,"pauta teste");
        try {
            sessaoService.abrirSessao(sessao);
            fail();
        } catch (Exception e) {
            assertEquals(e.getMessage(), "Pauta não existe. Nome: " + sessao.nomePauta());
        }
    }


}
