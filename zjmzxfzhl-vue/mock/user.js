export default [
    {
        url: '/sys/login',
        type: 'post',
        response: config => {
            return {
                code: 200,
                data: 'token'
            }
        }
    },
    {
        url: '/sys/user/getUserInfo',
        type: 'get',
        response: config => {
            return {
                "code": 200,
                "msg": "success",
                "data": {
                    "sysRole": {"roleId": "admin", "roleName": "admin", "sortNo": 1, "remark": "1"},
                    "routes": [{
                        "routeId": "sys",
                        "path": "/sys",
                        "component": "",
                        "redirect": "",
                        "hidden": false,
                        "name": "Sys",
                        "routeParentId": null,
                        "meta": {"title": "系统管理", "icon": "", "isCache": true, "affix": false, "permissions": null},
                        "children": [{
                            "routeId": "sysCodeType",
                            "path": "codeType",
                            "component": "views/sys/codeType/index",
                            "redirect": null,
                            "hidden": false,
                            "name": "SysCodeType",
                            "routeParentId": "sys",
                            "meta": {
                                "title": "代码类别",
                                "icon": null,
                                "isCache": true,
                                "affix": false,
                                "permissions": null
                            },
                            "children": null
                        }, {
                            "routeId": "sysCodeInfo",
                            "path": "codeInfo",
                            "component": "views/sys/codeInfo/index",
                            "redirect": "",
                            "hidden": false,
                            "name": "SysCodeInfo",
                            "routeParentId": "sys",
                            "meta": {"title": "代码信息", "icon": "", "isCache": true, "affix": false, "permissions": null},
                            "children": null
                        }, {
                            "routeId": "sysConfig",
                            "path": "config",
                            "component": "views/sys/config/index",
                            "redirect": null,
                            "hidden": false,
                            "name": "SysConfig",
                            "routeParentId": "sys",
                            "meta": {"title": "参数管理", "icon": "", "isCache": true, "affix": false, "permissions": null},
                            "children": null
                        }, {
                            "routeId": "sysMenu",
                            "path": "menu",
                            "component": "views/sys/menu/index",
                            "redirect": "",
                            "hidden": false,
                            "name": "SysMenu",
                            "routeParentId": "sys",
                            "meta": {"title": "菜单管理", "icon": "", "isCache": true, "affix": false, "permissions": null},
                            "children": null
                        }, {
                            "routeId": "sysOrg",
                            "path": "org",
                            "component": "views/sys/org/index",
                            "redirect": null,
                            "hidden": false,
                            "name": "SysOrg",
                            "routeParentId": "sys",
                            "meta": {
                                "title": "机构管理",
                                "icon": null,
                                "isCache": true,
                                "affix": false,
                                "permissions": null
                            },
                            "children": null
                        }, {
                            "routeId": "sysRole",
                            "path": "role",
                            "component": "views/sys/role/index",
                            "redirect": "",
                            "hidden": false,
                            "name": "SysRole",
                            "routeParentId": "sys",
                            "meta": {"title": "角色管理", "icon": "", "isCache": true, "affix": false, "permissions": null},
                            "children": null
                        }, {
                            "routeId": "sysFunc",
                            "path": "func",
                            "component": "views/sys/func/index",
                            "redirect": "",
                            "hidden": false,
                            "name": "SysFunc",
                            "routeParentId": "sys",
                            "meta": {"title": "功能管理", "icon": "", "isCache": true, "affix": false, "permissions": null},
                            "children": null
                        }, {
                            "routeId": "sysUser",
                            "path": "user",
                            "component": "views/sys/user/index",
                            "redirect": "",
                            "hidden": false,
                            "name": "SysUser",
                            "routeParentId": "sys",
                            "meta": {"title": "用户管理", "icon": "", "isCache": true, "affix": false, "permissions": null},
                            "children": null
                        }]
                    }],
                    "permissions": ["sys:user:list", "sys:role:save", "sys:config:save", "sys:config:update", "sys:role:permission", "sys:role:update", "sys:config:delete", "sys:user:delete", "sys:config:list", "sys:role:delete", "sys:role:list", "sys:user:update", "sys:user:save", "sys:role:roleUser"],
                    "funcIds": ["func-1", "func-2", "func-3", "func-4", "sysRole-1", "sysRole-2", "sysRole-3", "sysRole-4", "sysRole-5", "sysRole-6", "sysUser-1", "sysUser-2", "sysUser-3", "sysUser-4"],
                    "sysUser": {
                        "userId": "admin",
                        "userName": "admin",
                        "password": "1",
                        "sex": "1",
                        "roleId": "admin",
                        "orgId": "1",
                        "mobile": "1",
                        "idCardNo": "1",
                        "email": "1",
                        "status": "1",
                        "sortNo": 1,
                        "remark": "1"
                    },
                    "sysOrg": {
                        "orgId": "1",
                        "orgName": "总行",
                        "parentOrgId": "",
                        "shortName": "总行",
                        "orgType": "1",
                        "orgLevel": "1",
                        "orgLevelCode": "1",
                        "remark": "",
                        "isLeaf": "0",
                        "parentOrgName": null
                    },
                    "sysRoles": [{"roleId": "admin", "roleName": "admin", "sortNo": 1, "remark": "1"}]
                }
            }
        }
    },
    {
        url: '/sys/logout', type: 'post', response: config => {
            return {"code": 200, "msg": "注销成功！", "data": null}
        }
    },
    {url:"/sys/codeType/list",type:"get",response: config => {return {"code":200,"msg":"success","data":{"records":[{"codeTypeId":"yesOrNo","codeTypeName":"是否","sortNo":1,"remark":null},{"codeTypeId":"userSex","codeTypeName":"用户性别","sortNo":2,"remark":""},{"codeTypeId":"orgLevel","codeTypeName":"机构级别","sortNo":3,"remark":null},{"codeTypeId":"orgType","codeTypeName":"机构类型","sortNo":4,"remark":null}],"total":4,"size":10,"current":1,"searchCount":true,"pages":1}}}},
    {url:"/sys/codeType/update",type:"put",response: config => {return {"code":200,"msg":"success","data":null}}},
    {url:"/sys/codeType/save",type:"post",response: config => {return {"code":200,"msg":"success","data":null}}},
    {url:"/sys/codeType/delete",type:"delete",response: config => {return {"code":200,"msg":"success","data":null}}},
    {url:"/sys/codeInfo/getSysCodeInfos",type:"get",response: config => {return {"code":200,"msg":"success","data":{"orgType":[{"codeInfoId":"orgType-1","codeTypeId":"orgType","value":"1","content":"普通机构","parentValue":null,"sortNo":1,"remark":null,"isOk":"1"},{"codeInfoId":"orgType-2","codeTypeId":"orgType","value":"2","content":"部门","parentValue":"","sortNo":2,"remark":"","isOk":"1"}],"userSex":[{"codeInfoId":"7e8b9c8368aa4d82a505245ae51a81db","codeTypeId":"userSex","value":"1","content":"男","parentValue":"","sortNo":1,"remark":"","isOk":"1"},{"codeInfoId":"284bec072eab42d19f57ddb1ddcea911","codeTypeId":"userSex","value":"2","content":"女","parentValue":"","sortNo":2,"remark":"","isOk":"1"},{"codeInfoId":"ab558822b356411b9097eb79dbbfe2b7","codeTypeId":"userSex","value":"3","content":"未知","parentValue":"","sortNo":3,"remark":"","isOk":"1"}],"orgLevel":[{"codeInfoId":"orgLevel-1","codeTypeId":"orgLevel","value":"1","content":"一级","parentValue":null,"sortNo":1,"remark":null,"isOk":"1"},{"codeInfoId":"orgLevel-2","codeTypeId":"orgLevel","value":"2","content":"二级","parentValue":"","sortNo":2,"remark":"","isOk":"1"},{"codeInfoId":"orgLevel-3","codeTypeId":"orgLevel","value":"3","content":"三级","parentValue":"","sortNo":3,"remark":"","isOk":"1"}],"yesOrNo":[{"codeInfoId":"eb32eca53d9849629f5fdf1c04f094d5","codeTypeId":"yesOrNo","value":"1","content":"是","parentValue":"","sortNo":1,"remark":"","isOk":""},{"codeInfoId":"f6ed854423aa40d2a7cba213adef5edc","codeTypeId":"yesOrNo","value":"0","content":"否","parentValue":"","sortNo":2,"remark":"","isOk":""}]}}}},
    {url:"/sys/codeInfo/save",type:"post",response: config => {return {"code":200,"msg":"success","data":null}}},
    {url:"/sys/codeInfo/list",type:"get",response: config => {return {"code":200,"msg":"success","data":{"records":[{"codeInfoId":"1","codeTypeId":"yesOrNo","value":"1","content":"1","parentValue":"1","sortNo":1,"remark":"1","isOk":"1"},{"codeInfoId":"eb32eca53d9849629f5fdf1c04f094d5","codeTypeId":"yesOrNo","value":"1","content":"是","parentValue":"","sortNo":1,"remark":"","isOk":""},{"codeInfoId":"f6ed854423aa40d2a7cba213adef5edc","codeTypeId":"yesOrNo","value":"0","content":"否","parentValue":"","sortNo":2,"remark":"","isOk":""}],"total":3,"size":10,"current":1,"searchCount":true,"pages":1}}}},
    {url:"/sys/codeInfo/update",type:"put",response: config => {return {"code":200,"msg":"success","data":null}}},
    {url:"/sys/codeInfo/delete",type:"delete",response: config => {return {"code":200,"msg":"success","data":null}}},
    {url:"/sys/config/list",type:"get",response: config => {return {"code":200,"msg":"success","data":{"records":[{"configId":"1","configName":"中文中文","configValue":"1","sortNo":1,"remark":"1"},{"configId":"2","configName":"中文中文","configValue":"2","sortNo":2,"remark":"2"},{"configId":"3","configName":"中文中文","configValue":"3","sortNo":3,"remark":"3"},{"configId":"4","configName":"中文中文","configValue":"4","sortNo":4,"remark":"4"},{"configId":"5","configName":"中文中文","configValue":"5","sortNo":5,"remark":"5"},{"configId":"6","configName":"中文中文","configValue":"6","sortNo":6,"remark":"6"},{"configId":"7","configName":"中文中文","configValue":"7","sortNo":7,"remark":"7"},{"configId":"8","configName":"中文中文","configValue":"8","sortNo":8,"remark":"8"},{"configId":"9","configName":"中文中文","configValue":"9","sortNo":9,"remark":"9"}],"total":9,"size":10,"current":1,"searchCount":true,"pages":1}}}},
    {url:"/sys/config/save",type:"post",response: config => {return {"code":200,"msg":"success","data":null}}},
    {url:"/sys/config/update",type:"put",response: config => {return {"code":200,"msg":"success","data":null}}},
    {url:"/sys/config/delete",type:"delete",response: config => {return {"code":200,"msg":"success","data":null}}},
    {url:"/sys/menu/getTreeData",type:"get",response: config => {return {"code":200,"msg":"success","data":[{"id":"sys","label":"系统管理","parentId":null,"disabled":null,"isLeaf":false,"data":{"menuId":"sys","menuName":"系统管理","parentMenuId":"","menuIcon":"","menuUrl":"/sys","menuPermissions":null,"component":"","redirect":"","hidden":"0","isRoute":"1","routeName":"Sys","isCache":"1","affix":"0","isLeaf":"0","sortNo":10000,"parentMenuName":null},"children":[{"id":"sysCodeType","label":"代码类别","parentId":"sys","disabled":null,"isLeaf":true,"data":{"menuId":"sysCodeType","menuName":"代码类别","parentMenuId":"sys","menuIcon":null,"menuUrl":"codeType","menuPermissions":null,"component":"views/sys/codeType/index","redirect":null,"hidden":"0","isRoute":"1","routeName":"SysCodeType","isCache":"1","affix":"0","isLeaf":"1","sortNo":11000,"parentMenuName":"系统管理"},"children":null},{"id":"sysCodeInfo","label":"代码信息","parentId":"sys","disabled":null,"isLeaf":true,"data":{"menuId":"sysCodeInfo","menuName":"代码信息","parentMenuId":"sys","menuIcon":"","menuUrl":"codeInfo","menuPermissions":"","component":"views/sys/codeInfo/index","redirect":"","hidden":"0","isRoute":"1","routeName":"SysCodeInfo","isCache":"1","affix":"0","isLeaf":"1","sortNo":12000,"parentMenuName":"系统管理"},"children":null},{"id":"sysConfig","label":"参数管理","parentId":"sys","disabled":null,"isLeaf":true,"data":{"menuId":"sysConfig","menuName":"参数管理","parentMenuId":"sys","menuIcon":"","menuUrl":"config","menuPermissions":"","component":"views/sys/config/index","redirect":null,"hidden":"0","isRoute":"1","routeName":"SysConfig","isCache":"1","affix":"0","isLeaf":"1","sortNo":13000,"parentMenuName":"系统管理"},"children":null},{"id":"sysMenu","label":"菜单管理","parentId":"sys","disabled":null,"isLeaf":true,"data":{"menuId":"sysMenu","menuName":"菜单管理","parentMenuId":"sys","menuIcon":"","menuUrl":"menu","menuPermissions":"","component":"views/sys/menu/index","redirect":"","hidden":"0","isRoute":"1","routeName":"SysMenu","isCache":"1","affix":"0","isLeaf":"1","sortNo":14000,"parentMenuName":"系统管理"},"children":null},{"id":"sysOrg","label":"机构管理","parentId":"sys","disabled":null,"isLeaf":true,"data":{"menuId":"sysOrg","menuName":"机构管理","parentMenuId":"sys","menuIcon":null,"menuUrl":"org","menuPermissions":null,"component":"views/sys/org/index","redirect":null,"hidden":"0","isRoute":"1","routeName":"SysOrg","isCache":"1","affix":"0","isLeaf":"1","sortNo":15000,"parentMenuName":"系统管理"},"children":null},{"id":"sysRole","label":"角色管理","parentId":"sys","disabled":null,"isLeaf":true,"data":{"menuId":"sysRole","menuName":"角色管理","parentMenuId":"sys","menuIcon":"","menuUrl":"role","menuPermissions":"","component":"views/sys/role/index","redirect":"","hidden":"0","isRoute":"1","routeName":"SysRole","isCache":"1","affix":"0","isLeaf":"1","sortNo":17000,"parentMenuName":"系统管理"},"children":null},{"id":"sysFunc","label":"功能管理","parentId":"sys","disabled":null,"isLeaf":true,"data":{"menuId":"sysFunc","menuName":"功能管理","parentMenuId":"sys","menuIcon":"","menuUrl":"func","menuPermissions":"","component":"views/sys/func/index","redirect":"","hidden":"0","isRoute":"1","routeName":"SysFunc","isCache":"1","affix":"0","isLeaf":"1","sortNo":18000,"parentMenuName":"系统管理"},"children":null},{"id":"sysUser","label":"用户管理","parentId":"sys","disabled":null,"isLeaf":true,"data":{"menuId":"sysUser","menuName":"用户管理","parentMenuId":"sys","menuIcon":"","menuUrl":"user","menuPermissions":"","component":"views/sys/user/index","redirect":"","hidden":"0","isRoute":"1","routeName":"SysUser","isCache":"1","affix":"0","isLeaf":"1","sortNo":18000,"parentMenuName":"系统管理"},"children":null}]}]}}},
    {url:"/sys/menu/delete",type:"delete",response: config => {return {"code":200,"msg":"success","data":null}}},
    {url:"/sys/menu/save",type:"post",response: config => {return {"code":200,"msg":"success","data":{"menuId":"sysCodeType","menuName":"代码类别","parentMenuId":"sys","menuIcon":null,"menuUrl":"codeType","menuPermissions":null,"component":"views/sys/codeType/index","redirect":null,"hidden":"0","isRoute":"1","routeName":"SysCodeType","isCache":"1","affix":"0","isLeaf":"1","sortNo":11000,"parentMenuName":"系统管理"}}}},
    {url:"/sys/menu/update",type:"put",response: config => {return {"code":200,"msg":"success","data":{"menuId":"sysCodeType","menuName":"代码类别","parentMenuId":"sys","menuIcon":null,"menuUrl":"codeType","menuPermissions":null,"component":"views/sys/codeType/index","redirect":null,"hidden":"0","isRoute":"1","routeName":"SysCodeType","isCache":"1","affix":"0","isLeaf":"1","sortNo":11000,"parentMenuName":"系统管理"}}}},
    {url:"/sys/org/getTreeData",type:"get",response: config => {return {"code":200,"msg":"success","data":[{"id":"1","label":"总行","parentId":null,"disabled":null,"isLeaf":false,"data":{"orgId":"1","orgName":"总行","parentOrgId":"","shortName":"总行","orgType":"1","orgLevel":"1","orgLevelCode":"1","remark":"","isLeaf":"0","parentOrgName":null},"children":[{"id":"2","label":"福州分行","parentId":"1","disabled":null,"isLeaf":true,"data":{"orgId":"2","orgName":"福州分行","parentOrgId":"1","shortName":"福州分行","orgType":"1","orgLevel":"2","orgLevelCode":"1,2","remark":"","isLeaf":"1","parentOrgName":"总行"},"children":null},{"id":"3","label":"厦门分行","parentId":"1","disabled":null,"isLeaf":true,"data":{"orgId":"3","orgName":"厦门分行","parentOrgId":"1","shortName":"厦门分行","orgType":"1","orgLevel":"2","orgLevelCode":"1,3","remark":"","isLeaf":"1","parentOrgName":"总行"},"children":null},{"id":"4","label":"泉州分行","parentId":"1","disabled":null,"isLeaf":false,"data":{"orgId":"4","orgName":"泉州分行","parentOrgId":"1","shortName":"泉州分行","orgType":"1","orgLevel":"2","orgLevelCode":"1,4","remark":"","isLeaf":"0","parentOrgName":"总行"},"children":[{"id":"5","label":"泉港支行","parentId":"4","disabled":null,"isLeaf":true,"data":{"orgId":"5","orgName":"泉港支行","parentOrgId":"4","shortName":"泉港支行","orgType":"1","orgLevel":"3","orgLevelCode":"1,4,5","remark":"5","isLeaf":"1","parentOrgName":"泉州分行"},"children":null},{"id":"8","label":"惠安支行","parentId":"4","disabled":null,"isLeaf":true,"data":{"orgId":"8","orgName":"惠安支行","parentOrgId":"4","shortName":"惠安支行","orgType":"1","orgLevel":"3","orgLevelCode":"1,4,8","remark":"8","isLeaf":"1","parentOrgName":"泉州分行"},"children":null}]},{"id":"6","label":"风险管理部","parentId":"1","disabled":null,"isLeaf":false,"data":{"orgId":"6","orgName":"风险管理部","parentOrgId":"1","shortName":"风险管理部","orgType":"2","orgLevel":"2","orgLevelCode":"1,6","remark":"6","isLeaf":"0","parentOrgName":"总行"},"children":null}]}]}}},
    {url:"/sys/org/update",type:"put",response: config => {return {"code":200,"msg":"success","data":{"orgId":"2","orgName":"福州分行","parentOrgId":"1","shortName":"福州分行","orgType":"1","orgLevel":"2","orgLevelCode":"1,2","remark":"","isLeaf":"1","parentOrgName":"总行"}}}},
    {url:"/sys/org/save",type:"post",response: config => {return {"code":200,"msg":"success","data":{"orgId":"2","orgName":"福州分行","parentOrgId":"1","shortName":"福州分行","orgType":"1","orgLevel":"2","orgLevelCode":"1,2","remark":"","isLeaf":"1","parentOrgName":"总行"}}}},
    {url:"/sys/org/delete",type:"delete",response: config => {return {"code":200,"msg":"success","data":null}}},
    {url:"/sys/menu/getTreeData",type:"get",response: config => {return {"code":200,"msg":"success","data":[{"id":"sys","label":"系统管理","parentId":null,"disabled":null,"isLeaf":false,"data":{"menuId":"sys","menuName":"系统管理","parentMenuId":"","menuIcon":"","menuUrl":"/sys","menuPermissions":null,"component":"","redirect":"","hidden":"0","isRoute":"1","routeName":"Sys","isCache":"1","affix":"0","isLeaf":"0","sortNo":10000,"parentMenuName":null},"children":[{"id":"sysCodeType","label":"代码类别","parentId":"sys","disabled":null,"isLeaf":true,"data":{"menuId":"sysCodeType","menuName":"代码类别","parentMenuId":"sys","menuIcon":null,"menuUrl":"codeType","menuPermissions":null,"component":"views/sys/codeType/index","redirect":null,"hidden":"0","isRoute":"1","routeName":"SysCodeType","isCache":"1","affix":"0","isLeaf":"1","sortNo":11000,"parentMenuName":"系统管理"},"children":null},{"id":"sysCodeInfo","label":"代码信息","parentId":"sys","disabled":null,"isLeaf":true,"data":{"menuId":"sysCodeInfo","menuName":"代码信息","parentMenuId":"sys","menuIcon":"","menuUrl":"codeInfo","menuPermissions":"","component":"views/sys/codeInfo/index","redirect":"","hidden":"0","isRoute":"1","routeName":"SysCodeInfo","isCache":"1","affix":"0","isLeaf":"1","sortNo":12000,"parentMenuName":"系统管理"},"children":null},{"id":"sysConfig","label":"参数管理","parentId":"sys","disabled":null,"isLeaf":true,"data":{"menuId":"sysConfig","menuName":"参数管理","parentMenuId":"sys","menuIcon":"","menuUrl":"config","menuPermissions":"","component":"views/sys/config/index","redirect":null,"hidden":"0","isRoute":"1","routeName":"SysConfig","isCache":"1","affix":"0","isLeaf":"1","sortNo":13000,"parentMenuName":"系统管理"},"children":null},{"id":"sysMenu","label":"菜单管理","parentId":"sys","disabled":null,"isLeaf":true,"data":{"menuId":"sysMenu","menuName":"菜单管理","parentMenuId":"sys","menuIcon":"","menuUrl":"menu","menuPermissions":"","component":"views/sys/menu/index","redirect":"","hidden":"0","isRoute":"1","routeName":"SysMenu","isCache":"1","affix":"0","isLeaf":"1","sortNo":14000,"parentMenuName":"系统管理"},"children":null},{"id":"sysOrg","label":"机构管理","parentId":"sys","disabled":null,"isLeaf":true,"data":{"menuId":"sysOrg","menuName":"机构管理","parentMenuId":"sys","menuIcon":null,"menuUrl":"org","menuPermissions":null,"component":"views/sys/org/index","redirect":null,"hidden":"0","isRoute":"1","routeName":"SysOrg","isCache":"1","affix":"0","isLeaf":"1","sortNo":15000,"parentMenuName":"系统管理"},"children":null},{"id":"sysRole","label":"角色管理","parentId":"sys","disabled":null,"isLeaf":true,"data":{"menuId":"sysRole","menuName":"角色管理","parentMenuId":"sys","menuIcon":"","menuUrl":"role","menuPermissions":"","component":"views/sys/role/index","redirect":"","hidden":"0","isRoute":"1","routeName":"SysRole","isCache":"1","affix":"0","isLeaf":"1","sortNo":17000,"parentMenuName":"系统管理"},"children":null},{"id":"sysFunc","label":"功能管理","parentId":"sys","disabled":null,"isLeaf":true,"data":{"menuId":"sysFunc","menuName":"功能管理","parentMenuId":"sys","menuIcon":"","menuUrl":"func","menuPermissions":"","component":"views/sys/func/index","redirect":"","hidden":"0","isRoute":"1","routeName":"SysFunc","isCache":"1","affix":"0","isLeaf":"1","sortNo":18000,"parentMenuName":"系统管理"},"children":null},{"id":"sysUser","label":"用户管理","parentId":"sys","disabled":null,"isLeaf":true,"data":{"menuId":"sysUser","menuName":"用户管理","parentMenuId":"sys","menuIcon":"","menuUrl":"user","menuPermissions":"","component":"views/sys/user/index","redirect":"","hidden":"0","isRoute":"1","routeName":"SysUser","isCache":"1","affix":"0","isLeaf":"1","sortNo":18000,"parentMenuName":"系统管理"},"children":null}]}]}}},
    {url:"/sys/func/list",type:"get",response: config => {return {"code":200,"msg":"success","data":{"records":[{"funcId":"sysRole-1","funcName":"查询","menuId":"sysRole","funcPermissions":"sys:role:list","remark":"","sortNo":1},{"funcId":"sysRole-2","funcName":"新增","menuId":"sysRole","funcPermissions":"sys:role:save","remark":"","sortNo":2},{"funcId":"sysRole-3","funcName":"修改","menuId":"sysRole","funcPermissions":"sys:role:update","remark":"","sortNo":3},{"funcId":"sysRole-4","funcName":"删除","menuId":"sysRole","funcPermissions":"sys:role:delete","remark":"","sortNo":4},{"funcId":"sysRole-5","funcName":"角色授权","menuId":"sysRole","funcPermissions":"sys:role:permission","remark":"","sortNo":5},{"funcId":"sysRole-6","funcName":"分配用户","menuId":"sysRole","funcPermissions":"sys:role:roleUser","remark":"","sortNo":6}],"total":6,"size":10,"current":1,"searchCount":true,"pages":1}}}},
    {url:"/sys/func/save",type:"post",response: config => {return {"code":200,"msg":"success","data":null}}},
    {url:"/sys/func/update",type:"put",response: config => {return {"code":200,"msg":"success","data":null}}},
    {url:"/sys/func/delete",type:"delete",response: config => {return {"code":200,"msg":"success","data":null}}},
    {url:"/sys/user/list",type:"get",response: config => {return {"code":200,"msg":"success","data":{"records":[{"userId":"admin","userName":"admin","password":"1","sex":"1","roleId":"admin","orgId":"1","mobile":"1","idCardNo":"1","email":"1","status":"1","sortNo":1,"remark":"1"},{"userId":"user10","userName":"用户1","password":"1","sex":"1","roleId":"role1","orgId":"6","mobile":"1","idCardNo":"1","email":"1","status":"1","sortNo":2,"remark":""},{"userId":"user11","userName":"用户1","password":"1","sex":"1","roleId":"role1","orgId":"1","mobile":"1","idCardNo":"1","email":"1","status":"1","sortNo":2,"remark":""},{"userId":"user12","userName":"用户1","password":"1","sex":"1","roleId":"role1","orgId":"1","mobile":"1","idCardNo":"1","email":"1","status":"1","sortNo":2,"remark":""},{"userId":"user13","userName":"用户1","password":"1","sex":"1","roleId":"role1","orgId":"7","mobile":"1","idCardNo":"1","email":"1","status":"2","sortNo":2,"remark":""},{"userId":"user2","userName":"用户1","password":"1","sex":"1","roleId":"role1","orgId":"1","mobile":"1","idCardNo":"1","email":"1","status":"1","sortNo":2,"remark":""},{"userId":"user3","userName":"用户1","password":"1","sex":"1","roleId":"role1","orgId":"3","mobile":"1","idCardNo":"1","email":"1","status":"1","sortNo":2,"remark":""},{"userId":"user4","userName":"用户1","password":"1","sex":"1","roleId":"role1","orgId":"1","mobile":"1","idCardNo":"1","email":"1","status":"1","sortNo":2,"remark":""},{"userId":"user5","userName":"用户1","password":"1","sex":"1","roleId":"role1","orgId":"4","mobile":"1","idCardNo":"1","email":"1","status":"1","sortNo":2,"remark":""},{"userId":"user6","userName":"用户1","password":"1","sex":"1","roleId":"role1","orgId":"1","mobile":"1","idCardNo":"1","email":"1","status":"1","sortNo":2,"remark":""}],"total":14,"size":10,"current":1,"searchCount":true,"pages":2}}}},
    {url:"/sys/user/save",type:"post",response: config => {return {"code":200,"msg":"success","data":null}}},
    {url:"/sys/user/update",type:"put",response: config => {return {"code":200,"msg":"success","data":null}}},
    {url:"/sys/user/delete",type:"delete",response: config => {return {"code":200,"msg":"success","data":null}}},
    {url:"/sys/role/list",type:"get",response: config => {return {"code":200,"msg":"success","data":{"records":[{"roleId":"admin","roleName":"admin","sortNo":1,"remark":"1"},{"roleId":"role1","roleName":"角色1","sortNo":2,"remark":"2"}],"total":2,"size":10,"current":1,"searchCount":true,"pages":1}}}},
    {url:"/sys/role/save",type:"post",response: config => {return {"code":200,"msg":"success","data":null}}},
    {url:"/sys/role/update",type:"put",response: config => {return {"code":200,"msg":"success","data":null}}},
    {url:"/sys/role/delete",type:"delete",response: config => {return {"code":200,"msg":"success","data":null}}},
    {url:"/sys/role/getRolePermissions",type:"get",response: config => {return {"code":200,"msg":"success","data":{"permissionTree":[{"id":"sys","label":"系统管理","parentId":null,"disabled":null,"isLeaf":null,"data":{"rolePermissionId":null,"roleId":null,"permissionType":"1","menuOrFuncId":"sys","menuOrFuncName":"系统管理"},"children":[{"id":"sysCodeType","label":"代码类别","parentId":"sys","disabled":null,"isLeaf":null,"data":{"rolePermissionId":null,"roleId":null,"permissionType":"1","menuOrFuncId":"sysCodeType","menuOrFuncName":"代码类别"},"children":null},{"id":"sysCodeInfo","label":"代码信息","parentId":"sys","disabled":null,"isLeaf":null,"data":{"rolePermissionId":null,"roleId":null,"permissionType":"1","menuOrFuncId":"sysCodeInfo","menuOrFuncName":"代码信息"},"children":null},{"id":"sysConfig","label":"参数管理","parentId":"sys","disabled":null,"isLeaf":false,"data":{"rolePermissionId":null,"roleId":null,"permissionType":"1","menuOrFuncId":"sysConfig","menuOrFuncName":"参数管理"},"children":[{"id":"func-1","label":"查询","parentId":null,"disabled":null,"isLeaf":true,"data":{"rolePermissionId":null,"roleId":null,"permissionType":"2","menuOrFuncId":"func-1","menuOrFuncName":"查询"},"children":null},{"id":"func-2","label":"新增","parentId":null,"disabled":null,"isLeaf":true,"data":{"rolePermissionId":null,"roleId":null,"permissionType":"2","menuOrFuncId":"func-2","menuOrFuncName":"新增"},"children":null},{"id":"func-3","label":"修改","parentId":null,"disabled":null,"isLeaf":true,"data":{"rolePermissionId":null,"roleId":null,"permissionType":"2","menuOrFuncId":"func-3","menuOrFuncName":"修改"},"children":null},{"id":"func-4","label":"删除","parentId":null,"disabled":null,"isLeaf":true,"data":{"rolePermissionId":null,"roleId":null,"permissionType":"2","menuOrFuncId":"func-4","menuOrFuncName":"删除"},"children":null}]},{"id":"sysMenu","label":"菜单管理","parentId":"sys","disabled":null,"isLeaf":null,"data":{"rolePermissionId":null,"roleId":null,"permissionType":"1","menuOrFuncId":"sysMenu","menuOrFuncName":"菜单管理"},"children":null},{"id":"sysOrg","label":"机构管理","parentId":"sys","disabled":null,"isLeaf":null,"data":{"rolePermissionId":null,"roleId":null,"permissionType":"1","menuOrFuncId":"sysOrg","menuOrFuncName":"机构管理"},"children":null},{"id":"sysRole","label":"角色管理","parentId":"sys","disabled":null,"isLeaf":false,"data":{"rolePermissionId":null,"roleId":null,"permissionType":"1","menuOrFuncId":"sysRole","menuOrFuncName":"角色管理"},"children":[{"id":"sysRole-1","label":"查询","parentId":null,"disabled":null,"isLeaf":true,"data":{"rolePermissionId":null,"roleId":null,"permissionType":"2","menuOrFuncId":"sysRole-1","menuOrFuncName":"查询"},"children":null},{"id":"sysRole-2","label":"新增","parentId":null,"disabled":null,"isLeaf":true,"data":{"rolePermissionId":null,"roleId":null,"permissionType":"2","menuOrFuncId":"sysRole-2","menuOrFuncName":"新增"},"children":null},{"id":"sysRole-3","label":"修改","parentId":null,"disabled":null,"isLeaf":true,"data":{"rolePermissionId":null,"roleId":null,"permissionType":"2","menuOrFuncId":"sysRole-3","menuOrFuncName":"修改"},"children":null},{"id":"sysRole-4","label":"删除","parentId":null,"disabled":null,"isLeaf":true,"data":{"rolePermissionId":null,"roleId":null,"permissionType":"2","menuOrFuncId":"sysRole-4","menuOrFuncName":"删除"},"children":null},{"id":"sysRole-5","label":"角色授权","parentId":null,"disabled":null,"isLeaf":true,"data":{"rolePermissionId":null,"roleId":null,"permissionType":"2","menuOrFuncId":"sysRole-5","menuOrFuncName":"角色授权"},"children":null},{"id":"sysRole-6","label":"分配用户","parentId":null,"disabled":null,"isLeaf":true,"data":{"rolePermissionId":null,"roleId":null,"permissionType":"2","menuOrFuncId":"sysRole-6","menuOrFuncName":"分配用户"},"children":null}]},{"id":"sysFunc","label":"功能管理","parentId":"sys","disabled":null,"isLeaf":null,"data":{"rolePermissionId":null,"roleId":null,"permissionType":"1","menuOrFuncId":"sysFunc","menuOrFuncName":"功能管理"},"children":null},{"id":"sysUser","label":"用户管理","parentId":"sys","disabled":null,"isLeaf":false,"data":{"rolePermissionId":null,"roleId":null,"permissionType":"1","menuOrFuncId":"sysUser","menuOrFuncName":"用户管理"},"children":[{"id":"sysUser-1","label":"查询","parentId":null,"disabled":null,"isLeaf":true,"data":{"rolePermissionId":null,"roleId":null,"permissionType":"2","menuOrFuncId":"sysUser-1","menuOrFuncName":"查询"},"children":null},{"id":"sysUser-2","label":"新增","parentId":null,"disabled":null,"isLeaf":true,"data":{"rolePermissionId":null,"roleId":null,"permissionType":"2","menuOrFuncId":"sysUser-2","menuOrFuncName":"新增"},"children":null},{"id":"sysUser-3","label":"修改","parentId":null,"disabled":null,"isLeaf":true,"data":{"rolePermissionId":null,"roleId":null,"permissionType":"2","menuOrFuncId":"sysUser-3","menuOrFuncName":"修改"},"children":null},{"id":"sysUser-4","label":"删除","parentId":null,"disabled":null,"isLeaf":true,"data":{"rolePermissionId":null,"roleId":null,"permissionType":"2","menuOrFuncId":"sysUser-4","menuOrFuncName":"删除"},"children":null}]}]}],"permissions":["func-2","func-4","sys","sysUser-3","sysCodeInfo","sysRole","sysOrg","sysRole-1","sysMenu","func-3","sysUser-2","sysRole-5","sysConfig","func-1","sysRole-2","sysRole-3","sysUser","sysUser-1"]}}}},
    {url:"/sys/role/saveRolePermissions",type:"post",response: config => {return {"code":200,"msg":"success","data":null}}},
    {url:"/sys/role/getRoleUser",type:"get",response: config => {return {"code":200,"msg":"success","data":{"records":[{"userId":"user10","userName":"用户1","password":null,"sex":null,"roleId":null,"orgId":"6","mobile":null,"idCardNo":null,"email":null,"status":null,"sortNo":null,"remark":null}],"total":1,"size":10,"current":1,"searchCount":true,"pages":1}}}},
    {url:"/sys/role/saveRoleUsers",type:"post",response: config => {return {"code":200,"msg":"success","data":null}}},
    {url:"/sys/role/deleteRoleUsers",type:"delete",response: config => {return {"code":200,"msg":"success","data":null}}}
]
