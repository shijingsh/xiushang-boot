import {PlusOutlined} from '@ant-design/icons';
import {Button, Drawer, message} from 'antd';
import React, {useRef, useState} from 'react';
import {FormattedMessage, useIntl} from 'umi';
import {FooterToolbar, PageContainer} from '@ant-design/pro-layout';
import ProTable from '@ant-design/pro-table';
import {ModalForm, ProFormDateTimePicker, ProFormText, ProFormTextArea} from '@ant-design/pro-form';
import ProDescriptions from '@ant-design/pro-descriptions';
import UpdateForm from './components/UpdateForm';
import {saveNotice, queryNoticeList, removeNotice} from './service';


const handleAdd = async (fields) => {
  const hide = message.loading('正在添加');

  try {
    await saveNotice({...fields});
    hide();
    message.success('添加成功！');
    return true;
  } catch (error) {
    hide();
    message.error('添加失败！');
    return false;
  }
};

const handleUpdate = async (currentRow,fields) => {
  const hide = message.loading('正在保存');
  if(currentRow && currentRow.id){
    fields.id = currentRow.id;
  }
  try {
    await saveNotice(fields);
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
    await removeNotice(obj.id);
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

  const [updateModalVisible, handleUpdateModalVisible] = useState(false);
  const [showDetail, setShowDetail] = useState(false);
  const actionRef = useRef();
  const [currentRow, setCurrentRow] = useState();
  const [selectedRowsState, setSelectedRows] = useState([]);

  const intl = useIntl();
  const columns = [
    {
      title: "公告名称",
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
      title: "公告内容",
      dataIndex: 'content',
      valueType: 'textarea',
      search: false,
    },

    {
      title: <FormattedMessage id="pages.searchTable.titleStatus" defaultMessage="Status"/>,
      dataIndex: 'status',
      hideInForm: true,
      search: false,
      valueEnum: {
        0: {
          text: "无效",
          status: 'Default',
        },
        1: {
          text: "有效",
          status: 'Processing',
        }
      },
    },
    {
      title: "有效期",
      sorter: true,
      search: false,
      dataIndex: 'validDate',
      valueType: 'dateTime',
      renderFormItem: (item, {defaultRender, ...rest}, form) => {
        return defaultRender(item);
      },
    },
    {
      title: <FormattedMessage id="pages.searchTable.titleOption" defaultMessage="Operating"/>,
      dataIndex: 'option',
      valueType: 'option',
      render: (_, record) => [
        <a
          key="config"
          onClick={() => {
            handleUpdateModalVisible(true);
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
        /*        headerTitle={intl.formatMessage({
                  id: 'pages.searchTable.title',
                  defaultMessage: 'Enquiry form',
                })}*/
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
        request={queryNoticeList}
        columns={columns}
        rowSelection={{
          onChange: (_, selectedRows) => {
            setSelectedRows(selectedRows);
          },
        }}
      />
      {/*      {selectedRowsState?.length > 0 && (
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
        </FooterToolbar>
      )}*/}
      <ModalForm
        title={"添加公告"}
        width="400px"
        visible={createModalVisible}
        onVisibleChange={handleModalVisible}
        onFinish={async (value) => {
          const success = await handleAdd(value);

          if (success) {
            handleModalVisible(false);

            if (actionRef.current) {
              actionRef.current.reload();
            }
          }
        }}
      >
        <ProFormText
          label={"公告标题"}
          rules={[
            {
              required: true,
              message: "请输入公告标题",
            },
          ]}
          placeholder={"请输入公告标题"}
          width="md"
          name="title"
        />
        <ProFormTextArea
          label={"公告内容"}
          rules={[
            {
              required: true,
              message: "请输入公告内容",
            },
          ]}
          placeholder={"请输入公告内容"}
          width="md" name="content"/>

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
      </ModalForm>
      <UpdateForm
        onSubmit={async (value) => {
          const success = await handleUpdate(currentRow || {},value);

          if (success) {
            handleUpdateModalVisible(false);
            setCurrentRow(undefined);

            if (actionRef.current) {
              actionRef.current.reload();
            }
          }
        }}
        onCancel={() => {
          handleUpdateModalVisible(false);

          if (!showDetail) {
            setCurrentRow(undefined);
          }
        }}
        updateModalVisible={updateModalVisible}
        values={currentRow || {}}
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
