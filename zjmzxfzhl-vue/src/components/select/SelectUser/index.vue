<template>
    <el-dialog title="选择用户" :visible.sync="visibleInChild" :append-to-body="appendToBody" fullscreen
               :before-close="close">
        <div class="el-dialog-body-custom-height">
            <el-row :gutter="5">
                <el-col :span="6">
                    <div>
                        <el-input v-model="filterText" placeholder="请输入过滤条件" style="margin-bottom:10px"/>
                        <el-tree
                                ref="_selectOrgTree"
                                :data="treeData"
                                node-key="id"
                                :props="defaultProps"
                                :filter-node-method="filterNode"
                                @node-click="handleNodeClick"
                                class="filter-tree"
                        />
                    </div>
                </el-col>
                <el-col :span="12">
                    <div>
                        <div class="filter-container">
                            <el-input v-model="listQuery.userId" placeholder="用户ID" style="width: 200px;"
                                      class="filter-item"
                                      @keyup.enter.native="btnQuery"/>
                            <el-input v-model="listQuery.userName" placeholder="用户姓名" style="width: 200px;"
                                      class="filter-item"
                                      @keyup.enter.native="btnQuery"/>
                            <el-dropdown split-button type="primary" @click="btnQuery" class="filter-item">
                                <i class="el-icon-search el-icon--left"></i>查询
                                <el-dropdown-menu slot="dropdown">
                                    <el-dropdown-item icon="el-icon-zoom-out" @click.native="btnReset">重置
                                    </el-dropdown-item>
                                </el-dropdown-menu>
                            </el-dropdown>
                        </div>
                        <el-table
                                :data="records"
                                ref="_sysUserTable"
                                @row-click="rowClick"
                                border
                                fit
                                highlight-current-row
                                height="337"
                                style="width: 100%;"
                                :cell-style="{padding:'3px'}"
                        >
                            <!--<el-table-column v-if="multipleSelect" type="selection" align="center">
                            </el-table-column>-->
                            <el-table-column label="用户ID" prop="userId" align="center">
                                <template slot-scope="scope">
                                    <span>{{ scope.row.userId }}</span>
                                </template>
                            </el-table-column>
                            <el-table-column label="用户姓名" prop="userName" align="center">
                                <template slot-scope="scope">
                                    <span>{{ scope.row.userName }}</span>
                                </template>
                            </el-table-column>
                            <el-table-column label="所属机构ID" prop="orgId" align="center">
                                <template slot-scope="scope">
                                    <span>{{ scope.row.orgId }}</span>
                                </template>
                            </el-table-column>
                            <el-table-column label="所属机构" prop="orgName" align="center">
                                <template slot-scope="scope">
                                    <span>{{ scope.row.orgName }}</span>
                                </template>
                            </el-table-column>
                        </el-table>
                        <pagination v-show="total>0" :total="total" :current.sync="listQuery.current"
                                    :size.sync="listQuery.size" @pagination="listSysUsers"/>
                    </div>
                </el-col>
                <el-col :span="6">
                    <div>
                        <div style="margin-bottom:10px">
                            <el-button icon="el-icon-remove" type="primary" @click="btnRemoveAll">清空</el-button>
                        </div>
                        <el-table
                                :data="selectedRecords"
                                border
                                fit
                                highlight-current-row
                                height="337"
                                style="width: 100%;"
                                :cell-style="{padding:'3px'}"
                        >
                            <el-table-column label="用户ID" prop="userId" align="center">
                                <template slot-scope="scope">
                                    <span>{{ scope.row.userId }}</span>
                                </template>
                            </el-table-column>
                            <el-table-column label="用户姓名" prop="userName" align="center">
                                <template slot-scope="scope">
                                    <span>{{ scope.row.userName }}</span>
                                </template>
                            </el-table-column>
                            <el-table-column v-if="multipleSelect" label="操作" align="center">
                                <template slot-scope="{row}">
                                    <i class="el-icon-delete el-icon--left" style="cursor:pointer"
                                       @click="btnRemove(row)"></i>
                                </template>
                            </el-table-column>
                        </el-table>
                    </div>
                </el-col>
            </el-row>
        </div>
        <div slot="footer" class="dialog-footer">
            <el-button icon="el-icon-close" @click="visibleInChild = false">取消</el-button>
            <el-button icon="el-icon-check" type="primary" @click="confirm">确定</el-button>
        </div>
    </el-dialog>
</template>

<script>
    import Pagination from '@/components/Pagination'
    import {getAction} from '@/api/manage'
    import {Message} from 'element-ui'

    export default {
        name: "SelectUser",
        components: {Pagination},
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
                },
                records: [],
                total: 0,
                selectedRecords: [],
                listQuery: {
                    current: 1,
                    size: 10,
                    userId: undefined,
                    userName: undefined,
                    orgId: undefined
                },
                currOrgId: ''
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
            }
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
            handleNodeClick(node) {
                this.currOrgId = node.id
                this.listSysUsers()
            },
            listSysUsers() {
                this.listQuery.orgId = this.currOrgId
                getAction('/sys/user/listSelectUser', this.listQuery).then(res => {
                    const {data} = res
                    this.records = data.records;
                    this.total = data.total
                })
            },
            btnQuery() {
                this.listQuery.current = 1
                this.listSysUsers()
            },
            btnReset() {
                this.currOrgId = ''
                this.listQuery = {
                    current: 1,
                    size: 10,
                    userId: undefined,
                    userName: undefined,
                    orgId: undefined
                }
                this.listSysUsers()
            },
            rowClick(row) {
                if (!this.multipleSelect) {
                    this.selectedRecords = []
                    this.selectedRecords[0] = row
                } else {
                    let arr = this.selectedRecords
                    let index = arr.findIndex(item => item.userId === row.userId)
                    if (index < 0) {
                        arr.push(row)
                    } else {
                        arr.splice(index, 1)
                    }
                }
            },
            btnRemoveAll() {
                this.selectedRecords = []
            },
            btnRemove(row) {
                let arr = this.selectedRecords;
                arr.splice(arr.findIndex(item => item.userId === row.userId), 1)
            },
            confirm() {
                if (!this.selectedRecords || this.selectedRecords.length == 0) {
                    Message.error('请先选择用户')
                    return
                }
                if (this.multipleSelect) {
                    this.$emit("selectUserFinished", this.selectedRecords);
                } else {
                    this.$emit("selectUserFinished", this.selectedRecords[0]);
                }
                this.selectedRecords = []
                this.records = []
                this.visibleInChild = false;
            }
        }
    }
</script>
