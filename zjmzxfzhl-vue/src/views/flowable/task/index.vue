<template>
    <div class="app-container">
        <div class="filter-container">
            <el-input v-model="listQuery.processInstanceId" placeholder="流程实例ID" style="width: 200px;"
                      class="filter-item" @keyup.enter.native="btnQuery"/>
            <el-input v-model="listQuery.taskId" placeholder="任务ID" style="width: 200px;" class="filter-item"
                      @keyup.enter.native="btnQuery"/>
            <el-input v-model="listQuery.taskName" placeholder="任务名称" style="width: 200px;" class="filter-item"
                      @keyup.enter.native="btnQuery"/>
            <el-input v-model="listQuery.processInstanceBusinessKey" placeholder="业务主键" style="width: 200px;"
                      class="filter-item" @keyup.enter.native="btnQuery"/>
            <el-input v-model="listQuery.taskDescription" placeholder="任务描述" style="width: 200px;" class="filter-item"
                      @keyup.enter.native="btnQuery"/>
            <el-input v-model="listQuery.taskAssignee" placeholder="执行人" style="width: 200px;" class="filter-item"
                      @keyup.enter.native="btnQuery"/>
            <el-input v-model="listQuery.taskOwner" placeholder="所有人" style="width: 200px;" class="filter-item"
                      @keyup.enter.native="btnQuery"/>
            <el-input v-model="listQuery.taskInvolvedUser" placeholder="干系人" style="width: 200px;" class="filter-item"
                      @keyup.enter.native="btnQuery"/>
            <el-select v-model="listQuery.finished" placeholder="任务已完成" style="width: 200px;" class="filter-item">
                <el-option v-for="(item, index) in dicts.trueOrFalse" :key="index" :label="item.content"
                           :value="item.value"></el-option>
            </el-select>
            <el-select v-model="listQuery.processFinished" placeholder="流程已完成" style="width: 200px;"
                       class="filter-item">
                <el-option v-for="(item, index) in dicts.trueOrFalse" :key="index" :label="item.content"
                           :value="item.value"></el-option>
            </el-select>
            <el-input v-model="listQuery.parentTaskId" placeholder="父任务ID" style="width: 200px;" class="filter-item"
                      @keyup.enter.native="btnQuery"/>
            <el-input v-model="listQuery.parentTaskName" placeholder="父任务名称" style="width: 200px;" class="filter-item"
                      @keyup.enter.native="btnQuery"/>
            <el-date-picker v-model="listQuery.dueDateAfter" value-format="yyyy-MM-dd" placeholder="到期日开始"
                            type="date" style="width: 200px;"
                            class="filter-item"></el-date-picker>
            <el-date-picker v-model="listQuery.dueDateBefore" value-format="yyyy-MM-dd" placeholder="到期日结束"
                            type="date" style="width: 200px;"
                            class="filter-item"></el-date-picker>
            <el-date-picker v-model="listQuery.taskCreatedAfter" value-format="yyyy-MM-dd HH:mm:ss" placeholder="创建时间开始"
                            type="datetime" style="width: 200px;"
                            class="filter-item"></el-date-picker>
            <el-date-picker v-model="listQuery.taskCreatedBefore" value-format="yyyy-MM-dd HH:mm:ss"
                            placeholder="创建时间结束" type="datetime" style="width: 200px;"
                            class="filter-item"></el-date-picker>
            <el-date-picker v-model="listQuery.taskCompletedAfter" value-format="yyyy-MM-dd HH:mm:ss"
                            placeholder="完成时间开始" type="datetime"
                            style="width: 200px;" class="filter-item"></el-date-picker>
            <el-date-picker v-model="listQuery.taskCompletedBefore" value-format="yyyy-MM-dd HH:mm:ss"
                            placeholder="完成时间结束" type="datetime"
                            style="width: 200px;" class="filter-item"></el-date-picker>
            <!--<el-input v-model="listQuery.taskCandidateUser" placeholder="候选人" style="width: 200px;" class="filter-item"
                      @keyup.enter.native="btnQuery"/>
            <el-input v-model="listQuery.taskCandidateGroups" placeholder="候选组" style="width: 200px;"
                      class="filter-item" @keyup.enter.native="btnQuery"/>-->
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
            <el-table-column label="流程实例名称" prop="processInstanceName" align="center">
                <template slot-scope="scope"><span>{{ scope.row.processInstanceName }}</span></template>
            </el-table-column>
            <el-table-column label="任务名称" prop="name" align="center">
                <template slot-scope="scope"><span>{{ scope.row.name }}</span></template>
            </el-table-column>
            <el-table-column label="开始时间" prop="claimTime" align="center" width="165px">
                <template slot-scope="scope"><span>{{ scope.row.createTime }}</span></template>
            </el-table-column>
            <el-table-column label="结束时间" prop="endTime" align="center" width="165px">
                <template slot-scope="scope"><span>{{ scope.row.endTime }}</span></template>
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
                            <el-dropdown-item icon="el-icon-view" @click.native="btnViewTask(row)">查看</el-dropdown-item>
                            <el-dropdown-item icon="el-icon-view" divided @click.native="btnView(row.processInstanceId)">查看详情
                            </el-dropdown-item>
                            <el-dropdown-item v-permission="'flowable:task:execute'" v-if="row.endTime==null&&(row.assignee==null||row.assignee=='')" icon="el-icon-edit" divided
                                              @click.native="btnClaim(row)">认领
                            </el-dropdown-item>
                            <el-dropdown-item v-permission="'flowable:task:execute'" v-if="row.endTime==null&&row.assignee===$store.getters.sysUser.userId"
                                              icon="el-icon-edit" divided @click.native="btnUnclaim(row)">取消认领
                            </el-dropdown-item>
                            <el-dropdown-item v-if="row.endTime==null&&row.assignee!=null&&row.assignee===$store.getters.sysUser.userId"
                                              icon="el-icon-edit" divided @click.native="btnExcuteTask(row)">执行
                            </el-dropdown-item>
                            <!-- TODO 回收任务 -->
                        </el-dropdown-menu>
                    </el-dropdown>
                </template>
            </el-table-column>
        </el-table>
        <pagination v-show="total>0" :total="total" :current.sync="listQuery.current" :size.sync="listQuery.size"
                    @pagination="list"/>
        <!-- 弹窗任务信息 -->
        <task-detail v-if="dialogTaskDetailVisible" :visible.sync="dialogTaskDetailVisible" :dicts="dicts"
                     :taskDetailData="taskDetailData"></task-detail>
        <!-- 执行任务 -->
        <execute-task v-if="dialogExcuteTaskVisible" :visible.sync="dialogExcuteTaskVisible"
                      :executeTaskId="executeTaskId" @executeTaskFinished="list"></execute-task>
        <process-detail v-if="dialogViewVisible" :visible.sync="dialogViewVisible" :processInstanceId="processInstanceId"></process-detail>
    </div>
