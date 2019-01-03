/**
 * 
 */
$(function() {
	var getTeamUrl = '/FootballSystem/app/service/getTeamDetail';
	var teamId = getQueryString('itemid');
	getData();

	function getData() {
		var formData = new FormData();
		formData.append('teamId', teamId);
		formData.append('dbCode', 'FootballSystem');
		formData.append('dbType', 'MyBatis'); 
		$.ajax({
			url : getTeamUrl,
			type : 'POST',
			data : formData,
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
				if (data.state == 0) {
					var obj = data.result;
					$('#teamName').html(obj.teamName);
					$('#vaildCode').html(obj.vaildCode);
					$('#cupName').html(obj.cup.cupName);
					$('#leaderName').html(
							obj.leader == null ? '无' : obj.leader.playerName);
					$('#teamDesc').html(obj.teamDesc);

				} else {
					alert(data.message)
				}
			}
		});
	}
	function getQueryString(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null)
			return unescape(r[2]);
		return null;
	}

});
