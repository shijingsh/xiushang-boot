import { request } from 'umi';
export async function fakeRegister(params) {
  params.loginName = params.mobile;
  params.verifyCode = params.captcha;

  return request('/register', {
    method: 'POST',
    data: params,
  });
}


export async function getVerifyCode(params) {

  return request('/verifyCode', {
    method: 'POST',
    data: params,
  });
}
