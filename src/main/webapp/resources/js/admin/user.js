/**
 * 
 * 
 */
var getUrl = '/FootballSystem/admin/service/user/getUsers';
var addUrl = '/FootballSystem/admin/service/user/addUser';
var editUrl = '/FootballSystem/admin/service/user/editUser';
var delOneUrl = '/FootballSystem/admin/service/user/deleteUser';
var delListUrl = '/FootballSystem/admin/service/user/deleteUserList';
$(function() {
	var id = window.parent.getId(this);
	var loading = new LoadingUtils();
	loading.append();

	initData();
	$('#submitData')
			.click(
					function() {
						loading.show();
						var type = $('#submitData').val();
						var oldObjId = $('#submitData').data('objId');
						var urlStr = '';
						if (type == 'edit') {
							urlStr = editUrl;
						} else {
							urlStr = addUrl;
						}

						var username = $('#username').val();
						var password = $('#password').val();

						var formData = new FormData();
						formData.append('username', username);
						formData.append('password', password);
						$
								.ajax({
									url : urlStr,
									type : 'POST',
									data : formData,
									contentType : false,
									processData : false,
									cache : false,
									success : function(data) {
										loading.hide();
										if (data.state == 0) {
											var success = '<div class="p-3 mb-2 bg-success text-white">'
													+ data.message + '</div>'
											$('#errMsg').html(success);
											initData();
											setTimeout(function() { // 使用
												// setTimeout（）方法设定定时2000毫秒
												$("#inputModal").modal('hide');
												$('#errMsg').html('');
											}, 500);
										} else {
											var errMsg = '<div class="p-3 mb-2 bg-danger text-white">'
													+ data.message + '</div>'
											$('#errMsg').html(errMsg);
											setTimeout(function() { // 使用
												$("#errMsg").html('');
											}, 5000);
										}
									}
								});

					});

	$('#inputModal').on('show.bs.modal', function(event) {
		var button = $(event.relatedTarget) // Button that triggered the modal
		var modal = $(this)
		var type = button.data('id');
		var objId = button.data('objId');
		if (type == 'edit') {
			var username = button.closest('tr').find('td').eq(1).text();
			var password = button.closest('tr').find('td').eq(2).text();
			modal.find('.modal-body #username').val(username)
			modal.find('.modal-body #password').val(password)
			$('#username').attr('readonly','true');
			$('#submitData').val('edit');
			$('#submitData').data('objId', objId);
		} else {
			modal.find('.modal-body #username').val(null)
			modal.find('.modal-body #password').val(null)
			$('#username').removeAttr("readonly");
			$('#submitData').val('add');
		}
	});
	$('#deleteModal').on('show.bs.modal', function(event) {
		var button = $(event.relatedTarget) // Button that triggered the modal
		var modal = $(this)
		var objId = button.data('id');
		$('#deleteObj').val(objId);
		var type = button.data('type');
		$('#deleteObj').data('type', type);
	});

	$('#deleteObj').click(function() {
		loading.show();
		var type = $('#deleteObj').data('type');
		if (type == 'list') {
			delList();
		} else {
			delOne();
		}

	});

	function delOne() {
		var objId = $('#deleteObj').val();
		var formData = new FormData();
		formData.append('username', objId);
		$
				.ajax({
					url : delOneUrl,
					type : 'POST',
					data : formData,
					contentType : false,
					processData : false,
					cache : false,
					success : function(data) {
						loading.hide();
						if (data.state == 0) {
							var success = '<div class="p-3 mb-2 bg-success text-white">删除成功</div>'
							$('#delMsg').html(success);
							initData();
							setTimeout(function() { // 使用
													// setTimeout（）方法设定定时2000毫秒
								$("#deleteModal").modal('hide');
								$('#delMsg').html('');
							}, 500);
						} else {
							var errMsg = '<div class="p-3 mb-2 bg-danger text-white">'
									+ data.message + '</div>'
							$('#delMsg').html(errMsg);
							setTimeout(function() { // 使用
													// setTimeout（）方法设定定时2000毫秒
								$("#deleteModal").modal('hide');
								$('#delMsg').html('');
							}, 2000);
						}
					}
				});
	}
	function delList() {
		var list = [];
		$(".cboxlist").each(function(index, element) {
			if (element.checked == true) {
				list.push(element.value);
			}
		})
		$
				.ajax({
					url : delListUrl,
					type : 'POST',
					dataType : "json",
					contentType : "application/json",
					data : JSON.stringify(list),
					processData : false,
					cache : false,
					success : function(data) {
						loading.hide();
						if (data.state == 0) {
							var success = '<div class="p-3 mb-2 bg-success text-white">删除成功</div>'
							$('#delMsg').html(success);
							initData();
							setTimeout(function() { // 使用
													// setTimeout（）方法设定定时2000毫秒
								$("#deleteModal").modal('hide');
								$('#delMsg').html('');
							}, 500);
						} else {
							var errMsg = '<div class="p-3 mb-2 bg-danger text-white">'
									+ data.message + '</div>'
							$('#delMsg').html(errMsg);
							setTimeout(function() { // 使用
													// setTimeout（）方法设定定时2000毫秒
								$("#deleteModal").modal('hide');
								$('#delMsg').html('');
							}, 2000);
						}
					}
				});
	}
});
function initData() {
	var formData = new FormData();
	formData.append('pageIndex', pageIndex);
	formData.append('pageSize', pageSize);
	$.ajax({
		url : getUrl,
		type : 'POST',
		data : formData,
		contentType : false,
		processData : false,
		cache : false,
		success : function(data) {
			if (data.state == 0) {
				handlerList(data.result);
				initPageCount(data.count);
				initPageData();
			} else {
				alert(data.message)
			}
		}
	});
}
function handlerList(data) {
	var html = '';
	html += '<thead>'
			+ '	<tr id="cboxhead">'
			+ '			<th><div class="custom-control custom-checkbox"  >'
			+ '<input type="checkbox" class="custom-control-input"    >'
			+ '<label class="custom-control-label" for="cboxhead">全选</label></div></th>'
			+ '			<th>用户名</th>' + '			<th>密码</th>' + '			</tr>'
			+ '</thead><tbody>';
	data
			.map(function(item, index) {
				html += '<tr>'
						+ '<td>'
						+ '<div class="custom-control custom-checkbox">'
						+ '<input type="checkbox" class="custom-control-input cboxlist" id="checkbox'
						+ index
						+ '" value="'
						+ item.username
						+ '">'
						+ '<label class="custom-control-label" for="checkbox'
						+ index
						+ '">'
						+ '			<a href="#" data-toggle="modal" data-target="#inputModal" data-id="edit" data-obj-id="'
						+ item.username
						+ '"><i class="edit" /></a>'
						+ '<a href="#" data-toggle="modal" data-target="#deleteModal" data-id="'
						+ item.username
						+ '"><i class="delete" /></label>'
						+ '</div>'
						+ '</td>'
						+ '			<td>'
						+ item.username
						+ '</td>'
						+ '			<td>'
						+ item.password
						+ '</td>'
						+ '			</tr>';
			});

	html += '</tbody>'
	$('#datalist').html(html);
	// 在父类中调用,提高代码重用性
	// var iframe = parent.window.document.getElementById(id);
	// var height = this.document.body.scrollHeight;
	// iframe.style = 'height:' + height + 'px';

	// 版本2
	// window.parent.setHeight(id, this);
	// 版本3,合并setHeight和setIframeHeight方法
	// window.parent.setIframeHeight(id);
}
