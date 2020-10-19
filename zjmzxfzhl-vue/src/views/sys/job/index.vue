<template>
    <div class="app-container">
        <div class="filter-container">
            <el-input v-model="listQuery.jobName" placeholder="任务名称" style="width: 200px;" class="filter-item"
                      @keyup.enter.native="btnQuery"/>
            <el-input v-model="listQuery.jobGroup" placeholder="任务组名" style="width: 200px;" class="filter-item"
                      @keyup.enter.native="btnQuery"/>
            <el-select v-model="listQuery.misfirePolicy" placeholder="计划执行错误策略" class="filter-item">
                <el-option v-for="(item, index) in dicts.misfirePolicy" :key="index" :label="item.content"
                           :value="item.value"></el-option>
            </el-select>
            <el-select v-model="listQuery.concurrent" placeholder="是否并发执行" class="filter-item">
                <el-option v-for="(item, index) in dicts.yesOrNo" :key="index" :label="item.content"
                           :value="item.value"></el-option>
            </el-select>
            <el-select v-model="listQuery.status" placeholder="是否正常状态" class="filter-item">
                <el-option v-for="(item, index) in dicts.yesOrNo" :key="index" :label="item.content"
                           :value="item.value"></el-option>
            </el-select>
            <el-dropdown split-button type="primary" @click="btnQuery" class="filter-item">
                <i class="el-icon-search el-icon--left"></i>查询
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item icon="el-icon-zoom-out" @click.native="btnReset">重置</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
            <el-button-group>
                <el-button v-permission="'sys:job:save'" icon="el-icon-plus" type="primary" @click="btnCreate"
                           class="filter-item">新增
                </el-button>
                <el-button v-permission="'sys:job:delete'" icon="el-icon-delete" @click="btnDelete()"
                           class="filter-item">批量删除
                </el-button>
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
            <el-table-column label="任务名称" prop="jobName" align="center">
                <template slot-scope="scope"><span>{{ scope.row.jobName }}</span></template>
            </el-table-column>
            <el-table-column label="任务组名" prop="jobGroup" align="center">
                <template slot-scope="scope"><span>{{ scope.row.jobGroup }}</span></template>
            </el-table-column>
            <el-table-column label="计划执行错误策略" prop="misfirePolicy" align="center">
                <template slot-scope="scope"><span
                        v-html="formatDictText(dicts.misfirePolicy,scope.row.misfirePolicy)"></span></template>
            </el-table-column>
            <el-table-column label="是否并发执行" prop="concurrent" align="center">
                <template slot-scope="scope"><span v-html="formatDictText(dicts.yesOrNo,scope.row.concurrent)"></span>
                </template>
            </el-table-column>
            <el-table-column label="是否正常状态" prop="status" align="center">
                <template slot-scope="scope"><span v-html="formatDictText(dicts.yesOrNo,scope.row.status)"></span>
                </template>
            </el-table-column>
            <el-table-column label="操作" align="center">
                <template slot-scope="{row}">
                    <el-dropdown>
                        <span class="el-dropdown-link">操作<i class="el-icon-arrow-down el-icon--right"></i></span>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item icon="el-icon-view" @click.native="btnView(row)">查看</el-dropdown-item>
                            <el-dropdown-item v-permission="'sys:job:update'" icon="el-icon-edit" divided
                                              @click.native="btnUpdate(row)">修改
                            </el-dropdown-item>
                            <el-dropdown-item v-permission="'sys:job:delete'" icon="el-icon-delete" divided
                                              @click.native="btnDelete(row.jobId)">删除
                            </el-dropdown-item>
                            <el-dropdown-item v-permission="'sys:job:changeStatus'" icon="el-icon-delete" divided
                                              @click.native="btnChangeStatus(row.jobId)">{{row.status=='0'?'激活':'挂起' }}
                            </el-dropdown-item>
                            <el-dropdown-item v-permission="'sys:job:run'" icon="el-icon-delete" divided
                                              @click.native="btnRun(row.jobId)">立即执行
                            </el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </template>
            </el-table-column>
        </el-table>
        <pagination v-show="total>0" :total="total" :current.sync="listQuery.current" :size.sync="listQuery.size"
                    @pagination="list"/>

        <el-dialog title="定时任务" :visible.sync="dialogFormVisible">
            <el-form ref="dataForm" :rules="rules" :model="temp" :disabled="dialogStatus==='view'"
                     label-position="right" label-width="135px">
                <!--<el-form-item label="任务ID" prop="jobId"><el-input v-model="temp.jobId" :readonly="dialogStatus==='update'"/></el-form-item>-->
                <el-form-item label="任务名称" prop="jobName">
                    <el-input v-model="temp.jobName"/>
                </el-form-item>
                <el-form-item label="任务组名" prop="jobGroup">
                    <el-input v-model="temp.jobGroup"/>
                </el-form-item>
                <el-form-item label="调用目标字符串" prop="invokeTarget">
                    <el-input v-model="temp.invokeTarget"/>
                </el-form-item>
                <el-form-item label="cron执行表达式" prop="cronExpression">
                    <el-input v-model="temp.cronExpression"/>
                </el-form-item>
                <el-form-item label="计划执行错误策略" prop="misfirePolicy">
                    <el-select v-model="temp.misfirePolicy" placeholder="计划执行错误策略">
                        <el-option v-for="(item, index) in dicts.misfirePolicy" :key="index" :label="item.content"
                                   :value="item.value"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="是否并发执行" prop="concurrent">
                    <el-select v-model="temp.concurrent" placeholder="是否并发执行">
                        <el-option v-for="(item, index) in dicts.yesOrNo" :key="index" :label="item.content"
                                   :value="item.value"></el-option>
                    </el-select>
                </el-form-item>
                <!--<el-form-item label="是否正常状态" prop="status"><el-select v-model="temp.status" placeholder="是否正常状态"><el-option v-for="(item, index) in dicts.yesOrNo" :key="index" :label="item.content" :value="item.value"></el-option></el-select></el-form-item>-->
                <el-form-item label="备注" prop="remark">
                    <el-input v-model="temp.remark"/>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button icon="el-icon-close" @click="dialogFormVisible = false">取消</el-button>
                <el-button v-if="dialogStatus!=='view'" icon="el-icon-check" type="primary"
                           @click="dialogStatus==='create'?createData():updateData()">确定
                </el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import Pagination from '@/components/Pagination'
    import {deleteAction, getAction, postAction, putAction} from '@/api/manage'
    import {Message} from 'element-ui'

    export default {
        name: 'SysJob',
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
                    jobName: undefined,
                    jobGroup: undefined,
                    misfirePolicy: undefined,
                    concurrent: undefined,
                    status: undefined
                },
                dialogFormVisible: false,
                dialogStatus: '',
                temp: {
                    jobId: undefined,
                    jobName: '',
                    jobGroup: '',
                    invokeTarget: '',
                    cronExpression: '',
                    misfirePolicy: '',
                    concurrent: '',
                    status: '',
                    remark: ''
                },
                rules: {
                    jobId: [{required: true, message: '该项不能为空', trigger: 'change'}],
                    jobName: [{required: true, message: '该项不能为空', trigger: 'change'}],
                    jobGroup: [{required: true, message: '该项不能为空', trigger: 'change'}],
                    invokeTarget: [{required: true, message: '该项不能为空', trigger: 'change'}],
                    cronExpression: [{required: true, message: '该项不能为空', trigger: 'change'}],
                    misfirePolicy: [{required: true, message: '该项不能为空', trigger: 'change'}],
                    concurrent: [{required: true, message: '该项不能为空', trigger: 'change'}]
                }
            }
        },
        beforeCreate() {
            this.getDicts('misfirePolicy,yesOrNo').then(({data}) => {
                this.dicts = data
            })
        },
        created() {
            this.list()
        },
        methods: {
            list() {
                getAction('/sys/job/list', this.listQuery).then(res => {
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
                    jobName: undefined,
                    jobGroup: undefined,
                    misfirePolicy: undefined,
                    concurrent: undefined,
                    status: undefined
                }
                this.list()
            },
            resetTemp() {
                this.temp = {
                    jobId: undefined,
                    jobName: '',
                    jobGroup: '',
                    invokeTarget: '',
                    cronExpression: '',
                    misfirePolicy: '',
                    concurrent: '',
                    status: '',
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
                        postAction('/sys/job/save', this.temp).then(({msg}) => {
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
                        putAction('/sys/job/update', this.temp).then(({msg}) => {
                            this.dialogFormVisible = false
                            Message.success(msg)
                            this.list()
                        })
                    }
                })
            },
            btnDelete(id) {
                let ids = id ? [id] : this.selectedRecords.map(record => {
                    return record.jobId
                })
                if (ids.length == 0) {
                    Message.error('请选择要删除的记录')
                    return
                }
                deleteAction('/sys/job/delete', {ids: ids.toString()}).then(({msg}) => {
                    Message.success(msg)
                    this.list()
                })
            },
            selectionChange(selectedRecords) {
                this.selectedRecords = selectedRecords
            },
            btnChangeStatus(jobId) {
                putAction('/sys/job/changeStatus', {jobId}).then(({msg}) => {
                    Message.success(msg)
                    this.list()
                })
            },
            btnRun(jobId) {
                putAction('/sys/job/run', {jobId}).then(({msg}) => {
                    Message.success(msg)
                    this.list()
                })
            }
        }
    }
</script>
