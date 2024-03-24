package com.votacao.sistemavotacao.infra.configs;

import com.votacao.sistemavotacao.infra.message.producer.VotoApiProducer;
import com.votacao.sistemavotacao.infra.repository.AssociadoRepository;
import com.votacao.sistemavotacao.infra.repository.PautaRepository;
import com.votacao.sistemavotacao.infra.repository.SessaoRepository;
import com.votacao.sistemavotacao.infra.repository.VotoRepository;
import com.votacao.sistemavotacao.infra.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VotacaoConfig {

    @Bean
    VotoService votoService(VotoRepository repository, PautaService pautaService, AssociadoService associadoService, VerificaCPFService verificaCPFService, VotoApiProducer producer){
        return new VotoService(repository,pautaService,associadoService, verificaCPFService, producer);
    }

    @Bean
    AssociadoService associadoService(AssociadoRepository repository){
        return new AssociadoService(repository);
    }

    @Bean
    PautaService pautaService(PautaRepository repository, AssociadoService associadoService,VotoApiProducer producer){
        return new PautaService(repository, associadoService, producer);
    }

    @Bean
    SessaoService sessaoService(SessaoRepository repository, PautaService pautaService){
        return new SessaoService(repository,pautaService);
    }
}
