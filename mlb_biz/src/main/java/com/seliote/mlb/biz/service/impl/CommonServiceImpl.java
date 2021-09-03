package com.seliote.mlb.biz.service.impl;

import com.seliote.mlb.biz.domain.si.common.CheckCaptchaSi;
import com.seliote.mlb.biz.domain.si.common.SendSmsSi;
import com.seliote.mlb.biz.domain.so.country.CaptchaSo;
import com.seliote.mlb.biz.service.CommonService;
import com.seliote.mlb.common.exception.UtilException;
import com.seliote.mlb.common.service.RedisService;
import com.seliote.mlb.common.util.CommonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.QuadCurve2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

/**
 * 通用功能 Service 实现
 *
 * @author seliote
 * @version 2021-08-29
 */
@Slf4j
@Service
public class CommonServiceImpl implements CommonService {

    // 图形验证码 Redis 缓存 key
    private final String CAPTCHA_REDIS_KEY = "captcha";
    // 图形验证码过期时间
    private final Duration CAPTCHA_TIMEOUT = Duration.ofMinutes(5);
    // 图形验证码背景色
    private final Color CAPTCHA_BG_COLOR = Color.WHITE;
    // 图形验证码前景色范围
    private final Color[] CAPTCHA_FG_COLOR = new Color[]{Color.GRAY, Color.BLACK, Color.GREEN,
            Color.ORANGE, Color.RED, Color.BLUE};
    private final RedisService redisService;
    // 图形验证码基大小
    int CAPTCHA_BASE_SIZE = 200;

    @Autowired
    public CommonServiceImpl(RedisService redisService) {
        this.redisService = redisService;
    }

    @Override
    public CaptchaSo createCaptcha() {
        var uuid = UUID.randomUUID().toString();
        var captchaText = createCaptchaText();
        var bytes = createCaptchaImg(captchaText);
        redisService.set(CAPTCHA_TIMEOUT, captchaText, CAPTCHA_REDIS_KEY, uuid);
        return CaptchaSo.builder().uuid(uuid).captchaImg(CommonUtils.base64Encode(bytes)).build();
    }

    @Override
    public boolean checkCaptcha(CheckCaptchaSi si) {
        var captcha = redisService.get(CAPTCHA_REDIS_KEY, si.getUuid());
        if (captcha.isEmpty()) {
            return false;
        }
        return captcha.get().equalsIgnoreCase(si.getCaptcha());
    }

    @Override
    public void removeCaptcha(String uuid) {
        redisService.remove(CAPTCHA_REDIS_KEY, uuid);
    }

    @Override
    public boolean sendSms(SendSmsSi si) {
        var sb = new StringBuilder();
        for (int i = 0; i < VERIFY_CODE_LEN; i++) {
            sb.append(CommonUtils.getRandom().nextInt(10));
        }
        log.info("Send sign up verify code sms {} to +{}-{}", sb, si.getPhoneCode(), si.getTelNo());
        redisService.set(Duration.ofMinutes(5), sb.toString(),
                "sms", "sign_up", si.getPhoneCode(), si.getTelNo());
        return true;
    }

