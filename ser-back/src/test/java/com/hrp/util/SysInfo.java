package com.hrp.util;

import com.hrp.utils.Constant;
import com.hrp.utils.DateUtil;
import com.hrp.utils.MD5;
import com.hrp.utils.io.DesUtil;
import com.hrp.utils.io.SigarUtil;
import org.junit.Test;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * SysInfo
 *
 * @author KVLT
 * @date 2017-04-17.
 */
public class SysInfo extends BaseDemo {

    @Test
    public void sysInfo() throws Exception {
        String mac = SigarUtil.getMac();  // 本机MAC地址
        String address = MD5.md5(Constant.salt + mac);  // 使用salt加密

        String date = "2088-01-01 00:00:00";
        String expire = DesUtil.encrypt(date, Constant.salt);

        logger.info(address + "\n" + expire);

        expire = DesUtil.decrypt(expire, Constant.salt);
        boolean flag = DateUtil.dateCompare(expire);  // 当前时间在前，返回true

        logger.info("是否有效： " + flag);
    }
    @Test
    public void test() throws Exception {
        Date a=new Date();
        System.currentTimeMillis();
        //System.out.print(a);
       // String str=System.getProperty("java.library.path");
       // System.out.print(str);
        int b = 8;
        int c=b<<3;
        System.out.print(c);
        Integer as=0;
        System.out.print(as);

    }


}
