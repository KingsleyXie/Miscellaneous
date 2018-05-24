<?php
// Get books' location information from SCUT library OPAC

header('Content-Type: application/json');
if (!isset($_GET['isbn'])) {
	echo json_encode(['ok' => false]);
	exit();
}

$isbn = preg_replace('/978(\d+)/i', '$1', $_GET['isbn']);
$post_data = 'cmdACT=simple.list&FIELD1=ISBN&VAL1=' . $isbn;
$list_page = request_info($post_data);

preg_match_all('/结果数：<font color="red">(\d)/', $list_page, $result);
if ($result[1][0] == 1) {
	preg_match_all('/book_detail\((\d*)\)/', $list_page, $bookid);
	$post_data = 'cmdACT=query.bookdetail&bookid=' . $bookid[1][0];

	$detail_page = request_info($post_data);
	preg_match_all(
		'/<td bgcolor="#FFFFFF" width="18%">(.*?)<\/td>/',
		$detail_page, $call_no
	);
	preg_match_all(
		'/<td bgcolor="#FFFFFF" width="14%">(C\d*?)<\/td>/',
		$detail_page, $barcode
	);

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
