package com.hrp.utils.io;

import com.hrp.utils.MD5;
import org.hyperic.sigar.NetFlags;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.Sigar;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * SigarUtil
 *
 * @author KVLT
 * @date 2017-04-17.
 */
public class SigarUtil {

    public static String getMac() {
        Sigar sigar = new Sigar();
        try {
            String[] ifaces = sigar.getNetInterfaceList();
            String hwaddr = null;
            for (int i = 0; i < ifaces.length; i++) {
                NetInterfaceConfig cfg = sigar.getNetInterfaceConfig(ifaces[i]);
                if (NetFlags.LOOPBACK_ADDRESS.equals(cfg.getAddress())
                        || (cfg.getFlags() & NetFlags.IFF_LOOPBACK) != 0
                        || NetFlags.NULL_HWADDR.equals(cfg.getHwaddr())
                        || "0.0.0.0".equals(cfg.getNetmask())) {
                    continue;
                }

                if (!"0.0.0.0".equals(cfg.getNetmask())) {
                    hwaddr = cfg.getHwaddr();
                    break;
                }
            }

            return hwaddr != null ? hwaddr : null;
        } catch (Exception e) {
            return null;
        } finally {
            if (sigar != null)
                sigar.close();
        }
    }

    public String proper(String str) {
        InputStream InputStream = this.getClass().getResourceAsStream(System.getProperty("user.dir") + "/mac.properties");
        Properties properties = new Properties();
        try {
            properties.load(InputStream);//加载验证的配置文件

        } catch (IOException e) {

            e.printStackTrace();
        }
        String ValString = properties.getProperty(str);//读取value
        return ValString;
    }

    public static boolean license() {
        SigarUtil read = new SigarUtil();
        String pro = read.proper("mac");//配置文件验证信息
        String localString = getMac();//本地mac地址
        String longMac = localString + "tbyf";
        String md5Mac = MD5.md5(longMac);//拼接字符加密
        int arg = md5Mac.length();
        System.out.println(localString);
        System.out.println(md5Mac + "............." + arg);
        System.out.println(pro);
        boolean flag = false;
        if (pro.equals(md5Mac)) {
            flag = true;
        }
        return flag;

    }

}
