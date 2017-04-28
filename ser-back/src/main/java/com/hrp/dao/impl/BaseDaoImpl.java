package com.hrp.dao.impl;

import com.hrp.dao.BaseDao;
import com.hrp.utils.Constant;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * BaseDaoImpl
 *
 * @author KVLT
 * @date 2017-03-14.
 */
@Repository("baseDao")
public class BaseDaoImpl implements BaseDao {

    private static final Logger logger = LoggerFactory.getLogger(BaseDaoImpl.class);

    @Resource(name = "sqlSessionTemplate")
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 保存对象
     * @param str
     * @param obj
     * @return
     * @throws Exception
     */
    public Object save(String str, Object obj) throws Exception {
        return sqlSessionTemplate.insert(str, obj);
    }

    /**
     * 批量保存
     * @param str
     * @param objs
     * @return
     * @throws Exception
     */
    public Object batchSave(String str, List objs) throws Exception {
        return sqlSessionTemplate.insert(str, objs);
    }

    /**
     * 修改对象
     * @param str
     * @param obj
     * @return
     * @throws Exception
     */
    public Object update(String str, Object obj) throws Exception {
        return sqlSessionTemplate.update(str, obj);
    }

    /**
     * 批量更新
     * @param str
     * @param objs
     * @return
     * @throws Exception
     */
    public void batchUpdate(String str, List objs )throws Exception {
        SqlSessionFactory sqlSessionFactory = sqlSessionTemplate.getSqlSessionFactory();
        //批量执行器
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
        try{
            if(objs!=null){
                for(int i=0,size=objs.size();i<size;i++){
                    sqlSession.update(str, objs.get(i));
                }
                sqlSession.flushStatements();
                sqlSession.commit();
                sqlSession.clearCache();
            }
        }finally{
            sqlSession.close();
        }
    }

    /**
     * 批量删除
     * @param str
     * @param objs
     * @return
     * @throws Exception
     */
    public Object batchDelete(String str, List objs)throws Exception{
        return sqlSessionTemplate.delete(str, objs);
    }

    /**
     * 删除对象
     * @param str
     * @param obj
     * @return
     * @throws Exception
     */
    public Object delete(String str, Object obj) throws Exception {
        return sqlSessionTemplate.delete(str, obj);
    }

    /**
     * 查找对象
     * @param str
     * @param obj
     * @return
     * @throws Exception
     */
    public Object findForObject(String str, Object obj) throws Exception {
        return sqlSessionTemplate.selectOne(str, obj);
    }

    /**
     * 查找对象
     * @param str
     * @param obj
     * @return
     * @throws Exception
     */
    public Object findForList(String str, Object obj) throws Exception {
        return sqlSessionTemplate.selectList(str, obj);
    }

    /**
     * 查找对象封装成Map
     * @param str
     * @param obj
     * @param key
     * @return
     * @throws Exception
     */
    public Object findForMap(String str, Object obj, String key) throws Exception {
        return sqlSessionTemplate.selectMap(str, obj, key);
    }

    public Long findAllCount(String str, Object obj) {
        return sqlSessionTemplate.selectOne(str, obj);
    }

    /**
     * 批量新增
     * 同一事务范围内，分批commit insert batch
     * @param str
     * @param objs
     * @param <T>
     * @return
     */
    public <T> boolean insertBatch(String str, List<T> objs) {
        int result = 1;
        SqlSession batchSqlSession = null;

        try {
            batchSqlSession = this.sqlSessionTemplate.getSqlSessionFactory()
                    .openSession(ExecutorType.BATCH, false);  // 获取批量方式的sqlSession
            int batchCount = Constant.BATCH_UNIT_COUNT; // 每批commit的个数
            int batchLastIndex = batchCount; // 每批最后一个的下标
            for (int index = 0; index < objs.size();) {
                if (batchLastIndex >= objs.size()) {
                    batchLastIndex = objs.size();
                    result = result * batchSqlSession.insert(str, objs.subList(index, batchLastIndex));
                    batchSqlSession.commit();
                    logger.info(" --> index: " + index + " batchLastIndex: " + batchLastIndex);
                    break;
                } else {
                    result = result * batchSqlSession.insert(str, objs.subList(index, batchLastIndex));
                    batchSqlSession.commit();
                    logger.info(" --> index: " + index + " batchLastIndex: " + batchLastIndex);
                    index = batchLastIndex;  // 设置下一批的下标
                    batchLastIndex = index + (batchCount - 1);
                }
            }
            batchSqlSession.commit();
        } finally {
            if (null != batchSqlSession) {
                // 清理缓存，防止溢出
                batchSqlSession.clearCache();
                batchSqlSession.close();
            }
        }
        return (result > 0) ? true : false;
    }
    
}
