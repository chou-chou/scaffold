package com.hrp.filter;

import com.hrp.controller.common.BaseController;
import com.hrp.plugins.websocketInstantMsg.ChatServer;
import com.hrp.plugins.websocketOnline.OnlineChatServer;
import com.hrp.utils.Constant;
import com.hrp.utils.PropsUtil;
import com.hrp.utils.SystemValidate;
import org.java_websocket.WebSocketImpl;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * StartFilter
 * 启动tomcat时运行此类
 * @author KVLT
 * @date 2017-03-23.
 */
public class StartFilter extends BaseController implements Filter {

	/**
	 * 初始化
	 */
	public void init(FilterConfig fc) throws ServletException {
		this.startWebsocketInstantMsg();
		this.startWebsocketOnline();

        // 初始化ApplicationContext对象
        Constant.WEB_APP_CONTEXT = WebApplicationContextUtils.getWebApplicationContext(fc.getServletContext());

        boolean flag = SystemValidate.validate();
        ServletContext ctx =  ContextLoader.getCurrentWebApplicationContext().getServletContext();

        // 设置全局信息
        if (flag)
            ctx.setAttribute(Constant.SYS_ENABLED, true);
        else {
            ctx.setAttribute(Constant.SYS_ENABLED, false);
            logger.warn("系统过期... 请联系管理员！");
        }

	}
	
	/**
	 * 启动即时聊天服务
	 */
	public void startWebsocketInstantMsg(){
		WebSocketImpl.DEBUG = false;
		ChatServer s;
		try {
            String chatPort = PropsUtil.getValue("websocket.chat.port", Constant.CONFIG);//读取WEBSOCKET配置,获取端口配置
			if(null != chatPort && !"".equals(chatPort)){
                s = new ChatServer(Integer.parseInt(chatPort));
                s.start();
			}
			//System.out.println( "websocket服务器启动,端口" + s.getPort() );
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 启动在线管理服务
	 */
	public void startWebsocketOnline(){
		WebSocketImpl.DEBUG = false;
		OnlineChatServer s;
		try {
            String port = PropsUtil.getValue("websocket.manage.port", Constant.CONFIG);//读取WEBSOCKET配置,获取端口配置
			if(null != port && !"".equals(port)){
				s = new OnlineChatServer(Integer.parseInt(port));
				s.start();

			}
			//System.out.println( "websocket服务器启动,端口" + s.getPort() );
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	//计时器
	public void timer() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 9); // 控制时
		calendar.set(Calendar.MINUTE, 0); 		// 控制分
		calendar.set(Calendar.SECOND, 0); 		// 控制秒

		Date time = calendar.getTime(); 		// 得出执行任务的时间

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				
				//PersonService personService = (PersonService)ApplicationContext.getBean("personService");
				
				//System.out.println("-------设定要指定任务--------");
			}
		}, time, 1000*60*60*24);// 这里设定将延时每天固定执行
	}

	public void destroy() {

	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
						 FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		System.out.println(request.getRequestURL());
		String basePath = request.getContextPath();

        request.getSession().setAttribute("basePath", basePath);
        request.getSession().setAttribute("systemName", Constant.SYSTEM_NAME);
		filterChain.doFilter(request, servletResponse);
	}
	
}
