/**

 * 
 */
var getCupUrl = '/FootballSystem/admin/service/cup/getCups';
var getLeaderUrl = '/FootballSystem/admin/service/player/getPlayers';
var editLeaderUrl = '/FootballSystem/admin/service/team/updateTeamLeader';
var getUrl = '/FootballSystem/admin/service/team/getTeams';
var addUrl = '/FootballSystem/admin/service/team/addTeam';
var editUrl = '/FootballSystem/admin/service/team/editTeam';
var delOneUrl = '/FootballSystem/admin/service/team/deleteTeam';
var delListUrl = '/FootballSystem/admin/service/team/deleteTeamList';
$(function() {
	// var id = window.parent.getId(this);
	var loading = new LoadingUtils();
	loading.append();

	initData();

	$('#submitData')
			.click(
					function() {

						var type = $('#submitData').val();
						var oldObjId = $('#submitData').data('objId');
						var urlStr = '';
						if (type == 'edit') {
							urlStr = editUrl;
						} else {
							urlStr = addUrl;
						}
						var teamId = $('#teamId').val();
						var teamName = $('#teamName').val();
						var vaildCode = $('#vaildCode').val();
						var teamDesc = $('#teamDesc').val();
						var cupId = $('#cupId').val();
						if (vaildCode.length < 4) {
							$('#errMsg')
									.html(
											'<div class="p-3 mb-2 bg-danger text-white">请正确输入验证码,4位字符数字</div>');
							return;
						}
						loading.show();
						var formData = new FormData();
						formData.append('teamId', teamId);
						formData.append('teamName', teamName);
						formData.append('vaildCode', vaildCode);
						formData.append('teamDesc', teamDesc);
						formData.append('cupId', cupId);
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

	$('#inputModal')
			.on(
					'show.bs.modal',
					function(event) {
						var button = $(event.relatedTarget) // Button that
						// triggered the
						// modal
						var modal = $(this)
						var type = button.data('id');
						var objId = button.data('objId');
						if (type == 'edit') {
							var teamId = button.closest('tr').find('td').eq(1)
									.text();
							var teamName = button.closest('tr').find('td')
									.eq(2).text();
							var cupId = button.closest('tr').find('td').eq(3)
									.attr('value');
							var vaildCode = button.closest('tr').find('td').eq(
									4).text();
							var teamDesc = button.closest('tr').find('td')
									.eq(6).text();
							modal.find('.modal-body #teamId').val(teamId)
							modal.find('.modal-body #teamName').val(teamName)
							modal.find('.modal-body #vaildCode').val(vaildCode)
							modal.find('.modal-body #teamDesc').val(teamDesc)
							$('#submitData').val('edit');
							$('#submitData').data('objId', objId);
						} else {
							modal.find('.modal-body #teamId').val(null)
							modal.find('.modal-body #teamName').val(null)
							modal.find('.modal-body #vaildCode').val(null)
							modal.find('.modal-body #teamDesc').val(null)
							$('#submitData').val('add');
						}
						var formData = new FormData();
						formData.append('pageIndex', 1);
						formData.append('pageSize', 50);
						$.ajax({
							url : getCupUrl,
							type : 'POST',
							data : formData,
							contentType : false,
							processData : false,
							cache : false,
							success : function(data) {
								if (data.state == 0) {
									var html = '';
									data.result.map(function(item, index) {
										html += '<option value=' + item.cupId
												+ '>' + item.cupName
												+ '</option>'
									});
									$('#cupId').html(html);
									modal.find('.modal-body #cupId').find(
											"option[value='" + cupId + "']")
											.attr("selected", true);
								} else {
									alert(data.message)
								}
							}
						});
					});
	$('#leaderModal').on(
			'show.bs.modal',
			function(event) {
				var button = $(event.relatedTarget) // Button that triggered the
				// modal
				var modal = $(this)
				var teamId = button.closest('tr').find('td').eq(1).text();
				modal.find('.modal-body #lteamId').val(teamId)
				var formData = new FormData();
				formData.append('pageIndex', 1);
				formData.append('pageSize', 40);
				formData.append('teamId', teamId);
				$.ajax({
					url : getLeaderUrl,
					type : 'POST',
					data : formData,
					contentType : false,
					processData : false,
					cache : false,
					success : function(data) {
						if (data.state == 0) {

							var html = '';
							data.result.map(function(item, index) {
								html += '<option value=' + item.playerId + '>'
										+ item.playerName + '[号码:'
										+ item.playerNum + ']</option>'
							});
							if (html == '') {
								html = '<option>队内暂无球员,请新增球员后重试</option>'
							}
							$('#leaderId').html(html);
						} else {
							alert(data.message)
						}
					}
				});
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
	$('#submitLeader')
			.click(
					function() {
						loading.show();
						var teamId = $('#lteamId').val();
						var leaderId = $('#leaderId').val();
						var formData = new FormData();
						formData.append('teamId', teamId);
						formData.append('leaderId', leaderId);
						$
								.ajax({
									url : editLeaderUrl,
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
											$('#leaderMsg').html(success);
											initData();
											setTimeout(
													function() { // 使用
														// setTimeout（）方法设定定时2000毫秒
														$("#leaderModal")
																.modal('hide');
														$('#leaderMsg').html('');
													}, 500);
										} else {
											var errMsg = '<div class="p-3 mb-2 bg-danger text-white">'
													+ data.message + '</div>'
											$('#leaderMsg').html(errMsg);
											setTimeout(function() { // 使用
												$("#leaderMsg").html('');
											}, 5000);
										}
									}
								});

					});
	$("#teamDesc").on("input propertychange", function() {
		var $this = $(this), _val = $this.val();
		$("#teamDescCount").text(_val.length);
	});

	function delOne() {
		var objId = $('#deleteObj').val();
		var formData = new FormData();
		formData.append('teamId', objId);
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
							setTimeout(function() { //使用  setTimeout（）方法设定定时2000毫秒
								$("#deleteModal").modal('hide');
								$('#delMsg').html('');
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
			+ '			<th colspan="1"><div class="custom-control custom-checkbox"  >'
			+ '<input type="checkbox" class="custom-control-input"    >'
			+ '<label class="custom-control-label" for="cboxhead">全选</label></div></th>'
			+ '			<th colspan="2">球队编号</th>' + '			<th colspan="1">球队名称</th>'
			+ '			<th colspan="1">所属赛事</th>' + '			<th colspan="1">入队验证码</th>'
			+ '			<th colspan="1">队长</th>' + '			<th colspan="2">球队介绍</th>'
			+ '			<th colspan="1">操作</th>' + '			</tr>' + '</thead><tbody>';

	data
			.map(function(item, index) {
				html += '<tr>'
						+ '<td colspan="1">'
						+ '<div class="custom-control custom-checkbox">'
						+ '<input type="checkbox" class="custom-control-input cboxlist" id="checkbox'
						+ index
						+ '" value="'
						+ item.teamId
						+ '">'
						+ '<label class="custom-control-label" for="checkbox'
						+ index
						+ '">'
						+ '			<a href="#" data-toggle="modal" data-target="#inputModal" data-id="edit" data-obj-id="'
						+ item.teamId
						+ '"><i class="edit" /></a>'
						+ '<a href="#" data-toggle="modal" data-target="#deleteModal" data-id="'
						+ item.teamId
						+ '"><i class="delete" /></label>'
						+ '</div>'
						+ '</td>'
						+ '			<td colspan="2">'
						+ item.teamId
						+ '</td>'
						+ '			<td colspan="1">'
						+ item.teamName
						+ '</td>'
						+ '			<td value='
						+ item.cup.cupId
						+ '>'
						+ item.cup.cupName
						+ '</td>'
						+ '			<td colspan="1">'
						+ item.vaildCode
						+ '</td>'
						+ '			<td colspan="1">'
						+ item.leaderName
						+ '</td>'
						+ '<td colspan="2">'
						+ item.teamDesc
						+ '</td>'
						+ '<td colspan="1"><a href="#" data-toggle="modal" data-target="#leaderModal" class="btn btn-primary mr-3" id="cleaderModal">'+(item.leaderName=='无'?'选择队长':'更改队长')+'</a></td>'
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
	//window.parent.setIframeHeight(id);
}
