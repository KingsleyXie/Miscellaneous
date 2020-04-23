// ==UserScript==
// @name         破乎回答净化器
// @version      0.1
// @description  屏蔽没卵用的破乎账号的回答，思路参考自另一个油猴脚本：https://greasyfork.org/en/scripts/394800-%E7%9F%A5%E4%B9%8E%E5%B1%8F%E8%94%BD%E7%94%A8%E6%88%B7%E5%9B%9E%E7%AD%94
// @author       kingsleyxie
// @match        https://www.zhihu.com/question/*
// ==/UserScript==

(function() {
    'use strict';

    let timeout = 500
    let makelog = true
    let bprefix = 'https://www.zhihu.com/people/liu-kan-shan-'
    let blacklist = [
        'https://www.zhihu.com/people/gu-shi-dang-an-ju-71', // 故事档案局
        'https://www.zhihu.com/people/zhujiangren', // 盐选推荐
        'https://www.zhihu.com/people/yan-xuan-ke-pu-28', // 盐选科普
        /*
         * 以下用户整理自【盐选推荐】和【盐选科普】的关注列表
         * document.querySelectorAll('.List .UserItem-title .UserLink-link')
         * .forEach((ele) => {
         *     console.log(`'${ele.href}', // ${ele.innerText}`)
         * })
         *
         *
         * Update: 由于 URL 太有特点，下列用户直接 Hard Code 在校验代码中
         * 校验逻辑为 !ulink.search(bprefix)
         *
         * 'https://www.zhihu.com/people/liu-kan-shan-12-91', // 盐选点金
         * 'https://www.zhihu.com/people/liu-kan-shan-18-19', // 盐选职场
         * 'https://www.zhihu.com/people/liu-kan-shan-20-53', // 盐选科技前沿
         * 'https://www.zhihu.com/people/liu-kan-shan-50-89', // 盐选会员精品
         * 'https://www.zhihu.com/people/liu-kan-shan-51', // 盐选生活馆
         * 'https://www.zhihu.com/people/liu-kan-shan-68-21', // 盐选心理
         * 'https://www.zhihu.com/people/liu-kan-shan-71-80', // 真实职业故事
         * 'https://www.zhihu.com/people/liu-kan-shan-78-51', // 盐选成长计划
         * 'https://www.zhihu.com/people/liu-kan-shan-82', // 盐选文学甄选
         * 'https://www.zhihu.com/people/liu-kan-shan-94-42', // 盐选健康必修课
         * 'https://www.zhihu.com/people/liu-kan-shan-94-42', // 盐选健康必修课
         * 'https://www.zhihu.com/people/liu-kan-shan-9-68', // 盐选博物馆
         * 'https://www.zhihu.com/people/liu-kan-shan-98-70', // 盐选奇妙物语
         */
    ]
    let selector = {
        main: '.QuestionMainAction',
        list: '.List-item:not(.PlaceHolder)',
        content: '.ContentItem',
        src: '.ContentItem-time a',
        link: '.AuthorInfo-content a.UserLink-link',
    }

    let process = () => {
        let anslist = document.querySelector(selector.list).parentElement
        anslist.addEventListener(
            'DOMNodeInserted',
            () => {
                if (typeof timer !== 'undefined') {
                    clearTimeout(timer)
                }
                var timer = setTimeout(() => {
                    anslist.querySelectorAll(selector.list)
                    .forEach((answer, idx) => {
                        let userlink = answer.querySelector(selector.link)
                        let anslink = answer.querySelector(selector.src)
                        if (answer.dataset.processed) {
                            return
                        }

                        let ulink = userlink ? userlink.href : ''
                        if (blacklist.includes(ulink) || !ulink.search(bprefix)) {
                            let username = userlink.innerText
                            answer.dataset.processed = true
                            if (makelog) {
                                console.log(
                                    `[ZHIHU] Answer Has Been Hidden\n` +
                                    `User: ${username}\n` +
                                    `Aref: ${anslink.href}`
                                )
                            }
                            let content = answer.querySelector(selector.content)
                            content.style.height = 0
                            content.style.overflow = 'hidden'

                            let replacer = document.createElement('center')
                            replacer.style.cursor = 'pointer'
                            replacer.innerText = `此处已屏蔽一条来自【${username}】的回答`
                            replacer.onclick = () => {
                                content.style.height = 'auto'
                                replacer.parentNode.removeChild(replacer)
                            }
                            answer.appendChild(replacer)
                        }
                    })
                }, timeout)
            },
            false
        )
    }

    let mainbtn = document.querySelectorAll(selector.main)
    if (mainbtn.length) {
        mainbtn.forEach((ele) => {
            ele.onclick = () => {
                setTimeout(function() {
                    process()
                }, timeout * 2)
            }
        })
        return
    }
    process()
})();
