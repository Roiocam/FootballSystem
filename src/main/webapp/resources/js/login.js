/**
 * 
 */
$(function() {
	var oForm = document.getElementById('login');
	var oUser = document.getElementById('inputUser');
	var oPswd = document.getElementById('inputPassword');
	var oRemember = document.getElementById('remember');
	$('#login').on("click",function() {
		// 表单提交事件触发时，如果复选框是勾选状态则保存cookie
		if (remember.checked) {
			setCookie('user', oUser.value, 7); // 保存帐号到cookie，有效期7天
			setCookie('password', oPswd.value, 7); // 保存密码到cookie，有效期7天
		}
		var userStr = {}
		userStr.username = $('#inputUser').val();
		userStr.password = $('#inputPassword').val();
		var formData = new FormData();
		var loginUrl = '/FootballSystem/admin/service/loginCheck';
		formData.append('userStr', JSON.stringify(userStr));
		formData.append('dbCode', 'FootballSystem');
		formData.append('dbType', 'JPA');
		$.ajax({
			url : loginUrl,
			type : 'POST',
			data : formData,
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
										if (data.state==0) {
											window.location.href = '/FootballSystem/admin/view/index';
										} else {
											 errMsg = '<div class="alert alert-danger" role="alert">'
													+ data.message + '</div>';
											$('#errMsg').html(errMsg);
										}
									}
								});
					});
	
	// 页面初始化时，如果帐号密码cookie存在则填充
	if (getCookie('user') && getCookie('password')) {
		oUser.value = getCookie('user');
		oPswd.value = getCookie('password');
		oRemember.checked = true;
	}
	// 复选框勾选状态发生改变时，如果未勾选则清除cookie
	oRemember.onchange = function() {
		if (!this.checked) {
			delCookie('user');
			delCookie('password');
		}
	};
	
	// 设置cookie
	function setCookie(name, value, day) {
		var date = new Date();
		date.setDate(date.getDate() + day);
		document.cookie = name + '=' + value + ';expires=' + date;
	}
	;

	// 获取cookie
	function getCookie(name) {
		var reg = RegExp(name + '=([^;]+)');
		var arr = document.cookie.match(reg);
		if (arr) {
			return arr[1];
		} else {
			return '';
		}
	}
	;
	// 删除cookie
	function delCookie(name) {
		setCookie(name, null, -1);
	}
	;
});
