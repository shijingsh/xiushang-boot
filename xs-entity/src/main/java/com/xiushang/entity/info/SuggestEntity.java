package com.xiushang.entity.info;

import com.alibaba.fastjson.annotation.JSONField;
import com.xiushang.entity.BaseEntity;
import com.xiushang.entity.shop.ShopEntity;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 意见反馈
 */
@Entity
@Table(name="app_suggest")
public class SuggestEntity extends BaseEntity {
    /**
     * 所属商铺
     */
    @ManyToOne
    @NotFound(action= NotFoundAction.IGNORE)
    @JoinColumn(name = "shop_id")
    @JSONField(serialize = false, deserialize = false)
    @ApiModelProperty(hidden = true)
    private ShopEntity belongShop;
    /**
     * 姓名
     */
    private String name ;
    /**
     * 联系方式
     */
    private String contact ;
    /**
     * 建议内容
     */
    @Lob
    @Basic(fetch = FetchType.EAGER)
    private String content;
    /**
     * email
     */
    private String email;
    /**
     * 来源
     */
    @Column(name = "from_client")
    @JSONField(serialize = false, deserialize = false)
    @ApiModelProperty(hidden = true)
    private String fromClient;

    /**
     * 相关图片
     */
    @ElementCollection
    @CollectionTable(name="images_suggest", joinColumns=@JoinColumn(name="suggest_id"))
    @Column(name="images")
    private List<String> images = new ArrayList<>();
    /**
     * 反馈状态 0 用户反馈  1 处理中  2 已处理
     */
    private Integer status = 0;

    public ShopEntity getBelongShop() {
        return belongShop;
    }

    public void setBelongShop(ShopEntity belongShop) {
        this.belongShop = belongShop;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getFromClient() {
        return fromClient;
    }

    public void setFromClient(String fromClient) {
        this.fromClient = fromClient;
    }
}
