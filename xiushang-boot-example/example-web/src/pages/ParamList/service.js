// @ts-ignore

/* eslint-disable */
import { request } from 'umi';
import * as CommonUtils from "@/utils/CommonUtils";


/** 获取列表 */
export async function queryParamList(params, options) {
  params  = CommonUtils.getPageParam(params);
  params.searchKey = params.paramName;
  return request('/api/param/listPage', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: params,
    ...(options || {}),
  });
}


/** 保存 */
export async function saveParam(params,options) {
  return request('/api/param/post', {
    method: 'POST',
    data: params,
    ...(options || {}),
  });
}

/** 删除 */
export async function removeParam(id) {
  return request('/api/param/delete?id='+id, {
    method: 'GET',
  });
}
