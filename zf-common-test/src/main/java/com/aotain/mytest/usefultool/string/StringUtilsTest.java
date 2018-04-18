package com.aotain.mytest.usefultool.string;

import cn.hutool.core.lang.Assert;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;


/**
 * Demo class
 *
 * @author HP
 * @date 2018/04/18
 */
public class StringUtilsTest {

    @Test
    public void testIsEmpty(){
        // 检查字符串是否为空或null
        System.out.println(StringUtils.isEmpty(null));
        System.out.println(StringUtils.isEmpty(""));
    }

    @Test
    public void testStrip(){
        // 去掉字符串前后空格
        System.out.println(StringUtils.strip("     abcd      "));
        Assert.isTrue("abcde".equals(StringUtils.strip("     abcd      ")));
    }

    @Test
    public void test(){
        System.out.println(StringUtils.compareIgnoreCase("ABdc","abDc"));
        System.out.println(StringUtils.equalsIgnoreCase("ABdc","abDc"));
        Assert.isTrue(StringUtils.equalsIgnoreCase("ABdc","abDc"));
    }
}
