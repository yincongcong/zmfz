<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<script type="text/javascript">
    $(function () {
        $("#list").jqGrid({
            url: "${pageContext.request.contextPath}/album/queryPage",//默认访问路径
            editurl: "${pageContext.request.contextPath}/album/editAlbum",//表格增删改查访问路径*/
            styleUI: "Bootstrap",//表格的样式
            datatype: "json",//接收的格式
            autowidth: true,//自动适应父容器
            toolbar: [true, "top"],//导航工具栏
            caption: "专辑详细信息",//名称
            pager: "#pager",//工具栏
            rowNum: 3,//每页展示的条数
            rowList: [3, 8, 10],
            height: "300px",
            multiselect: true,
            multiboxonly: true,
            colNames: [
                "ID", "标题", "图片", "分数", "作者", "播音者", "章节数量", "内容简介", "发布时间", "状态"
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
                {name: "score", editable: true},
                {name: "author", editable: true},
                {name: "broadcaster", editable: true},
                {name: "count", editable: true},
                {name: "brief", editable: true},
                {name: "create_date", editable: true, edittype: 'date'},
                {
                    name: "status", editable: true, edittype: "select", editoptions: {
                        value: "显示:显示;不显示:不显示"
                    }
                }
            ],
            subGrid: true,
            //subGridId  父表格的id   albumI  当前行的数据的id
            subGridRowExpanded: function (subGridId, albumId) {
                //动态添加子表格的方法
                addSubGrid(subGridId, albumId);
            }
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
                        url: "${pageContext.request.contextPath}/album/uploadAlbum",
                        fileElementId: 'img',
                        data: {id: id},
                        success: function () {
                            $("#list").trigger("reloadGrid");
                            /*if (status==200){
                                $("#msgDiv").text("添加成功").show();
                            }else{
                                $("#msgDiv").text("添加失败").show();
                            }
                            setTimeout(function () {
                                $("#msgDiv").hide();
                            },3000)*/
                        }
                    });
                    return response;
                }
            },
            // options for the Add Dialog
            {
                closeAfterAdd: true,
                afterSubmit: function (response) {
                    var id = response.responseJSON.bannerId;
                    var status = response.responseJSON.status;
                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/album/uploadAlbum",
                        fileElementId: 'img',
                        data: {id: id},
                        success: function () {
                            $("#list").trigger("reloadGrid");
                            /*if (status==200){
                                $("#msgDiv").text("添加成功").show();
                            }else{
                                $("#msgDiv").text("添加失败").show();
                            }
                            setTimeout(function () {
                                $("#msgDiv").hide();
                            },3000)*/
                        }
                    });
                    return response;
                }
            }
        )
    })

    //添加子表格的方法
    function addSubGrid(subGridId, albumId) {
        // alert();
        //动态设置  table id
        var subGridTableId = subGridId + "table";
        //设置动态div id   分页栏
        var subGridDivId = subGridId + "div";
        //动态添加子表格
        $("#" + subGridId).html("<table id = '" + subGridTableId + "'></table>" +
            "<div id ='" + subGridDivId + "' style='height:50px'></div>"
        );
        //为表格添加jqGrid样式
        $("#" + subGridTableId).jqGrid({
            url: "${pageContext.request.contextPath}/chapter/queryPageChapter?album_id=" + albumId,
            editurl: "${pageContext.request.contextPath}/chapter/editChapter?album_id=" + albumId,
            styleUI: "Bootstrap",
            datatype: "json",
            autowidth: true,//自适应父容器
            records: true,//是否显示总条数
            rowNum: 3,
            caption: "章节",
            toolbar: [true, "top"],//工具栏
            pager: "#" + subGridDivId,
            rowList: [3, 6, 9],
            multiselect: true,
            multiboxonly: true,
            colNames: [
                "id", "title", "album_id", "size", "duration", "src", "status"
            ],
            colModel: [
                {name: "id"},
                {name: "title", editable: true},
                {name: "album_id"},
                {name: "size"},
                {name: "duration"},
                {
                    name: "src", editable: true, edittype: 'file',
                    formatter: function (cellvalue, options, rowObject) {
                        return "<audio controls loop preload='auto'>\n" +
                            "<source src='${pageContext.request.contextPath}/music/" + cellvalue + "' type='audio/mpeg'>\n" +
                            "</audio>";
                    }
                },
                {
                    name: "status", editable: true, edittype: "select", editoptions: {
                        value: "显示:显示;不显示:不显示"
                    }
                }
            ],
        }).navGrid("#" + subGridDivId, {},
            {
                closeAfterEdit: true,
                afterSubmit: function (response) {
                    var id = response.responseJSON.chapterId;
                    alert(id);
                    if (id != null) {
                        $.ajaxFileUpload({
                            url: "${pageContext.request.contextPath}/chapter/upload",
                            fileElementId: 'src',
                            type: "post",
                            data: {id: id},
                            success: function () {
                                $("#" + subGridTableId).trigger("reloadGrid");
                            }
                        });
                    }
                    return response;
                }
            },
            // options for the Add Dialog
            {
                closeAfterAdd: true,
                afterSubmit: function (response) {
                    var id = response.responseJSON.chapterId;
                    alert(id);
                    $.ajaxFileUpload({
                        url: "${pageContext.request.contextPath}/chapter/upload",
                        fileElementId: 'src',
                        type: "post",
                        data: {id: id},
                        success: function () {
                            $("#" + subGridTableId).trigger("reloadGrid");
                        }
                    });
                    return response;
                }

            });
        //添加按钮
        $("#t_" + subGridTableId).html("<button class='btn btn-danger' onclick=\"play('" + subGridTableId + "')\">播放 <span class='glyphicon glyphicon-play'></span></button>" +
            "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
            "<button class='btn btn-danger' onclick=\"down('" + subGridTableId + "')\">下载 <span class='glyphicon glyphicon-arrow-down'></span></button>"
        )
    }

    //播放
    function play(subGridTableId) {
        //判断   用户是否选中一行    未选中---》null      选中---》被选中的id
        var gr = $("#" + subGridTableId).jqGrid('getGridParam', 'selrow');
        if (gr == null) {
            alert("请选中要播放的音频");
        } else {
            //根据id拿到对应的值
            var jqGrid = $("#" + subGridTableId).jqGrid('getRowData', gr);
            //根据
            var src = jqGrid.src;
            var Element = $(src)[0];
            //获取字标签
            var attr = $(Element).children();
            var source = $(attr)[0];
            var prop = $(source).prop("src");
            $("#myModal").modal('show');
            //获取路径
            $("#audio").attr("src", prop);

        }
    }

    //下载
    function down(subGridTableId) {
        var gr = $("#" + subGridTableId).jqGrid('getGridParam', 'selrow');
        if (gr == null) {
            alert("请选中要播放的音频");
        } else {
            //根据id拿到对应的值
            var jqGrid = $("#" + subGridTableId).jqGrid('getRowData', gr);
            //通过拿到的值获取到src的值
            var src = jqGrid.src;
            console.log(src);
            //通过src 获取到audio的标签
            var element = $(src)[0];
            console.log(element);
            //通过element获取子标签
            var children = $(element).children();
            console.log(children);
            var element1 = $(children)[0];
            var path = $(element1).prop("src");
            location.href = '${pageContext.request.contextPath}/chapter/downFile?src=' + path;
        }
    }
</script>
<div class="container-fluid">
    <%--一行--%>
    <div class="row">
        <%--用户管理信息--%>
        <div class="col-sm-12" id="gl">
            <ul class="nav nav-tabs">
                <li role="presentation" class="active"><a href="#aaa">专辑管理</a></li>
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
    <%--模态框--%>
    <div class="modal fade" tabindex="-1" id="myModal" data-keyboard="false" data-backdrop="false">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                                aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">播放器</h4>
                    </div>
                    <!--内容-->
                    <div class="modal-body">
                        <audio autoplay src="" id="audio" controls>
                        </audio>
                    </div>
                    <!--页码-->
                    <div class="modal-footer">
                        <button type="button" id="myUpdate" class="btn btn-primary">保存</button>
                        <button type="button" class="btn btn-primary" data-dismiss="modal">取消</button>
                    </div>
                </div>
            </div>
        </div>
    </div>