###可跨域单点登录系统:

#####操作步骤:</br>
    1.配置域名</br>
        127.0.0.1 www.sso.com www.a.com www.b.com</br>
    2.demo步骤</br>
        登录:</br>
            a) 访问http://www.a.com:8087/resource/test (受保护资源则跳转到登录页面)</br>
            b) 登录后跳回  resource/test 地址</br>
            c) 访问http://www.b.com:8087/resource/test 直接可访问受保护资源</br>
        登出:</br>
            a) 访问http://www.a.com:8087/index/logout  退后会自动跳转登录页</br>
            b) 访问http://www.b.com:8087/resource/test 因已经退出,跳转登录页</br>

#####扩展自定义登录页</br>
    仿照sso登录页表单即可</br>
#####自定义退出下次登录后跳转页面</br>
    http://www.a.com:8087/index/logout 此方法redirect页面增加loginSuccessUrl地址</br>
#####自定义退出后跳转某个应用登录页面</br>
    http://www.a.com:8087/index/logout 此方法redirect页面增加callback地址</br>
#####集群</br>
    修改sky-sso-client 存储器  ClientSessionStorage</br>
#####原理</br>
    仿照CAS登录流程</br>