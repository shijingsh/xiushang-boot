package com.xiushang.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.xiushang.framework.entity.model.EntityListener;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public abstract class BaseEntity implements java.io.Serializable{

	private static final long serialVersionUID = -4932645577838935714L;

	@Transient
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(generator = "system-uuid", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(length = 32)
    protected String id;


    /**
     * 创建实体对象的操作员ID
     */
    @Column(name = "created_by_id",length = 32)
    @JSONField(serialize = false, deserialize = false)
    protected String createdById;

    /**
     * 创建实体对象的时间
     */
    //@JSONField(serialize = false, deserialize = false)
    @Column(name = "created_date")
    protected Date createdDate;

    /**
     * 最后修改实体对象的操作员ID
     */
    @Column(name = "updated_by_id",length = 32)
    @JSONField(serialize = false, deserialize = false)
    protected String updatedById;

    /**
     * 最后修改实体对象的时间
     */
    @JSONField(serialize = false, deserialize = false)
    @Column(name = "updated_date")
    protected Date updatedDate;

    /**
     * 是否已删除
     */
    @Column(name = "is_deleted")
    @JSONField(serialize = false, deserialize = false)
    private boolean isDeleted = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (StringUtils.isBlank(id)) {
            logger.debug("{}'s id is \"{}\" and now change it to null.", this.getClass().getName(), id);
            this.id = null;
        }
        else {
            this.id = id;
        }
    }


    public String getCreatedById() {
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

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        return "Entity {" +
                "id='" + id + '\'' + "}" ;
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

    protected boolean isLazy(Object value) {
        if (value instanceof HibernateProxy) {//hibernate代理对象
            LazyInitializer initializer = ((HibernateProxy) value).getHibernateLazyInitializer();
            if (initializer.isUninitialized()) {
                return true;
            }
        } else if (value instanceof PersistentCollection) {//实体关联集合一对多等
            PersistentCollection collection = (PersistentCollection) value;
            if (!collection.wasInitialized()) {
                return true;
            }
            Object val = collection.getValue();
            if (val == null) {
                return true;
            }
        }
        return false;
    }

}
