package com.xiushang.vo;

import com.xiushang.dto.NewsDTO;
import com.xiushang.entity.news.NewsEntity;
import com.xiushang.framework.model.BaseMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface NewsMapper extends BaseMapping<NewsEntity, NewsDTO> {

    NewsMapper INSTANCE = Mappers.getMapper( NewsMapper.class );

}
