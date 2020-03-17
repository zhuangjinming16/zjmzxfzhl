<template>
    <el-dialog title="发起流程" :visible.sync="dialogStartProcessVisibleInChild" :fullscreen="false">
        <div v-if="generateStartFormVisible">
            <fm-generate-form :data="startFormJson" ref="generateStartForm">
            </fm-generate-form>
        </div>
        <div slot="footer" class="dialog-footer">
            <el-form label-width="100px" v-if="showBusinessKey">
                <el-form-item label="业务主键Key:">
                    <el-input v-model="businessKey" placeholder="请输入业务主键Key"/>
                </el-form-item>
            </el-form>
            <el-button icon="el-icon-close" @click="dialogStartProcessVisibleInChild = false">取消</el-button>
            <el-button icon="el-icon-check" type="primary" @click="doStartInstance">确定
            </el-button>
        </div>
    </el-dialog>
</template>

<script>
    import {getAction, postAction} from '@/api/manage'
    import {Message} from 'element-ui'

    export default {
        name: 'StartProcess',
        props: {
            visible: {
                type: Boolean,
                default: function () {
                    return false
                }
            },
            processDefinition: {
                type: Object,
                default: function () {
                    return {}
                }
            }
        },
        computed: {
            dialogStartProcessVisibleInChild: {
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
                // fullscreen: true,
                generateStartFormVisible: false,
                startFormJson: undefined,
                variables: undefined,
                showBusinessKey: false,
                businessKey: undefined
            }
        },
        created() {
            this.initData()
        },
        methods: {
            initData() {
                if (this.processDefinition.formKey) {
                    getAction('/flowable/processDefinition/renderedStartForm', {processDefinitionId: this.processDefinition.id}).then(res => {
                        const {data} = res
                        this.showBusinessKey = data.showBusinessKey
                        if (data.renderedStartForm) {
                            this.startFormJson = JSON.parse(data.renderedStartForm)
                            this.generateStartFormVisible = true
                        }
                    })
                }
                // else {
                //     this.fullscreen = false
                // }
            },
            doStartInstance() {
                if (this.$refs.generateStartForm) {
                    this.$refs.generateStartForm.getData().then(values => {
                        if (values && values != undefined) {
                            let processInstanceFormData = JSON.stringify(values)
                            let realValues = Object.assign({processInstanceFormData}, values)
                            return postAction('/flowable/processInstance/start', {
                                processDefinitionId: this.processDefinition.id,
                                businessKey: this.businessKey,
                                values: realValues
                            }).then(({msg}) => {
                                Message.success(msg)
                                this.dialogStartProcessVisibleInChild = false
                            })
                        }
                    }).catch(e => {
                    })
                } else {
                    postAction('/flowable/processInstance/start', {
                        processDefinitionId: this.processDefinition.id,
                        businessKey: this.businessKey
                    }).then(({msg}) => {
                        Message.success(msg)
                        this.dialogStartProcessVisibleInChild = false
                    })
                }
            }
        }
    }
</script>

