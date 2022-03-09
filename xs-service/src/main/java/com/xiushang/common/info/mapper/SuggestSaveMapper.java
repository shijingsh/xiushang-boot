package com.xiushang.common.info.mapper;

import com.xiushang.common.info.vo.SuggestVo;
import com.xiushang.entity.info.SuggestEntity;
import com.xiushang.framework.model.BaseMapping;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface SuggestSaveMapper extends BaseMapping<SuggestEntity, SuggestVo> {

    SuggestSaveMapper INSTANCE = Mappers.getMapper( SuggestSaveMapper.class );

}
