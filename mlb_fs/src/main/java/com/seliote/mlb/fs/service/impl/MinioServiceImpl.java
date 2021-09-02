package com.seliote.mlb.fs.service.impl;

import com.seliote.mlb.common.config.YmlConfig;
import com.seliote.mlb.fs.exception.MinioException;
import com.seliote.mlb.fs.service.MinioService;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * Minio Service 实现
 *
 * @author seliote
 * @version 2021-07-02
 */
@Slf4j
@Service
public class MinioServiceImpl implements MinioService {

    private final YmlConfig.Minio minio;
    private final MinioClient minioClient;

    @Autowired
    public MinioServiceImpl(YmlConfig ymlConfig, MinioClient minioClient) {
        this.minio = ymlConfig.getMinio();
        this.minioClient = minioClient;
    }

    @Override
    public String upload(String catalog, String extension, InputStream inputStream) throws MinioException {
        // Minio 路径分隔符
        final String MINIO_PATH_SEPARATOR = "/";
        String filename = UUID.randomUUID().toString();
        // 分为 26 * 26 目录存储，避免同级存在大量文件
        filename = catalog + MINIO_PATH_SEPARATOR + filename.charAt(0) + MINIO_PATH_SEPARATOR
                + filename.charAt(1) + MINIO_PATH_SEPARATOR + filename + "." + extension;
        try {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(minio.getBucket())
                    .object(filename)
                    .stream(inputStream, -1, 1024 * 1024 * 10)
                    .build());
            log.info("Upload file {}", filename);
            return filename;
        } catch (io.minio.errors.MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException
                exception) {
            log.info("Failed upload file {}, {}", filename, exception.getMessage());
            throw new MinioException(exception);
        }
    }

    @Override
    public InputStream download(String filename) throws MinioException {
        try {
            var inputStream = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(minio.getBucket())
                    .object(filename)
                    .build());
            log.info("Download file {}", filename);
            return inputStream;
        } catch (io.minio.errors.MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException
                exception) {
            log.info("Failed download file {}, {}", filename, exception.getMessage());
            throw new MinioException(exception);
        }
    }
}
