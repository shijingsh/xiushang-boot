package com.xiushang.entity.info;

import com.alibaba.fastjson.annotation.JSONField;
import com.xiushang.entity.BaseEntity;
import com.xiushang.entity.UserEntity;
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
@Table(name="t_suggest")
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
    @ApiModelProperty(value = "姓名")
    private String name ;
    /**
     * 联系方式
     */
    @ApiModelProperty(value = "联系手机",required = true)
    private String mobile ;

    /**
     * email
     */
    @ApiModelProperty(value = "联系邮箱")
    private String email;
    /**
     * 反馈内容
     */
    @Lob
    @Basic(fetch = FetchType.EAGER)
    @ApiModelProperty(value = "反馈内容")
    private String content;

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
    @CollectionTable(name="rel_images_suggest", joinColumns=@JoinColumn(name="suggest_id"))
    @Column(name="images")
    @ApiModelProperty(value = "相关图片")
    private List<String> images = new ArrayList<>();
    /**
     * 反馈状态 0 用户反馈  1 处理中  2 已处理
     */
    @ApiModelProperty(value = "反馈状态 0 用户反馈  1 处理中  2 已处理")
    private Integer status = 0;

    /**
     * 反馈类型
     * 0 用户主动反馈
     * 1 用户被动相应 （客户要求给他来电）
     *
     */
    @ApiModelProperty(value = "反馈类型")
    private Integer type = 0;


    @ManyToOne
    @JoinColumn(name = "user_id")
    @ApiModelProperty(value = "处理人")
    private UserEntity handlerUser;
    /**
     * 反馈内容
     */
    @Lob
    @Basic(fetch = FetchType.EAGER)
    @ApiModelProperty(value = "处理备注")
    private String handlerContent;

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public UserEntity getHandlerUser() {
        return handlerUser;
    }

    public void setHandlerUser(UserEntity handlerUser) {
        this.handlerUser = handlerUser;
    }

    public String getHandlerContent() {
        return handlerContent;
    }

    public void setHandlerContent(String handlerContent) {
        this.handlerContent = handlerContent;
    }
}
