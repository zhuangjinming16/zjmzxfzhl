<template>
    <div class="app-container">
        <div class="filter-container">
            <el-input v-model="listQuery.processDefinitionId" placeholder="流程定义ID" style="width: 200px;"
                      class="filter-item"
                      @keyup.enter.native="btnQuery"/>
            <el-input v-model="listQuery.processInstanceId" placeholder="流程实例ID" style="width: 200px;"
                      class="filter-item"
                      @keyup.enter.native="btnQuery"/>
            <el-input v-model="listQuery.processDefinitionName" placeholder="流程定义名称" style="width: 200px;"
                      class="filter-item"
                      @keyup.enter.native="btnQuery"/>
            <el-input v-model="listQuery.processDefinitionKey" placeholder="流程定义KEY" style="width: 200px;"
                      class="filter-item"
                      @keyup.enter.native="btnQuery"/>
            <el-input v-model="listQuery.businessKey" placeholder="业务主键KEY" style="width: 200px;" class="filter-item"
                      @keyup.enter.native="btnQuery"/>
            <el-input v-model="listQuery.startedBy" placeholder="启动人" style="width: 200px;" class="filter-item"
                      @keyup.enter.native="btnQuery"/>
            <el-select v-model="listQuery.finished" placeholder="是否已结束" style="width: 200px;" class="filter-item">
                <el-option v-for="(item, index) in dicts.trueOrFalse"
                           :key="index" :label="item.content"
                           :value="item.value"></el-option>
            </el-select>
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
                border
                fit
                highlight-current-row
                style="width: 100%;"
                :cell-style="{padding:'3px'}"
        >
            <el-table-column label="流程实例ID" prop="id" align="center" width="290px">
                <template slot-scope="scope"><span>{{ scope.row.id }}</span></template>
            </el-table-column>
            <el-table-column label="流程定义名称" prop="processDefinitionName" align="center">
                <template slot-scope="scope"><span>{{ scope.row.processDefinitionName }}</span></template>
            </el-table-column>
            <el-table-column label="流程定义KEY" prop="processDefinitionKey" align="center">
                <template slot-scope="scope"><span>{{ scope.row.processDefinitionKey }}</span></template>
            </el-table-column>
            <el-table-column label="版本" prop="processDefinitionVersion" align="center" width="60px">
                <template slot-scope="scope"><span>{{ scope.row.processDefinitionVersion }}</span></template>
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
                            <el-dropdown-item icon="el-icon-view" @click.native="btnViewProcessInstance(row)">查看
                            </el-dropdown-item>
                            <el-dropdown-item icon="el-icon-view" divided @click.native="btnView(row.id)">查看详情
                            </el-dropdown-item>
                            <el-dropdown-item v-permission="'flowable:task:list'" icon="el-icon-view" divided @click.native="btnViewTaskList(row)">任务列表
                            </el-dropdown-item>
                            <el-dropdown-item v-permission="'flowable:processInstance:delete'" icon="el-icon-delete" divided @click.native="btnDelete(row.id)">删除
                            </el-dropdown-item>
                            <el-dropdown-item v-permission="'flowable:processInstance:delete'" icon="el-icon-delete" divided @click.native="btnDelete(row.id,true)">
                                删除包含历史
                            </el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </template>
            </el-table-column>
        </el-table>
        <pagination v-show="total>0" :total="total" :current.sync="listQuery.current" :size.sync="listQuery.size"
                    @pagination="list"/>

        <el-dialog title="流程实例" :visible.sync="dialogFormVisible">
            <el-form ref="dataForm" :model="temp" disabled
                     label-position="right" label-width="110px">
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="流程实例ID" prop="id">
                            <el-input v-model="temp.id"/>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="业务主键Key" prop="businessKey">
                            <el-input v-model="temp.businessKey"/>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="流程定义ID" prop="processDefinitionId">
                            <el-input v-model="temp.processDefinitionId"/>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="流程定义名称" prop="processDefinitionName">
                            <el-input v-model="temp.processDefinitionName"/>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="流程定义KEY" prop="processDefinitionKey">
                            <el-input v-model="temp.processDefinitionKey"/>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="流程定义版本" prop="processDefinitionVersion">
                            <el-input v-model="temp.processDefinitionVersion"/>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="开始时间" prop="startTime">
                            <el-date-picker v-model="temp.startTime" style="width: 200px" type="datetime"
                                            value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="结束时间" prop="endTime">
                            <el-date-picker v-model="temp.endTime" style="width: 200px" type="datetime"
                                            value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="启动人ID" prop="startUserId">
                            <el-input v-model="temp.startUserId"/>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="启动人姓名" prop="startUserName">
                            <el-input v-model="temp.startUserName"/>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="启动节点ID" prop="startActivityId">
                            <el-input v-model="temp.startActivityId"/>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="耗时(毫秒)" prop="durationInMillis">
                            <el-input v-model="temp.durationInMillis"/>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="子流程ID" prop="superProcessInstanceId">
                            <el-input v-model="temp.superProcessInstanceId"/>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">

                        <el-form-item label="状态" prop="suspended">
                            <el-input v-if="temp.endTime==null" v-model="temp.suspended?'挂起':'激活'"/>
                            <el-input v-else value="结束"/>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="删除原因" prop="deleteReason">
                            <el-input v-model="temp.deleteReason"/>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button v-permission="'flowable:processInstance:suspendOrActivate'" v-if="temp.endTime==null" icon="el-icon-check"
                           @click="btnSuspendOrActivate(temp.id,temp.suspended)">{{temp.suspended?'激活':'挂起'}}
                </el-button>
                <el-button icon="el-icon-close" @click="dialogFormVisible = false">取消</el-button>
            </div>
        </el-dialog>
        <process-detail v-if="dialogViewVisible" :visible.sync="dialogViewVisible" :processInstanceId="processInstanceId"></process-detail>
    </div>
