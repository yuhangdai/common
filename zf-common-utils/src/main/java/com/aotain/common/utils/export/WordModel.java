package com.aotain.common.utils.export;

import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import lombok.Getter;
import lombok.Setter;

/**
 * Demo class
 *
 * @author HP
 * @date 2018/05/11
 */
@Getter
@Setter
public class WordModel<T> extends BaseModel<T> {
    /** 标题 */
    private String title;
    /** 纸张大小 */
    private Rectangle pageSize = PageSize.A4;
    /** 每次写入文件的行数 */
    private int writeNum = 1000;
}
