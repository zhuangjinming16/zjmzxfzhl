<template>
    <div class="app-container">
        <div class="filter-container">
            <el-select v-model="listQuery.logType" placeholder="日志类型" class="filter-item"><el-option v-for="(item, index) in dicts.logType" :key="index" :label="item.content" :value="item.value"></el-option></el-select>
            <el-input v-model="listQuery.userId" placeholder="操作用户" style="width: 200px;" class="filter-item" @keyup.enter.native="btnQuery"/>
            <el-input v-model="listQuery.clientId" placeholder="客户端ID" style="width: 200px;" class="filter-item" @keyup.enter.native="btnQuery"/>
            <el-input v-model="listQuery.ip" placeholder="IP地址" style="width: 200px;" class="filter-item" @keyup.enter.native="btnQuery"/>
            <el-dropdown split-button type="primary" @click="btnQuery" class="filter-item">
                <i class="el-icon-search el-icon--left"></i>查询
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item icon="el-icon-zoom-out" @click.native="btnReset">重置</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
            <el-button-group>
                <el-button v-permission="'sys:log:save'" icon="el-icon-plus" type="primary" @click="btnCreate" class="filter-item">新增</el-button>
                <el-button v-permission="'sys:log:delete'" icon="el-icon-delete" @click="btnDelete()" class="filter-item">批量删除</el-button>
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
            <el-table-column label="日志类型" prop="logType" align="center"><template slot-scope="scope"><span v-html="formatDictText(dicts.logType,scope.row.logType)"></span></template></el-table-column>            <el-table-column label="日志内容" prop="logContent" align="center"><template slot-scope="scope"><span>{{ scope.row.logContent }}</span></template></el-table-column>
            <el-table-column label="操作用户" prop="userId" align="center"><template slot-scope="scope"><span>{{ scope.row.userId }}</span></template></el-table-column>
            <el-table-column label="客户端ID" prop="clientId" align="center"><template slot-scope="scope"><span>{{ scope.row.clientId }}</span></template></el-table-column>
            <el-table-column label="耗时" prop="costTime" align="center"><template slot-scope="scope"><span>{{ scope.row.costTime }}</span></template></el-table-column>
            <el-table-column label="操作" align="center">
                <template slot-scope="{row}">
                    <el-dropdown>
                        <span class="el-dropdown-link">操作<i class="el-icon-arrow-down el-icon--right"></i></span>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item icon="el-icon-view" @click.native="btnView(row)">查看</el-dropdown-item>
                            <el-dropdown-item v-permission="'sys:log:update'" icon="el-icon-edit" divided @click.native="btnUpdate(row)">修改</el-dropdown-item>
                            <el-dropdown-item v-permission="'sys:log:delete'" icon="el-icon-delete" divided @click.native="btnDelete(row.logId)">删除</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </template>
            </el-table-column>
        </el-table>
        <pagination v-show="total>0" :total="total" :current.sync="listQuery.current" :size.sync="listQuery.size"
                    @pagination="list"/>

        <el-dialog title="系统日志" :visible.sync="dialogFormVisible">
            <el-form ref="dataForm" :rules="rules" :model="temp" :disabled="dialogStatus==='view'" label-position="right" label-width="110px">
                <el-form-item label="UUID" prop="logId"><el-input v-model="temp.logId" :readonly="dialogStatus==='update'"/></el-form-item>
                <el-form-item label="日志类型" prop="logType"><el-select v-model="temp.logType" placeholder="日志类型"><el-option v-for="(item, index) in dicts.logType" :key="index" :label="item.content" :value="item.value"></el-option></el-select></el-form-item>
                <el-form-item label="日志内容" prop="logContent"><el-input v-model="temp.logContent"/></el-form-item>
                <el-form-item label="操作类型" prop="operateType"><el-input v-model="temp.operateType"/></el-form-item>
                <el-form-item label="操作用户" prop="userId"><el-input v-model="temp.userId"/></el-form-item>
                <el-form-item label="IP地址" prop="ip"><el-input v-model="temp.ip"/></el-form-item>
                <el-form-item label="请求方法" prop="method"><el-input v-model="temp.method"/></el-form-item>
                <el-form-item label="浏览器" prop="userAgent"><el-input v-model="temp.userAgent"/></el-form-item>
                <el-form-item label="客户端ID" prop="clientId"><el-input v-model="temp.clientId"/></el-form-item>
                <el-form-item label="请求路径" prop="requestUrl"><el-input v-model="temp.requestUrl"/></el-form-item>
                <el-form-item label="请求参数" prop="requestParam"><el-input v-model="temp.requestParam"/></el-form-item>
                <el-form-item label="请求类型" prop="requestType"><el-input v-model="temp.requestType"/></el-form-item>
                <el-form-item label="操作结果" prop="operateResult"><el-input v-model="temp.operateResult"/></el-form-item>
                <el-form-item label="耗时" prop="costTime"><el-input v-model="temp.costTime"/></el-form-item>
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
        name: 'SysLog',
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
                    logType: undefined,
                    userId: undefined,
                    clientId: undefined,
                    ip: undefined
                },
                dialogFormVisible: false,
                dialogStatus: '',
                temp: {
                    logId: undefined,
                    logType: '',
                    logContent: '',
                    operateType: '',
                    userId: '',
                    ip: '',
                    method: '',
                    userAgent: '',
                    clientId: '',
                    requestUrl: '',
                    requestParam: '',
                    requestType: '',
                    operateResult: '',
                    costTime: ''
                },
                rules: {
                    logId: [{required: true, message: '该项不能为空', trigger: 'change'}]
                }
            }
        },
        beforeCreate(){
            this.getDicts('logType').then(({data}) => {this.dicts = data})
        },
        created() {
            this.list()
        },
        methods: {
            list() {
                getAction('/sys/log/list', this.listQuery).then(res => {
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
                    logType: undefined,
                    userId: undefined,
                    clientId: undefined,
                    ip: undefined
                }
                this.list()
            },
            resetTemp() {
                this.temp = {
                    logId: undefined,
                    logType: '',
                    logContent: '',
                    operateType: '',
                    userId: '',
                    ip: '',
                    method: '',
                    userAgent: '',
                    clientId: '',
                    requestUrl: '',
                    requestParam: '',
                    requestType: '',
                    operateResult: '',
                    costTime: ''
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
                        postAction('/sys/log/save', this.temp).then(({msg}) => {
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
                        putAction('/sys/log/update', this.temp).then(({msg}) => {
                            this.dialogFormVisible = false
                            Message.success(msg)
                            this.list()
                        })
                    }
                })
            },
            btnDelete(id) {
                let ids = id ? [id] : this.selectedRecords.map(record => {
                    return record.logId
                })
                if (ids.length == 0) {
                    Message.error('请选择要删除的记录')
                    return
                }
                deleteAction('/sys/log/delete', {ids: ids.toString()}).then(({msg}) => {
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
