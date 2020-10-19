<template>
    <div class="app-container">
        <div class="filter-container">
            <!--<el-input v-model="listQuery.processInstanceId" placeholder="流程实例ID" style="width: 200px;"
                      class="filter-item" @keyup.enter.native="btnQuery"/>-->
            <el-input v-model="listQuery.taskName" placeholder="任务名称" style="width: 200px;" class="filter-item"
                      @keyup.enter.native="btnQuery"/>
            <el-input v-model="listQuery.processInstanceBusinessKey" placeholder="业务主键Key" style="width: 200px;"
                      class="filter-item" @keyup.enter.native="btnQuery"/>
            <el-date-picker v-model="listQuery.taskDueAfter" value-format="yyyy-MM-dd" placeholder="到期日开始"
                            type="date" style="width: 200px;"
                            class="filter-item"></el-date-picker>
            <el-date-picker v-model="listQuery.taskDueBefore" value-format="yyyy-MM-dd" placeholder="到期日结束"
                            type="date" style="width: 200px;"
                            class="filter-item"></el-date-picker>
            <el-dropdown split-button type="primary" @click="btnQuery" class="filter-item">
                <i class="el-icon-search el-icon--left"></i>查询
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item icon="el-icon-zoom-out" @click.native="btnReset">重置</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </div>
        <el-table
                :data="records"
                border
                fit
                highlight-current-row
                style="width: 100%;"
                :cell-style="{padding:'3px'}"
        >
            <el-table-column label="流程实例名称" prop="processInstanceName" align="center">
                <template slot-scope="scope"><span>{{ scope.row.processInstanceName }}</span></template>
            </el-table-column>
            <el-table-column label="任务名称" prop="name" align="center">
                <template slot-scope="scope"><span>{{ scope.row.name }}</span></template>
            </el-table-column>
            <el-table-column label="开始时间" prop="createTime" align="center" width="165px">
                <template slot-scope="scope"><span>{{ scope.row.createTime }}</span></template>
            </el-table-column>
            <el-table-column label="到期日期" prop="dueDate" align="center">
                <template slot-scope="scope"><span>{{ scope.row.dueDate }}</span></template>
            </el-table-column>
            <el-table-column label="所有人" prop="owner" align="center">
                <template slot-scope="scope"><span>{{ scope.row.owner }}</span></template>
            </el-table-column>
            <el-table-column label="执行人" prop="assignee" align="center">
                <template slot-scope="scope"><span>{{ scope.row.assignee }}</span></template>
            </el-table-column>
            <el-table-column label="操作" align="center">
                <template slot-scope="{row}">
                    <el-dropdown>
                        <span class="el-dropdown-link">操作<i class="el-icon-arrow-down el-icon--right"></i></span>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item icon="el-icon-view" @click.native="btnView(row.processInstanceId)">查看详情
                            </el-dropdown-item>
                            <el-dropdown-item v-if="row.assignee==null||row.assignee==''" icon="el-icon-edit" divided
                                              @click.native="btnClaim(row)">认领并执行
                            </el-dropdown-item>
                            <el-dropdown-item v-if="row.assignee===$store.getters.sysUser.userId && row.endTime==null"
                                              icon="el-icon-edit" divided @click.native="btnUnclaim(row)">取消签收
                            </el-dropdown-item>
                            <el-dropdown-item v-if="row.endTime==null&&row.assignee!=null&&row.assignee!=''"
                                              icon="el-icon-edit" divided @click.native="btnExcuteTask(row)">执行
                            </el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </template>
            </el-table-column>
        </el-table>
        <pagination v-show="total>0" :total="total" :current.sync="listQuery.current" :size.sync="listQuery.size"
                    @pagination="list"/>

        <!-- 执行任务 -->
        <execute-task v-if="dialogExcuteTaskVisible" :visible.sync="dialogExcuteTaskVisible"
                      :executeTaskId="executeTaskId" @executeTaskFinished="list"></execute-task>
        <process-detail v-if="dialogViewVisible" :visible.sync="dialogViewVisible"
                        :processInstanceId="processInstanceId"></process-detail>
    </div>
</template>

<script>
    import Pagination from '@/components/Pagination'
    import {deleteAction, getAction, postAction, putAction} from '@/api/manage'
    import {Message} from 'element-ui'
    import ExecuteTask from '../components/ExecuteTask'
    import ProcessDetail from '../components/ProcessDetail'

    export default {
        name: 'FlowableTaskTodo',
        components: {Pagination, ExecuteTask, ProcessDetail},
        data() {
            return {
                dicts: [],
                records: null,
                selectedRecords: [],
                total: 0,
                listQuery: {
                    current: 1,
                    size: 10,
                    processInstanceId: undefined,
                    taskName: undefined,
                    processInstanceBusinessKey: undefined,
                    taskDueAfter: undefined,
                    taskDueBefore: undefined
                },
                dialogExcuteTaskVisible: false,
                formJson: undefined,
                executeTaskId: '',
                processInstanceId: '',
                dialogViewVisible: false
            }
        },
        created() {
            this.list()
        },
        methods: {
            list() {
                getAction('/flowable/task/listTodo', this.listQuery).then(res => {
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
                    processInstanceId: undefined,
                    taskName: undefined,
                    processInstanceBusinessKey: undefined,
                    taskDueAfter: undefined,
                    taskDueBefore: undefined
                }
                this.list()
            },
            btnClaim(row) {
                putAction('/flowable/task/claim', {taskId: row.id}).then(({msg}) => {
                    this.list()
                    this.btnExcuteTask(row)
                }).catch((response) => {
                    this.list()
                })
            },
            btnUnclaim(row) {
                putAction('/flowable/task/unclaim', {taskId: row.id}).then(({msg}) => {
                    Message.success(msg)
                    this.list()
                })
            },
            btnExcuteTask(row) {
                this.executeTaskId = row.id
                this.dialogExcuteTaskVisible = true
            },
            btnView(processInstanceId) {
                this.processInstanceId = processInstanceId
                this.dialogViewVisible = true
            }
        }
    }
</script>
