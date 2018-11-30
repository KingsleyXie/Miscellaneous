let worksheet = null, previewed = false;



// = =! You have to use jQuery here
$("#file").fileinput({
	theme: 'explorer',
	language: 'zh',
	browseLabel: '浏览',
	msgPlaceholder: '点击右侧按钮上传 👉',
	dropZoneTitle:
		'<p>请上传 Excel 文件后选取数据列并输入短信模板</p>' +
		'<p><strong>点击右下角浏览按钮选择文件</strong>' +
		'或 <strong>直接将文件拖拽到这里</strong></p>',
	showUpload: false,
	allowedFileExtensions: ['xls', 'xlsx'],
	maxFileSize: 500000,
	maxFileCount: 1,
	previewFileExtSettings: {
		'xls': function(ext) { return ext.match(/(xls|xlsx)$/i); }
	}
})

// = =! You have to use jQuery here again
$('#select-column').on('changed.bs.select', function (e, clickedIndex, isSelected, previousValue) {
	var tgt = e.target;
	var middle = '{{' + tgt.value + '(' + tgt.selectedOptions[0].dataset.subtext + ')}}';

	var textarea = document.getElementById("textarea");
	var firstHalf = textarea.value.substr(0, textarea.selectionStart);
	var secondHalf = textarea.value.substr(textarea.selectionEnd, textarea.value.length);

	textarea.value = firstHalf + middle + secondHalf;
	previewed = false;

	// = =! You have to use jQuery here again and again
	$("#data-col-modal").modal('hide');
})

document.getElementById("operations").style.display = "none";

document.getElementById('file')
.addEventListener('input', (e) => {
	var reader = new FileReader();
	reader.onload = (e) => {
		document.getElementById("operations").style.display = "none";
		var data = e.target.result;
		var workbook = XLSX.read(data, {type: 'binary'});
		worksheet = workbook.Sheets[workbook.SheetNames[0]];

		var colRange = XLSX.utils.decode_range(worksheet['!ref']).e.c;
		var rowRange = XLSX.utils.decode_range(worksheet['!ref']).e.r;
		var headerRow = 0;

		// Get the row of header names
		while (headerRow < rowRange) {
			var finalCellPos = XLSX.utils.encode_cell({c: colRange, r: headerRow});
			if (worksheet[finalCellPos] == undefined) headerRow++;
			else break;
		}

		document.getElementById("select-phone").innerHTML = '';
		document.getElementById("select-column").innerHTML = '';

		var success = true;
		for (var col = 0; col <= colRange; col++) {
			var pos = XLSX.utils.encode_cell({c: col, r: headerRow});

			var option = document.createElement("option");
			try {
				option.text = worksheet[pos].w;
				document.getElementById("select-phone").add(option);

				var optionWithSubtext = option.cloneNode(true);
				optionWithSubtext.dataset.subtext = col;
				document.getElementById("select-column").add(optionWithSubtext);
			} catch (TypeError) {
				modalAlert('<center>表格格式不符合要求！</center>');
				success = false;
				break;
			}
		}

		// = =! You have to use jQuery here again and again and again
		$('.selectpicker').selectpicker('refresh');
		if (success) document.getElementById("operations").style.display = "block";
	};
	reader.readAsBinaryString(e.target.files[0]);
});



function generateSMS() {
	if (!previewed) {
		modalAlert('<center>请先预览短信效果</center>');
	} else {
		//
	}
}

function previewSMS() {
	previewed = true;
	modalAlert('喵喵喵');
}

function previewOff() {
	previewed = false;
}



function modalAlert(msg) {
	document.getElementById("modal-msg").innerHTML = msg;

	// = =! You have to use jQuery here again and again and again and again
	$("#alert-modal").modal('show');
}
