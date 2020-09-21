<template>
    <div class="app-container">
        <div class="filter-container">
            <el-checkbox v-model="listQuery.latestVersion">最新版本</el-checkbox>
            <el-checkbox v-model="listQuery.suspended">挂起的</el-checkbox>
            <el-checkbox v-model="listQuery.active">激活的</el-checkbox>
        </div>
        <div class="filter-container">
            <el-input v-model="listQuery.processDefinitionId" placeholder="流程定义ID" style="width: 200px;" class="filter-item"
                      @keyup.enter.native="btnQuery"/>
            <el-input v-model="listQuery.processDefinitionName" placeholder="流程定义名称" style="width: 200px;" class="filter-item"
                      @keyup.enter.native="btnQuery"/>
            <el-input v-model="listQuery.processDefinitionKey" placeholder="流程定义KEY" style="width: 200px;" class="filter-item"
                      @keyup.enter.native="btnQuery"/>
            <el-dropdown split-button type="primary" @click="btnQuery" class="filter-item">
                <i class="el-icon-search el-icon--left"></i>查询
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item icon="el-icon-zoom-out" @click.native="btnReset">重置</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
            <el-button-group>
                <el-button v-permission="'flowable:processDefinition:import'" icon="el-icon-plus" type="primary" @click="btnImport" class="filter-item">导入</el-button>
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
            <el-table-column label="流程定义名称" prop="name" align="center">
                <template slot-scope="scope"><span>{{ scope.row.name }}</span></template>
            </el-table-column>
            <el-table-column label="流程定义KEY" prop="key" align="center">
                <template slot-scope="scope"><span>{{ scope.row.key }}</span></template>
            </el-table-column>
            <el-table-column label="流程定义分类" prop="category" align="center">
                <template slot-scope="scope"><span>{{ scope.row.category }}</span></template>
            </el-table-column>
            <el-table-column label="版本" prop="version" align="center">
                <template slot-scope="scope"><span>{{ scope.row.version }}</span></template>
            </el-table-column>
            <el-table-column label="状态" prop="suspended" align="center">
                <template slot-scope="scope"><span>{{ scope.row.suspended?'挂起':'激活' }}</span></template>
            </el-table-column>
            <el-table-column label="表单key" prop="formKey" align="center">
                <template slot-scope="scope"><span>{{ scope.row.formKey }}</span></template>
            </el-table-column>
            <el-table-column label="操作" align="center">
                <template slot-scope="{row}">
                    <el-dropdown>
                        <span class="el-dropdown-link">操作<i class="el-icon-arrow-down el-icon--right"></i></span>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item icon="el-icon-view" @click.native="btnView(row)">查看
                            </el-dropdown-item>
                            <el-dropdown-item icon="el-icon-view" divided @click.native="btnImage(row.id)">流程图
                            </el-dropdown-item>
                            <el-dropdown-item v-permission="'flowable:processDefinitionIdentityLink:list'" icon="el-icon-edit" divided @click.native="btnProcessDefinitionIdentityLink(row)">流程授权
                            </el-dropdown-item>
                            <el-dropdown-item v-permission="'flowable:processDefinition:xml'" icon="el-icon-edit" divided @click.native="btnExport(row)">导出
                            </el-dropdown-item>
                            <el-dropdown-item v-permission="'flowable:processDefinition:suspendOrActivate'" icon="el-icon-edit" divided @click.native="btnSuspendOrActivate(row.id,row.suspended)">{{row.suspended?'激活':'挂起' }}
                            </el-dropdown-item>
                            <el-dropdown-item v-permission="'flowable:processDefinition:delete'" icon="el-icon-delete" divided @click.native="btnDelete(row.id)">删除
                            </el-dropdown-item>
                            <el-dropdown-item v-permission="'flowable:processDefinition:delete'" icon="el-icon-delete" divided @click.native="btnDelete(row.id,true)">删除包含实例
                            </el-dropdown-item>
                            <el-dropdown-item v-permission="'flowable:processInstance:list'" icon="el-icon-view" divided @click.native="btnViewProcessInstanceList(row)">实例列表
                            </el-dropdown-item>
                            <!--<el-dropdown-item icon="el-icon-edit" divided @click.native="btnStartInstance(row)">发起流程
                            </el-dropdown-item>-->
                        </el-dropdown-menu>
                    </el-dropdown>
                </template>
            </el-table-column>
        </el-table>
        <pagination v-show="total>0" :total="total" :current.sync="listQuery.current" :size.sync="listQuery.size"
                    @pagination="list"/>

        <el-dialog title="流程定义" :visible.sync="dialogFormVisible">
            <el-form ref="dataForm" :model="temp" disabled label-position="right" label-width="110px">
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="流程定义ID" prop="id">
                            <el-input v-model="temp.id"/>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="流程定义名称" prop="name">
                            <el-input v-model="temp.name"/>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="流程定义KEY" prop="key">
                            <el-input v-model="temp.key"/>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="流程定义分类" prop="category">
                            <el-input v-model="temp.category"/>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="流程定义描述" prop="description">
                            <el-input v-model="temp.description"/>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="版本" prop="version">
                            <el-input v-model="temp.version"/>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="状态" prop="suspended">
                            <el-input v-model="temp.suspended?'挂起':'激活'"/>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="表单key" prop="formKey">
                            <el-input v-model="temp.formKey"/>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button icon="el-icon-close" @click="dialogFormVisible = false">取消</el-button>
            </div>
        </el-dialog>
        <el-dialog title="流程定义导入" :visible.sync="dialogImportVisible">
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
                <div class="el-upload__tip" slot="tip">只能上传.bpmn20.xml,.bpmn,.bar,.zip文件，且不超过512K</div>
            </el-upload>
        </el-dialog>

        <process-image v-if="dialogProcessImageVisible" :visible.sync="dialogProcessImageVisible"
                       :processDefinitionId="selectedProcessDefinitionId"></process-image>

        <process-definition-identity-link v-if="dialogProcessDefinitionIdentityLinkVisible"
                                          :visible.sync="dialogProcessDefinitionIdentityLinkVisible"
                                          :processDefinitionId="selectedProcessDefinitionId"
                                          :processDefinitionName="selectedProcessDefinitionName"></process-definition-identity-link>
    </div>
