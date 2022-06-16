package com.xiushang.common.notice.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.xiushang.common.annotations.XiushangApi;
import com.xiushang.common.notice.service.NoticeService;
import com.xiushang.common.notice.vo.NoticeVo;
import com.xiushang.entity.notice.NoticeEntity;
import com.xiushang.framework.entity.vo.PageTableVO;
import com.xiushang.framework.entity.vo.SearchPageVo;
import com.xiushang.framework.log.CommonResult;
import com.xiushang.security.SecurityRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 公告管理
 */
@Api(tags = "常用接口")
@ApiSort(value = 14)
@Controller
@RequestMapping(value = "/api/notice",
        produces = "application/json; charset=UTF-8")
@Validated
public class NoticeController {
    @Autowired
    private NoticeService noticeService;
    /**
     * 获取
     * @return
     */
    @XiushangApi
    @ApiOperation(value = "获取公告详情")
    @ResponseBody
    @GetMapping("/get")
    public CommonResult<NoticeEntity> get(@ApiParam(value = "id主键",required = true)String id) {
        NoticeEntity NoticeEntity = noticeService.get(id);

        return CommonResult.success(NoticeEntity);
    }

    /**
     * 删除
     * @return
     */
    @XiushangApi
    @ApiOperation(value = "删除公告")
    @ResponseBody
    @GetMapping("/delete")
    @Secured({SecurityRole.ROLE_USER})
    public CommonResult<NoticeEntity> delete(@ApiParam(value = "id主键",required = true)String id) {
        noticeService.deleteNotice(id);
        return CommonResult.success();
    }

    /**
     * 保存
     * @return
     */
    @XiushangApi
    @ApiOperation(value = "添加公告")
    @ResponseBody
    @PostMapping("/post")
    @Secured({SecurityRole.ROLE_USER})
    public CommonResult<NoticeEntity> post(@Valid @RequestBody NoticeVo noticeVo) {

        NoticeEntity NoticeEntity = noticeService.saveNotice(noticeVo);
        return CommonResult.success(NoticeEntity);
    }
    /**
     * 获取租户公告列表
     * @param searchPageVo
     * @return          PageTableVO
     */
    @XiushangApi
    @ApiOperation(value = "获取租户公告列表")
    @ResponseBody
    @PostMapping("/listPage")
    public CommonResult<PageTableVO<NoticeEntity>> listPage(@RequestBody SearchPageVo searchPageVo) {

        PageTableVO vo = noticeService.findPageList(searchPageVo);

        return CommonResult.success(vo);
    }



    /**
     * 获取我的公告列表
     * @param searchPageVo
     * @return          PageTableVO
     */
    @ResponseBody
    @PostMapping("/myListPage")
    @Secured(SecurityRole.ROLE_CLIENT_MANAGE)
    public CommonResult<PageTableVO<NoticeEntity>> shopListPage(@RequestBody SearchPageVo searchPageVo) {

        PageTableVO vo = noticeService.findMyPageList(searchPageVo);

        return CommonResult.success(vo);
    }

}
