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
public class PDFModel<T> extends BaseModel<T> {
    /** 标题 */
    private String title;
    /** 下边距 */
    private float marginBottom = 50;
    /** 左边距 */
    private float marginLeft = 50;
    /** 上边距 */
    private float marginTop = 50;
    /** 右边距 */
    private float marginRight = 50;
    /** 纸张大小 */
    private Rectangle pageSize = PageSize.A4;
    /** 每次写入文件的行数 */
    private int writeNum = 2000;
}
