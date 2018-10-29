<?php
header('Content-type: image/png');

$image = imagecreatefrompng('card.png');
$color = imagecolorallocate($image, 144, 139, 134);
$font = getcwd() . '/name.ttf';

$name = "章保滑";

imagettftext($image, 70, 0, 637, 165, $color, $font, $name);
imagepng($image);
imagedestroy($image);
