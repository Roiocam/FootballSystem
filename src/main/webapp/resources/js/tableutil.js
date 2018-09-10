/**
 * Roiocam
 */
$(function() {
	$('#datalist').delegate("tr","click",function (e) {
		var check = $(this).find("input[type='checkbox']");
		if(check){
			var flag = check[0].checked;
			if(flag){
				check[0].checked = false;
			}else{
				check[0].checked = true;
			}
		}
	});
	
	$('body').delegate("#cboxhead","click",function (e) {
		var check = $(this).find("input[type='checkbox']");
		if(check[0].checked==true){
			$(".cboxlist").each(function(index,element){
				element.checked=true;
			})
		}else{
			$(".cboxlist").each(function(index,element){
				element.checked=false;
			})
		}
		
	});
	
	$("input[type='checkbox']").click(function(e){
		e.stopPropagation(); 
	});
	
	function isRealNum(val){
	    // isNaN()函数 把空串 空格 以及NUll 按照0来处理 所以先去除
	    if(val === "" || val ==null){
	        return false;
	    }
	    if(!isNaN(val)){
	        return true;
	    }else{
	        return false;
	    }
	}
	
});




