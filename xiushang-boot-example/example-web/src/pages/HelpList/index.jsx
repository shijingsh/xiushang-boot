import {PlusOutlined} from '@ant-design/icons';
import {Button, Drawer, message} from 'antd';
import React, {useRef, useState} from 'react';
import {FormattedMessage, history, useIntl} from 'umi';
import {PageContainer} from '@ant-design/pro-layout';
import ProTable from '@ant-design/pro-table';
import ProDescriptions from '@ant-design/pro-descriptions';
import {queryList, remove} from './service';

const  handleEdit = (id) => {
  history.push({
    pathname: '/help/help-edit',
    query: {
      id:id
    },
  });
};

const handleRemove = async (obj) => {
  const hide = message.loading('正在删除');
  if (!obj) return true;

  try {
    await remove(obj.id);
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
      title: "帮助标题",
      dataIndex: 'title',
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
      title: "排序值",
      dataIndex: 'displayOrder',
      search: false,
    },
    {
      title: "创建时间",
      dataIndex: 'createdDate',
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
            handleEdit(record.id);
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
              history.push({
                pathname: '/help/help-edit'
              });
            }}
          >
            <PlusOutlined/> <FormattedMessage id="pages.searchTable.new" defaultMessage="New"/>
          </Button>,
        ]}
        request={queryList}
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
        {currentRow?.title && (
          <ProDescriptions
            column={1}
            title={currentRow?.title}
            request={async () => ({
              data: currentRow || {},
            })}
            params={{
              id: currentRow?.title,
            }}
            columns={columns}
          />
        )}
      </Drawer>
    </PageContainer>
  );
};

export default TableList;
