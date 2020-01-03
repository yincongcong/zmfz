<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<script type="text/javascript">
    $(function () {
        $("#list").jqGrid({
            url: "${pageContext.request.contextPath}/banner/queryPage",//默认访问路径
            editurl: "${pageContext.request.contextPath}/banner/editBanner",//表格增删改查访问路径*/
            styleUI: "Bootstrap",//表格的样式
            datatype: "json",//接收的格式
            autowidth: true,//自动适应父容器
            toolbar: [true, "top"],//导航工具栏
            caption: "大图轮播",//名称
            pager: "#pager",//工具栏
            rowNum: 3,//每页展示的条数
            rowList: [3, 8, 10],
            height: "300px",
            multiselect: true,
            multiboxonly: true,
            colNames: [
                "ID", "标题", "图片", "上传时间", "状态"
            ],
            colModel: [
                {name: "id"},
                {name: "title", editable: true},
                {
                    name: "img", editable: true, edittype: 'file',
                    formatter: function (cellvalue, options, rowObject) {
                        return "<img style='width:100%;height:60px' src='${pageContext.request.contextPath}/upload/" + cellvalue + "'/>";
                    }
                },
                {name: "create_date", editable: true, edittype: 'date'},
                {
                    name: "status", editable: true, edittype: "select", editoptions: {
                        value: "显示:显示;不显示:不显示"
                    }
                }
            ],
        }).navGrid("#pager",
            {
                edit: true,
                add: true,
                del: true,
                search: false,
                refresh: false,
                view: false,
                position: "left",
                cloneToTop: false,
                addtext: 'Add',
                edittext: 'Edit',
                deltext: 'Delete',
                refreshtext: 'Reload',
            },
            {
                closeAfterEdit: true,
                afterSubmit: function (response) {
                    var id = response.responseJSON.bannerId;
                    var status = response.responseJSON.status;
                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/banner/upload",
                        fileElementId: 'img',
                        data: {id: id},
                        success: function () {
                            $("#list").trigger("reloadGrid");
                            if (status == 200) {
                                $("#msgDiv").text("修改成功").show();
                            } else {
                                $("#msgDiv").text("修改失败").show();
                            }
                            setTimeout(function () {
                                $("#msgDiv").hide();
                            }, 3000)
                        }
                    });
                    return response;
                }
            },
            // options for the Add Dialog
            {
                closeAfterAdd: true,
                recreateForm: true,

                afterSubmit: function (response) {
                    var id = response.responseJSON.bannerId;
                    var status = response.responseJSON.status;
                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/banner/upload",
                        fileElementId: 'img',
                        data: {id: id},
                        success: function () {
                            $("#list").trigger("reloadGrid");
                            if (status == 200) {
                                $("#msgDiv").text("添加成功").show();
                            } else {
                                $("#msgDiv").text("添加失败").show();
                            }
                            setTimeout(function () {
                                $("#msgDiv").hide();
                            }, 3000)
                        }
                    });
                    return response;
                }

            }
        )
        $("#t_list").html("<a href='${pageContext.request.contextPath}/poi/poiOut' id='lunbo'>大图轮播导出</a>");
    })
    /*$("#lunbo").click(function () {
        alert();
        $.ajax({
            url:"${pageContext.request.contextPath}/poi/poiOut",
            datatype:"json",
            success:function () {

            }
        })
    })*/
</script>
<div class="container-fluid">
    <%--一行--%>
    <div class="row">
        <%--用户管理信息--%>
        <div class="col-sm-12" id="gl">
            <ul class="nav nav-tabs">
                <li role="presentation" class="active"><a href="#aaa">大图轮管理</a></li>
            </ul>

            <!--
                客户信息展示相关
            -->
            <div class="panel" id="aaa">
                <div class="panel-heading">
                    <div class="panel-body">
                        <table class="table table-hover table-bordered table-striped" id="list">
                        </table>
                        <div id="pager" style="height: 50px"></div>
                        <div class="alert alert-success" style="display:none" id="msgDiv">
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>