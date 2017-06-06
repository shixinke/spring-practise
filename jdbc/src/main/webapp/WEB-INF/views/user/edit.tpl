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
            <h2 class="pull-left">编辑用户信息</h2>
            <div class="pull-right">
                <a class="btn btn-sm btn-info" href="/user/index"><i class="glyphicon-user"></i>用户管理</a>
            </div>
        </div>
    </div>
    <hr>
    <#if userInfo.getUserId()  >
    <form class="form-horizontal" method="post" id="user-form">
        <input type="hidden" name="user_id" value="${userInfo.getUserId()}">
        <div class="form-group">
            <label for="account" class="col-sm-2 control-label">账号</label>
            <div class="col-sm-6">
                <input type="text" class="form-control" name="account" id="account" placeholder="账号" value="${userInfo.getAccount()}">
            </div>
        </div>

        <div class="form-group">
            <label for="nickname" class="col-sm-2 control-label">昵称</label>
            <div class="col-sm-6">
                <input type="text" class="form-control" name="nickname" id="nickname" placeholder="昵称" value="${userInfo.getNickname()}">
            </div>
        </div>
        <div class="form-group">
            <label for="email" class="col-sm-2 control-label">邮箱</label>
            <div class="col-sm-6">
                <input type="text" class="form-control" name="email" id="email" placeholder="邮箱" value="${userInfo.getEmail()}">
            </div>
        </div>
        <div class="form-group">
            <label for="mobile" class="col-sm-2 control-label">手机号</label>
            <div class="col-sm-6">
                <input type="text" class="form-control" name="mobile" id="mobile" placeholder="手机号" value="${userInfo.getMobile()}">
            </div>
        </div>
        <div class="form-group">
            <label for="mobile" class="col-sm-2 control-label">状态</label>
            <div class="col-sm-2">
                <select name="status" class="form-control">
                    <#if userInfo.getStatus() == 1 >
                        <option value="1" selected="selected">正常</option>
                        <option value="0">禁用</option>
                    <#else>
                        <option value="1">正常</option>
                        <option value="0" selected="selected">禁用</option>
                    </#if>
                </select>
            </div>
        </div>

        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-primary">保存</button>
            </div>
        </div>
    </form>
    <#else>
    <div class="alert alert-danger">
        ${error}
    </div>
    </#if>
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
<script type="text/javascript" src="/static/js/jquery.form.js"></script>
<script type="text/javascript">
    $(function(){
        $('#user-form').on('submit', function(e) {
            e.preventDefault(); // prevent native submit
            $(this).ajaxSubmit({
                dataType:'json',
                success:function(res){
                    var result = typeof res == 'object' ? res : {};
                    if (result.code == 200) {
                        $("#msg-modal .msg-body").text("修改成功");
                        $("#msg-modal").modal("show");
                        setTimeout(function(){
                            window.location.href = "/user/index";
                        }, 3000)
                    } else {
                        $("#msg-modal .msg-body").text("修改失败");
                        $("#msg-modal").modal("show");
                    }
                }
            })
        });
    });
</script>
</body>
</html>