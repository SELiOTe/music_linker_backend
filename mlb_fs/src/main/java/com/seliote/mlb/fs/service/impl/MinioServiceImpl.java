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

import java.io.ByteArrayInputStream;
import java.io.IOException;
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

    // Minio 路径分隔符
    private static final String MINIO_PATH_SEPARATOR = "/";

    private final YmlConfig.Minio minio;
    private final MinioClient minioClient;

    @Autowired
    public MinioServiceImpl(YmlConfig ymlConfig, MinioClient minioClient) {
        this.minio = ymlConfig.getMinio();
        this.minioClient = minioClient;
    }

    @Override
    public String upload(String catalog, String extension, byte[] bytes) throws MinioException {
        String filename = UUID.randomUUID().toString();
        // 分为 26 * 26 目录存储，避免同级存在大量文件
        String path = catalog + MINIO_PATH_SEPARATOR + filename.charAt(0) + MINIO_PATH_SEPARATOR
                + filename.charAt(1) + MINIO_PATH_SEPARATOR + filename + "." + extension;
        try (var is = new ByteArrayInputStream(bytes)) {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(minio.getBucket())
                    .object(path)
                    .stream(is, -1, 1024 * 1024 * 10)
                    .build());
            log.info("Upload file {}", path);
            return filename;
        } catch (io.minio.errors.MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException
                exception) {
            log.info("Failed upload file {}, {}", filename, exception.getMessage());
            throw new MinioException(exception);
        }
    }

    @Override
    public byte[] download(String catalog, String filename) throws MinioException {
        String path = catalog + MINIO_PATH_SEPARATOR + filename.charAt(0) + MINIO_PATH_SEPARATOR
                + filename.charAt(1) + MINIO_PATH_SEPARATOR + filename;
        try (var is = minioClient.getObject(GetObjectArgs.builder()
                .bucket(minio.getBucket())
                .object(path)
                .build())) {
            log.info("Download file {}", path);
            return is.readAllBytes();
        } catch (io.minio.errors.MinioException | InvalidKeyException | IOException | NoSuchAlgorithmException
                exception) {
            log.info("Failed download file {}, {}", path, exception.getMessage());
            throw new MinioException(exception);
        }
    }
}
