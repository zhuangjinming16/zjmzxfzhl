<template>
    <div class="login-container">
        <el-form ref="loginForm" size="medium" :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on"
                 label-position="left">

            <div class="title-container">
                <h3 class="title">登录界面</h3>
            </div>

            <el-form-item prop="username">
                <span class="svg-container">
                  <svg-icon icon-class="user"/>
                </span>
                <el-input size="medium"
                        ref="username"
                        v-model="loginForm.username"
                        placeholder="用户名"
                        name="username"
                        type="text"
                        tabindex="1"
                        auto-complete="on"
                />
            </el-form-item>

            <el-form-item prop="password">
                <span class="svg-container">
                  <svg-icon icon-class="password"/>
                </span>
                <el-input size="medium"
                        :key="passwordType"
                        ref="password"
                        v-model="loginForm.password"
                        :type="passwordType"
                        placeholder="密码"
                        name="password"
                        tabindex="2"
                        auto-complete="on"
                        @keyup.enter.native="handleLogin"
                />
                <span class="show-pwd" @click="showPwd">
                  <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'"/>
                </span>
            </el-form-item>

            <el-form-item prop="captcha">
                <span class="svg-container">
                  <svg-icon icon-class="password"/>
                </span>
                <el-input size="medium" v-model="loginForm.captcha" placeholder="验证码" style="width: 200px;" @keyup.enter.native="handleLogin">
                </el-input>
                <span class="show-captcha">
                    <img :src="captchaPath" @click="getCaptcha()" alt="">
                </span>
            </el-form-item>

            <el-button size="medium" :loading="loading" type="primary" style="width:100%;margin-bottom:30px;"
                       @click.native.prevent="handleLogin">登录
            </el-button>
        </el-form>
    </div>
</template>

<script>
    import { getUUID } from '@/utils'
    import {getAction} from '@/api/manage'

    export default {
        name: 'Login',
        data() {
            const validateUserId = (rule, value, callback) => {
                if (value.length==0) {
                    callback(new Error('请输入用户名'))
                } else {
                    callback()
                }
            }
            const validatePassword = (rule, value, callback) => {
                if (value.length==0) {
                    callback(new Error('请输入密码'))
                } else {
                    callback()
                }
            }
            const validateCaptcha = (rule, value, callback) => {
                if (value.length==0) {
                    callback(new Error('请输入验证码'))
                } else {
                    callback()
                }
            }
            return {
                captchaPath: '',
                loginForm: {
                    username: 'admin',
                    password: '1',
                    uuid: '',
                    captcha: ''
                },
                loginRules: {
                    username: [{required: true, trigger: 'blur', validator: validateUserId}],
                    password: [{required: true, trigger: 'blur', validator: validatePassword}],
                    captcha: [{required: true, trigger: 'blur', validator: validateCaptcha}]
                },
                loading: false,
                passwordType: 'password',
                redirect: undefined
            }
        },
        watch: {
            $route: {
                handler: function (route) {
                    this.redirect = route.query && route.query.redirect
                },
                immediate: true
            }
        },
        created () {
            this.getCaptcha()
        },
        methods: {
            showPwd() {
                if (this.passwordType === 'password') {
                    this.passwordType = ''
                } else {
                    this.passwordType = 'password'
                }
                this.$nextTick(() => {
                    this.$refs.password.focus()
                })
            },
            handleLogin() {
                this.$refs.loginForm.validate(valid => {
                    if (valid) {
                        this.loading = true
                        this.$store.dispatch('user/login', this.loginForm).then(() => {
                            this.$router.push({ path: this.redirect || '/' })
                            // this.$router.push({path: '/'})
                            this.loading = false
                        }).catch(() => {
                            this.loading = false
                        })
                    } else {
                        console.log('error submit!!')
                        return false
                    }
                })
            },
            // 获取验证码
            getCaptcha () {
                this.loginForm.uuid = getUUID()
                this.captchaPath = `${process.env.VUE_APP_BASE_API}/captcha.jpg?uuid=${this.loginForm.uuid}`
            }
        }
    }
</script>

<style lang="scss">
    /* 修复input 背景不协调 和光标变色 */
    /* Detail see https://github.com/PanJiaChen/vue-element-admin/pull/927 */

    $bg: #283443;
    $light_gray: #fff;
    $cursor: #fff;

    @supports (-webkit-mask: none) and (not (cater-color: $cursor)) {
        .login-container .el-input input {
            color: $cursor;
        }
    }

    /* reset element-ui css */
    .login-container {
        .el-input {
            display: inline-block;
            height: 47px;
            width: 85%;

            input {
                background: transparent;
                border: 0px;
                -webkit-appearance: none;
                border-radius: 0px;
                padding: 12px 5px 12px 15px;
                color: $light_gray;
                height: 47px;
                caret-color: $cursor;

                &:-webkit-autofill {
                    box-shadow: 0 0 0px 1000px $bg inset !important;
                    -webkit-text-fill-color: $cursor !important;
                }
            }
        }

        .el-form-item {
            border: 1px solid rgba(255, 255, 255, 0.1);
            background: rgba(0, 0, 0, 0.1);
            border-radius: 5px;
            color: #454545;
        }
    }
</style>

<style lang="scss" scoped>
    $bg: #2d3a4b;
    $dark_gray: #889aa4;
    $light_gray: #eee;

    .login-container {
        min-height: 100%;
        width: 100%;
        background-color: $bg;
        overflow: hidden;

        .login-form {
            position: relative;
            width: 520px;
            max-width: 100%;
            padding: 100px 35px 0;
            margin: 0 auto;
            overflow: hidden;
        }

        .tips {
            font-size: 14px;
            color: #fff;
            margin-bottom: 10px;

            span {
                &:first-of-type {
                    margin-right: 16px;
                }
            }
        }

        .svg-container {
            padding: 6px 5px 6px 15px;
            color: $dark_gray;
            vertical-align: middle;
            width: 30px;
            display: inline-block;
        }

        .title-container {
            position: relative;

            .title {
                font-size: 26px;
                color: $light_gray;
                margin: 0px auto 40px auto;
                text-align: center;
                font-weight: bold;
            }
        }

        .show-pwd {
            position: absolute;
            right: 10px;
            top: 7px;
            font-size: 16px;
            color: $dark_gray;
            cursor: pointer;
            user-select: none;
        }

        .show-captcha {
            position: absolute;
            //top: 1px;
            right: 1px;
            font-size: 16px;
            color: $dark_gray;
            cursor: pointer;
            user-select: none;
        }
    }
</style>
