### Usage

1. Add following HTML code to the post page:

```html
<style>
html {
    /*
        The following variables stands for:
            Start Color
            End Color
            Vertical Length
            Horizontal Length
            Width
        of the lyric style, respectively.

        Not decided yet about which color gradient to use?
        Try https://uigradients.com, it may be of great help!
    */

    --lrc-start: #414345;
    --lrc-end: transparent;
    --lrc-vlen: 30%;
    --lrc-hlen: 30%;
    --lrc-width: 2px;
}

/* Lyric styles as blockquote */
blockquote.lyric {
    text-align: center;
    background-repeat: no-repeat;
    background-image:
        linear-gradient(
            to bottom, var(--lrc-start), var(--lrc-end)
        ),
        linear-gradient(
            to right, var(--lrc-start), var(--lrc-end)
        ),
        linear-gradient(
            to top, var(--lrc-start), var(--lrc-end)
        ),
        linear-gradient(
            to left, var(--lrc-start), var(--lrc-end)
        ),
        linear-gradient(transparent, transparent);
    background-size:
        var(--lrc-width) var(--lrc-vlen),
        var(--lrc-hlen) var(--lrc-width),
        var(--lrc-width) var(--lrc-vlen),
        var(--lrc-hlen) var(--lrc-width),
        calc(100% - (var(--lrc-width) * 2))
        calc(100% - (var(--lrc-width) * 2));
    background-position:
        left top, left top,
        right bottom, right bottom,
        var(--lrc-width) var(--lrc-width);

    /* Style offsets for Ghost */
    padding: .5em 0;
    border: none;
}
</style>

<script>
    //Lyric Blockquote Placeholder
    const lrcBPH = '[lyric]';

    document.querySelectorAll("blockquote")
    .forEach(function(ele) {
        //Check if the blockquote content starts with `lrcBPH`
        var txt = ele.firstElementChild.innerText;
        if(txt.indexOf(lrcBPH) == 0) {
            ele.firstElementChild.innerText =
            txt.substr(lrcBPH.length, txt.length);

            ele.classList.add("lyric");
        }
    });
</script>
```

2. Write a `lrcBPH` in the first child element of `<blockquote>`, or for Markdown format it shoule be like this:

```markdown
> [lyric]Almost heaven west virginia
>
> Blue ridge mountains shenandoah river
```

So that the code will be parsed to:

```html
<blockquote>
    <p>[lyric]Almost heaven west virginia</p>
    <p>Blue ridge mountains shenandoah river</p>
</blockquote>
```

Then the Javascript operation code will add a `lyric` class to it, and make the CSS code work.

### Notes
CSS global variables(those starts with `--lrc`) can be changed according to your liking, so is the Javascript constant `lrcBPH`.

Do not forget to add the placeholder.

### Effects
Visit the `lyric-style.html` file in broswers to preview the effects.
