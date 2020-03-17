<template>
    <div class="app-container">
        <div class="filter-container">
            <el-input v-model="listQuery.codeTypeId" placeholder="代码类别ID" style="width: 200px;" class="filter-item" @keyup.enter.native="btnQuery"/>
            <el-input v-model="listQuery.codeTypeName" placeholder="代码类别名称" style="width: 200px;" class="filter-item" @keyup.enter.native="btnQuery"/>
            <el-dropdown split-button type="primary" @click="btnQuery" class="filter-item">
                <i class="el-icon-search el-icon--left"></i>查询
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item icon="el-icon-zoom-out" @click.native="btnReset">重置</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
            <el-button-group>
                <el-button v-permission="'sys:codeType:save'" icon="el-icon-plus" type="primary" @click="btnCreate" class="filter-item">新增</el-button>
                <el-button v-permission="'sys:codeType:delete'" icon="el-icon-delete" @click="btnDelete()" class="filter-item">批量删除</el-button>
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
            <el-table-column label="代码类别ID" prop="codeTypeId" align="center"><template slot-scope="scope"><span>{{ scope.row.codeTypeId }}</span></template></el-table-column>
            <el-table-column label="代码类别名称" prop="codeTypeName" align="center"><template slot-scope="scope"><span>{{ scope.row.codeTypeName }}</span></template></el-table-column>
            <el-table-column label="排序号" prop="sortNo" align="center"><template slot-scope="scope"><span>{{ scope.row.sortNo }}</span></template></el-table-column>
            <el-table-column label="备注" prop="remark" align="center"><template slot-scope="scope"><span>{{ scope.row.remark }}</span></template></el-table-column>
            <el-table-column label="操作" align="center">
                <template slot-scope="{row}">
                    <el-dropdown>
                        <span class="el-dropdown-link">操作<i class="el-icon-arrow-down el-icon--right"></i></span>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item icon="el-icon-view" @click.native="btnView(row)">查看</el-dropdown-item>
                            <el-dropdown-item v-permission="'sys:codeType:update'" icon="el-icon-edit" divided @click.native="btnUpdate(row)">修改</el-dropdown-item>
                            <el-dropdown-item v-permission="'sys:codeType:delete'" icon="el-icon-delete" divided @click.native="btnDelete(row.codeTypeId)">删除</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </template>
            </el-table-column>
        </el-table>
        <pagination v-show="total>0" :total="total" :current.sync="listQuery.current" :size.sync="listQuery.size"
                    @pagination="list"/>

        <el-dialog title="代码类别" :visible.sync="dialogFormVisible">
            <el-form ref="dataForm" :rules="rules" :model="temp" :disabled="dialogStatus==='view'" label-position="right" label-width="110px">
                <el-form-item label="代码类别ID" prop="codeTypeId"><el-input v-model="temp.codeTypeId" :readonly="dialogStatus==='update'"/></el-form-item>
                <el-form-item label="代码类别名称" prop="codeTypeName"><el-input v-model="temp.codeTypeName"/></el-form-item>
                <el-form-item label="排序号" prop="sortNo"><el-input v-model="temp.sortNo"/></el-form-item>
                <el-form-item label="备注" prop="remark"><el-input v-model="temp.remark"/></el-form-item>
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
        name: 'SysCodeType',
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
                    codeTypeId: undefined,
                    codeTypeName: undefined
                },
                dialogFormVisible: false,
                dialogStatus: '',
                temp: {
                    codeTypeId: undefined,
                    codeTypeName: '',
                    sortNo: '',
                    remark: ''
                },
                rules: {
                    codeTypeId: [{required: true, message: '该项不能为空', trigger: 'change'}],
                    codeTypeName: [{required: true, message: '该项不能为空', trigger: 'change'}],
                    sortNo: [{required: true, message: '该项不能为空', trigger: 'change'}]
                }
            }
        },
        beforeCreate(){

        },
        created() {
            this.list()
        },
        methods: {
            list() {
                getAction('/sys/codeType/list', this.listQuery).then(res => {
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
                    codeTypeId: undefined,
                    codeTypeName: undefined
                }
                this.list()
            },
            resetTemp() {
                this.temp = {
                    codeTypeId: undefined,
                    codeTypeName: '',
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
                        postAction('/sys/codeType/save', this.temp).then(({msg}) => {
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
                        putAction('/sys/codeType/update', this.temp).then(({msg}) => {
                            this.dialogFormVisible = false
                            Message.success(msg)
                            this.list()
                        })
                    }
                })
            },
            btnDelete(id) {
                let ids = id ? [id] : this.selectedRecords.map(record => {
                    return record.codeTypeId
                })
                if (ids.length == 0) {
                    Message.error('请选择要删除的记录')
                    return
                }
                deleteAction('/sys/codeType/delete', {ids: ids.toString()}).then(({msg}) => {
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
