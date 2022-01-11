package com.xiushang.validation;

import javax.validation.constraints.PaperNo;

import com.xiushang.validation.utils.ValidationUtils;
import org.junit.Assert;
import org.junit.Test;

public class PaperNoTests {
    @Test
    public void test01(){
        PaperNoDTO dto = new PaperNoDTO();

        dto.setIdNo("36042119940706281X");
        ValidationUtils.ValidResult validResult = ValidationUtils.validateBean(dto);
        Assert.assertTrue(validResult.hasErrors());
        System.out.println(validResult.getErrors());

        dto.setIdNo("360421199407062817");
        validResult = ValidationUtils.validateBean(dto);
        Assert.assertFalse(validResult.hasErrors());

    }

    static class PaperNoDTO{

        @PaperNo
        private String idNo;

        public void setIdNo(String idNo) {
            this.idNo = idNo;
        }

        public String getIdNo() {
            return idNo;
        }
    }
}
