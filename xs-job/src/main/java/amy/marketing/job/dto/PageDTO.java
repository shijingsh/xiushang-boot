package amy.marketing.job.dto;

import lombok.Data;

/**
 * 考虑改成动态赋值
 * 所有请求智子云列表接口都要继承该类
 * @author ZKUI created by 2021-06-01 2:57 下午
 */
@Data
public class PageDTO {
  private Integer page = 1;
  private Integer rows = 10;
}
