import { SettingDrawer } from '@ant-design/pro-layout';
import { PageLoading } from '@ant-design/pro-layout';
import { history, Link } from 'umi';
import RightContent from '@/components/RightContent';
import Footer from '@/components/Footer';
import { currentUser as queryCurrentUser } from './services/ant-design-pro/api';
import { BookOutlined, LinkOutlined } from '@ant-design/icons';
import defaultSettings from '../config/defaultSettings';
const isDev = process.env.NODE_ENV === 'development';
const loginPath = '/user/login';
import * as CommonUtils from '@/utils/CommonUtils';

import token from '@/utils/token';
import { notification } from 'antd';
/** 获取用户信息比较慢的时候会展示一个 loading */

export const initialStateConfig = {
  loading: <PageLoading />,
};
/**
 * @see  https://umijs.org/zh-CN/plugins/plugin-initial-state
 * */

export async function getInitialState() {
  const fetchUserInfo = async () => {
    try {
      const msg = await queryCurrentUser();

      return msg.data;
    } catch (error) {
      history.push(loginPath);
    }

    return undefined;
  }; // 如果是登录页面，不执行

  if (history.location.pathname !== loginPath) {
    const currentUser = await fetchUserInfo();
    return {
      fetchUserInfo,
      currentUser,
      settings: defaultSettings,
    };
  }

  return {
    fetchUserInfo,
    settings: defaultSettings,
  };
} // ProLayout 支持的api https://procomponents.ant.design/components/layout

export const layout = ({ initialState, setInitialState }) => {
  return {
    rightContentRender: () => <RightContent />,
    disableContentMargin: false,
    waterMarkProps: {
      content: initialState?.currentUser?.name,
    },
    footerRender: () => <Footer />,
    onPageChange: () => {
      const { location } = history; // 如果没有登录，重定向到 login

      if (!initialState?.currentUser && location.pathname !== loginPath) {
        history.push(loginPath);
      }
    },
    links: isDev
      ? [
          <Link to="/umi/plugin/openapi" target="_blank">
            <LinkOutlined />
            <span>OpenAPI 文档</span>
          </Link>,
          <Link to="/~docs">
            <BookOutlined />
            <span>业务组件文档</span>
          </Link>,
        ]
      : [],
    menuHeaderRender: undefined,
    // 自定义 403 页面
    // unAccessible: <div>unAccessible</div>,
    // 增加一个 loading 的状态
    childrenRender: (children, props) => {
      // if (initialState?.loading) return <PageLoading />;
      return (
        <>
          {children}
          {!props.location?.pathname?.includes('/login') && (
            <SettingDrawer
              enableDarkTheme
              settings={initialState?.settings}
              onSettingChange={(settings) => {
                setInitialState((preInitialState) => ({ ...preInitialState, settings }));
              }}
            />
          )}
        </>
      );
    },
    ...initialState?.settings,
  };
};


const codeMessage = {
  200: '服务器成功返回请求的数据。',
  201: '新建或修改数据成功。',
  202: '一个请求已经进入后台排队（异步任务）。',
  204: '删除数据成功。',
  400: '发出的请求有错误，服务器没有进行新建或修改数据的操作。',
  401: '用户没有权限（令牌、用户名、密码错误）。',
  403: '用户得到授权，但是访问是被禁止的。',
  404: '发出的请求针对的是不存在的记录，服务器没有进行操作。',
  405: '请求方法不被允许。',
  406: '请求的格式不可得。',
  410: '请求的资源被永久删除，且不会再得到的。',
  422: '当创建一个对象时，发生一个验证错误。',
  500: '服务器发生错误，请检查服务器。',
  502: '网关错误。',
  503: '服务不可用，服务器暂时过载或维护。',
  504: '网关超时。',
};

// 全局请求
const requestInterceptor = (url, options) => {
  let fullUrl = CommonUtils.baseUrl + url;
  options.headers = {
    ...options.headers,
    Authorization: token.get(),
  };
  return {
    url: fullUrl,
    options: {
      ...options,
    },
  };
};

// 全局相应拦截
const responseInterceptor = async (response, options) => {
  console.log("=====================================");
  const data = await response.clone().json();
  console.log(data);
  if (data.errorCode !== undefined && data.errorCode !== 0) {
    // 界面报错处理
    if (data.errorCode === 401 || data.errorCode === 403) {
      notification.error({
        message: '未登录或登录已过期，请重新登录。',
      });
      token.delete();
      if (!history) return;

      history.push(loginPath);

      return;
    }

    if(data.errorText){
      notification.error({
        message: data.errorText,
      });
    }else {
      notification.error({
        message: "请求发生错误",
      });
    }
    return;
  }
  return response;
};

const errorHandler = (error) => {
  const { response } = error;

  if (response && response.status) {
    const { status, url } = response;
    const errorText = codeMessage[response.status] || response.statusText;
    console.log(`请求错误 ${status}: ${url}`);


    if (status === 401) {
      notification.error({
        message: '未登录或登录已过期，请重新登录。',
      });
      history.push(loginPath);
      return;
    }
    notification.error({
      message: `请求错误 ${status}: ${url}`,
      description: errorText,
    });
    // environment should not be used
    if (status === 403) {
      history.push('/exception/403');
      return;
    }
    if (status <= 504 && status >= 500) {
      history.push('/exception/500');
      return;
    }
    if (status >= 404 && status < 422) {
      history.push('/exception/404');
      return;
    }
  }



  if (!response) {
    // notification.error({
    //   description: '您的网络发生异常，无法连接服务器',
    //   message: '网络异常',
    // });
  }
  throw error;
};

export const request = {
  timeout: 1000,
  //自定义端口规范
  errorConfig: {
    adaptor: res => {
      return {
        success: res.errorCode ==0,
        data:res.data,
        errorCode:res.errorCode,
        errorMessage: res.errorText,
      };
    },
  },
  middlewares: [],
  // 异常处理
  errorHandler,
  //请求拦截器
  requestInterceptors: [requestInterceptor],
  //响应拦截器
  responseInterceptors: [responseInterceptor],
};


