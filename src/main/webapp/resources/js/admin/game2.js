/**
 * 
 * 
 */
var getCupUrl = '/FootballSystem/admin/service/cup/getCups';
var getUrl = '/FootballSystem/admin/service/game/getGames';
$(function() {
	// var id = window.parent.getId(this);
	var loading = new LoadingUtils();
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
			getGameData(cupId);
		} else {
			$('#errModal').modal('show');
			setTimeout(() => {
				$('#errModal').modal('hide');
			}, 1000);
		}

	});
});
function getGameData(cupId) {

	var formData = new FormData();
	formData.append('pageIndex', pageIndex);
	formData.append('pageSize', pageSize);
	formData.append('cupId', cupId);
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
			+ '			<th>比赛编号</th>' + '			<th>比赛日期</th>' + '			<th>主队</th>'
			+ '			<th>客队</th>' + '			<th>所属赛事</th>' 
			+'	</tr>' + '</thead><tbody>';
	data
			.map(function(item, index) {
				html += '<tr>'
						+ '			<td>'
						+ item.gameId
						+ '</td>'
						+ '			<td>'
						+ (item.gameDate).substring(0,16)
						+ '</td>'
						+ '			<td>'
						+ item.teamHome
						+ '</td>'
						+ '			<td>'
						+ item.teamAway
						+ '</td>'
						+ '			<td>'
						+ item.cupName
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
