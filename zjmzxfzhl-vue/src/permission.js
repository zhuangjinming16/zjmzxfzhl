import router, {resetRouter, error404Routers} from './router'
import store from './store'
import {Message} from 'element-ui'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import {getToken} from '@/utils/auth' // get token from cookie
import getPageTitle from '@/utils/get-page-title'
import Layout from '@/layout'

NProgress.configure({showSpinner: false}) // NProgress Configuration

const whiteList = ['/login'] // no redirect whitelist
router.beforeEach((to, from, next) => {
    NProgress.start()
    document.title = getPageTitle(to.meta.title)
    const hasToken = getToken()
    if (hasToken) {
        if (to.path === '/login') {
            next({path: '/'})
            NProgress.done()
        } else {
            try {
                const hasPermissions = store.getters.name
                if (hasPermissions) {
                    next()
                } else {
                    // get user info
                    store.dispatch('user/getInfo').then(data => {
                        store.dispatch('permission/generateRoutes', data).then(permissionRoutes => {
                            //resetRouter()
                            router.addRoutes(permissionRoutes)
                            router.addRoutes([{ path: '*', redirect: '/404', hidden: true }]);
                            next({...to, replace: true})
                        })
                    })
                }
            } catch (error) {
                // remove token and go to login page to re-login
                store.dispatch('user/resetToken').then(() => {
                    Message.error(error || 'Has Error')
                    next(`/login?redirect=${to.path}`)
                    NProgress.done()
                })
            }
        }
    } else {
        /* has no token*/
        if (whiteList.indexOf(to.path) !== -1) {
            // in the free login whitelist, go directly
            next()
        } else {
            // other pages that do not have permission to access are redirected to the login page.
            next(`/login?redirect=${to.path}`)
            NProgress.done()
        }
    }
})

router.afterEach(() => {
    // finish progress bar
    NProgress.done()
})
