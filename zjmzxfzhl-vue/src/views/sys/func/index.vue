<template>
    <div class="app-container">
        <el-row :gutter="5">
            <el-col :span="8">
                <div>
                    <el-input v-model="filterText" placeholder="请输入过滤条件" style="margin-bottom:10px"/>
                    <el-tree
                            ref="menuTree"
                            :data="treeData"
                            node-key="menuId"
                            :props="defaultProps"
                            :filter-node-method="filterNode"
                            @node-click="handleNodeClick"
                            class="filter-tree"
                    />
                </div>
            </el-col>

            <el-col :span="16">
                <div>
                        <el-button v-permission="'sys:func:save'" icon="el-icon-plus" type="primary" @click="btnFuncCreate" class="filter-item" style="margin-bottom:10px">新增</el-button>
                        <el-button v-permission="'sys:func:delete'" icon="el-icon-delete" @click="btnFuncDelete()" class="filter-item" style="margin-bottom:10px">批量删除</el-button>
                    <el-table
                            :data="funcRecords"
                            ref="funcTable"
                            @selection-change="selectionChange"
                            border
                            fit
                            highlight-current-row
                            style="width: 100%;"
                            :cell-style="{padding:'3px'}"
                    >
                        <el-table-column type="selection" align="center">
                        </el-table-column>
                        <el-table-column label="功能ID" prop="funcId" align="center">
                            <template slot-scope="scope">
                                <span>{{ scope.row.funcId }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="功能名称" prop="funcName" align="center">
                            <template slot-scope="scope">
                                <span>{{ scope.row.funcName }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="排序号" prop="sortNo" align="center">
                            <template slot-scope="scope">
                                <span>{{ scope.row.sortNo }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="操作" align="center">
                            <template slot-scope="{row}">
                                <el-dropdown>
                                    <span class="el-dropdown-link">操作<i class="el-icon-arrow-down el-icon--right"></i></span>
                                    <el-dropdown-menu slot="dropdown">
                                        <el-dropdown-item icon="el-icon-view" @click.native="btnView(row)">查看</el-dropdown-item>
                                        <el-dropdown-item v-permission="'sys:func:update'" icon="el-icon-edit" divided @click.native="btnFuncUpdate(row)">修改</el-dropdown-item>
                                        <el-dropdown-item v-permission="'sys:func:delete'" icon="el-icon-delete" divided @click.native="btnFuncDelete(row.funcId)">删除</el-dropdown-item>
                                    </el-dropdown-menu>
                                </el-dropdown>
                            </template>
                        </el-table-column>
                    </el-table>
                    <pagination v-show="total>0" :total="total" :current.sync="listQuery.current"
                                :size.sync="listQuery.size"
                                @pagination="listFuncs"/>
                </div>
            </el-col>
        </el-row>

        <el-dialog title="功能" :visible.sync="dialogFormVisible">
            <el-form ref="dataForm" :rules="rules" :model="temp" :disabled="dialogStatus==='view'" label-position="right" label-width="110px">
                <el-form-item label="功能ID" prop="funcId">
                    <el-input v-model="temp.funcId" :readonly="dialogStatus==='update'"/>
                </el-form-item>
                <el-form-item label="功能名称" prop="funcName">
                    <el-input v-model="temp.funcName"/>
                </el-form-item>
                <el-form-item label="授权" prop="funcPermissions">
                    <el-input v-model="temp.funcPermissions"/>
                </el-form-item>
                <el-form-item label="排序号" prop="sortNo">
                    <el-input v-model="temp.sortNo"/>
                </el-form-item>
                <el-form-item label="备注">
                    <el-input v-model="temp.remark" :autosize="{ minRows: 2, maxRows: 4}" type="textarea"
                              placeholder="请输入备注信息"/>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button icon="el-icon-close" @click="dialogFormVisible = false">取消</el-button>
                <el-button v-if="dialogStatus!=='view'" icon="el-icon-check" type="primary" @click="dialogStatus==='create'?createFuncData():updateFuncData()">
                    确定
                </el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import Pagination from '@/components/Pagination'
    import {getAction, putAction, postAction, deleteAction} from '@/api/manage'
    import {Message} from 'element-ui'

    export default {
        name: 'SysFunc',
        components: {Pagination},
        data() {
            return {
                funcRecords: null,
                selectedRecords: [],
                total: 0,
                listQuery: {
                    current: 1,
                    size: 10,
                    menuId: undefined
                },
                currMenuId : undefined,
                dialogFormVisible: false,
                dialogStatus: '',
                temp: {
                    funcId: undefined,
                    funcName: '',
                    menuId: '',
                    funcPermissions: '',
                    sortNo: '',
                    remark: ''
                },
                rules: {
                    funcId: [{required: true, message: '该项不能为空', trigger: 'change'}],
                    funcName: [{required: true, message: '该项不能为空', trigger: 'change'}],
                    sortNo: [{required: true, message: '该项不能为空', trigger: 'change'}]
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
                this.$refs.menuTree.filter(val)
            }
        },
        created() {
            this.getTreeData()
        },
        methods: {
            getTreeData() {
                getAction('/sys/menu/getTreeData', {}).then(res => {
                    const {data} = res
                    this.treeData = data
                })
            },
            filterNode(value, data) {
                if (!value) return true
                return data.label.indexOf(value) !== -1
            },
            handleNodeClick(node) {
                if(!node.isLeaf){
                    return
                }
                this.currMenuId = node.id
                this.listFuncs()
            },
            listFuncs() {
                this.listQuery.menuId = this.currMenuId
                getAction('/sys/func/list', this.listQuery).then(res => {
                    const {data} = res
                    this.funcRecords = data.records;
                    this.total = data.total
                })
            },
            resetTemp() {
                this.temp = {
                    funcId: undefined,
                    funcName: '',
                    menuId: this.currMenuId,
                    funcPermissions: '',
                    sortNo: '',
                    remark: ''
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
            btnFuncCreate() {
                if(!this.currMenuId){
                    Message.error('请先选择菜单')
                    return
                }
                this.resetTemp()
                console.log(this.temp)
                this.dialogStatus = 'create'
                this.dialogFormVisible = true
                this.$nextTick(() => {
                    this.$refs['dataForm'].clearValidate()
                })
            },
            createFuncData() {
                console.log(this.temp)
                this.$refs['dataForm'].validate((valid) => {
                    if (valid) {
                        postAction('/sys/func/save', this.temp).then(({msg}) => {
                            this.dialogFormVisible = false
                            Message.success(msg)
                            this.listFuncs(this.listQuery);
                        })
                    }
                })
            },
            btnFuncUpdate(row) {
                this.temp = Object.assign({}, row) // copy obj
                this.dialogStatus = 'update'
                this.dialogFormVisible = true
                this.$nextTick(() => {
                    this.$refs['dataForm'].clearValidate()
                })
            },
            updateFuncData() {
                this.$refs['dataForm'].validate((valid) => {
                    if (valid) {
                        putAction('/sys/func/update', this.temp).then(({msg}) => {
                            this.dialogFormVisible = false
                            Message.success(msg)
                            this.listFuncs(this.listQuery);
                        })
                    }
                })
            },
            btnFuncDelete(id) {
                let ids = id ? [id] : this.selectedRecords.map(record => {
                    return record.funcId
                })
                if (ids.length == 0) {
                    Message.error('请选择要删除的记录')
                    return
                }
                deleteAction('/sys/func/delete', {ids: ids.toString()}).then(({msg}) => {
                    Message.success(msg)
                    this.listFuncs(this.listQuery);
                })
            },
            selectionChange(selectedRecords) {
                this.selectedRecords = selectedRecords
            }
        }
    }
</script>

