// ==UserScript==
// @name         裁判文书网旧版链接跳转工具
// @version      0.1
// @description  Make Court Wenshu Accessable Again!
// @author       kingsleyxie
// @match        https://wenshu.court.gov.cn/content/content?DocID=*
// ==/UserScript==

(function() {
    'use strict';
    let url = window.location.href
    if (url.includes('DocID')) {
        let doc = `docId=${url.split('=')[1].replace(/-/g, '')}`
        let ele = document.createElement('h2')
        ele.innerHTML = `<center>${doc} - 即将重定向至新版文书阅读页面...</center>`
        document.body.insertBefore(ele, document.querySelector('.main'))

        window.location.href = `https://wenshu.court.gov.cn/website/wenshu/181107ANFZ0BXSK4/index.html?${doc}`
    }
})();
