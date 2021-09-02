package com.seliote.mlb.auth.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.seliote.mlb.auth.domain.JwsPayload;
import com.seliote.mlb.auth.service.JwsService;
import com.seliote.mlb.common.config.YmlConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Optional;

/**
 * JWS Service 实现
 *
 * @author seliote
 * @version 2021-07-04
 */
@Slf4j
@Service
public class JwsServiceImpl implements JwsService {

    private final ObjectMapper objectMapper;
    private final JWSHeader jwsHeader;
    private final MACSigner macSigner;
    private final MACVerifier macVerifier;

    @Autowired
    public JwsServiceImpl(ObjectMapper objectMapper, YmlConfig ymlConfig) throws JOSEException {
        var secret = ymlConfig.getJws().getSecret().getBytes(StandardCharsets.UTF_8);
        this.objectMapper = objectMapper;
        this.jwsHeader = new JWSHeader(JWSAlgorithm.HS256);
        this.macSigner = new MACSigner(secret);
        this.macVerifier = new MACVerifier(secret);
    }

    @Override
    public Optional<String> sign(JwsPayload jwsPayload) {
        try {
            var jwsObject = new JWSObject(jwsHeader, new Payload(objectMapper.writeValueAsString(jwsPayload)));
            jwsObject.sign(macSigner);
            log.debug("Success sign JWS {}", jwsPayload);
            return Optional.of(jwsObject.serialize());
        } catch (JsonProcessingException | JOSEException exception) {
            log.error("Failed sign {}, {}", jwsPayload, exception.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<JwsPayload> parse(String jws) {
        try {
            var jwsObject = JWSObject.parse(jws);
            if (!jwsObject.verify(macVerifier)) {
                log.warn("Failed verify JWS {}", jws);
                return Optional.empty();
            }
            var jwsPayload = objectMapper.readValue(jwsObject.getPayload().toString(),
                    new TypeReference<JwsPayload>() {
                    });
            log.debug("Success verify JWS payload {}", jwsPayload);
            return Optional.of(jwsPayload);
        } catch (ParseException | JOSEException | JsonProcessingException exception) {
            log.warn("Failed parse JWS {}", exception.getMessage());
            return Optional.empty();
        }
    }
}
