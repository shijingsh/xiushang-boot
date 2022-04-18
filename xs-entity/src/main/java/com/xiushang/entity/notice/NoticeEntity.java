package com.xiushang.entity.notice;

import com.alibaba.fastjson.annotation.JSONField;
import com.xiushang.entity.BaseEntity;
import com.xiushang.entity.shop.ShopEntity;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Date;

/**
 * 公告管理
 */
@Entity
@Table(name="t_notice")
public class NoticeEntity extends BaseEntity {
    /**
     * 所属商铺
     */
    @ManyToOne
    @JoinColumn(name = "shop_id")
    @JSONField(serialize = false, deserialize = false)
    @ApiModelProperty(hidden = true)
    private ShopEntity belongShop;

    /**
     * 标题
     */
    @ApiModelProperty(notes = "公告标题")
    private String title;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    @ApiModelProperty(notes = "公告內容")
    private String content;

    /**
     * 有效期
     */
    @ApiModelProperty(notes = "有效期")
    private Date validDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ShopEntity getBelongShop() {
        return belongShop;
    }

    public void setBelongShop(ShopEntity belongShop) {
        this.belongShop = belongShop;
    }

    public Date getValidDate() {
        return validDate;
    }

    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }

}
