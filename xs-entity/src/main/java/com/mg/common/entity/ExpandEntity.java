package com.mg.common.entity;

import com.mg.framework.entity.model.BaseEntity;

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

    private String cnStr1;
    private String cnStr2;
    private String cnStr3;
    private String cnStr4;
    private String cnStr5;
    private String cnStr6;
    private String cnStr7;
    private String cnStr8;
    private String cnStr9;

    private int cnInt1;
    private int cnInt2;
    private int cnInt3;
    private int cnInt4;
    private int cnInt5;

    private Double cnDb1;
    private Double cnDb2;
    private Double cnDb3;
    private Double cnDb4;
    private Double cnDb5;

    private Date cnDate1;
    private Date cnDate2;
    private Date cnDate3;
    private Date cnDate4;
    private Date cnDate5;

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

    public String getCnStr6() {
        return cnStr6;
    }

    public void setCnStr6(String cnStr6) {
        this.cnStr6 = cnStr6;
    }

    public String getCnStr7() {
        return cnStr7;
    }

    public void setCnStr7(String cnStr7) {
        this.cnStr7 = cnStr7;
    }

    public String getCnStr8() {
        return cnStr8;
    }

    public void setCnStr8(String cnStr8) {
        this.cnStr8 = cnStr8;
    }

    public String getCnStr9() {
        return cnStr9;
    }

    public void setCnStr9(String cnStr9) {
        this.cnStr9 = cnStr9;
    }

    public int getCnInt1() {
        return cnInt1;
    }

    public void setCnInt1(int cnInt1) {
        this.cnInt1 = cnInt1;
    }

    public int getCnInt2() {
        return cnInt2;
    }

    public void setCnInt2(int cnInt2) {
        this.cnInt2 = cnInt2;
    }

    public int getCnInt3() {
        return cnInt3;
    }

    public void setCnInt3(int cnInt3) {
        this.cnInt3 = cnInt3;
    }

    public int getCnInt4() {
        return cnInt4;
    }

    public void setCnInt4(int cnInt4) {
        this.cnInt4 = cnInt4;
    }

    public int getCnInt5() {
        return cnInt5;
    }

    public void setCnInt5(int cnInt5) {
        this.cnInt5 = cnInt5;
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

    public Double getCnDb4() {
        return cnDb4;
    }

    public void setCnDb4(Double cnDb4) {
        this.cnDb4 = cnDb4;
    }

    public Double getCnDb5() {
        return cnDb5;
    }

    public void setCnDb5(Double cnDb5) {
        this.cnDb5 = cnDb5;
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

    public Date getCnDate4() {
        return cnDate4;
    }

    public void setCnDate4(Date cnDate4) {
        this.cnDate4 = cnDate4;
    }

    public Date getCnDate5() {
        return cnDate5;
    }

    public void setCnDate5(Date cnDate5) {
        this.cnDate5 = cnDate5;
    }
}
