/**
 * 
 */
$(function() {
	var getTeamUrl = '/FootballSystem/admin/service/team/getTeams';
	getData();

	function getData() {
		 var formData = new FormData();
			formData.append('pageIndex', 1);
			formData.append('pageSize', 999);
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
					var html='';
					data.result.map(function(item, index) {
						html+='<a class="weui-cell weui-cell_access" href="/FootballSystem/app/view/teamdetail?itemid='+item.teamId+'">'
								+'<div class="weui-cell__bd">'
								+'<p>'+item.teamName+'</p>'
								+'</div>'
								+'<div class="weui-cell__ft"></div>'
								+'</a>';
					});
					$('#teamList').html(html);
				} else {
					alert(data.message)
				}
			}
		});
	}
	
});