</template>

<script>
    import Pagination from '@/components/Pagination'
    import {getAction, putAction, postAction, deleteAction} from '@/api/manage'
    import {Message} from 'element-ui'
    import {getToken} from '@/utils/auth'
    import ProcessDetail from "../components/ProcessDetail";

    export default {
        name: 'FlowableProcessInstance',
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
                    processDefinitionId: undefined,
                    processDefinitionName: undefined,
                    processDefinitionKey: undefined,
                    businessKey: undefined,
                    startedBy: undefined,
                    finished: undefined
                },
                dialogFormVisible: false,
                dialogImportVisible: false,
                importTenantId: '',
                dialogProcessImageVisible: false,
                processInstanceId: '',
                imagePath: '',
                temp: {
                    id: undefined,
                    businessKey: '',
                    processDefinitionId: '',
                    processDefinitionName: '',
                    processDefinitionKey: '',
                    processDefinitionVersion: '',
                    startTime: undefined,
                    endTime: undefined,
                    durationInMillis: undefined,
                    startUserId: '',
                    startUserName: '',
                    startActivityId: '',
                    superProcessInstanceId: '',
                    suspended: undefined,
                    deleteReason: ''
                },
                dialogViewVisible: false
            }
        },
        beforeCreate() {
            this.getDicts('trueOrFalse').then(({data}) => {
                this.dicts = data
            })
        },
        created() {
            if (this.$route.query && this.$route.query.processDefinitionId) {
                this.listQuery.processDefinitionId = this.$route.query.processDefinitionId
            }
            this.list()
        },
        methods: {
            list() {
                getAction('/flowable/processInstance/list', this.listQuery).then(res => {
                    const {data} = res
                    console.info(data)
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
                    processDefinitionId: undefined,
                    processDefinitionName: undefined,
                    processDefinitionKey: undefined,
                    businessKey: undefined,
                    startedBy: undefined,
                    finished: undefined
                }
                this.list()
            },
            btnViewProcessInstance(row) {
                getAction('/flowable/processInstance/queryById', {processInstanceId: row.id}).then(res => {
                    const {data} = res
                    this.temp = Object.assign({}, data)
                    this.dialogFormVisible = true
                })
            },
            btnView(processInstanceId) {
                this.processInstanceId = processInstanceId
                this.dialogViewVisible = true
            },
            btnViewTaskList(row) {
                this.$router.push({path: '/flowable/task', query: {processInstanceId: row.id}})
            },
            btnDelete(id, cascade) {
                if (!id) {
                    Message.error('请选择要删除的记录')
                    return
                }
                deleteAction('/flowable/processInstance/delete', {
                    processInstanceId: id,
                    cascade: cascade
                }).then(({msg}) => {
                    Message.success(msg)
                    this.list();
                })
            },
            btnSuspendOrActivate(processInstanceId, suspend) {
                let suspendOrActivate = suspend ? 'activate' : 'suspend'
                putAction('/flowable/processInstance/' + suspendOrActivate, {processInstanceId: processInstanceId}).then(({msg}) => {
                    Message.success(msg)
                    this.temp.suspended = !suspend
                })
            }
        }
    }
</script>
