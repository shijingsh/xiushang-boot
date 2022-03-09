package com.xiushang.common.info.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.xiushang.common.info.dao.AddressDao;
import com.xiushang.common.info.mapper.AddressSaveMapper;
import com.xiushang.common.info.vo.AddressVo;
import com.xiushang.common.utils.BaseServiceImpl;
import com.xiushang.common.utils.GeocodingUtils;
import com.xiushang.common.utils.GpsLocation;
import com.xiushang.entity.UserEntity;
import com.xiushang.entity.info.AddressEntity;
import com.xiushang.entity.info.QAddressEntity;
import com.xiushang.framework.utils.DeleteEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 用户地址
 * Created by liukefu on 2017/1/18.
 */
@Service
public class AddressServiceImpl extends BaseServiceImpl<AddressEntity> implements AddressService {
    @Autowired
    private AddressDao addressDao;

    @Transactional(readOnly=true)
    public List<AddressEntity> findList() {

        UserEntity user = userService.getCurrentUser();
        if(user==null){
            return null;
        }
        QAddressEntity entity = QAddressEntity.addressEntity;
        BooleanExpression ex = entity.user.id.eq(user.getId()).and(entity.deleted.eq(DeleteEnum.VALID));

        Iterable<AddressEntity> iterable =  addressDao.findAll(ex);
        Iterator<AddressEntity> it = iterable.iterator();

        List<AddressEntity> list = new ArrayList<>();
        while (it.hasNext()){
            AddressEntity addressEntity = it.next();
            list.add(addressEntity);
        }
        return list;
    }

    public AddressEntity saveAddress(AddressVo addressVo) {

        UserEntity userEntity = userService.getCurrentUser();
        AddressEntity entity = AddressSaveMapper.INSTANCE.targetToSource(addressVo);
        if(userEntity!=null){
            List<AddressEntity> list = findDefaultAddress();
            if(entity.getUserDefault()){
                for (AddressEntity addressEntity:list){
                    addressEntity.setUserDefault(false);
                    save(addressEntity);
                }
            }
            if(list.size()==0){
                //只有一个的时候，就默认
                entity.setUserDefault(true);
            }
            entity.setUser(userEntity);
            entity.setFullAddress(entity.createFullAddress());

            if(entity.getLatitude()==null || entity.getLongitude()==null){
                //如果没有经纬度，重新根据地址获取
                GpsLocation location = GeocodingUtils.geocoding(entity.getFullAddress());
                entity.setLongitude(location.getLongitude());
                entity.setLatitude(location.getLatitude());
            }
            addressDao.save(entity);
            //更新用户的gps位置
            if(userEntity.getLatitude()==null || userEntity.getLongitude()==null){
                UserEntity user = userService.getUserById(userEntity.getId());
                user.setLongitude(entity.getLongitude());
                user.setLatitude(entity.getLatitude());
                userService.registerUser(user);
            }
        }
        return entity;
    }

    @Transactional
    public AddressEntity saveDefaultAddress(String id) {

        UserEntity userEntity = userService.getCurrentUser();
        AddressEntity db = null;
        if(userEntity!=null){
            List<AddressEntity> list = findDefaultAddress();
            for (AddressEntity addressEntity:list){
                if(addressEntity.getUserDefault()){
                    addressEntity.setUserDefault(false);
                    addressDao.save(addressEntity);
                }
            }

            db = addressDao.getById(id);
            if(db!=null){
                db.setUserDefault(true);
                addressDao.save(db);
            }
        }

        return db;
    }

    @Override
    public AddressEntity findMyAddress() {
        List<AddressEntity> list = findDefaultAddress();
        if(list.size()>0){
            return list.get(0);
        }
        return null;
    }

    public List<AddressEntity> findDefaultAddress() {

        UserEntity user = userService.getCurrentUser();
        if(user==null){
            return null;
        }
        QAddressEntity entity = QAddressEntity.addressEntity;

        BooleanExpression ex = entity.user.id.eq(user.getId()).and(entity.userDefault.eq(true));

        Iterable<AddressEntity> iterable =  addressDao.findAll(ex);
        Iterator<AddressEntity> it = iterable.iterator();
        List<AddressEntity> list = new ArrayList<>();
        while (it.hasNext()){
            list.add(it.next());
        }
        return list;
    }
}
