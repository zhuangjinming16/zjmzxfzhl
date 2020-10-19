<template>
    <div class="app-container">
        <div class="filter-container">
            <div class="filter-container">
                <el-checkbox v-model="listQuery.startedByMe">我发起的</el-checkbox>
                <el-checkbox v-model="listQuery.ccToMe">抄送我</el-checkbox>
                <el-checkbox v-model="listQuery.unfinished">未办结</el-checkbox>
                <el-checkbox v-model="listQuery.finished">已办结</el-checkbox>
            </div>
            <div>
                <el-input v-model="listQuery.processDefinitionCategory" placeholder="流程分类"
                          style="width: 200px;"
                          class="filter-item"
                          @keyup.enter.native="btnMyInvolvedQuery"/>
                <el-input v-model="listQuery.processDefinitionKey" placeholder="流程Key"
                          style="width: 200px;"
                          class="filter-item"
                          @keyup.enter.native="btnMyInvolvedQuery"/>
                <el-input v-model="listQuery.businessKey" placeholder="业务主键KEY" style="width: 200px;"
                          class="filter-item"
                          @keyup.enter.native="btnMyInvolvedQuery"/>
                <el-input v-model="listQuery.startedBy" placeholder="启动人" style="width: 200px;"
                          class="filter-item"
                          @keyup.enter.native="btnMyInvolvedQuery"/>
                <el-dropdown split-button type="primary" @click="btnMyInvolvedQuery" class="filter-item">
                    <i class="el-icon-search el-icon&#45;&#45;left"></i>查询
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item icon="el-icon-zoom-out" @click.native="btnMyInvolvedReset">重置
                        </el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
            </div>
        </div>
        <el-table
                :data="myInvolvedRecords"
                border
                fit
                highlight-current-row
                style="width: 100%;"
                :cell-style="{padding:'3px'}"
        >
            <el-table-column label="流程实例名称" prop="name" align="center">
                <template slot-scope="scope"><span>{{ scope.row.name }}</span></template>
            </el-table-column>
            <el-table-column label="业务主键Key" prop="businessKey" align="center">
                <template slot-scope="scope"><span>{{ scope.row.businessKey }}</span></template>
            </el-table-column>
            <el-table-column label="启动人" prop="startUserId" align="center">
                <template slot-scope="scope"><span>{{ scope.row.startUserId }}</span></template>
            </el-table-column>
            <el-table-column label="开始时间" prop="startTime" align="center" width="165px">
                <template slot-scope="scope"><span>{{ scope.row.startTime }}</span></template>
            </el-table-column>
            <el-table-column label="结束时间" prop="endTime" align="center" width="165px">
                <template slot-scope="scope"><span>{{ scope.row.endTime }}</span></template>
            </el-table-column>
            <el-table-column label="操作" align="center">
                <template slot-scope="{row}">
                    <el-dropdown>
                        <span class="el-dropdown-link">操作<i class="el-icon-arrow-down el-icon--right"></i></span>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item icon="el-icon-view" @click.native="btnView(row.id)">查看详情
                            </el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </template>
            </el-table-column>
        </el-table>
        <pagination v-show="myInvolvedTotal>0" :total="myInvolvedTotal" :current.sync="listQuery.current"
                    :size.sync="listQuery.size" @pagination="list"/>

        <process-detail v-if="dialogViewVisible" :visible.sync="dialogViewVisible"
                        :processInstanceId="processInstanceId"></process-detail>
    </div>
</template>

<script>
    import Pagination from '@/components/Pagination'
    import {deleteAction, getAction, postAction, putAction} from '@/api/manage'
    import {getToken} from '@/utils/auth'
    import ProcessDetail from "../components/ProcessDetail";

    export default {
        name: 'FlowableMyProcessQuery',
        components: {ProcessDetail, Pagination},
        data() {
            return {
                dicts: [],
                myInvolvedRecords: null,
                myInvolvedTotal: 0,
                listQuery: {
                    current: 1,
                    size: 10,
                    startedByMe: false,
                    ccToMe: false,
                    finished: false,
                    unfinished: false,
                    processDefinitionCategory: undefined,
                    processDefinitionKey: undefined,
                    processInstanceId: undefined,
                    processInstanceName: undefined,
                    businessKey: undefined
                },
                processInstanceId: '',
                dialogViewVisible: false
            }
        },
        created() {
            if (this.$route.query) {
                if (this.$route.query.processDefinitionCategory) {
                    this.listQuery.processDefinitionCategory = this.$route.query.processDefinitionCategory
                }
                if (this.$route.query.processDefinitionKey) {
                    this.listQuery.processDefinitionKey = this.$route.query.processDefinitionKey
                }
                if (this.$route.query.startedByMe) {
                    this.listQuery.startedByMe = this.$route.query.startedByMe
                }
                if (this.$route.query.ccToMe) {
                    this.listQuery.ccToMe = this.$route.query.ccToMe
                }
                if (this.$route.query.unfinished) {
                    this.listQuery.unfinished = this.$route.query.unfinished
                }
                if (this.$route.query.finished) {
                    this.listQuery.finished = this.$route.query.finished
                }
            }
            this.list()
        },
        methods: {
            list() {
                getAction('/flowable/processInstance/listMyInvolved', this.listQuery).then(res => {
                    const {data} = res
                    this.myInvolvedRecords = data.records;
                    this.myInvolvedTotal = data.total
                })
            },
            btnMyInvolvedQuery() {
                this.listQuery.current = 1
                this.list()
            },
            btnMyInvolvedReset() {
                this.listQuery = {
                    current: 1,
                    size: 10,
                    startedByMe: false,
                    ccToMe: false,
                    processDefinitionCategory: undefined,
                    processInstanceId: undefined,
                    processInstanceName: undefined,
                    businessKey: undefined,
                    startedBy: undefined,
                    unfinished: false,
                    finished: false
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
