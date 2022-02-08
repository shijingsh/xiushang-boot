package com.xiushang.common.user.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.xiushang.common.user.vo.SystemParamSaveVo;
import com.xiushang.common.utils.BaseServiceImpl;
import com.xiushang.common.utils.LazyLoadUtil;
import com.xiushang.entity.QSystemParamEntity;
import com.xiushang.entity.SystemParamEntity;
import com.xiushang.framework.entity.vo.PageTableVO;
import com.xiushang.framework.entity.vo.SearchPageVo;
import com.xiushang.framework.utils.DeleteEnum;
import com.xiushang.jpa.repository.SystemParamDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class SystemParamServiceImpl extends BaseServiceImpl<SystemParamEntity> implements SystemParamService {

    @Autowired
    private SystemParamDao systemParamDao;
    @Autowired
    private UserService userService;

    @Transactional(readOnly=true)
    public PageTableVO findPageList(SearchPageVo param) {

        String shopId = userService.getCurrentTenantShopId();
        QSystemParamEntity entity = QSystemParamEntity.systemParamEntity;
        BooleanExpression ex = entity.deleted.eq(DeleteEnum.VALID);
        if(StringUtils.isNotBlank(shopId)){
            ex = ex.and(entity.shopId.eq(shopId));
        }
        if(StringUtils.isNotBlank(param.getSearchKey())){
            ex = ex.and(entity.paramName.like("%" + param.getSearchKey() + "%"));
        }
        Page<SystemParamEntity> page = findPageList(ex,param.createPageRequest());
        LazyLoadUtil.fullLoad(page);
        PageTableVO vo = new PageTableVO(page,param);

        return vo;
    }

    @Transactional
    public SystemParamEntity getOrSaveParam(String paramName) {

        return getOrSaveParam(paramName,"");
    }

    @Transactional
    public SystemParamEntity getOrSaveParam(String paramName, String defaultValue) {
        return getOrSaveParam(paramName,defaultValue,"");
    }


    @Transactional
    public SystemParamEntity getOrSaveParam(String paramName, String defaultValue,String remark) {
        String shopId = userService.getCurrentUserShopId();
        List<SystemParamEntity> list = systemParamDao.findByShopIdAndParamName(shopId,paramName);
        if(list!=null && list.size()>0){
            return list.get(0);
        }else {
            SystemParamEntity paramEntity = new SystemParamEntity();
            paramEntity.setShopId(shopId);
            paramEntity.setParamName(paramName);
            paramEntity.setParamValue(defaultValue);
            paramEntity.setRemark(remark);
            return paramEntity;
        }
    }

    @Transactional
    public SystemParamEntity saveParam(SystemParamSaveVo paramVo) {
        String shopId = userService.getCurrentUserShopId();
        List<SystemParamEntity> list = systemParamDao.findByShopIdAndParamName(shopId,paramVo.getParamName());
        SystemParamEntity paramEntity = null;
        if(list!=null && list.size()>0){
            paramEntity = list.get(0);
        }

        if(paramEntity==null){
            paramEntity = new SystemParamEntity();
            paramEntity.setShopId(shopId);
            paramEntity.setParamName(paramVo.getParamName());
            paramEntity.setParamValue(paramVo.getParamValue());
            paramEntity.setRemark(paramVo.getRemark());
        }else {
            paramEntity.setParamValue(paramVo.getParamValue());
            paramEntity.setRemark(paramVo.getRemark());
        }
        return systemParamDao.save(paramEntity);
    }
}
