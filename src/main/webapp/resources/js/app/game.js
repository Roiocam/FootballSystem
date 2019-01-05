var getCupUrl = '/FootballSystem/admin/service/cup/getCups';
var getUrl = '/FootballSystem/admin/service/game/getGames';
$(function() {
	getCupData();
	function getCupData() {

		var formData = new FormData();
		formData.append('pageIndex', 1);
		formData.append('pageSize', 10);
		formData.append('dbCode', 'FootballSystem');
		formData.append('dbType', 'MyBatis'); 

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
		var _this = $(this)[0].selectedOptions[0];
		var grouped = _this.attributes.grouped.value;
		$('#chooseSelect').attr('disabled', 'true');
		var cupId = _this.value;
		if (grouped == 1) {
			getGameData(cupId);
		} else {
			alert('该赛事下无赛程记录,请选择其他赛事!');
		}

	});
	function getGameData(cupId) {

		var formData = new FormData();
		formData.append('pageIndex', 1);
		formData.append('pageSize', 50);
		formData.append('cup.cupId', cupId);
		formData.append('dbCode', 'FootballSystem');
		formData.append('dbType', 'MyBatis'); 
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
				} else {
					alert(data.message)
				}
			}
		});
	}
	function handlerList(data) {
		var html = '';
		data
				.map(function(item, index) {
					html += '<div class="weui-panel weui-panel_access">'
							+ '	<div class="weui-cell">'
							+ '<div class="weui-cell__hd"'
							+ '	style="position: relative; margin-right: 10px;">'
							+ '	<img src="/FootballSystem/resources/image/icon.png"'
							+ '		style="width: 50px; display: block">'
							+ '</div>' + '<div class="weui-cell__bd">'
							+ '	<h1 class="weui-media-box__title">'
							+ item.home.teamName
							+ ' vs '
							+ item.away.teamName
							+ '</h1>'
							+ '	<p class="weui-media-box__desc">'
							+ (item.gameDate).substring(0, 16)
							+ '</p>'
							+ '</div>'
							+ '<div class="weui-cell__hd"'
							+ '	style="position: relative; margin-left: 10px;">'
							+ '	<img src="/FootballSystem/resources/image/icons8-round-256.png"'
							+ '		style="width: 50px; display: block">'
							+ '</div>'
							+ '</div>'
							+ '<div class="weui-panel__ft">'
							+ '<a href="javascript:void(0);"'
							+ '	class="weui-cell weui-cell_access weui-cell_link">'
							+ '	<div class="weui-cell__bd">进入文字直播</div> <span'
							+ '	class="weui-cell__ft"></span>'
							+ '</a>'
							+ '</div>' + '</div>';
				});
		$('#datalist').html(html);

	}
});