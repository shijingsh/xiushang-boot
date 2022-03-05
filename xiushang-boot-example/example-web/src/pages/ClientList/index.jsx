import {PlusOutlined} from '@ant-design/icons';
import {Button, Drawer} from 'antd';
import React, {useRef, useState} from 'react';
import {FormattedMessage, history, useIntl} from 'umi';
import {PageContainer} from '@ant-design/pro-layout';
import ProTable from '@ant-design/pro-table';
import {queryClientList} from './service';
import ProDescriptions from "@ant-design/pro-descriptions";

const  handleEdit = (id) => {
  history.push({
    pathname: '/client/client-edit',
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
            handleEdit(record.clientId);
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
                pathname: '/client/client-edit'
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

      <Drawer
        width={600}
        visible={showDetail}
        onClose={() => {
          setCurrentRow(undefined);
          setShowDetail(false);
        }}
        closable={false}
      >
        {currentRow?.clientId && (
          <ProDescriptions
            column={1}
            title={currentRow?.clientId}
            request={async () => ({
              data: currentRow || {},
            })}
            params={{
              id: currentRow?.clientId,
            }}
            columns={columns}
          />
        )}
      </Drawer>
    </PageContainer>
  );
};

export default ClientList;
