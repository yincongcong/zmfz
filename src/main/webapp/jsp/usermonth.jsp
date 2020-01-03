<%@page contentType="text/html; UTF-8" isELIgnored="false" pageEncoding="UTF-8" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <script src="../boot/js/jquery-3.3.1.min.js"></script>
    <%--echarts核心js--%>
    <script src="../echarts/echarts.min.js"></script>
    <%--地图js--%>
    <script src="../echarts/china.js"></script>
    <script type="text/javascript" src="https://cdn.goeasy.io/goeasy-1.0.3.js"></script>

</head>
<body>
<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
<div id="main" style="width: 600px;height:400px;"></div>
<script type="text/javascript">
    var goEasy = new GoEasy({
        host: 'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
        appkey: "BS-172bf1a3efa1482d9364f61e2923bb62", //替换为您的应用appkey
    });
    $(function () {
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));

        // 指定图表的配置项和数据
        var option = {
            title: {
                text: 'ECharts 入门示例'
            },
            tooltip: {},
            legend: {
                data: ['用户量']
            },
            xAxis: {
                data: ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"]
            },
            yAxis: {},
            series: [{
                name: '用户量',
                type: 'line'
            }]
        };
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
        $.ajax({
            url: "${pageContext.request.contextPath}/echarts/getMonthUser",
            datatype: "json",
            success: function (data) {
                var name = [];
                var value = [];
                for (index in data) {
                    name.push(data[index].month);
                    value.push(data[index].count);
                }
                console.log(data);
                myChart.setOption({
                    xAxis: {
                        data: name
                    },
                    series: [
                        {
                            data: value
                        }
                    ]
                });

            }
        });
        goEasy.subscribe({
            channel: "month", //替换为您自己的channel
            onMessage: function (message) {
                /*alert("Channel:" + message.channel + " content:" + message.content);*/
                console.log(message.content);
                var parse = JSON.parse(message.content);
                console.log(parse);
                myChart.setOption({
                    series: [{
                        data: parse
                    }]
                })
            }
        });
    })

</script>

</body>
</html>