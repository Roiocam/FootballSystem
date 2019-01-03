/**
 * 
 */
$(function() {
	var getDataUrl = '/FootballSystem/app/service/getCreated';
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
						var resultOne=data.resultOne;
						var resultTwo=data.resultTwo;
						$('#teamDesc').html(resultOne.teamDesc);
						$('#vaildCode').html(resultOne.vaildCode);
						$('#teamName').html(resultOne.teamName);
						$('#playerName').html(resultTwo.playerName);
						$('#playerNum').html(resultTwo.playerNum);
						$('#playerStuno').html(resultTwo.info.playerStuno);
						$('#playerDepart').html(resultTwo.info.playerDepart);
						$('#playerTel').html(resultTwo.info.playerTel);
				
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
