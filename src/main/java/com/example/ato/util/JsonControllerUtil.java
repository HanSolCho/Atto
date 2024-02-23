package com.example.ato.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Repository
public class JsonControllerUtil {
    private JsonUtil jsonUtil;

    public JsonControllerUtil(JsonUtil jsonUtil) {
        this.jsonUtil = jsonUtil;
    }

    public ResponseEntity<String> getResponseEntity() {
        return new ResponseEntity(HttpStatus.OK);
    }

    public ResponseEntity<String> getResponseEntity(HttpStatus status) {
        return new ResponseEntity(status);
    }

    public ResponseEntity<String> getJsonResponseEntity(Object obj) {
        return new ResponseEntity(this.getJsonString(obj), this.getHttpHeaders("application/json"), HttpStatus.OK);
    }

    public String getJsonString(Object obj) {
        String jsonStr = null;

        try {
            jsonStr = this.jsonUtil.getJsonString(obj);
            return jsonStr;
        } catch (Exception var4) {
            throw new RuntimeException("Can't convert " + (obj != null ? obj.getClass().getSimpleName() : "null") + " to json string.", var4);
        }
    }

    private HttpHeaders getHttpHeaders(String contentType) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", contentType);
        return headers;
    }
}
