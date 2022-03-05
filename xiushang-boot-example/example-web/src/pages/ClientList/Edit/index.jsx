import React, {useEffect, useState} from "react";
import {Button, Card, Form, Input, message, Radio, Select,InputNumber,} from "antd";
import {PageContainer} from "@ant-design/pro-layout";
import {history, useRequest} from "umi";
import { editClient,queryDetail } from '../service';
const {Option} = Select;


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

  const { loading: submitting, run: saveEditClient } = useRequest(editClient, {
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
        password:"",
        confirm:"",
        clientType:"CLIENT_TYPE_WX_MINI_APP"
      }}
      scrollToFirstError
    >
      <Form.Item
        name="clientName"
        label={
          <span>
            客户端名称&nbsp;
          </span>
        }
        rules={[
          {
            required: true,
            message: "请输入客户端名称!",
            whitespace: true,
          },
        ]}
      >
        <Input/>
      </Form.Item>
      <Form.Item
        name="clientSecret"
        label={
          <span>
            客户端秘钥&nbsp;
          </span>
        }
        rules={[
          {
            required: true,
            message: "请输入客户端秘钥!",
            whitespace: true,
          },
        ]}
      >
        <Input/>
      </Form.Item>

      <Form.Item name="clientType" label="客户端名类型"
                 rules={[
                   {
                     required: true,
                     message: "请选择客户端名类型!",
                   },
                 ]}
      >
        <Radio.Group onChange={onChangeRadioGroup}>
          <Radio value={'CLIENT_TYPE_WX_MINI_APP'}>小程序</Radio>
          <Radio value={'CLIENT_TYPE_APP'}>APP</Radio>
          <Radio value={'CLIENT_TYPE_WEB'}>Web</Radio>
        </Radio.Group>
      </Form.Item>

      <Form.Item
        name="webServerRedirectUri"
        label="回调地址"
      >
        <Input/>
      </Form.Item>

      <Form.Item
        name="accessTokenValidity"
        label="认证令牌时效"
      >
        <InputNumber/>
      </Form.Item>

      <Form.Item
        name="refreshTokenValidity"
        label="刷新令牌时效"
      >
        <InputNumber/>
      </Form.Item>

      {
        (clientType==="CLIENT_TYPE_APP" || clientType==="CLIENT_TYPE_WX_MINI_APP") &&
          <>
            <Form.Item
              name="appId"
              label={clientType==="CLIENT_TYPE_APP"?"APP包名":"appId"}
            >
              <Input/>
            </Form.Item>

            <Form.Item
              name="secret"
              label={clientType==="CLIENT_TYPE_APP"?"APP签名":"secret"}
            >
              <Input/>
            </Form.Item>
          </>
      }

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
