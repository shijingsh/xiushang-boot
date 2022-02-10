package com.xiushang.common.user.mapper;

import com.xiushang.common.user.vo.OauthClientDetailsSaveVo;
import com.xiushang.entity.oauth.OauthClientDetailsEntity;
import com.xiushang.framework.model.BaseMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface OauthClientDetailsSaveMapper extends BaseMapping<OauthClientDetailsEntity, OauthClientDetailsSaveVo> {

    OauthClientDetailsSaveMapper INSTANCE = Mappers.getMapper( OauthClientDetailsSaveMapper.class );

}
