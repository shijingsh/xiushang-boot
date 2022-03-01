import token from '@/utils/token';
import { request } from 'umi';

export const baseUrl = ''; //stg
//export const baseUrl = '/proxy'; //dev


export function listData(data) {
  let list = {
    list: [],
    pagination: {
      total: 0,
      pageSize: 10,
      current: 1,
    },
  };
  if (data.rowData && data.rowData.length) {
    list.list = data.rowData;
  }
  if (data.result == null && data.length) {
    list.list = data;
  }
  if (data.totalCount) {
    list.pagination.total = data.totalCount;
  }
  if (data.pageSize) {
    list.pagination.pageSize = data.pageSize;
  }
  if (data.pageNo) {
    list.pagination.current = data.pageNo;
  }
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
        callback(getImageUrl(url));
      }
    } else {
      message.error('上传失败！');
    }
  } else {
    message.error(res.errorText);
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
      message.error(((err.response || {}).data || {}).message || '保存数据异常！');
    });
}

