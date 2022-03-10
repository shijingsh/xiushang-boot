import React, {useEffect, useState,useRef } from "react";
import {Button, Card, Form, Input, message, Radio, Select,InputNumber,} from "antd";
import {PageContainer} from "@ant-design/pro-layout";
import {history, useRequest} from "umi";
import { save,queryDetail } from '../service';
const {Option} = Select;
import { Editor } from '@tinymce/tinymce-react';
import * as CommonUtils from '@/utils/CommonUtils';

const formItemLayout = {
  labelCol: {
    xs: {span: 24},
    sm: {span: 2},
  },
  wrapperCol: {
    xs: {span: 24},
    sm: {span: 15},
  },
};
const tailFormItemLayout = {
  wrapperCol: {
    xs: {
      span: 24,
      offset: 0,
    },
    sm: {
      span: 16,
      offset: 8,
    },
  },
};

const DataForm = (props) => {

  const { detail } = props;


  const [form] = Form.useForm();

  useEffect(() => {
    form.setFieldsValue({
      title: detail.title,
      //content: detail.content,
      displayOrder: detail.displayOrder,
    });
  }, [detail]);

  const editorRef = useRef(null);


  const { loading: submitting, run: saveEdit } = useRequest(save, {
    manual: true,
    formatResult:(res) => {
      return  res;
    },
    onSuccess: (res, params) => {
      if (res.errorCode === 0) {
        message.success('操作成功！');
        history.push({
          pathname: '/help/list'
        });
      }else {
        message.error(res.errorText);
      }
    },
  });

  const onFinish = (values) => {
    if(detail && detail.id){
      values.id = detail.id;
    }
    let content = tinymce.get('helpEditor').getContent();
    if (!content) {
      message.error('请编辑内容！');
      return;
    }
    if (editorRef.current) {
      console.log(editorRef.current.getContent());
    }

    values.content = content;
    saveEdit(values);
  };


  const imagesUploadHandler = (blobInfo, success, failure) => {
    if (blobInfo.blob()) {
      const formData = new window.FormData();
      formData.append('file', blobInfo.blob(), blobInfo.filename());

      CommonUtils.uploadFile(formData, 0, function(url) {
        success(url);
      });
    } else {
      message.error('请选择图片');
    }
  };


  return (
    <Form
      {...formItemLayout}
      form={form}
      name="register"
      onFinish={onFinish}
      initialValues={{

      }}
      scrollToFirstError
    >
      <Form.Item
        name="title"
        label={
          <span>
            帮助标题&nbsp;
          </span>
        }
        rules={[
          {
            required: true,
            message: "请输入帮助标题!",
            whitespace: true,
          },
        ]}
      >
        <Input/>
      </Form.Item>
      <Form.Item
        name="displayOrder"
        label="显示顺序"
      >
        <InputNumber/>
      </Form.Item>
      <Form.Item  label={"帮助内容"}>
        <Editor
          id="helpEditor"
          onInit={(evt, editor) => editorRef.current = editor}
          className="min-h300"
          plugins="preview fullscreen  code"
          apiKey="zm114lj7bies6smm7z0wt1d6uhzlt72kr7x3vb5pmxtnkmof"
          //ref="tinyEditor"
          // automatic_uploads={!false}
          // images_upload_url={utils.url + '/fileclient-management/api/uploadpic'}
          // images_upload_handler={this.imagesUploadHandler}
          // initialValue='<p>在此輸入您的資訊內容</p>'
          value={detail.content}
          init={{
            min_height: 500,
            //theme: 'modern',
            language: 'zh_CN',
            // skin: 'lightgray',
            // menubar: false, // 頂部菜單
            // resize: false, // 右下角調整大小
            statusbar: false, // 底部狀態欄
            object_resizing: false, // 禁止設置媒體大小
            convert_urls: false,
            //images_upload_url: url,
            images_upload_handler: imagesUploadHandler,
            images_reuse_filename: true,
            plugins: 'table advlist image lists preview textcolor', // imagetools 圖片編輯工具-剪切、旋轉、設置大小
            toolbar:
              'formatselect | bold italic strikethrough forecolor backcolor | link | alignleft aligncenter alignright alignjustify  | numlist bullist outdent indent  | removeformat | image',
            // image_description: false, // 图像对话框中的图像描述输入字段
            // image_caption: true // 圖片下的文字
            // image_title: true
          }}
        />

      </Form.Item>

      <Form.Item {...tailFormItemLayout}>
        <Button type="primary" htmlType="submit">
          保存
        </Button>
      </Form.Item>
    </Form>
  );
};

export default ({ location }) =>{
  const id = location.query.id ;

  const { data: detail, loading } = useRequest(() => {
    if(id){
      return queryDetail(id);
    }else {
      return null;
    }
  });

  const [userDetail, setUserDetail] = useState({});
  useEffect(() => {
    setUserDetail(detail || []);
  }, [detail]);

  return (
    <PageContainer>
      <Card bordered={false}>
        <DataForm detail={userDetail}/>
      </Card>
    </PageContainer>

  );
}
