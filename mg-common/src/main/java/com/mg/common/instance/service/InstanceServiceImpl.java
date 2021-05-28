package com.mg.common.instance.service;

import com.mg.common.entity.QInstanceEntity;
import com.mg.framework.utils.StatusEnum;
import com.mg.common.context.MgServerContext;
import com.mg.common.entity.InstanceEntity;
import com.mg.common.instance.dao.InstanceDao;
import com.mg.framework.entity.vo.PageTableVO;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * 公司实例管理接口
 * @author liukefu
 */
@Service
public class InstanceServiceImpl implements InstanceService {
    @Autowired
    private InstanceDao instanceDao;
    @PersistenceContext
    private EntityManager entityManager;
    /**
     * 查询公司实例 根据ID
     * @param id
     * @return
     */
    public InstanceEntity findInstanceById(String id) {
        return instanceDao.findById(id).get();
    }

    /**
     * 保存公司实例
     * @param instanceEntity
     * @return
     */
    public InstanceEntity save(InstanceEntity instanceEntity) {
        MgServerContext.setInstance(instanceEntity.getId(),instanceEntity);
        return instanceDao.save(instanceEntity);
    }
    /**
     * 查询公司实例 根据name
     * @param name
     * @return
     */
    public InstanceEntity findInstanceByName(String name) {
        List<InstanceEntity> list =  instanceDao.findInstanceByName(name);

        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

    /**
     * 根据token获取公司实例
     * @param token
     * @return
     */
    public InstanceEntity findInstanceByToken(String token) {
        List<InstanceEntity> list =  instanceDao.findInstanceByToken(token);

        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }
    /**
     * 查询所有公司实例
     * @return
     */
    public List<InstanceEntity> findInstanceAll() {
        return instanceDao.findAll();
    }

    public List<InstanceEntity> findList(Integer offset,Integer limit) {
        QInstanceEntity entity = QInstanceEntity.instanceEntity;

        if(limit==null || limit <=0){
            limit = 15;
        }
        if(offset==null || offset <=0){
            offset = 0;
        }else{
            offset = (offset-1) * limit;
        }

        JPAQuery query = new JPAQuery(entityManager);
        query.from(entity)
                .where(entity.status.eq(StatusEnum.STATUS_VALID)).offset(offset).limit(limit);
        List<InstanceEntity> users = query.fetch();

        return users;
    }

    public Long findCount() {
        QInstanceEntity entity = QInstanceEntity.instanceEntity;

        JPAQuery query = new JPAQuery(entityManager);
        query.from(entity).where(entity.status.eq(StatusEnum.STATUS_VALID));
        Long totalNum = query.fetchCount();

        return totalNum;
    }

    public PageTableVO findPageList(Integer offset,Integer limit) {

        List<InstanceEntity> list = findList(offset, limit);
        Long totalCount = findCount();
        PageTableVO vo = new PageTableVO();
        vo.setRowData(list);
        vo.setTotalCount(totalCount);
        vo.setPageNo(offset);
        vo.setPageSize(limit);
        return vo;
    }
}
