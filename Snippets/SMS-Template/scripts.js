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
				option.text = parseCellV(headerPos);
				document.getElementById("select-phone").add(option);

				let optionWithSubtext = option.cloneNode(true);
				optionWithSubtext.dataset.subtext = '【示例：' + parseCellV(samplePos) + '】';
				document.getElementById("select-column").add(optionWithSubtext);

				optionWithSubtext.dataset.index = col;
			} catch (TypeError) {
				modalAlert('<h3><center>表格格式不符合要求！</center></h3>');
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
	let idx = document.getElementById("select-phone").selectedIndex - 1;
	if (idx == -1) {
		modalAlert('<h3><center>请选择手机号对应的数据列</center></h3>');
		// document.querySelector('button[data-id="select-phone"]').click();
		return;
	}

	let msgs = [];
	for (let row = start; row <= end; row++) {
		let msg = document.getElementById("textarea").value
		.replace(/\{\{.*?\((\d+)\)\}\}/g, (match, str) => {
			let pos = XLSX.utils.encode_cell({c: parseInt(str), r: row});
			return parseCellV(pos);
		});
		let pos = XLSX.utils.encode_cell({c: idx, r: row});
		msgs.push([parseCellV(pos), msg]);
	}
	return msgs;
}



function generateSMS() {
	if (!templatePreviewed) {
		modalAlert('<h3><center>请先预览短信效果</center></h3>');
	} else {
		let resultarray = generateSMSFromTemplate(headerRow + 1, rowRange);
		resultarray.splice(0, 0, ['收信人手机号', '短信内容']);
		let filename = '模板短信';

		let resultsheet = XLSX.utils.aoa_to_sheet(resultarray);

		let workbook = XLSX.utils.book_new();
		XLSX.utils.book_append_sheet(workbook, resultsheet, filename);
		XLSX.writeFile(workbook, filename + '.xlsx');

		let txtString = XLSX.utils.sheet_to_txt(resultsheet);
		let link = document.createElement('a');
		let blob = new Blob([txtString], { type: 'text/plain;charset=utf-8' });
		$(link).attr({ 'download': filename + '.txt', 'href': URL.createObjectURL(blob)});
		link.click();
	}
}

function previewSMS() {
	if (document.getElementById("textarea").value == '') {
		modalAlert('<h3><center>请输入短信模板！</center></h3>');
		return;
	}

	templatePreviewed = true;
	if (msg = generateSMSFromTemplate(headerRow + 1, headerRow + 2)) {
		modalAlert(
			'<h4>收信人手机号：' + msg[0][0] + '</h4>' +
			'<h4>短信内容：</h4>' + msg[0][1]
		);
	}
}

function previewOff() {
	templatePreviewed = false;
}



// Get Cell Value Without TypeError
function parseCellV(pos) {
	let placeholder = '（表格中该项为空）';
	return worksheet[pos] == undefined ? placeholder : worksheet[pos].w;
}

function modalAlert(msg) {
	document.getElementById("modal-msg").innerHTML = msg;

	// = =! You have to use jQuery here again and again and again and again
	$("#alert-modal").modal('show');
}
