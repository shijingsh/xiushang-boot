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



export async function editClient(params) {
  params.loginName = params.mobile;

  return request('/api/user/client/post', {
    method: 'POST',
    data: params,
  });
}



export async function queryDetail(id) {

  return request('/api/user/client/get?clientId='+id, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    },
  });
}
