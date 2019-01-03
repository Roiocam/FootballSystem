/**
 * 分页条
 */

var pageIndex = 1;
var pageCount = 0;
var pageSize = 10;
var leftPage = 5;
var rightPage = 6;


function initPageCount(recordCount) {
	pageCount = parseInt((recordCount % 10) == 0 ? (recordCount / 10) : (recordCount / 10) + 1);
}
function initPageData() {
	var html = '<li class="page-item prev ' + (pageIndex == 1 ? 'disabled' : '') + '"><a class="page-link" href="#" tabindex="-1" >上一页</a></li>';
	// 当页码总数小于等于10
	if (pageCount <= 10) {
		for (var i = 1; i <= pageCount; i++) {
			html += '<li class="page-item ' + (i == pageIndex ? 'active' : '') + '"><a class="page-link" href="#">'
				+ i + '</a></li>';
		}
	} else {// 页码总数大于10，需要处理分页
		// 当页码为1时
		pageIndex = parseInt(pageIndex);
		if (pageIndex == 1) {
			for (var i = pageIndex; i <= 10; i++) {
				html += '<li class="page-item ' + (i == pageIndex ? 'active' : '') + '"><a class="page-link" href="#">'
					+ i + '</a></li>';
			}
			html += '	<li class="page-item disabled"><a class="page-link" href="#">...</a></li>'
				+ '<li class="page-item "><a class="page-link" href="#">'
				+ pageCount + '</a></li>';
		} else if (pageIndex > leftPage && (pageCount - rightPage) >= pageIndex) {
			// 当页码位于两边都大于能渲染最大页数的中间时
			// 渲染1....
			html += '	<li class="page-item "><a class="page-link" href="#">1</a></li>'
				+ '<li class="page-item disabled"><a class="page-link" href="#">...</a></li>';
			// 渲染2,3,4,pageIndex,5,6,7
			for (var i = pageIndex - 4; i <= pageIndex; i++) {
				html += '<li class="page-item ' + (i == pageIndex ? 'active' : '') + '"><a class="page-link" href="#">'
					+ i + '</a></li>';
			}
			for (var i = pageIndex + 1; i < pageIndex + rightPage; i++) {
				html += '<li class="page-item "><a class="page-link" href="#">'
					+ i + '</a></li>';
			}
			// 渲染..pageCount
			html += '	<li class="page-item disabled"><a class="page-link" href="#">...</a></li>'
				+ '<li class="page-item "><a class="page-link" href="#">'
				+ pageCount + '</a></li>';
		} else if (pageIndex > leftPage && (pageCount - rightPage) < pageIndex) {
			// 只需1...的渲染
			// 渲染1..
			html += '	<li class="page-item "><a class="page-link" href="#">1</a></li>'
				+ '<li class="page-item disabled"><a class="page-link" href="#">...</a></li>';
			// 渲染70~76
			var index = 9 - (pageCount - pageIndex);
			for (var i = pageIndex - index, j = 1; j <= index; i++ , j++) {
				html += '<li class="page-item"><a class="page-link" href="#">'
					+ i + '</a></li>';
			}
			// 渲染77,78,79,80(尾页)
			for (var i = pageIndex; i <= pageCount; i++) {
				html += '<li class="page-item ' + (i == pageIndex ? 'active' : '') + '"><a class="page-link" href="#">'
					+ i + '</a></li>';
			}
		} else if (pageIndex <= leftPage && (pageCount - rightPage) >= pageIndex) {
			// 只需渲染..pageCount
			// 渲染1,2,3,pageIndex
			for (var i = 1; i <= pageIndex; i++) {
				html += '<li class="page-item ' + (i == pageIndex ? 'active' : '') + '"><a class="page-link" href="#">'
					+ i + '</a></li>';
			}
			// 渲染pageIndex+1,+2,+3
			for (var i = pageIndex + 1; i <= 10; i++) {
				html += '<li class="page-item "><a class="page-link" href="#">'
					+ i + '</a></li>';
			}

			html += '	<li class="page-item disabled"><a class="page-link" href="#">...</a></li>'
				+ '<li class="page-item "><a class="page-link" href="#">'
				+ pageCount + '</a></li>';
		}

	}
	html += '	<li class="page-item next ' + ((pageCount == 1 || pageIndex == pageCount) ? 'disabled' : '') + '"><a class="page-link" href="#">下一页</a></li>';
	$('#pageList').html(html);


}
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

