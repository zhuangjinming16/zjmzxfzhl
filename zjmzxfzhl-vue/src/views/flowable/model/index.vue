<template>
    <div class="app-container">
        <div class="filter-container">
            <el-input v-model="listQuery.modelKey" placeholder="模型key" style="width: 200px;" class="filter-item"
                      @keyup.enter.native="btnQuery"/>
            <el-input v-model="listQuery.modelName" placeholder="模型名称" style="width: 200px;" class="filter-item"
                      @keyup.enter.native="btnQuery"/>
            <el-select v-model="listQuery.modelCategory" placeholder="模型类别" class="filter-item">
                <el-option v-for="(item, index) in dicts.processCategory" :key="index" :label="item.content"
                           :value="item.value"></el-option>
            </el-select>

            <el-dropdown split-button type="primary" @click="btnQuery" class="filter-item">
                <i class="el-icon-search el-icon--left"></i>查询
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item icon="el-icon-zoom-out" @click.native="btnReset">重置</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
            <el-button-group>
                <el-button v-permission="'flowable:model:save'" icon="el-icon-plus" type="primary" @click="btnCreate"
                           class="filter-item">新增
                </el-button>
                <el-button v-permission="'flowable:model:import'" icon="el-icon-plus" type="primary" @click="btnImport"
                           class="filter-item">导入
                </el-button>
                <!--<el-button v-permission="'flowable:model:delete'" icon="el-icon-delete" @click="btnDelete()"
                           class="filter-item">批量删除
                </el-button>-->
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
            <el-table-column label="模型key" prop="key" align="center">
                <template slot-scope="scope"><span>{{ scope.row.key }}</span></template>
            </el-table-column>
            <el-table-column label="模型名称" prop="name" align="center">
                <template slot-scope="scope"><span>{{ scope.row.name }}</span></template>
            </el-table-column>
            <el-table-column label="模型类别" prop="category" align="center">
                <template slot-scope="scope">
                    <span v-html="formatDictText(dicts.processCategory,scope.row.category)"></span>
                </template>
            </el-table-column>
            <el-table-column label="版本" prop="version" align="center">
                <template slot-scope="scope"><span>{{ scope.row.version }}</span></template>
            </el-table-column>
            <el-table-column label="是否发布" prop="deployed" align="center">
                <template slot-scope="scope"><span>{{ scope.row.deployed?'是':'否' }}</span></template>
            </el-table-column>
            <el-table-column label="操作" align="center">
                <template slot-scope="{row}">
                    <el-dropdown>
                        <span class="el-dropdown-link">操作<i class="el-icon-arrow-down el-icon--right"></i></span>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item icon="el-icon-view" @click.native="btnView(row)">查看</el-dropdown-item>
                            <el-dropdown-item v-permission="'flowable:model:update'" icon="el-icon-edit" divided
                                              @click.native="btnUpdate(row)">修改
                            </el-dropdown-item>
                            <el-dropdown-item v-permission="'flowable:model:saveModelEditor'" icon="el-icon-edit"
                                              divided
                                              @click.native="btnUpdateModel(row)">流程设计
                            </el-dropdown-item>
                            <el-dropdown-item v-permission="'flowable:model:delete'" icon="el-icon-delete" divided
                                              @click.native="btnDelete(row.id)">删除
                            </el-dropdown-item>
                            <el-dropdown-item v-permission="'flowable:model:delete'" icon="el-icon-delete" divided
                                              @click.native="btnDelete(row.id,true)">删除包含历史
                            </el-dropdown-item>
                            <el-dropdown-item v-permission="'flowable:model:deploy'" icon="el-icon-edit" divided
                                              @click.native="btnDeploy(row.id)">部署
                            </el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </template>
            </el-table-column>
        </el-table>
        <pagination v-show="total>0" :total="total" :current.sync="listQuery.current" :size.sync="listQuery.size"
                    @pagination="list"/>

        <el-dialog title="流程模型" :visible.sync="dialogFormVisible">
            <el-form ref="dataForm" :rules="rules" :model="temp" :disabled="dialogStatus==='view'"
                     label-position="right" label-width="110px">
                <el-form-item label="模型key" prop="key">
                    <el-input v-model="temp.key"/>
                </el-form-item>
                <el-form-item label="模型名称" prop="name">
                    <el-input v-model="temp.name"/>
                </el-form-item>
                <el-form-item label="模型类别" prop="category">
                    <el-select v-model="temp.category" placeholder="模型类别">
                        <el-option v-for="item in dicts.processCategory" :key="item.value" :label="item.content"
                                   :value="item.value"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="模型描述" prop="description">
                    <el-input v-model="temp.description"/>
                </el-form-item>
                <el-form-item label="租户ID" prop="tenantId">
                    <el-input v-model="temp.tenantId"/>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button icon="el-icon-close" @click="dialogFormVisible = false">取消</el-button>
                <el-button v-if="dialogStatus!=='view'" icon="el-icon-check" type="primary"
                           @click="dialogStatus==='create'?createData():updateData()">确定
                </el-button>
            </div>
        </el-dialog>

        <el-dialog title="流程导入" :visible.sync="dialogImportVisible">
            <!--<div class="filter-container">
                <el-input v-model="importTenantId" placeholder="租户" clearable style="width: 200px;"/>
            </div>-->
            <el-upload
                    class="upload-demo"
                    drag
                    action
                    :show-file-list="false"
                    :before-upload="beforeUpload"
                    :http-request="doImport"
            >
                <i class="el-icon-upload"></i>
                <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                <div class="el-upload__tip" slot="tip">只能上传.bpmn20.xml,.bpmn文件，且不超过512K</div>
            </el-upload>
        </el-dialog>
    </div>
