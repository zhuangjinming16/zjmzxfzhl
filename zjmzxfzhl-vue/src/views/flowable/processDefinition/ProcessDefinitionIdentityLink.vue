<template>
    <el-dialog :title="title" fullscreen :visible.sync="dialogProcessDefinitionIdentityLinkInChild" width="75%">
        <el-button-group style="padding-bottom: 10px">
            <el-button v-permission="'flowable:processDefinitionIdentityLink:save'" icon="el-icon-plus" type="primary" @click="btnCreate" class="filter-item">新增</el-button>
        </el-button-group>
        <el-table
                :data="records"
                @selection-change="selectionChange"
                border
                fit
                highlight-current-row
                style="width:100%;"
                :cell-style="{padding:'3px'}"
        >
            <el-table-column type="selection" align="center">
            </el-table-column>
            <el-table-column label="授权ID" prop="identityId" align="center">
                <template slot-scope="scope"><span>{{ scope.row.identityId }}</span></template>
            </el-table-column>
            <el-table-column label="授权类型" prop="identityType" align="center">
                <template slot-scope="scope">
                    <span v-html="formatDictText(dicts.identityType,scope.row.identityType)"></span>
                </template>
            </el-table-column>
            <el-table-column label="授权名称" prop="identityName" align="center">
                <template slot-scope="scope"><span>{{ scope.row.identityName }}</span></template>
            </el-table-column>
            <el-table-column label="操作" align="center">
                <template slot-scope="{row}">
                    <el-dropdown>
                        <span class="el-dropdown-link">操作<i
                                class="el-icon-arrow-down el-icon--right"></i></span>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item icon="el-icon-view" @click.native="btnView(row)">查看
                            </el-dropdown-item>
                            <el-dropdown-item v-permission="'flowable:processDefinitionIdentityLink:delete'" icon="el-icon-delete" divided @click.native="btnDelete(row)">删除
                            </el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </template>
            </el-table-column>
        </el-table>

        <el-dialog title="流程授权" :visible.sync="dialogFormVisible" :append-to-body="true">
            <el-form ref="dataForm" :rules="rules" :model="temp" :disabled="dialogStatus==='view'"
                     label-position="right" label-width="110px">
                <el-form-item label="授权类型" prop="identityType">
                    <!--<el-input v-model="temp.identityType"/>-->
                    <el-select v-model="temp.identityType" placeholder="授权类型">
                        <el-option v-for="(item, index) in dicts.identityType" :key="index"
                                   :label="item.content" :value="item.value"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="授权id" prop="identityId">
                    <el-input v-model="temp.identityId"/>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button icon="el-icon-close" @click="dialogFormVisible = false">取消</el-button>
                <el-button v-if="dialogStatus!=='view'" icon="el-icon-check" type="primary" @click="createData()">确定
                </el-button>
            </div>
        </el-dialog>
    </el-dialog>
</template>

<script>
    import {getAction, postAction, deleteAction} from '@/api/manage'
    import {Message} from 'element-ui'

    export default {
        name: 'ProcessDefinitionIdentityLink',
        props: {
            visible: {
                type: Boolean,
                default: function () {
                    return false
                }
            },
            // dicts: {
            //     type: Object,
            //     default: function () {
            //         return {}
            //     }
            // },
            processDefinitionId: {
                type: String,
                default: function () {
                    return ''
                }
            },
            processDefinitionName: {
                type: String,
                default: function () {
                    return ''
                }
            }
        },
        computed: {
            dialogProcessDefinitionIdentityLinkInChild: {
                get() {
                    return this.visible
                },
                set(val) {
                    this.$emit('update:visible', val)
                }
            },
            title: {
                get() {
                    return '流程授权【' + this.processDefinitionName + '】'
                }
            }
        },
        created() {
            this.getDicts('identityType').then(({data}) => {
                this.dicts = data
            })
            this.list()
        },
        data() {
            return {
                records: [],
                selectedRecords: [],
                dicts:[],
                listQuery: {
                    processDefinitionId: this.processDefinitionId,
                    identityType: '',
                    identityId: ''
                },
                dialogFormVisible: false,
                dialogStatus: '',
                temp: {
                    processDefinitionId: this.processDefinitionId,
                    identityType: '',
                    identityId: ''
                },
                rules: {
                    identityType: [{required: true, message: '该项不能为空', trigger: 'change'}],
                    identityId: [{required: true, message: '该项不能为空', trigger: 'change'}]
                }
            }
        },
        methods: {
            list() {
                getAction('/flowable/processDefinitionIdentityLink/list', this.listQuery).then(res => {
                    const {data} = res
                    this.records = data
                })
            },
            resetTemp() {
                this.temp = {
                    processDefinitionId: this.processDefinitionId,
                    identityType: '',
                    identityId: ''
                }
            },
            btnView(row) {
                this.temp = Object.assign({}, row)
                this.dialogStatus = 'view'
                this.dialogFormVisible = true
                this.$nextTick(() => {
                    this.$refs['dataForm'].clearValidate()
                })
            },
            btnCreate() {
                this.resetTemp()
                this.dialogStatus = 'create'
                this.dialogFormVisible = true
                this.$nextTick(() => {
                    this.$refs['dataForm'].clearValidate()
                })
            },
            createData() {
                this.$refs['dataForm'].validate((valid) => {
                    if (valid) {
                        postAction('/flowable/processDefinitionIdentityLink/save', this.temp).then(({msg}) => {
                            this.dialogFormVisible = false
                            Message.success(msg)
                            this.list()
                        })
                    }
                })
            },
            btnDelete(row) {
                deleteAction('/flowable/processDefinitionIdentityLink/delete', {
                    processDefinitionId: this.processDefinitionId,
                    identityType: row.identityType,
                    identityId: row.identityId
                }).then(({msg}) => {
                    Message.success(msg)
                    this.list()
                })
            },
            selectionChange(selectedRecords) {
                this.selectedRecords = selectedRecords
            }
        }
    }
</script>