</template>

<script>
    import Pagination from '@/components/Pagination'
    import {getAction, putAction, postAction, deleteAction} from '@/api/manage'
    import {Message} from 'element-ui'
    import TaskDetail from '../components/TaskDetail'
    import ExecuteTask from '../components/ExecuteTask'
    import ProcessDetail from '../components/ProcessDetail'

    export default {
        name: 'FlowableTask',
        components: {Pagination, TaskDetail, ExecuteTask, ProcessDetail},
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
                    taskId: undefined,
                    taskName: undefined,
                    processInstanceBusinessKey: undefined,
                    taskDescription: undefined,
                    taskAssignee: undefined,
                    taskOwner: undefined,
                    taskInvolvedUser: undefined,
                    finished: undefined,
                    processFinished: undefined,
                    parentTaskId: undefined,
                    parentTaskName: undefined,
                    dueDateBefore: undefined,
                    dueDateAfter: undefined,
                    taskCreatedBefore: undefined,
                    taskCreatedAfter: undefined,
                    taskCompletedBefore: undefined,
                    taskCompletedAfter: undefined,
                    taskCandidateUser: undefined,
                    taskCandidateGroups: undefined
                },
                dialogTaskDetailVisible: false,
                dialogExcuteTaskVisible: false,
                dialogTaskBackNodesVisible: false,
                formJson: undefined,
                taskDetailData: {
                    id: '',
                    name: '',
                    description: '',
                    category: '',
                    owner: '',
                    assignee: '',
                    claimTime: '',
                    dueDate: '',
                    endTime: '',
                    durationInMillis: '',
                    workTimeInMillis: '',
                    priority: '',
                    taskDefinitionKey: '',
                    processInstanceId: '',
                    processInstanceName: '',
                    processDefinitionId: '',
                    processDefinitionName: '',
                    processDefinitionDescription: '',
                    processDefinitionKey: '',
                    processDefinitionCategory: '',
                    processDefinitionVersion: '',
                    processDefinitionDeploymentId: '',
                    formKey: '',
                    renderedTaskForm: '',
                    processInstanceStartUserId: '',
                    initiatorCanCompleteTask: '',
                    memberOfCandidateGroup: '',
                    memberOfCandidateUsers: '',
                    delegationState: '',
                    suspended: '',
                    involvedPeople: '',
                    ownerName: '',
                    assigneeName: '',
                    tenantId: ''
                },
                executeTaskId: '',
                processInstanceId: '',
                dialogViewVisible: false
            }
        },
        beforeCreate() {
            this.getDicts('trueOrFalse').then(({data}) => {
                this.dicts = data
            })
        },
        created() {
            if (this.$route.query && this.$route.query.processInstanceId) {
                this.listQuery.processInstanceId = this.$route.query.processInstanceId
            }
            this.list()
        },
        methods: {
            list() {
                getAction('/flowable/task/list', this.listQuery).then(res => {
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
                    taskId: undefined,
                    taskName: undefined,
                    processInstanceBusinessKey: undefined,
                    taskDescription: undefined,
                    taskAssignee: undefined,
                    taskOwner: undefined,
                    taskInvolvedUser: undefined,
                    finished: undefined,
                    processFinished: undefined,
                    parentTaskId: undefined,
                    parentTaskName: undefined,
                    dueDateBefore: undefined,
                    dueDateAfter: undefined,
                    taskCreatedBefore: undefined,
                    taskCreatedAfter: undefined,
                    taskCompletedBefore: undefined,
                    taskCompletedAfter: undefined,
                    taskCandidateUser: undefined,
                    taskCandidateGroups: undefined
                }
                this.list()
            },

            btnViewTask(row) {
                getAction('/flowable/task/queryById', {taskId: row.id}).then(res => {
                    const {data} = res
                    this.taskDetailData = Object.assign({}, data)
                    this.dialogTaskDetailVisible = true
                })
            },
            btnClaim(row) {
                putAction('/flowable/task/claim', {taskId: row.id}).then(({msg}) => {
                    Message.success(msg)
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
            doExcuteTask() {
                this.$refs.generateForm.getData().then(values => {
                    putAction('/flowable/task/complete', {
                        taskId: this.taskDetailData.id,
                        values: values
                    }).then(({msg}) => {
                        Message.success(msg)
                        this.dialogExcuteTaskVisible = false
                        this.list();
                    })
                }).catch(e => {
                    // data check failed
                })
            },
            btnBackTask(row) {
                this.executeTaskId = row.id
                this.dialogTaskBackNodesVisible = true
            },
            btnView(processInstanceId) {
                this.processInstanceId = processInstanceId
                this.dialogViewVisible = true
            },
            selectionChange(selectedRecords) {
                this.selectedRecords = selectedRecords
            }
        }
    }
</script>