</template>

<script>
    import Pagination from '@/components/Pagination'
    import {getAction, putAction, postAction, deleteAction, downloadAction} from '@/api/manage'
    import {Message} from 'element-ui'
    import {getToken} from '@/utils/auth'
    import ProcessImage from "../components/ProcessImage";
    import ProcessDefinitionIdentityLink from "./ProcessDefinitionIdentityLink";

    export default {
        name: 'FlowableProcessDefinition',
        components: {ProcessImage, ProcessDefinitionIdentityLink, Pagination},
        data() {
            return {
                dicts: [],
                records: null,
                selectedRecords: [],
                total: 0,
                listQuery: {
                    current: 1,
                    size: 10,
                    processDefinitionId: undefined,
                    processDefinitionName: undefined,
                    processDefinitionKey: undefined,
                    latestVersion: false,
                    suspended: false,
                    active: false
                },
                dialogFormVisible: false,
                dialogImportVisible: false,
                // importTenantId: '',
                dialogProcessImageVisible: false,
                imagePath: '',
                // formJson: {"list":[{"type":"input","icon":"icon-input","options":{"width":"100%","defaultValue":"","required":false,"dataType":"string","pattern":"","placeholder":"","customClass":"","disabled":false,"labelWidth":100,"isLabelWidth":false,"hidden":false,"dataBind":true,"showPassword":false,"remoteFunc":"func_1575897887618","remoteOption":"option_1575897887618"},"name":"名称","key":"1575897887618","model":"name","rules":[{"type":"string","message":"名称格式不正确"}]}],"config":{"labelWidth":100,"labelPosition":"right","size":"small","customClass":""}},
                formJson: undefined,
                temp: {
                    id: undefined,
                    name: '',
                    key: '',
                    category: '',
                    description: '',
                    version: '',
                    latestVersion: '',
                    suspended: '',
                    formKey: '',
                    tenantId: ''
                },
                dialogProcessDefinitionIdentityLinkVisible: false,
                selectedProcessDefinitionId: '',
                selectedProcessDefinitionName: ''
            }
        },
        created() {
            this.list()
        },
        methods: {
            list() {
                getAction('/flowable/processDefinition/list', this.listQuery).then(res => {
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
                    processDefinitionId: undefined,
                    processDefinitionName: undefined,
                    processDefinitionKey: undefined,
                    latestVersion: false,
                    suspended: false,
                    active: false
                }
                this.list()
            },
            btnView(row) {
                this.temp = Object.assign({}, row)
                this.dialogFormVisible = true
            },
            btnViewProcessInstanceList(row) {
                this.$router.push({path: '/flowable/processInstance', query: {processDefinitionId: row.id}})
            },
            btnDelete(id, cascade) {
                let ids = id ? [id] : this.selectedRecords.map(record => {
                    return record.id
                })
                if (ids.length == 0) {
                    Message.error('请选择要删除的记录')
                    return
                }
                if (ids.length > 1) {
                    Message.error('只能选择一条记录')
                    return
                }
                deleteAction('/flowable/processDefinition/delete', {
                    processDefinitionId: ids.toString(),
                    cascade: cascade
                }).then(({msg}) => {
                    Message.success(msg)
                    this.list()
                })
            },
            btnSuspendOrActivate(processDefinitionId, suspend) {
                let suspendOrActivate = suspend ? 'activate' : 'suspend'
                putAction('/flowable/processDefinition/' + suspendOrActivate, {processDefinitionId}).then(({msg}) => {
                    Message.success(msg)
                    this.list();
                })
            },
            btnImport() {
                this.dialogImportVisible = true;
            },
            beforeUpload(file) {
                // 上传前格式与大小校验
                const fileName = file.name
                const isFileTypeOk = fileName.endsWith('.bpmn20.xml') || fileName.endsWith('.bpmn') || fileName.endsWith('.bar') || fileName.endsWith('.zip')
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
                postAction('/flowable/processDefinition/import', formData).then(({msg}) => {
                    this.dialogImportVisible = false
                    Message.success(msg)
                    this.list();
                })
            },
            btnExport(row) {
                downloadAction('/flowable/processDefinition/xml','get', {processDefinitionId: row.id}, row.name + '-v' + row.version + '.bpmn20.xml')
            },
            btnImage(processDefinitionId) {
                // this.imagePath = `${process.env.VUE_APP_BASE_API}` + '/flowable/processDefinition/image?processDefinitionId=' + processDefinitionId + '&access_token=' + getToken() + '&time=' + new Date()
                this.selectedProcessDefinitionId = processDefinitionId
                this.dialogProcessImageVisible = true
            },
            btnProcessDefinitionIdentityLink(row) {
                this.selectedProcessDefinitionId = row.id
                this.selectedProcessDefinitionName = row.name
                this.dialogProcessDefinitionIdentityLinkVisible = true;
            },
            selectionChange(selectedRecords) {
                this.selectedRecords = selectedRecords
            }
        }
    }
</script>
