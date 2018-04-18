package com.aotain.mytest.usefultool.bean;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.junit.jupiter.api.Test;


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * 对象属性赋值工具类 BeanUtils(101ms) BeanCopier(55ms) 都是实现的浅赋值，
 * 对于list等非基本数据只是赋值了一个引用,BeanCopies 效率更高
 * dozer实现深复制 但是耗时相对更多
 *      PropertyUtils
 *
 * @author HP
 * @date 2018/04/18
 */
public class BeanUtilsTest {

    @Test
    public void testCopyProperties() throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Person person = new Person();
        person.setId(111L);
        person.setName("bang");
        List<Integer> integerList = new ArrayList<Integer>();
        integerList.add(1);
        integerList.add(2);
        person.setIntegerList(integerList);
        Boy boy = new Boy();
        long start = System.currentTimeMillis();
//        BeanUtils.copyProperties(person,boy);
//        PropertyUtils.copyProperties(person,boy);
//        BeanCopier copier = BeanCopier.create(Person.class,Boy.class,false);
//        copier.copy(person,boy,null);
        EntityObjectConverter.setObject(person,boy);
        boy.getIntegerList().add(3);
        long end = System.currentTimeMillis();
        System.out.println("it takes "+(end-start)+" ms");
        System.out.println(boy.getIntegerList()==person.getIntegerList());
        System.out.println(boy+"====="+person);
    }
}
