package com.aotain.common.pager;

import com.aotain.common.config.BaseTest;
import com.aotain.common.config.dao.SystemConfigDictMapper;
import com.aotain.common.config.model.SystemConfigDict;
import com.aotain.common.config.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Demo class
 *
 * @author HP
 * @date 2018/03/30
 */
public class PageInterceptorTest extends BaseTest{

    @Autowired
    private SystemConfigDictMapper systemConfigDictMapper;

    @Test
    public void testPage(){
        Page<SystemConfigDict> page = new Page<SystemConfigDict>();
        page.setPage(1);
        page.setPageSize(10);
        systemConfigDictMapper.selectPageConfig(page);
        System.out.println(page.getTotalRecord());
    }

    @Test
    public void testPageHelper(){
        PageHelper.startPage(1,10);
        List<SystemConfigDict> result = systemConfigDictMapper.selectConfig();
        PageInfo pageInfo = new PageInfo(result);
        System.out.println(pageInfo);
    }
}
