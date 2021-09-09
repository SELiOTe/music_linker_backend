package com.seliote.mlb.common.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * YML 相关配置
 *
 * @author seliote
 * @version 2021-07-02
 */
@Component
@ConfigurationProperties(prefix = "com.seliote.mlb")
@Getter
@Setter
public class YmlConfig {

    private String defaultUserAvatar;
    private final Redis redis = new Redis();
    private final Jws jws = new Jws();
    private final Minio minio = new Minio();

    @Getter
    @Setter
    public static class Redis {
        private String namespace;
        private String keySeparator;
    }

    @Getter
    @Setter
    public static class Jws {
        private String iss;
        private Long validDays;
        private String secret;
    }

    @Getter
    @Setter
    public static class Minio {
        private final Catalog catalog = new Catalog();
        private final Extension extension = new Extension();
        private String endpoint;
        private String accessKey;
        private String secretKey;
        private String bucket;

        @Getter
        @Setter
        public static class Catalog {
            private String img;
            private String audio;
        }

        @Getter
        @Setter
        public static class Extension {
            private String imgPng;
            private String imgJpg;
            private String imgJpeg;
            private String audioMp3;
        }
    }
}
