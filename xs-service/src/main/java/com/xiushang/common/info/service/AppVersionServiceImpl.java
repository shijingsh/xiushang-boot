package com.xiushang.common.info.service;

import com.xiushang.common.info.dao.AppVersionDao;
import com.xiushang.common.utils.BaseServiceImpl;
import com.xiushang.entity.info.AppVersionEntity;
import com.xiushang.framework.entity.vo.PageTableVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AppVersionServiceImpl extends BaseServiceImpl<AppVersionEntity> implements AppVersionService {
    @Autowired
    private AppVersionDao appVersionDao;
    @Transactional()
    public PageTableVO findPageList(PageTableVO pageTableVO) {

        return null;
    }

    public AppVersionEntity findByShopIdAndClientId(String shopId, String clientId){

        List<AppVersionEntity> list=  appVersionDao.findByShopIdAndClientId(shopId,clientId);
        if(list!=null && list.size()>0){
            return list.get(0);
        }

        return null;
    }

}
