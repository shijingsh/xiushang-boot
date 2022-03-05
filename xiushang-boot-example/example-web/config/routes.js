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
    name: 'list.table-list',
    icon: 'table',
    path: '/list',
    component: './TableList',
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
