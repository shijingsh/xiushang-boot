package com.xiushang.common.notice.vo;

import com.xiushang.framework.entity.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class NoticeVo extends BaseVO {

    /**
     * 标题
     */
    @ApiModelProperty(value = "公告标题",required = true)
    private String title;

    @ApiModelProperty(value = "公告内容",required = true)
    private String content;

    /**
     * 有效期
     */
    @ApiModelProperty(value = "有效期",required = true)
    private Date validDate;
}
