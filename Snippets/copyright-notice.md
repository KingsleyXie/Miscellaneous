### Copyright Notice Snippet

This is the code added on my blog post before footer part, which provides a copyright information for readers.

```html
<style type="text/css">
    .post-copyright {
        padding-top: 1.75em;
        border-top: #ebf2f6 1px solid;
    }

    .copyright-notice {
        padding: .3em 1em;
        transition: all .5s;
        box-shadow: 0 1.5px 3px rgba(0, 0, 0, 0.1);
    }
    .copyright-notice:hover {
        transform: translateY(-1px);
        box-shadow: 0 2px 7px rgba(0, 0, 0, 0.15);
    }
    .copyright-notice > center {
        cursor: pointer;
        user-select: none;
    }

    .copyright-notice p {
        margin: .5em 0;
    }
</style>

<div class="post-copyright">
    <div class="copyright-notice">
        <center onclick="this.nextElementSibling.hidden = !this.nextElementSibling.hidden;">文章版权声明</center>
        <div hidden>
            <hr>
            <p>本文链接：{{url absolute="true"}}</p>
            <p>许可协议：<a href="https://creativecommons.org/licenses/by-nc-sa/4.0/deed.zh">知识共享 署名-非商业性使用-相同方式共享 4.0 国际</a></p>
        </div>
    </div>
</div>
```

If the placeholder `{{url absolute="true"}}` does not work correctly(it doesn't generate the url with `https` protocol in my case), you can change that line of code and use Javascript instead to generate the link:

```html
<!-- Modify this line of code -->
<p id="copyright-link">本文链接：</p>

<!-- And then add following JS code -->
<script type="text/javascript">
    document.getElementById("copyright-link").innerText +=
    window.location.origin + window.location.pathname;
</script>
```
