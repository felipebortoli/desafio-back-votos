package com.votacao.sistemavotacao.infra.controller;


import com.votacao.sistemavotacao.domain.VotoRequest;
import com.votacao.sistemavotacao.infra.Utils.AppResponse;
import com.votacao.sistemavotacao.infra.service.VotoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/assembleia/")
public class VotacaoController {

    private final VotoService service;

    public VotacaoController( VotoService service) {
        this.service = service;
    }

    @PostMapping(value = "voto", params = "version=1")
    @ResponseStatus(HttpStatus.CREATED)
    public Object votar(@RequestBody VotoRequest votoRequest){
        try {
            return AppResponse.success(service.computaVoto(votoRequest));
        } catch (Exception e) {
            return AppResponse.error(e.getMessage());
        }
    }

    @GetMapping(value = "resultado", params = "version=1")
    @ResponseStatus(HttpStatus.CREATED)
    public Object resultado(@RequestParam String nomePauta){
        try {
            return AppResponse.success(service.findResultado(nomePauta));
        } catch (Exception e) {
            return AppResponse.error(e.getMessage());
        }
    }
}
