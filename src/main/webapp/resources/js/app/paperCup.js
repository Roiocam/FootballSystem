/**
 * 
 */
$(function() {
	// var id = window.parent.getId(this);
	var getCupUrl = '/FootballSystem/admin/service/cup/getCups';
	var getGroupUrl = '/FootballSystem/admin/service/team/getTeamGroup';
	
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
		var selected = $(this).children('option:selected').val(); // 这就是selected的值
		$('#chooseSelect').attr('disabled', 'true');
		getGroupData(selected);
		
	});
	function getGroupData(cupId) {
		var cupId = $('#cupId').val();
		var formData = new FormData();
		formData.append('team.cupId', cupId);
		formData.append('dbCode', 'FootballSystem');
		formData.append('dbType', 'MyBatis'); 
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
	
});
