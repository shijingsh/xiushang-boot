import React from 'react';
import { Modal } from 'antd';
import {
  ProFormSelect,
  ProFormText,
  ProFormTextArea,
  StepsForm,
  ProFormRadio,
  ProFormDateTimePicker,
} from '@ant-design/pro-form';
import { useIntl, FormattedMessage } from 'umi';

const UpdateForm = (props) => {
  const intl = useIntl();
  console.log(props.values.validDate)
  return (
    <StepsForm
      stepsProps={{
        size: 'small',
      }}
      stepsFormRender={(dom, submitter) => {
        return (
          <Modal
            width={640}
            bodyStyle={{
              padding: '32px 40px 48px',
            }}
            destroyOnClose
            title={"修改公告"}
            visible={props.updateModalVisible}
            footer={submitter}
            onCancel={() => {
              props.onCancel();
            }}
          >
            {dom}
          </Modal>
        );
      }}
      onFinish={props.onSubmit}
    >
      <StepsForm.StepForm
        initialValues={{
          title: props.values.title,
          content: props.values.content,
        }}
        title={"基本信息"}
      >
        <ProFormText
          name="title"
          label={"公告标题"}
          width="md"
          rules={[
            {
              required: true,
              message: "请输入公告标题！",
            },
          ]}
        />
        <ProFormTextArea
          name="content"
          width="md"
          label={"公告内容"}
          placeholder={"请输入公告内容！"}
          rules={[
            {
              required: true,
              message: "请输入公告内容",
            },
          ]}
        />
      </StepsForm.StepForm>

      <StepsForm.StepForm
        initialValues={{

          validDate: props.values.validDate,
        }}
        title={"设置有效期"}
      >
        <ProFormDateTimePicker
          name="validDate"
          width="md"
          label={"公告有效期"}
          rules={[
            {
              required: true,
              message: "请选择公告有效期",
            },
          ]}
        />
      </StepsForm.StepForm>
    </StepsForm>
  );
};

export default UpdateForm;
