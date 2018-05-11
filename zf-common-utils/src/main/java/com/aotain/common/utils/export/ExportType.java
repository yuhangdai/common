package com.aotain.common.utils.export;

import java.util.ArrayList;
import java.util.List;

/**
 * Demo class
 *
 * @author HP
 * @date 2018/05/11
 */
public enum  ExportType {
    EXCEL(".xlsx"),WORD(".doc"),PDF(".pdf");

    private String suffix;

    private ExportType(String suffix) {
        this.suffix = suffix;
    }

    public String getSuffix() {
        return suffix;
    }

    public static List<KeyValueDTO> getExportTypes(){
        List<KeyValueDTO> dtoes = new ArrayList<KeyValueDTO>();
        for(ExportType item : ExportType.values()){
            KeyValueDTO dto = new KeyValueDTO();
            dto.setKey("" + item.ordinal());
            dto.setValue(item.name());
            dtoes.add(dto);
        }
        return dtoes;
    }

    public static List<KeyValueDTO> getExcelOfExportTypes(){
        List<KeyValueDTO> dtoes = new ArrayList<KeyValueDTO>();
        for(ExportType item : ExportType.values()){
            if(item.equals(ExportType.EXCEL)){
                KeyValueDTO dto = new KeyValueDTO();
                dto.setKey("" + item.ordinal());
                dto.setValue(item.name());
                dtoes.add(dto);
            }
        }
        return dtoes;
    }

    /**
     * 根据ordinal值获取枚举对象
     * @param ordinal
     * @return SystemActionLogType
     */
    public static ExportType valueOf(int ordinal) {
        ExportType[] exportTypes = ExportType.values();
        if (ordinal < 0 || ordinal >= exportTypes.length) {
            throw new IndexOutOfBoundsException("Invalid ordinal");
        }
        return exportTypes[ordinal];
    }
}