package com.votacao.sistemavotacao.infra.service;

import com.votacao.sistemavotacao.domain.AssociadoDTO;
import com.votacao.sistemavotacao.infra.entity.AssociadoEntity;
import com.votacao.sistemavotacao.infra.repository.AssociadoRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class AssociadoService {

    private final AssociadoRepository repository;

    public AssociadoService(AssociadoRepository repository) {
        this.repository = repository;
    }

    public List<AssociadoEntity> save(List<AssociadoDTO> associadoDTOList) throws Exception {
        Set<String> existingCpfs = new HashSet<>(repository.findByCpf());
        List<AssociadoEntity> associadosToSave = associadoDTOList.stream()
                .filter(dto -> !existingCpfs.contains(dto.cpf()))
                .collect(Collectors.toList()).stream().map(dto -> {
                    AssociadoEntity entity = new AssociadoEntity();
                    entity.setId(UUID.randomUUID());
                    entity.setCpf(dto.cpf());
                    entity.setNome(dto.nome());
                    return entity;
                })
                .collect(Collectors.toList());
        try{
            repository.saveAll(associadosToSave);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new Exception("Associados não foram criados.");
        }
        return associadosToSave;
    }

    public AssociadoEntity findByCPF(String cpf) {
        return repository.findByCpf(cpf);
    }
}
