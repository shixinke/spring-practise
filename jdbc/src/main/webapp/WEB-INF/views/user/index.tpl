<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${title}</title>
    <link type="text/css" href="/static/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="col-sm-12">
        <div class="row" style="line-height:50px;">
            <h2 class="pull-left">用户管理 </h2>
            <div class="pull-right">
                <a class="btn btn-sm btn-info" href="/user/add"><i class="glyphicon-plus"></i>添加</a>
            </div>
        </div>
    </div>
    <hr>
    <table class="table table-bordered table-striped ">
        <thead>
            <th>用户ID</th>
            <th>账号</th>
            <th>昵称</th>
            <th>邮箱</th>
            <th>手机号</th>
            <th>注册时间</th>
            <th>状态</th>
            <th>操作</th>
        </thead>
        <tbody>
            <#list userList as user>
            <tr>
            <td>${user.getUserId()}</td>
            <td>${user.getAccount()}</td>
            <td>${user.getNickname()}</td>
            <td>${user.getEmail()}</td>
            <td>${user.getMobile()}</td>
            <td>${user.getCreateTime() ? string("yyyy-MM-dd HH:mm:ss")}</td>
            <td>
            <#if user.getStatus() == 1>
                <label class="label label-success">正常</label>
            <#else>
                <label class="label label-danger">禁用</label>
            </#if>
            </td>
            <td>
                <a href="/user/edit?id=${user.getUserId()}"><span class="glyphicon glyphicon-edit" aria-hidden="true"></span> 编辑</a>
                <a href="javascript:;" class="ajax-delete-link" data-id="${user.getUserId()}" data-action="/user/delete"><span class="glyphicon glyphicon-trash" aria-hidden="true"></span> 删除</a>
            </td>
            </#list>
            </tr>
        </tbody>
    </table>
</div>

<div class="modal fade" tabindex="-1" role="dialog" id="msg-modal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">提示信息</h4>
            </div>
            <div class="modal-body">
                <p class="msg-body"></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>

            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


<script type="text/javascript" src="/static/js/jquery.min.js"></script>
<script type="text/javascript" src="/static/js/bootstrap.min.js"></script>
<script type="text/javascript">
    $(function(){
        $(".ajax-delete-link").on("click", function(){
            var id = $(this).attr("data-id");
            var url = $(this).attr("data-action");
            var $tr = $(this).parents("tr");
            $.ajax({
                url : url,
                type:"post",
                data:{id : id},
                dataType:"json",
                success:function(res){
                    var result = typeof res == 'object' ? res : {};
                    if (result.code == 200) {
                        $("#msg-modal .msg-body").text("删除成功");
                        $("#msg-modal").modal("show");
                        setTimeout(function(){
                            $tr.remove();
                        }, 5000)
                    } else {
                        $("#msg-modal .msg-body").text("删除失败");
                        $("#msg-modal").modal("show");
                    }
                }
            });
        });
    });
</script>
</body>
</html>