package com.seliote.mlb.common.jsr303.minio.validator;

import com.seliote.mlb.common.jsr303.minio.Filename;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.regex.Pattern;

/**
 * Minio 上传后返回的文件名 JSR 303 校验实现
 * FIXME
 * @author seliote
 * @version 2021-09-12
 */
@Slf4j
public class FilenameValidator implements ConstraintValidator<Filename, String> {

    //private final MlbService mlbService;

    private final Set<String> extensionSet = new CopyOnWriteArraySet<>();
    Pattern pattern = Pattern.compile("^\\d{8}-\\d{4}-\\d{4}-\\d{4}-\\d{12}\\.(\\w+)$");

    //@Autowired
    //public FilenameValidator(MlbService mlbService) {
    //    this.mlbService = mlbService;
    //}

    //@Override
    //public void initialize(Filename constraintAnnotation) {
    //    extensionSet.addAll(mlbService.minioSupportExtension());
    //}

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        var matcher = pattern.matcher(s);
        if (!matcher.find()) {
            log.warn("Filename {} not match pattern", s);
            return false;
        }
        var extension = matcher.group(1);
        //if (extensionSet.contains(extension)) {
        return true;
        //}
        //log.warn("File {} extension is not illegal", s);
        //return false;
    }
}
