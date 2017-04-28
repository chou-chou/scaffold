package com.hrp.utils.io;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * LockedClassLoader
 * JAVA加密class和解密class加载运行
 * @author KVLT
 * @date 2017-04-17.
 */
public class LockedClassLoader extends ClassLoader {
    public static final int CLASS_LOADER_CHAR = 0x12FFAADE;
    private String fname;

    public LockedClassLoader() {
        super();
    }

    public LockedClassLoader(String fname) {
        super();
        this.fname = fname;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String clazzName = name.indexOf(".") != -1 ?
                name.substring(name.lastIndexOf(".") + 1) : name;
        byte[] bs = decrypt(new File(fname + "\\" + clazzName + ".class"));
        return super.defineClass(name, bs, 0, bs.length);
    }

    static byte[] decrypt(File writeFile) {
        try {
            if (!writeFile.exists()) {
                return null;
            }
            InputStream is = new FileInputStream(writeFile);
            byte[] b = new byte[1];
            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            int len = b.length;
            while (is.read(b) != -1) {
                for (int x=0; x<len; x++) {
                    b[x] = (byte) (b[x] ^ CLASS_LOADER_CHAR);
                }
                bos.write(b);
            }

            is.close();
            byte[] bytes = bos.toByteArray();
            bos.close();
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    static boolean encrypt(File readFile, File writeFile) {
        if (!readFile.exists()) {
            return false;
        }
        try {
            InputStream is = new FileInputStream(readFile);
            OutputStream os = new FileOutputStream(writeFile + "\\"
                    + readFile.getName());
            byte[] b = new byte[1];
            int len = b.length;
            while (is.read(b) != -1) {
                for (int x = 0; x < len; x++) {
                    b[x] = (byte) (b[x] ^ CLASS_LOADER_CHAR);
                }
                os.write(b);
            }
            is.close();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static void main(String[] args) throws Exception {
        File rfile = new File("WebRoot\\WEB-INF\\classes\\com\\ehr\\util\\Constant.class");
        File wfile = new File("lib");
        encrypt(rfile, wfile);
        Class<?> clazz = new LockedClassLoader("lib")
                .findClass("com.ehr.util.Constant");
    }
}
