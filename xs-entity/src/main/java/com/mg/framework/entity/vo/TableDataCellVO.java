package com.mg.framework.entity.vo;

/**
 * 格子对象
 */
public class TableDataCellVO {
    private String value;

    //是否编辑 input text
    private String cellType;

    //
    private boolean valid;

    public TableDataCellVO(String value,boolean valid)
    {
        this.value=value;
        this.valid=valid;
        this.cellType=null;
    }

    public TableDataCellVO(String value,String cellType)
    {
    	this.value=value;
    	this.cellType=cellType;
    }
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCellType() {
        return cellType;
    }

    public void setCellType(String cellType) {
        this.cellType = cellType;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}

