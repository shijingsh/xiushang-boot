export default [
  {
    path: '/user',
    layout: false,
    routes: [
      {
        path: '/user',
        routes: [
          {
            name: 'login',
            path: '/user/login',
            component: './user/Login',
          },
          {
            name: 'register',
            path: '/user/register',
            component: './user/register',
          },
        ],
      },
      {
        component: './404',
      },
    ],
  },
  {
    path: '/welcome',
    name: 'welcome',
    icon: 'smile',
    component: './Welcome',
  },

  {
    name: 'notice.list',
    icon: 'table',
    path: '/notice/list',
    component: './NoticeList',
  },
  {
    name: 'param.list',
    icon: 'table',
    path: '/param/list',
    component: './ParamList',
  },
  {
    name: 'help.list',
    icon: 'table',
    path: '/help/list',
    component: './HelpList',
  },
  {
    name: 'help.help-edit',
    icon: 'table',
    path: '/help/help-edit',
    hideInMenu: true,
    component: './HelpList/Edit',
  },
  {
    name: 'client.list',
    icon: 'table',
    path: '/client/list',
    component: './ClientList',
  },
  {
    name: 'client.client-edit',
    icon: 'table',
    path: '/client/client-edit',
    hideInMenu: true,
    component: './ClientList/Edit',
  },
  {
    path: '/admin',
    name: 'admin',
    icon: 'crown',
    access: 'canAdmin',
    routes: [
      {
        path: '/admin',
        redirect: '/admin/user-list',
      },
      {
        path: '/admin/index',
        name: 'index',
        icon: 'smile',
        hideInMenu: true,
        component: './Admin',
      },
      {
        path: '/admin/user-list',
        name: 'user-list',
        icon: 'smile',
        component: './UserList',
      },
      {
        path: '/admin/user-edit',
        name: 'user-edit',
        hideInMenu: true,
        icon: 'smile',
        component: './UserList/Edit',
      },
      {
        path: '/admin/sms-list',
        name: 'sms-list',
        icon: 'smile',
        component: './SmsList',
      },
      {
        component: './404',
      },
    ],
  },
  {
    name: 'account',
    icon: 'user',
    path: '/account',
    routes: [
/*      {
        path: '/account',
        redirect: '/account/center',
      },
      {
        name: 'center',
        icon: 'smile',
        path: '/account/center',
        component: './account/center',
      },*/
      {
        path: '/account',
        redirect: '/account/settings',
      },
      {
        name: 'settings',
        icon: 'smile',
        path: '/account/settings',
        component: './account/settings',
      },
      {
        name: 'passwd',
        hideInMenu: true,
        icon: 'smile',
        path: '/account/passwd',
        component: './account/passwd',
      },
    ],
  },
  {
    path: '/',
    redirect: '/welcome',
  },
  {
    component: './404',
  },
];
