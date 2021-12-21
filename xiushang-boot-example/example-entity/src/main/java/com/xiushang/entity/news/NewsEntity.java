package com.xiushang.entity.news;


import com.xiushang.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;

/**
 * 公告管理
 */
@Entity
@Table(name="app_news")
public class NewsEntity extends BaseEntity {
    /**
     * 循环依赖测试
     */
    @ManyToOne
    @JoinColumn(name = "parent_news_id")
    private NewsEntity belongNews;
    /**
     * 所属店铺
     */
    private String  userId;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    @ApiModelProperty(notes = "公告内容",required = true)
    private String content;

    /**
     * 有效期
     */
    @ApiModelProperty(notes = "有效期",required = true)
    private Date validDate;

    private Integer status = 1;

    public NewsEntity getBelongNews() {
        return belongNews;
    }

    public void setBelongNews(NewsEntity belongNews) {
        this.belongNews = belongNews;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
