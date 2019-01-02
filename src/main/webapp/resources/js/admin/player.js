/**
 * 
 * 
 */
const getTeamUrl = '/FootballSystem/admin/service/team/getTeams';
const getUrl = '/FootballSystem/admin/service/player/getPlayers';
const addUrl = '/FootballSystem/admin/service/player/addPlayer';
const editUrl = '/FootballSystem/admin/service/player/editPlayer';
const delOneUrl = '/FootballSystem/admin/service/player/deletePlayer';
const delListUrl = '/FootballSystem/admin/service/player/deletePlayerList';
var searchData = null;
var loading = new LoadingUtils();
loading.append();

var app = new Vue(
	{
		el: '#app',
		data: {
			datalist: [],
			message: '',
			err: false,
			messageShow: false,
			teamlist: []
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
			this.initData();
			this.getTeamData();
			this.err = false
		},
		methods: {
			initData: function () {
				var formData = new FormData();
				formData.append('pageIndex', pageIndex);
				formData.append('pageSize', pageSize);
				formData.append('dbCode', 'FootballSystem');
				formData.append('dbType', 'MyBatis');
				if (searchData != null) {
					formData.append('teamId', searchData.teamId);
					formData.append('playerName', searchData.playerName);
				}
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
			getTeamData: function () {
				var formData = new FormData();
				formData.append('pageIndex', 1);
				formData.append('pageSize', 50);
				formData.append('dbCode', 'FootballSystem');
				formData.append('dbType', 'MyBatis');
				const that = this
				axios
					.post(
						getTeamUrl,
						formData)
					.then(
						function (res) {
							if (res.data.state == 0) {
								that.teamlist = res.data.result
							} else {
								alert(res.data.message)
							}
						});
			}

		},

	})
$(function () {
	// var id = window.parent.getId(this);
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
				var playerId = $('#playerId').val();
				var playerName = $('#playerName').val();
				var playerNum = $('#playerNum').val();
				var teamId = $('#teamId').val();
				var playerStuno = $('#playerStuno').val();
				var playerDepart = $('#playerDepart').val();
				var playerTel = $('#playerTel').val();
				if (playerStuno.length != 10 && playerStuno.length != 11) {
					app.message = '请正确输入学号，纯数字10或11位';
					app.err = true;
					app.messageShow = true;
					return;
				}
				if (playerNum.length > 2) {
					app.message = '请正确输入球衣号码';
					app.err = true;
					app.messageShow = true;
					return;
				}
				if (playerTel.length != 11) {
					app.message = '请正确输入手机号，纯数字11位';
					app.messageShow = true;
					return;
				}
				var myreg = /^[1][3,4,5,7,8][0-9]{9}$/;
				if (!myreg.test(playerTel)) {
					app.message = '手机号码格式有误';
					app.messageShow = true;
					return;
				}
				if (teamId==null) {
					app.message = '请选择球队';
					app.messageShow = true;
					return;
				}
				loading.show();

				var formData = new FormData();
				formData.append('playerId', playerId);
				formData.append('teamId', teamId);
				formData.append('playerNum', playerNum);
				formData.append('playerName', playerName);
				formData.append('playerStuno', playerStuno);
				formData.append('playerDepart', playerDepart);
				formData.append('playerTel', playerTel);
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
							app.message = data.message;
							app.messageShow = true;
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

	$('#inputModal')
		.on(
			'show.bs.modal',
			function (event) {
				var button = $(event.relatedTarget) // Button that
				var modal = $(this)
				var type = button.data('id');
				var objId = button.data('objId');
				var formData = new FormData();
				formData.append('pageIndex', 1);
				formData.append('pageSize', 50);
				formData.append('dbCode', 'FootballSystem');
				formData.append('dbType', 'MyBatis');

				if (type == 'edit') {
					var playerId = button.closest('tr').find('td')
						.eq(1).text();
					var playerName = button.closest('tr').find('td')
						.eq(2).text();
					var playerNum = button.closest('tr').find('td').eq(
						3).text();
					var teamId = button.closest('tr').find('td').eq(4)
						.attr('value');
					var playerStuno = button.closest('tr').find('td')
						.eq(5).text();
					var playerDepart = button.closest('tr').find('td')
						.eq(6).text();
					var playerTel = button.closest('tr').find('td').eq(
						7).text();
					modal.find('.modal-body #playerId').val(playerId)
					modal.find('.modal-body #playerName').val(
						playerName)
					modal.find('.modal-body #playerNum').val(playerNum)
					modal.find('.modal-body #playerStuno').val(
						playerStuno)
					modal.find('.modal-body #playerDepart').val(
						playerDepart)
					modal.find('.modal-body #playerTel').val(playerTel)
					$('#submitData').val('edit');
					$('#submitData').data('objId', objId);
				} else {
					modal.find('.modal-body #playerId').val(null)
					modal.find('.modal-body #playerName').val(null)
					modal.find('.modal-body #playerNum').val(null)
					modal.find('.modal-body #teamId').val(null)
					modal.find('.modal-body #playerStuno').val(null)
					modal.find('.modal-body #playerTel').val(null)
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
	$('#searchBtn').click(function () {
		searchData = new Object();
		searchData.playerName = $('#searchName').val();
		searchData.teamId = $('#searchTeam').val();
		app.initData();
	});
	function delOne() {
		var objId = $('#deleteObj').val();
		var formData = new FormData();
		formData.append('playerId', objId);
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
					app.message = data.message;
					app.messageShow = true;
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
				},
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
					app.message = data.message;
					app.messageShow = true;
					if (data.state == 0) {
						app.err = false;
						app.initData();
						setTimeout(function () { //使用  setTimeout（）方法设定定时2000毫秒
							$("#deleteModal").modal('hide');
							app.message = '';
							app.messageShow = false;
						}, 500);
					} else {
						setTimeout(function () { //使用  setTimeout（）方法设定定时2000毫秒
							$("#deleteModal").modal('hide');
							app.message = '';
							app.messageShow = false;
						}, 2000);
					}
				}
			});
	}

});
function tableToExcel() {
	var teamId = $('#searchTeam').val();
	if (teamId == '') {
		alert('请选择球队')
		return
	}
	window.location.href = '/FootballSystem/admin/view/word?teamId=' + teamId;
}
