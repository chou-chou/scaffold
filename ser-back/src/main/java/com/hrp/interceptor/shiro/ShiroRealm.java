package com.hrp.interceptor.shiro;

import com.hrp.entity.system.User;
import com.hrp.service.MenuService;
import com.hrp.service.UserService;
import com.hrp.utils.Constant;
import com.hrp.utils.PropertiesUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Set;

/**
 * ShiroRealm
 * 系统安全认证实现类
 */
public class ShiroRealm extends AuthorizingRealm {

    protected final static Logger logger = LoggerFactory.getLogger(ShiroRealm.class);

	/**
	 * 认证回掉函数，登录时调用
	 */
	@Resource(name = "userService")
	private UserService userService;
	@Resource(name = "menuService")
	private MenuService menuService;

	/**
	 * 登录信息和用户验证信息验证[登录时调用]
	 * @param authcToken	含登录名密码的信息
	 * @return 				认证信息
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        // 获取基于用户名和密码的令牌   实际上这个authcToken是从IndexController里面subject.login(token)传递来的
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

		// 校验用户名密码
		String username = token.getUsername();
		String password = String.copyValueOf(token.getPassword());

        logger.info("password from token: " + password);
		User user = null;

		try {
			user = userService.getUserByLoginName(username);
			if (user != null) {
                logger.info("用户密码： " + user.getPassword());
				/*if (!password.equalsIgnoreCase(user.getPassword())) {  //  && isNeedPassword()
					throw new IncorrectCredentialsException("msg：用户信息验证失败!");
				}*/
                if (Boolean.TRUE.equals(user.isLocked())) {  // 账号锁定
                    throw new LockedAccountException("msg: 该账号已锁定!");
                }
				// 此处的返回值没有使用加盐方式,如需要加盐，可以在密码参数上处理
				//return new SimpleAuthenticationInfo(user, password, username);
                // 可以交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，也可以自定义实现
                SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                        user.getUserName(), //用户名
                        user.getPassword(), //密码
                        ByteSource.Util.bytes(username + Constant.salt), // salt = username + salt
                        getName()  //realm name
                );

                this.setSession(Constant.CURRENT_USER, user);
                this.setSession(Constant.CURRENT_USERNAME, username);

                return authenticationInfo;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		throw new UnknownAccountException(); // 没找到账号
	}
	
	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用,负责在应用程序中决定用户的访问控制的方法(non-Javadoc)
	 * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
	 * shiro 权限控制有三种:
	 * 	1、通过xml配置资源的权限
	 * 	2、通过shiro标签控制权限
	 * 	3、通过shiro注解控制权限
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		if (principals == null) {
			throw new AuthorizationException("parameters principals is null");
		}
		//获取已认证的用户名（登录名）
		//String username = (String)super.getAvailablePrincipal(pc);
        String username = (String)principals.getPrimaryPrincipal();

		Set<String> roleCodes = userService.findAllRoleNamesByUsername(username);
		Set<String> functionCodes = menuService.getMenuCodeSet(roleCodes);

		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.setRoles(roleCodes);  // 查询用户的角色放入凭证中
		authorizationInfo.setStringPermissions(functionCodes);  // 查询用户权限放入凭证中
		return authorizationInfo;
	}

	//是否需要校验密码登录，用于开发环境 0默认为开发环境，其他为正式环境（1，或者不配）
	public boolean isNeedPassword(){
		String version = PropertiesUtil.getValue("system.version");
		if("0".equals(version))
			return false;
		else
			return true;
	}

    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

    private Session getSession(){
        try{
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession(false);
            if (session == null){
                session = subject.getSession();
            }
            if (session != null){
                return session;
            }
        }catch (InvalidSessionException e){

        }
        return null;
    }

    /**
     * 保存登录名
     */
    private void setSession(Object key, Object value) {
        Session session = getSession();
        logger.info("Session 默认超时时间为 [" + session.getTimeout() + "]毫秒");
        if (null != session) {
            session.setAttribute(key, value);
        }
    }

}
