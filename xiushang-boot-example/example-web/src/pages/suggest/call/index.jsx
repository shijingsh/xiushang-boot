import {PlusOutlined} from '@ant-design/icons';
import {Button, Drawer, message} from 'antd';
import React, {useRef, useState} from 'react';
import {FormattedMessage, history, useIntl} from 'umi';
import {PageContainer} from '@ant-design/pro-layout';
import ProTable from '@ant-design/pro-table';
import ProDescriptions from '@ant-design/pro-descriptions';
import {queryListType, remove,saveProcessing,saveProcess} from '../service';

const  handleProcessing = async(obj) => {
  const hide = message.loading('正在保存');
  if (!obj) return true;

  try {
    await saveProcessing(obj.id);
    hide();
    message.success('保存成功！');
    return true;
  } catch (error) {
    hide();
    message.error('保存失败!');
    return false;
  }
};


const  handleProcess = (obj) => {
  history.push({
    pathname: '/suggest/edit',
    query: {
      id:obj.id
    },
  });
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
      title: "姓名",
      dataIndex: 'name',
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
      title: "联系手机",
      dataIndex: 'mobile',
      copyable: true,
    },
    {
      title: "联系邮箱",
      dataIndex: 'email',
      copyable: true,
      search: false,
    },
/*    {
      title: "意见内容",
      dataIndex: 'content',
      search: false,
      ellipsis: true,
      copyable: true,
    },*/
    {
      title: "状态",
      dataIndex: 'status',
      hideInForm: true,
      valueEnum: {
        0: {
          text: "未处理",
        },
        1: {
          text: "处理中",
        },
        2: {
          text: "已处理",
        }
      },
    },
    {
      title: <FormattedMessage id="pages.searchTable.titleOption" defaultMessage="Operating"/>,
      dataIndex: 'option',
      valueType: 'option',
      render: (_, record) => {
        if(record.status ===2){
          return [];
        }
        if(record.status ===1){
          return [
            <a
              key="delete"
              onClick={ () => {
                handleProcess(record);
              }}
            >
              标注已处理
            </a>,
          ]
        }

        return [
          <a
            key="config"
            onClick={async() => {
              await handleProcessing(record);
              actionRef.current?.reloadAndRest?.();
            }}
          >
            标注处理中
          </a>,
          <a
            key="delete"
            onClick={ () => {
               handleProcess(record);
            }}
          >
            标注已处理
          </a>,
        ]
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
        toolBarRender={() => [
          <Button
            type="primary"
            key="primary"
            onClick={() => {
              history.push({
                pathname: '/help/help-edit'
              });
            }}
          >
            <PlusOutlined/> <FormattedMessage id="pages.searchTable.new" defaultMessage="New"/>
          </Button>,
        ]}
        request={queryListType}
        columns={columns}
        rowSelection={{
          onChange: (_, selectedRows) => {
            setSelectedRows(selectedRows);
          },
        }}
      />


      <Drawer
        width={600}
        visible={showDetail}
        onClose={() => {
          setCurrentRow(undefined);
          setShowDetail(false);
        }}
        closable={false}
      >
        {currentRow?.id && (
          <ProDescriptions
            column={1}
            title={currentRow?.name}
            request={async () => ({
              data: currentRow || {},
            })}
            params={{
              id: currentRow?.id,
            }}
            columns={columns}
          />
        )}
      </Drawer>
    </PageContainer>
  );
};

export default TableList;
