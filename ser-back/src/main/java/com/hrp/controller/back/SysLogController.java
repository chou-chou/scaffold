package com.hrp.controller.back;

import com.hrp.annotation.MvcMapping;
import com.hrp.controller.common.BaseController;
import com.hrp.entity.system.SysLog;
import com.hrp.entity.vo.DataTableView;
import com.hrp.service.SysLogService;
import com.hrp.utils.JsonUtil;
import com.hrp.utils.PageData;
import com.hrp.utils.lang.StringUtil;
import com.hrp.utils.plugins.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * SysLogController
 *
 * @author KVLT
 * @date 2017-07-19.
 */
@Controller
@RequestMapping("/b/syslog/")
public class SysLogController extends BaseController {

    // SysLog模块基础路径
    private final static String BASE_PATH = "back/system/syslog/";  // --> WEB-INF/views/back/system/syslog/

    @Resource
    private SysLogService sysLogService;

    /**
     * 系统日志主页面
     */
    @RequestMapping(method = RequestMethod.GET, value = "/list.do")
    @MvcMapping(tag = "syslog:list", path = BASE_PATH + "syslog_list", type = MvcMapping.ViewType.JSP)
    public ModelAndView list(Page page) {
        return this.getModelAndView(BASE_PATH + "syslog_list");
    }

    /**
     * 页面ajax分页取数
     * @return
     * @throws Exception
     */
    @RequestMapping("/fetchData.do")
    @ResponseBody
    public DataTableView<SysLog> fetchData() throws Exception {

        DataTableView<SysLog> dtv = new DataTableView<>();  // 回传页面的数据实体

        Page page = this.getPage();

        // 1
        getPageParam();

        if (StringUtil.isNullOrEmpty(orderColumn)) {
            orderColumn = "CREATE_DATE";
            orderDir = "desc";
        }

        // 2
        setPage(page);

        try {
            PageData pd = this.getPageData();
            String keywords = searchValue;  // 检索条件 关键词
            if (StringUtil.isNotNullOrBlank(keywords)){
                pd.put("keywords", keywords.trim());
            }
            page.setPd(pd);

            List<SysLog> logList = this.sysLogService.sysLogListPage(page);

            recordsTotal =  page.getTotalResult();
            recordsFiltered = recordsTotal;

            dtv.setDraw(Integer.parseInt(draw));
            dtv.setRecordsTotal(recordsTotal);
            dtv.setRecordsFiltered(recordsFiltered);
            dtv.setData(logList);
        } catch (Exception e) {
            logger.error("获取日志列表出错...", e);
        }

        return dtv;
    }

    /**
     * 获取明细数据
     * @return
     * @throws Exception
     */
    @RequestMapping("/logDetail.do")
    @ResponseBody
    public void logDetail(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String logId = request.getParameter("logId");
        SysLog log = this.sysLogService.findLogById(logId);

        JsonUtil.writeJsonToResponse(response, log, JsonUtil.OBJECT_TYPE_BEAN);
    }

}
