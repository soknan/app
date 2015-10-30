function bindMenuClick() {
	$.each($('a[name="menu"]'), function(i, val) {
		$(this).click(function() {
			onOpenWindow($(this).attr('id'));

			$.each($('a[name="menu"]'), function(i, val) {
				$(this).parent().removeClass('active');
			});

			$(this).parent().addClass('active');
		});
	});
}

function onOpenWindow(cmd) {
	var data = "{\"cmd\":\"" + cmd + "\"}";
	zAu.send(new zk.Event(zk.Widget.$('$mainComposer'), 'onCmd', data, {
		toServer : true
	}));
}

function onChecker(br_cd, chk_json) {
	
	var arr_json = eval(chk_json);
	
	var d1 = new Date();
	
	$('#divAjaxResult').html(''); $('#spanStatus').html('');
	
	for(var i=0; i<arr_json.length; i++){
		onDoChecker(br_cd, arr_json[i]);
	}
	var d2 = new Date();
	var diff = d2.getTime() - d1.getTime();
	
	$('#spanStatus').html('Done in ' + diff/1000 + ' seconds');
	
	
}

function onDoChecker(br_cd, chk_id){
	
	var startTime = new Date();
	
	var url = '/kSupport/GeneralProcess1';
	
	var txt_checking = '<span>'+ chk_id["statusMsg"] +' <img src=\"img/loading.gif\"><span>';
	
	$('#spanStatus').html(txt_checking);
	$.ajax({
		url : url,
		async : false,
		cache : false,
		type : "POST",
		dataType : "html",
		data : {
			proc_id : 1,
			br_cd: br_cd,
			chk_id:chk_id["id"]
		},
		success : function(responseText) {
			$('#divAjaxResult').append(responseText);
			$('#spanStatus').html(chk_id["statusMsg"] + '... Done');
			
			var endTime = new Date();
			var timeDiff = endTime.getTime() - startTime.getTime();
			
			$('#done' + chk_id["id"]).append(' ' + timeDiff/1000 + ' seconds');
			//$container.animate({scrollTop: $container.prop("scrollHeight")}, 500);
			
		}
	});
	// console.log('1. render template...');
}