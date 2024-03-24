package com.votacao.sistemavotacao.infra.message.consumer;


import com.votacao.sistemavotacao.domain.VotoRequest;
import com.votacao.sistemavotacao.infra.exceptions.BusinessException;
import com.votacao.sistemavotacao.infra.message.VotoDTO;
import com.votacao.sistemavotacao.infra.service.VotoService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;

import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class VotoApiConsumer {

     private final VotoService votoService;


    @KafkaListener(topics = "${topico.voto-api.consumer.nome}", groupId = "${topico.voto-api.consumer.group-id}")
    public void computarVotos(VotoDTO votoDTO) {
        try{
            votoService.computaVoto(new VotoRequest(votoDTO.getNomePauta(),votoDTO.getCpf(), votoDTO.getVoto()));
        } catch (Exception exception) {
            throw new BusinessException("Erro ao consumir mensagem do kafka " + exception);
        }
    }
}
