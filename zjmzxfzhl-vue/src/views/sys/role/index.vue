<template>
    <div class="app-container">
        <div class="filter-container">
            <el-input v-model="listQuery.roleId" placeholder="角色ID" style="width: 200px;" class="filter-item" @keyup.enter.native="btnQuery"/>
            <el-input v-model="listQuery.roleName" placeholder="角色名称" style="width: 200px;" class="filter-item" @keyup.enter.native="btnQuery"/>
            <el-dropdown v-permission="'sys:role:list'" split-button type="primary" @click="btnQuery" class="filter-item">
                <i class="el-icon-search el-icon--left"></i>查询
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item icon="el-icon-zoom-out" @click.native="btnReset">重置</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
            <el-button-group>
                <el-button v-permission="'sys:role:save'" icon="el-icon-plus" type="primary" @click="btnCreate" class="filter-item">新增
                </el-button>
                <el-button v-permission="'sys:role:delete'" icon="el-icon-delete" @click="btnDelete()" class="filter-item">批量删除</el-button>
            </el-button-group>
        </div>
        <el-table
                :data="records"
                ref="roleTable"
                @selection-change="selectionChange"
                border
                fit
                highlight-current-row
                style="width: 100%;"
                :cell-style="{padding:'3px'}"
        >
            <el-table-column type="selection" align="center">
            </el-table-column>
            <el-table-column label="角色ID" prop="roleId" align="center">
                <template slot-scope="scope">
                    <span>{{ scope.row.roleId }}</span>
                </template>
            </el-table-column>
            <el-table-column label="角色名称" prop="roleName" align="center">
                <template slot-scope="scope">
                    <span>{{ scope.row.roleName }}</span>
                </template>
            </el-table-column>
            <el-table-column label="排序号" prop="sortNo" align="center">
                <template slot-scope="scope">
                    <span>{{ scope.row.sortNo }}</span>
                </template>
            </el-table-column>
            <el-table-column label="备注" prop="remark" align="center">
                <template slot-scope="scope">
                    <span>{{ scope.row.remark }}</span>
                </template>
            </el-table-column>
            <el-table-column label="操作" align="center">
                <template slot-scope="{row}">
                    <el-dropdown>
                        <span class="el-dropdown-link">操作<i class="el-icon-arrow-down el-icon--right"></i></span>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item icon="el-icon-view" @click.native="btnView(row)">查看</el-dropdown-item>
                            <el-dropdown-item v-permission="'sys:role:update'" icon="el-icon-edit" divided @click.native="btnUpdate(row)">
                                修改</el-dropdown-item>
                            <el-dropdown-item v-permission="'sys:role:delete'" icon="el-icon-delete" divided @click.native="btnDelete(row.roleId)">
                                删除</el-dropdown-item>
                            <el-dropdown-item v-permission="'sys:role:getRolePermissions'" icon="el-icon-setting"
                                              divided @click.native="btnPermission(row.roleId)">
                                角色授权</el-dropdown-item>
                            <el-dropdown-item v-permission="'sys:role:getRoleUser'" icon="el-icon-setting"
                                              divided @click.native="btnRoleUser(row.roleId)">
                                分配用户</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </template>
            </el-table-column>
        </el-table>
        <pagination v-show="total>0" :total="total" :current.sync="listQuery.current" :size.sync="listQuery.size"
                    @pagination="list"/>

        <el-dialog title="角色" :visible.sync="dialogFormVisible">
            <el-form ref="dataForm" :rules="rules" :model="temp" :disabled="dialogStatus==='view'" label-position="right" label-width="110px">
                <el-form-item label="角色ID" prop="roleId">
                    <el-input v-model="temp.roleId" :readonly="dialogStatus==='update'"/>
                </el-form-item>
                <el-form-item label="角色名称" prop="roleName">
                    <el-input v-model="temp.roleName"/>
                </el-form-item>
                <el-form-item label="排序号" prop="sortNo">
                    <el-input v-model="temp.sortNo"/>
                </el-form-item>
                <el-form-item label="备注">
                    <el-input v-model="temp.remark" :autosize="{ minRows: 2, maxRows: 4}" type="textarea"
                              placeholder="请输入备注信息"/>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button icon="el-icon-close" @click="dialogFormVisible = false">取消</el-button>
                <el-button v-if="dialogStatus!=='view'" icon="el-icon-check" type="primary" @click="dialogStatus==='create'?createData():updateData()">
                    确定
                </el-button>
            </div>
        </el-dialog>

        <el-dialog :title="rolePermissionTitle" :visible.sync="dialogPermissionFormVisible">
            <div class="el-dialog-body-custom-height">
                <el-tree
                        ref="permissionTree"
                        :data="treeData"
                        node-key="id"
                        :props="defaultProps"
                        class="filter-tree"
                        default-expand-all
                        show-checkbox
                        check-strictly
                />
            </div>
            <div slot="footer" class="dialog-footer">
                <el-button icon="el-icon-close" @click="dialogPermissionFormVisible = false">取消</el-button>
                <el-button icon="el-icon-check" type="primary" @click="permissionData">确定</el-button>
            </div>
        </el-dialog>
        <el-dialog :title="roleUserTitle" fullscreen :visible.sync="dialogRoleUserFormVisible" custom-class="el-dialog-custom-height">
            <div class="filter-container">
                <el-input v-model="listQueryRoleUser.userId" placeholder="用户ID" style="width: 120px;"
                          class="filter-item" @keyup.enter.native="getRoleUser"/>
                <el-input v-model="listQueryRoleUser.userName" placeholder="用户姓名" style="width: 120px;"
                          class="filter-item" @keyup.enter.native="getRoleUser"/>
                <el-dropdown split-button type="primary" @click="getRoleUser" class="filter-item">
                    <i class="el-icon-search el-icon--left"></i>查询
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item icon="el-icon-zoom-out" @click.native="btnRoleUserReset">重置</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
                <el-button-group>
                    <el-button icon="el-icon-plus" type="primary" class="filter-item" @click="btnRoleUserAdd">新增</el-button>
                    <el-button icon="el-icon-delete" class="filter-item" @click="btnRoleUserDelete">批量删除</el-button>
                </el-button-group>
            </div>
            <el-table
                    :data="recordsRoleUser"
                    ref="roleUserTable"
                    @selection-change="selectionChangeRoleUser"
                    border
                    fit
                    highlight-current-row
                    style="width: 100%;"
                    :cell-style="{padding:'3px'}"
            >
                <el-table-column type="selection" align="center">
                </el-table-column>
                <el-table-column label="用户ID" prop="userId" align="center">
                    <template slot-scope="scope">
                        <span>{{ scope.row.userId }}</span>
                    </template>
                </el-table-column>
                <el-table-column label="用户姓名" prop="userName" align="center">
                    <template slot-scope="scope">
                        <span>{{ scope.row.userName }}</span>
                    </template>
                </el-table-column>
                <el-table-column label="所属机构ID" prop="orgId" align="center">
                    <template slot-scope="scope">
                        <span>{{ scope.row.orgId }}</span>
                    </template>
                </el-table-column>
                <el-table-column label="所属机构" prop="orgId" align="center">
                    <template slot-scope="scope">
                        <span>{{ scope.row.orgName }}</span>
                    </template>
                </el-table-column>
            </el-table>
            <pagination v-show="totalRoleUser>0" :total="totalRoleUser" :current.sync="listQueryRoleUser.current"
                        :size.sync="listQueryRoleUser.size"
                        @pagination="getRoleUser"/>
        </el-dialog>
        <select-user ref="selectUser" :visible.sync="selectUserVisible" :appendToBody="true" :multipleSelect="true"
                     @selectUserFinished="selectUserFinished"></select-user>
    </div>
