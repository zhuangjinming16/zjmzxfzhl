<template>
    <div class="app-container">
        <div class="filter-container">
            <el-input v-model="listQuery.zjmzxfzhlName" placeholder="名称" style="width: 200px;" class="filter-item" @keyup.enter.native="btnQuery"/>
            <el-select v-model="listQuery.zjmzxfzhlCodeInfo" placeholder="代码信息" class="filter-item"><el-option v-for="(item, index) in dicts.yesOrNo" :key="index" :label="item.content" :value="item.value"></el-option></el-select>
            <el-input v-model="listQuery.filterOperatorEq" placeholder="等于" style="width: 200px;" class="filter-item" @keyup.enter.native="btnQuery"/>
            <el-input v-model="listQuery.filterOperatorNe" placeholder="不等于" style="width: 200px;" class="filter-item" @keyup.enter.native="btnQuery"/>
            <el-input v-model="listQuery.filterOperatorLt" placeholder="小于" style="width: 200px;" class="filter-item" @keyup.enter.native="btnQuery"/>
            <el-input v-model="listQuery.filterOperatorLe" placeholder="小于等于" style="width: 200px;" class="filter-item" @keyup.enter.native="btnQuery"/>
            <el-input v-model="listQuery.filterOperatorGt" placeholder="大于" style="width: 200px;" class="filter-item" @keyup.enter.native="btnQuery"/>
            <el-input v-model="listQuery.filterOperatorGe" placeholder="大于等于" style="width: 200px;" class="filter-item" @keyup.enter.native="btnQuery"/>
            <el-input v-model="listQuery.filterOperatorIn" placeholder="IN" style="width: 200px;" class="filter-item" @keyup.enter.native="btnQuery"/>
            <el-input v-model="listQuery.filterOperatorBetween" placeholder="BETWEEN" style="width: 200px;" class="filter-item" @keyup.enter.native="btnQuery"/>
            <el-input v-model="listQuery.filterOperatorLike" placeholder="模糊" style="width: 200px;" class="filter-item" @keyup.enter.native="btnQuery"/>
            <el-input v-model="listQuery.filterOperatorLikeLeft" placeholder="左模糊" style="width: 200px;" class="filter-item" @keyup.enter.native="btnQuery"/>
            <el-input v-model="listQuery.filterOperatorLikeRight" placeholder="右模糊" style="width: 200px;" class="filter-item" @keyup.enter.native="btnQuery"/>
            <el-dropdown split-button type="primary" @click="btnQuery" class="filter-item">
            	<i class="el-icon-search el-icon--left"></i>查询
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item icon="el-icon-zoom-out" @click.native="btnReset">重置</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
            <el-button-group>
                <el-button v-permission="'demo:zjmzxfzhl:save'" icon="el-icon-plus" type="primary" @click="btnCreate" class="filter-item">新增</el-button>
                <el-button v-permission="'demo:zjmzxfzhl:delete'" icon="el-icon-delete" @click="btnDelete()" class="filter-item">批量删除</el-button>
            </el-button-group>
        </div>
        <el-table
                :data="records"
                ref="multipleTable"
                @selection-change="selectionChange"
                border
                fit
                highlight-current-row
                style="width: 100%;"
                :cell-style="{padding:'3px'}"
        >
            <el-table-column type="selection" align="center">
            </el-table-column>
            <el-table-column label="名称" prop="zjmzxfzhlName" align="center"><template slot-scope="scope"><span>{{ scope.row.zjmzxfzhlName }}</span></template></el-table-column>
            <el-table-column label="代码信息" prop="zjmzxfzhlCodeInfo" align="center"><template slot-scope="scope"><span v-html="formatDictText(dicts.yesOrNo,scope.row.zjmzxfzhlCodeInfo)"></span></template></el-table-column>
            <el-table-column label="等于" prop="filterOperatorEq" align="center"><template slot-scope="scope"><span>{{ scope.row.filterOperatorEq }}</span></template></el-table-column>
            <el-table-column label="小于" prop="filterOperatorLt" align="center"><template slot-scope="scope"><span>{{ scope.row.filterOperatorLt }}</span></template></el-table-column>
            <el-table-column label="小于等于" prop="filterOperatorLe" align="center"><template slot-scope="scope"><span>{{ scope.row.filterOperatorLe }}</span></template></el-table-column>
            <el-table-column label="IN" prop="filterOperatorIn" align="center"><template slot-scope="scope"><span>{{ scope.row.filterOperatorIn }}</span></template></el-table-column>
            <el-table-column label="BETWEEN" prop="filterOperatorBetween" align="center"><template slot-scope="scope"><span>{{ scope.row.filterOperatorBetween }}</span></template></el-table-column>
            <el-table-column label="模糊" prop="filterOperatorLike" align="center"><template slot-scope="scope"><span>{{ scope.row.filterOperatorLike }}</span></template></el-table-column>
            <el-table-column label="操作" align="center">
                <template slot-scope="{row}">
                	<el-dropdown>
                        <span class="el-dropdown-link">操作<i class="el-icon-arrow-down el-icon--right"></i></span>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item icon="el-icon-view" @click.native="btnView(row)">查看</el-dropdown-item>
                            <el-dropdown-item v-permission="'demo:zjmzxfzhl:update'" icon="el-icon-edit" divided @click.native="btnUpdate(row)">修改</el-dropdown-item>
                            <el-dropdown-item v-permission="'demo:zjmzxfzhl:delete'" icon="el-icon-delete" divided @click.native="btnDelete(row.zjmzxfzhlId)">删除</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </template>
            </el-table-column>
        </el-table>
        <pagination v-show="total>0" :total="total" :current.sync="listQuery.current" :size.sync="listQuery.size"
                    @pagination="list"/>

        <el-dialog title="开发示例" :visible.sync="dialogFormVisible">
            <el-form ref="dataForm" :rules="rules" :model="temp" :disabled="dialogStatus==='view'" label-position="right" label-width="110px">
                <el-form-item label="ID" prop="zjmzxfzhlId"><el-input v-model="temp.zjmzxfzhlId" :readonly="dialogStatus==='update'"/></el-form-item>
                <el-form-item label="名称" prop="zjmzxfzhlName"><el-input v-model="temp.zjmzxfzhlName"/></el-form-item>
                <el-form-item label="代码信息" prop="zjmzxfzhlCodeInfo"><el-select v-model="temp.zjmzxfzhlCodeInfo" placeholder="代码信息"><el-option v-for="(item, index) in dicts.yesOrNo" :key="index" :label="item.content" :value="item.value"></el-option></el-select></el-form-item>
                <el-form-item label="日期格式" prop="zjmzxfzhlDate"><el-date-picker v-model="temp.zjmzxfzhlDate" type="date"></el-date-picker></el-form-item>
                <el-form-item label="时间格式" prop="zjmzxfzhlDatetime"><el-date-picker v-model="temp.zjmzxfzhlDatetime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"></el-date-picker></el-form-item>
                <el-form-item label="所属机构ID" prop="orgId"><el-input v-model="temp.orgId"/></el-form-item>
                <el-form-item label="参数1" prop="zjmzxfzhlDbparam1"><el-input v-model="temp.zjmzxfzhlDbparam1"/></el-form-item>
                <el-form-item label="参数2" prop="zjmzxfzhlDbparam2"><el-input v-model="temp.zjmzxfzhlDbparam2"/></el-form-item>
                <el-form-item label="等于" prop="filterOperatorEq"><el-input v-model="temp.filterOperatorEq"/></el-form-item>
                <el-form-item label="不等于" prop="filterOperatorNe"><el-input v-model="temp.filterOperatorNe"/></el-form-item>
                <el-form-item label="小于" prop="filterOperatorLt"><el-input v-model="temp.filterOperatorLt"/></el-form-item>
                <el-form-item label="小于等于" prop="filterOperatorLe"><el-input v-model="temp.filterOperatorLe"/></el-form-item>
                <el-form-item label="大于" prop="filterOperatorGt"><el-input v-model="temp.filterOperatorGt"/></el-form-item>
                <el-form-item label="大于等于" prop="filterOperatorGe"><el-input v-model="temp.filterOperatorGe"/></el-form-item>
                <el-form-item label="IN" prop="filterOperatorIn"><el-input v-model="temp.filterOperatorIn"/></el-form-item>
                <el-form-item label="BETWEEN" prop="filterOperatorBetween"><el-input v-model="temp.filterOperatorBetween"/></el-form-item>
                <el-form-item label="模糊" prop="filterOperatorLike"><el-input v-model="temp.filterOperatorLike"/></el-form-item>
                <el-form-item label="左模糊" prop="filterOperatorLikeLeft"><el-input v-model="temp.filterOperatorLikeLeft"/></el-form-item>
                <el-form-item label="右模糊" prop="filterOperatorLikeRight"><el-input v-model="temp.filterOperatorLikeRight"/></el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button icon="el-icon-close" @click="dialogFormVisible = false">取消</el-button>
                <el-button v-if="dialogStatus!=='view'" icon="el-icon-check" type="primary" @click="dialogStatus==='create'?createData():updateData()">确定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import Pagination from '@/components/Pagination'
    import {getAction, putAction, postAction, deleteAction} from '@/api/manage'
    import {Message} from 'element-ui'

    export default {
        name: 'DemoZjmzxfzhl',
        components: {Pagination},
        data() {
            return {
                dicts: [],
                records: null,
                selectedRecords: [],
                total: 0,
                listQuery: {
                    current: 1,
                    size: 10,
                    zjmzxfzhlName: undefined,
                    zjmzxfzhlCodeInfo: undefined,
                    filterOperatorEq: undefined,
                    filterOperatorNe: undefined,
                    filterOperatorLt: undefined,
                    filterOperatorLe: undefined,
                    filterOperatorGt: undefined,
                    filterOperatorGe: undefined,
                    filterOperatorIn: undefined,
                    filterOperatorBetween: undefined,
                    filterOperatorLike: undefined,
                    filterOperatorLikeLeft: undefined,
                    filterOperatorLikeRight: undefined
                },
                dialogFormVisible: false,
                dialogStatus: '',
                temp: {
                    zjmzxfzhlId: undefined,
                    zjmzxfzhlName: '',
                    zjmzxfzhlCodeInfo: '',
                    zjmzxfzhlDate: '',
                    zjmzxfzhlDatetime: '',
                    orgId: '',
                    zjmzxfzhlDbparam1: '',
                    zjmzxfzhlDbparam2: '',
                    filterOperatorEq: '',
                    filterOperatorNe: '',
                    filterOperatorLt: '',
                    filterOperatorLe: '',
                    filterOperatorGt: '',
                    filterOperatorGe: '',
                    filterOperatorIn: '',
                    filterOperatorBetween: '',
                    filterOperatorLike: '',
                    filterOperatorLikeLeft: '',
                    filterOperatorLikeRight: ''
                },
                rules: {
                    zjmzxfzhlId: [{required: true, message: '该项不能为空', trigger: 'change'}],
                    zjmzxfzhlName: [{required: true, message: '该项不能为空', trigger: 'change'}]
                }
            }
        },
        beforeCreate(){
            this.getDicts('yesOrNo').then(({data}) => {this.dicts = data})
        },
        created() {
            this.list()
        },
        methods: {
            list() {
                getAction('/demo/zjmzxfzhl/list', this.listQuery).then(res => {
                    const {data} = res
                    this.records = data.records;
                    this.total = data.total
                })
            },
            btnQuery() {
                this.listQuery.current = 1
                this.list()
            },
            btnReset() {
                this.listQuery = {
                    current: 1,
                    size: 10,
                    zjmzxfzhlName: undefined,
                    zjmzxfzhlCodeInfo: undefined,
                    filterOperatorEq: undefined,
                    filterOperatorNe: undefined,
                    filterOperatorLt: undefined,
                    filterOperatorLe: undefined,
                    filterOperatorGt: undefined,
                    filterOperatorGe: undefined,
                    filterOperatorIn: undefined,
                    filterOperatorBetween: undefined,
                    filterOperatorLike: undefined,
                    filterOperatorLikeLeft: undefined,
                    filterOperatorLikeRight: undefined
                }
                this.list()
            },
            resetTemp() {
                this.temp = {
                    zjmzxfzhlId: undefined,
                    zjmzxfzhlName: '',
                    zjmzxfzhlCodeInfo: '',
                    zjmzxfzhlDate: '',
                    zjmzxfzhlDatetime: '',
                    orgId: '',
                    zjmzxfzhlDbparam1: '',
                    zjmzxfzhlDbparam2: '',
                    filterOperatorEq: '',
                    filterOperatorNe: '',
                    filterOperatorLt: '',
                    filterOperatorLe: '',
                    filterOperatorGt: '',
                    filterOperatorGe: '',
                    filterOperatorIn: '',
                    filterOperatorBetween: '',
                    filterOperatorLike: '',
                    filterOperatorLikeLeft: '',
                    filterOperatorLikeRight: ''
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
                        postAction('/demo/zjmzxfzhl/save', this.temp).then(({msg}) => {
                            this.dialogFormVisible = false
                            Message.success(msg)
                            this.list()
                        })
                    }
                })
            },
            btnUpdate(row) {
                this.temp = Object.assign({}, row)
                this.dialogStatus = 'update'
                this.dialogFormVisible = true
                this.$nextTick(() => {
                    this.$refs['dataForm'].clearValidate()
                })
            },
            updateData() {
                this.$refs['dataForm'].validate((valid) => {
                    if (valid) {
                        putAction('/demo/zjmzxfzhl/update', this.temp).then(({msg}) => {
                            this.dialogFormVisible = false
                            Message.success(msg)
                            this.list()
                        })
                    }
                })
            },
            btnDelete(id) {
                let ids = id ? [id] : this.selectedRecords.map(record => {
                    return record.zjmzxfzhlId
                })
                if (ids.length == 0) {
                    Message.error('请选择要删除的记录')
                    return
                }
                deleteAction('/demo/zjmzxfzhl/delete', {ids: ids.toString()}).then(({msg}) => {
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
