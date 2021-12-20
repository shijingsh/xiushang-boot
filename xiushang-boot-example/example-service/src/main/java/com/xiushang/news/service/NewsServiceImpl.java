package com.xiushang.news.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.xiushang.common.user.service.UserService;
import com.xiushang.common.utils.BaseServiceImpl;
import com.xiushang.entity.news.NewsEntity;
import com.xiushang.entity.news.QNewsEntity;
import com.xiushang.framework.entity.vo.PageTableVO;
import com.xiushang.news.dao.NewsDao;
import com.xiushang.vo.HelpSearchVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Calendar;

/**
 * 公告
 * Created by kf on 2016/11/7.
 */
@Service
public class NewsServiceImpl extends BaseServiceImpl<NewsEntity> implements NewsService {
    @Autowired
    private NewsDao newsDao;
    @Autowired
    private UserService userService;


    @Transactional
    public PageTableVO findPageList(HelpSearchVo helpSearchVo) {

        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_YEAR, -1);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        //有效的公告
        QNewsEntity entity = QNewsEntity.newsEntity;
        BooleanExpression ex = entity.status.eq(1).and(entity.validDate.after(c.getTime()));

        String searchKey = helpSearchVo.getSearchKey();
        if (StringUtils.isNotBlank(searchKey)) {
            ex = ex.and(entity.content.like("%" + searchKey + "%"));
        }
        helpSearchVo.setSort("validDate");
        helpSearchVo.setDir("asc");

        Page<NewsEntity> page = findPageList(ex, helpSearchVo.createPageRequest());
        PageTableVO vo = new PageTableVO(page, helpSearchVo);

        return vo;
    }

    public NewsEntity saveNews(NewsEntity newsEntity) {

        return newsDao.save(newsEntity);
    }

    public NewsEntity deleteNews(NewsEntity entity) {
        newsDao.delete(entity);

        return entity;
    }
}
