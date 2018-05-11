package com.aotain.common.utils.export;

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
public class ExcelModel<T> extends BaseModel<T> {
    private int rowAccessWindowSize = 1000000;
}
