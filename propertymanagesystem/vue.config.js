module.exports = {
  // lintOnSave: false
  //配置跨域的问题
  devServer: {
    open: true,
    port: 8080,
    proxy: {
      '/system': {
        target: 'http://localhost:8089',
        changeOrigin: true,
        pathRewrite: {
          '^/system': '/system'  //这里/system必须写，否则cookie的path不对
        }
      }
    }
  }
}
