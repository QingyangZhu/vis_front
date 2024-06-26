# 大数据可视化启动文档

## 后台部分

1.使用提供的项目server文件夹 使用cmd切换到其路径之下

2.npm install 下载依赖

3.node index.js 启动项目

## 前台部分

1.使用提供的项目myapp文件夹 使用cmd切换到其路径之下

2.npm install 下载依赖

3.修改flexible配置

在node_module/lib-flexible/flexible.js中修改代码如下

```
       // 修改原始的
        // if (width / dpr > 540) {
        //     width = 540 * dpr;
        // }
        // var rem = width / 10;

        // 修改成为
          // 最小400px，最大适配2560px
        if (width / dpr < 400) {
            width = 400 * dpr;
        } else if (width / dpr > 2560) {
            width = 2560 * dpr;
        }
        // 设置成24等份，设计稿时1920px的，这样1rem就是80px （1920/24=80）方便使用
        var rem = width / 24;
```

4.启动项目 npm run start