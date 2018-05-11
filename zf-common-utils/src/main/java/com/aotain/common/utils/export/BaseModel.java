package com.aotain.common.utils.export;

import lombok.Getter;
import lombok.Setter;

import java.io.OutputStream;
import java.util.List;

/**
 * 导出公共类
 *
 * @author HP
 * @date 2018/05/11
 */
@Getter
@Setter
public class BaseModel<T> {

    /** 报表类型 */
    private int chartsType;
    /** 导出类型 0-Excel 1-word 2-pdf @see ExportType */
    private int exportType;
    /** 文件名称 */
    private String fileName;
    /** 表头名称 */
    private String[] headers;
    /** 字段名称 */
    private String[] fields;
    /** 列宽 */
    private float[] colWidths;
    /** 数据集 */
    private List<T> datas;
    /** 文件输出流x */
    private OutputStream os;
    /** 报表Base64图片dataURL */
    private List<String> dataURLs;

}
