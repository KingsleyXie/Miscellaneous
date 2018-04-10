<?php
$password = '2333';

$salt = sha1(mt_rand());
$salted_hash = hash('sha512', $password . $salt);

echo json_encode([
	'salt' => $salt,
	'salted_hash' => $salted_hash
]);



$right_input = '2333';
$right_hash = hash('sha512', $right_input . $salt);

echo json_encode([
	'right_hash' => $right_hash,
	'passed' => ($right_hash === $salted_hash)
]);

$wrong_input = '2332';
$wrong_hash = hash('sha512', $wrong_input . $salt);
echo json_encode([
	'wrong_hash' => $wrong_hash,
	'passed' => ($wrong_hash === $salted_hash)
]);
