<?php
class TextBox
{
    public static $RECT = [
        'left' => 630,
        'top' => 80,
        'right' => 860,
        'bottom' => 200
    ];

    public function getBounding($size, $font, $name, $center =true) {
        $innerBox = imagettfbbox($size, 0, $font, $name);
        $rect = self::$RECT;

        $outterHeight = $rect['bottom'] - $rect['top'];
        $outterWidth = $rect['right'] - $rect['left'];

        $innerHeight = abs($innerBox[5] - $innerBox[1]);
        $innerWidth = abs($innerBox[4] - $innerBox[0]);

        $bounding = [
            'lower_left_x' => $rect['left'],
            'lower_left_y' => ($rect['bottom'] - ($outterHeight - $innerHeight) / 2)
        ];

        $bounding['upper_right_x'] = $bounding['lower_left_x'] + $innerWidth;
        $bounding['upper_right_y'] = $bounding['lower_left_y'] - $innerHeight;

        if ($center) {
            $offset = ($outterWidth - $innerWidth) / 2;
            $bounding['lower_left_x'] += $offset;
            $bounding['upper_right_x'] += $offset;
        }
        return $bounding;
    }
}
