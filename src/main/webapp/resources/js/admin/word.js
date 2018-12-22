/**
 * 
 */
var getUrl = '/FootballSystem/admin/service/team/getTeamById';
var teamId='';
var teamName='';
$(function() {
	teamId=getUrlParam('teamId');
	printData(teamId);
	$("#download").click(function() {
        $("#WordSection1").wordExport(teamName+'报名表');                  //fileName为导出的word文件的命名,content为要导出的html内容容器

   });
});
function handlerList(obj){
	var team=obj.team;
	var leader=obj.leader;
	var playerList=obj.playerList;
	teamName=team.teamName
	$('#teamName').html(team.teamName)
	$('#leaderName').html(leader.playerName)
	$('#leaderPhone').html(leader.info.playerTel)
	$('#teamCount').html(playerList.length)
	var html='';
	playerList.map(function(item,index){
		html+="<tr style='height:33.1pt'>"
			  +"<td style='border:solid windowtext 1.0pt;padding:.75pt .75pt .75pt .75pt;"
			  +"height:33.1pt'>"
			  +" <p class=MsoNormal align=center style='text-align:center'>"+item.playerName+"</p>"
			  +" </td>"
			  +" <td style='border:solid windowtext 1.0pt;padding:.75pt .75pt .75pt .75pt;"
			  +" height:33.1pt'>"
			  +" <p class=MsoNormal align=center style='text-align:center'><span lang=EN-US>"+item.playerNum+"</span></p>"
			  +"</td>"
			  +" <td style='border:solid windowtext 1.0pt;padding:.75pt .75pt .75pt .75pt;"
			  +" height:33.1pt'>"
			  +"<p class=MsoNormal align=center style='text-align:center'>"+item.info.playerDepart+"</p>"
			  +"</td>"
			  +" <td style='border:solid windowtext 1.0pt;padding:.75pt .75pt .75pt .75pt;"
			  +" height:33.1pt'>"
			  +" <p class=MsoNormal align=center style='text-align:center'><span lang=EN-US>"+item.info.playerStuno+"</span></p>"
			  +" </td>"
			  +" <td style='border:solid windowtext 1.0pt;padding:.75pt .75pt .75pt .75pt;"
			  +" height:33.1pt'>"
			  +" <p class=MsoNormal align=center style='text-align:center'><span lang=EN-US>"+item.info.playerTel+"</span></p>"
			  +" </td>"
			  +"</tr>";
		
	})
	$('#datalist').append(html);
}
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  // 匹配目标参数
    if (r != null) return unescape(r[2]); return null; // 返回参数值
}
function printData(teamId) {
	var formData = new FormData();
	formData.append('teamId', teamId);
	$.ajax({
		url : getUrl,
		type : 'POST',
		data : formData,
		contentType : false,
		processData : false,
		cache : false,
		success : function(data) {
			if (data.state == 0) {
				handlerList(data.result);
			} else {
				alert('球队信息不完整，请先指定球队队长。')
			}
		}
	});
}