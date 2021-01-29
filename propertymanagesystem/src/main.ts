import Vue from 'vue'
import './plugins/axios'
import App from './App.vue'
import router from './router'
import store from './store'
import './plugins/element.js'
import ElementUI, { Message } from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import './assets/css/global.css' 
import axios from 'axios'

Vue.use(ElementUI);

Vue.config.productionTip = false

axios.defaults.withCredentials = true

//配置axios的响应拦截器，遇到401就会重定向到登录界面
//设置这个拦截器主要是为了没登录，访问后端接口能够回到登录页面
axios.interceptors.response.use(res =>{
  return res;
},err => {
  console.log(err);
  if (err.response.status === 401){
    Message.error("没有权限");
    router.push("/login");
     
  }
  
})

Vue.prototype.$http = axios


//将Message挂载在vue原型上
Vue.prototype.$message = Message




new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
