import {constantRoutes} from '@/router'
import Layout from '@/layout'

const state = {
    routes: [],
    addRoutes: []
}

const mutations = {
    SET_ROUTES: (state, routes) => {
        state.addRoutes = routes
        state.routes = constantRoutes.concat(routes)
    }
}

const actions = {
    generateRoutes({commit}, {routes}) {
        return new Promise(resolve => {
            let permissionRoutes = makeRoutes(routes)
            commit('SET_ROUTES', permissionRoutes)
            resolve(permissionRoutes)
        })
    }
}

export function makeRoutes(routes) {
    const res = []
    routes.forEach(route => {
        const {
            path,
            redirect,
            hidden,
            name,
            meta,
            component,
            children
        } = route
        const oRouter = {
            path,
            redirect,
            hidden,
            name,
            meta,
            component(resolve) {
                let componentPath = ''
                if (!component || component === '') {
                    require(['@/layout/index.vue'], resolve)
                    return
                } else {
                    componentPath = component
                }
                require([`@/${componentPath}.vue`], resolve)
            },
            children: children && children.length > 0 ? makeRoutes(children) : []
        }
        res.push(oRouter)
    })
    return res
}

export default {
    namespaced: true,
    state,
    mutations,
    actions
}
