<template>
    <div class="app-container">
        <el-row :gutter="5">
            <el-col :span="8">
                <div>
                    <el-button v-permission="'sys:org:save'" icon="el-icon-plus" type="primary" @click="btnAdd">新增</el-button>
                    <el-button v-permission="'sys:org:delete'" icon="el-icon-delete" @click="btnDelete">删除</el-button>
                    <el-input v-model="filterText" placeholder="请输入过滤条件" style="margin:10px 0px 10px 0px"/>
                    <el-tree
                            ref="orgTree"
                            :data="treeData"
                            node-key="id"
                            :props="defaultProps"
                            :filter-node-method="filterNode"
                            @node-click="handleNodeClick"
                            class="filter-tree"
                    />
                </div>
            </el-col>

            <el-col :span="16">
                <div>
                    <el-form ref="dataForm" :rules="rules" :model="temp" label-position="right" label-width="110px">
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="机构ID" prop="orgId">
                                    <el-input v-model="temp.orgId" :readonly="status==='update'"/>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="上级机构ID" prop="parentOrgId" hidden>
                                    <el-input v-model="temp.parentOrgId" readonly/>
                                </el-form-item>
                                <el-form-item label="上级机构" prop="parentOrgName">
                                    <el-input v-model="temp.parentOrgName" readonly/>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="机构名称" prop="orgName">
                                    <el-input v-model="temp.orgName"/>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="机构简称" prop="shortName">
                                    <el-input v-model="temp.shortName"/>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="机构类型" prop="orgType">
                                    <el-select v-model="temp.orgType" placeholder="机构类型">
                                        <el-option v-for="(item, index) in dicts.orgType" :key="index"
                                                   :label="item.content" :value="item.value"></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="机构级次码" prop="orgLevelCode">
                                    <el-input v-model="temp.orgLevelCode" readonly/>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="12">
                                <el-form-item label="机构级别" prop="orgLevel" value-key="value">
                                    <el-select v-model="temp.orgLevel" placeholder="机构级别" disabled>
                                        <el-option v-for="(item, index) in dicts.orgLevel" :key="index"
                                                   :label="item.content" :value="item.value"></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :span="12">
                                <el-form-item label="是否叶子" prop="isLeaf">
                                    <el-select v-model="temp.isLeaf" placeholder="是否叶子" disabled>
                                        <el-option v-for="(item, index) in dicts.yesOrNo" :key="index"
                                                   :label="item.content" :value="item.value"></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="24">
                                <el-form-item label="备注">
                                    <el-input v-model="temp.remark" :autosize="{ minRows: 2, maxRows: 4}"
                                              type="textarea"
                                              placeholder="请输入备注信息"/>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="24">
                                <el-form-item>
                                    <el-button v-permission="['sys:org:save','sys:org:update']" icon="el-icon-check" type="primary" @click="btnSave">保存</el-button>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </el-form>
                </div>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    import {getAction, putAction, postAction, deleteAction} from '@/api/manage'
    import {Message} from 'element-ui'

    export default {
        name: 'SysOrg',
        data() {
            return {
                dicts: [],
                status: '',
                temp: {
                    orgId: undefined,
                    orgName: '',
                    parentOrgId: '',
                    shortName: '',
                    orgType: '1',
                    orgLevel: '',
                    orgLevelCode: '',
                    remark: '',
                    isLeaf: '',
                    parentOrgName: ''
                },
                rules: {
                    orgId: [{required: true, message: '该项不能为空', trigger: 'change'}],
                    orgName: [{required: true, message: '该项不能为空', trigger: 'change'}],
                    orgType: [{required: true, message: '该项不能为空', trigger: 'change'}]
                },
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
                this.$refs.orgTree.filter(val)
            }
        },
        created() {
            this.getTreeData()
            this.getDicts('orgLevel,orgType,yesOrNo').then(({data}) => {
                this.dicts = data
            })
        },
        methods: {
            getTreeData() {
                getAction('/sys/org/getTreeData', {}).then(res => {
                    const {data} = res
                    this.treeData = data
                })
            },
            filterNode(value, data) {
                if (!value) return true
                return data.label.indexOf(value) !== -1
            },
            handleNodeClick({data}) {
                this.status = 'update'
                this.temp = Object.assign({}, data) // copy obj
            },
            btnSave() {
                this.$refs['dataForm'].validate((valid) => {
                    if (valid) {
                        if (this.status === 'update') {
                            putAction('/sys/org/update', this.temp).then(({msg, data}) => {
                                Message.success(msg)
                                this.temp = Object.assign({}, data)
                                this.$refs.orgTree.getCurrentNode().label = data.orgName
                                this.$refs.orgTree.getCurrentNode().data = Object.assign({}, data)
                            })
                        } else {
                            postAction('/sys/org/save', this.temp).then(({msg, data}) => {
                                Message.success(msg)
                                this.temp = Object.assign({}, data)
                                this.getTreeData()
                            })
                        }
                    }
                })
            },
            resetTemp() {
                this.temp = {
                    orgId: undefined,
                    orgName: '',
                    parentOrgId: '',
                    shortName: '',
                    orgType: '1',
                    orgLevel: '',
                    orgLevelCode: '',
                    remark: '',
                    isLeaf: '',
                    parentOrgName: ''
                }
            },
            btnAdd() {
                let parentOrgId = ''
                let parentOrgName = ''
                if (this.temp.orgId) {
                    parentOrgId = this.temp.orgId
                    parentOrgName = this.temp.orgName
                }
                this.resetTemp()
                this.temp.parentOrgId = parentOrgId
                this.temp.parentOrgName = parentOrgName
                this.status = 'create'
                this.$nextTick(() => {
                    this.$refs['dataForm'].clearValidate()
                })
            },
            btnDelete() {
                if (!this.temp.orgId) {
                    Message.error('请选择要删除的记录')
                    return
                }
                let curNode = this.$refs.orgTree.getCurrentNode();
                if (curNode && curNode.children && curNode.children.length > 0) {
                    Message.error('请先删除叶子节点')
                    return
                }
                deleteAction('/sys/org/delete', {ids: this.temp.orgId}).then(({msg}) => {
                    Message.success(msg)
                    this.resetTemp()
                    this.$nextTick(() => {
                        this.$refs['dataForm'].clearValidate()
                    })
                    this.getTreeData()
                })
            }
        }
    }
</script>

