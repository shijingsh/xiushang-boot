import {PlusOutlined} from '@ant-design/icons';
import {Button, Drawer, message} from 'antd';
import React, {useRef, useState} from 'react';
import {FormattedMessage, useIntl} from 'umi';
import {PageContainer} from '@ant-design/pro-layout';
import ProTable from '@ant-design/pro-table';
import {ModalForm, ProFormText, ProFormTextArea} from '@ant-design/pro-form';
import ProDescriptions from '@ant-design/pro-descriptions';
import {queryParamList, removeParam, saveParam} from './service';


const handleAdd = async (currentRow,fields) => {

  if(currentRow && currentRow.id){
    fields.id = currentRow.id;
  }
  const hide = message.loading('正在保存');
  try {
    await saveParam({...fields});
    hide();
    message.success('保存成功！');
    return true;
  } catch (error) {
    hide();
    message.error('保存失败！');
    return false;
  }
};

const handleRemove = async (obj) => {
  const hide = message.loading('正在删除');
  if (!obj) return true;

  try {
    await removeParam(obj.id);
    hide();
    message.success('删除成功！');
    return true;
  } catch (error) {
    hide();
    message.error('删除失败!');
    return false;
  }
};

const TableList = () => {

  const [createModalVisible, handleModalVisible] = useState(false);

  const [showDetail, setShowDetail] = useState(false);
  const actionRef = useRef();
  const [currentRow, setCurrentRow] = useState();
  const [selectedRowsState, setSelectedRows] = useState([]);

  const intl = useIntl();
  const columns = [
    {
      title: "参数名称",
      dataIndex: 'paramName',
      render: (dom, entity) => {
        return (
          <a
            onClick={() => {
              setCurrentRow(entity);
              setShowDetail(true);
            }}
          >
            {dom}
          </a>
        );
      },
    },
    {
      title: "参数值",
      dataIndex: 'paramValue',
      valueType: 'textarea',
      search: false,
    },
    {
      title: "备注",
      dataIndex: 'remark',
      valueType: 'textarea',
      search: false,
    },
    {
      title: <FormattedMessage id="pages.searchTable.titleOption" defaultMessage="Operating"/>,
      dataIndex: 'option',
      valueType: 'option',
      render: (_, record) => [
        <a
          key="config"
          onClick={() => {
            handleModalVisible(true);
            setCurrentRow(record);
          }}
        >
          修改
        </a>,
        <a
          key="delete"
          onClick={async () => {
            await handleRemove(record);
            actionRef.current?.reloadAndRest?.();
          }}
        >
          删除
        </a>,
      ],
    },
  ];
  return (
    <PageContainer>
      <ProTable

        actionRef={actionRef}
        rowKey="id"
        search={{
          labelWidth: 120,
        }}
        tableAlertRender={false}
        toolBarRender={() => [
          <Button
            type="primary"
            key="primary"
            onClick={() => {
              handleModalVisible(true);
            }}
          >
            <PlusOutlined/> <FormattedMessage id="pages.searchTable.new" defaultMessage="New"/>
          </Button>,
        ]}
        request={queryParamList}
        columns={columns}
        rowSelection={{
          onChange: (_, selectedRows) => {
            setSelectedRows(selectedRows);
          },
        }}
      />

      <ModalForm
        title={"配置参数"}
        width="400px"
        visible={createModalVisible}
        onVisibleChange={handleModalVisible}
        onFinish={async (value) => {
          const success = await handleAdd(currentRow || {},value);

          if (success) {
            handleModalVisible(false);

            if (actionRef.current) {
              actionRef.current.reload();
            }
          }
        }}
        initialValues={currentRow}
      >
        <ProFormText
          label={"参数名称"}
          rules={[
            {
              required: true,
              message: "请输入参数名称",
            },
          ]}
          placeholder={"请输入参数名称"}
          width="md"
          name="paramName"
        />
        <ProFormTextArea
          label={"参数值"}
          rules={[
            {
              required: true,
              message: "请输入参数值",
            },
          ]}
          placeholder={"请输入参数值"}
          width="md" name="paramValue"/>

        <ProFormTextArea
          label={"备注"}
          placeholder={"请输入备注"}
          width="md" name="remark"/>

      </ModalForm>

      <Drawer
        width={600}
        visible={showDetail}
        onClose={() => {
          setCurrentRow(undefined);
          setShowDetail(false);
        }}
        closable={false}
      >
        {currentRow?.paramName && (
          <ProDescriptions
            column={1}
            title={currentRow?.paramName}
            request={async () => ({
              data: currentRow || {},
            })}
            params={{
              id: currentRow?.paramName,
            }}
            columns={columns}
          />
        )}
      </Drawer>
    </PageContainer>
  );
};

export default TableList;
