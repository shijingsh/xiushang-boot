// @ts-ignore

/* eslint-disable */
import { request } from 'umi';
/** 获取当前的用户 GET /api/currentUser */

export async function currentUser(options) {
  return request('/api/user/info', {
    method: 'GET',
    ...(options || {}),
  });
}
/** 退出登录接口 POST /api/login/outLogin */

export async function outLogin(options) {
  return request('/api/login/outLogin', {
    method: 'POST',
    ...(options || {}),
  });
}
/** 登录接口 POST /api/login/account */

export async function login(body, options) {
  var param = {
    username:body.username,
    password:body.password
  }
  return request('/authentication/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: param,
    ...(options || {}),
  });
}
