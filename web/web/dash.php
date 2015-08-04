<?php 
	session_start();
	if(!isset($_SESSION['user_id'])){
		$home_url = "signin.html";
		header('Location: '.$home_url);
	}
	?>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <meta name="description" content="">
    <meta name="author" content="">

    <title>团聚</title>

    <!-- Bootstrap core CSS -->
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="dash.css" rel="stylesheet">
    <link href="ch-font.css" rel="stylesheet">
  </head>

  <body>

    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container-fluid">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">团聚</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav navbar-right">
            <li><a href="#">主页</a></li>
            <li><a href="#">用户信息</a></li>
            <li><a href="logout.php">登出</a></li>
          </ul>
          <form class="navbar-form navbar-right">
            <input type="text" class="form-control" placeholder="Search...">
          </form>
        </div>
      </div>
    </nav>

    <div class="container-fluid">
      <div class="row">
        <div class="col-sm-3 col-md-2 sidebar" role="tabpanel">
          <ul class="nav nav-sidebar" role="tablist">
            <li role="presentation" class="active"><a href="#news" aria-controls="news" role="tab" data-toggle="tab">编辑社团消息</a></li>
            <li role="presentation"><a href="#notice" aria-controls="notice" role="tab" data-toggle="tab">编辑社团通知</a></li>
            <li><a href="#">用户信息</a></li>
          </ul>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main tab-content">
          <div role="tabpanel" class="tab-pane fade in active" id="news">
            <h1 class="page-header">
          	  <?php
          			if(isset($_SESSION['name'])){
          				echo $_SESSION['name']."的消息";
          			} else{
          				echo "未命名社团的消息";
          			}
          	  ?>
          	  </h1>

            <h2 class="sub-header">添加消息</h2>
            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addModal">&nbsp;+&nbsp;</button>
            
            <h2 class="sub-header">社团消息列表</h2>
            <div class="table-responsive">
              <table class="table table-striped">
                <thead>
                  <tr>
                    <th>标题</th>
                    <th>URL</th>
                    <th>创建日期</th>
                    <th>编辑</th>
                    <th>删除</th>
                  </tr>
                </thead>
                <tbody id="info-tbody">
                  <tr class="tr-template" style="display:none">
                    <td class="info-title">Hello, world</td>
                    <td class="info-url"><a href="#" target="_blank">点击查看</a></td>
                    <td class="info-date">2015-5-28</td>
                    <td class="info-edit"><a href="#" data-toggle="modal" data-target="#editModal">编辑</a></td>
                    <td class="info-delete"><a href="#" data-toggle="modal" data-target="#deleteModal">删除</a></td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
          <div role="tabpanel" class="tab-pane fade" id="notice">
            <h1 class="page-header">
          	  <?php
          			if(isset($_SESSION['name'])){
          				echo $_SESSION['name']."的通知";
          			} else{
          				echo "未命名社团的通知";
          			}
          	  ?>
          	  </h1>

            <h2 class="sub-header">添加通知</h2>
            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addNotice">&nbsp;+&nbsp;</button>
            <h2 class="sub-header">通知列表</h2>
            <div class="table-responsive">
              <table class="table table-striped">
                <thead>
                  <tr>
                    <th>内容</th>
                    <th>创建日期</th>
                    <th>编辑</th>
                    <th>删除</th>
                  </tr>
                </thead>
                <tbody id="notice-tbody">
                  <tr class="trn-template" style="display:none">
                    <td class="notice-content">Hello, world</td>
                    <td class="notice-date">2015-5-28</td>
                    <td class="notice-edit"><a href="#">编辑</a></td>
                    <td class="notice-delete"><a href="#" data-toggle="modal" data-target="#deleteNotice">删除</a></td>
                  </tr>
                </tbody>
              </table>
            </div> <!-- div.table-responsive -->
          </div> <!-- div#notice -->
        </div>
      </div>
    </div>
    <!-- Modal -->
    <div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="editModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" id="editModalLabel">编辑社团消息</h4>
          </div>
          <div class="modal-body">
            <form>
              <div class="form-group">
                <label for="edit-title" class="control-label">标题：</label>
                <input type="text" class="form-control" id="edit-title">
              </div>
              <div class="form-group">
                <label for="edit-url" class="control-label">URL：</label>
                <input type="text" class="form-control" id="edit-url">
              </div>
              <input type="hidden" id="edit-id">
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" id="edit-save" class="btn btn-primary start" data-loading-text="正在保存">保存更改</button>
          </div>
        </div>
      </div>
    </div>
    <div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="deleteModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" id="deleteModalLabel">删除社团消息</h4>
          </div>
          <div class="modal-body">
            你确定要删除该消息吗？
            <form>
              <input type="hidden" id="delete-id">
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" id="delete-confirm" class="btn btn-danger start" data-loading-text="正在删除">确定删除</button>
          </div>
        </div>
      </div>
    </div>
    <div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="addModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" id="addModalLabel">添加社团消息</h4>
          </div>
          <div class="modal-body">
            <form>
              <div class="form-group">
                <label for="add-title" class="control-label">标题：</label>
                <input type="text" class="form-control" id="add-title">
              </div>
              <div class="form-group">
                <label for="add-url" class="control-label">URL：</label>
                <input type="text" class="form-control" id="add-url">
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" id="add-save" class="btn btn-primary start" data-loading-text="正在添加">确认添加</button>
          </div>
        </div>
      </div>
    </div>
    <div class="modal fade" id="addNotice" tabindex="-1" role="dialog" aria-labelledby="addNoticeLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" id="addNoticeLabel">添加社团消息</h4>
          </div>
          <div class="modal-body">
            <form>
              <div class="form-group">
                <label for="addnotice-content" class="control-label">内容：</label>
                <textarea class="form-control" id="addnotice-content"></textarea>
              </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" id="addnotice-save" class="btn btn-primary start" data-loading-text="正在添加">确认添加</button>
          </div>
        </div>
      </div>
    </div>
    <div class="modal fade" id="deleteNotice" tabindex="-1" role="dialog" aria-labelledby="deleteNoticeLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" id="deleteNoticeLabel">删除通知</h4>
          </div>
          <div class="modal-body">
            你确定要删除该通知吗？
            <form>
              <input type="hidden" id="deletenotice-id">
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
            <button type="button" id="deletenotice-confirm" class="btn btn-danger start" data-loading-text="正在删除">确定删除</button>
          </div>
        </div>
      </div>
    </div>

    <script src="js/jquery.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script type="text/javascript">

