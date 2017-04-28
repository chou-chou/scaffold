package com.hrp.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;

/**
 * Created by zhangdp on 2016/6/22.
 */
public class Base64ImgUtil {
    /**
     * 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
     *
     * @return
     */
    public static String ImgToBase64(String filePath) {
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
			File file = new File(filePath);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			if(!file.exists()){
				file.createNewFile();
			}
            in = new FileInputStream(file);
            data = new byte[in.available()];
            in.read(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }

    /**
     * 对字节数组字符串进行Base64解码并生成图片
     */
    public static boolean Base64ToImg(String imgStr, String filePath) {
        if (imgStr == null)
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        OutputStream out = null;
        try {
			File file = new File(filePath);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			if(!file.exists()){
				file.createNewFile();
			}
            out = new FileOutputStream(file);
            byte[] b = decoder.decodeBuffer(imgStr);
            out.write(b);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
