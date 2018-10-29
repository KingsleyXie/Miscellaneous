<?php
$font = getcwd() . '/name.ttf';
$name = "章保滑";

print_r(imagettfbbox(70, 0, $font, $name));
// Array ( [0] => 9 [1] => 5 [2] => 218 [3] => 5 [4] => 218 [5] => -65 [6] => 9 [7] => -65 )
