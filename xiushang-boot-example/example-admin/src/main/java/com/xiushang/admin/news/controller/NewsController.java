package com.xiushang.admin.news.controller;

import com.xiushang.common.user.service.UserService;
import com.xiushang.common.utils.DateUtil;
import com.xiushang.config.ApiVersion;
import com.xiushang.dto.NewsDTO;
import com.xiushang.entity.UserEntity;
import com.xiushang.entity.news.NewsEntity;
import com.xiushang.framework.entity.vo.PageTableVO;
import com.xiushang.framework.log.CommonResult;
import com.xiushang.news.service.NewsService;
import com.xiushang.security.SecurityRole;
import com.xiushang.vo.NewsSearchVo;
import com.xiushang.vo.NewsMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

/**
 * 公告管理
 */
@ApiVersion
@Api(tags = {"常用接口"})   //@Api(tags = {"常用接口","常用接口2"})
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
    @ApiOperation(value = "获取公告详情（客户端授权就能访问）")
    @ResponseBody
    @GetMapping("/{version}/get")
    @Secured({SecurityRole.ROLE_CLIENT})
    public CommonResult<NewsDTO> get(String id) {
        NewsEntity newsEntity = newsService.get(id);

        /**
         *  mapstruct 测试
         */
        NewsDTO newDTO = NewsMapper.INSTANCE.sourceToTarget(newsEntity);

        return CommonResult.success(newDTO);
    }


    @ApiVersion(2)
    @GetMapping("/{version}/get")
    @ResponseBody
    @ApiOperation(value = "获取公告详情V2（用户授权才能访问）")
    @Secured({SecurityRole.ROLE_USER})
    public CommonResult<NewsEntity> getV2(String id) {
        NewsEntity newsEntity = newsService.get(id);

        return CommonResult.success(newsEntity);
    }

    @ApiVersion(3)
    @GetMapping("/{version}/get")
    @ResponseBody
    @ApiOperation(value = "获取公告详情V3（管理员才能访问）")
    @Secured({SecurityRole.ROLE_CLIENT_MANAGE})
    public CommonResult<NewsEntity> getV3(String id) {
        NewsEntity newsEntity = newsService.get(id);

        return CommonResult.success(newsEntity);
    }
    /**
     * 删除
     * @return
     */
    @ApiOperation(value = "删除公告")
    @ResponseBody
    @GetMapping("/{version}/delete")
    @Secured({SecurityRole.ROLE_USER})
    public CommonResult<NewsEntity> delete(String id) {
        newsService.delete(id);
        return CommonResult.success();
    }

    /**
     * 保存
     * @return
     */
    @ApiOperation(value = "添加公告")
    @ResponseBody
    @PostMapping("/{version}/post")
    @Secured({SecurityRole.ROLE_USER})
    public CommonResult<NewsEntity> post(@Valid @RequestBody NewsDTO newsDTO) {

        UserEntity userEntity = userService.getCurrentUser();
        if(userEntity!=null){
            NewsEntity entity = NewsMapper.INSTANCE.targetToSource(newsDTO);
            entity.setUserId(userEntity.getId());
            NewsEntity newsEntity = newsService.saveNews(entity);
            return CommonResult.success(newsEntity);
        }

        return CommonResult.success();
    }
    /**
     * 分页列表
     * @return          PageTableVO
     */
    @ApiOperation(value = "获取公告列表")
    @ResponseBody
    @PostMapping("/{version}/listPage")
    public CommonResult<PageTableVO<NewsEntity>> listPage(@RequestBody NewsSearchVo helpSearchVo) {

        PageTableVO vo = newsService.findPageList(helpSearchVo);

        NewsMapper.INSTANCE.sourceToTarget(vo.getRowData());

        return CommonResult.success(vo);
    }

    public static void main(String args[]){
        Date date = new Date();
        date.setTime(1639977415504l);  //1639977415506l 1639977415504

        System.out.println(DateUtil.convertDateToString(DateUtil.FORMATTER,date));
    }


}
