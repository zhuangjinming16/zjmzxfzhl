<template>
    <div class="navbar">
        <hamburger :is-active="sidebar.opened" class="hamburger-container" @toggleClick="toggleSideBar"/>

        <breadcrumb class="breadcrumb-container"/>

        <div class="right-menu">
            <div class="right-menu-item hover-effect">
                {{name}}
            </div>
            <switch-roles class="right-menu-item hover-effect"/>
            <template v-if="device!=='mobile'">
                <screenfull id="screenfull" class="right-menu-item hover-effect" />
                <size-select id="size-select" class="right-menu-item hover-effect"/>
            </template>

            <el-dropdown class="avatar-container right-menu-item hover-effect" trigger="click">
                <div class="avatar-wrapper">
                    <img :src="avatar+'?imageView2/1/w/80/h/80'" class="user-avatar">
                    <i class="el-icon-caret-bottom"/>
                </div>
                <el-dropdown-menu slot="dropdown" class="user-dropdown">
                    <router-link to="/">
                        <el-dropdown-item>
                            首页
                        </el-dropdown-item>
                    </router-link>
                    <el-dropdown-item>
                        <span style="display:block;" @click="updatePasswordHandle">修改密码</span>
                    </el-dropdown-item>
                    <el-dropdown-item>
                        <span style="display:block;" @click="logout">注销</span>
                    </el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </div>
        <!-- 弹窗, 修改密码 -->
        <update-password v-if="updatePassowrdVisible" ref="updatePassowrd"></update-password>
    </div>
</template>

<script>
    import {mapGetters} from 'vuex'
    import Breadcrumb from '@/components/Breadcrumb'
    import Hamburger from '@/components/Hamburger'
    import SwitchRoles from '@/components/SwitchRoles'
    import Screenfull from '@/components/Screenfull'
    import SizeSelect from '@/components/SizeSelect'
    import UpdatePassword from '@/components/UpdatePassword'

    export default {
        components: {
            Breadcrumb,
            Hamburger,
            SwitchRoles,
            Screenfull,
            SizeSelect,
            UpdatePassword
        },
        data() {
            return {
                updatePassowrdVisible: false
            }
        },
        computed: {
            ...mapGetters([
                'sidebar',
                'avatar',
                'device',
                'name'
            ])
        },
        methods: {
            toggleSideBar() {
                this.$store.dispatch('app/toggleSideBar')
            },
            async logout() {
                await this.$store.dispatch('user/logout')
                this.$router.push(`/login?redirect=${this.$route.fullPath}`)
            },
            updatePasswordHandle() {
                this.updatePassowrdVisible = true
                this.$nextTick(() => {
                    this.$refs.updatePassowrd.init()
                })
            }
        }
    }
</script>

<style lang="scss" scoped>
    .navbar {
        height: 50px;
        overflow: hidden;
        position: relative;
        background: #fff;
        box-shadow: 0 1px 4px rgba(0, 21, 41, .08);

        .hamburger-container {
            line-height: 46px;
            height: 100%;
            float: left;
            cursor: pointer;
            transition: background .3s;
            -webkit-tap-highlight-color: transparent;

            &:hover {
                background: rgba(0, 0, 0, .025)
            }
        }

        .breadcrumb-container {
            float: left;
        }

        .right-menu {
            float: right;
            height: 100%;
            line-height: 50px;

            &:focus {
                outline: none;
            }

            .right-menu-item {
                display: inline-block;
                padding: 0 8px;
                height: 100%;
                font-size: 18px;
                color: #5a5e66;
                vertical-align: text-bottom;

                &.hover-effect {
                    cursor: pointer;
                    transition: background .3s;

                    &:hover {
                        background: rgba(0, 0, 0, .025)
                    }
                }
            }

            .avatar-container {
                margin-right: 30px;

                .avatar-wrapper {
                    margin-top: 5px;
                    position: relative;

                    .user-avatar {
                        cursor: pointer;
                        width: 40px;
                        height: 40px;
                        border-radius: 10px;
                    }

                    .el-icon-caret-bottom {
                        cursor: pointer;
                        position: absolute;
                        right: -20px;
                        top: 25px;
                        font-size: 12px;
                    }
                }
            }
        }
    }
</style>
