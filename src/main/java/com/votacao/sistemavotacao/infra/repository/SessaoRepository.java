package com.votacao.sistemavotacao.infra.repository;

import com.votacao.sistemavotacao.infra.entity.SessaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SessaoRepository extends JpaRepository<SessaoEntity, UUID> {
}
