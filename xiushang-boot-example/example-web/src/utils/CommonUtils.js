import token from '@/utils/token';
import { request } from 'umi';
import {notification} from 'antd';

//export const baseUrl = ''; //stg
export const baseUrl = '/proxy'; //dev


export function listData(res) {
  let list =  {
    "data": [],
    "total": 0,
    "success": true,
    "pageSize": "10",
    "current": 1
  }

  if (res.data.rowData && res.data.rowData.length) {
    //list.data = res.data.rowData;
    list.data.push(    {
      "key": 16,
      "disabled": false,
      "href": "https://ant.design",
      "avatar": "https://gw.alipayobjects.com/zos/rmsportal/eeHMaZBwmTvLdIwMfBpg.png",
      "name": "TradeCode 16",
      "owner": "曲丽丽",
      "desc": "这是一段描述",
      "callNo": 942,
      "status": 1,
      "updatedAt": "2022-03-01",
      "createdAt": "2022-03-01",
      "progress": 93
    })
  }
  if (res.data.totalCount) {
    list.total = res.data.totalCount;
  }
  if (res.data.pageSize) {
    list.pageSize = res.data.pageSize;
  }
  if (res.data.pageNo) {
    list.current = res.data.pageNo;
  }
  console.log(list)
  return list;
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

