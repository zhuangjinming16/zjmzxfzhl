<template>
    <div class="app-container">
        <div class="filter-container">
            <el-input v-model="listQuery.userId" placeholder="用户ID" style="width: 200px;" class="filter-item"
                      @keyup.enter.native="btnQuery"/>
            <el-input v-model="listQuery.userName" placeholder="用户姓名" style="width: 200px;" class="filter-item"
                      @keyup.enter.native="btnQuery"/>
            <el-dropdown v-permission="'sys:user:list'" split-button type="primary" @click="btnQuery"
                         class="filter-item">
                <i class="el-icon-search el-icon--left"></i>查询
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item icon="el-icon-zoom-out" @click.native="btnReset">重置</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
            <el-button-group>
                <el-button v-permission="'sys:user:export'" icon="el-icon-download" type="primary" @click="btnExport" class="filter-item">导出
                </el-button>
                <el-button v-permission="'sys:user:save'" icon="el-icon-plus" type="primary" @click="btnCreate" class="filter-item">新增
                </el-button>
                <el-button v-permission="'sys:user:delete'" icon="el-icon-delete" @click="btnDelete()" class="filter-item">批量删除</el-button>
            </el-button-group>
        </div>
        <el-table
                :data="records"
                ref="multipleTable"
                @selection-change="selectionChange"
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
            <el-table-column label="当前角色" prop="roleId" align="center" :formatter="roleFormatter">
            </el-table-column>
            <el-table-column label="所属机构" prop="orgName" align="center">
                <template slot-scope="scope">
                    <span>{{ scope.row.orgName }}</span>
                </template>
            </el-table-column>
            <el-table-column label="手机号" prop="mobile" align="center">
                <template slot-scope="scope">
                    <span>{{ scope.row.mobile }}</span>
                </template>
            </el-table-column>
            <el-table-column label="用户状态" prop="status" align="center">
                <template slot-scope="scope">
                    <span v-html="formatDictText(dicts.userStatus,scope.row.status)"></span>
                </template>
            </el-table-column>
            <el-table-column label="排序号" prop="sortNo" align="center">
                <template slot-scope="scope">
                    <span>{{ scope.row.sortNo }}</span>
                </template>
            </el-table-column>
            <el-table-column label="操作" align="center">
                <template slot-scope="{row}">
                    <el-dropdown>
                        <span class="el-dropdown-link">操作<i class="el-icon-arrow-down el-icon--right"></i></span>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item icon="el-icon-view" @click.native="btnView(row)">查看</el-dropdown-item>
                            <el-dropdown-item v-permission="'sys:user:update'" icon="el-icon-edit" divided @click.native="btnUpdate(row)">修改</el-dropdown-item>
                            <el-dropdown-item v-permission="'sys:user:delete'" icon="el-icon-delete" divided @click.native="btnDelete(row.userId)">删除</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </template>
            </el-table-column>
        </el-table>
        <pagination v-show="total>0" :total="total" :current.sync="listQuery.current" :size.sync="listQuery.size"
                    @pagination="list"/>

        <el-dialog title="用户" :visible.sync="dialogFormVisible">
            <el-form ref="dataForm" :rules="rules" :model="temp" :disabled="dialogStatus==='view'" label-position="right" label-width="110px">
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="用户ID" prop="userId">
                            <el-input v-model="temp.userId" :readonly="dialogStatus==='update'"/>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="用户姓名" prop="userName">
                            <el-input v-model="temp.userName"/>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="性别" prop="sex">
                            <el-select v-model="temp.sex" placeholder="性别">
                                <el-option v-for="item in dicts.userSex" :key="item.value" :label="item.content" :value="item.value"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="当前角色" prop="roleId">
                            <el-select v-model="temp.roleId" placeholder="当前角色">
                                <el-option v-for="item in roles" :key="item.roleId" :label="item.roleName" :value="item.roleId"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="所属机构ID" prop="orgId" hidden>
                            <el-input v-model="temp.orgId"/>
                        </el-form-item>
                        <el-form-item label="所属机构" prop="orgName">
                            <el-input v-model="temp.orgName">
                                <el-button slot="append" icon="el-icon-search" @click="btnSelectOrg"></el-button>
                            </el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="手机号码" prop="mobile">
                            <el-input v-model="temp.mobile"/>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="身份证号" prop="idCardNo">
                            <el-input v-model="temp.idCardNo"/>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="邮箱地址" prop="email">
                            <el-input v-model="temp.email"/>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="用户状态" prop="status">
                            <el-select v-model="temp.status" placeholder="用户状态">
                                <el-option v-for="item in dicts.userStatus" :key="item.value" :label="item.content" :value="item.value"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="排序号" prop="sortNo">
                            <el-input v-model="temp.sortNo"/>
                        </el-form-item>
                    </el-col>
                </el-row>

                <el-row>
                    <el-col :span="24">
                        <el-form-item label="备注">
                            <el-input v-model="temp.remark" :autosize="{ minRows: 2, maxRows: 4}" type="textarea"
                                      placeholder="请输入备注信息"/>
                        </el-form-item>
                    </el-col>
                </el-row>

            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button icon="el-icon-close" @click="dialogFormVisible = false">
                    取消
                </el-button>
                <el-button v-if="dialogStatus!=='view'" icon="el-icon-check" type="primary" @click="dialogStatus==='create'?createData():updateData()">
                    确定
                </el-button>
            </div>
        </el-dialog>

        <select-org ref="selectOrg" :visible.sync="selectOrgVisible" :appendToBody="true" :multipleSelect="false"
                     @selectOrgFinished="selectOrgFinished"></select-org>
    </div>
