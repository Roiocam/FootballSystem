/**
 * 
 * 
 */
const getUrl = '/FootballSystem/admin/service/user/getUsers';
const addUrl = '/FootballSystem/admin/service/user/addUser';
const editUrl = '/FootballSystem/admin/service/user/editUser';
const delOneUrl = '/FootballSystem/admin/service/user/deleteUser';
const delListUrl = '/FootballSystem/admin/service/user/deleteUserList';
const loading = new LoadingUtils();
loading.append();
var app = new Vue(
	{
		el: '#app',
		data: {
			message: '',
			datalist: [],
			message: '',
			err: false,
			messageShow: false,
		},
		computed: {
			msgClass: function () {
				return {
					'bg-success': !this.err,
					'bg-danger': this.err,
				}
			}
		},
		methods: {
			initData: function () {
				var formData = new FormData();
				formData.append('pageIndex', pageIndex);
				formData.append('pageSize', pageSize);
				formData.append('dbCode', 'FootballSystem');
				formData.append('dbType', 'MyBatis');
				const that = this
				axios
					.post(
						getUrl,
						formData)
					.then(
						function (res) {
							if (res.data.state == 0) {
								that.datalist = res.data.result
								initPageCount(res.data.count);
								initPageData();
							} else {
								alert(res.data.message)
							}
						});
			}
		},
		mounted: function () {
			this.initData()
		}
	})
$(function () {
	$('#submitData')
		.click(
			function () {
				loading.show();
				var type = $('#submitData').val();
				var oldObjId = $('#submitData').data('objId');
				var urlStr = '';
				if (type == 'edit') {
					urlStr = editUrl;
				} else {
					urlStr = addUrl;
				}
				var username = $('#username').val();
				var password = $('#password').val();
				var formData = new FormData();
				formData.append('username', username);
				formData.append('password', password);
				formData.append('dbCode', 'FootballSystem');
				formData.append('dbType', 'MyBatis');
				$
					.ajax({
						url: urlStr,
						type: 'POST',
						data: formData,
						contentType: false,
						processData: false,
						cache: false,
						success: function (data) {
							loading.hide();
							app.messageShow = true;
							app.message = data.message;
							if (data.state == 0) {
								app.err = false;
								app.initData();
								setTimeout(function () { // 使用
									// setTimeout（）方法设定定时2000毫秒
									$("#inputModal").modal('hide');
									app.message = '';
									app.messageShow = false;
								}, 500);
							} else {
								app.err = true;
								setTimeout(function () { // 使用
									app.message = '';
									app.messageShow = false;
								}, 5000);
							}
						}
					});

			});

	$('#inputModal').on('show.bs.modal', function (event) {
		var button = $(event.relatedTarget) // Button that triggered the modal
		var modal = $(this)
		var type = button.data('id');
		var objId = button.data('objId');
		if (type == 'edit') {
			var username = button.closest('tr').find('td').eq(1).text();
			var password = button.closest('tr').find('td').eq(2).text();
			modal.find('.modal-body #username').val(username)
			modal.find('.modal-body #password').val(password)
			$('#username').attr('readonly', 'true');
			$('#submitData').val('edit');
			$('#submitData').data('objId', objId);
		} else {
			modal.find('.modal-body #username').val(null)
			modal.find('.modal-body #password').val(null)
			$('#username').removeAttr("readonly");
			$('#submitData').val('add');
		}
	});
	$('#deleteModal').on('show.bs.modal', function (event) {
		var button = $(event.relatedTarget) // Button that triggered the modal
		var modal = $(this)
		var objId = button.data('id');
		$('#deleteObj').val(objId);
		var type = button.data('type');
		$('#deleteObj').data('type', type);
	});

	$('#deleteObj').click(function () {
		loading.show();
		var type = $('#deleteObj').data('type');
		if (type == 'list') {
			delList();
		} else {
			delOne();
		}

	});

	function delOne() {
		var objId = $('#deleteObj').val();
		var formData = new FormData();
		formData.append('username', objId);
		formData.append('dbCode', 'FootballSystem');
		formData.append('dbType', 'MyBatis');
		$
			.ajax({
				url: delOneUrl,
				type: 'POST',
				data: formData,
				contentType: false,
				processData: false,
				cache: false,
				success: function (data) {
					loading.hide();
					app.messageShow = true;
					app.message = data.message;
					if (data.state == 0) {
						app.err = false;
						app.initData();
						setTimeout(function () { // 使用
							// setTimeout（）方法设定定时2000毫秒
							$("#deleteModal").modal('hide');
							app.message = '';
							app.messageShow = false;
						}, 500);
					} else {
						app.err = true;
						setTimeout(function () { // 使用
							// setTimeout（）方法设定定时2000毫秒
							$("#deleteModal").modal('hide');
							app.message = '';
							app.messageShow = false;
						}, 2000);
					}
				}
			});
	}
	function delList() {
		var list = [];
		$(".cboxlist").each(function (index, element) {
			if (element.checked == true) {
				list.push(element.value);
			}
		})
	var formData = new FormData();
	formData.append('dbCode', 'FootballSystem');
	formData.append('dbType', 'MyBatis');
	formData.append('list', JSON.stringify(list));
		$
			.ajax({
				url: delListUrl,
				type : 'POST',
				data : formData,
				contentType : false,
				processData : false,
				cache : false,
				success: function (data) {
					loading.hide();
					app.messageShow = true;
					app.message = data.message;
					if (data.state == 0) {
						app.err = false;
						app.initData();
						setTimeout(function () { // 使用
							// setTimeout（）方法设定定时2000毫秒
							$("#deleteModal").modal('hide');
							app.message = '';
							app.messageShow = false;
						}, 500);
					} else {
						app.err = true;
						setTimeout(function () { // 使用
							// setTimeout（）方法设定定时2000毫秒
							$("#deleteModal").modal('hide');
							app.message = '';
							app.messageShow = false;
						}, 2000);
					}
				}
			});
	}
});


