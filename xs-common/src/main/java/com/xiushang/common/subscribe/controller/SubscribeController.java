package com.xiushang.common.subscribe.controller;

import com.xiushang.common.job.service.DynamicTaskService;
import com.xiushang.common.job.vo.SubscribeMsgAppointVo;
import com.xiushang.common.user.service.UserService;
import com.xiushang.entity.SubscribeMsgAppointEntity;
import com.xiushang.entity.SubscribeMsgEntity;
import com.xiushang.entity.UserEntity;
import com.xiushang.framework.entity.vo.PageTableVO;
import com.xiushang.framework.log.CommonResult;
import com.xiushang.framework.utils.WebUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "微信接口")
@Controller
@RequestMapping(value = "/subscribe",
        produces = "application/json; charset=UTF-8")
public class SubscribeController {



    @Autowired
    private  DynamicTaskService dynamicTaskService;
    /**
     * 获取订阅
     * @return
     */
    @ApiOperation(value = "获取订阅消息")
    @ResponseBody
    @GetMapping("/get")
    public CommonResult<SubscribeMsgEntity> get(String id) {
        SubscribeMsgEntity entity = dynamicTaskService.get(id);

        return CommonResult.success(entity);
    }


    /**
     * 保存
     * @return
     */
    @ApiOperation(value = "添加订阅消息")
    @ResponseBody
    @PostMapping("/post")
    public CommonResult<SubscribeMsgEntity> post(@RequestBody SubscribeMsgEntity entity) {

        dynamicTaskService.saveOrUpdateTask(entity);
        return CommonResult.success(entity);
    }


    /**
     * 保存
     * @return
     */
    @ApiOperation(value = "删除订阅消息")
    @ResponseBody
    @PostMapping("/delete")
    public CommonResult<SubscribeMsgEntity> delete(HttpServletRequest req) {
        SubscribeMsgEntity entity = WebUtil.getJsonBody(req,SubscribeMsgEntity.class);
        dynamicTaskService.deleteTask(entity.getId());
        return CommonResult.success(entity);
    }

    /**
     * 保存
     * @return
     */
    @ApiOperation(value = "取消订阅消息")
    @ResponseBody
    @PostMapping("/cancel")
    public CommonResult<SubscribeMsgEntity> cancel(HttpServletRequest req) {
        SubscribeMsgEntity entity = WebUtil.getJsonBody(req,SubscribeMsgEntity.class);
        entity.setStatus(4);
        dynamicTaskService.saveOrUpdateTask(entity);
        return CommonResult.success(entity);
    }

    /**
     * 分页列表
     * @return          PageTableVO
     */
    @ApiOperation(value = "订阅消息列表")
    @ResponseBody
    @PostMapping("/listPage")
    public CommonResult<PageTableVO<SubscribeMsgEntity>> listPage() {

        PageTableVO vo = dynamicTaskService.findPageList();

        return CommonResult.success(vo);
    }

    @ApiOperation("订阅消息")
    @PostMapping("/appoint")
    @ResponseBody
    public CommonResult<SubscribeMsgAppointEntity> appoint(@RequestBody SubscribeMsgAppointVo appointVo) {
        SubscribeMsgAppointEntity appointEntity = dynamicTaskService.appoint(appointVo);

        return CommonResult.success(appointEntity);
    }


}
