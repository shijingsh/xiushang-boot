import { request } from 'umi';
import * as CommonUtils from '@/utils/CommonUtils';

export async function queryClientList(params, options) {

  params = CommonUtils.getPageParam(params);
  params.searchKey = params.clientId;
  return request('/api/user/client/listPage', {
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



export async function queryUserDetail(id) {

  return request('/api/user/get?id='+id, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    },
  });
}
