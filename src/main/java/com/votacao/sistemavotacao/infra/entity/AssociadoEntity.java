package com.votacao.sistemavotacao.infra.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "Associado")
public class AssociadoEntity {
    @Id
    private UUID id;

    private String nome;

    private String cpf;

    @ManyToMany(mappedBy = "associados")
    private List<PautaEntity> pautas;

}
