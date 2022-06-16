package com.xiushang.validation;

import com.xiushang.validation.utils.ValidationUtils;
import io.swagger.annotations.ApiModelProperty;
import org.junit.Assert;
import org.junit.Test;

import javax.validation.constraints.IdCard;


public class ApiModelPropertyTests {

    @Test
    public void test01(){

        UserDTO userDTO = new UserDTO();
        userDTO.setAge("1");
        userDTO.setBirthDay("1");
        //userDTO.setName("name");
        userDTO.setIdCard("xxxxxx");
        // 验证错误
        ValidationUtils.ValidResult validResult = ValidationUtils.validateBean(userDTO);
        Assert.assertTrue(validResult.hasErrors());
        System.out.println(validResult.getErrors());

    }

    static class UserDTO{
        @ApiModelProperty(value="用户名",notes = "用户名称",required = true)
        private String name;

        @ApiModelProperty(value="年龄",notes = "用户名称",required = true)
        private String age;

        @ApiModelProperty(message="生日不能为空！",notes = "生日",required = true)
        private String birthDay;

        @ApiModelProperty(value = "身份证")
        @IdCard
        private String idCard;

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

        public String getBirthDay() {
            return birthDay;
        }

        public void setBirthDay(String birthDay) {
            this.birthDay = birthDay;
        }

        public String getIdCard() {
            return idCard;
        }

        public void setIdCard(String idCard) {
            this.idCard = idCard;
        }
    }

}
