package com.votacao.sistemavotacao.infra.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "Pauta")
public class PautaEntity {

    @Id
    private UUID id;

    private String nome;

    @ManyToOne
    private SessaoEntity sessao;

    @ManyToMany(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "pauta_associados",
            joinColumns = @JoinColumn(name = "pauta_id"),
            inverseJoinColumns = @JoinColumn(name = "associado_id"))
    private List<AssociadoEntity> associados;

    @Column(name = "data_inclusao")
    private LocalDateTime dataInclusao;

}
