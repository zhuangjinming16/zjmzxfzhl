<template>
    <el-dialog title="任务信息" :visible.sync="dialogTaskDetailVisibleInChild" width="75%">
        <el-form ref="dataForm" :model="taskDetailData" disabled label-position="right" label-width="110px">
            <el-row>
                <el-col :span="8">
                    <el-form-item label="任务ID" prop="id">
                        <el-input v-model="taskDetailData.id"/>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="任务名称" prop="name">
                        <el-input v-model="taskDetailData.name"/>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="任务分类" prop="category">
                        <el-input v-model="taskDetailData.category"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="8">
                    <el-form-item label="任务描述" prop="description">
                        <el-input v-model="taskDetailData.description"/>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="所有人" prop="owner">
                        <el-input v-model="taskDetailData.owner"/>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="所有人姓名" prop="ownerName">
                        <el-input v-model="taskDetailData.ownerName"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="8">
                    <el-form-item label="认领时间" prop="claimTime">
                        <el-date-picker v-model="taskDetailData.claimTime" style="width: 200px" type="datetime"
                                        value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="执行人" prop="assignee">
                        <el-input v-model="taskDetailData.assignee"/>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="执行人姓名" prop="assigneeName">
                        <el-input v-model="taskDetailData.assigneeName"/>
                    </el-form-item>
                </el-col>

            </el-row>
            <el-row>
                <el-col :span="8">
                    <el-form-item label="到期日" prop="dueDate">
                        <el-date-picker v-model="taskDetailData.dueDate" style="width: 200px" type="date"
                                        value-format="yyyy-MM-dd"></el-date-picker>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="结束时间" prop="endTime">
                        <el-date-picker v-model="taskDetailData.endTime" style="width: 200px" type="datetime"
                                        value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="状态" prop="suspended">
                        <el-input v-if="taskDetailData.endTime==null" v-model="taskDetailData.suspended?'挂起':'激活'"/>
                        <el-input v-else value="已完成"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="8">
                    <el-form-item label="持续时间" prop="durationInMillis">
                        <el-input v-model="taskDetailData.durationInMillis"/>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="工作时间" prop="workTimeInMillis">
                        <el-input v-model="taskDetailData.workTimeInMillis"/>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="优先级" prop="priority">
                        <el-input v-model="taskDetailData.priority"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="8">
                    <el-form-item label="任务定义KEY" prop="taskDefinitionKey">
                        <el-input v-model="taskDetailData.taskDefinitionKey"/>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="流程实例ID" prop="processInstanceId">
                        <el-input v-model="taskDetailData.processInstanceId"/>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="流程实例名称" prop="processInstanceName">
                        <el-input v-model="taskDetailData.processInstanceName"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="8">
                    <el-form-item label="流程定义ID" prop="processDefinitionId">
                        <el-input v-model="taskDetailData.processDefinitionId"/>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="流程定义名称" prop="processDefinitionName">
                        <el-input v-model="taskDetailData.processDefinitionName"/>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="流程定义描述" prop="processDefinitionDescription">
                        <el-input v-model="taskDetailData.processDefinitionDescription"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="8">
                    <el-form-item label="流程定义KEY" prop="processDefinitionKey">
                        <el-input v-model="taskDetailData.processDefinitionKey"/>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="流程定义分类" prop="processDefinitionCategory">
                        <el-input v-model="taskDetailData.processDefinitionCategory"/>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="流程定义版本" prop="processDefinitionVersion">
                        <el-input v-model="taskDetailData.processDefinitionVersion"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="8">
                    <el-form-item label="流程定义发布ID" prop="processDefinitionDeploymentId">
                        <el-input v-model="taskDetailData.processDefinitionDeploymentId"/>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="表单KEY" prop="formKey">
                        <el-input v-model="taskDetailData.formKey"/>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="流程启动人" prop="processInstanceStartUserId">
                        <el-input v-model="taskDetailData.processInstanceStartUserId"/>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="8">
                    <el-form-item label="发起人完成任务" prop="initiatorCanCompleteTask">
                        <el-select v-model="taskDetailData.initiatorCanCompleteTask" placeholder="发起人完成任务">
                            <el-option v-for="item in dicts.trueOrFalse" :key="item.value" :label="item.content"
                                       :value="item.value"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="候选组" prop="memberOfCandidateGroup">
                        <el-select v-model="taskDetailData.memberOfCandidateGroup" placeholder="候选组">
                            <el-option v-for="item in dicts.trueOrFalse" :key="item.value" :label="item.content"
                                       :value="item.value"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <el-form-item label="候选成员" prop="memberOfCandidateUsers">
                        <el-select v-model="taskDetailData.memberOfCandidateUsers" placeholder="候选成员">
                            <el-option v-for="item in dicts.trueOrFalse" :key="item.value" :label="item.content"
                                       :value="item.value"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="8">
                    <el-form-item label="委派状态" prop="delegationState">
                        <el-input v-model="taskDetailData.delegationState"/>
                    </el-form-item>
                </el-col>
                <el-col :span="8">
                    <!--<el-form-item label="干系人" prop="involvedPeople">
                            <el-input v-model="taskDetailData.involvedPeople"/>
                        </el-form-item>-->
                </el-col>
                <el-col :span="8">
                </el-col>
            </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button icon="el-icon-close" @click="dialogTaskDetailVisibleInChild = false">取消</el-button>
        </div>
    </el-dialog>
</template>

<script>
    import {getAction} from '@/api/manage'

    export default {
        name: 'TaskDetail',
        props: {
            visible: {
                type: Boolean,
                default: function () {
                    return false
                }
            },
            dicts: {
                type: Object,
                default: function () {
                    return {}
                }
            },
            taskDetailData: {
                type: Object,
                default: function () {
                    return {}
                }
            }
        },
        computed: {
            dialogTaskDetailVisibleInChild: {
                get() {
                    return this.visible
                },
                set(val) {
                    this.$emit('update:visible', val)
                }
            },
        },
        data() {
            return {}
        }
    }
</script>

