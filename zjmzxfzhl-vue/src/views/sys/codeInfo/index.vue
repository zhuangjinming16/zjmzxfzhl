<template>
    <div class="app-container">
        <el-row :gutter="5">
            <el-col :span="10">
                <div class="filter-container">
                    <el-input v-model="listQuery.codeTypeId" placeholder="代码类别ID" style="width: 120px;"
                              class="filter-item"
                              @keyup.enter.native="btnQuery"/>
                    <el-input v-model="listQuery.codeTypeName" placeholder="代码类别名称" style="width: 120px;"
                              class="filter-item"
                              @keyup.enter.native="btnQuery"/>
                    <el-dropdown split-button type="primary" @click="btnQuery" class="filter-item">
                        <i class="el-icon-search el-icon--left"></i>查询
                        <el-dropdown-menu slot="dropdown" trigger="click">
                            <el-dropdown-item icon="el-icon-zoom-out" @click.native="btnReset">重置</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </div>
                <el-table
                        :data="records"
                        ref="codeTypeTable"
                        @row-click="codeTypeSelect"
                        border
                        fit
                        highlight-current-row
                        style="width: 100%;"
                        :cell-style="{padding:'3px'}"
                >
                    <el-table-column label="代码类别ID" prop="codeTypeId" align="center">
                        <template slot-scope="scope">
                            <span>{{ scope.row.codeTypeId }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="代码类别名称" prop="codeTypeName" align="center">
                        <template slot-scope="scope">
                            <span>{{ scope.row.codeTypeName }}</span>
                        </template>
                    </el-table-column>
                </el-table>
                <pagination v-show="total>0" :total="total" :current.sync="listQuery.current"
                            :size.sync="listQuery.size"
                            @pagination="list"/>
            </el-col>
            <el-col :span="14">
                <div class="filter-container">
                    <el-input v-model="listCodeInfoQuery.value" placeholder="下拉框值" style="width: 120px;"
                              class="filter-item" @keyup.enter.native="btnCodeInfoQuery"/>
                    <el-input v-model="listCodeInfoQuery.content" placeholder="下拉框内容" style="width: 120px;"
                              class="filter-item" @keyup.enter.native="btnCodeInfoQuery"/>
                    <el-dropdown split-button type="primary" @click="btnCodeInfoQuery" class="filter-item">
                        <i class="el-icon-search el-icon--left"></i>查询
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item icon="el-icon-zoom-out" @click.native="btnCodeInfoReset">重置</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                    <el-button-group>
                        <el-button v-permission="'sys:codeInfo:save'" icon="el-icon-plus" type="primary" @click="btnCodeInfoCreate" class="filter-item">新增</el-button>
                        <el-button v-permission="'sys:codeInfo:delete'" icon="el-icon-delete" @click="btnCodeInfoDelete()" class="filter-item">批量删除</el-button>
                    </el-button-group>
                </div>
                <el-table
                        :data="recordsCodeInfo"
                        ref="codeInfoTable"
                        @selection-change="selectionChange"
                        border
                        fit
                        highlight-current-row
                        style="width: 100%;"
                        :cell-style="{padding:'3px'}"
                >
                    <el-table-column type="selection" align="center">
                    </el-table-column>
                    <el-table-column label="代码信息ID" prop="codeInfoId" align="center">
                        <template slot-scope="scope">
                            <span>{{ scope.row.codeInfoId }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="下拉框值" prop="value" align="center">
                        <template slot-scope="scope">
                            <span>{{ scope.row.value }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="下拉框内容" prop="content" align="center">
                        <template slot-scope="scope">
                            <span>{{ scope.row.content }}</span>
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
                                    <el-dropdown-item icon="el-icon-view" @click.native="btnCodeInfoView(row)">查看</el-dropdown-item>
                                    <el-dropdown-item v-permission="'sys:codeInfo:update'" icon="el-icon-edit" divided @click.native="btnCodeInfoUpdate(row)">修改</el-dropdown-item>
                                    <el-dropdown-item v-permission="'sys:codeInfo:delete'" icon="el-icon-delete" divided @click.native="btnCodeInfoDelete(row.codeInfoId)">删除</el-dropdown-item>
                                </el-dropdown-menu>
                            </el-dropdown>
                        </template>
                    </el-table-column>
                </el-table>
                <pagination v-show="totalCodeInfo>0" :total="totalCodeInfo" :current.sync="listCodeInfoQuery.current"
                            :size.sync="listCodeInfoQuery.size" @pagination="listCodeInfo"/>
            </el-col>
        </el-row>
        <el-dialog title="代码信息" :visible.sync="dialogFormVisible">
            <el-form ref="dataForm" :rules="rules" :model="temp" :disabled="dialogStatus==='view'" label-position="right" label-width="110px">
                <el-form-item label="代码信息ID" prop="codeInfoId">
                    <el-input v-model="temp.codeInfoId" :readonly="dialogStatus==='update'"/>
                </el-form-item>
                <el-form-item label="下拉框值" prop="value">
                    <el-input v-model="temp.value"/>
                </el-form-item>
                <el-form-item label="下拉框内容" prop="content">
                    <el-input v-model="temp.content"/>
                </el-form-item>
                <el-form-item label="上级联动值" prop="parentValue">
                    <el-input v-model="temp.parentValue"/>
                </el-form-item>
                <el-form-item label="排序号" prop="sortNo">
                    <el-input v-model="temp.sortNo"/>
                </el-form-item>
                <el-form-item label="是否显示" prop="isOk">
                    <el-select v-model="temp.isOk" placeholder="是否显示">
                        <el-option v-for="item in dicts.yesOrNo" :key="item.value" :label="item.content" :value="item.value"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="备注">
                    <el-input v-model="temp.remark" :autosize="{ minRows: 2, maxRows: 4}" type="textarea"
                              placeholder="请输入备注信息"/>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button icon="el-icon-close" @click="dialogFormVisible = false">
                    取消
                </el-button>
                <el-button v-if="dialogStatus!=='view'" icon="el-icon-check" type="primary" @click="dialogStatus==='create'?createCodeInfoData():updateCodeInfoData()">
                    确定
                </el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import Pagination from '@/components/Pagination' // secondary package based on el-pagination
    import {getAction, putAction, postAction, deleteAction} from '@/api/manage'
    import {Message} from 'element-ui'

    export default {
        name: 'SysCodeInfo',
        components: {Pagination},
        data() {
            return {
                dicts : [],
                records: null,
                total: 0,
                listQuery: {
                    current: 1,
                    size: 10,
                    codeTypeId: undefined,
                    codeTypeName: undefined
                },
                selectedRecords: [],
                recordsCodeInfo: null,
                totalCodeInfo: 0,
                listCodeInfoLoading: false,
                listCodeInfoQuery: {
                    current: 1,
                    size: 10,
                    codeTypeId: undefined,
                    value: undefined,
                    content: undefined
                },
                currCodeTypeId: '',
                dialogFormVisible: false,
                dialogStatus: '',
                temp: {
                    codeInfoId: undefined,
                    codeTypeId: this.currCodeTypeId,
                    value: '',
                    content: '',
                    parentValue: '',
                    sortNo: '',
                    isOk: '1',
                    remark: ''
                },
                rules: {
                    codeInfoId: [{required: true, message: '该项不能为空', trigger: 'change'}],
                    value: [{required: true, message: '该项不能为空', trigger: 'change'}],
                    content: [{required: true, message: '该项不能为空', trigger: 'change'}],
                    isOk: [{required: true, message: '该项不能为空', trigger: 'change'}],
                    sortNo: [{required: true, message: '该项不能为空', trigger: 'change'}]
                }
            }
        },
        beforeCreate(){
            this.getDicts('yesOrNo').then(({data}) => { this.dicts = data})
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
            codeTypeSelect(row, event, column) {
                this.currCodeTypeId = row.codeTypeId
                this.listCodeInfo();
            },
            listCodeInfo() {
                if(!this.currCodeTypeId){
                    Message.error('请先选择代码类别')
                    return
                }
                this.listCodeInfoLoading = true
                this.listCodeInfoQuery.codeTypeId = this.currCodeTypeId
                getAction('/sys/codeInfo/list', this.listCodeInfoQuery).then(res => {
                    const {data} = res
                    this.recordsCodeInfo = data.records;
                    this.totalCodeInfo = data.total
                    this.listCodeInfoLoading = false
                })
            },
            btnCodeInfoQuery() {
                this.listCodeInfoQuery.current = 1
                this.listCodeInfo()
            },
            btnCodeInfoReset() {
                this.listCodeInfoQuery = {
                    current: 1,
                    size: 10,
                    codeInfoId: undefined,
                    content: undefined
                }
                this.listCodeInfo()
            },
            resetTemp() {
                this.temp = {
                    codeInfoId: undefined,
                    codeTypeId: this.currCodeTypeId,
                    value: '',
                    content: '',
                    parentValue: '',
                    sortNo: '',
                    isOk: '1',
                    remark: ''
                }
            },
            btnCodeInfoView(row) {
                this.temp = Object.assign({}, row)
                this.dialogStatus = 'view'
                this.dialogFormVisible = true
                this.$nextTick(() => {
                    this.$refs['dataForm'].clearValidate()
                })
            },
            btnCodeInfoCreate() {
                if(!this.currCodeTypeId){
                    Message.error('请先选择代码类别')
                    return
                }
                this.resetTemp()
                this.dialogStatus = 'create'
                this.dialogFormVisible = true
                this.$nextTick(() => {
                    this.$refs['dataForm'].clearValidate()
                })
            },
            createCodeInfoData() {
                this.$refs['dataForm'].validate((valid) => {
                    if (valid) {
                        postAction('/sys/codeInfo/save', this.temp).then(({msg}) => {
                            this.dialogFormVisible = false
                            Message.success(msg)
                            this.listCodeInfo()
                        })
                    }
                })
            },
            btnCodeInfoUpdate(row) {
                this.temp = Object.assign({}, row) // copy obj
                this.dialogStatus = 'update'
                this.dialogFormVisible = true
                this.$nextTick(() => {
                    this.$refs['dataForm'].clearValidate()
                })
            },
            updateCodeInfoData() {
                this.$refs['dataForm'].validate((valid) => {
                    if (valid) {
                        putAction('/sys/codeInfo/update', this.temp).then(({msg}) => {
                            this.dialogFormVisible = false
                            Message.success(msg)
                            this.listCodeInfo()
                        })
                    }
                })
            },
            btnCodeInfoDelete(id) {
                let ids = id ? [id] : this.selectedRecords.map(record => {
                    return record.codeInfoId
                })
                if (ids.length == 0) {
                    Message.error('请选择要删除的记录')
                    return
                }
                deleteAction('/sys/codeInfo/delete', {ids: ids.toString()}).then(({msg}) => {
                    Message.success(msg)
                    this.listCodeInfo();
                })
            },
            selectionChange(selectedRecords) {
                this.selectedRecords = selectedRecords
            }
        }
    }
</script>
