import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)
import Layout from '@/layout'

export const constantRoutes = [
    {
        path: '/redirect',
        component: Layout,
        hidden: true,
        children: [
            {
                path: '/redirect/:path*',
                component: () => import('@/views/redirect/index')
            }
        ]
    },
    {
        path: '/login',
        component: () => import('@/views/login/index'),
        hidden: true
    },
    {
        path: '/updatePassword',
        component: () => import('@/views/login/index'),
        hidden: true
    },
    {
        path: '/flowableFormEdit',
        component: Layout,
        hidden: true,
        children: [{
            path: '/',
            name:'flowableFormEdit',
            component: () => import('@/views/flowable/form/edit'),
            meta: {title: '修改表单'}
        }]

    },
    {
        path: '/404',
        component: () => import('@/views/exception/404'),
        hidden: true
    },
    {
        path: '/',
        component: Layout,
        redirect: '/dashboard',
        //meta: {title: '首页', icon: 'dashboard'},
        children: [{
            path: 'dashboard',
            name: 'dashboard',
            component: () => import('@/views/dashboard/index'),
            children: null,
            meta: {title: '首页', icon: 'home', affix: true}
        }]
    }
]
const createRouter = () => new Router({
    // 前后分离部署可使用history
    // mode: 'history',
    // 与springboot合并部署应改成hash，并把编译后文件夹dist下的文件全部拷贝到springboot工程路径:/src/main/resources/static
    mode: 'hash',
    scrollBehavior: () => ({y: 0}),
    routes: constantRoutes
})
const router = createRouter()

export function resetRouter() {
    const newRouter = createRouter()
    router.matcher = newRouter.matcher
}

export default router
