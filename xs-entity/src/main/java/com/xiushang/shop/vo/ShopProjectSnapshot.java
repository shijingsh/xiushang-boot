package com.xiushang.shop.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ShopProjectSnapshot implements java.io.Serializable {

    private String id;
    /**
     * 项目
     */
    private String name;
    /**
     * 内容
     */
    private String content;
    /**
     * 存放富文本介绍
     */
    private String h5Content;

    /**
     * 小店介绍页面 和h5Content相同
     */
    private String h5Url;
    /**
     * 价格
     */
    private BigDecimal price;

    private Integer integralPrice = -1;

    private List<String> images = new ArrayList();

    /**
     * 总销量
     */
    private Integer totalSales = 0;

    private Integer projectType = 0;
    /**
     * 是否是店长推荐
     */
    private Boolean recommend = Boolean.FALSE;

    /**
     * 默认图片
     */
    private String defaultPic;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getH5Content() {
        return h5Content;
    }

    public void setH5Content(String h5Content) {
        this.h5Content = h5Content;
    }

    public Integer getIntegralPrice() {
        return integralPrice;
    }

    public void setIntegralPrice(Integer integralPrice) {
        this.integralPrice = integralPrice;
    }

    public Integer getProjectType() {
        return projectType;
    }

    public void setProjectType(Integer projectType) {
        this.projectType = projectType;
    }

    public String getH5Url() {
        return h5Url;
    }

    public void setH5Url(String h5Url) {
        this.h5Url = h5Url;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public Integer getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(Integer totalSales) {
        this.totalSales = totalSales;
    }

    public Boolean getRecommend() {
        return recommend;
    }

    public void setRecommend(Boolean recommend) {
        recommend = recommend;
    }

    public String getDefaultPic() {
        return defaultPic;
    }

    public void setDefaultPic(String defaultPic) {
        this.defaultPic = defaultPic;
    }
}
