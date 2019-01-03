const getCupUrl = '/FootballSystem/admin/service/cup/getCups';
const getLeaderUrl = '/FootballSystem/admin/service/player/getPlayers';
const editLeaderUrl = '/FootballSystem/admin/service/team/updateTeamLeader';
const getUrl = '/FootballSystem/admin/service/team/getTeams';
const addUrl = '/FootballSystem/admin/service/team/addTeam';
const editUrl = '/FootballSystem/admin/service/team/editTeam';
const delOneUrl = '/FootballSystem/admin/service/team/deleteTeam';
const delListUrl = '/FootballSystem/admin/service/team/deleteTeamList';
const loading = new LoadingUtils();
loading.append();
var app = new Vue(
	{
		el: '#app',
		data: {
			message: '',
			datalist: [],
			cuplist: [],
			playerlist: [],
			cupId: '',
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
				formData.append('dbType', 'JPA');
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
			},
			getCupData: function () {
				var formData = new FormData();
				formData.append('pageIndex', 1);
				formData.append('pageSize', 50);
				formData.append('dbCode', 'FootballSystem');
				formData.append('dbType', 'JPA');
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
			}
		},
		mounted: function () {
			this.initData()
			this.getCupData()
			$("#pageList").delegate(".page-link", "click", function () {
				var obj = $(this).parent();
				if (obj.hasClass('next')) {
					pageIndex = parseInt(pageIndex) + 1;
					$('#pageIndexInput').val(pageIndex);
					app.initData();
					return; 
				} if (obj.hasClass('prev')) {
					pageIndex = parseInt(pageIndex) - 1;
					$('#pageIndexInput').val(pageIndex);
					app.initData();
					return;
				}
				pageIndex = parseInt(obj.children().html());
				$('#pageIndexInput').val(pageIndex);
				app.initData();
			});
			$('#pageIndexInput').bind('keypress', function (event) {
				if (event.keyCode == 13) {
					var index = parseInt($(this).val());
					if (index > pageCount) {
						alert("请正确输入页码");
						return;
					}
					pageIndex = index;
					app.initData();
				}
			});
		}
	})

$(function () {
	$('#submitData')
		.click(
			function () {

				var type = $('#submitData').val();
				var oldObjId = $('#submitData').data('objId');
				var urlStr = '';
				if (type == 'edit') {
					urlStr = editUrl;
				} else {
					urlStr = addUrl;
				}
				var teamId = $('#teamId').val();
				var teamName = $('#teamName').val();
				var vaildCode = $('#vaildCode').val();
				var teamDesc = $('#teamDesc').val();
				var cupId = $('#cupId').val();
				if (vaildCode.length < 4) {
					app.messageShow = true;
					app.message = '请正确输入验证码,4位字符数字';
					app.err = true;
					return;
				}
				loading.show();
				var formData = new FormData();
				formData.append('teamId', teamId);
				formData.append('teamName', teamName);
				formData.append('vaildCode', vaildCode);
				formData.append('teamDesc', teamDesc);
				formData.append('cupId', cupId);
				formData.append('dbCode', 'FootballSystem');
				formData.append('dbType', 'JPA');
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
									app.messageShow = false;
									app.message = '';
								}, 500);
							} else {
								app.err = true;
								setTimeout(function () { // 使用
									app.messageShow = false;
									app.message = '';
								}, 5000);
							}
						}
					});

			});

	$('#inputModal')
		.on(
			'show.bs.modal',
			function (event) {
				var button = $(event.relatedTarget) // Button that
				// triggered the
				// modal
				var modal = $(this)
				var type = button.data('id');
				var objId = button.data('objId');
				if (type == 'edit') {
					var teamId = button.closest('tr').find('td').eq(1)
						.text();
					var teamName = button.closest('tr').find('td')
						.eq(2).text();
					var cupId = button.closest('tr').find('td').eq(3)
						.attr('value');
					app.cupId = cupId;
					var vaildCode = button.closest('tr').find('td').eq(
						4).text();
					var teamDesc = button.closest('tr').find('td')
						.eq(6).text();
					modal.find('.modal-body #teamId').val(teamId)
					modal.find('.modal-body #teamName').val(teamName)
					modal.find('.modal-body #vaildCode').val(vaildCode)
					modal.find('.modal-body #teamDesc').val(teamDesc)
					$('#submitData').val('edit');
					$('#submitData').data('objId', objId);
				} else {
					modal.find('.modal-body #teamId').val(null)
					modal.find('.modal-body #teamName').val(null)
					modal.find('.modal-body #vaildCode').val(null)
					modal.find('.modal-body #teamDesc').val(null)
					$('#submitData').val('add');
				}
			});
	$('#leaderModal').on(
		'show.bs.modal',
		function (event) {
			var button = $(event.relatedTarget) // Button that triggered the
			// modal
			var modal = $(this)
			var teamId = button.closest('tr').find('td').eq(1).text();
			modal.find('.modal-body #lteamId').val(teamId)
			var formData = new FormData();
			formData.append('pageIndex', 1);
			formData.append('pageSize', 40);
			formData.append('teamId', teamId);
			formData.append('dbCode', 'FootballSystem');
			formData.append('dbType', 'JPA');
			$.ajax({
				url: getLeaderUrl,
				type: 'POST',
				data: formData,
				contentType: false,
				processData: false,
				cache: false,
				success: function (data) {
					if (data.state == 0) {
						app.playerlist = data.result
					} else {
						alert(data.message)
					}
				}
			});
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
	$('#submitLeader')
		.click(
			function () {
				loading.show();
				var teamId = $('#lteamId').val();
				var leaderId = $('#leaderId').val();
				if (leaderId == 0||leaderId=='0') {
					loading.hide();
					app.messageShow = true
					app.message = '请先为球队新增球员';
					app.err = true
					return
				}
				var formData = new FormData();
				formData.append('teamId', teamId);
				formData.append('leaderId', leaderId);
				formData.append('dbCode', 'FootballSystem');
				formData.append('dbType', 'JPA');
				$
					.ajax({
						url: editLeaderUrl,
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
								setTimeout(
									function () { // 使用
										// setTimeout（）方法设定定时2000毫秒
										$("#leaderModal")
											.modal('hide');
										app.messageShow = false;
										app.message = '';
									}, 500);
							} else {
								app.err = true;
								setTimeout(function () { // 使用
									app.messageShow = false;
									app.message = '';
								}, 5000);
							}
						}
					});

			});
	$("#teamDesc").on("input propertychange", function () {
		var $this = $(this), _val = $this.val();
		$("#teamDescCount").text(_val.length);
	});

	function delOne() {
		var objId = $('#deleteObj').val();
		var formData = new FormData();
		formData.append('teamId', objId);
		formData.append('dbCode', 'FootballSystem');
		formData.append('dbType', 'JPA');
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
							app.messageShow = false;
							app.message = '';
						}, 500);
					} else {
						app.err = true;
						setTimeout(function () { // 使用
							// setTimeout（）方法设定定时2000毫秒
							$("#deleteModal").modal('hide');
							app.messageShow = false;
							app.message = '';
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
	formData.append('dbType', 'JPA');
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
						setTimeout(function () { //使用  setTimeout（）方法设定定时2000毫秒
							$("#deleteModal").modal('hide');
							app.messageShow = false;
							app.message = '';
						}, 500);
					} else {
						app.err = true;
						setTimeout(function () { //使用  setTimeout（）方法设定定时2000毫秒
							$("#deleteModal").modal('hide');
							app.messageShow = false;
							app.message = '';
						}, 2000);
					}
				}
			});
	}

});


