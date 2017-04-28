package com.hrp.utils;

import com.hrp.pojo.GenerateSetting;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.DefaultObjectWrapper;

import java.io.*;
import java.util.Locale;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;


/**
 * FreemarkerUtil
 * FreemarkerUtil 模版引擎类
 * @author KVLT
 * @date 2017-03-14.
 */
public class FreemarkerUtil {
    /**
     * 打印到控制台(测试用)
     *  @param ftlName
     */
    public static void print(String ftlName, Map<String,Object> root, String ftlPath) throws Exception{
        try {
            Template temp = getTemplate(ftlName, ftlPath);		//通过Template可以将模板文件输出到相应的流
            temp.process(root, new PrintWriter(System.out));
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 输出到输出到文件
     * @param ftlName   ftl文件名
     * @param root		传入的map
     * @param outFile	输出后的文件全部路径
     * @param filePath	输出前的文件上部路径
     */
    public static void printFile(String ftlName, Map<String,Object> root, String outFile, String filePath, String ftlPath) throws Exception{
        try {
            File file = new File(PathUtil.getClasspath() + filePath + outFile);
            if(!file.getParentFile().exists()){				//判断有没有父路径，就是判断文件整个路径是否存在
                file.getParentFile().mkdirs();				//不存在就全部创建
            }
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
            Template template = getTemplate(ftlName, ftlPath);
            template.process(root, out);					//模版输出
            out.flush();
            out.close();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过文件名加载模版
     * @param ftlName
     */
    public static Template getTemplate(String ftlName, String ftlPath) throws Exception{
        try {
            Configuration cfg = new Configuration();  												//通过Freemaker的Configuration读取相应的ftl
            cfg.setEncoding(Locale.CHINA, "utf-8");
            cfg.setDirectoryForTemplateLoading(new File(PathUtil.getClassResources()+"/ftl/"+ftlPath));		//设定去哪里读取相应的ftl模板文件
            Template temp = cfg.getTemplate(ftlName);												//在模板文件目录中找到名称为name的文件
            return temp;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取模板路径
     *
     * @param templateName
     *            模板名称（含后缀名）
     * @return
     * @throws IOException
     */
    public static String getTemplatePath(String templateName) throws IOException {
        Resource res = FreemarkerUtil.getResource(templateName);
        return res.getFile().getPath();
    }

    /**
     * 获取模板资源
     *
     * @param templateName
     *            模板名称（含后缀名）
     * @return Resource
     */
    public static Resource getResource(String templateName) {
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource res = resolver.getResource("/template/" + templateName);
        return res;
    }

    /**
     * 获取模板
     *
     * @param templateName
     *            模板名称（含后缀名）
     * @return Template
     * @throws IOException
     */
    public static Template getTemplate(String templateName) throws IOException {
        Configuration cfg = new Configuration();
        Template temp = null;
        File tmpRootFile = getResource(templateName).getFile().getParentFile();
        if (tmpRootFile == null) {
            throw new RuntimeException("无法取得模板根路径！");
        }
        try {
            cfg.setDefaultEncoding("utf-8");
            cfg.setOutputEncoding("utf-8");
            cfg.setDirectoryForTemplateLoading(tmpRootFile);
			/* cfg.setDirectoryForTemplateLoading(getResourceURL()); */
            cfg.setObjectWrapper(new DefaultObjectWrapper());
            temp = cfg.getTemplate(templateName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return temp;
    }

    /**
     * 根据freemark模板生成文件
     *
     * @param templateName
     *            模板名称（含后缀名）
     * @param filePath
     *            生成文件路径
     * @param setting
     *            参数
     */
    public static void generateFile(String templateName, String filePath, GenerateSetting setting)
            throws TemplateException, IOException {
        Writer writer = null;
        Template template = getTemplate(templateName);
        String dir = filePath.substring(0, filePath.lastIndexOf("\\"));
        File fdir = new File(dir);
        if (!fdir.exists()) {
            if (!fdir.mkdirs()) {
                System.out.println("创建目录" + fdir.getAbsolutePath() + "失败");
                return;
            }
        }
        File file = new File(filePath);
        if(file.exists())
            file.delete();
        writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
        template.setEncoding("utf-8");
        template.process(setting, writer);
        writer.flush();
        writer.close();
    }

}
