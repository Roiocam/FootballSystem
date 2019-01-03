/**
 * 
 */
$(function() {
	var getDepartUrl = '/FootballSystem/admin/service/cup/getCups';
	var addDataUrl = '/FootballSystem/app/service/createTeam';
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
			// 必要信息验证失败警告提醒，按钮不可点击
			$(this).parent().parent().addClass("weui-cell_warn");
			setBtnOffClick();
			return;
		}
		// ajax查看学号
		checkId(playerStuno);

		// 成功取消警告提醒，按钮可点击
		$(this).parent().parent().removeClass("weui-cell_warn");
		setBtnOnClick();

	});
	$('#playerTel').blur(function() {
		var playerTel = $(this).val();
		if (playerTel.length != 11) {
			$tooltips.html('请正确输入手机号，纯数字11位');
			$tooltips.css('display', 'block');
			setTimeout(function() {
				$tooltips.css('display', 'none');
			}, 2000);
			// 必要信息验证失败警告提醒，按钮不可点击
			$(this).parent().parent().addClass("weui-cell_warn");
			setBtnOffClick();
			return;
		}
		// 成功取消警告提醒，按钮可点击
		$(this).parent().parent().removeClass("weui-cell_warn");
		setBtnOnClick();
	});
	$('#vaildCode').blur(function() {
		var vaildCode = $(this).val();
		if (vaildCode.length != 4) {
			$tooltips.html('请正确输入入队验证码，4位字母/数字');
			$tooltips.css('display', 'block');
			setTimeout(function() {
				$tooltips.css('display', 'none');
			}, 2000);
			// 必要信息验证失败警告提醒，按钮不可点击
			$(this).parent().parent().addClass("weui-cell_warn");
			setBtnOffClick();
			return;
		}
		// 成功取消警告提醒，按钮可点击
		$(this).parent().parent().removeClass("weui-cell_warn");
		setBtnOnClick();
	});
	$('#teamDesc').blur(function() {
		var teamDesc = $(this).val();
		if (teamDesc.length > 255) {
			$tooltips.html('入队介绍过长');
			$tooltips.css('display', 'block');
			setTimeout(function() {
				$tooltips.css('display', 'none');
			}, 2000);
			// 必要信息验证失败警告提醒，按钮不可点击
			$(this).parent().parent().addClass("weui-cell_warn");
			setBtnOffClick();
			return;
		}
		// 成功取消警告提醒，按钮可点击
		$(this).parent().parent().removeClass("weui-cell_warn");
		setBtnOnClick();
	});
	$('#submitData').click(function() {
		var cupId = $('#cupId').val();
		var teamName = $('#teamName').val();
		var vaildCode = $('#vaildCode').val();
		var teamDesc = $('#teamDesc').val();
		var playerName = $('#playerName').val();
		var playerNum = $('#playerNum').val();
		var playerStuno = $('#playerStuno').val();
		var playerDepart = $('#playerDepart').val();
		var playerTel = $('#playerTel').val();

		if (teamName == "" || playerName == "" || playerNum == "") {
			$tooltips.html('请完整填写所有内容');
			$tooltips.css('display', 'block');
			setTimeout(function() {
				$tooltips.css('display', 'none');
			}, 2000);
			return;
		}
		if (vaildCode == "" || vaildCode.length < 4) {
			$tooltips.html('请正确输入入队验证码,4位字符');
			$tooltips.css('display', 'block');
			setTimeout(function() {
				$tooltips.css('display', 'none');
			}, 2000);
			return;
		}
		if (playerStuno.length < 10) {
			$tooltips.html('请正确输入学号，纯数字10位');
			$tooltips.css('display', 'block');
			setTimeout(function() {
				$tooltips.css('display', 'none');
			}, 2000);
			return;
		}
		if (playerTel.length < 11) {
			$tooltips.html('请正确输入手机号，纯数字11位');
			$tooltips.css('display', 'block');
			setTimeout(function() {
				$tooltips.css('display', 'none');
			}, 2000);
			return;
		}
		var formData = new FormData();
		formData.append('cupId', cupId);
		formData.append('teamName', teamName);
		formData.append('vaildCode', vaildCode);
		formData.append('teamDesc', teamDesc);
		formData.append('playerNum', playerNum);
		formData.append('playerName', playerName);
		formData.append('playerStuno', playerStuno);
		formData.append('playerDepart', playerDepart);
		formData.append('playerTel', playerTel);
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
		$('#submitData').attr('disabled', "true");
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
						html += '<option value=' + item.cupId + '>'
								+ item.cupName + '</option>'
					});
					$('#cupId').html(html);
				} else {
					alert(data.message)
				}
			}
		});
	}
	function checkId(id) {
		var formData = new FormData();
		formData.append('playerId', id);
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
					$('#playerStuno').parent().parent().addClass(
							"weui-cell_warn");
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
