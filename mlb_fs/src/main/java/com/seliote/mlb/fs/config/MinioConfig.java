package com.seliote.mlb.fs.config;

import com.seliote.mlb.common.config.YmlConfig;
import io.minio.BucketExistsArgs;
import io.minio.MinioClient;
import io.minio.errors.MinioException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Minio 配置
 *
 * @author seliote
 * @version 2021-07-02
 */
@Slf4j
@Configuration
public class MinioConfig {

    private final YmlConfig.Minio minio;

    @Autowired
    public MinioConfig(YmlConfig ymlConfig) {
        this.minio = ymlConfig.getMinio();
    }

    /**
     * 创建 MinioClient Bean
     *
     * @return MinioClient 对象
     */
    @Bean
    public MinioClient minioClient() throws MinioException, IOException, NoSuchAlgorithmException, InvalidKeyException {
        var client = MinioClient.builder()
                .endpoint(minio.getEndpoint())
                .credentials(minio.getAccessKey(), minio.getSecretKey())
                .build();
        boolean found = client.bucketExists(BucketExistsArgs.builder().bucket(minio.getBucket()).build());
        if (!found) {
            log.error("Minio bucket {} not found! Please create it first", minio.getBucket());
            throw new MinioException("Bucket not found");
        }
        log.info("Minio bucket {}", minio.getBucket());
        return client;
    }
}
