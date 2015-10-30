function addCommas(nStr) {
	nStr += '';
	x = nStr.split('.');
	x1 = x[0];
	x2 = x.length > 1 ? '.' + x[1] : '';
	var rgx = /(\d+)(\d{3})/;
	while (rgx.test(x1)) {
		x1 = x1.replace(rgx, '$1' + ',' + '$2');
	}
	return x1 + x2;
}

function loadjscssfile(filename, filetype) {
	var fileref;

	if (filetype == "js") { // if filename is a external JavaScript file
		fileref = document.createElement('script');
		fileref.setAttribute("type", "text/javascript");
		fileref.setAttribute("src", filename);
	} else if (filetype == "css") { // if filename is an external CSS file
		fileref = document.createElement("link");
		fileref.setAttribute("rel", "stylesheet");
		fileref.setAttribute("type", "text/css");
		fileref.setAttribute("href", filename);
	}
	if (typeof fileref != "undefined")
		document.getElementsByTagName("head")[0].appendChild(fileref);
}


function loadjscssfile(server_root, filename, filetype) {
	var fileref;
	var d = new Date();
	if (filetype == "js") { // if filename is a external JavaScript file
		var ignore = false;
		$.each($('script[type="text/javascript"]'), function(){
		    var m_src = $(this).attr('src');
		    if(m_src != undefined){
			    if(m_src.indexOf(filename) > -1){
			        ignore = true;
			    	return false;
			    }
		    } 
		});
		if(ignore) return;
		filename = server_root + filename + '?t=' + d.getSeconds() + d.getMinutes() + d.getHours();
		fileref = document.createElement('script');
		fileref.setAttribute("type", "text/javascript");
		fileref.setAttribute("src", filename);
	} else if (filetype == "css") { // if filename is an external CSS file
		var ignore = false;
		$.each($('link[rel="stylesheet"][type="text/css"]'), function(){
		    var m_src = $(this).attr('href');
		    if(m_src != undefined){
		    	var i = filename.indexOf('?vrs=');
			    if(i>-1) {
			    	filename = filename.substring(0,i);
			    }
			    if(m_src.indexOf(filename) > -1){
			        ignore = true;
			    	return false;
			    }
		    } 
		});
		if(ignore) return;
		filename = server_root + filename + '?t=' + d.getSeconds() + d.getMinutes() + d.getHours();;
		
		fileref = document.createElement("link");
		fileref.setAttribute("rel", "stylesheet");
		fileref.setAttribute("type", "text/css");
		fileref.setAttribute("href", filename);
	}
	if (typeof fileref != "undefined")
		document.getElementsByTagName("head")[0].appendChild(fileref);
}


function removejscssfile(filename, filetype) {
	var targetelement = (filetype == "js") ? "script"
			: (filetype == "css") ? "link" : "none" // determine element type to
													// create nodelist from
	var targetattr = (filetype == "js") ? "src" : (filetype == "css") ? "href"
			: "none" // determine corresponding attribute to test for
	var allsuspects = document.getElementsByTagName(targetelement)
	for ( var i = allsuspects.length; i >= 0; i--) { // search backwards
														// within nodelist for
														// matching elements to
														// remove
		if (allsuspects[i]
				&& allsuspects[i].getAttribute(targetattr) != null
				&& allsuspects[i].getAttribute(targetattr).indexOf(filename) != -1)
			allsuspects[i].parentNode.removeChild(allsuspects[i]) // remove
																	// element
																	// by
																	// calling
																	// parentNode.removeChild()
	}
}

function getSelectedCheckBoxValuesBy(input_name, seperated) {
	var result = '';
	var i = 0;
	$.each($('input[@name=' + input_name + ']:checked'), function() {
		if ($(this).attr('name') == input_name) {
			if (i == 0)
				result += $(this).attr('value');
			else
				result += seperated + $(this).attr('value');
			i++;
		}
	});
	return $.trim(result);
}