<?php
require_once 'TextBox-fixed.class.php';

header('Content-type: image/png');

$image = imagecreatefrompng('card.png');
$color = imagecolorallocate($image, 144, 139, 134);
$font = getcwd() . '/name.ttf';

$name = "章保滑";

$box = new TextBox;
$bounding = $box->getBounding(70, $font, $name);

imagettftext($image, 70, 0, $bounding['baseline_x'], $bounding['baseline_y'], $color, $font, $name);

// Draw reference lines if necessary
if (true) {
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
        $bounding['upper_left_x'],
        $bounding['upper_left_y'],
        $bounding['lower_right_x'],
        $bounding['lower_right_y'],
        $color
    );

    // Text Height Lines
    imageline(
        $image,
        000, $bounding['upper_left_y'],
        950, $bounding['upper_left_y'],
        $color
    );
    imageline(
        $image,
        000, $bounding['lower_right_y'],
        950, $bounding['lower_right_y'],
        $color
    );
}

imagepng($image);
imagedestroy($image);
