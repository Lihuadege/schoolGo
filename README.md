# schoolGo
毕业设计，开始于2021年1月8日。

项目如果要运行，请先将sql文件导入到数据库里面。
然后使用idea导入项目，修改位于front和background里面src->main->resource下面的application.properties文件：
修改里面的数据库配置和Redis配置。
前台微信小程序运行，请在导入项目之后，务必先运行后台服务器，然后在详情里勾选使用npm模块和不校验合法域名（因为微信小程序上线之后必须使用https加密域名才可以）。如果还不能运行，请参考微信小程序开发文档构建npm模块。
微信小程序获取用户信息需要开发者的appid和secretid，这个需要在front/src/main/com/li/schoolGo/controller/LoginController中的方法里添加这两个参数，具体参加微信开发者文档。












停了半个月，28号再来做发现当初的架构搭好以后持久层连不上数据库，琢磨几天重构

重构完成，自定义拦截器，重定向页面时总是把静态资源当成Controller进行mapping映射，百般寻求无果，
最后通过降级SpringBoot版本到2.0以下，发现成功了。开心

基于此，在2月3号完成登录功能制作，但是测试功能Redis不知道为什么连不上，等待明天解决

从头来真的难受，各种jar包版本冲突搞得头秃，也没有什么准确的报错提示，有没有什么工具能够解决这个问题啊！

简单测试了一下，用本机的虚拟机的ip地址能够正确访问Redis，但是换成我的阿里云服务器的Redis就不行了。
啊，竟然忘了Redis安装默认拒绝非本机ip地址访问，配置重启成功解决



新增了前台用户功能，使用的是微信小程序，小程序所在目录就是本项目根目录的schoolGo文件夹，小程序中，为了避免项目可能造成的不稳定性，@vant组件和weui里面并没有精简，node_modules文件夹删除不影响项目运行，但是最好不要删除。至于怎么使用@vant组件，请参考小程序官方文档使用第三方组件说明。



项目后台上传图片，保存的目录是项目编译后之后存放的静态目录images文件夹下，项目附带的只是示例图片
