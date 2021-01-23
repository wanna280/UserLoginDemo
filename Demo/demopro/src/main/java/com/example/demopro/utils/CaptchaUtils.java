package com.example.demopro.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

public class CaptchaUtils {
    private int w = 100;    //设置图片宽度
    private int h = 40;        //设置图片高度
    private Color bgColor = new Color(255, 255, 255);        //设置图片背景
    Random random = new Random();
    StringBuilder sb = new StringBuilder();

    private BufferedImage createImage() {
        /*
         * 1.创建图片
         * 2.设置背景色
         */
        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        //设置画笔颜色
        Graphics2D graphics2d = (Graphics2D) img.getGraphics();
        graphics2d.setColor(this.bgColor);
        graphics2d.fillRect(0, 0, w, h);
        //填充图片大小的矩形（设置背景色）
        return img;
    }

    private Color randomColor() {
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        return new Color(r, g, b);
    }

    /**
     * 设置 字体、字号、样式
     *
     * @return
     */
    private String[] fontName = {"宋体", "华文楷体", "黑体", "华文新魏", "华文隶书", "微软雅黑"};
    private int[] fontSize = {28, 32, 35, 39, 45};

    private Font randomFont() {
        int index = random.nextInt(fontName.length);
        //根据随机索引获取字体名
        String name = fontName[index];
        //设置字体样式
        int style = random.nextInt(4);
        index = random.nextInt(fontSize.length);
        //根据索引获取字体大小
        int size = fontSize[index];
        return new Font(name, style, size);
    }

    private String codes = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRESTUVWXYZ0123456789";

    private String randomChar() {
        int index = random.nextInt(codes.length());
        //返回随机字符
        return codes.charAt(index) + "";
    }

    private void drawLine(BufferedImage image) {
        int num = 3;
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        for (int i = 0; i < num; i++) {
            int x1 = random.nextInt(w);
            int y1 = random.nextInt(h);
            int x2 = random.nextInt(w);
            int y2 = random.nextInt(h);
            g2.setStroke(new BasicStroke(1.5F));
            g2.setColor(Color.BLUE);
            g2.drawLine(x1, y1, x2, y2);
        }
    }

    public BufferedImage getImage() {
        /**
         * 写入字符（包括字符字体、颜色、字符种类）
         */
        BufferedImage img = this.createImage();
        Graphics g = img.getGraphics();
        for (int i = 0; i < 4; i++) {
            //设置字符
            String ch = this.randomChar();
            sb.append(ch);
            //设置画笔颜色
            g.setColor(this.randomColor());
            //设置画笔字体
            g.setFont(this.randomFont());
            //画出字符
            g.drawString(ch, w / 4 * i, h - 5);
        }
        drawLine(img);
        return img;
    }

    //获取验证码的字符
    public String getText() {
        return sb.toString();
    }

    public static void saveImage(BufferedImage img, OutputStream out) throws Exception {
        ImageIO.write(img, "jpeg", out);
    }


    /**
     * BufferedImage 编码转换为 base64
     *
     * @param bufferedImage
     * @return
     */
    public String BufferedImageToBase64(BufferedImage bufferedImage) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();//io流
        try {
            ImageIO.write(bufferedImage, "png", baos);//写入流中
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = baos.toByteArray();//转换成字节
        BASE64Encoder encoder = new BASE64Encoder();
        String png_base64 = encoder.encodeBuffer(bytes).trim();//转换成base64串
        png_base64 = png_base64.replaceAll("\n", "").replaceAll("\r", "");//删除 \r\n
        //System.out.println("值为：" + "data:image/jpg;base64," + png_base64);
        return "data:image/jpg;base64," + png_base64;
    }

    /**
     * base64 编码转换为 BufferedImage
     *
     * @param base64
     * @return
     */
    public BufferedImage base64ToBufferedImage(String base64) {
        BASE64Decoder decoder = new sun.misc.BASE64Decoder();
        try {
            byte[] bytes1 = decoder.decodeBuffer(base64);
            ByteArrayInputStream bais = new ByteArrayInputStream(bytes1);
            return ImageIO.read(bais);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}