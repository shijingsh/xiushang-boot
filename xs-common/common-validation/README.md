### spring validation  入参校验扩展

扩展swagger注解，根据required增加必填校验

- 使用ApiModelProperty的required校验必填
- 使用ApiParam的required校验必填

### 实例
- @ApiModelProperty 使用message 属性，指定错误提示文本
  如：@ApiModelProperty(message = "年龄不能为空！",required = true)

- 未指定message时，默认使用 ApiModelProperty注解中“value” + 不能为空！
  
```java
@Data
public class ParamVo implements java.io.Serializable {

    @ApiModelProperty(value = "名称name",notes = "名称notes",position = 1,required = true)
    private String name;

    @ApiModelProperty(value = "年龄",message = "年龄不能为空！",position = 1,required = true)
    private Integer age;

    @ApiModelProperty(value = "身份证",position = 1)
    @PaperNo
    private String idCard;
}
```

```java
@Api(tags = {"常用接口"})
@Validated
@Controller
@RequestMapping(value = "/api/valid",
        produces = "application/json; charset=UTF-8")
public class ValidateController {

	@ApiOperation(value = "ApiModelProperty必填校验")
    @PostMapping(value = "/apiModelProperty")
	@ResponseBody
    public CommonResult valApiModelProperty(@Valid @RequestBody ParamVo paramVo) {

        return CommonResult.success();
    }

    @ApiOperation(value = "ApiParam必填校验")
    @GetMapping(value = "/apiParam")
    @ResponseBody
    public CommonResult valApiParam(@ApiParam(value = "id主键",required = true) String id) {

        return CommonResult.success();
    }
}
```

     
### 实际例子

	@size (min=3, max=20, message="用户名长度只能在3-20之间")
	@size (min=6, max=20, message="密码长度只能在6-20之间")
	@pattern (regexp="[a-za-z0-9._%+-]+@[a-za-z0-9.-]+\\.[a-za-z]{2,4}", message="邮件格式错误")
	@Length(min = 5, max = 20, message = "用户名长度必须位于5到20之间")  
	@Email(message = "比如输入正确的邮箱")  
	@NotNull(message = "用户名称不能为空")
	@Max(value = 100, message = "年龄不能大于100岁")
	@Min(value= 18 ,message= "必须年满18岁！" )  
	@AssertTrue(message = "bln4 must is true")
	@AssertFalse(message = "blnf must is falase")
	@DecimalMax(value="100",message="decim最大值是100")
	@DecimalMin(value="100",message="decim最小值是100")
	@NotNull(message = "身份证不能为空")
	@Pattern(regexp="^(\\d{18,18}|\\d{15,15}|(\\d{17,17}[x|X]))$", message="身份证格式错误")

### 常见问题   
  
  配置failFast(true) 不起作用？
  
  WebMvcConfigurer 增加 getValidator 配置
  
  ```

  @Configuration
  public class WebMvcConfigurer extends WebMvcConfigurationSupport {
  
      ...
  
      @Override
      protected org.springframework.validation.Validator getValidator() {
          return ValidationConfiguration.getValidator();
      }
  }
  
```
