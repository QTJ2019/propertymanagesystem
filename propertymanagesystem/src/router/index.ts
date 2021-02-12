import Vue from 'vue'
import VueRouter, { RouteConfig } from 'vue-router'
import User from '../views/User.vue'
import Login from '../components/Login.vue'
import Register from '../components/Register.vue'
import Test from '../components/Test.vue'
import Home from '../components/Home.vue'
import Welcome from '../components/Welcome.vue'
import UserInformation from '../components/baseinformation/UserInformation.vue'
import HouseInformation from '../components/baseinformation/HouseInformation.vue'
import UserHouseInformation from '../components/baseinformation/UserHouseInformation.vue'
import ComplaintService from '../components/complaintandadvise/ComplaintService.vue'
import ComplaintHandleService from '../components/complaintandadvise/ComplaintHandleService.vue'

 Vue.use(VueRouter)  //安装插件 
  //配置映射关系
const routes: Array<RouteConfig> = [
  {
    path: '/',
    redirect: "/login"
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  },
  {
    path:'/home',
    component: Home,
    meta: {
      title: "首页"
    },
    children:[
      {
        path:'',
        redirect: "welcome",
        meta: {
          title: "欢迎"
        }
      },
      {
        path:'welcome',
        component: Welcome
      },
      {
        path:"/userinformation",
        component: UserInformation,
        meta: {
          title: "个人信息维护"
        }
      },
      {
        path:"/houseinformation",
        component: HouseInformation,
        meta: {
          title: "房屋信息维护"
        }
      },
      {
        path:"/userhouseinformation",
        component: UserHouseInformation,
        meta: {
          title: "居民信息查询"
        }
      },
      {
        path:"/complaintservice",
        component: ComplaintService,
        meta: {
          title: "投诉服务"
        }
      },
      {
        path:"/complainthandleservice",
        component: ComplaintHandleService,
        meta: {
          title: "投诉处理"
        }
      }
    ]
  },
  {
    path: '/login',
    component: Login,
    meta: {
      title: "登录"
    }
  },
  {
    path: '/register',
    component: Register,
    meta: {
      title: "注册"
    }
  },
  {
    path: '/test',
    component: Test
  }
]

const router = new VueRouter({
  mode: 'history',  //该模式下url没有#
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to,from,next) =>{
  //设置标题
  console.log(to);
  if(to.meta.title !== undefined){
    document.title = to.meta.title;
  }else{
    document.title = to.matched[0].meta.title;
  }
  
  
  if (to.path === "/login" || to.path === "/register") {
    return next();
  }
  const jsessionid = window.sessionStorage.getItem("jsessionid");
  if(!jsessionid){
    return next('/login');
  } 
  next();
})

//将router对象传入到Vue实例
export default router
