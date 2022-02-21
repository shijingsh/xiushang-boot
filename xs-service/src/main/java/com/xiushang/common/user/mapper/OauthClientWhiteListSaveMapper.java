package com.xiushang.common.user.mapper;

import com.xiushang.common.user.vo.OauthClientWhiteListSaveVo;
import com.xiushang.entity.oauth.OauthClientWhiteListEntity;
import com.xiushang.framework.model.BaseMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface OauthClientWhiteListSaveMapper extends BaseMapping<OauthClientWhiteListEntity, OauthClientWhiteListSaveVo> {

    OauthClientWhiteListSaveMapper INSTANCE = Mappers.getMapper( OauthClientWhiteListSaveMapper.class );

}
