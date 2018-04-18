package com.aotain.mytest.usefultool.bean;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;

/**
 * @Description: 两个对象间，相同属性名之间进行转换
 *
 * @ClassName: EntityObjectConverter
 * @Copyright: Copyright (c) 2013
 *
 * @author Comsys-LZP
 * @date 2013-11-4 上午09:55:14
 * @version V2.0
 */
public class EntityObjectConverter {
    /*
     * 实例化对象
     */
    private static DozerBeanMapper map = new DozerBeanMapper();

    /**
     * @Description: 将目标对象转换为指定对象，相同属性名进行属性值复制
     *
     * @Title: EntityObjectConverter.java
     * @Copyright: Copyright (c) 2013
     *
     * @author Comsys-LZP
     * @date 2013-11-4 下午02:32:34
     * @version V2.0
     */
    @SuppressWarnings("unchecked")
    public static <T> T getObject(Object source,Class<T> cls){
        if (source==null) {
            return null;
        }
        return (T) map.map(source, cls);
    }

    /**
     * @Description: 两个对象之间相同属性名的属性值复制
     *
     * @Title: EntityObjectConverter.java
     * @Copyright: Copyright (c) 2013
     *
     * @author Comsys-LZP
     * @date 2013-11-4 下午02:33:56
     * @version V2.0
     */
    public static void setObject(Object source,Object target){
        map.map(source, target);
    }

    /**
     * @Description: 对象集合中对象相同属性名的属性值复制
     *
     * @Title: EntityObjectConverter.java
     *
     * @Copyright: Copyright (c) 2013
     * @author Comsys-LZP
     * @date 2013-11-4 下午02:34:26
     * @version V2.0
     */
    @SuppressWarnings("unchecked")
    public static List getList(List source,Class cls){
        List listTarget = new ArrayList();
        if(source != null){
            for (Object object : source) {
                Object objTarget = EntityObjectConverter.getObject(object, cls);
                listTarget.add(objTarget);
            }
        }
        return listTarget;
    }
}
