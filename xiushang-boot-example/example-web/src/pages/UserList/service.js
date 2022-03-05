import { request } from 'umi';
import * as CommonUtils from '@/utils/CommonUtils';

/** 获取用户列表 */
export async function queryUserList(params, options) {
  console.log(params)
  params = CommonUtils.getPageParam(params);

  return request('/api/user/pageList', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: params,
    ...(options || {}),
  });
}


export async function enableOrDisable(id, options) {

  return request('/api/user/enableOrDisable?id='+id, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    },
    ...(options || {}),
  });
}



export async function editUser(params) {
  params.loginName = params.mobile;

  return request('/api/user/modifyByAdmin', {
    method: 'POST',
    data: params,
  });
}
