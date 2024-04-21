module.exports = {
    devServer: {
        host:'0.0.0.0',
  	    port: 5173,
        proxy: {
            '/api': {
                target: 'http://localhost:8080', // 设置代理目标地址
                changeOrigin: true, // 开启跨域
                pathRewrite: {
                    '^/api': '', // 将请求的路径中的 '/api' 替换为空字符串
                },
            },
        },
    },
};