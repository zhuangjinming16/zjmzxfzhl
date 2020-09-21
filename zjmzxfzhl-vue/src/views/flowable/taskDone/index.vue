<template>
    <div class="app-container">
        <div class="filter-container">
            <el-checkbox v-model="listQuery.processUnfinished">流程未办结</el-checkbox>
            <el-checkbox v-model="listQuery.processFinished">流程已办结</el-checkbox>
        </div>
        <div class="filter-container">
            <el-input v-model="listQuery.processInstanceId" placeholder="流程实例ID" style="width: 200px;"
                      class="filter-item" @keyup.enter.native="btnQuery"/>
            <el-input v-model="listQuery.taskName" placeholder="任务名称" style="width: 200px;" class="filter-item"
                      @keyup.enter.native="btnQuery"/>
            <el-input v-model="listQuery.processInstanceBusinessKey" placeholder="业务主键Key" style="width: 200px;"
                      class="filter-item" @keyup.enter.native="btnQuery"/>
            <el-date-picker v-model="listQuery.taskCreatedAfter" value-format="yyyy-MM-dd HH:mm:ss" placeholder="创建时间开始"
                            type="datetime" style="width: 200px;" class="filter-item"></el-date-picker>
            <el-date-picker v-model="listQuery.taskCreatedBefore" value-format="yyyy-MM-dd HH:mm:ss" placeholder="创建时间结束"
                            type="datetime" style="width: 200px;" class="filter-item"></el-date-picker>
            <el-date-picker v-model="listQuery.taskCompletedAfter" value-format="yyyy-MM-dd HH:mm:ss" placeholder="完成时间开始"
                            type="datetime" style="width: 200px;" class="filter-item"></el-date-picker>
            <el-date-picker v-model="listQuery.taskCompletedBefore" value-format="yyyy-MM-dd HH:mm:ss" placeholder="完成时间结束"
                            type="datetime" style="width: 200px;" class="filter-item"></el-date-picker>
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
                style="width: 99%;"
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
            <el-table-column label="完成时间" prop="endTime" align="center" width="165px">
                <template slot-scope="scope"><span>{{ scope.row.endTime }}</span></template>
            </el-table-column>
            <el-table-column label="工作耗时" prop="workTimeInMillis" align="center">
                <template slot-scope="scope"><span>{{ scope.row.workTimeInMillis }}</span></template>
            </el-table-column>
            <el-table-column label="总耗时" prop="durationInMillis" align="center">
                <template slot-scope="scope"><span>{{ scope.row.durationInMillis }}</span></template>
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
                        </el-dropdown-menu>
                    </el-dropdown>
                </template>
            </el-table-column>
        </el-table>
        <pagination v-show="total>0" :total="total" :current.sync="listQuery.current" :size.sync="listQuery.size"
                    @pagination="list"/>

        <process-detail v-if="dialogViewVisible" :visible.sync="dialogViewVisible" :processInstanceId="processInstanceId"></process-detail>
    </div>
</template>

<script>
    import Pagination from '@/components/Pagination'
    import {getAction} from '@/api/manage'
    import ProcessDetail from "../components/ProcessDetail";

    export default {
        name: 'FlowableTaskDone',
        components: {ProcessDetail, Pagination},
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
                    taskCreatedAfter: undefined,
                    taskCreatedBefore: undefined,
                    taskCompletedAfter: undefined,
                    taskCompletedBefore: undefined,
                    processUnfinished: false,
                    processFinished: false
                },
                formJson: undefined,
                processInstanceId: '',
                dialogViewVisible: false
            }
        },
        created() {
            this.list()
        },
        methods: {
            list() {
                getAction('/flowable/task/listDone', this.listQuery).then(res => {
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
                    taskCreatedAfter: undefined,
                    taskCreatedBefore: undefined,
                    taskCompletedAfter: undefined,
                    taskCompletedBefore: undefined,
                    processUnfinished: false,
                    processFinished: false
                }
                this.list()
            },
            btnView(processInstanceId) {
                this.processInstanceId = processInstanceId
                this.dialogViewVisible = true
            }
        }
    }
</script>
