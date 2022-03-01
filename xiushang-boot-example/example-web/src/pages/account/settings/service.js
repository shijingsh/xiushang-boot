import { request } from 'umi';
export async function queryCurrent() {
  return request('/api/user/info');
}
export async function queryProvince() {
  return request('/api/geographic/province');
}
export async function queryCity(province) {
  return request(`/api/geographic/city/${province}`);
}
export async function query() {
  return request('/api/users');
}


export async function modifyHeadPortrait(headPortrait, options) {

  let param = {
    headPortrait:headPortrait,
  };
  return request('/api/user/modifyHeadPortrait', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: param,
    ...(options || {}),
  });

}


export async function modifyUser(param, options) {

  return request('/api/user/modify', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: param,
    ...(options || {}),
  });

}
