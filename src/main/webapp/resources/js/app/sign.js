/**
 * 
 */
$(function() {
	var getDepartUrl = '/FootballSystem/admin/service/team/getTeams';
	var addDataUrl = '/FootballSystem/app/service/addPlayer';
	var checkIdUrl = '/FootballSystem/app/service/checkValidId';
	var $tooltips = $('.js_tooltips');
	getData();
	$('#playerStuno').blur(function() {
		var playerStuno = $(this).val();
		if (playerStuno.length < 10) {
			$tooltips.html('请正确输入学号，纯数字10位');
			$tooltips.css('display', 'block');
			setTimeout(function() {
				$tooltips.css('display', 'none');
			}, 2000);
			//必要信息验证失败警告提醒，按钮不可点击
			$(this).parent().parent().addClass("weui-cell_warn");
			setBtnOffClick();
			return;
		}
		//ajax查看学号
		checkId(playerStuno);
		
		//成功取消警告提醒，按钮可点击
		$(this).parent().parent().removeClass("weui-cell_warn");
		setBtnOnClick();
	});
	$('#vaildCode').blur(function() {
		var vaildCode = $(this).val();
		if (vaildCode.length !=4) {
			$tooltips.html('请正确输入入队验证码，4位字母/数字');
			$tooltips.css('display', 'block');
			setTimeout(function() {
				$tooltips.css('display', 'none');
			}, 2000);
			//必要信息验证失败警告提醒，按钮不可点击
			$(this).parent().parent().addClass("weui-cell_warn");
			setBtnOffClick();
			return;
		}
		//成功取消警告提醒，按钮可点击
		$(this).parent().parent().removeClass("weui-cell_warn");
		setBtnOnClick();
	});
	$('#playerTel').blur(function() {
		var playerTel = $(this).val();
		if (playerTel.length !=11) {
			$tooltips.html('请正确输入手机号，纯数字11位');
			$tooltips.css('display', 'block');
			setTimeout(function() {
				$tooltips.css('display', 'none');
			}, 2000);
			//必要信息验证失败警告提醒，按钮不可点击
			$(this).parent().parent().addClass("weui-cell_warn");
			setBtnOffClick();
			return;
		}
		//成功取消警告提醒，按钮可点击
		$(this).parent().parent().removeClass("weui-cell_warn");
		setBtnOnClick();
	});
	$('#submitData').click(function() {
		var vaildCode = $('#vaildCode').val();
		var playerName = $('#playerName').val();
		var playerNum = $('#playerNum').val();
		var teamId = $('#teamId').val();
		var playerStuno = $('#playerStuno').val();
		var playerDepart = $('#playerDepart').val();
		var playerTel = $('#playerTel').val();
		
		var formData = new FormData();
		formData.append('team.vaildCode', vaildCode);
		formData.append('team.teamId', teamId);
		formData.append('player.playerNum', playerNum);
		formData.append('player.playerName', playerName);
		formData.append('playerInfo.playerStuno', playerStuno);
		formData.append('playerInfo.playerDepart', playerDepart);
		formData.append('playerInfo.playerTel', playerTel);
		formData.append('dbCode', 'FootballSystem');
		formData.append('dbType', 'MyBatis'); 

		$.ajax({
			url : addDataUrl,
			type : 'POST',
			data : formData,
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
				if (data.state == 0) {
					$('#successMes').html(data.message);
					$('#successDialog').fadeIn(200);

					setTimeout(function() { // 使用 setTimeout（）方法设定定时2000毫秒
						window.location.reload();
					}, 500);
				} else {
					$('#errorMes').html(data.message);
					$('#failDialog').fadeIn(200);
				}
			}
		});
	});
	function setBtnOnClick() {
		$('#submitData').removeClass('weui-btn_disabled ');
		$('#submitData').removeAttr('disabled');
	}
	function setBtnOffClick() {
		$('#submitData').addClass('weui-btn_disabled ');
		$('#submitData').attr('disabled',"true");
	}
	function getData() {
		var formData = new FormData();
		formData.append('pageIndex', 1);
		formData.append('pageSize', 50);
		formData.append('dbCode', 'FootballSystem');
		formData.append('dbType', 'MyBatis'); 
		$.ajax({
			url : getDepartUrl,
			type : 'POST',
			data : formData,
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
				if (data.state == 0) {
					var html = '';
					data.result.map(function(item, index) {
						html += '<option value=' + item.teamId + '>'
								+ item.teamName + '</option>'
					});
					$('#teamId').html(html);
				} else {
					alert(data.message)
				}
			}
		});
	}
	function checkId(id) {
		 var formData = new FormData();
			formData.append('playerInfo.playerId', id);
			formData.append('dbCode', 'FootballSystem');
			formData.append('dbType', 'MyBatis'); 
		$.ajax({
			url : checkIdUrl,
			type : 'POST',
			data : formData,
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
				if (data.state != 0) {
					$('#playerStuno').parent().parent().addClass("weui-cell_warn");
					$tooltips.html(data.message);
					$tooltips.css('display', 'block');
					setTimeout(function() {
						$tooltips.css('display', 'none');
					}, 2000);
					setBtnOffClick();
				}
			}
		});
	}
	$('.weui-dialog__btn').on('click', function() {
		$(this).parents('.js_dialog').fadeOut(200);
		getData();
	});
});

