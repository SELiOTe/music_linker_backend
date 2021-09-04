package com.seliote.mlb.auth.service;

import com.seliote.mlb.auth.domain.JwsPayload;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * JWS Service
 *
 * @author seliote
 * @version 2021-07-04
 */
@Validated
public interface JwsService {

    /**
     * 生成 JWS 签名
     *
     * @param jwsPayload JWS 负载
     * @return JWS 签名，签名失败时返回空
     */
    Optional<String> sign(@Valid @NotNull JwsPayload jwsPayload);

    /**
     * 解析 JWS 签名
     *
     * @param jws JWS 签名
     * @return JWS 负载，解析失败时返回空
     */
    @Valid
    Optional<JwsPayload> parse(@NotBlank String jws);
}
