package com.cegal.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RESTControllerMVCTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getHeartbeat() throws Exception {
        ResponseEntity<String> response =
                restTemplate.getForEntity(new URL("http://localhost:" + port + "/heartbeat").toString(), String.class);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Assignment-REST - heartbeat page", response.getBody());
    }
}