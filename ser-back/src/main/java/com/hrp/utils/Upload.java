package com.hrp.utils;


import com.hrp.entity.system.User;
import com.hrp.utils.lang.StringUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

/**
 * Upload
 *
 * @author KVLT
 * @date 2017-04-17.
 */
public class Upload extends HttpServlet {

    private static final int COUNT_LENGTH = 5; // 计数字符串位数
    private static final int MIN_VALUE = 00001;
    private static final int MAX_VALUE = 99999;

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_hhmmss");  // 日期格式化工具
    private static AtomicInteger counter = new AtomicInteger(MIN_VALUE);  // 原子计数器，上限32767

    @SuppressWarnings("unchecked")
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 获取当前用户账号
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("userInfoSession");

        String path = request.getParameter("path");
        String savePath = this.getServletConfig().getServletContext()
                .getRealPath("");
        if (StringUtil.isNullOrEmpty(path))
            savePath = savePath + "/uploads/";
        else
            savePath = savePath + "/" + path + "/";

        File f1 = new File(savePath);
        if (!f1.exists()) {
            f1.mkdirs();
        }
        DiskFileItemFactory fac = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(fac);
        upload.setHeaderEncoding("utf-8");
        List fileList = null;
        try {
            fileList = upload.parseRequest(request);
        } catch (FileUploadException ex) {
            return;
        }

        Iterator<FileItem> it = fileList.iterator();
        String name = "";
        String wholeName = "";
        String extName = "";
        String temp = "";
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");  // 正则表达式判断是否包含中文

        while (it.hasNext()) {
            FileItem item = it.next();
            if (!item.isFormField()) {
                wholeName = item.getName();
                long size = item.getSize();
                String type = item.getContentType();
                if (wholeName == null || wholeName.trim().equals("")) {
                    continue;
                }

                // 文件名名称和扩展名格式：
                if (wholeName.lastIndexOf(".") >= 0) {
                    name = wholeName.replace(wholeName.substring(wholeName.lastIndexOf(".")),
                            "");
                    extName = wholeName.substring(wholeName.lastIndexOf("."));
                }

				/*temp = name;
                File file = new File(savePath + name + extName);
				int i = 1;
				while (file.exists()) {
					name= temp + "(" + i + ")";
					file = new File(savePath + name + extName);
					i++;
				}

				Matcher m = p.matcher(name);
				if (m.find()) {
					name = Tool.getUUID();
		        }*/

                String time = sdf.format(new Date());
                String account =
                        (user != null && StringUtil.isNotNullOrBlank(user.getAccount()))
                                ? user.getAccount() : "000000";
                String count = "000000";
                try {
                    count = getSerial();
                } catch (Exception e) {
                    count = "except";
                }

                name = time + "_" + account + "_" + count; // 文件名格式： 时间_用户账号_计数

                File saveFile = new File(savePath + name + extName);
                try {
                    item.write(saveFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        // response.getWriter().print(name + extName);
        String jumpPage = request.getParameter("jumpPage");
        if ("importHuman".equals(jumpPage)) {
            session.setAttribute("Session_importHumanSavePath", savePath + name + extName);
            response.getWriter().print(wholeName);
            return;
        }
        response.getWriter().print(savePath + name + extName);
    }

    /**
     * 获取计数器对应的字符串，
     *
     * @return
     * @throws Exception
     */
    protected String getSerial() throws Exception {
        int sn = counter.getAndIncrement();
        if (sn + 1 > MAX_VALUE) {  // 如果达到上限，则更新上限
            synchronized (counter) {  // counter是AtomicInteger类型
                if (sn + 1 > MAX_VALUE) {// 如果仍然达到上限，则更新上限
                    counter.set(MIN_VALUE);  // 更新上限
                    sn = counter.getAndIncrement();
                }
            }
        }
        return seiralFormat(sn, COUNT_LENGTH);
    }

    protected String seiralFormat(int sn, int length) {
        String tmp = String.valueOf(sn);
        StringBuilder sb = new StringBuilder();
        for (int i = tmp.length(); i < length; i++) {
            sb.append("0");
        }

        sb.append(tmp);

        return sb.toString();
    }

}
