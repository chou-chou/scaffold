package com.hrp.utils;

import com.hrp.entity.system.Authority;
import com.hrp.service.AuthorityService;
import com.hrp.utils.io.DesUtil;
import com.hrp.utils.io.SigarUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * SystemValidate
 *
 * @author KVLT
 * @date 2017-04-17.
 */
public class SystemValidate {

    protected static final Logger logger = LoggerFactory.getLogger(SystemValidate.class);

    /**
     * 验证系统有效性
     * @return
     */
    public static boolean validate() {
        boolean flag = false;

        String mac = SigarUtil.getMac();  // 本机MAC地址
        String entryStr = MD5.md5(Constant.salt + mac);  // 使用salt加密

        try {
            AuthorityService authorityService = ServiceHelper.getSysAuthorityService();
            List<Authority> authorityList = authorityService.listAll();

            String expire = "";
            for (Authority autho : authorityList) {
                if (entryStr.equalsIgnoreCase(autho.getAddress())) {  // 不区分大小写
                    expire = DesUtil.decrypt(autho.getExpire(), Constant.salt);
                    flag = DateUtil.dateCompare(expire);  // 当前时间在前，返回true
                    if (flag)   continue;
                }
            }

        } catch (Exception e) {
            logger.error("获取系统验证信息失败... ");
            e.printStackTrace();
        }

        return flag;
    }
}
