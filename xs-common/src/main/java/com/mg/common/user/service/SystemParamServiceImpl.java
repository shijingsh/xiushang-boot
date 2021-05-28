package com.mg.common.user.service;

import com.mg.common.entity.SystemParamEntity;
import com.mg.common.user.dao.SystemParamDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class SystemParamServiceImpl implements SystemParamService {

    @Autowired
    private SystemParamDao systemParamDao;

    @Transactional(readOnly = true)
    public SystemParamEntity findByName(String shopId, String paramName) {
        List<SystemParamEntity> list = systemParamDao.findByShopIdAndParamName(shopId,paramName);
        if(list!=null && list.size()>0){
            return list.get(0);
        }

        return null;
    }

    public SystemParamEntity updateParam(SystemParamEntity paramEntity) {
       return systemParamDao.save(paramEntity);
    }
}
