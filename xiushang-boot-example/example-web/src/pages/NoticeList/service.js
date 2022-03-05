// @ts-ignore

/* eslint-disable */
import { request } from 'umi';
import * as CommonUtils from "@/utils/CommonUtils";

/** 获取公告 */
export async function queryNotices(id) {
  return request('/api/news/v1/get?id='+id, {
    method: 'GET',
  });
}

/** 获取公告列表 */
export async function queryNoticeList(params, options) {
  params  = CommonUtils.getPageParam(params);
  params.searchKey = params.title;
  return request('/api/news/v1/listPage', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: params,
    ...(options || {}),
  });
}


/** 保存 */
export async function saveNotice(params,options) {
  return request('/api/news/v1/post', {
    method: 'POST',
    data: params,
    ...(options || {}),
  });
}

/** 删除 */
export async function removeNotice(id) {
  return request('/api/news/v1/delete?id='+id, {
    method: 'GET',
  });
}
