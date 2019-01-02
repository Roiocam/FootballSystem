/**
 * 
 * 
 */
var getCupUrl = '/FootballSystem/admin/service/cup/getCups';
var getUrl = '/FootballSystem/admin/service/game/getGames';
var loading = new LoadingUtils();
loading.append();
var app = new Vue(
	{
		el: '#app',
		data: {
			datalist: [],
			cuplist:[],
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
			getGameData: function (cupId) {
				var cupId = $('#cupId').val();
				var formData = new FormData();
				formData.append('pageIndex', pageIndex);
				formData.append('pageSize', pageSize);
				formData.append('cupId', cupId);
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
			app.getGameData(cupId);
		} else {
			$('#errModal').modal('show');
			setTimeout(() => {
				$('#errModal').modal('hide');
			}, 1000);
		}

	});
});


