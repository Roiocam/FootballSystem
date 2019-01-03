/**
 * 
 */
$(function() {
	var getDataUrl = '/FootballSystem/app/service/getSigned';
	getData();


	function getData() {
		var formData=new FormData();
		formData.append('dbCode', 'FootballSystem');
		formData.append('dbType', 'JPA');
		 $.ajax({
				url : getDataUrl,
				type : 'POST',
				data:formData,
				contentType : false,
				processData : false,
				cache : false,
				success : function(data) {
					if (data.state == 0) {
						var result=data.result;
						$('#teamName').html(result.team.teamName);
						$('#playerName').html(result.playerName);
						$('#playerNum').html(result.playerNum);
						$('#playerStuno').html(result.info.playerStuno);
						$('#playerDepart').html(result.info.playerDepart);
						$('#playerTel').html(result.info.playerTel);
				
					} else {
						alert(data.message)
					}
				}
			});
	}
	$('.weui-dialog__btn').on('click', function() {
		$(this).parents('.js_dialog').fadeOut(200);
		getData();
	});
});
function timeclock() {
	var datetime = new Date();
	var time = datetime.toLocaleTimeString();
	var date = datetime.toLocaleDateString();
	$('#time').html(date + "&nbsp" + time);

}

timeclock();
setInterval(function() {
	timeclock()
}, 1000);
