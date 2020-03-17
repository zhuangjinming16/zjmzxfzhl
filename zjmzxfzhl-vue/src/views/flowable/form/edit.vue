<template>
    <fm-making-form ref="makingFrom" style="height:500px;" preview generate-json >
        <template slot="action">
            <el-button v-permission="'flowable:form:save,flowable:form:update'" type="text" icon="el-icon-upload" @click="btnSave">保存</el-button>
        </template>
    </fm-making-form>
</template>
<script>
    import {getAction,putAction} from '@/api/manage'
    import {Message} from 'element-ui'

    export default {
        name: 'FlowableFormEdit',
        data() {
            return {
                formKey: undefined,
                formData:{
                    formKey: undefined,
                    formName: '',
                    formJson: ''
                },
                defaultJson:{ "list": [], "config": { "labelWidth": 100, "labelPosition": "right", "size": "small" } }
            }
        },
        created() {
            if(this.$route.query && this.$route.query.formKey){
                this.formKey = this.$route.query.formKey
            }
            this.getFormData()
        },
        methods: {
            getFormData(){
                if(!this.formKey){
                    Message.error('formKey is null')
                    return
                }
                getAction('/flowable/form/queryById', {id:this.formKey}).then(({data}) => {
                    this.formData = data
                    if(this.formData && this.formData.formJson){
                        setTimeout(() => this.$refs.makingFrom.setJSON(JSON.parse(this.formData.formJson)), 100)
                    }else{
                        setTimeout(() => this.$refs.makingFrom.setJSON(this.defaultJson), 100)
                    }
                })
            },
            btnSave(){
                this.formData.formJson = JSON.stringify(this.$refs.makingFrom.getJSON())
                putAction('/flowable/form/update', this.formData).then(({msg,data}) => {
                    Message.success(msg)
                })
            }
        }
    }
</script>