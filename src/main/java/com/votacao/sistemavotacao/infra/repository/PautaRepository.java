package com.votacao.sistemavotacao.infra.repository;

import com.votacao.sistemavotacao.infra.entity.PautaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface PautaRepository extends JpaRepository<PautaEntity, UUID> {

    @Query("SELECT pauta FROM PautaEntity pauta  Where pauta.nome=:nome")
    Optional<PautaEntity> findByNome(@Param("nome")String nome);
}
