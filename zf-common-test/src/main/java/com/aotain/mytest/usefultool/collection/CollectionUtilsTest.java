package com.aotain.mytest.usefultool.collection;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 集合工具类CollectionUtils使用示例
 *
 * @author HP
 * @date 2018/04/18
 */
public class CollectionUtilsTest {

    @Test
    public void testUnion(){
        // 集合并集
        String[] strArray1 = new String[]{"A","B","C","D","E"};
        String[] strArray2 = new String[]{"D","E","F","G","H"};
        List<String> list1 = Arrays.asList(strArray1);
        List<String> list2 = Arrays.asList(strArray2);
        System.out.println(ArrayUtils.toString(CollectionUtils.union(list1,list2)));
    }

    @Test
    public void testIntersection(){
        // 集合交集
        String[] strArray1 = new String[]{"A","B","C","D","E"};
        String[] strArray2 = new String[]{"D","E","F","G","H"};
        List<String> list1 = Arrays.asList(strArray1);
        List<String> list2 = Arrays.asList(strArray2);
        System.out.println(ArrayUtils.toString(CollectionUtils.intersection(list1,list2)));
    }

    @Test
    public void testSubtract(){
        // 集合差集
        String[] strArray1 = new String[]{"A","B","C","D","E"};
        String[] strArray2 = new String[]{"D","E","F","G","H"};
        List<String> list1 = Arrays.asList(strArray1);
        List<String> list2 = Arrays.asList(strArray2);
        System.out.println(ArrayUtils.toString(CollectionUtils.subtract(list1,list2)));
    }

    @Test
    public void testDisjunction(){
        // 交集的补集（析取）
        String[] strArray1 = new String[]{"A","B","C","D","E"};
        String[] strArray2 = new String[]{"D","E","F","G","H"};
        List<String> list1 = Arrays.asList(strArray1);
        List<String> list2 = Arrays.asList(strArray2);
        System.out.println(ArrayUtils.toString(CollectionUtils.disjunction(list1,list2)));
    }

    @Test
    public void testIsEmpty(){
        // 测试集合是否为空
        String[] strArray1 = new String[]{"A","B","C","D","E"};
        String[] strArray2 = new String[]{"D","E","F","G","H"};
        List<String> list1 = Arrays.asList(strArray1);
        List<String> list2 = Arrays.asList(strArray2);
        System.out.println(CollectionUtils.isEmpty(list1));
        System.out.println(CollectionUtils.isEmpty(list2));
        System.out.println(CollectionUtils.isEmpty(Collections.EMPTY_LIST));
        System.out.println(CollectionUtils.isEmpty(null));
    }

    @Test
    public void testIsEqualCollection(){
        // 判断集合是否相等
        String[] strArray1 = new String[]{"A","B","C","D","E"};
        String[] strArray2 = new String[]{"D","E","F","G","H"};
        List<String> list1 = Arrays.asList(strArray1);
        List<String> list2 = Arrays.asList(strArray2);
        System.out.println(CollectionUtils.isEqualCollection(list1,list2));
    }

    @Test
    public void test(){
        String[] strArray1 = new String[]{"E","B","C","D","A"};
        List<String> list1 = Arrays.asList(strArray1);
        System.out.println(CollectionUtils.permutations(list1).size());
    }

}
