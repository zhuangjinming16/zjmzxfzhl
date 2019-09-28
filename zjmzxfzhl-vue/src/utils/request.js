import axios from 'axios'
import {MessageBox, Message, Loading} from 'element-ui'
import store from '@/store'
import {getToken} from '@/utils/auth'

const service = axios.create({
    baseURL: process.env.VUE_APP_BASE_API, // url = base url + request url
    withCredentials: true, // send cookies when cross-domain requests
    timeout: 50000 // request timeout
})

service.interceptors.request.use(
    config => {
        Loading.service({text: "Loading..."});
        if (store.getters.token) {
            config.headers['token'] = getToken()
        }
        return config
    },
    error => {
        console.log(error) // for debug
        Message.error('请求超时!');
        return Promise.reject(error)
    }
)

service.interceptors.response.use(
    response => {
        Loading.service().close();
        const res = response.data

        if (res.code !== 200) {
            if (res.code === 801 || res.code === 802 || res.code === 803) {
                MessageBox.confirm('登录超时，请重新登录', '提示', {
                    confirmButtonText: '重新登录',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    store.dispatch('user/resetToken').then(() => {
                        location.reload()
                    })
                })
            } else {
                Message.error(res.msg || 'error')
            }
            return Promise.reject(res.msg || 'error')
        } else {
            return res
        }
    },
    error => {
        Loading.service().close();
        console.log('err' + error) // for debug
        Message.error(error.message)
        return Promise.reject(error)
    }
)

export default service
