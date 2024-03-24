package com.votacao.sistemavotacao.infra.message.producer;


import com.votacao.sistemavotacao.infra.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class VotoApiProducer {

    @Value("${topico.voto-api.producer.nome}")
    private String topico;

    @Autowired
    private final KafkaTemplate<String, String> kafkaTemplate;

    public VotoApiProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void enviaRespostaCadastroProdutos(final String mensagem) {
        try{
            kafkaTemplate.send(topico, mensagem);
        } catch (Exception e) {
            throw new BusinessException("Erro ao produzir mensagem do kafka");
        }
    }


}
