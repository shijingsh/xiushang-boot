package com.xiushang.news.service;

import com.xiushang.common.utils.BaseService;
import com.xiushang.entity.news.NewsEntity;
import com.xiushang.framework.entity.vo.PageTableVO;

public interface NewsService extends BaseService<NewsEntity>{

    PageTableVO findPageList();

    NewsEntity saveNews(NewsEntity newsEntity);

    NewsEntity deleteNews(NewsEntity entity);
}
