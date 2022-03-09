package com.xiushang.common.info.mapper;

import com.xiushang.common.info.vo.HelpVo;
import com.xiushang.entity.info.HelpEntity;
import com.xiushang.framework.model.BaseMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface HelpSaveMapper extends BaseMapping<HelpEntity, HelpVo> {

    HelpSaveMapper INSTANCE = Mappers.getMapper( HelpSaveMapper.class );

}
