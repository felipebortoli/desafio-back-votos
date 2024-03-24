package com.votacao.sistemavotacao.infra.service;

import com.votacao.sistemavotacao.domain.SessaoDTO;
import com.votacao.sistemavotacao.infra.entity.PautaEntity;
import com.votacao.sistemavotacao.infra.entity.SessaoEntity;
import com.votacao.sistemavotacao.infra.repository.SessaoRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

public class SessaoService {

    private final SessaoRepository repository;
    private final PautaService pautaService;
    private final static Long DURACAO_DEFAULT = 1L;

    public SessaoService(SessaoRepository repository, PautaService pautaService) {
        this.repository = repository;
        this.pautaService = pautaService;
    }

    public Object abrirSessao(SessaoDTO sessaoDTO) throws Exception {
        PautaEntity pautaEntity = pautaService.findPauta(sessaoDTO.nomePauta());

        SessaoEntity sessaoEntity = pautaEntity.getSessao();

        if (sessaoEntity == null) {
            sessaoEntity = new SessaoEntity();
            sessaoEntity.setId(UUID.randomUUID());
        }

        Long duracao = sessaoDTO.duracao() > 0 ? sessaoDTO.duracao() : DURACAO_DEFAULT;
        sessaoEntity.setDuracao(duracao);
        sessaoEntity.setDataAbertura(LocalDateTime.now());
        sessaoEntity.setDataFechamento(LocalDateTime.now().plusMinutes(duracao));

        repository.save(sessaoEntity);
        pautaEntity.setSessao(sessaoEntity);
        pautaService.saveSessaoPauta(pautaEntity);

        return sessaoEntity;
    }

}
