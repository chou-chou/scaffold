package com.hrp.dao;

import java.util.List;

/**
 * BaseDao
 *
 * @author KVLT
 * @date 2017-03-14.
 */
public interface BaseDao {

    /**
     * 保存对象
     * @param str
     * @param obj
     * @return
     * @throws Exception
     */
    public Object save(String str, Object obj) throws Exception;

    /**
     * 修改对象
     * @param str
     * @param obj
     * @return
     * @throws Exception
     */
    public Object update(String str, Object obj) throws Exception;

    /**
     * 删除对象
     * @param str
     * @param obj
     * @return
     * @throws Exception
     */
    public Object delete(String str, Object obj) throws Exception;

    /**
     * 批量保存
     * @param str
     * @param objs
     * @return
     * @throws Exception
     */
    public Object batchSave(String str, List objs) throws Exception;

    /**
     * 批量更新
     * @param str
     * @param objs
     * @return
     * @throws Exception
     */
    public void batchUpdate(String str, List objs )throws Exception;

    /**
     * 查找对象
     * @param str
     * @param obj
     * @return
     * @throws Exception
     */
    public Object findForObject(String str, Object obj) throws Exception;

    /**
     * 查找对象
     * @param str
     * @param obj
     * @return
     * @throws Exception
     */
    public Object findForList(String str, Object obj) throws Exception;

    /**
     * 查找对象封装成Map
     * @param sql
     * @param obj
     * @return
     * @throws Exception
     */
    public Object findForMap(String sql, Object obj, String key) throws Exception;

    /**
     * 获取记录总数
     * @return
     */
    public Long findAllCount(String str, Object obj);

    /**
     * 批量新增
     */
    public <T> boolean insertBatch(String str, List<T> objs);

}
