<template>
    <el-dialog title="选择机构" :visible.sync="visibleInChild" :append-to-body="appendToBody" :before-close="close">
        <div class="el-dialog-body-custom-height">
            <el-input v-model="filterText" placeholder="请输入过滤条件" style="margin:10px 0px 10px 0px"/>
            <el-tree
                    ref="_selectOrgTree"
                    :data="treeData"
                    node-key="id"
                    :props="defaultProps"
                    :filter-node-method="filterNode"
                    class="filter-tree"
                    check-strictly
                    :show-checkbox="multipleSelect"
            />
        </div>
        <div slot="footer" class="dialog-footer">
            <el-button icon="el-icon-close" @click="visibleInChild = false">取消</el-button>
            <el-button icon="el-icon-check" type="primary" @click="confirm">确定</el-button>
        </div>
    </el-dialog>
</template>

<script>
    import {getAction} from '@/api/manage'
    import {Message} from 'element-ui'

    export default {
        name: "SelectOrg",
        props: {
            visible: {
                type: Boolean,
                default: false
            },
            multipleSelect: {
                type: Boolean,
                default: false
            },
            type: {
                type: String,
                default: ''
            },
            appendToBody: {
                type: Boolean,
                default: false
            }
        },
        data() {
            return {
                filterText: '',
                treeData: [],
                defaultProps: {
                    children: 'children',
                    label: 'label',
                    isLeaf: 'isLeaf',
                    data: 'data'
                }
            }
        },
        watch: {
            filterText(val) {
                this.$refs._selectOrgTree.filter(val)
            }
        },
        computed: {
            visibleInChild: {
                get() {
                    return this.visible
                },
                set(val) {
                    this.$emit('update:visible', val)
                }
            },
        },
        methods: {
            close() {
                this.visibleInChild = false;
            },
            filterNode(value, data) {
                if (!value) return true
                return data.label.indexOf(value) !== -1
            },
            getTreeData() {
                getAction('/sys/org/getSelectTreeData', {type: this.type}).then(res => {
                    const {data} = res
                    this.treeData = data
                })
            },
            confirm() {
                if (this.multipleSelect) {
                    let checkedNodes = this.$refs._selectOrgTree.getCheckedNodes();
                    if (!checkedNodes || checkedNodes.length == 0) {
                        Message.error('请先选择机构')
                        return
                    }
                    this.$emit("selectOrgFinished", checkedNodes);
                } else {
                    let curNode = this.$refs._selectOrgTree.getCurrentNode();
                    if (!curNode) {
                        Message.error('请先选择机构')
                        return
                    }
                    this.$emit("selectOrgFinished", curNode);
                }
                this.visibleInChild = false;
            }
        }
    }
</script>
