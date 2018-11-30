let worksheet = null, headerRow = 0, rowRange = 0, templatePreviewed = false;



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
	let tgt = e.target;
	let middle = '{{' + tgt.value + '(' + tgt.selectedOptions[0].dataset.index + ')}}';

	let textarea = document.getElementById("textarea");
	let firstHalf = textarea.value.substr(0, textarea.selectionStart);
	let secondHalf = textarea.value.substr(textarea.selectionEnd, textarea.value.length);

	textarea.value = firstHalf + middle + secondHalf;
	templatePreviewed = false;

	// = =! You have to use jQuery here again and again
	$("#data-col-modal").modal('hide');
})

document.getElementById("operations").style.display = "none";

document.getElementById('file')
.addEventListener('input', (e) => {
	let reader = new FileReader();
	reader.onload = (e) => {
		document.getElementById("operations").style.display = "none";
		let data = e.target.result;
		let workbook = XLSX.read(data, {type: 'binary'});
		worksheet = workbook.Sheets[workbook.SheetNames[0]];

		let colRange = XLSX.utils.decode_range(worksheet['!ref']).e.c;

		headerRow = 0;
		rowRange = XLSX.utils.decode_range(worksheet['!ref']).e.r;

		// Get the row of header names
		while (headerRow < rowRange) {
			let finalCellPos = XLSX.utils.encode_cell({c: colRange, r: headerRow});
			if (worksheet[finalCellPos] == undefined) headerRow++;
			else break;
		}

		document.getElementById("select-phone").innerHTML = '';
		document.getElementById("select-column").innerHTML = '';

		let success = true;
		for (let col = 0; col <= colRange; col++) {
			let headerPos = XLSX.utils.encode_cell({c: col, r: headerRow});
			let samplePos = XLSX.utils.encode_cell({c: col, r: headerRow + 1});

			let option = document.createElement("option");
			try {
				option.text = worksheet[headerPos].w;
				document.getElementById("select-phone").add(option);

				let optionWithSubtext = option.cloneNode(true);
				optionWithSubtext.dataset.subtext = '【示例：' + worksheet[samplePos].w + '】';
				document.getElementById("select-column").add(optionWithSubtext);

				optionWithSubtext.dataset.index = col;
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



function generateSMSFromTemplate(start, end) {
	let msgs = [];
	for (let row = start; row <= end; row++) {
		let msg = document.getElementById("textarea").value.replace(/\{\{.*?\((\d+)\)\}\}/g, (match, str) => {
			let pos = XLSX.utils.encode_cell({c: parseInt(str), r: row});
			return worksheet[pos].w;
		});
		msgs.push(msg);
	}
	return msgs;
}




function generateSMS() {
	if (!templatePreviewed) {
		modalAlert('<center>请先预览短信效果</center>');
	} else {
		//
	}
}

function previewSMS() {
	templatePreviewed = true;
	modalAlert(generateSMSFromTemplate(headerRow + 1, headerRow + 2)[0]);
}

function previewOff() {
	templatePreviewed = false;
}



function modalAlert(msg) {
	document.getElementById("modal-msg").innerHTML = msg;

	// = =! You have to use jQuery here again and again and again and again
	$("#alert-modal").modal('show');
}
