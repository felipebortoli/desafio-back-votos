package com.votacao.sistemavotacao.infra.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class VotoDTO {

    @JsonProperty("nomePauta")
    private String nomePauta;
    @JsonProperty("cpf")
    private String cpf;
    @JsonProperty("voto")
    private String voto;
}
