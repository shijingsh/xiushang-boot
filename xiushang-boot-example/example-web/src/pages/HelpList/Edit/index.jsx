import React, {useEffect, useState} from "react";
import {Button, Card, Form, Input, message, Radio, Select,InputNumber,} from "antd";
import {PageContainer} from "@ant-design/pro-layout";
import {history, useRequest} from "umi";
import { save,queryDetail } from '../service';
const {Option} = Select;
import { Editor } from '@tinymce/tinymce-react';

const formItemLayout = {
  labelCol: {
    xs: {span: 24},
    sm: {span: 8},
  },
  wrapperCol: {
    xs: {span: 24},
    sm: {span: 8},
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

const RegistrationForm = (props) => {

  const { detail } = props;


  const [form] = Form.useForm();

  useEffect(() => {
    form.setFieldsValue({
      clientName: detail.clientName,
      clientType: detail.clientType,
      clientSecret: detail.clientSecret,
      webServerRedirectUri: detail.webServerRedirectUri,
      accessTokenValidity: detail.accessTokenValidity,
      refreshTokenValidity: detail.refreshTokenValidity,
      appId: detail.appId,
      secret: detail.secret,
    });
  }, [detail]);

  const [clientType, setClientType] = useState("CLIENT_TYPE_WX_MINI_APP");

  const { loading: submitting, run: saveEditClient } = useRequest(save, {
    manual: true,
    formatResult:(res) => {
      return  res;
    },
    onSuccess: (res, params) => {
      if (res.errorCode === 0) {
        message.success('操作成功！');
        history.push({
          pathname: '/client/list'
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
    saveEditClient(values);
  };

  const onChangeRadioGroup= (e) => {
    setClientType(e.target.value);
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
        name="clientName"
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

      <Editor
        id="knowledgeEditor"
        className="min-h300"
        plugins="preview fullscreen  code"
        apiKey="zm114lj7bies6smm7z0wt1d6uhzlt72kr7x3vb5pmxtnkmof"
        ref="tinyEditor"
        // automatic_uploads={!false}
        // images_upload_url={utils.url + '/fileclient-management/api/uploadpic'}
        // images_upload_handler={this.imagesUploadHandler}
        // initialValue='<p>在此輸入您的資訊內容</p>'
        value={detail.h5Content}
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
          images_upload_handler: this.imagesUploadHandler,
          images_reuse_filename: true,
          plugins: 'table advlist image lists preview textcolor', // imagetools 圖片編輯工具-剪切、旋轉、設置大小
          toolbar:
            'formatselect | bold italic strikethrough forecolor backcolor | link | alignleft aligncenter alignright alignjustify  | numlist bullist outdent indent  | removeformat | image',
          // image_description: false, // 图像对话框中的图像描述输入字段
          // image_caption: true // 圖片下的文字
          // image_title: true
        }}
      />

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
        <RegistrationForm detail={userDetail}/>
      </Card>
    </PageContainer>

  );
}
