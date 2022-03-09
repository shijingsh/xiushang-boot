import {PlusOutlined} from '@ant-design/icons';
import {Button, Drawer, message} from 'antd';
import React, {useRef, useState} from 'react';
import {FormattedMessage, useIntl} from 'umi';
import {PageContainer} from '@ant-design/pro-layout';
import ProTable from '@ant-design/pro-table';
import {ModalForm, ProFormText, ProFormTextArea} from '@ant-design/pro-form';
import ProDescriptions from '@ant-design/pro-descriptions';
import {queryList} from './service';


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
      title: "短信接收号码",
      dataIndex: 'mobile',
    },
    {
      title: "模板参数",
      dataIndex: 'templateParam',
      search: false,
    },
    {
      title: "模板代码",
      dataIndex: 'templateCode',
      search: false,
    },
    {
      title: "短信发送时间",
      dataIndex: 'sendTime',
      search: false,
    },
    {
      title: "短信发送结果",
      dataIndex: 'message',
      search: false,
    },
    {
      title: "系统短信",
      dataIndex: 'systemFlag',
      hideInForm: true,
      search: false,
      valueEnum: {
        0: {
          text: "是",
        },
        1: {
          text: "否",
        }
      },
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
        toolBarRender={false}
        request={queryList}
        columns={columns}
        rowSelection={{
          onChange: (_, selectedRows) => {
            setSelectedRows(selectedRows);
          },
        }}
      />

    </PageContainer>
  );
};

export default TableList;
