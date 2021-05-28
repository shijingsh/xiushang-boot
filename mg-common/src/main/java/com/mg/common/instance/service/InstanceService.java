package com.mg.common.instance.service;

import com.mg.common.entity.InstanceEntity;
import com.mg.framework.entity.vo.PageTableVO;

import java.util.List;

/**
 * 实例管理服务接口
 */
public interface InstanceService {
    /**
     * 查询公司实例 根据ID
     * @param id
     * @return
     */
    InstanceEntity findInstanceById(String id);
    /**
     * 保存公司实例
     * @param instanceEntity
     * @return
     */
    public InstanceEntity save(InstanceEntity instanceEntity);
    /**
     * 查询公司实例 根据name
     * @param name
     * @return
     */
    InstanceEntity findInstanceByName(String name);
    /**
     * 根据token获取公司实例
     * @param token
     * @return
     */
    public InstanceEntity findInstanceByToken(String token);
    /**
     * 查询所有公司实例
     * @return
     */
    List<InstanceEntity> findInstanceAll();
    /**
     * 分页查询所有公司实例
     * @return
     */
    public PageTableVO findPageList(Integer offset,Integer limit);
}
