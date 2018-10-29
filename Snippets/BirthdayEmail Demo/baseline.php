<?php
header('Content-Type: image/png');

$font = getcwd() . '/name.ttf';
$name = '章保滑';
$size = 70;

//Determine font dimensions
$bounding = imagettfbbox($size, 0, $font, $name);
$width =  abs($bounding[4] - $bounding[0]);
$height = abs($bounding[5] - $bounding[1]);

$image = imagecreatetruecolor($width, $height);
$fg = imagecolorallocate($image, 12, 13, 14);
$bg = imagecolorallocate($image, 239, 240, 241);

imagefilledrectangle($image, 0, 0, $width, $height, $bg);
imagettftext($image, $size, 0, 0, $height, $fg, $font, $name);

imagepng($image);
imagedestroy($image);





// print_r(imagettfbbox(70, 0, $font, $name));
// Array ( [0] => 9 [1] => 5 [2] => 218 [3] => 5 [4] => 218 [5] => -65 [6] => 9 [7] => -65 )
