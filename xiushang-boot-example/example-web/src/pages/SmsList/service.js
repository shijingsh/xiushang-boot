// @ts-ignore

/* eslint-disable */
import { request } from 'umi';
import * as CommonUtils from "@/utils/CommonUtils";


/** 获取列表 */
export async function queryList(params, options) {
  params  = CommonUtils.getPageParam(params);
  params.searchKey = params.paramName;
  return request('/api/sms/listPage', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: params,
    ...(options || {}),
  });
}


