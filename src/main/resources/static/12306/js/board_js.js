(function(){jQuery.extend({boardCommon_getHeadDate:function(b){var c=parseInt(b.substring(4,6),10);var a=parseInt(b.substring(6,8),10);return c+"月"+a+"日"},boardCommon_getLastFlushTime:function(){var a=lastFlushTimeStr.substring(8,10);var c=lastFlushTimeStr.substring(10,12);var b="余票张数为全部席别的总数（"+leftTicket_flush_interval+"分钟更新一次，上次更新时间："+a+":"+c+"）";return b},boardCommon_ifRequestData:function(b){var c=false;if(b=="undefined"){return true}var a=new Date().getTime();var d=b.substring(0,4)+"/"+b.substring(4,6)+"/"+b.substring(6,8)+" "+b.substring(8,10)+":"+b.substring(10,12)+":"+b.substring(12,14);var e=Date.parse(d);if(a-e>=leftTicket_flush_interval*60*1000){c=true}return c},boardCommon_gotoLeftQuery:function(b){var a=b.split("_");var g=a[0];var e=a[1];var f=a[2];var d=a[3];var c=a[4].substring(0,4)+"-"+a[4].substring(4,6)+"-"+a[4].substring(6,8);if(a.length==5){$.jc_setFromStation(g,e);$.jc_setToStation(f,d);$.jc_setFromDate(c)}$.jc_setWfOrDc("dc");otsRedirect("post","https://kyfw.12306.cn/otn/leftTicket/init",{"leftTicketDTO.from_station_name":g,"leftTicketDTO.to_station_name":f,"leftTicketDTO.from_station":e,"leftTicketDTO.to_station":d,"leftTicketDTO.train_date":c,back_train_date:"",flag:"dc",purpose_code:"ADULT",pre_step_flag:"index"},"_blank")}})})();
var page=1;var lineNumber=1;var totalPage=1;var interval;var data;var dataInterv;var lastFlushTimeStr;var gotoLeftQuery=new Object();(function(){$(document).ready(function(){$.board_init();interval=setInterval($.board_nextPage,index_board_data_show_time*1000);dataInterv=setInterval($.board_init,request_ajax_check_interval*60000);$.board_regist_tr_Listener()});jQuery.extend({board_init:function(){if(!$.boardCommon_ifRequestData(lastFlushTimeStr+"")){return}var a=dhtmlx.modalbox({targSrc:'<div><img src="'+ctx+'resources/images/loading.gif"></img></div>'});$.ajax({url:ctx+"board/query",async:false,type:"get",success:function(b){if(b.data){data=b.data;$.board_getHtml()}dhtmlx.modalbox.hide(a)},error:function(b){dhtmlx.modalbox.hide(a)}})},board_nextPage:function(){var c=1;var a=pageSize;if(page>totalPage){page=1}if(page==1){}else{if(page==totalPage){c=(page-1)*pageSize+1;a=lineNumber}else{c=(page-1)*pageSize+1;a=page*pageSize}}page++;var b;$.each($("#board_table tr[index]"),function(d,e){d=$(e).attr("index");if(d>=c&&d<=a){$(e).fadeIn(1000)}else{$(e).hide()}})},board_getHtml:function(){var d=data.show;var m=data.dates;lastFlushTimeStr=data.lastFlushTimeStr;if(lastFlushTimeStr){$("#leftTicketTip").html($.boardCommon_getLastFlushTime())}var f=0;var l=[];var c=$.board_getTh(m);var a=data.values;if(a){var f=0;var l=[];var j,k,h,i,e;var b=0;var g=true;$.each(a,function(n,p){j=p.from_station_telecode_name;k=p.to_station_telecode_name;i=p.from_station_telecode;e=p.to_station_telecode;h=p.values;for(var o=0;o<m.length;o++){if(h[m[o]]!=0&&h[m[o]]!="--"){g=false;break}}if(!g){b++;l[n++]=$.board_getTr(j,k,h,m,b,i,e)}g=true})}$("#board_table").empty().append(c+l.join(""));lineNumber=b;totalPage=Math.ceil(lineNumber/pageSize);page=1},board_regist_tr_Listener:function(){$("#board_table  tr[index]").each(function(a){$(this).bind("mouseover",function(){$(this).addClass("sel")});$(this).bind("mouseout",function(){$(this).removeClass("sel")})});$("#board_table  tr[index] a").each(function(a){$(this).bind("click",function(){arrayInd=$(this).attr("arrayInd");$.boardCommon_gotoLeftQuery(gotoLeftQuery[arrayInd])})});$("#board_gotoMore").bind("click",function(){otsRedirect("post",ctx+"board/boardMore","","_blank")})},board_getTh:function(d){var b=0;var a=[];a[b++]='<tr><th style="width:14%;">出发地</th><th style="width:14%;">目的地</th>';for(var c=0;c<d.length;c++){a[b++]='<th class="time" style="width:18%;">'+$.boardCommon_getHeadDate(d[c])+"</th>"}a[b++]="</tr>";return a.join("")},board_getTr:function(h,j,e,m,a,g,c){var d=0;var l=[];if(a<=pageSize){l[d++]="<tr index="+a+">"}else{l[d++]='<tr style="display:none;" index='+a+">"}l[d++]="<td>"+h+"</td>";l[d++]="<td>"+j+"</td>";var f;for(var b=0;b<m.length;b++){var k=h+"_"+g+"_"+j+"_"+c+"_"+m[b];f=a+"_"+b;gotoLeftQuery[f]=k;l[d++]='<td class="num">  <a href="javascript:void(0);" arrayInd='+f+">"+e[m[b]]+"</a></td>"}l[d++]="</tr>";return l.join("")}})})();
