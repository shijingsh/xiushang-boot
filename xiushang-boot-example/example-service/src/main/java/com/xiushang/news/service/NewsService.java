package com.xiushang.news.service;

import com.xiushang.common.utils.BaseService;
import com.xiushang.entity.news.NewsEntity;
import com.xiushang.framework.entity.vo.PageTableVO;
import com.xiushang.vo.NewsSearchVo;

public interface NewsService extends BaseService<NewsEntity>{

    PageTableVO findPageList(NewsSearchVo helpSearchVo);

    NewsEntity saveNews(NewsEntity newsEntity);

    NewsEntity deleteNews(NewsEntity entity);
}
