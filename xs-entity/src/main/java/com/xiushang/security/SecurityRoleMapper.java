package com.xiushang.security;

import com.xiushang.entity.RoleEntity;
import com.xiushang.framework.model.BaseMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface SecurityRoleMapper extends BaseMapping<RoleEntity, SecurityRoleVo> {

    SecurityRoleMapper INSTANCE = Mappers.getMapper( SecurityRoleMapper.class );

}