</template>

<script>
    import Pagination from '@/components/Pagination'
    import {deleteAction, getAction, postAction, putAction} from '@/api/manage'
    import {Message} from 'element-ui'

    export default {
        name: 'FlowableModel',
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
                    modelKey: undefined,
                    modelName: undefined,
                    modelCategory: undefined
                },
                dialogFormVisible: false,
                dialogStatus: '',
                temp: {
                    id: undefined,
                    key: '',
                    name: '',
                    category: '',
                    description: '',
                    tenantId: ''
                },
                rules: {
                    id: [{required: true, message: '该项不能为空', trigger: 'change'}],
                    key: [{required: true, message: '该项不能为空', trigger: 'change'}],
                    name: [{required: true, message: '该项不能为空', trigger: 'change'}]
                },
                dialogImportVisible: false
            }
        },
        beforeCreate() {
            this.getDicts('processCategory,taskCategory').then(({data}) => {
                this.dicts = data
            })
        },
        created() {
            this.list()
        },
        methods: {
            list() {
                getAction('/flowable/model/list', this.listQuery).then(res => {
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
                    modelKey: undefined,
                    modelName: undefined,
                    modelCategory: undefined
                }
                this.list()
            },
            resetTemp() {
                this.temp = {
                    id: undefined,
                    key: '',
                    name: '',
                    category: '',
                    description: '',
                    tenantId: ''
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
            btnImport() {
                this.dialogImportVisible = true;
            },
            beforeUpload(file) {
                // 上传前格式与大小校验
                const fileName = file.name
                const isFileTypeOk = fileName.endsWith('.bpmn20.xml') || fileName.endsWith('.bpmn')
                const isLt512 = file.size / 1024 / 512 < 1;
                if (!isFileTypeOk) {
                    Message.error("上传文件格式不正确");
                }
                if (!isLt512) {
                    Message.error("上传文件大小不能超过512K");
                }
                return isFileTypeOk && isLt512;
            },
            doImport(fileObj) {
                let formData = new FormData();
                formData.set("file", fileObj.file);
                // formData.set("tenantId", this.importTenantId);
                postAction('/flowable/model/import', formData).then(({msg}) => {
                    this.dialogImportVisible = false
                    Message.success(msg)
                    this.list();
                })
            },
            createData() {
                this.$refs['dataForm'].validate((valid) => {
                    if (valid) {
                        postAction('/flowable/model/save', this.temp).then(({msg}) => {
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
            btnUpdateModel(row) {
                this.$router.push({path: '/flowableModelEdit', query: {id: row.id}})
            },
            updateData() {
                this.$refs['dataForm'].validate((valid) => {
                    if (valid) {
                        putAction('/flowable/model/update', this.temp).then(({msg}) => {
                            this.dialogFormVisible = false
                            Message.success(msg)
                            this.list()
                        })
                    }
                })
            },
            btnDelete(id, cascade) {
                let ids = id ? [id] : this.selectedRecords.map(record => {
                    return record.id
                })
                if (ids.length == 0) {
                    Message.error('请选择要删除的记录')
                    return
                }
                deleteAction('/flowable/model/delete', {ids: ids.toString(), cascade}).then(({msg}) => {
                    Message.success(msg)
                    this.list()
                })
            },
            btnDeploy(id) {
                postAction('/flowable/model/deploy', {id}).then(({msg}) => {
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
