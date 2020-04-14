// ==UserScript==
// @name         DengDuaURL
// @namespace    http://tampermonkey.net/
// @version      0.1
// @description  Make Court Wenshu Accessable Again!
// @author       AJIE
// @match        https://wenshu.court.gov.cn/*
// @grant        none
// ==/UserScript==

(function() {
    'use strict';
    let url = window.location.href
    if (url.includes('DocID')) {
        // https://wenshu.court.gov.cn/content/content?DocID=xxxxxxx
        console.log('Old Format URL Detected, Redirecting...')
        let docId = url.split('=')[1].replace(/-/g, '')
        console.log('Doc ID:', docId)
        window.location.href = 'https://wenshu.court.gov.cn/website/wenshu/181107ANFZ0BXSK4/index.html?docId=' + docId
    }
})();
