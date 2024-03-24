package com.votacao.sistemavotacao.infra.controller;


import com.votacao.sistemavotacao.domain.SessaoDTO;
import com.votacao.sistemavotacao.infra.Utils.AppResponse;
import com.votacao.sistemavotacao.infra.service.SessaoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/sessao/")
public class SessaoController {

    private final SessaoService service;

    public SessaoController(SessaoService service) {
        this.service = service;
    }

    @PostMapping(value = "abrir",params = "version=1")
    @ResponseStatus(HttpStatus.CREATED)
    public Object abrirSessao(@RequestBody SessaoDTO sessaoDTO){
        try {
            return AppResponse.success(service.abrirSessao(sessaoDTO));
        } catch (Exception e) {
            return AppResponse.error(e.getMessage());
        }
    }

}
