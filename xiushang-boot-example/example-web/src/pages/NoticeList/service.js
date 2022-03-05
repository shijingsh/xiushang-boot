// @ts-ignore

/* eslint-disable */
import { request } from 'umi';
import * as CommonUtils from "@/utils/CommonUtils";

/** 此处后端没有提供注释 GET /api/notices */
export async function getNotices(options) {
  return request('/api/notices', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 获取公告列表 */
export async function listNotice(params, options) {
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

/** 新建规则 PUT /api/rule */

export async function updateRule(options) {
  return request('/api/rule', {
    method: 'PUT',
    ...(options || {}),
  });
}

/** 新建规则 POST /api/rule */
export async function addRule(options) {
  return request('/api/rule', {
    method: 'POST',
    ...(options || {}),
  });
}

/** 删除规则 DELETE /api/rule */
export async function removeRule(options) {
  return request('/api/rule', {
    method: 'DELETE',
    ...(options || {}),
  });
}