var info_list = [];

var refresh_info = function() {
  var tr_template = $('.tr-template').clone()
                                     .removeClass('tr-template')
                                     .addClass('tr-temp-gen')
                                     .css('display', 'table-row');

  $('.tr-temp-gen').remove();

  $.get('select_news.php', {}, function(data, status) {
    var info_seq = 0;

    info_list = data.contents;
    info_list.forEach(function(info_item) {
      var item_dom = tr_template.clone();

      item_dom.find('.info-title').text(info_item.title);
      item_dom.find('.info-url a').attr('href', info_item.URL);
      item_dom.find('.info-date').text(info_item.thedate);
      item_dom.find('.info-edit a').attr('data-seq', info_seq)
                                   .attr('data-id', info_item.id);
      item_dom.find('.info-delete a').attr('data-seq', info_seq)
                                     .attr('data-id', info_item.id);
      item_dom.appendTo('#info-tbody');

      info_seq++;
    }, 'json');
    tr_template = null;
  });
}

var notice_list = [];

var refresh_notice = function() {
  var tr_template = $('.trn-template').clone()
                                      .removeClass('trn-template')
                                      .addClass('trn-temp-gen')
                                      .css('display', 'table-row');

  $('.trn-temp-gen').remove();

  $.get('select_notice.php', {}, function(data, status) {
    var info_seq = 0;

    notice_list = data.contents;
    notice_list.forEach(function(info_item) {
      var item_dom = tr_template.clone();

      item_dom.find('.notice-content').text(info_item.content);
      item_dom.find('.notice-date').text(info_item.thedate);
      item_dom.find('.notice-edit a').attr('data-seq', info_seq)
                                     .attr('data-id', info_item.id);
      item_dom.find('.notice-delete a').attr('data-seq', info_seq)
                                       .attr('data-id', info_item.id);
      item_dom.appendTo('#notice-tbody');

      info_seq++;
    }, 'json');
    tr_template = null;
  });
}

