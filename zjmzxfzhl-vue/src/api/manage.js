import request from '@/utils/request'
import {Message} from 'element-ui'

export function postAction(url, parameter) {
    return request({
        url: url,
        method: 'post',
        data: parameter
    })
}

export function httpAction(url, parameter, method) {
    return request({
        url: url,
        method: method,
        data: parameter
    })
}

export function putAction(url, parameter) {
    return request({
        url: url,
        method: 'put',
        data: parameter
    })
}

export function getAction(url, parameter) {
    return request({
        url: url,
        method: 'get',
        params: parameter
    })
}

export function downloadAction(url, method, parameter, filename) {
    return request({
        url: url,
        method: method,
        params: parameter,
        responseType: 'blob',
    }).then(response => {
        const type = response.type || ''
        if (type.includes('application/json')) {
            let reader = new FileReader()
            reader.onload = e => {
                if (e.target.readyState === 2) {
                    let res = {}
                    res = JSON.parse(e.target.result)
                    Message.error(res.msg)
                }
            }
            reader.readAsText(response)
        }else {
            filename = decodeURI(filename);
            if (typeof window.navigator.msSaveBlob !== 'undefined') {
                window.navigator.msSaveBlob(response, filename)
            } else {
                var blobURL = window.URL.createObjectURL(response)// 将blob对象转为一个URL
                var tempLink = document.createElement('a')// 创建一个a标签
                tempLink.style.display = 'none'
                tempLink.href = blobURL
                tempLink.setAttribute('download', filename)// 给a标签添加下载属性
                if (typeof tempLink.download === 'undefined') {
                    tempLink.setAttribute('target', '_blank')
                }
                document.body.appendChild(tempLink)// 将a标签添加到body当中
                tempLink.click()// 启动下载
                document.body.removeChild(tempLink)// 下载完毕删除a标签
                window.URL.revokeObjectURL(blobURL)
            }
        }
    }).catch((error) => {
        console.log(error)
    })
}

export function deleteAction(url, parameter) {
    return request({
        url: url,
        method: 'delete',
        params: parameter
    })
}
