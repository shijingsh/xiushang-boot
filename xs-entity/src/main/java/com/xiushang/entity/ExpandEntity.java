package com.xiushang.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * 扩展实体
 * Created by liukefu on 2017/4/25.
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class ExpandEntity extends BaseEntity {

    @ApiModelProperty(notes = "扩展字段")
    private String cnStr1;
    @ApiModelProperty(notes = "扩展字段")
    private String cnStr2;
    @ApiModelProperty(notes = "扩展字段")
    private String cnStr3;
    @ApiModelProperty(notes = "扩展字段")
    private String cnStr4;
    @ApiModelProperty(notes = "扩展字段")
    private String cnStr5;

    @ApiModelProperty(notes = "扩展字段")
    private Integer cnInt1;
    @ApiModelProperty(notes = "扩展字段")
    private Integer cnInt2;
    @ApiModelProperty(notes = "扩展字段")
    private Integer cnInt3;

    @ApiModelProperty(notes = "扩展字段")
    private Double cnDb1;
    @ApiModelProperty(notes = "扩展字段")
    private Double cnDb2;
    @ApiModelProperty(notes = "扩展字段")
    private Double cnDb3;
    @ApiModelProperty(notes = "扩展字段")
    private Date cnDate1;
    @ApiModelProperty(notes = "扩展字段")
    private Date cnDate2;
    @ApiModelProperty(notes = "扩展字段")
    private Date cnDate3;

    public String getCnStr1() {
        return cnStr1;
    }

    public void setCnStr1(String cnStr1) {
        this.cnStr1 = cnStr1;
    }

    public String getCnStr2() {
        return cnStr2;
    }

    public void setCnStr2(String cnStr2) {
        this.cnStr2 = cnStr2;
    }

    public String getCnStr3() {
        return cnStr3;
    }

    public void setCnStr3(String cnStr3) {
        this.cnStr3 = cnStr3;
    }

    public String getCnStr4() {
        return cnStr4;
    }

    public void setCnStr4(String cnStr4) {
        this.cnStr4 = cnStr4;
    }

    public String getCnStr5() {
        return cnStr5;
    }

    public void setCnStr5(String cnStr5) {
        this.cnStr5 = cnStr5;
    }

    public Integer getCnInt1() {
        return cnInt1;
    }

    public void setCnInt1(Integer cnInt1) {
        this.cnInt1 = cnInt1;
    }

    public Integer getCnInt2() {
        return cnInt2;
    }

    public void setCnInt2(Integer cnInt2) {
        this.cnInt2 = cnInt2;
    }

    public Integer getCnInt3() {
        return cnInt3;
    }

    public void setCnInt3(Integer cnInt3) {
        this.cnInt3 = cnInt3;
    }

    public Double getCnDb1() {
        return cnDb1;
    }

    public void setCnDb1(Double cnDb1) {
        this.cnDb1 = cnDb1;
    }

    public Double getCnDb2() {
        return cnDb2;
    }

    public void setCnDb2(Double cnDb2) {
        this.cnDb2 = cnDb2;
    }

    public Double getCnDb3() {
        return cnDb3;
    }

    public void setCnDb3(Double cnDb3) {
        this.cnDb3 = cnDb3;
    }

    public Date getCnDate1() {
        return cnDate1;
    }

    public void setCnDate1(Date cnDate1) {
        this.cnDate1 = cnDate1;
    }

    public Date getCnDate2() {
        return cnDate2;
    }

    public void setCnDate2(Date cnDate2) {
        this.cnDate2 = cnDate2;
    }

    public Date getCnDate3() {
        return cnDate3;
    }

    public void setCnDate3(Date cnDate3) {
        this.cnDate3 = cnDate3;
    }

}
