package com.votacao.sistemavotacao.infra.entity;

import com.votacao.sistemavotacao.infra.enums.VotoEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;


@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "Voto")
public class VotoEntity {

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    private VotoEnum voto;

    @ManyToOne
    private AssociadoEntity associado;

    @ManyToOne
    private PautaEntity pauta;

}
