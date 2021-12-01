package com.xiushang.admin.news.controller;

import com.xiushang.common.user.service.UserService;
import com.xiushang.config.ApiVersion;
import com.xiushang.entity.news.NewsEntity;
import com.xiushang.framework.entity.vo.PageTableVO;

import com.xiushang.framework.log.CommonResult;
import com.xiushang.framework.utils.WebUtil;
import com.xiushang.news.service.NewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 公告管理
 */
@ApiVersion
@Api(tags = {"常用接口","常用接口2"})
@Controller
@RequestMapping(value = "/api/news",
        produces = "application/json; charset=UTF-8")
public class NewsController {
    @Autowired
    private NewsService newsService;
    @Autowired
    private UserService userService;
    /**
     * 获取
     * @return
     */
    @ApiOperation(value = "获取公告详情（普通用户才能用）")
    @ResponseBody
    @GetMapping("/{version}/get")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public CommonResult<NewsEntity> get(String id) {
        NewsEntity newsEntity = newsService.get(id);

        return CommonResult.success(newsEntity);
    }

    @Secured({"ROLE_ADMIN"})
    @ApiVersion(2)
    @GetMapping("/{version}/get")
    @ResponseBody
    @ApiOperation(value = "获取公告详情V2（管理员才能用）")
    public CommonResult<NewsEntity> getV2(String id) {
        NewsEntity newsEntity = newsService.get(id);

        return CommonResult.success(newsEntity);
    }
    /**
     * 删除
     * @return
     */
    @ApiOperation(value = "删除公告")
    @ResponseBody
    @PostMapping("/{version}/delete")
    public CommonResult<NewsEntity> delete(HttpServletRequest req) {
        NewsEntity entity = WebUtil.getRequstBody(req,NewsEntity.class);
        newsService.deleteNews(entity);
        return CommonResult.success(entity);
    }

    /**
     * 保存
     * @return
     */
    @ApiOperation(value = "添加公告")
    @ResponseBody
    @PostMapping("/{version}/post")
    public CommonResult<NewsEntity> post(@RequestBody NewsEntity entity) {

        NewsEntity newsEntity = newsService.saveNews(entity);
        return CommonResult.success(newsEntity);
    }
    /**
     * 分页列表
     * @param req   请求
     * @return          PageTableVO
     */
    @ApiOperation(value = "获取公告列表")
    @ResponseBody
    @PostMapping("/{version}/listPage")
    public CommonResult<PageTableVO<NewsEntity>> listPage(HttpServletRequest req) {

        PageTableVO vo = newsService.findPageList();

        return CommonResult.success(vo);
    }


}