$(function() {
  $('#editModal').on('show.bs.modal', function(event) {
    var anchor = $(event.relatedTarget);
    var info_seq = anchor.data('seq');

    var modal = $(this);
    modal.find('.modal-title').text("编辑消息");
    modal.find('#edit-title').val(info_list[info_seq].title);
    modal.find('#edit-url').val(info_list[info_seq].URL);
    modal.find('#edit-id').val(info_list[info_seq].id);
  });

  $('#addModal').on('show.bs.modal', function(event) {
    var modal = $(this);
    modal.find('.modal-title').text("添加消息");
    modal.find('#add-title').val('');
    modal.find('#add-url').val('');
  });


  $('#deleteModal').on('show.bs.modal', function(event) {
    var modal = $(this);
    var anchor = $(event.relatedTarget);
    var info_seq = anchor.data('seq');

    modal.find('#delete-id').val(info_list[info_seq].id);
  });

  $('#edit-save').on('click', function () {
    var btn = $(this).button('loading');
    var modal = $('#editModal');
    var id = modal.find('#edit-id').val();
    var title = modal.find('#edit-title').val();
    var URL = modal.find('#edit-url').val();

    $.post('update_news.php', {"id": id, "title": title, "URL": URL}, function(data, status) {
      btn.button('reset');
      $('#editModal').modal('hide');
      refresh_info();
    }, 'json');
  });

  $('#add-save').on('click', function () {
    var btn = $(this).button('loading');
    var modal = $('#addModal');
    var title = modal.find('#add-title').val();
    var URL = modal.find('#add-url').val();

    $.post('insert_news.php', {"title": title, "URL": URL}, function(data, status) {
      btn.button('reset');
      $('#addModal').modal('hide');
      refresh_info();
    }, 'json');
  });

  $('#delete-confirm').on('click', function () {
    var btn = $(this).button('loading');
    var modal = $('#deleteModal');
    var id = modal.find('#delete-id').val();

    $.post('delete_news.php', {"id": id}, function(data, status) {
      btn.button('reset');
      $('#deleteModal').modal('hide');
      refresh_info();
    }, 'json');
  });

  $('#addNotice').on('show.bs.modal', function(event) {
    var modal = $(this);
    modal.find('#addnotice-content').val('');
  });

  $('#addnotice-save').on('click', function () {
    var btn = $(this).button('loading');
    var modal = $('#addNotice');
    var content = modal.find('#addnotice-content').val();

    $.post('insert_notice.php', {"content": content}, function(data, status) {
      btn.button('reset');
      $('#addNotice').modal('hide');
      refresh_notice();
    }, 'json');
  });

  $('#deleteNotice').on('show.bs.modal', function(event) {
    var modal = $(this);
    var anchor = $(event.relatedTarget);
    var info_seq = anchor.data('seq');

    modal.find('#deletenotice-id').val(notice_list[info_seq].id);
  });

  $('#deletenotice-confirm').on('click', function () {
    var btn = $(this).button('loading');
    var modal = $('#deleteNotice');
    var id = modal.find('#deletenotice-id').val();

    $.post('delete_notice.php', {"id": id}, function(data, status) {
      btn.button('reset');
      $('#deleteNotice').modal('hide');
      refresh_notice();
    }, 'json');
  });

  refresh_info();
  refresh_notice();
});
    </script>
  </body>
</html>
