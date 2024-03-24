package com.votacao.sistemavotacao.infra.controller;

import com.votacao.sistemavotacao.domain.PautaDTO;
import com.votacao.sistemavotacao.infra.Utils.AppResponse;
import com.votacao.sistemavotacao.infra.service.PautaService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/pauta/")
public class PautaController {

    private final PautaService service;

    public PautaController(PautaService service) {
        this.service = service;
    }

    @PostMapping(value = "create", params = "version=1")
    @ResponseStatus(HttpStatus.CREATED)
    public Object create(@RequestBody PautaDTO pautaDTO) {
        try {
            return AppResponse.success(service.createPauta(pautaDTO));
        } catch (Exception e) {
            return AppResponse.error(e.getMessage());
        }
    }

}
