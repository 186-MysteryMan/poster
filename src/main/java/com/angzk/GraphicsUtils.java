package com.angzk;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;

/**
 * 海报 画笔绘制工具类
 *
 * @author Angzk
 * @date 2019年8月7日
 */
public class GraphicsUtils {

    /**
     * 代码中的 坐标 依据UI 切图的 像素值。 请根据个人需要调整
     *
     * @param linkUrl       分享链接
     * @param logoStatus    是否加logo 一般是 用户的头像. 这里的logo是加在 二维码的中间.
     * @param logoPath      logo地址
     * @param backgroundUrl 背景模板地址
     * @param spuPicUrl     商品 主图
     * @param price         原价
     * @param spuName       spuName
     */
    public static void createPosterByRedTemplate(String linkUrl, boolean logoStatus, String logoPath,
                                                 String backgroundUrl, String spuPicUrl, String nickName,String text, String price, String spuName) {

        System.err.println("LOGO 地址 [logoPath] : " + logoPath);
        System.err.println("背景板 地址 [backgroundUrl] : " + backgroundUrl);
        System.err.println("商品图片 地址 [spuPicUrl] : " + spuPicUrl);
        // 生成二维码
        BufferedImage qrCodeImage = QrCodeGraphicsUtils.createQrCode(linkUrl, false, logoStatus, logoPath, true, 140);

        // 海报背景
        BufferedImage bufferImage = QrCodeBaseUtils.imageToBufferedImage(backgroundUrl);
        if (bufferImage != null) {
            Graphics2D graphics;
//            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            graphics = bufferImage.createGraphics();
            // 绘制 qrCode
            graphics.drawImage(qrCodeImage, 230, 515, null);

            // 绘制 头像
            graphics = QrCodeGraphicsUtils.drawAvatar(graphics, logoPath, bufferImage, 13, 10);

            // 商品主图
            BufferedImage spuPicBufferImage = QrCodeBaseUtils.imageToBufferedImage(spuPicUrl);
            // 绘制商品主图
            graphics.drawImage(spuPicBufferImage, 35, 112, null);

            // 文本
            QrCodeGraphicsUtils.drawTextNewLine(graphics, text, 33, 570, 20, 170, Color.BLACK, 13, 4, 60);

            // 昵称
            Font font = new Font("微软雅黑", Font.PLAIN, 13);
            graphics.setFont(msyhFont);
            graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            QrCodeGraphicsUtils.drawText(graphics, nickName, 93, 38, Color.BLACK);
            // 我推荐一个好物
            Font font2 = new Font("思源黑体", Font.BOLD, 20);
            graphics.setFont(msyhBoldFont);
            graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            QrCodeGraphicsUtils.drawText(graphics, spuName, 93, 65, Color.WHITE);

            // 价格
            Font font3 = new Font("微软雅黑", Font.PLAIN, 16);
            graphics.setFont(msyhFont);
            graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            QrCodeGraphicsUtils.drawText(graphics, price, 33, 450,new Color(222, 96, 58));

            graphics.dispose();

            ByteArrayOutputStream os = null;

            try {
                os = new ByteArrayOutputStream();
                ImageIO.write(bufferImage, "JPG", os);
                QrCodeGraphicsUtils.savePic(bufferImage, 15, "png", 1, System.currentTimeMillis() + "");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

                try {
                    if (os != null) {
                        os.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * getResource 返回的 Path 可能会以 / 开头,这里做了截取.
     *
     * @param path 图片地址
     * @return String
     */
    public static String handlePath(String path) {
        if (StringUtils.isNotBlank(path)) {
            if (path.startsWith("/")) {
                System.err.println("true");
                path = path.substring(1);
            }
        }
        return path;
    }

    private static Font msyhFont;

    private static Font msyhBoldFont;

    static {
        InputStream inputStream = null;
        InputStream boldInputStream = null;
        try {
            inputStream = GraphicsUtils.class.getClassLoader().getResourceAsStream("font/苹方黑体-准-简.ttf");
            boldInputStream = GraphicsUtils.class.getClassLoader().getResourceAsStream("font/苹方黑体-中黑-简.ttf");
            if (inputStream != null && boldInputStream != null) {
                msyhFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
                msyhFont = msyhFont.deriveFont(Font.PLAIN,20);
                msyhBoldFont = Font.createFont(Font.TRUETYPE_FONT, boldInputStream);
                msyhBoldFont = msyhBoldFont.deriveFont(Font.BOLD,20);
            }
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        } finally {
            CloseStreamUtil.close(boldInputStream,inputStream,"字体异常");
        }
    }
}
