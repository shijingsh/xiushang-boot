package com.xiushang.common.info.mapper;

import com.xiushang.common.info.vo.AddressVo;
import com.xiushang.entity.info.AddressEntity;
import com.xiushang.framework.model.BaseMapping;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface AddressSaveMapper extends BaseMapping<AddressEntity, AddressVo> {

    AddressSaveMapper INSTANCE = Mappers.getMapper( AddressSaveMapper.class );

}
