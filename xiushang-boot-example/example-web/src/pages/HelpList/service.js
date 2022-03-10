// @ts-ignore

/* eslint-disable */
import { request } from 'umi';
import * as CommonUtils from "@/utils/CommonUtils";


/** 获取列表 */
export async function queryList(params, options) {
  params  = CommonUtils.getPageParam(params);
  params.searchKey = params.paramName;
  return request('/api/help/listPage', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: params,
    ...(options || {}),
  });
}


/** 查询详情 */
export async function queryDetail(id) {

  return request('/api/help/get?id='+id, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    },
  });
}

/** 保存 */
export async function save(params,options) {
  return request('/api/help/post', {
    method: 'POST',
    data: params,
    ...(options || {}),
  });
}

/** 删除 */
export async function remove(id) {
  return request('/api/help/delete?id='+id, {
    method: 'GET',
  });
}
