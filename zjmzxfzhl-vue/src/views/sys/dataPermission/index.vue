<template>
    <div class="app-container">
        <div class="filter-container">
            <el-input v-model="listQuery.dataPermissionName" placeholder="数据权限名称" style="width: 200px;" class="filter-item" @keyup.enter.native="btnQuery"/>
            <el-input v-model="listQuery.methodId" placeholder="方法Id" style="width: 200px;" class="filter-item" @keyup.enter.native="btnQuery"/>
            <el-select v-model="listQuery.entityType" placeholder="实体类型" style="width: 200px;" class="filter-item"><el-option v-for="(item, index) in dicts.entityType" :key="index" :label="item.content" :value="item.value"></el-option></el-select>
            <el-input v-model="listQuery.entityId" placeholder="实体ID" style="width: 200px;" class="filter-item" @keyup.enter.native="btnQuery"/>
            <el-select v-model="listQuery.sourceStrategy" placeholder="数据源策略" style="width: 200px;" class="filter-item"><el-option v-for="(item, index) in dicts.sourceStrategy" :key="index" :label="item.content" :value="item.value"></el-option></el-select>
            <el-input v-model="listQuery.tableName" placeholder="业务表名" style="width: 200px;" class="filter-item" @keyup.enter.native="btnQuery"/>
            <el-input v-model="listQuery.columnName" placeholder="字段名" style="width: 200px;" class="filter-item" @keyup.enter.native="btnQuery"/>
            <el-dropdown split-button type="primary" @click="btnQuery" class="filter-item">
            	<i class="el-icon-search el-icon--left"></i>查询
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item icon="el-icon-zoom-out" @click.native="btnReset">重置</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
            <el-button-group>
                <el-button v-permission="'sys:dataPermission:save'" icon="el-icon-plus" type="primary" @click="btnCreate" class="filter-item">新增</el-button>
                <el-button v-permission="'sys:dataPermission:delete'" icon="el-icon-delete" @click="btnDelete()" class="filter-item">批量删除</el-button>
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
            <el-table-column label="数据权限名称" prop="dataPermissionName" align="center"><template slot-scope="scope"><span>{{ scope.row.dataPermissionName }}</span></template></el-table-column>
            <el-table-column label="方法Id" prop="methodId" align="center"><template slot-scope="scope">{{ scope.row.methodId }}</template></el-table-column>
            <el-table-column label="实体类型" prop="entityType" align="center"><template slot-scope="scope"><span v-html="formatDictText(dicts.entityType,scope.row.entityType)"></span></template></el-table-column>
            <el-table-column label="实体ID" prop="entityId" align="center"><template slot-scope="scope"><span>{{ scope.row.entityId }}</span></template></el-table-column>
            <el-table-column label="业务表名" prop="tableName" align="center"><template slot-scope="scope"><span>{{ scope.row.tableName }}</span></template></el-table-column>
            <el-table-column label="字段名" prop="columnName" align="center"><template slot-scope="scope"><span>{{ scope.row.columnName }}</span></template></el-table-column>
            <el-table-column label="数据源策略" prop="sourceStrategy" align="center"><template slot-scope="scope"><span v-html="formatDictText(dicts.sourceStrategy,scope.row.sourceStrategy)"></span></template></el-table-column>
            <el-table-column label="操作" align="center">
                <template slot-scope="{row}">
                	<el-dropdown>
                        <span class="el-dropdown-link">操作<i class="el-icon-arrow-down el-icon--right"></i></span>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item icon="el-icon-view" @click.native="btnView(row)">查看</el-dropdown-item>
                            <el-dropdown-item v-permission="'sys:dataPermission:update'" icon="el-icon-edit" divided @click.native="btnUpdate(row)">修改</el-dropdown-item>
                            <el-dropdown-item v-permission="'sys:dataPermission:delete'" icon="el-icon-delete" divided @click.native="btnDelete(row.dataPermissionId)">删除</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </template>
            </el-table-column>
        </el-table>
        <pagination v-show="total>0" :total="total" :current.sync="listQuery.current" :size.sync="listQuery.size"
                    @pagination="list"/>

        <el-dialog title="数据权限" :visible.sync="dialogFormVisible">
            <el-form ref="dataForm" :rules="rules" :model="temp" :disabled="dialogStatus==='view'" label-position="right" label-width="110px">
                <el-form-item v-if="dialogStatus!=='create'" label="数据权限ID" prop="dataPermissionId"><el-input v-model="temp.dataPermissionId" :readonly="dialogStatus==='update'"/></el-form-item>
                <el-form-item label="数据权限名称" prop="dataPermissionName"><el-input v-model="temp.dataPermissionName"/></el-form-item>
                <el-form-item label="方法Id" prop="methodId"><el-input v-model="temp.methodId"/></el-form-item>
                <el-form-item label="实体类型" prop="entityType"><el-select v-model="temp.entityType" placeholder="实体类型"><el-option v-for="(item, index) in dicts.entityType" :key="index" :label="item.content" :value="item.value"></el-option></el-select></el-form-item>
                <el-form-item label="实体ID" prop="entityId"><el-input v-model="temp.entityId"/></el-form-item>
                <el-form-item label="业务表名" prop="tableName"><el-input v-model="temp.tableName"/></el-form-item>
                <el-form-item label="类名" prop="className"><el-input v-model="temp.className"/></el-form-item>
                <el-form-item label="字段名" prop="columnName"><el-input v-model="temp.columnName"/></el-form-item>
                <el-form-item label="数据源策略" prop="sourceStrategy"><el-select v-model="temp.sourceStrategy" placeholder="数据源策略"><el-option v-for="(item, index) in dicts.sourceStrategy" :key="index" :label="item.content" :value="item.value"></el-option></el-select></el-form-item>
                <el-form-item label="查询表达式" prop="operate"><el-input v-model="temp.operate"/></el-form-item>
                <el-form-item label="查询条件" prop="value"><el-input v-model="temp.value"/></el-form-item>
                <el-form-item label="系统数据源类" prop="sourceProvider"><el-input v-model="temp.sourceProvider"/></el-form-item>
                <el-form-item label="系统数据源参数" prop="sourceProviderParams"><el-input v-model="temp.sourceProviderParams"/></el-form-item>
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
        name: 'SysDataPermission',
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
                    dataPermissionName: undefined,
                    methodId: undefined,
                    entityType: undefined,
                    entityId: undefined,
                    tableName: undefined,
                    columnName: undefined,
                    sourceStrategy: undefined
                },
                dialogFormVisible: false,
                dialogStatus: '',
                temp: {
                    dataPermissionId: undefined,
                    dataPermissionName: '',
                    methodId: '',
                    entityType: '',
                    entityId: '',
                    tableName: '',
                    className: '',
                    columnName: '',
                    sourceStrategy: '',
                    operate: '',
                    value: '',
                    sourceProvider: '',
                    sourceProviderParams: ''
                },
                rules: {
                    dataPermissionId: [{required: true, message: '该项不能为空', trigger: 'change'}],
                    dataPermissionName: [{required: true, message: '该项不能为空', trigger: 'change'}]
                }
            }
        },
        beforeCreate(){
            this.getDicts('entityType,sourceStrategy').then(({data}) => {this.dicts = data})
        },
        created() {
            this.list()
        },
        methods: {
            list() {
                getAction('/sys/dataPermission/list', this.listQuery).then(res => {
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
                    dataPermissionName: undefined,
                    methodId: undefined,
                    entityType: undefined,
                    entityId: undefined,
                    tableName: undefined,
                    columnName: undefined,
                    sourceStrategy: undefined
                }
                this.list()
            },
            resetTemp() {
                this.temp = {
                    dataPermissionId: undefined,
                    dataPermissionName: '',
                    methodId: '',
                    entityType: '',
                    entityId: '',
                    tableName: '',
                    className: '',
                    columnName: '',
                    sourceStrategy: '',
                    operate: '',
                    value: '',
                    sourceProvider: '',
                    sourceProviderParams: ''
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
                        postAction('/sys/dataPermission/save', this.temp).then(({msg}) => {
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
                        putAction('/sys/dataPermission/update', this.temp).then(({msg}) => {
                            this.dialogFormVisible = false
                            Message.success(msg)
                            this.list()
                        })
                    }
                })
            },
            btnDelete(id) {
                let ids = id ? [id] : this.selectedRecords.map(record => {
                    return record.dataPermissionId
                })
                if (ids.length == 0) {
                    Message.error('请选择要删除的记录')
                    return
                }
                deleteAction('/sys/dataPermission/delete', {ids: ids.toString()}).then(({msg}) => {
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
