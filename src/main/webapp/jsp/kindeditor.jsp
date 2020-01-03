<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; UTF-8" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>富文本编译器</title>
    <script charset="utf-8" src="${pageContext.request.contextPath}/kindeditor/kindeditor-all-min.js"></script>
    <script charset="utf-8" src="${pageContext.request.contextPath}/kindeditor/lang/zh-CN.js"></script>
    <script>
        KindEditor.ready(function (K) {
            K.create("#editor_id", {
                width: 400,//文本编译器的宽度
                height: 500,//文本编译器的高度
                minHeight: 400,//文本编译器的最小宽度
                resizeType: 0,//0代表不能拖动
                allowFileManager: true,//是否展示图片空间
                filePostName: 'img',//上传是后台接收的名字
                uploadJson: '${pageContext.request.contextPath}/article/articleImg',//上传后台的路径
                fileManagerJson: '${pageContext.request.contextPath}/article/articleImg' //指定浏览远程图片的服务器端程序
            });
        })
    </script>
</head>
<body>
<textarea id="editor_id" name="content" style="width:700px;height:300px;">
    </textarea>
</body>
</html>