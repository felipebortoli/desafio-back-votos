package com.votacao.sistemavotacao.infra.repository;

import com.votacao.sistemavotacao.infra.entity.AssociadoEntity;
import com.votacao.sistemavotacao.infra.entity.PautaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AssociadoRepository extends JpaRepository<AssociadoEntity, UUID> {
    @Query("SELECT associado.cpf FROM AssociadoEntity associado")
    List<String> findByCpf();

    @Query("SELECT associado FROM AssociadoEntity associado where associado.cpf = :cpf")
    AssociadoEntity findByCpf(String cpf);
}
