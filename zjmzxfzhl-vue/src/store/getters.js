const getters = {
    sidebar: state => state.app.sidebar,
    device: state => state.app.device,
    size: state => state.app.size,
    token: state => state.user.token,
    avatar: state => state.user.avatar,
    name: state => state.user.name,
    sysUser: state => state.user.sysUser,
    sysRole: state => state.user.sysRole,
    sysRoles: state => state.user.sysRoles,
    sysOrg: state => state.user.sysOrg,
    permissions: state => state.user.permissions,
    routes: state => state.permission.routes,
    addRoutes: state => state.permission.addRoutes,
    visitedViews: state => state.tagsView.visitedViews,
    cachedViews: state => state.tagsView.cachedViews,
}
export default getters
