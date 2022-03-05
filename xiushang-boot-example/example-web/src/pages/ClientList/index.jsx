import { PlusOutlined } from '@ant-design/icons';
import { Button, message, Input, Drawer } from 'antd';
import React, { useState, useRef } from 'react';
import {useIntl, FormattedMessage, history} from 'umi';
import { PageContainer, FooterToolbar } from '@ant-design/pro-layout';
import ProTable from '@ant-design/pro-table';
import { queryClientList,enableOrDisable } from './service';
import ProDescriptions from "@ant-design/pro-descriptions";

const handleRemove = async (selectedRows) => {
  const hide = message.loading('正在删除');
  if (!selectedRows) return true;

  try {
    /*await removeRule({
      key: selectedRows.map((row) => row.key),
    });*/
    hide();
    message.success('删除成功！');
    return true;
  } catch (error) {
    hide();
    message.error('删除失败！');
    return false;
  }
};

const handleStatus = async (obj) => {
  const hide = message.loading('正在修改');
  if (!obj) return true;

  try {
    await enableOrDisable(obj.id);
    hide();
    message.success('操作成功！');
    return true;
  } catch (error) {
    hide();
    message.error('操作失败！');
    return false;
  }
};

const  handleEdit = (id) => {
  history.push({
    pathname: '/admin/user-edit',
    query: {
      id:id
    },
  });
};


const ClientList = () => {

  const [showDetail, setShowDetail] = useState(false);
  const actionRef = useRef();
  const [currentRow, setCurrentRow] = useState();
  const [selectedRowsState, setSelectedRows] = useState([]);
  /**
   国际化配置
  */

  const intl = useIntl();
  const columns = [
    {
      title: "客户端ID",
      dataIndex: 'clientId',

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
      title: "客户端名称",
      search: false,
      dataIndex: 'clientName',
    },
    {
      title: "客户端类型",
      dataIndex: 'clientType',
      hideInForm: true,
      search: false,
      valueEnum: {
        'CLIENT_TYPE_WX_MINI_APP': {
          text: "小程序",
        },
        'CLIENT_TYPE_WEB': {
          text: "WEB",
        },
        'CLIENT_TYPE_APP': {
          text: "APP",
        }
      },
    },
    {
      title: "认证令牌时效",
      search: false,
      dataIndex: 'accessTokenValidity',
    },
    {
      title: "刷新令牌时效",
      search: false,
      dataIndex: 'refreshTokenValidity',
    },
    {
      title: "回调地址",
      search: false,
      dataIndex: 'webServerRedirectUri',
    },
    {
      title: "操作",
      dataIndex: 'option',
      valueType: 'option',
      render: (_, record) => [
        <a
          key="editable"
          onClick={() => {
            handleEdit(record.id);
          }}
        >
          编辑
        </a>
      ],
    },
  ];
  return (
    <PageContainer>
      <ProTable
        actionRef={actionRef}
        rowKey="id"
        /*search={false}*/
        search={{
          labelWidth: 120,
        }}
        /*options={false}*/
        /*toolBarRender={false}*/
        tableAlertRender={false}
        toolBarRender={() => [
          <Button
            type="primary"
            key="primary"
            onClick={() => {

              history.push({
                pathname: '/admin/user-edit'
              });
            }}
          >
            <PlusOutlined /> <FormattedMessage id="pages.searchTable.new" defaultMessage="New" />
          </Button>,
        ]}
        request={queryClientList}
        columns={columns}
        rowSelection={{
          onChange: (_, selectedRows) => {
            setSelectedRows(selectedRows);
          },
        }}
      />
{/*
      {selectedRowsState?.length > 0 && (
        <FooterToolbar
          extra={
            <div>
              <FormattedMessage id="pages.searchTable.chosen" defaultMessage="Chosen" />{' '}
              <a
                style={{
                  fontWeight: 600,
                }}
              >
                {selectedRowsState.length}
              </a>{' '}
            </div>
          }
        >
          <Button
            onClick={async () => {
              await handleRemove(selectedRowsState);
              setSelectedRows([]);
              actionRef.current?.reloadAndRest?.();
            }}
          >
            <FormattedMessage
              id="pages.searchTable.batchDeletion"
              defaultMessage="Batch deletion"
            />
          </Button>
          <Button type="primary">
            <FormattedMessage
              id="pages.searchTable.batchApproval"
              defaultMessage="Batch approval"
            />
          </Button>
        </FooterToolbar>
      )}
*/}

      <Drawer
        width={600}
        visible={showDetail}
        onClose={() => {
          setCurrentRow(undefined);
          setShowDetail(false);
        }}
        closable={false}
      >
        {currentRow?.name && (
          <ProDescriptions
            column={2}
            title={currentRow?.name}
            request={async () => ({
              data: currentRow || {},
            })}
            params={{
              id: currentRow?.name,
            }}
            columns={columns}
          />
        )}
      </Drawer>
    </PageContainer>
  );
};

export default ClientList;
