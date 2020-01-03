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
    $(function () {
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));

        // 指定图表的配置项和数据
        var option = {
            title: {
                text: '用户地理分布图',
                left: 'center'
            },
            tooltip: {
                trigger: 'item'
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data: ['用户']
            },
            visualMap: {
                min: 0,
                max: 2500,
                left: 'left',
                top: 'bottom',
                text: ['高', '低'],           // 文本，默认为数值文本
                calculable: true
            },
            toolbox: {
                show: true,
                orient: 'vertical',
                left: 'right',
                top: 'center',
                feature: {
                    mark: {show: true},
                    dataView: {show: true, readOnly: false},
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            series: [
                {
                    name: '用户',
                    type: 'map',
                    mapType: 'china',
                    roam: false,
                    label: {
                        normal: {
                            show: false
                        },
                        emphasis: {
                            show: true
                        }
                    }
                },
            ]
        };
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
        $.ajax({
            url: "${pageContext.request.contextPath}/echarts/getAddressUser",
            datatype: "json",
            success: function (data) {
                console.log(data);
                myChart.setOption({
                    series: [
                        {
                            data: data
                        }
                    ]
                });
            }
        });
    })

</script>

</body>
</html>