import store from '@/store'

/**
 *  1.若参数为字符串则以且进行解析，字符串以','隔开并用且解析
 *  2.若参数为数组则以或进行解析
 *  3.若参数为数组，且数组元素有包含 ',' 则以依次判断 且跟或
 *  例如：
 *  v-permission="'sys:config:save'" -> sys:config:save
 *  v-permission="'sys:config:save,sys:config:update']" -> sys:config:save && sys:config:update
 *  v-permission="['sys:config:save','sys:config:update']" -> sys:config:save || sys:config:update
 *  v-permission="['sys:config:save','sys:config:list,sys:config:update']" -> sys:config:save || (sys:config:list && sys:config:update)
 */
export default {
    inserted(el, binding, vnode) {
        let {value} = binding

        if (!value) {
            throw new Error(`权限不能为空! 例如 v-permission="'sys:config:save'",v-permission="['sys:config:save','sys:config:update']"`)
        }
        if (typeof value === 'string' && value !== '') {
            value = [value]
        }
        if (!(value instanceof Array && value.length > 0)) {
            throw new Error(`权限不能为空! 例如 v-permission="'sys:config:save'",v-permission="['sys:config:save','sys:config:update']"`)
        }
        const permissions = value
        const allPermissions = store.getters && store.getters.permissions
        const hasPermission = permissions.some(permission => {
            if (!permission.indexOf(',')) {
                return allPermissions.includes(permission)
            } else {
                let permissionSubAnds = permission.split(',')
                let hasPermissionSubAnd = permissionSubAnds.every(permissionSubAdd => {
                    return allPermissions.includes(permissionSubAdd)
                })
                return hasPermissionSubAnd
            }
        })

        let parentNode1 = el.parentNode
        if (!hasPermission) {
            el.parentNode && el.parentNode.removeChild(el)
        }
    }
}
