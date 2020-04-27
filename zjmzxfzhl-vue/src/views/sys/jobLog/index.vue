<template>
    <div class="app-container">
        <div class="filter-container">
            <el-input v-model="listQuery.jobName" placeholder="任务名称" style="width: 200px;" class="filter-item" @keyup.enter.native="btnQuery"/>
            <el-input v-model="listQuery.jobGroup" placeholder="任务组名" style="width: 200px;" class="filter-item" @keyup.enter.native="btnQuery"/>
            <el-select v-model="listQuery.status" placeholder="是否正常执行" class="filter-item"><el-option v-for="(item, index) in dicts.yesOrNo" :key="index" :label="item.content" :value="item.value"></el-option></el-select>
            <el-dropdown split-button type="primary" @click="btnQuery" class="filter-item">
                <i class="el-icon-search el-icon--left"></i>查询
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item icon="el-icon-zoom-out" @click.native="btnReset">重置</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
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
            <el-table-column label="任务名称" prop="jobName" align="center"><template slot-scope="scope"><span>{{ scope.row.jobName }}</span></template></el-table-column>
            <el-table-column label="任务组名" prop="jobGroup" align="center"><template slot-scope="scope"><span>{{ scope.row.jobGroup }}</span></template></el-table-column>
            <el-table-column label="调用目标字符串" prop="invokeTarget" align="center"><template slot-scope="scope"><span>{{ scope.row.invokeTarget }}</span></template></el-table-column>
            <el-table-column label="是否正常执行" prop="status" align="center"><template slot-scope="scope"><span v-html="formatDictText(dicts.yesOrNo,scope.row.status)"></span></template></el-table-column>
            <el-table-column label="开始时间" prop="startTime" align="center"><template slot-scope="scope"><span>{{ scope.row.startTime }}</span></template></el-table-column>
            <el-table-column label="结束时间" prop="stopTime" align="center"><template slot-scope="scope"><span>{{ scope.row.stopTime }}</span></template></el-table-column>
            <el-table-column label="操作" align="center">
                <template slot-scope="{row}">
                    <el-dropdown>
                        <span class="el-dropdown-link">操作<i class="el-icon-arrow-down el-icon--right"></i></span>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item icon="el-icon-view" @click.native="btnView(row)">查看</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </template>
            </el-table-column>
        </el-table>
        <pagination v-show="total>0" :total="total" :current.sync="listQuery.current" :size.sync="listQuery.size"
                    @pagination="list"/>

        <el-dialog title="定时任务执行日志" :visible.sync="dialogFormVisible">
            <el-form ref="dataForm" :model="temp" :disabled="dialogStatus==='view'" label-position="right" label-width="135px">
                <el-form-item label="任务名称" prop="jobName"><el-input v-model="temp.jobName"/></el-form-item>
                <el-form-item label="任务组名" prop="jobGroup"><el-input v-model="temp.jobGroup"/></el-form-item>
                <el-form-item label="调用目标字符串" prop="invokeTarget"><el-input v-model="temp.invokeTarget"/></el-form-item>
                <el-form-item label="日志信息" prop="jobMessage"><el-input v-model="temp.jobMessage"/></el-form-item>
                <el-form-item label="是否正常执行" prop="status"><el-select v-model="temp.status" placeholder="是否正常执行"><el-option v-for="(item, index) in dicts.yesOrNo" :key="index" :label="item.content" :value="item.value"></el-option></el-select></el-form-item>
                <el-form-item label="异常信息" prop="exceptionInfo"><el-input v-model="temp.exceptionInfo"/></el-form-item>
                <el-form-item label="开始时间" prop="startTime"><el-date-picker v-model="temp.startTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker></el-form-item>
                <el-form-item label="结束时间" prop="stopTime"><el-date-picker v-model="temp.stopTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker></el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button icon="el-icon-close" @click="dialogFormVisible = false">取消</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import Pagination from '@/components/Pagination'
    import {getAction, putAction, postAction, deleteAction} from '@/api/manage'
    import {Message} from 'element-ui'

    export default {
        name: 'SysJobLog',
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
                    status: undefined
                },
                dialogFormVisible: false,
                dialogStatus: '',
                temp: {
                    jobLogId: undefined,
                    jobName: '',
                    jobGroup: '',
                    invokeTarget: '',
                    jobMessage: '',
                    status: '',
                    exceptionInfo: '',
                    startTime: '',
                    stopTime: ''
                }
            }
        },
        beforeCreate(){
            this.getDicts('yesOrNo').then(({data}) => {this.dicts = data})
        },
        created() {
            this.list()
        },
        methods: {
            list() {
                getAction('/sys/jobLog/list', this.listQuery).then(res => {
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
                    status: undefined
                }
                this.list()
            },
            btnView(row) {
                this.temp = Object.assign({}, row)
                this.dialogStatus = 'view'
                this.dialogFormVisible = true
                this.$nextTick(() => {
                    this.$refs['dataForm'].clearValidate()
                })
            },
            selectionChange(selectedRecords) {
                this.selectedRecords = selectedRecords
            }
        }
    }
</script>
