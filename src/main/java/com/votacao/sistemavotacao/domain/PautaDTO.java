package com.votacao.sistemavotacao.domain;

import java.util.List;

public record PautaDTO(String nome, List<AssociadoDTO> associados) {
}
