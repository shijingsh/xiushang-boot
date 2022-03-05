import { PlusOutlined } from '@ant-design/icons';
import { Button, message, Input, Drawer } from 'antd';
import React, { useState, useRef } from 'react';
import {useIntl, FormattedMessage, history} from 'umi';
import { PageContainer, FooterToolbar } from '@ant-design/pro-layout';
import ProTable from '@ant-design/pro-table';
import { queryUserList,enableOrDisable } from './service';
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
    id: id,
  });
};


const UserList = () => {

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
      title: "用户名称",
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
      title: "手机",
      dataIndex: 'mobile',
      valueType: 'textarea',
    },
    {
      title: "状态",
      dataIndex: 'deleted',
      hideInForm: true,
      valueEnum: {
        0: {
          text: "有效",
          status: 'Processing',
        },
        1: {
          text: "无效",
          status: 'Default',
        }
      },
    },
    {
      title: "最后登陆时间",
      sorter: true,
      search: false,
      dataIndex: 'lastLoginDate',
      valueType: 'dateTime',
      renderFormItem: (item, { defaultRender}) => {
        return defaultRender(item);
      },
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
        </a>,
        <a
          key="config"
          onClick={async () => {
            await handleStatus(record);
            actionRef.current?.reloadAndRest?.();
          }}
        >
          {
            record.deleted==0?"禁用":"启用"
          }

        </a>,
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
        request={queryUserList}
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

export default UserList;
