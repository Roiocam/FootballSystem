/**
 * 
 */
$(function() {
	var getDataUrl = '/FootballSystem/app/service/getKick';
	var addDataUrl = '/FootballSystem/app/service/plusKick';
	var reduceDataUrl = '/FootballSystem/app/service/reduceKick';
	getData();
	$('#sumbitKick').on("click", function() {
		$.ajax({
			url : addDataUrl,
			type : 'POST',
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
				if (data.state==0) {
					$('#successMes').html(data.message);
					$('#successDialog').fadeIn(200);
				} else {
					$('#errorMes').html(data.message);
					$('#failDialog').fadeIn(200);
				}
			}
		});
	});
	$('#cancelKick').on("click", function() {
		$.ajax({
			url : reduceDataUrl,
			type : 'POST',
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
				if (data.state==0) {

					$('#successMes').html(data.message);
					$('#successDialog').fadeIn(200);
				} else {
					$('#errorMes').html(data.message);
					$('#failDialog').fadeIn(200);
				}
			}
		});
	});
	function getData() {
		$.ajax({
			url : getDataUrl,
			type : 'GET',
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
				if (data.state==0) {
					var num = data.result.obj.num;
					$('#kickNum').html(num);
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
