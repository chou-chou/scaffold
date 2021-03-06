package com.hrp.utils;

import org.codehaus.jackson.map.util.JSONPObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * AppUtil
 *
 * @author KVLT
 * @date 2017-04-06.
 */
public class AppUtil {

    protected static Logger logger = LoggerFactory.getLogger(AppUtil.class);

    /**
     * 设置分页的参数
     * @param pd
     * @return
     */
    public static PageData setPageParam(PageData pd){
        String page_now_str = pd.get("page_now").toString();
        int pageNowInt = Integer.parseInt(page_now_str)-1;
        String page_size_str = pd.get("page_size").toString(); //每页显示记录数
        int pageSizeInt = Integer.parseInt(page_size_str);
        String page_now = pageNowInt+"";
        String page_start = (pageNowInt*pageSizeInt)+"";
        pd.put("page_now", page_now);
        pd.put("page_start", page_start);
        return pd;
    }

    /**设置list中的distance
     * @param list
     * @param pd
     * @return
     */
    public static List<PageData>  setListDistance(List<PageData> list, PageData pd){
        List<PageData> listReturn = new ArrayList<PageData>();
        String user_longitude = "";
        String user_latitude = "";
        try{
            user_longitude = pd.get("user_longitude").toString(); //"117.11811";
            user_latitude  = pd.get("user_latitude").toString();  //"36.68484";
        } catch(Exception e){
            logger.error("缺失参数--user_longitude和user_longitude");
            logger.error("lost param：user_longitude and user_longitude");
        }
        PageData pdTemp = new PageData();
        int size = list.size();
        for(int i=0;i<size;i++){
            pdTemp = list.get(i);
            String longitude = pdTemp.get("longitude").toString();
            String latitude = pdTemp.get("latitude").toString();
            String distance = MapDistance.getDistance(
                    user_longitude,	user_latitude,
                    longitude,		latitude
            );
            pdTemp.put("distance", distance);
            pdTemp.put("size", distance.length());
            listReturn.add(pdTemp);
        }
        return listReturn;
    }

    /**
     * @param pd
     * @param map
     * @return
     */
    public static Object returnObject(PageData pd, Map map){
        if(pd.containsKey("callback")){
            String callback = pd.get("callback").toString();
            return new JSONPObject(callback, map);
        }else{
            return map;
        }
    }

}
