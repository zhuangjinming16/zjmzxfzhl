<template>
    <el-dialog title="查看详情" :visible.sync="dialogProcessDetailVisibleInChild" fullscreen>
        <el-tabs v-model="activeName" type="border-card" @tab-click="handleClick">
            <el-tab-pane label="过程意见" name="comments">
                <el-table
                        :data="comments"
                        border
                        fit
                        highlight-current-row
                        style="width: 100%;"
                        :cell-style="{padding:'3px'}"
                >
                    <el-table-column label="任务节点" prop="taskName" align="center" width="165px">
                        <template slot-scope="scope"><span>{{ scope.row.taskName }}</span></template>
                    </el-table-column>
                    <el-table-column label="操作类型" prop="typeDesc" align="center" width="80px">
                        <template slot-scope="scope"><span>{{ scope.row.typeDesc }}</span></template>
                    </el-table-column>
                    <el-table-column label="参与人" prop="userName" align="center" width="80px">
                        <template slot-scope="scope"><span>{{ scope.row.userName }}</span></template>
                    </el-table-column>
                    <el-table-column label="时间" prop="time" align="center" width="165px">
                        <template slot-scope="scope"><span>{{ scope.row.time }}</span></template>
                    </el-table-column>
                    <el-table-column label="意见" prop="fullMessage" align="left">
                        <template slot-scope="scope"><span>{{ scope.row.fullMessage }}</span></template>
                    </el-table-column>
                </el-table>
            </el-tab-pane>
            <el-tab-pane label="流程表单" name="processIntanceForm">
                <div v-if="generateStartFormVisible">
                    <fm-generate-form :data="startFormJson" :value="variables" ref="generateStartForm">
                    </fm-generate-form>
                </div>
                <el-form label-width="100px" v-if="showBusinessKey">
                    <el-form-item label="业务主键Key:">
                        <el-input v-model="businessKey" disabled/>
                    </el-form-item>
                </el-form>
                <div v-if="!generateStartFormVisible&&!showBusinessKey">
                    无
                </div>
            </el-tab-pane>
            <el-tab-pane label="流程图" name="processIntanceImage">
                <el-scrollbar><img :src="imagePath" alt="流程图"></el-scrollbar>
            </el-tab-pane>
        </el-tabs>

    </el-dialog>
</template>

<script>
    import {getAction} from '@/api/manage'
    import {getToken} from '@/utils/auth'

    export default {
        name: 'ProcessDetail',
        props: {
            visible: {
                type: Boolean,
                default: function () {
                    return false
                }
            },
            processInstanceId: {
                type: String,
                default: function () {
                    return ''
                }
            }
        },
        computed: {
            dialogProcessDetailVisibleInChild: {
                get() {
                    return this.visible
                },
                set(val) {
                    this.$emit('update:visible', val)
                }
            }
        },
        data() {
            return {
                activeName: 'comments',
                comments: [],
                imagePath: '',
                isShowProcessInstanceForm: false,
                generateStartFormVisible: false,
                startFormJson: undefined,
                variables: undefined,
                showBusinessKey: false,
                businessKey: undefined
            }
        },
        created() {
            this.init()
        },
        methods: {
            init() {
                this.listComments()
            },
            handleClick(tab, event) {
                if (tab.name == 'processIntanceForm') {
                    this.showProcessInstanceForm();
                } else if (tab.name == 'processIntanceImage') {
                    this.showImage();
                }
            },
            showProcessInstanceForm() {
                if (!this.isShowProcessInstanceForm) {
                    getAction('/flowable/processInstance/formData', {processInstanceId: this.processInstanceId}).then(res => {
                        const {data} = res
                        this.showBusinessKey = data.showBusinessKey
                        this.businessKey = data.businessKey
                        if (data && data.renderedStartForm) {
                            this.startFormJson = JSON.parse(data.renderedStartForm)
                            this.variables = JSON.parse(data.variables.processInstanceFormData)
                            this.generateStartFormVisible = true
                        }
                        this.isShowProcessInstanceForm = true
                    })
                }
            },
            listComments() {
                getAction('/flowable/processInstance/comments', {processInstanceId: this.processInstanceId}).then(res => {
                    const {data} = res
                    this.comments = data;
                })
            },
            showImage() {
                if (!this.imagePath) {
                    this.imagePath = `${process.env.VUE_APP_BASE_API}` + '/flowable/processInstanceImage?processInstanceId=' + this.processInstanceId + '&access_token=' + getToken() + '&time=' + new Date()
                }
            }
        }
    }
</script>