</template>

<script>
    import Pagination from '@/components/Pagination' // secondary package based on el-pagination
    import {getAction, putAction, postAction, deleteAction,downloadAction} from '@/api/manage'
    import {Message} from 'element-ui'
    import SelectOrg from '@/components/select/SelectOrg'

    export default {
        name: 'SysUser',
        components: {Pagination,SelectOrg},
        data() {
            return {
                dicts: [],
                roles: [],
                records: null,
                selectedRecords: [],
                total: 0,
                listQuery: {
                    current: 1,
                    size: 10,
                    userId: undefined,
                    userName: undefined
                },
                dialogFormVisible: false,
                dialogStatus: '',
                temp: {
                    userId: undefined,
                    userName: '',
                    sex: '1',
                    roleId: '',
                    orgId: '',
                    orgName: '',
                    mobile: '',
                    idCardNo: '',
                    email: '',
                    status: '1',
                    sortNo: '',
                    remark: ''
                },
                rules: {
                    userId: [{required: true, message: '该项不能为空', trigger: 'change'}],
                    userName: [{required: true, message: '该项不能为空', trigger: 'change'}],
                    sex: [{required: true, message: '该项不能为空', trigger: 'change'}],
                    roleId: [{required: true, message: '该项不能为空', trigger: 'change'}],
                    orgId: [{required: true, message: '该项不能为空', trigger: 'change'}],
                    mobile: [{required: true, message: '该项不能为空', trigger: 'change'}],
                    idCardNo: [{required: true, message: '该项不能为空', trigger: 'change'}],
                    email: [{required: true, message: '该项不能为空', trigger: 'change'}],
                    status: [{required: true, message: '该项不能为空', trigger: 'change'}],
                    sortNo: [{required: true, message: '该项不能为空', trigger: 'change'}]
                },
                selectOrgVisible: false
            }
        },
        beforeCreate(){
            this.getDicts('userSex,userStatus').then(({data}) => {
                this.dicts = data
            })
            getAction("/sys/role/listAll",{}).then(({data}) => {
                this.roles = data
            })
        },
        created() {
            this.list()
        },
        methods: {
            list() {
                getAction('/sys/user/list', this.listQuery).then(res => {
                    const {data} = res
                    this.records = data.records;
                    this.total = data.total
                })
            },
            btnExport() {
                downloadAction('/sys/user/export', 'get', this.listQuery, 'SysUserExport.xlsx')
            },
            btnQuery() {
                this.listQuery.current = 1
                this.list()
            },
            btnReset() {
                this.listQuery = {
                    current: 1,
                    size: 10,
                    userId: undefined,
                    userName: undefined
                }
                this.list()
            },
            resetTemp() {
                this.temp = {
                    userId: undefined,
                    userName: '',
                    sex: '1',
                    roleId: '',
                    orgId: '',
                    orgName: '',
                    mobile: '',
                    idCardNo: '',
                    email: '',
                    status: '1',
                    sortNo: '',
                    remark: ''
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
                        postAction('/sys/user/save', this.temp).then(({msg}) => {
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
                        putAction('/sys/user/update', this.temp).then(({msg}) => {
                            this.dialogFormVisible = false
                            Message.success(msg)
                            this.list()
                        })
                    }
                })
            },
            btnDelete(id) {
                let ids = id ? [id] : this.selectedRecords.map(record => {
                    return record.userId
                })
                if (ids.length == 0) {
                    Message.error('请选择要删除的记录')
                    return
                }
                deleteAction('/sys/user/delete', {ids: ids.toString()}).then(({msg}) => {
                    Message.success(msg)
                    this.list()
                })
            },
            selectionChange(selectedRecords) {
                this.selectedRecords = selectedRecords
            },
            roleFormatter(row,column){
                let result = row.roleId
                for(let i=0;i<this.roles.length;i++){
                    if(this.roles[i].roleId == result){
                        result = this.roles[i].roleName
                        break
                    }
                }
                return result
            },
            btnSelectOrg() {
                this.selectOrgVisible = true
                if(this.$refs.selectOrg.treeData.length == 0){
                    this.$refs.selectOrg.getTreeData()
                }
            },
            selectOrgFinished(selectData) {
                if (!selectData || selectData.length == 0) {
                    Message.error('请先选择机构')
                    return
                }
                this.temp.orgId = selectData.id
                this.temp.orgName = selectData.label
                this.selectOrgVisible = false
            }
        }
    }
</script>
