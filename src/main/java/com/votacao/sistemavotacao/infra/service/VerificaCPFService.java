package com.votacao.sistemavotacao.infra.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.votacao.sistemavotacao.infra.Utils.HttpUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.net.http.HttpResponse;

@Component
public class VerificaCPFService {

    private final HttpUtil httpUtil;
    public final ObjectMapper mapper;

    private final static String URL_EXRTERNAL_API_CPF = " https://user-info.herokuapp.com/users/";

    public VerificaCPFService(HttpUtil httpUtil, ObjectMapper mapper) {
        this.httpUtil = httpUtil;
        this.mapper = mapper;
    }


    public String verificaCPFpodeVotar(String cpf){
        String url = URL_EXRTERNAL_API_CPF + cpf;
        HttpResponse<String> result;
        try {
            result = this.httpUtil.requestGET(url);
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível buscar CPF do Associado: " + url, e);
        }

        if (result.statusCode() == HttpStatus.NOT_FOUND.value()) {
            throw new RuntimeException(
                    "CPF pode ser invalido: http code: " + result.statusCode() + "url:" + url);
        }

        if (result.statusCode() != HttpStatus.OK.value()) {
            throw new RuntimeException(
                    "Não foi possível buscar CPF do Associado: http code: " + result.statusCode() + "url:" + url);
        }

        JsonNode jsonNode;
        try {
            jsonNode = mapper.readTree(result.body());
            return jsonNode.get("body").toString();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao processar resposta JSON", e);
        }
    }
}
