<?php
$password = '2333';
$input = '2333';



/*
 * Implementation 1
 */
$salt = sha1(mt_rand());
$hash = password_hash($password, PASSWORD_DEFAULT, ['salt' => $salt]);
$passed = password_verify($input, $hash);



/*
 * Implementation 2 [PHP Version >= 7.0.0]
 */
$hash = password_hash($password, PASSWORD_DEFAULT);
$passed = password_verify($input, $hash);



/*
 * Implementation 3 [Not Recommended]
 */
$salt = sha1(mt_rand());
$hash = hash('sha512', $password . $salt);

$curr_hash = hash('sha512', $input . $salt);
$passed = ($curr_hash === $hash);
