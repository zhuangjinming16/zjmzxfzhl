<template>
    <div class="app-container">
        <div class="filter-container">
            <div class="filter-container">
                <el-checkbox v-model="listQuery.startByMe">我发起的</el-checkbox>
            </div>
            <div>
                <el-input v-model="listQuery.processInstanceId" placeholder="流程实例ID"
                          style="width: 200px;"
                          class="filter-item"
                          @keyup.enter.native="btnMyInvolvedQuery"/>
                <el-input v-model="listQuery.processInstanceName" placeholder="流程实例名称"
                          style="width: 200px;"
                          class="filter-item"
                          @keyup.enter.native="btnMyInvolvedQuery"/>
                <el-input v-model="listQuery.businessKey" placeholder="业务主键KEY" style="width: 200px;"
                          class="filter-item"
                          @keyup.enter.native="btnMyInvolvedQuery"/>
                <el-input v-model="listQuery.startedBy" placeholder="启动人" style="width: 200px;"
                          class="filter-item"
                          @keyup.enter.native="btnMyInvolvedQuery"/>
                <el-select v-model="listQuery.finished" placeholder="是否已结束" style="width: 200px;"
                           class="filter-item">
                    <el-option v-for="(item, index) in dicts.trueOrFalse"
                               :key="index" :label="item.content"
                               :value="item.value"></el-option>
                </el-select>

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

        <process-detail v-if="dialogViewVisible" :visible.sync="dialogViewVisible" :processInstanceId="processInstanceId"></process-detail>
    </div>
</template>

<script>
    import Pagination from '@/components/Pagination'
    import {getAction, putAction, postAction, deleteAction} from '@/api/manage'
    import {getToken} from '@/utils/auth'
    import ProcessDetail from "../components/ProcessDetail";

    export default {
        name: 'FlowableMyProcess',
        components: {ProcessDetail, Pagination},
        data() {
            return {
                dicts: [],
                myInvolvedRecords: null,
                myInvolvedTotal: 0,
                listQuery: {
                    current: 1,
                    size: 10,
                    startByMe: false,
                    processInstanceId: undefined,
                    processInstanceName: undefined,
                    businessKey: undefined,
                    finished: undefined
                },
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
                    startByMe: false,
                    processInstanceId: undefined,
                    processInstanceName: undefined,
                    businessKey: undefined,
                    startedBy: undefined,
                    finished: undefined
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
