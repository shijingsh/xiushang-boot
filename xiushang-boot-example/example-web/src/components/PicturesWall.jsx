import {Modal, Upload} from 'antd';
import React, {useEffect, useState} from "react";
import * as CommonUtils from '@/utils/CommonUtils';

const PicturesWall = (props) => {

  const getBase64 =(file)  => {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => resolve(reader.result);
      reader.onerror = error => reject(error);
    });
  }

  const handleCancel = () => setPreviewVisible(false);

  const  handlePreview = async file => {
    if (!file.url && !file.preview) {
      file.preview = await getBase64(file.originFileObj);
    }

    setPreviewVisible(true);
    setPreviewImage(file.url || file.preview);

  };

  const getFileList = (fileList) => {

    if(!fileList || !fileList.length){
      return []
    }
    let list = [];
    let index = 1;
    for (let i=0;i<fileList.length;i++){
      let url = fileList[i];
      let obj = {
        uid: index,
        name: 'image'+(index++)+'.png',
        status: 'done',
        url: CommonUtils.getImageUrl(url),
      };
      list.push(obj)
    }
    return list
  };

  const {fileList} = props;
  const fileListShow = getFileList(fileList);

  const [previewVisible, setPreviewVisible] = useState(false);
  const [previewImage, setPreviewImage] = useState(null);


  return (
    <div className="clearfix">
      <Upload
        disabled
        listType="picture-card"
        fileList={fileListShow}
        onPreview={handlePreview}
      >

      </Upload>
      <Modal visible={previewVisible} footer={null} onCancel={handleCancel}>
        <img alt="example" style={{ width: '100%' }} src={previewImage} />
      </Modal>
    </div>
  );
};

export default (props) => {

  return(
    <PicturesWall {...props}/>
  );
}
