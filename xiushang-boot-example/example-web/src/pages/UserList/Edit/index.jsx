import React, {useEffect, useState} from "react";
import {Button, Card, Form, Input, message, Radio, Select,} from "antd";
import {PageContainer} from "@ant-design/pro-layout";
import {history, useRequest} from "umi";
import { editUser,queryUserDetail } from '../service';
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
      name: detail.name,
      email: detail.email?detail.email:"",
      mobile: detail.mobile,
      deleted: detail.deleted + '',
    });
  }, [detail]);


  const { loading: submitting, run: saveEditUser } = useRequest(editUser, {
    manual: true,
    formatResult:(res) => {
      return  res;
    },
    onSuccess: (res, params) => {
      if (res.errorCode === 0) {
        message.success('操作成功！');
        history.push({
          pathname: '/admin/user-list'
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
    saveEditUser(values);
  };

  const prefixSelector = (
    <Form.Item name="prefix" noStyle>
      <Select style={{width: 70}}>
        <Option value="86">+86</Option>
        <Option value="87">+87</Option>
      </Select>
    </Form.Item>
  );


  return (
    <Form
      {...formItemLayout}
      form={form}
      name="register"
      onFinish={onFinish}
      initialValues={{
        password:"",
        confirm:""
      }}
      scrollToFirstError
    >
      <Form.Item
        name="name"
        label={
          <span>
            昵称&nbsp;
          </span>
        }
        rules={[
          {
            required: true,
            message: "请输入昵称!",
            whitespace: true,
          },
        ]}
      >
        <Input/>
      </Form.Item>

      <Form.Item
        name="mobile"
        label="手机号"
        rules={[
          {
            pattern:/^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "请输入有效的手机号码!",
          },
          /*{
            type: "mobile",
            message: "请输入有效的手机号码!",
          },*/
          {required: true, message: "请输入手机号!"}
        ]}
      >
        <Input/>
      </Form.Item>

      <Form.Item
        name="email"
        label="邮箱"
        rules={[
          {
            type: "email",
            message: "请输入有效的邮箱!",
          },
          {
            required: false,
            message: "请输入邮箱!",
          },
        ]}
      >
        <Input/>
      </Form.Item>

      <Form.Item
        name="password"
        label="设置密码"
        rules={[
          {
            required: true,
            message: "请输入密码!",
          },
        ]}
        hasFeedback
      >
        <Input.Password/>
      </Form.Item>

      <Form.Item
        name="confirm"
        label="确认密码"
        dependencies={["password"]}
        hasFeedback
        rules={[
          {
            required: true,
            message: "请输入确认密码!",
          },
          ({getFieldValue}) => ({
            validator(rule, value) {
              if (!value || getFieldValue("password") === value) {
                return Promise.resolve();
              }
              return Promise.reject(
                "两次输入密码不一致!"
              );
            },
          }),
        ]}
      >
        <Input.Password/>
      </Form.Item>

      <Form.Item name="deleted" label="账号状态">
        <Radio.Group>
          <Radio value={'0'}>正常</Radio>
          <Radio value={'1'}>禁止</Radio>
        </Radio.Group>
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
      return queryUserDetail(id);
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
