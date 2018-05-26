<?php
// Get books' location information from SCUT library OPAC

header('Content-Type: application/json');
if (!(isset($_GET['title']) && isset($_GET['press']))) {
	echo json_encode(['ok' => false]);
	exit();
}

$post_data = 'cmdACT=advance.list&TABLE=&RDID=ANONYMOUS&CODE=&SCODE=&PAGE=&CLANLINK=&libcode=&MARCTYPE=&ORGLIB=SCUT&FIELD1=TITLE&VAL1=' . $_GET['title'] . '&RELATE2=AND&FIELD2=PUBLISHER&VAL2=' . $_GET['press'] . '&RELATE2=AND&FIELD3=TITLE&VAL3=&RELATE2=AND&FIELD4=TITLE&VAL4=&MARCTYPELIST=+%27CNMARC01%27&MARCTYPELIST=+%27CNMARC02%27&MARCTYPELIST=+%27CNMARC11%27&MARCTYPELIST=+%27USMARC01%27&MODE=RANDOM&LIB=%0D%0A%09++++%27SCUT%27%0D%0A%09++++&PAGESIZE=20';
$list_page = request_info($post_data);

preg_match_all('/结果数：<font color="red">(\d)/', $list_page, $result);

if ($result[1][0] > 1) {
	preg_match_all('/book_detail\((\d*)\)/', $list_page, $bookid);
	$post_data = 'cmdACT=query.bookdetail&bookid=' . $bookid[1][0];

	$detail_page = request_info($post_data);
	preg_match_all(
		'/南校区.*<td bgcolor="#FFFFFF" width="18%">(.*?)<\/td>/',
		$detail_page, $call_no
	);
	if (count($call_no[1]) == 0) {
		preg_match_all(
			'/<td bgcolor="#FFFFFF" width="18%">(.*?)<\/td>/',
			$detail_page, $call_no
		);
	}

	preg_match_all(
		'/南校区.*<td bgcolor="#FFFFFF" width="14%">(C\d*?)<\/td>/',
		$detail_page, $barcode
	);
	if (count($barcode[1]) == 0) {
		preg_match_all(
			'/<td bgcolor="#FFFFFF" width="14%">(C\d*?)<\/td>/',
			$detail_page, $barcode
		);
	}

	$location_page = request_location($barcode[1][0]);
	preg_match_all(
		'/var strWZxxxxxx = "(.*)";/',
		$location_page, $location
	);

	if (empty($location[1][0])) {
		$location[1][0] = '非自助借还(RFID)图书，无法定位!';
	} elseif ($location[1][0] == '图书未上架') {
		$location[1][0] = '图书未上架，无法定位!';
	} else {
		$location[1][0] = preg_replace('/.*?\|(.*)/', '$1', $location[1][0]);
	}

	echo json_encode([
		'ok' => true,
		'call_no' => $call_no[1][0],
		'location' => $location[1][0]
	]);
	exit();
} else {
	echo json_encode(['ok' => false]);
	exit();
}

function request_info($__post_data)
{
	$url = 'http://202.38.232.10/opac/servlet/opac.go';

	$ch = curl_init();
	curl_setopt($ch, CURLOPT_URL, $url);
	curl_setopt($ch, CURLOPT_POST, 1);
	curl_setopt($ch, CURLOPT_HTTPHEADER,
		['Content-Type: application/x-www-form-urlencoded']
	);
	curl_setopt($ch, CURLOPT_POSTFIELDS, $__post_data);
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);

	$__result =  curl_exec($ch);
	curl_close($ch);
	return $__result;
}

function request_location($__barcode)
{
	$url = 'http://202.38.232.12/TSDW/GoToFlash.aspx?szbarcode=' . $__barcode;

	$ch = curl_init();
	curl_setopt($ch, CURLOPT_URL, $url);
	curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);

	$__result =  curl_exec($ch);
	curl_close($ch);
	return iconv('GB2312', 'UTF-8', $__result);
}
