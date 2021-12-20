package com.xiushang.news.service;

import com.xiushang.common.utils.BaseService;
import com.xiushang.entity.news.NewsEntity;
import com.xiushang.framework.entity.vo.PageTableVO;
import com.xiushang.vo.HelpSearchVo;

public interface NewsService extends BaseService<NewsEntity>{

    PageTableVO findPageList(HelpSearchVo helpSearchVo);

    NewsEntity saveNews(NewsEntity newsEntity);

    NewsEntity deleteNews(NewsEntity entity);
}
