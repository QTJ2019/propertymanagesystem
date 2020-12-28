import Vue from 'vue'
import VueRouter, { RouteConfig } from 'vue-router'
import Home from '../views/Home.vue'
import User from '../views/User.vue'
import Login from '../components/Login.vue'
import Register from '../components/Register.vue'

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
    path:'/user',
    component: User
  },
  {
    path: '/login',
    component: Login
  },
  {
    path: '/register',
    component: Register
  }
]

const router = new VueRouter({
  mode: 'history',  //该模式下url没有#
  base: process.env.BASE_URL,
  routes
})

//将router对象传入到Vue实例
export default router
