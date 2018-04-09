<?php
/*
 * File location: localhost
 *
 * The following 'header' line should be uncommented,
 * Or the client won't process the returned data
 * But it can still receive it
 * And we can still handle the requested data
 * Check 'demo.txt' for result
 */

// header('Access-Control-Allow-Origin: *');
echo 'return data';

$file = fopen('./demo.txt', 'a');
fwrite($file, $_GET['param']);
fclose($file);
