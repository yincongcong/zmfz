<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<script type="text/javascript">
    $(function () {
        $("#list").jqGrid({
            url: "${pageContext.request.contextPath}/article/queryPageArticle",//默认访问路径
            editurl: "${pageContext.request.contextPath}/article/editArticle",//表格增删改查访问路径*/
            styleUI: "Bootstrap",//表格的样式
            datatype: "json",//接收的格式
            autowidth: true,//自动适应父容器
            toolbar: [true, "top"],//导航工具栏
            caption: "文章管理",//名称
            pager: "#pager",//工具栏
            rowNum: 3,//每页展示的条数
            rowList: [3, 8, 10],
            height: "300px",
            multiselect: true,
            multiboxonly: true,
            colNames: [
                "ID", "标题", "作者", "内容简介", "上师id", "上传时间", "状态", "操作"
            ],
            colModel: [
                {name: "id"},
                {name: "title", editable: true},
                {name: "author", editable: true},
                {name: "content", editable: true},
                {name: "guru_id"},
                {name: "create_date", editable: true, edittype: 'date'},
                {
                    name: "status", editable: true, edittype: "select", editoptions: {
                        value: "显示:显示;不显示:不显示"
                    }
                },
                {
                    name: "", formatter: function (value, grid, rows, status) {
                        return "<a href='#'><span class='glyphicon glyphicon-list' style='color:blue ' onclick='show()'></span></a>" +
                            "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                            "<a href='#>'<span class='glyphicon glyphicon-pencil' style='color:blue ' onclick='update()'></span></a>"
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
            },
            // options for the Add Dialog
            {
                closeAfterAdd: true,
                recreateForm: true,
            }
        )
        //添加按钮
        $("#t_list").html("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='#'><span class='glyphicon glyphicon-list' style='color:blue' onclick='show()'></span></a>" +
            " &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
            "<a href='#>'<span class='glyphicon glyphicon-pencil' style='color:blue ' onclick='update()'></span></a>");
    })
    KindEditor.create("#editor_id", {
        width: 500,//文本编译器的宽度
        height: 300,//文本编译器的高度
        minHeight: 400,//文本编译器的最小宽度
        resizeType: 0,//0代表不能拖动
        allowFileManager: true,//是否展示图片空间
        filePostName: 'img',//上传是后台接收的名字
        uploadJson: '${pageContext.request.contextPath}/article/uploadImg',//上传后台的路径
        fileManagerJson: '${pageContext.request.contextPath}/article/getAllImgs', //指定浏览远程图片的服务器端程序
        afterBlur: function () {
            this.sync();
        }
    });

    function showaddModal() {
        $.ajax({
            url: "${pageContext.request.contextPath}/article/insertArticle",
            datatype: "json",
            type: "post",
            data: $("#addForm").serialize(),
            success: function (data) {
                $("#myModal").modal("hide");
                $("#addForm")[0].reset();
                $("#list").trigger("reloadGrid");
            }
        });
    }

    function show() {
        //展示模态框
        $("#myModal1").modal("show");
        var gr = $("#list").jqGrid('getGridParam', 'selrow');
        console.log(gr);
        var data = $("#list").getRowData(gr);
        $("#id").val(data.id);
        $("#title1").val(data.title);
        $("#author1").val(data.author);
        $("#create_date1").val(data.create_date);
        $("#guru_id1").val(data.guru_id);
        if (data.status == "显示") {
            $("#op3").prop("selected", "selected");
        } else {
            $("#op4").prop("selected", "selected");
        }
        KindEditor.create("#editor_id1", {
            width: 500,//文本编译器的宽度
            height: 300,//文本编译器的高度
            minHeight: 400,//文本编译器的最小宽度
            resizeType: 0,//0代表不能拖动
            allowFileManager: true,//是否展示图片空间
            filePostName: 'img',//上传是后台接收的名字
            uploadJson: '${pageContext.request.contextPath}/article/uploadImg',//上传后台的路径
            fileManagerJson: '${pageContext.request.contextPath}/article/getAllImgs', //指定浏览远程图片的服务器端程序
            afterBlur: function () {
                this.sync();
            }
        });
        KindEditor.html("#editor_id1", data.content);

        function showaddModal() {
            $.ajax({
                url: "${pageContext.request.contextPath}/article/insertArticle",
                datatype: "json",
                type: "post",
                data: $("#addForm").serialize(),
                success: function (data) {
                    $("#myModal").modal("hide");
                    $("#addForm")[0].reset();
                    $("#list").trigger("reloadGrid");
                }
            });
        }
    }

    function update() {
        //展示模态框
        var gr = $("#list").jqGrid('getGridParam', 'selrow');
        console.log(gr);
        if (gr == null) {
            alert("请选择要修改的数据");
        } else {
            $("#myModal2").modal("show");
            var data = $("#list").getRowData(gr);
            $("#id1").val(data.id);
            $("#title2").val(data.title);
            $("#author2").val(data.author);
            $("#create_date2").val(data.create_date);
            $("#guru_id2").val(data.guru_id);
            if (data.status == "显示") {
                $("#op5").prop("selected", "selected");
            } else {
                $("#op6").prop("selected", "selected");
            }

            KindEditor.create("#editor_id2", {
                width: 500,//文本编译器的宽度
                height: 300,//文本编译器的高度
                minHeight: 400,//文本编译器的最小宽度
                resizeType: 0,//0代表不能拖动
                allowFileManager: true,//是否展示图片空间
                filePostName: 'img',//上传是后台接收的名字
                uploadJson: '${pageContext.request.contextPath}/article/uploadImg',//上传后台的路径
                fileManagerJson: '${pageContext.request.contextPath}/article/getAllImgs', //指定浏览远程图片的服务器端程序
                afterBlur: function () {
                    this.sync();
                }
            });
            KindEditor.html("#editor_id2", null);
            KindEditor.appendHtml("#editor_id2", data.content);
        }

    }

    function up() {
        $.ajax({
            url: "${pageContext.request.contextPath}/article/updateArticle",
            datatype: "json",
            type: "post",
            data: $("#updateForm").serialize(),
            success: function (data) {
                $("#myModal2").modal("hide");
                $("#updateForm")[0].reset();
                $("#list").trigger("reloadGrid");
            }
        });
    }
</script>
<div class="container-fluid">
    <%--一行--%>
    <div class="row">
        <%--用户管理信息--%>
        <div class="col-sm-12" id="gl">
            <ul class="nav nav-tabs">
                <li role="presentation" class="active"><a href="#aaa">文章管理</a></li>
                <li role="presentation"><a href="#" data-toggle="modal" data-target="#myModal">添加文章</a></li>
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
    <%--添加的模态框--%>
    <div class="modal fade" id="myModal" data-keyboard="false" data-backdrop="false">
        <div class="modal-dialog" style="width: 700px">
            <div class="modal-content">
                <!--头部-->
                <div class="modal-header">
                    <button class="close" data-dismiss="modal">
                        <span>&times;</span>
                    </button>
                    <!--标题-->
                    <h3 class="modal-title">添加用户信息</h3>
                </div>
                <!--内容-->
                <div class="modal-body">
                    <form class="form-horizontal" id="addForm">
                        <div class="form-group">
                            <lable for="title" class="col-sm-4 control-label"><strong>标题：</strong></lable>
                            <div class="col-sm-6">
                                <input type="text" class="form-control" id="title" name="title">
                            </div>
                        </div>
                        <div class="form-group">
                            <lable for="author" class="col-sm-4 control-label"><strong>作者：</strong></lable>
                            <div class="col-sm-6">
                                <input type="text" class="form-control" id="author" name="author">
                            </div>
                        </div>
                        <div class="form-group">
                            <lable for="create_date" class="col-sm-4 control-label"><strong>上传时间：</strong></lable>
                            <div class="col-sm-6">
                                <input type="date" class="form-control" id="create_date" name="create_date">
                            </div>
                        </div>
                        <div class="form-group">
                            <lable for="guru_id" class="col-sm-4 control-label"><strong>上师id：</strong></lable>
                            <div class="col-sm-6">
                                <input type="text" class="form-control" id="guru_id" name="guru_id">
                            </div>
                        </div>
                        <div class="form-group">
                            <lable for="status" class="col-sm-4 control-label"><strong>状态：</strong></lable>
                            <div class="col-sm-6">
                                <select name="status" class="form-control">
                                    <option value="">---请选择---</option>
                                    <option id="op1" value="显示">显示</option>
                                    <option id="op2" value="不显示">不显示</option>
                                </select>
                            </div>
                        </div>
                        <textarea id="editor_id" name="content" style="width:700px;height:300px;">
                        </textarea>
                    </form>
                </div>

                <!--页码-->
                <div class="modal-footer">
                    <button type="button" id="myUpdate" class="btn btn-primary" onclick="showaddModal()">保存</button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal">取消</button>
                </div>
            </div>
        </div>
    </div>
    <%--查看详细信息的模态框--%>
    <div class="modal fade" id="myModal1" data-keyboard="false" data-backdrop="false">
        <div class="modal-dialog" style="width: 700px">
            <div class="modal-content">
                <!--头部-->
                <div class="modal-header">
                    <button class="close" data-dismiss="modal">
                        <span>&times;</span>
                    </button>
                    <!--标题-->
                    <h3 class="modal-title">查看文章信息</h3>
                </div>
                <!--内容-->
                <div class="modal-body">
                    <form class="form-horizontal" id="showForm">
                        <div class="form-group">
                            <lable for="id" class="col-sm-4 control-label"><strong>ID：</strong></lable>
                            <div class="col-sm-6">
                                <input type="text" class="form-control" id="id" name="id">
                            </div>
                        </div>
                        <div class="form-group">
                            <lable for="title1" class="col-sm-4 control-label"><strong>标题：</strong></lable>
                            <div class="col-sm-6">
                                <input type="text" class="form-control" id="title1" name="title">
                            </div>
                        </div>
                        <div class="form-group">
                            <lable for="author1" class="col-sm-4 control-label"><strong>作者：</strong></lable>
                            <div class="col-sm-6">
                                <input type="text" class="form-control" id="author1" name="author">
                            </div>
                        </div>
                        <div class="form-group">
                            <lable for="create_date1" class="col-sm-4 control-label"><strong>上传时间：</strong></lable>
                            <div class="col-sm-6">
                                <input type="date" class="form-control" id="create_date1" name="create_date">
                            </div>
                        </div>
                        <div class="form-group">
                            <lable for="guru_id1" class="col-sm-4 control-label"><strong>上师id：</strong></lable>
                            <div class="col-sm-6">
                                <input type="text" class="form-control" id="guru_id1" name="guru_id">
                            </div>
                        </div>
                        <div class="form-group">
                            <lable for="status" class="col-sm-4 control-label"><strong>状态：</strong></lable>
                            <div class="col-sm-6">
                                <select name="" class="form-control">
                                    <option value="">---请选择---</option>
                                    <option id="op3" value="显示">显示</option>
                                    <option id="op4" value="不显示">不显示</option>
                                </select>
                            </div>
                        </div>
                        <textarea id="editor_id1" name="content" style="width:700px;height:300px;">
                        </textarea>
                    </form>
                </div>

                <!--页码-->
                <div class="modal-footer">
                    <button type="button" class="btn btn-primary" data-dismiss="modal">取消</button>
                </div>
            </div>
        </div>
    </div>
    <%--修改信息的模态框--%>
    <div class="modal fade" id="myModal2" data-keyboard="false" data-backdrop="false">
        <div class="modal-dialog" style="width: 700px">
            <div class="modal-content">
                <!--头部-->
                <div class="modal-header">
                    <button class="close" data-dismiss="modal">
                        <span>&times;</span>
                    </button>
                    <!--标题-->
                    <h3 class="modal-title">修改文章信息</h3>
                </div>
                <!--内容-->
                <div class="modal-body">
                    <form class="form-horizontal" id="updateForm">
                        <div class="form-group">
                            <lable for="id1" class="col-sm-4 control-label"><strong>ID：</strong></lable>
                            <div class="col-sm-6">
                                <input type="hidden" class="form-control" id="id1" name="id">
                            </div>
                        </div>
                        <div class="form-group">
                            <lable for="title2" class="col-sm-4 control-label"><strong>标题：</strong></lable>
                            <div class="col-sm-6">
                                <input type="text" class="form-control" id="title2" name="title">
                            </div>
                        </div>
                        <div class="form-group">
                            <lable for="author2" class="col-sm-4 control-label"><strong>作者：</strong></lable>
                            <div class="col-sm-6">
                                <input type="text" class="form-control" id="author2" name="author">
                            </div>
                        </div>
                        <div class="form-group">
                            <lable for="create_date2" class="col-sm-4 control-label"><strong>上传时间：</strong></lable>
                            <div class="col-sm-6">
                                <input type="date" class="form-control" id="create_date2" name="create_date">
                            </div>
                        </div>
                        <div class="form-group">
                            <lable for="guru_id2" class="col-sm-4 control-label"><strong>上师id：</strong></lable>
                            <div class="col-sm-6">
                                <input type="text" class="form-control" id="guru_id2" name="guru_id">
                            </div>
                        </div>
                        <div class="form-group">
                            <lable for="status" class="col-sm-4 control-label"><strong>状态：</strong></lable>
                            <div class="col-sm-6">
                                <select name="status" id="status" class="form-control">
                                    <option id="op5" value="显示">显示</option>
                                    <option id="op6" value="不显示">不显示</option>
                                </select>
                            </div>
                        </div>
                        <textarea id="editor_id2" name="content" style="width:700px;height:300px;">
                        </textarea>
                    </form>
                </div>

                <!--页码-->
                <div class="modal-footer">
                    <button type="button" id="myUpdate1" class="btn btn-primary" onclick="up()">保存</button>
                    <button type="button" class="btn btn-primary" data-dismiss="modal">取消</button>
                </div>
            </div>
        </div>
    </div>
