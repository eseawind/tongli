<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" session="false"%>
	<div id="scrollDiv_keleyi_com"> </div>
	<script type="text/javascript">
	//焦点图
	function loadUrlListScrollDiv() {
		jQuery.ajax({
			url : '${basePath}/w/c003_list1.ac',
			success : function(req) {
				jQuery("#scrollDiv_keleyi_com").html(req);
			},
			error : function() {
				//--异常--
			}
		});
	}
	loadUrlListScrollDiv();
	</script>