package com.xiushang.common.notice.mapper;

import com.xiushang.common.notice.vo.NoticeVo;
import com.xiushang.entity.notice.NoticeEntity;
import com.xiushang.framework.model.BaseMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface NoticeSaveMapper extends BaseMapping<NoticeEntity, NoticeVo> {

    NoticeSaveMapper INSTANCE = Mappers.getMapper( NoticeSaveMapper.class );

}
