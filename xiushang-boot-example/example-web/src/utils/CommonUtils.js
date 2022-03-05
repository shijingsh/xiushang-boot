import token from '@/utils/token';
import { request } from 'umi';
import {notification} from 'antd';

//export const baseUrl = ''; //stg
export const baseUrl = '/proxy'; //dev


export function listData(res) {

  if (res.data.totalCount) {
    res.total = res.data.totalCount;
  }
  if (res.data.pageSize) {
    res.pageSize = res.data.pageSize;
  }
  if (res.data.pageNo) {
    res.current = res.data.pageNo;
  }

  if (res.data.rowData && res.data.rowData.length) {
    res.data = res.data.rowData;
  }else if(res.data.rowData !== undefined){
    res.data = [];
  }

  return res;
}

export function getPageParam(params) {
  if(!params){
    params = {};
  }

  if(params.current){
    params.pageNo = params.current
  }else {
    params.pageNo = 1;
  }

  return params;

}

export function getHomeUrl() {
  return window.location.protocol + '//' + window.location.host;
}

export function getImageUrl(url, defaultUrl) {
  if (url) {
    if (url.indexOf('http') >= 0) {
      return url;
    }
    return getHomeUrl() + url;
  } else {
    return getHomeUrl() + defaultUrl;
  }
}


export function getUploadProps() {
  let propsUpload = {
    action: baseUrl + '/api/upload',
    headers: {
      Authorization: token.get(),
    },
  };

  return propsUpload;
}

export function getUploadResponse(res, callback) {

  const data = res.data;
  if (res.execResult) {
    if (data && data.length) {
      let url = data[0].relativePath;
      // 将图片插入到编辑器中
      if (callback) {
        callback(getImageUrl(url),url);
      }
    } else {
      notification.error({
        message: '上传失败！',
      });
    }
  } else {
    notification.error({
      message: res.errorText,
    });
  }
}

export function uploadFile(formData, index, callback) {
  request('/api/upload', {
    method: 'POST',
    body: formData,
  })
    .then(res => {
      console.log(res);
      getUploadResponse(res, callback);
    })
    .catch(err => {
      notification.error({
        message: '上传发生异常！',
      });
    });
}