</template>

<script>
    import Pagination from '@/components/Pagination'
    import SelectUser from '@/components/select/SelectUser'
    import {getAction, putAction, postAction, deleteAction} from '@/api/manage'
    import {Message} from 'element-ui'

    export default {
        name: 'SysRole',
        components: {Pagination, SelectUser},
        computed: {
            rolePermissionTitle: {
                get() {
                    return '角色【'+ this.currRoleId +'】授权'
                }
            },
            roleUserTitle: {
                get() {
                    return '角色【'+ this.currRoleId +'】分配用户'
                }
            }
        },
        data() {
            return {
                records: null,
                selectedRecords: [],
                total: 0,
                listQuery: {
                    current: 1,
                    size: 10,
                    roleId: undefined,
                    roleName: undefined
                },
                dialogFormVisible: false,
                dialogStatus: '',
                temp: {
                    roleId: undefined,
                    roleName: '',
                    sortNo: '',
                    remark: '',
                },
                rules: {
                    roleId: [{required: true, message: '该项不能为空', trigger: 'change'}],
                    roleName: [{required: true, message: '该项不能为空', trigger: 'change'}],
                    sortNo: [{required: true, message: '该项不能为空', trigger: 'change'}]
                },
                dialogPermissionFormVisible: false,
                dialogRoleUserFormVisible: false,
                treeData: [],
                defaultProps: {
                    children: 'children',
                    label: 'label',
                    isLeaf: 'isLeaf',
                    data: 'data'
                },
                currRoleId: '',
                recordsRoleUser: null,
                selectedRecordsRoleUser: [],
                totalRoleUser: 0,
                listQueryRoleUser: {
                    current: 1,
                    size: 10,
                    roleId: undefined,
                    userId: undefined,
                    userName: undefined
                },
                selectUserVisible: false
            }
        },
        created() {
            this.list()
        },
        methods: {
            list() {
                getAction('/sys/role/list', this.listQuery).then(res => {
                    const {data} = res
                    this.records = data.records;
                    this.total = data.total
                })
            },
            btnQuery() {
                this.listQuery.current = 1
                this.list()
            },
            btnReset() {
                this.listQuery = {
                    current: 1,
                    size: 10,
                    roleId: undefined,
                    roleName: undefined
                }
                this.list()
            },
            resetTemp() {
                this.temp = {
                    roleId: undefined,
                    roleName: '',
                    sortNo: '',
                    remark: '',
                }
            },
            btnView(row) {
                this.temp = Object.assign({}, row)
                this.dialogStatus = 'view'
                this.dialogFormVisible = true
                this.$nextTick(() => {
                    this.$refs['dataForm'].clearValidate()
                })
            },
            btnCreate() {
                this.resetTemp()
                this.dialogStatus = 'create'
                this.dialogFormVisible = true
                this.$nextTick(() => {
                    this.$refs['dataForm'].clearValidate()
                })
            },
            createData() {
                this.$refs['dataForm'].validate((valid) => {
                    if (valid) {
                        postAction('/sys/role/save', this.temp).then(({msg}) => {
                            this.dialogFormVisible = false
                            Message.success(msg)
                            this.list()
                        })
                    }
                })
            },
            btnUpdate(row) {
                this.temp = Object.assign({}, row) // copy obj
                this.dialogStatus = 'update'
                this.dialogFormVisible = true
                this.$nextTick(() => {
                    this.$refs['dataForm'].clearValidate()
                })
            },
            updateData() {
                this.$refs['dataForm'].validate((valid) => {
                    if (valid) {
                        putAction('/sys/role/update', this.temp).then(({msg}) => {
                            this.dialogFormVisible = false
                            Message.success(msg)
                            this.list()
                        })
                    }
                })
            },
            btnDelete(id) {
                let ids = id ? [id] : this.selectedRecords.map(record => {
                    return record.roleId
                })
                if (ids.length == 0) {
                    Message.error('请选择要删除的记录')
                    return
                }
                deleteAction('/sys/role/delete', {ids: ids.toString()}).then(({msg}) => {
                    Message.success(msg)
                    this.list()
                })
            },
            selectionChange(selectedRecords) {
                this.selectedRecords = selectedRecords
            },
            getTreeData(roleId) {
                getAction('/sys/role/getRolePermissions', {roleId}).then(res => {
                    const {data} = res
                    this.treeData = data.permissionTree
                    const permissions = data.permissions
                    this.$refs.permissionTree.setCheckedKeys(permissions);
                })
            },
            btnPermission(roleId) {
                this.currRoleId = roleId
                this.dialogPermissionFormVisible = true
                this.getTreeData(roleId)
            },
            permissionData() {
                let menuOrFuncIdNodes = this.$refs.permissionTree.getCheckedNodes();
                let menuOrFuncId = []
                let permissionType = []
                menuOrFuncIdNodes.forEach(
                    ({data}) => {
                        menuOrFuncId.push(data.menuOrFuncId)
                        permissionType.push(data.permissionType)
                    }
                )
                let params = {
                    roleId: this.currRoleId,
                    menuOrFuncId: menuOrFuncId.toString(),
                    permissionType: permissionType.toString()
                }
                postAction('/sys/role/saveRolePermissions', params).then(({msg}) => {
                    Message.success(msg)
                    this.dialogPermissionFormVisible = false
                })
            },
            btnRoleUser(roleId) {
                this.currRoleId = roleId
                this.dialogRoleUserFormVisible = true
                this.getRoleUser()
            },
            getRoleUser() {
                this.listQueryRoleUser.roleId = this.currRoleId
                getAction('/sys/role/getRoleUser', this.listQueryRoleUser).then(res => {
                    const {data} = res
                    this.recordsRoleUser = data.records
                    this.totalRoleUser = data.total
                })
            },
            btnRoleUserQuery(){
                this.listQueryRoleUser.current = 1
                this.getRoleUser()
            },
            btnRoleUserReset() {
                this.listQueryRoleUser = {
                    current: 1,
                    size: 10,
                    roleId: undefined,
                    userId: undefined,
                    userName: undefined
                }
                this.getRoleUser()
            },
            selectionChangeRoleUser(selectedRecords) {
                this.selectedRecordsRoleUser = selectedRecords
            },
            btnRoleUserAdd() {
                this.selectUserVisible = true
                if(this.$refs.selectUser.treeData.length == 0){
                    this.$refs.selectUser.getTreeData()
                }
            },
            selectUserFinished(selectData) {
                if (!selectData || selectData.length == 0) {
                    Message.error('请先选择用户')
                    return
                }
                let userId = selectData.map(item => {
                    return item.userId
                })
                postAction('/sys/role/saveRoleUsers', {
                    roleId: this.currRoleId,
                    userId: userId.toString()
                }).then(({msg}) => {
                    Message.success(msg)
                    this.selectUserVisible = false
                    this.btnRoleUserQuery()
                })
            },
            btnRoleUserDelete() {
                let userId = this.selectedRecordsRoleUser.map(record => {
                    return record.userId
                })
                if (userId.length == 0) {
                    Message.error('请选择要删除的记录')
                    return
                }
                deleteAction('/sys/role/deleteRoleUsers', {
                    roleId: this.currRoleId,
                    userIds: userId.toString()
                }).then(({msg}) => {
                    Message.success(msg)
                    this.btnRoleUserQuery();
                })
            }
        }
    }
</script>
