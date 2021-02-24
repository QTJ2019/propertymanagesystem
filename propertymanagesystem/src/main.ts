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
import JSEncrypt from 'jsencrypt'
const Base64 = require('js-base64').Base64;
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

//该方法用于对密码加密
Vue.prototype.$getRsaCode = function(password: string,publicKey: string){
    const encryptStr = new JSEncrypt({});
    //console.log('publicKey '+publicKey);
    encryptStr.setPublicKey(publicKey);
    //console.log('publickey decode:'+Base64.decode(publicKey));
    const newPassWord = encryptStr.encrypt(password).toString();
   // console.log('加密:'+encryptStr.encrypt(password));
    //使用base64进行编码
    return newPassWord;

}



//将Message挂载在vue原型上
Vue.prototype.$message = Message




new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
