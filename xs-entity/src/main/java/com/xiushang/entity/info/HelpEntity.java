package com.xiushang.entity.info;

import com.alibaba.fastjson.annotation.JSONField;
import com.xiushang.entity.BaseEntity;
import com.xiushang.entity.shop.ShopEntity;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

/**
 * 常见问题
 */
@Entity
@Table(name="t_help")
public class HelpEntity extends BaseEntity {

    /**
     * 所属商铺
     */
    @ManyToOne
    @NotFound(action= NotFoundAction.IGNORE)
    @JoinColumn(name = "shop_id")
    @JSONField(serialize = false, deserialize = false)
    @ApiModelProperty(hidden = true)
    private ShopEntity belongShop;

    @ApiModelProperty(value = "标题")
    private String title;

    @Lob
    @Basic(fetch = FetchType.EAGER)
    @ApiModelProperty(value = "内容")
    private String content;

    @ApiModelProperty(value = "排序值")
    private Integer displayOrder = 999;

    public ShopEntity getBelongShop() {
        return belongShop;
    }

    public void setBelongShop(ShopEntity belongShop) {
        this.belongShop = belongShop;
    }

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

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

}
