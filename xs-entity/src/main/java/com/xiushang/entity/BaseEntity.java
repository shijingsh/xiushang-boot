package com.xiushang.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.xiushang.framework.entity.model.EntityListener;
import com.xiushang.framework.utils.DeleteEnum;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;

import javax.persistence.*;
import java.util.Date;


/**
 * 系统的实体对象的基础类.
 * 这是一个抽象类，且不是被持久化的实体类，但可以被子类继承并包含对应的字段。所包括的字段为
 * 绝大多数实体类都需要用到的字段。对于不需要用到的，设定默认值即可。
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@EntityListeners(EntityListener.class)
public abstract class BaseEntity extends BaseLazy {

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(generator = "system-uuid", strategy = GenerationType.IDENTITY)
    //@GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GenericGenerator(name = "system-uuid", strategy = "org.hibernate.id.UUIDGenerator" )
    @Column(length = 36)
    @ApiModelProperty(notes = "主键ID")
    protected String id;


    /**
     * 创建实体对象的操作员ID
     */
    @Column(name = "created_by_id",updatable=false, length = 36)
    @JSONField(serialize = false, deserialize = false)
    @ApiModelProperty(hidden = true)
    protected String createdById;

    /**
     * 创建实体对象的时间
     */
    @ApiModelProperty(notes = "创建时间")
    @Column(name = "created_date",updatable=false)
    protected Date createdDate;

    /**
     * 最后修改实体对象的操作员ID
     */
    @Column(name = "updated_by_id", length = 36)
    @JSONField(serialize = false, deserialize = false)
    @ApiModelProperty(hidden = true)
    protected String updatedById;

    /**
     * 最后修改实体对象的时间
     */
    @Column(name = "updated_date")
    @ApiModelProperty(notes = "修改时间")
    protected Date updatedDate;

    /**
     * 是否已删除 （0 未删除  1 已删除）
     */
    @ApiModelProperty(notes = "是否已删除 （0 未删除  1 已删除）")
    private Integer deleted = DeleteEnum.VALID;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (StringUtils.isBlank(id)) {
            this.id = null;
        } else {
            this.id = id;
        }
    }

    public String getCreatedById() {
        if(createdById==null){
            return "";
        }
        return createdById;
    }

    public void setCreatedById(String createdById) {
        this.createdById = createdById;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedById() {
        if(updatedById==null){
            return "";
        }
        return updatedById;
    }

    public void setUpdatedById(String updatedById) {
        this.updatedById = updatedById;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Integer getDeleted() {
        if (deleted == null) {
            return 0;
        }
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        if(deleted==null || deleted < 0 || deleted >1 ){
            this.deleted = 0;
        }else {
            this.deleted = deleted;
        }
    }

    @Override
    public String toString() {
        return "Entity {" +
                "id='" + id + '\'' + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseEntity that = (BaseEntity) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

}
