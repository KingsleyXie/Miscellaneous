<?php
require_once 'wtf.php';

header('Content-type: image/png');

$image = imagecreatefrompng('card.png');
$color = imagecolorallocate($image, 144, 139, 134);
$font = getcwd() . '/name.ttf';

$name = "章保滑";

$box = new TextBox;
$bounding = $box->getBounding(70, $font, $name);

imagettftext($image, 70, 0, 637, 165, $color, $font, $name);

// Outer Rectangle
imagerectangle(
    $image,
    TextBox::$RECT['left'],
    TextBox::$RECT['top'],
    TextBox::$RECT['right'],
    TextBox::$RECT['bottom'],
    $color
);

// Inner Rectangle
imagerectangle(
    $image,
    $bounding['lower_left_x'],
    $bounding['lower_left_y'],
    $bounding['upper_right_x'],
    $bounding['upper_right_y'],
    $color
);

// Text Height Lines
imageline(
    $image,
    000, $bounding['lower_left_y'],
    950, $bounding['lower_left_y'],
    $color
);
imageline(
    $image,
    000, $bounding['upper_right_y'],
    950, $bounding['upper_right_y'],
    $color
);


imagepng($image);
imagedestroy($image);
