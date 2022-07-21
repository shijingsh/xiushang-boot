package com.xiushang.entity;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


/**
 * 系统的实体对象的基础类.
 * 这是一个抽象类，且不是被持久化的实体类，但可以被子类继承并包含对应的字段。所包括的字段为
 * 绝大多数实体类都需要用到的字段。对于不需要用到的，设定默认值即可。
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class BaseUserEntity extends BaseLazy {

    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(generator = "system-uuid-user", strategy = GenerationType.IDENTITY)
    //@GenericGenerator(name = "system-uuid-user", strategy = "uuid")
    @GenericGenerator(name = "system-uuid-user", strategy = "org.hibernate.id.UUIDGenerator" )
    @Column(length = 36)
    @ApiModelProperty(notes = "主键ID")
    protected String id;


    /**
     * 所属用户ID
     * 用于权限判断，不是自己的数据不能修改、删除
     */
    @ApiModelProperty(notes = "所属用户ID")
    @Column(name = "user_id",updatable=false, length = 36)
    protected String userId;

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

    public String getUserId() {
        if (StringUtils.isBlank(userId)) {
            return "";
        }
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

        BaseUserEntity that = (BaseUserEntity) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }


}
