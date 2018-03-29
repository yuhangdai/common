package com.aotain.common.config.pagehelper;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页数据返回结果
 *
 * @author daiyh@aotain.com
 * @date 2018/02/28
 */
@Getter
@Setter
public class PageResult {

    /**
     * 返回的数据内容
     */
    private List rows = new ArrayList();

    /**
     * 记录总条数
     */
    private Integer total;
}
