/**

 * 
 */

var getUrl = '/FootballSystem/admin/service/cup/getCups';
var addUrl = '/FootballSystem/admin/service/cup/addCup';
var editUrl = '/FootballSystem/admin/service/cup/editCup';
var delOneUrl = '/FootballSystem/admin/service/cup/deleteCup';
var delListUrl = '/FootballSystem/admin/service/cup/deleteCupList';
$(function() {
	// var id = window.parent.getId(this);
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

						var cupId = $('#cupId').val();
						var cupName = $('#cupName').val();

						var formData = new FormData();
						formData.append('cupId', cupId);
						formData.append('cupName', cupName);
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
												$("#errMsg").html('');
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
			var cupId = button.closest('tr').find('td').eq(1).text();
			var cupName = button.closest('tr').find('td').eq(2).text();
			modal.find('.modal-body #cupId').val(cupId)
			modal.find('.modal-body #cupName').val(cupName)
			$('#submitData').val('edit');
			$('#submitData').data('objId', objId);
		} else {
			modal.find('.modal-body #cupId').val(null)
			modal.find('.modal-body #cupName').val(null)
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
		formData.append('cupId', objId);
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
								$("#delMsg").html('');
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
							setTimeout(function() { //使用  setTimeout（）方法设定定时2000毫秒
								$("#deleteModal").modal('hide');
								$("#delMsg").html('');
							}, 500);
						} else {
							var errMsg = '<div class="p-3 mb-2 bg-danger text-white">'
									+ data.message + '</div>'
							$('#delMsg').html(errMsg);
							setTimeout(function() { //使用  setTimeout（）方法设定定时2000毫秒
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
			+ '			<th>赛事id</th>' + '			<th>赛事名称</th>' + '			</tr>'
			+ '</thead><tbody>';
	data
			.map(function(item, index) {
				html += '<tr>'
						+ '<td>'
						+ '<div class="custom-control custom-checkbox">'
						+ '<input type="checkbox" class="custom-control-input cboxlist" id="checkbox'
						+ index
						+ '" value="'
						+ item.cupId
						+ '">'
						+ '<label class="custom-control-label" for="checkbox'
						+ index
						+ '">'
						+ '			<a href="#" data-toggle="modal" data-target="#inputModal" data-id="edit" data-obj-id="'
						+ item.cupId
						+ '"><i class="edit" /></a>'
						+ '<a href="#" data-toggle="modal" data-target="#deleteModal" data-id="'
						+ item.cupId
						+ '"><i class="delete" /></label>'
						+ '</div>'
						+ '</td>'
						+ '			<td>'
						+ item.cupId
						+ '</td>'
						+ '			<td>' + item.cupName + '</td>' + '			</tr>';
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
