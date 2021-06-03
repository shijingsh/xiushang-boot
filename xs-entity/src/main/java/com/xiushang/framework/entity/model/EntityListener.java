
package com.xiushang.framework.entity.model;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

/**
 * Listener - 创建日期、修改日期处理
 *
 * @author liukefu
 * @version 1.0
 */
public class EntityListener {

	/**
	 * 保存前处理
	 *
	 * @param entity
	 *            基类
	 */
	@PrePersist
	public void prePersist(BaseEntity entity) {
		entity.setCreatedDate(new Date());
		entity.setUpdatedDate(new Date());
		/*if(UserHolder.getLoginUser()!=null){
			entity.setCreatedById(UserHolder.getLoginUserId());
			entity.setUpdatedById(UserHolder.getLoginUserId());
		}*/
	}

	/**
	 * 更新前处理
	 *
	 * @param entity
	 *            基类
	 */
	@PreUpdate
	public void preUpdate(BaseEntity entity) {
		entity.setUpdatedDate(new Date());
		/*if(UserHolder.getLoginUser()!=null){
			entity.setUpdatedById(UserHolder.getLoginUserId());
		}*/
	}

}
