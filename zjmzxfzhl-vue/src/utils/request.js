import axios from 'axios'
import {MessageBox, Message, Loading} from 'element-ui'
import errorCode from '@/utils/errorCode'
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
        let token = store.getters.token;
        if (token) {
            config.headers['Authorization'] = 'Bearer ' + token
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
        const status = Number(response.status) || 200
        const res = response.data
        const message = res.msg || errorCode[status] || errorCode['default']
        if(status === 401 || (res && res.code && res.code === 401)){
            store.dispatch('user/resetToken').then(() => {
                location.reload()
            })
            return
        }

        if(status !== 200 || (res && res.code && res.code !== 200)){
            Message.error(message)
            return Promise.reject(message)
        }

        //打印后台返回数据，用于mock数据
        // let uri = response.config.url.replace(response.config.baseURL,'')
        // let log = '{url:"' + uri + '",type:"' + response.config.method + '",response: config => {return '+ JSON.stringify(res) +'}},'
        // console.log(log)
        return res
    },
    error => {
        Loading.service().close();
        if(error.response && error.response.status && error.response.status === 401){
            store.dispatch('user/resetToken').then(() => {
                location.reload()
            })
            return
        }
        const message = (error.response && error.response.data && (error.response.data.msg || error.response.data.message)) || error.message
        console.log('err' + error) // for debug
        Message.error(message)
        return Promise.reject(error)
    }
)

export default service
