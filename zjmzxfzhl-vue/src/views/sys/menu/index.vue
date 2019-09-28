<template>
    <div class="app-container">
        <el-row :gutter="5">
            <el-col :span="8">
                <div>
                    <el-button v-permission="'sys:menu:save'" icon="el-icon-plus" type="primary" @click="btnAdd">新增</el-button>
                    <el-button v-permission="'sys:menu:delete'" icon="el-icon-delete" @click="btnDelete">删除</el-button>
                    <el-input v-model="filterText" placeholder="请输入过滤条件" style="margin:10px 0px 10px 0px"/>
                    <el-tree
                            ref="menuTree"
                            :data="treeData"
                            node-key="menuId"
                            :props="defaultProps"
                            :filter-node-method="filterNode"
                            @node-click="handleNodeClick"
                            class="filter-tree"
                    />
                </div>
            </el-col>

            <el-col :span="16">
                <div>
                    <el-form ref="dataForm" :rules="rules" :model="temp" label-position="right" label-width="110px">
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="菜单ID" prop="menuId">
                                    <el-input v-model="temp.menuId" :readonly="status==='update'"/>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="上级菜单ID" prop="parentMenuId" hidden>
                                    <el-input v-model="temp.parentMenuId" readonly/>
                                </el-form-item>
                                <el-form-item label="上级菜单" prop="parentMenuName">
                                    <el-input v-model="temp.parentMenuName" readonly/>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="菜单名称" prop="menuName">
                                    <el-input v-model="temp.menuName"/>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="菜单图标" prop="menuIcon">
                                    <el-input v-model="temp.menuIcon"/>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="菜单URL" prop="menuUrl">
                                    <el-input v-model="temp.menuUrl"/>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="授权" prop="menuPermissions">
                                    <el-input v-model="temp.menuPermissions"/>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="组件" prop="component">
                                    <el-input v-model="temp.component"/>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="重定向" prop="redirect">
                                    <el-input v-model="temp.redirect"/>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="是否路由" prop="isRoute">
                                    <el-select v-model="temp.isRoute" placeholder="是否路由">
                                        <el-option v-for="item in dicts.yesOrNo" :key="item.value" :label="item.content" :value="item.value"></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="路由名称" prop="routeName">
                                    <el-input v-model="temp.routeName"/>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="是否隐藏" prop="hidden">
                                    <el-select v-model="temp.hidden" placeholder="是否隐藏">
                                        <el-option v-for="item in dicts.yesOrNo" :key="item.value" :label="item.content" :value="item.value"></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="是否缓存" prop="isCache">
                                    <el-select v-model="temp.isCache" placeholder="是否缓存">
                                        <el-option v-for="item in dicts.yesOrNo" :key="item.value" :label="item.content" :value="item.value"></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="是否常驻菜单" prop="affix">
                                    <el-select v-model="temp.affix" placeholder="是否常驻菜单">
                                        <el-option v-for="item in dicts.yesOrNo" :key="item.value" :label="item.content" :value="item.value"></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="是否叶子" prop="isLeaf">
                                    <el-select v-model="temp.isLeaf" placeholder="是否叶子" disabled>
                                        <el-option v-for="item in dicts.yesOrNo" :key="item.value" :label="item.content" :value="item.value"></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="排序号" prop="sortNo">
                                    <el-input v-model="temp.sortNo"/>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="24">
                                <el-form-item>
                                    <el-button v-permission="['sys:menu:save','sys:menu:update']" icon="el-icon-check" type="primary" @click="btnSave">保存</el-button>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </el-form>
                </div>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    import {getAction, putAction, postAction, deleteAction} from '@/api/manage'
    import {Message} from 'element-ui'
    export default {
        name: 'SysMenu',
        data() {
            return {
                dicts : [],
                status: '',
                temp: {
                    menuId: undefined,
                    menuName: '',
                    parentMenuId: '',
                    menuIcon: '',
                    menuUrl: '',
                    menuPermissions: '',
                    component: '',
                    redirect: '',
                    hidden: '0',
                    isRoute: '1',
                    routeName: '',
                    isCache: '1',
                    affix: '0',
                    isLeaf: '1',
                    sortNo: '',
                    parentMenuName: ''
                },
                rules: {
                    menuId: [{required: true, message: '该项不能为空', trigger: 'change'}],
                    menuName: [{required: true, message: '该项不能为空', trigger: 'change'}],
                    menuUrl: [{required: true, message: '该项不能为空', trigger: 'change'}],
                    sortNo: [{required: true, message: '该项不能为空', trigger: 'change'}]
                },
                filterText: '',
                treeData: [],
                defaultProps: {
                    children: 'children',
                    label: 'label',
                    isLeaf: 'isLeaf',
                    data : 'data'
                }
            }
        },
        watch: {
            filterText(val) {
                this.$refs.menuTree.filter(val)
            }
        },
        created() {
            this.getTreeData()
            this.getDicts('menuLevel,menuType,yesOrNo').then(({data}) => { this.dicts = data})
        },
        methods: {
            getTreeData() {
                getAction('/sys/menu/getTreeData', {}).then(res => {
                    const {data} = res
                    this.treeData = data
                })
            },
            filterNode(value, data) {
                if (!value) return true
                return data.label.indexOf(value) !== -1
            },
            handleNodeClick({data}) {
                this.status = 'update'
                this.temp = Object.assign({}, data) // copy obj
            },
            btnSave(){
                this.$refs['dataForm'].validate((valid) => {
                    if (valid) {
                        if(this.status==='update'){
                            putAction('/sys/menu/update', this.temp).then(({msg,data}) => {
                                Message.success(msg)
                                // this.resetTemp()
                                // this.getTreeData()
                                this.temp = Object.assign({}, data)
                                this.$refs.menuTree.getCurrentNode().label = data.menuName
                                this.$refs.menuTree.getCurrentNode().data = Object.assign({}, data)
                            })
                        }else{
                            postAction('/sys/menu/save', this.temp).then(({msg,data}) => {
                                Message.success(msg)
                                this.temp = Object.assign({}, data)
                                this.getTreeData()
                            })
                        }
                    }
                })
            },
            resetTemp() {
                this.temp = {
                    menuId: undefined,
                    menuName: '',
                    parentMenuId: '',
                    menuIcon: '',
                    menuUrl: '',
                    menuPermissions: '',
                    component: '',
                    redirect: '',
                    hidden: '0',
                    isRoute: '1',
                    routeName: '',
                    isCache: '1',
                    affix: '0',
                    isLeaf: '1',
                    sortNo: '',
                    parentMenuName: ''
                }
            },
            btnAdd(){
                let parentMenuId = ''
                let parentMenuName = ''
                if(this.temp.menuId){
                    parentMenuId = this.temp.menuId
                    parentMenuName = this.temp.menuName
                }
                this.resetTemp()
                this.temp.parentMenuId=parentMenuId
                this.temp.parentMenuName=parentMenuName
                this.status = 'create'
                this.$nextTick(() => {
                    this.$refs['dataForm'].clearValidate()
                })
            },
            btnDelete(){
                if(!this.temp.menuId){
                    Message.error('请选择要删除的记录')
                    return
                }
                let curNode = this.$refs.menuTree.getCurrentNode();
                if(curNode && curNode.children && curNode.children.length>0){
                    Message.error('请先删除叶子节点')
                    return
                }
                deleteAction('/sys/menu/delete', {id: this.temp.menuId}).then(({msg}) => {
                    Message.success(msg)
                    this.resetTemp()
                    this.$nextTick(() => {
                        this.$refs['dataForm'].clearValidate()
                    })
                    this.getTreeData()
                })
            }
        }
    }
</script>

