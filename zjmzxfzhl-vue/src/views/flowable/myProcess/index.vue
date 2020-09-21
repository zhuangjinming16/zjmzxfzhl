<template>
    <div class="app-container">
        <div class="filter-container">
            <div class="filter-container">
                <el-checkbox v-model="listQuery.startedByMe" @change="list">我发起的</el-checkbox>
                <el-checkbox v-model="listQuery.ccToMe" @change="list">抄送我</el-checkbox>
                <el-checkbox v-model="listQuery.unfinished" @change="list">未办结</el-checkbox>
                <el-checkbox v-model="listQuery.finished" @change="list">已办结</el-checkbox>
            </div>
        </div>
        <el-row>
            <el-col :span="12" v-for="row in myInvolvedRecords">
                <div class="filter-container" style="margin-right: 3px;margin-bottom: 3px;">
                    <el-card>
                        <div slot="header" class="clearfix">
                            <router-link
                                    :to="{path:'/myFlowable/myProcessQuery', query: {processDefinitionCategory:row.category,startedByMe:listQuery.startedByMe,ccToMe:listQuery.ccToMe,unfinished:listQuery.unfinished,finished:listQuery.finished}}">
                                <el-link type="primary">
                                    {{row.categoryName}}
                                </el-link>
                            </router-link>
                        </div>

                        <div style="height: 110px;padding-left: 15px">
                            <el-scrollbar style="height: 100%">
                                <router-link v-for="(item, index) in row.processDefinitionVoList"
                                             :key="item.processDefinitionKey"
                                             :to="{path:'/myFlowable/myProcessQuery', query: {processDefinitionKey:item.processDefinitionKey,startedByMe:listQuery.startedByMe,ccToMe:listQuery.ccToMe,unfinished:listQuery.unfinished,finished:listQuery.finished}}">
                                    <el-link type="info" style="padding-bottom: 5px">
                                        {{item.processDefinitionName}}({{item.count}})
                                    </el-link>
                                    <br/>
                                </router-link>
                            </el-scrollbar>
                        </div>
                    </el-card>
                </div>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    import {getAction, putAction, postAction, deleteAction} from '@/api/manage'
    import {getToken} from '@/utils/auth'

    export default {
        name: 'FlowableMyProcess',
        data() {
            return {
                dicts: [],
                myInvolvedRecords: null,
                myInvolvedTotal: 0,
                listQuery: {
                    startedByMe: false,
                    ccToMe: false,
                    unfinished: false,
                    finished: false
                },
                processInstanceId: '',
                dialogViewVisible: false
            }
        },
        created() {
            this.list()
        },
        methods: {
            list() {
                getAction('/flowable/processInstance/listMyInvolvedSummary', this.listQuery).then(res => {
                    const {data} = res
                    this.myInvolvedRecords = data;
                })
            }
        }
    }
</script>
<style lang="scss">
    .el-scrollbar__wrap {
        overflow-x: hidden;
    }
</style>