<template>
    <el-dialog title="退回任务" :visible.sync="dialogTaskBackNodesInChild" :appendToBody="true" fullscreen>

        <el-radio-group v-model="backNode" @change="selectBackNode">
            <el-radio border v-for="item in backNodes" :label="item">
                【{{item.nodeId}}-{{item.nodeName}}】{{item.userName}}
            </el-radio>
        </el-radio-group>

        <div slot="footer" class="dialog-footer">
            <el-button icon="el-icon-close" @click="dialogTaskBackNodesInChild = false">取消</el-button>
            <el-button icon="el-icon-check" type="primary" @click="doConfirm">确定</el-button>
        </div>
    </el-dialog>
</template>

<script>
    import {getAction, putAction} from '@/api/manage'
    import {Message} from 'element-ui'

    export default {
        name: 'TaskBackNodes',
        props: {
            visible: {
                type: Boolean,
                default: function () {
                    return false
                }
            },
            executeTaskId: {
                type: String,
                default: function () {
                    return ''
                }
            }
        },
        computed: {
            dialogTaskBackNodesInChild: {
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
                backNode: '',
                backNodes: []
            }
        },
        created() {
            this.initData()
        },
        methods: {
            initData() {
                getAction('/flowable/task/backNodes', {taskId: this.executeTaskId}).then(res => {
                    const {data} = res
                    this.backNodes = data
                    this.backNode = this.backNodes[0]
                })
            },
            selectBackNode(val) {
                this.backNode = val
            },
            doConfirm() {
                this.dialogTaskBackNodesInChild = false
                this.$emit("backTaskFinished", this.backNode);
            }
        }
    }
</script>

