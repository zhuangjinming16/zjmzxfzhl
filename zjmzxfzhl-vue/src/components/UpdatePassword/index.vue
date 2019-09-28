<template>
    <el-dialog
            title="修改密码"
            :visible.sync="visible"
            :append-to-body="true">
        <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
                 label-width="80px">
            <el-form-item label="用户名">
                <span>{{ userId }}</span>
            </el-form-item>
            <el-form-item label="原密码" prop="password">
                <el-input type="password" v-model="dataForm.password"></el-input>
            </el-form-item>
            <el-form-item label="新密码" prop="newPassword">
                <el-input type="password" v-model="dataForm.newPassword"></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="confirmPassword">
                <el-input type="password" v-model="dataForm.confirmPassword"></el-input>
            </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
      <el-button icon="el-icon-close" @click="visible = false">取消</el-button>
      <el-button icon="el-icon-check" type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
    </el-dialog>
</template>

<script>
    import {postAction} from "@/api/manage";
    import {Message} from 'element-ui'

    export default {
        data() {
            var validateConfirmPassword = (rule, value, callback) => {
                if (this.dataForm.newPassword !== value) {
                    callback(new Error('确认密码与新密码不一致'))
                } else {
                    callback()
                }
            }
            return {
                visible: false,
                dataForm: {
                    password: '',
                    newPassword: '',
                    confirmPassword: ''
                },
                dataRule: {
                    password: [
                        {required: true, message: '原密码不能为空', trigger: 'blur'}
                    ],
                    newPassword: [
                        {required: true, message: '新密码不能为空', trigger: 'blur'}
                    ],
                    confirmPassword: [
                        {required: true, message: '确认密码不能为空', trigger: 'blur'},
                        {validator: validateConfirmPassword, trigger: 'blur'}
                    ]
                }
            }
        },
        computed: {
            userId() {
                if(this.$store.getters.sysUser){
                    return this.$store.getters.sysUser.userId
                }else{
                    return ''
                }
            }
        },
        methods: {
            // 初始化
            init() {
                this.visible = true
                this.dataForm = {
                    password: '',
                    newPassword: '',
                    confirmPassword: ''
                }
            },
            // 表单提交
            dataFormSubmit() {
                this.$refs['dataForm'].validate((valid) => {
                    if (valid) {
                        postAction('/sys/user/updatePassword', this.dataForm).then(({msg}) => {
                            Message.success(msg)
                            this.visible = false
                            this.$store.dispatch('user/clearLoginInfo').then(() => {
                                this.$router.push(`/login?redirect=${this.$route.fullPath}`)
                            })
                        })
                    }
                })
            }
        }
    }
</script>

