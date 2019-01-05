/**
 * 
 */
const getCupUrl = '/FootballSystem/admin/service/cup/getCups';
const randomUrl = '/FootballSystem/admin/service/cup/randomTeamToGroup';
const getGroupUrl = '/FootballSystem/admin/service/team/getTeamGroup';
const randomGameUrl = '/FootballSystem/admin/service/cup/randomGameByGroup';
const delUrl = '/FootballSystem/admin/service/cup/removeGroupByCup';
var loading = new LoadingUtils();
loading.append();
var app = new Vue(
	{
		el: '#app',
		data: {
			datalist: [],
			cuplist: [],
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
		mounted: function () {
			loading.append();
			this.initData()
			this.err = false
		},
		methods: {
			initData: function () {
				var formData = new FormData();
				formData.append('pageIndex', 1);
				formData.append('pageSize', 10);
				formData.append('dbCode', 'FootballSystem');
				formData.append('dbType', 'MyBatis'); 
				const that = this
				axios
					.post(
						getCupUrl,
						formData)
					.then(
						function (res) {
							if (res.data.state == 0) {
								that.cuplist = res.data.result
							} else {
								alert(res.data.message)
							}
						});
			},
			getGroupData: function (cupId) {
				var cupId = $('#cupId').val();
				var formData = new FormData();
				formData.append('cup.cupId', cupId);
				formData.append('dbCode', 'FootballSystem');
				formData.append('dbType', 'MyBatis'); 
				const that = this
				axios
					.post(
						getGroupUrl,
						formData)
					.then(
						function (res) {
							if (res.data.state == 0) {
								that.datalist = res.data.result
							} else {
								alert(res.data.message)
							}
						});
			},

		},

	})
$(function () {
	$("select[name='cupId']").change(function () {
		var selected = $(this).children('option:selected'); // 这就是selected的值
		$('#chooseSelect').attr('disabled', 'true');
		var cupId = selected.val();
		var grouped = selected.attr('grouped');
		if (grouped == 1) {
			$('#groupBtn').attr('disabled', 'true');
			$('#delBtn').removeAttr('disabled');
			app.getGroupData(cupId);
		} else {
			$('#errModal').modal('show');
			$('#groupBtn').removeAttr('disabled');
			$('#delBtn').attr('disabled', 'true');
			setTimeout(() => {
				$('#errModal').modal('hide');
			}, 1000);
		}

	});
	$('#groupBtn').click(function () {
		loading.show();
		$('#modalBody').html('正在随机分组中,请稍等...');
		var cupId = $('#cupId').val();
		var formData = new FormData();
		formData.append('dbCode', 'FootballSystem');
		formData.append('dbType', 'MyBatis'); 
		formData.append('cup.cupId', cupId);
		$.ajax({
			url: randomUrl,
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
					$('#messageBtn').removeAttr('disabled');

				} else {
					app.err = true;
					setTimeout(function () {  //使用  setTimeout（）方法设定定时2000毫秒
						$("#messageModal").modal('hide');
						app.message = '';
						app.messageShow = false;
					}, 2000);
				}
			}
		});


	});
	$('#deleteObj').click(function () {
		loading.show();
		var cupId = $('#cupId').val();
		var formData = new FormData();
		formData.append('cup.cupId', cupId)
		formData.append('dbCode', 'FootballSystem');
			formData.append('dbType', 'MyBatis'); 
		$.ajax({
			url: delUrl,
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
					$('#messageBtn').removeAttr('disabled');
					setTimeout(function () {  //使用  setTimeout（）方法设定定时2000毫秒
						$("#deleteModal").modal('hide');
						app.message = '';
						app.messageShow = false;
						window.location.reload();
					}, 1000);
				} else {
					app.err = true;
					setTimeout(function () {  //使用  setTimeout（）方法设定定时2000毫秒
						$("#deleteModal").modal('hide');
						app.message = '';
						app.messageShow = false;
					}, 2000);
				}
			}
		});

	});
	$('#messageBtn').click(function () {
		// 显示加载gif
		loading.show();
		$('#modalBody').html('正在安排赛程中,请稍等...');
		var cupId = $('#cupId').val();
		var formData = new FormData();
		formData.append('cup.cupId', cupId);
		formData.append('dbCode', 'FootballSystem');
		formData.append('dbType', 'MyBatis'); 
		$.ajax({
			url: randomGameUrl,
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
					$('#messageBtn').attr('disabled', 'true');
					setTimeout(function () {  //使用  setTimeout（）方法设定定时2000毫秒
						$("#messageModal").modal('hide');
						app.message = '';
						app.messageShow = false;
					}, 2000);
				} else {
					app.err = true;
					setTimeout(function () {  //使用  setTimeout（）方法设定定时2000毫秒
						$("#messageModal").modal('hide');
						app.message = '';
						app.messageShow = false;
					}, 2000);
				}
			}
		});

	});

});
