package com.xiushang.common.info.service;

import com.xiushang.common.info.vo.AddressVo;
import com.xiushang.common.utils.BaseService;
import com.xiushang.entity.info.AddressEntity;

import java.util.List;

/**
 * Created by liukefu on 2017/1/18.
 */
public interface AddressService extends BaseService<AddressEntity> {

    AddressEntity findMyAddress();

    List<AddressEntity> findList();

    AddressEntity saveAddress(AddressVo addressVo);

    AddressEntity saveDefaultAddress(String id);
}
