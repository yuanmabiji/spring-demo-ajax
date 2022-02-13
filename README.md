### spring-demo-ajax_
这个module主要展示各种ajax的使用，访问地址：127.0.0.1:8080

### spring-demo-cookie
这个module主要展示各种ajax及form表单等请求服务端，服务端设置cookie的demo，访问地址：127.0.0.1:8081/cookie  
访问地址：127.0.0.1:8081/cookie
**注意：** localhost不能设置cookie；以及在filter中不断更新同名cookie的值，如果jwt需要更新的话则会用到该功能。

### spring-demo-sondomain1和spring-demo-sondomain2
这两个module主要主要用来验证两个项目设置父域名，此时可以浏览器请求、ajax请求等可以共用父域的cookie；
此外spring-demo-sondomain2还有服务端删除cookie和浏览器Js删除cookie的功能。

**注意：** 浏览器Js不能增删改查跨域cookie的值。比如在a.bb.com的调试页面（F12）不能增删改查c.bb.com这个域名的cookie

使用：
1. Host文件设置：127.0.0.1 demo.smart-sso.com 和 127.0.0.1 demo2.smart-sso.com
2. 分别访问http://demo.smart-sso.com:8082/cookie和http://demo2.smart-sso.com:8083/cookie

TODO form表单测试跨域还未做