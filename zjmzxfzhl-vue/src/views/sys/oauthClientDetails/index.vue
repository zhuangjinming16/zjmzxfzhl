<template>
    <div class="app-container">
        <div class="filter-container">
            <el-input v-model="listQuery.clientId" placeholder="应用标识" style="width: 200px;" class="filter-item" @keyup.enter.native="btnQuery"/>
            <el-select v-model="listQuery.autoapprove" placeholder="是否自动授权" class="filter-item"><el-option v-for="(item, index) in dicts.trueOrFalse" :key="index" :label="item.content" :value="item.value"></el-option></el-select>
            <el-select v-model="listQuery.status" placeholder="是否有效" class="filter-item"><el-option v-for="(item, index) in dicts.yesOrNo" :key="index" :label="item.content" :value="item.value"></el-option></el-select>
            <el-dropdown split-button type="primary" @click="btnQuery" class="filter-item">
                <i class="el-icon-search el-icon--left"></i>查询
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item icon="el-icon-zoom-out" @click.native="btnReset">重置</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
            <el-button-group>
                <el-button v-permission="'sys:oauthClientDetails:save'" icon="el-icon-plus" type="primary" @click="btnCreate" class="filter-item">新增</el-button>
                <el-button v-permission="'sys:oauthClientDetails:delete'" icon="el-icon-delete" @click="btnDelete()" class="filter-item">批量删除</el-button>
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
            <el-table-column label="应用标识" prop="clientId" align="center"><template slot-scope="scope"><span>{{ scope.row.clientId }}</span></template></el-table-column>
            <el-table-column label="范围" prop="scope" align="center"><template slot-scope="scope"><span>{{ scope.row.scope }}</span></template></el-table-column>
            <el-table-column label="token有效期" prop="accessTokenValidity" align="center"><template slot-scope="scope"><span>{{ scope.row.accessTokenValidity }}</span></template></el-table-column>
            <el-table-column label="refresh有效期" prop="refreshTokenValidity" align="center"><template slot-scope="scope"><span>{{ scope.row.refreshTokenValidity }}</span></template></el-table-column>
            <el-table-column label="是否自动授权" prop="autoapprove" align="center"><template slot-scope="scope"><span v-html="formatDictText(dicts.trueOrFalse,scope.row.autoapprove)"></span></template></el-table-column>
            <el-table-column label="是否有效" prop="status" align="center"><template slot-scope="scope"><span v-html="formatDictText(dicts.yesOrNo,scope.row.status)"></span></template></el-table-column>
            <el-table-column label="操作" align="center">
                <template slot-scope="{row}">
                    <el-dropdown>
                        <span class="el-dropdown-link">操作<i class="el-icon-arrow-down el-icon--right"></i></span>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item icon="el-icon-view" @click.native="btnView(row)">查看</el-dropdown-item>
                            <el-dropdown-item v-permission="'sys:oauthClientDetails:update'" icon="el-icon-edit" divided @click.native="btnUpdate(row)">修改</el-dropdown-item>
                            <el-dropdown-item v-permission="'sys:oauthClientDetails:delete'" icon="el-icon-delete" divided @click.native="btnDelete(row.clientId)">删除</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </template>
            </el-table-column>
        </el-table>
        <pagination v-show="total>0" :total="total" :current.sync="listQuery.current" :size.sync="listQuery.size"
                    @pagination="list"/>

        <el-dialog title="应用客户端" :visible.sync="dialogFormVisible">
            <el-form ref="dataForm" :rules="rules" :model="temp" :disabled="dialogStatus==='view'" label-position="right" label-width="110px">
                <el-form-item label="应用标识" prop="clientId"><el-input v-model="temp.clientId" :readonly="dialogStatus==='update'"/></el-form-item>
                <el-form-item label="资源限定串" prop="resourceIds"><el-input v-model="temp.resourceIds"/></el-form-item>
                <el-form-item label="应用密钥" prop="clientSecret"><el-input v-model="temp.clientSecret"/></el-form-item>
                <el-form-item label="范围" prop="scope"><el-input v-model="temp.scope"/></el-form-item>
                <el-form-item label="授权方式" prop="authorizedGrantTypes"><el-input v-model="temp.authorizedGrantTypes"/></el-form-item>
                <el-form-item label="回调地址" prop="webServerRedirectUri"><el-input v-model="temp.webServerRedirectUri"/></el-form-item>
                <el-form-item label="权限" prop="authorities"><el-input v-model="temp.authorities"/></el-form-item>
                <el-form-item label="token有效期" prop="accessTokenValidity"><el-input v-model="temp.accessTokenValidity"/></el-form-item>
                <el-form-item label="refresh有效期" prop="refreshTokenValidity"><el-input v-model="temp.refreshTokenValidity"/></el-form-item>
                <el-form-item label="扩展信息" prop="additionalInformation"><el-input v-model="temp.additionalInformation"/></el-form-item>
                <el-form-item label="是否自动授权" prop="autoapprove"><el-select v-model="temp.autoapprove" placeholder="是否自动授权"><el-option v-for="(item, index) in dicts.trueOrFalse" :key="index" :label="item.content" :value="item.value"></el-option></el-select></el-form-item>
                <el-form-item label="是否有效" prop="status"><el-select v-model="temp.status" placeholder="是否有效"><el-option v-for="(item, index) in dicts.yesOrNo" :key="index" :label="item.content" :value="item.value"></el-option></el-select></el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button icon="el-icon-close" @click="dialogFormVisible = false">取消</el-button>
                <el-button v-if="dialogStatus!=='view'" icon="el-icon-check" type="primary" @click="dialogStatus==='create'?createData():updateData()">确定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import Pagination from '@/components/Pagination'
    import {getAction, putAction, postAction, deleteAction} from '@/api/manage'
    import {Message} from 'element-ui'

    export default {
        name: 'SysOauthClientDetails',
        components: {Pagination},
        data() {
            return {
                dicts: [],
                records: null,
                selectedRecords: [],
                total: 0,
                listQuery: {
                    current: 1,
                    size: 10,
                    clientId: undefined,
                    autoapprove: undefined,
                    status: undefined
                },
                dialogFormVisible: false,
                dialogStatus: '',
                temp: {
                    clientId: undefined,
                    resourceIds: '',
                    clientSecret: '',
                    scope: '',
                    authorizedGrantTypes: '',
                    webServerRedirectUri: '',
                    authorities: '',
                    accessTokenValidity: '',
                    refreshTokenValidity: '',
                    additionalInformation: '',
                    autoapprove: '',
                    status: ''
                },
                rules: {
                    clientId: [{required: true, message: '该项不能为空', trigger: 'change'}],
                    clientSecret: [{required: true, message: '该项不能为空', trigger: 'change'}],
                }
            }
        },
        beforeCreate(){
            this.getDicts('trueOrFalse,yesOrNo').then(({data}) => {this.dicts = data})
        },
        created() {
            this.list()
        },
        methods: {
            list() {
                getAction('/sys/oauthClientDetails/list', this.listQuery).then(res => {
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
                    clientId: undefined,
                    autoapprove: undefined,
                    status: undefined
                }
                this.list()
            },
            resetTemp() {
                this.temp = {
                    clientId: undefined,
                    resourceIds: '',
                    clientSecret: '',
                    scope: '',
                    authorizedGrantTypes: '',
                    webServerRedirectUri: '',
                    authorities: '',
                    accessTokenValidity: '',
                    refreshTokenValidity: '',
                    additionalInformation: '',
                    autoapprove: '',
                    status: ''
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
                        postAction('/sys/oauthClientDetails/save', this.temp).then(({msg}) => {
                            this.dialogFormVisible = false
                            Message.success(msg)
                            this.list()
                        })
                    }
                })
            },
            btnUpdate(row) {
                this.temp = Object.assign({}, row)
                this.dialogStatus = 'update'
                this.dialogFormVisible = true
                this.$nextTick(() => {
                    this.$refs['dataForm'].clearValidate()
                })
            },
            updateData() {
                this.$refs['dataForm'].validate((valid) => {
                    if (valid) {
                        putAction('/sys/oauthClientDetails/update', this.temp).then(({msg}) => {
                            this.dialogFormVisible = false
                            Message.success(msg)
                            this.list()
                        })
                    }
                })
            },
            btnDelete(id) {
                let ids = id ? [id] : this.selectedRecords.map(record => {
                    return record.clientId
                })
                if (ids.length == 0) {
                    Message.error('请选择要删除的记录')
                    return
                }
                deleteAction('/sys/oauthClientDetails/delete', {ids: ids.toString()}).then(({msg}) => {
                    Message.success(msg)
                    this.list()
                })
            },
            selectionChange(selectedRecords) {
                this.selectedRecords = selectedRecords
            }
        }
    }
</script>
