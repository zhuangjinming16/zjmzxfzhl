<template>
    <el-dialog title="流程图" :visible.sync="dialogProcessImageVisibleInChild" fullscreen>
        <img :src="imagePath" alt="流程图">
    </el-dialog>
</template>

<script>
    import {getToken} from '@/utils/auth'

    export default {
        name: 'ProcessImage',
        props: {
            visible: {
                type: Boolean,
                default: function () {
                    return false
                }
            },
            processDefinitionId: {
                type: String,
                default: function () {
                    return ''
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
            dialogProcessImageVisibleInChild: {
                get() {
                    return this.visible
                },
                set(val) {
                    this.$emit('update:visible', val)
                }
            },
        },
        data() {
            return {
                imagePath: ''
            }
        },
        created() {
            this.init()
        },
        methods: {
            init() {
                if (this.processInstanceId && this.processInstanceId != null && this.processInstanceId !== '') {
                    this.imagePath = `${process.env.VUE_APP_BASE_API}` + '/flowable/processInstanceImage?processInstanceId=' + this.processInstanceId + '&access_token=' + getToken() + '&time=' + new Date()
                } else if (this.processDefinitionId && this.processDefinitionId != null && this.processDefinitionId !== '') {
                    this.imagePath = `${process.env.VUE_APP_BASE_API}` + '/flowable/processDefinition/image?processDefinitionId=' + this.processDefinitionId + '&access_token=' + getToken() + '&time=' + new Date()
                }
            }
        }
    }
</script>

