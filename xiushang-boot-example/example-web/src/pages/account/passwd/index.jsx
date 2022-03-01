import { Card, message } from 'antd';
import ProForm, {
  ProFormDateRangePicker,
  ProFormDependency,
  ProFormDigit,
  ProFormRadio,
  ProFormSelect,
  ProFormText,
  ProFormTextArea,
} from '@ant-design/pro-form';
import { useRequest } from 'umi';
import { PageContainer } from '@ant-design/pro-layout';
import { modifyPass } from './service';
import styles from './style.less';
import React from "react";

const BasicForm = () => {
  const { run, data: passData,errorCode:errorCode, } = useRequest(modifyPass, {
    manual: true,//需要手动触发
    onSuccess: (result) => {
      message.success('密码修改成功！');
    },
  });

  const onFinish = async (values) => {
    run(values);
  };

  return (
    <PageContainer >
      <Card bordered={false}>
        <ProForm
          hideRequiredMark
          style={{
            margin: 'auto',
            marginTop: 8,
            maxWidth: 600,
          }}
          name="basic"
          layout="vertical"
          initialValues={{
            public: '1',
          }}
          onFinish={onFinish}
        >
          <ProFormText.Password
            width="md"
            label="原密码"
            name="oldPassword"
            rules={[
              {
                required: true,
                message: '请输入原密码',
              },
            ]}
            placeholder="请输入原密码"
          />
          <ProFormText.Password
            width="md"
            label="新密码"
            name="newPassword"
            rules={[
              {
                required: true,
                message: '请输入新密码',
              },
            ]}
            placeholder="请输入新密码"
          />
          <ProFormText.Password
            width="md"
            label="确认密码"
            name="confirmPassword"
            rules={[
              {
                required: true,
                message: '请输入确认密码',
              },
            ]}
            placeholder="请输入确认密码"
          />
        </ProForm>
      </Card>
    </PageContainer>
  );
};

export default BasicForm;
