import { request } from 'umi';


/** 获取用户列表 */
export async function queryUserList(params, options) {
  let param = {
    "pageNo": 0,
    "pageSize": 0,
    "searchKey": "",
    "shopId": ""
  }
  return request('/api/user/pageList', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: param,
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
