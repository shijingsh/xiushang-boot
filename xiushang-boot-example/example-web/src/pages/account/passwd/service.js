import { request } from 'umi';
export async function modifyPass(params) {
  return request('/api/user/modifyPass', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: params,
  });
}
