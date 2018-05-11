package com.aotain.common.utils.export;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Demo class
 *
 * @author HP
 * @date 2018/05/11
 */
public interface ExportService<T> {
    String export(BaseModel<T> baseModel, HttpServletResponse response, HttpServletRequest request);
}
