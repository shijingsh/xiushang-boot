package com.xiushang.validation;

import com.xiushang.validation.utils.ValidationUtils;
import io.swagger.annotations.ApiModelProperty;
import org.junit.Assert;
import org.junit.Test;

public class ApiModelPropertyTests {

    @Test
    public void test01(){

        UserDTO userDTO = new UserDTO();
        // 验证错误
        ValidationUtils.ValidResult validResult = ValidationUtils.validateBean(userDTO);
        Assert.assertTrue(validResult.hasErrors());
        System.out.println(validResult.getErrors());

    }

    static class UserDTO{
        @ApiModelProperty(notes = "用户名称",required = true)
        private String name;

        @ApiModelProperty(notes = "用户名称",required = true)
        private String age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }
    }

}
