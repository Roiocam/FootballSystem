/**
 * 
 */
$(function() {
	// var id = window.parent.getId(this);
	var getCupUrl = '/FootballSystem/admin/service/cup/getCups';
	var randomUrl = '/FootballSystem/admin/service/cup/randomTeamToGroup';
	var getGroupUrl = '/FootballSystem/admin/service/team/getTeamGroup';
	var randomGameUrl = '/FootballSystem/admin/service/cup/randomGameByGroup';
	var delUrl='/FootballSystem/admin/service/cup/removeGroupByCup';
	var loading=new LoadingUtils();
	loading.append();
	getCupData();
	function getCupData() {

		var formData = new FormData();
		formData.append('pageIndex', 1);
		formData.append('pageSize', 10);

		$
				.ajax({
					url : getCupUrl,
					type : 'POST',
					data : formData,
					contentType : false,
					processData : false,
					cache : false,
					success : function(data) {
						if (data.state == 0) {
							var html = '<option value="" id="chooseSelect">选择赛事</option>';
							data.result.map(function(item, index) {
								html += '<option value="' + item.cupId
										+ '" grouped="' + item.isGroup + '">'
										+ item.cupName + '</option>'
							});
							$('#cupId').html(html);
						} else {
							alert(data.message)
						}
					}
				});
	}
	$("select[name='cupId']").change(function() {
		var selected = $(this).children('option:selected'); // 这就是selected的值
		$('#chooseSelect').attr('disabled', 'true');
		var cupId = selected.val();
		var grouped = selected.attr('grouped');
		if (grouped == 1) {
			$('#groupBtn').attr('disabled', 'true');
			$('#delBtn').removeAttr('disabled');
			getGroupData(cupId);
		} else {
			$('#errModal').modal('show');
			$('#groupBtn').removeAttr('disabled');
			$('#delBtn').attr('disabled', 'true');
			setTimeout(() => {
				$('#errModal').modal('hide');
			}, 1000);
		}

	});
	function getGroupData(cupId) {
		var cupId = $('#cupId').val();
		var formData = new FormData();
		formData.append('cupId', cupId);
		$.ajax({
			url : getGroupUrl,
			type : 'POST',
			data : formData,
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
				if (data.state == 0) {
					var htmlA = '<div class="card-group bg-gray">A</div>';
					var htmlB = '<div class="card-group bg-gray">B</div>';
					var htmlC = '<div class="card-group bg-gray">C</div>';
					data.result.map(function(item, index) {
						var html = '<div class="card-group">' + item.teamName
								+ '</div>';
						if (item.teamGroup == 'A') {
							htmlA += html;
						} else if (item.teamGroup == 'B') {
							htmlB += html
						} else {
							htmlC += html
						}
					});
					$('#groupA').html(htmlA);
					$('#groupB').html(htmlB);
					$('#groupC').html(htmlC);

				} else {
					alert(data.message);
				}
			}
		});
	}
	$('#groupBtn').click(function() {
		
		loading.show();
		$('#modalBody').html('正在随机分组中,请稍等...');
		var cupId = $('#cupId').val();
		var formData = new FormData();
		formData.append('cupId', cupId);
		$.ajax({
			url : randomUrl,
			type : 'POST',
			data : formData,
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
				if (data.state == 0) {
					$('#modalBody').html(data.message);
					$('#messageBtn').removeAttr('disabled');
					loading.hide();
				} else {
					loading.hide();
					$('#modalBody').html(data.message);
					setTimeout(function(){  //使用  setTimeout（）方法设定定时2000毫秒
						$("#messageModal").modal('hide'); 
					},2000);
				}
			}
		});
		
		
	});
	$('#deleteObj').click(function(){
		
		loading.show();
		var cupId=$('#cupId');
		var formData=new FormData();
		formData.append('cupId',cupId)
		$.ajax({
			url : delUrl,
			type : 'POST',
			data : formData,
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
				loading.hide();
				if (data.state==0) {
					var success ='<div class="p-3 mb-2 bg-success text-white">删除成功</div>'
					$('#delMsg').html(success);
					setTimeout(function(){  //使用  setTimeout（）方法设定定时2000毫秒
						$("#deleteModal").modal('hide'); 
						$('#delMsg').html('');
						window.location.reload();
					},1000);
				} else {
					var errMsg ='<div class="p-3 mb-2 bg-danger text-white">'+data.message+'</div>'
					$('#delMsg').html(errMsg);
					setTimeout(function(){  //使用  setTimeout（）方法设定定时2000毫秒
						$("#deleteModal").modal('hide'); 
						$('#delMsg').html('');
					},2000);
				}
				}
			});
		
	});
	$('#messageBtn').click(function() {
		// 显示加载gif
		
		loading.show();
		$('#modalBody').html('正在安排赛程中,请稍等...');
		var cupId = $('#cupId').val();
		var formData = new FormData();
		formData.append('cupId', cupId);
		$.ajax({
			url : randomGameUrl,
			type : 'POST',
			data : formData,
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
				if (data.state == 0) {
					$('#modalBody').html(data.message);
					$('#messageBtn').attr('disabled','true');
					loading.hide();
					setTimeout(function(){  //使用  setTimeout（）方法设定定时2000毫秒
						$("#messageModal").modal('hide'); 
					},2000);
				} else {
					loading.hide();
					$('#modalBody').html(data.message);
					setTimeout(function(){  //使用  setTimeout（）方法设定定时2000毫秒
						$("#messageModal").modal('hide'); 
					},2000);
				}
			}
		});
		
	});

});
