1、部署应用test.war
2、使用curl命令验证 下载地址：https://curl.se/windows/dl-8.13.0_5/curl-8.13.0_5-win64-mingw.zip
curl -k -v --http3 https://localhost:8443/test/hello
3、上述命令执行后可以看到使用http3协议请求响应