    /**
     * 创建图形验证码文本
     *
     * @return 图形验证码文本
     */
    private String createCaptchaText() {
        var sb = new StringBuilder();
        for (int i = 0; i < CAPTCHA_TEXT_LEN; i++) {
            var index = CommonUtils.getRandom().nextInt(CAPTCHA_TEXT_RANGE.length());
            var c = CAPTCHA_TEXT_RANGE.charAt(index);
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * 创建验证码图片
     *
     * @param captchaText 验证码文本
     * @return 验证码图片
     */
    private byte[] createCaptchaImg(String captchaText) {
        var img = new BufferedImage((captchaText.length() + 1) * CAPTCHA_BASE_SIZE,
                CAPTCHA_BASE_SIZE * 2, BufferedImage.TYPE_INT_RGB);
        var g2d = img.createGraphics();
        g2d.setColor(CAPTCHA_BG_COLOR);
        g2d.fillRect(0, 0, img.getWidth(), img.getHeight());
        drawCaptchaText(g2d, captchaText);
        drawCaptchaInterference(img, g2d);
        g2d.dispose();
        var outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(img, "PNG", outputStream);
        } catch (IOException e) {
            log.error("Failed write captcha image, IO error, {}", e.getMessage());
            throw new UtilException(e);
        }
        return outputStream.toByteArray();
    }

    /**
     * 绘制验证码文本
     *
     * @param g2d         Graphics2D 对象
     * @param captchaText 验证码文本
     */
    private void drawCaptchaText(Graphics2D g2d, String captchaText) {
        // 左侧留白
        final var leftSpace = CAPTCHA_BASE_SIZE / 2;
        // 角度转弧度
        final var angle2Radian = Math.PI / 180;
        // 画字
        for (int i = 0; i < captchaText.length(); i++) {
            // 随机设置字体样式
            g2d.setColor(CAPTCHA_FG_COLOR[CommonUtils.getRandom().nextInt(CAPTCHA_FG_COLOR.length)]);
            g2d.setFont(new Font(null,
                    CommonUtils.getRandom().nextInt(2) == 0 ? Font.ITALIC : Font.BOLD,
                    CAPTCHA_BASE_SIZE));
            // 计算当前字体的起点位置，左上角
            var x = leftSpace + i * CAPTCHA_BASE_SIZE + CommonUtils.getRandom().nextInt(CAPTCHA_BASE_SIZE / 2);
            var y = CAPTCHA_BASE_SIZE * 0.85 + CommonUtils.getRandom().nextInt(CAPTCHA_BASE_SIZE);
            var radian = (CommonUtils.getRandom().nextInt(90) - 45) * angle2Radian;
            // 转换并绘制
            g2d.translate(x, y);
            g2d.rotate(radian);
            g2d.drawString(captchaText.substring(i, i + 1), 0, 0);
            // 转回去
            g2d.rotate(-radian);
            g2d.translate(-x, -y);
        }
    }

    /**
     * 绘制干扰线
     *
     * @param img BufferedImage 对象
     * @param g2d Graphics2D 对象
     */
    private void drawCaptchaInterference(BufferedImage img, Graphics2D g2d) {
        final var imageWidth = img.getWidth();
        final var imageHeight = img.getHeight();
        // 干扰线数量
        final var interferenceLineCount = (imageWidth / imageHeight) * 10;
        // 干扰线最大宽度
        final var interferenceLineMaxWidth = Math.round(imageHeight * 0.03F);
        for (int i = 0; i < interferenceLineCount; i++) {
            g2d.setColor(CAPTCHA_FG_COLOR[CommonUtils.getRandom().nextInt(CAPTCHA_FG_COLOR.length)]);
            g2d.setStroke(new BasicStroke(CommonUtils.getRandom().nextInt(interferenceLineMaxWidth)));
            if (CommonUtils.getRandom().nextBoolean()) {
                // 画直线
                g2d.drawLine(CommonUtils.getRandom().nextInt(imageWidth), CommonUtils.getRandom().nextInt(imageHeight),
                        CommonUtils.getRandom().nextInt(imageWidth), CommonUtils.getRandom().nextInt(imageHeight));
            } else {
                // 画曲线
                g2d.draw(new QuadCurve2D.Double(
                        CommonUtils.getRandom().nextInt(imageWidth), CommonUtils.getRandom().nextInt(imageHeight),
                        CommonUtils.getRandom().nextInt(imageWidth), CommonUtils.getRandom().nextInt(imageHeight),
                        CommonUtils.getRandom().nextInt(imageWidth), CommonUtils.getRandom().nextInt(imageHeight)));
            }
        }
    }
}
