const getUrl = '/FootballSystem/admin/service/cup/getCups';
const addUrl = '/FootballSystem/admin/service/cup/addCup';
const editUrl = '/FootballSystem/admin/service/cup/editCup';
const delOneUrl = '/FootballSystem/admin/service/cup/deleteCup';
const delListUrl = '/FootballSystem/admin/service/cup/deleteCupList';
var loading = new LoadingUtils();
loading.append();
var app = new Vue({
	el : '#app',
	data : {
		message : '',
		datalist : [],
		message : '',
		err : false,
		messageShow : false,
	},
	computed : {
		msgClass : function() {
			return {
				'bg-success' : !this.err,
				'bg-danger' : this.err,
			}
		}
	},
	methods : {
		initData : function() {
			var formData = new FormData();
			formData.append('pageIndex', pageIndex);
			formData.append('pageSize', pageSize);
			formData.append('dbCode', 'FootballSystem');
			formData.append('dbType', 'MyBatis');
			const that = this
			axios.post(getUrl, formData).then(function(res) {
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
	mounted : function() {
		this.initData()
	}
})
$('#inputModal').on('show.bs.modal', function(event) {
	var button = $(event.relatedTarget) // Button that triggered the modal
	var modal = $(this)
	var type = button.data('id');
	var objId = button.data('objId');
	if (type == 'edit') {
		var cupId = button.closest('tr').find('td').eq(1).text();
		var cupName = button.closest('tr').find('td').eq(2).text();
		modal.find('.modal-body #cupId').val(cupId)
		modal.find('.modal-body #cupName').val(cupName)
		$('#submitData').val('edit');
		$('#submitData').data('objId', objId);
	} else {
		modal.find('.modal-body #cupId').val(null)
		modal.find('.modal-body #cupName').val(null)
		$('#submitData').val('add');
	}
});
$('#deleteModal').on('show.bs.modal', function(event) {
	var button = $(event.relatedTarget) // Button that triggered the modal
	var modal = $(this)
	var objId = button.data('id');
	$('#deleteObj').val(objId);
	var type = button.data('type');
	$('#deleteObj').data('type', type);
});

$('#deleteObj').click(function() {

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
	formData.append('cupId', objId);
	formData.append('dbCode', 'FootballSystem');
	formData.append('dbType', 'MyBatis');
	$.ajax({
		url : delOneUrl,
		type : 'POST',
		data : formData,
		contentType : false,
		processData : false,
		cache : false,
		success : function(data) {
			loading.hide();
			app.messageShow = true;
			app.message = data.message;
			if (data.state == 0) {
				app.err = false;
				app.initData();
				setTimeout(function() { // 使用
					// setTimeout（）方法设定定时2000毫秒
					$("#deleteModal").modal('hide');
					app.message = '';
					app.messageShow = false;
				}, 500);
			} else {
				app.err = true;
				setTimeout(function() { // 使用
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
	$(".cboxlist").each(function(index, element) {
		if (element.checked == true) {
			list.push(element.value);
		}
	})
	var formData = new FormData();
	formData.append('dbCode', 'FootballSystem');
	formData.append('dbType', 'MyBatis');
	formData.append('list', JSON.stringify(list));
	
	$.ajax({
		url : delListUrl,
		type : 'POST',
		data : formData,
		contentType : false,
		processData : false,
		cache : false,
		success : function(data) {
			loading.hide();
			app.messageShow = true;
			app.message = data.message;
			if (data.state == 0) {
				app.err = false;
				app.initData();
				setTimeout(function() { //使用  setTimeout（）方法设定定时2000毫秒
					$("#deleteModal").modal('hide');
					app.message = '';
					app.messageShow = false;
				}, 500);
			} else {
				app.err = true;
				setTimeout(function() { //使用  setTimeout（）方法设定定时2000毫秒
					$("#deleteModal").modal('hide');
					app.message = '';
					app.messageShow = false;
				}, 2000);
			}
		}
	});
}
$('#submitData').click(function() {

	loading.show();
	var type = $('#submitData').val();
	var oldObjId = $('#submitData').data('objId');
	var urlStr = '';
	if (type == 'edit') {
		urlStr = editUrl;
	} else {
		urlStr = addUrl;
	}
	var cupId = $('#cupId').val();
	var cupName = $('#cupName').val();
	var formData = new FormData();
	formData.append('cupId', cupId);
	formData.append('cupName', cupName);
	formData.append('dbCode', 'FootballSystem');
	formData.append('dbType', 'MyBatis');
	$.ajax({
		url : urlStr,
		type : 'POST',
		data : formData,
		contentType : false,
		processData : false,
		cache : false,
		success : function(data) {
			loading.hide();
			app.messageShow = true;
			app.message = data.message;
			if (data.state == 0) {
				app.err = false;
				app.initData();
				setTimeout(function() { // 使用
					// setTimeout（）方法设定定时2000毫秒
					$("#inputModal").modal('hide');
					app.message = '';
					app.messageShow = false;
				}, 500);
			} else {
				app.err = true;
				setTimeout(function() { // 使用
					app.message = '';
					app.messageShow = false;
				}, 5000);
			}
		}
	});

});