package com.votacao.sistemavotacao.infra.repository;

import com.votacao.sistemavotacao.infra.entity.VotoEntity;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VotoRepository extends JpaRepository<VotoEntity, UUID> {

    @Query("SELECT voto FROM VotoEntity voto  Where voto.pauta.id = :uuidPauta and voto.associado.id = :uuidAssociado")
    Optional<VotoEntity> findVotoByPautaAndAssociado(@Param("uuidPauta")UUID uuidPauta,@Param("uuidAssociado") UUID uuidAssociado);

    @Query("SELECT SUM(CASE WHEN voto.voto = 'SIM' THEN 1 ELSE 0 END) AS voto_sim," +
            "SUM(CASE WHEN voto.voto = 'NAO' THEN 1 ELSE 0 END) AS voto_nao FROM VotoEntity voto  Where voto.pauta.id = :id")
    List<Tuple> countVotosByPauta(UUID id);


}
