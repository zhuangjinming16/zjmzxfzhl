import request from '@/utils/request'

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

export function deleteAction(url, parameter) {
    return request({
        url: url,
        method: 'delete',
        params: parameter
    })
}
