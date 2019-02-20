## 工程搭建
###工程框架

前端框架：

使用 `react`框架开发
+ 工程根目录：src/main/frontend
+ 工程初始化方法：https://reactjs.org/docs/create-a-new-react-app.html
+ 在*IDEA*中创建`react`项目：https://www.jetbrains.com/help/idea/react.html
+ 修改`package.json`，在`scripts`中增加：
  + "postbuild": "mkdir -p ../resources/public/ && cp -r build/* ../resources/public/"