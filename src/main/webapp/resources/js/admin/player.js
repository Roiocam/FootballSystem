/**
 * 
 * 
 */
var getTeamUrl = '/FootballSystem/admin/service/team/getTeams';
var getUrl = '/FootballSystem/admin/service/player/getPlayers';
var addUrl = '/FootballSystem/admin/service/player/addPlayer';
var editUrl = '/FootballSystem/admin/service/player/editPlayer';
var delOneUrl = '/FootballSystem/admin/service/player/deletePlayer';
var delListUrl = '/FootballSystem/admin/service/player/deletePlayerList';
var searchData = null;

$(function() {
	// var id = window.parent.getId(this);
	var loading = new LoadingUtils();
	loading.append();

	initData();
	getTeamData();

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
						var playerId = $('#playerId').val();
						var playerName = $('#playerName').val();
						var playerNum = $('#playerNum').val();
						var teamId = $('#teamId').val();
						var playerStuno = $('#playerStuno').val();
						var playerDepart = $('#playerDepart').val();
						var playerTel = $('#playerTel').val();
						if (playerStuno.length < 10) {
							$('#errMsg')
									.html(
											'<div class="p-3 mb-2 bg-danger text-white">请正确输入学号，纯数字10位</div>');
							return;
						}
						if (playerNum.length > 2) {
							$('#errMsg')
									.html(
											'<div class="p-3 mb-2 bg-danger text-white">请正确输入球衣号码</div>');
							return;
						}
						if (playerTel.length < 11) {
							$('#errMsg')
									.html(
											'<div class="p-3 mb-2 bg-danger text-white">请正确输入手机号，纯数字11位</div>');
							return;
						}
						loading.show();

						var formData = new FormData();
						formData.append('playerId', playerId);
						formData.append('teamId', teamId);
						formData.append('playerNum', playerNum);
						formData.append('playerName', playerName);
						formData.append('playerStuno', playerStuno);
						formData.append('playerDepart', playerDepart);
						formData.append('playerTel', playerTel);
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
						var modal = $(this)
						var type = button.data('id');
						var objId = button.data('objId');
						var formData = new FormData();
						formData.append('pageIndex', 1);
						formData.append('pageSize', 50);

						if (type == 'edit') {
							var playerId = button.closest('tr').find('td')
									.eq(1).text();
							var playerName = button.closest('tr').find('td')
									.eq(2).text();
							var playerNum = button.closest('tr').find('td').eq(
									3).text();
							var teamId = button.closest('tr').find('td').eq(4)
									.attr('value');
							var playerStuno = button.closest('tr').find('td')
									.eq(5).text();
							var playerDepart = button.closest('tr').find('td')
									.eq(6).text();
							var playerTel = button.closest('tr').find('td').eq(
									7).text();
							modal.find('.modal-body #playerId').val(playerId)
							modal.find('.modal-body #playerName').val(
									playerName)
							modal.find('.modal-body #playerNum').val(playerNum)
							modal.find('.modal-body #playerStuno').val(
									playerStuno)
							modal.find('.modal-body #playerDepart').val(
									playerDepart)
							modal.find('.modal-body #playerTel').val(playerTel)
							$('#submitData').val('edit');
							$('#submitData').data('objId', objId);
						} else {
							modal.find('.modal-body #playerId').val(null)
							modal.find('.modal-body #playerName').val(null)
							modal.find('.modal-body #playerNum').val(null)
							modal.find('.modal-body #teamId').val(null)
							modal.find('.modal-body #playerStuno').val(null)
							modal.find('.modal-body #playerTel').val(null)
							$('#submitData').val('add');
						}
						$.ajax({
							url : getTeamUrl,
							type : 'POST',
							data : formData,
							contentType : false,
							processData : false,
							cache : false,
							success : function(data) {
								if (data.state == 0) {
									var html = '';
									data.result.map(function(item, index) {
										html += '<option value=' + item.teamId
												+ '>' + item.teamName
												+ '</option>'
									});
									$('#teamId').html(html);
									modal.find('.modal-body #teamId').find(
											"option[value='" + teamId + "']")
											.attr("selected", true);
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
	$('#searchBtn').click(function() {
		searchData = new Object();
		searchData.playerName = $('#searchName').val();
		searchData.teamId = $('#searchTeam').val();
		initData();
	});
	function getTeamData() {
		var formData = new FormData();
		formData.append('pageIndex', 1);
		formData.append('pageSize', 50);
		$.ajax({
			url : getTeamUrl,
			type : 'POST',
			data : formData,
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
				if (data.state == 0) {
					var html = '<option value="">选择球队</option>';
					data.result.map(function(item, index) {
						html += '<option value=' + item.teamId + '>'
								+ item.teamName + '</option>'
					});
					$('#searchTeam').html(html);
					
				} else {
					alert(data.message)
				}
			}
		});
	}
	function delOne() {
		var objId = $('#deleteObj').val();
		var formData = new FormData();
		formData.append('playerId', objId);
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
	if (searchData != null) {
		formData.append('teamId', searchData.teamId);
		formData.append('playerName', searchData.playerName);
	}
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
			+ '			<th>球员编号</th>' + '			<th>球员名称</th>' + '			<th>球衣号码</th>'
			+ '			<th>所属球队</th>' + '			<th>学号</th>' + '			<th>学院</th>'
			+ '			<th>联系方式</th>' + '			</tr>' + '</thead><tbody>';
	data
			.map(function(item, index) {
				html += '<tr>'
						+ '<td>'
						+ '<div class="custom-control custom-checkbox">'
						+ '<input type="checkbox" class="custom-control-input cboxlist" id="checkbox'
						+ index
						+ '" value="'
						+ item.playerId
						+ '">'
						+ '<label class="custom-control-label" for="checkbox'
						+ index
						+ '">'
						+ '			<a href="#" data-toggle="modal" data-target="#inputModal" data-id="edit" data-obj-id="'
						+ item.playerId
						+ '"><i class="edit" /></a>'
						+ '<a href="#" data-toggle="modal" data-target="#deleteModal" data-id="'
						+ item.playerId
						+ '"><i class="delete" /></label>'
						+ '</div>'
						+ '</td>'
						+ '			<td>'
						+ item.playerId
						+ '</td>'
						+ '			<td>'
						+ item.playerName
						+ '</td>'
						+ '			<td>'
						+ item.playerNum
						+ '</td>'
						+ '			<td value='
						+ item.team.teamId
						+ '>'
						+ item.team.teamName
						+ '</td>'
						+ '			<td>'
						+ item.info.playerStuno
						+ '</td>'
						+ '			<td>'
						+ item.info.playerDepart
						+ '</td>'
						+ '			<td>'
						+ item.info.playerTel + '</td>' + '			</tr>';
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
function tableToExcel(){
	var teamId=$('#searchTeam').val();
	if(teamId==''){
		alert('请选择球队')
		return
	}
	window.location.href='/FootballSystem/admin/view/word?teamId='+teamId;
  }
