package com.xiushang.common.info.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.xiushang.common.annotations.XiushangApi;
import com.xiushang.common.info.service.AddressService;
import com.xiushang.common.info.vo.AddressVo;
import com.xiushang.entity.info.AddressEntity;
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
import java.util.List;

/**
 * 用户地址
 */
@Api(tags = "常用接口")
@ApiSort(value = 14)
@Controller
@RequestMapping(value = "/api/address",
        produces = "application/json; charset=UTF-8")
@Validated
public class AddressController {
    @Autowired
    private AddressService addressService;
    /**
     * 获取
     * @return
     */
    @ResponseBody
    @GetMapping("/get")
    public CommonResult<AddressEntity> get(@ApiParam(value = "id主键",required = true)String id) {
        AddressEntity entity = addressService.get(id);

        return CommonResult.success(entity);
    }

    /**
     * 保存
     * @return
     */
    @XiushangApi
    @ApiOperation(value = "保存用户收货地址")
    @ResponseBody
    @PostMapping("/post")
    @Secured(SecurityRole.ROLE_USER)
    public CommonResult<AddressEntity> post(@Valid @RequestBody AddressVo addressVo) {

        AddressEntity addressEntity = addressService.saveAddress(addressVo);
        return CommonResult.success(addressEntity);
    }

    /**
     * 保存
     * @return
     */
    @XiushangApi
    @ApiOperation(value = "设置用户默认收货地址")
    @ResponseBody
    @GetMapping("/setDefault")
    @Secured(SecurityRole.ROLE_USER)
    public CommonResult<AddressEntity> setDefault(@ApiParam(value = "id主键",required = true)String id) {
        AddressEntity addressEntity = addressService.saveDefaultAddress(id);
        return CommonResult.success(addressEntity);
    }
    /**
     * 保存
     * @return
     */
    @XiushangApi
    @ApiOperation(value = "删除用户收货地址")
    @ResponseBody
    @GetMapping("/delete")
    @Secured(SecurityRole.ROLE_USER)
    public CommonResult<AddressEntity> delete(@ApiParam(value = "id主键",required = true)String id) {
        addressService.delete(id);
        return CommonResult.success();
    }
    /**
     * 我的地址
     */
    @XiushangApi
    @ApiOperation(value = "获取用户收货地址")
    @ResponseBody
    @GetMapping("/my")
    @Secured(SecurityRole.ROLE_USER)
    public CommonResult<AddressEntity> my() {

        AddressEntity entity = addressService.findMyAddress();
        //if(entity==null){
          //  entity = new AddressEntity();
        //}
        return CommonResult.success(entity);
    }

    /**
     * 我的地址
     */
    @XiushangApi
    @ApiOperation(value = "获取收货地址列表")
    @ResponseBody
    @PostMapping("/myList")
    @Secured(SecurityRole.ROLE_USER)
    public CommonResult<List<AddressEntity>> myList() {

        List<AddressEntity> list = addressService.findList();

        return CommonResult.success(list);
    }

}
