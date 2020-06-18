import request from '@/utils/request'
import {getAction, postAction, deleteAction} from '@/api/manage'
import {getToken, setToken, removeToken} from '@/utils/auth'
import router, {resetRouter, constantRoutes, permissionRoutes} from '@/router'
import store from "../index";

const state = {
    token: '',
    name: '',
    avatar: '',
    sysUser: {},
    sysRole: {},
    sysRoles: [],
    sysOrg: {},
    funcIds: [],
    permissions: []
}

const mutations = {
    SET_TOKEN: (state, token) => {
        state.token = token
    },
    SET_NAME: (state, name) => {
        state.name = name
    },
    SET_AVATAR: (state, avatar) => {
        state.avatar = avatar
    },
    SET_USER: (state, user) => {
        state.sysUser = user
    },
    SET_ROLE: (state, role) => {
        state.sysRole = role
    },
    SET_ROLES: (state, roles) => {
        state.sysRoles = roles
    },
    SET_ORG: (state, sysOrg) => {
        state.sysOrg = sysOrg
    },
    SET_FUNCIDS: (state, funcIds) => {
        state.funcIds = funcIds
    },
    SET_PERMISSIONS: (state, permissions) => {
        state.permissions = permissions
    },
}

const actions = {
    login({commit}, sysLoginForm) {
        return new Promise((resolve, reject) => {
            Object.assign(sysLoginForm,{grant_type:'password',scope:'admin'})
            request({
                url: '/oauth/token',
                headers: {
                    isToken:false,
                    'Authorization': 'Basic emptenhmemhsOjE='
                },
                method: 'post',
                params: sysLoginForm
            }).then(data => {
                // const {data} = response
                commit('SET_TOKEN', data.access_token)
                setToken(data.access_token)
                resolve()
            }).catch(error => {
                reject(error)
            })
        })
    },
    getInfo: function ({commit, state}, roleId) {
        return new Promise((resolve, reject) => {
            const hasToken = getToken()
            commit('SET_TOKEN', hasToken)
            getAction('/sys/user/getUserInfo', {roleId}).then(response => {
                const {data} = response
                if (!data) {
                    reject('登录失败，请重新登录')
                }
                const {sysUser, sysRole, sysRoles, sysOrg, routes, funcIds, permissions, avatar} = data
                // roles must be a non-empty array
                if (!sysRoles || sysRoles.length == 0) {
                    reject('该用户未配置角色，请联系管理员授权')
                }
                commit('SET_NAME', sysUser.userName)
                commit('SET_AVATAR', avatar || require('@/assets/avatar.gif'))
                commit('SET_USER', sysUser)
                commit('SET_ROLE', sysRole)
                commit('SET_ROLES', sysRoles)
                commit('SET_ORG', sysOrg)
                commit('SET_FUNCIDS', funcIds)
                commit('SET_PERMISSIONS', permissions)
                resolve(data)
            }).catch(error => {
                reject(error)
            })
        })
    },

    // user logout
    logout({commit, state}) {
        return new Promise((resolve, reject) => {
            deleteAction('/token/logout', state.token).then(() => {
                commit('SET_TOKEN', '')
                commit('SET_NAME', '')
                commit('SET_AVATAR', '')
                commit('SET_USER', null)
                commit('SET_ROLE', null)
                commit('SET_ROLES', [])
                commit('SET_ORG', null)
                commit('SET_FUNCIDS', [])
                commit('SET_PERMISSIONS', [])
                removeToken()
                resetRouter()
                resolve()
            }).catch(error => {
                reject(error)
            })
        })
    },

    clearLoginInfo({commit}) {
        return new Promise(resolve => {
            commit('SET_TOKEN', '')
            commit('SET_NAME', '')
            commit('SET_AVATAR', '')
            commit('SET_USER', null)
            commit('SET_ROLE', null)
            commit('SET_ROLES', [])
            commit('SET_ORG', null)
            commit('SET_FUNCIDS', [])
            commit('SET_PERMISSIONS', [])
            removeToken()
            resetRouter()
            resolve()
        })
    },

    // remove token
    resetToken({commit}) {
        return new Promise(resolve => {
            commit('SET_TOKEN', '')
            removeToken()
            resolve()
        })
    },
    changeRoles({commit, dispatch}, role) {
        return new Promise(async resolve => {
            const data = await dispatch('getInfo', role.roleId)
            const permissionRoutes = await dispatch('permission/generateRoutes', data, {root: true})
            resetRouter()
            router.addRoutes(permissionRoutes)
            router.addRoutes([{path: '*', redirect: '/404', hidden: true}]);
            await dispatch('tagsView/delAllViews', null, {root: true})
            router.push({path: '/'})
            resolve()
        })
    }
}

export default {
    namespaced: true,
    state,
    mutations,
    actions
}

