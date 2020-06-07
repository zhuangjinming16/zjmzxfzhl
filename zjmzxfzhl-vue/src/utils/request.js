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
            // config.headers['token'] = getToken()
            config.headers['Authorization'] = 'Bearer ' + getToken()
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

        if (res && res.code && res.code !== 200) {
            if (res.code === 401 || res.code === 403) {
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
            //打印后台返回数据，用于mock数据
            // let uri = response.config.url.replace(response.config.baseURL,'')
            // let log = '{url:"' + uri + '",type:"' + response.config.method + '",response: config => {return '+ JSON.stringify(res) +'}},'
            // console.log(log)
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
