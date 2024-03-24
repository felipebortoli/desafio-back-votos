package com.votacao.sistemavotacao.infra.Utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.votacao.sistemavotacao.domain.ResponseDTO;


public class AppResponse {
    public static JsonNode success(Object data) throws Exception {
        ResponseDTO resp = new ResponseDTO();
        resp.setStatus("ok");
        resp.setBody(data);
        JsonNode node;
        try {
            node = JSON.valueToTree(resp);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
        return node;
    }

    public static JsonNode error(String message) {
        ResponseDTO resp = new ResponseDTO();
        resp.setStatus("error");
        resp.setMessage(message);
        JsonNode node;
        try {
            node = JSON.valueToTree(resp);
        } catch (Exception e) {
            var result = JSON.getNodeFactory().objectNode();
            result.putPOJO("status", "error");
            result.putPOJO("message", e.getMessage());
            node = result;
        }
        return node;
    }

}
