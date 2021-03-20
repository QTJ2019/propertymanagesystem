module.exports = {
  // lintOnSave: false
  //配置跨域的问题
  devServer: {
    open: true,
    port: 8080,
    proxy: {
      '/system': {
        target: 'http://localhost:8089',  //需要跨域的目标url，遇到/system就会向target请求
                                        //如果不配置pathRewrite会导致请求带有system
        changeOrigin: true, //将基于名称的虚拟托管网站的选项，如果不配置，请求会报404
        pathRewrite: {
          '^/system': '/system'  //这里是重写路径，当/system是不存在请求路径时，就要将它写为空 这里/system必须写，否则cookie的path不对  
        }
      }
    }
  }
}
