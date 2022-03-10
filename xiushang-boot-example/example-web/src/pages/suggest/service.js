// @ts-ignore

/* eslint-disable */
import { request } from 'umi';
import * as CommonUtils from "@/utils/CommonUtils";


/** 获取列表 */
export async function queryList(params, options) {
  params  = CommonUtils.getPageParam(params);
  params.searchKey = params.name;
  return request('/api/suggest/listPage', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: params,
    ...(options || {}),
  });
}

export async function queryListType(params, options) {
  params  = CommonUtils.getPageParam(params);
  params.type = 1;
  return request('/api/suggest/listPage', {
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

  return request('/api/suggest/get?id='+id, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    },
  });
}

/** 保存 */
export async function save(params,options) {
  return request('/api/suggest/post', {
    method: 'POST',
    data: params,
    ...(options || {}),
  });
}

/** 删除 */
export async function remove(id) {
  return request('/api/suggest/delete?id='+id, {
    method: 'GET',
  });
}


/** 设置处理中 */
export async function saveProcessing(id,options) {
  return request('/api/suggest/processing?id='+id, {
    method: 'GET',
    ...(options || {}),
  });
}


/** 设置处理完成 */
export async function saveProcess(params,options) {
  return request('/api/suggest/process', {
    method: 'POST',
    data: params,
    ...(options || {}),
  });
}
