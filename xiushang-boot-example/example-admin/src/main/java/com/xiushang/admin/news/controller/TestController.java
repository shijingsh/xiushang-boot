package com.xiushang.admin.news.controller;

import com.xiushang.config.ApiVersion;
import com.xiushang.entity.news.NewsEntity;
import com.xiushang.framework.log.CommonResult;
import com.xiushang.news.service.NewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 公告管理
 */
@ApiVersion
@Api(tags = {"oauth2接口"})
@Controller
@RequestMapping(value = "/api/oauth",
        produces = "application/json; charset=UTF-8")
public class TestController {
    @Autowired
    private NewsService newsService;
    /**
     * 获取
     * @return
     */
    @ApiOperation(value = "获取公告详情(oauth2 授权版本)")
    @ResponseBody
    @GetMapping("/get")
    public CommonResult<NewsEntity> get(String id) {
        NewsEntity newsEntity = newsService.get(id);

        return CommonResult.success(newsEntity);
    }

}
