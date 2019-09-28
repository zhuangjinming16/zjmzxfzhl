<template>
    <el-breadcrumb class="app-breadcrumb" separator="/">
        <transition-group name="breadcrumb">
            <el-breadcrumb-item v-for="(item,index) in levelList" :key="item.path">
                <span v-if="item.name!='dashboard'&&(item.redirect==undefined||item.redirect==null||item.redirect===''||index==levelList.length-1)"
                      class="no-redirect">{{ item.meta.title }}</span>
                <a v-else @click.prevent="handleLink(item)">{{ item.meta.title }}</a>
            </el-breadcrumb-item>
        </transition-group>
    </el-breadcrumb>
</template>

<script>
    import pathToRegexp from 'path-to-regexp'

    export default {
        data() {
            return {
                levelList: null
            }
        },
        watch: {
            $route() {
                this.getBreadcrumb()
            }
        },
        created() {
            this.getBreadcrumb()
        },
        methods: {
            getBreadcrumb() {
                let matched = this.$route.matched.filter(item => item.meta && item.meta.title && item.meta.breadcrumb !== false)
                const first = matched[0]
                const firstLevel = {path: '/dashboard', name: 'dashboard', meta: {title: '首页'}}
                if (!this.isDashboard(first)) {
                    matched = [firstLevel].concat(matched)
                } else {
                    matched = [firstLevel]
                }
                this.levelList = matched
            },
            isDashboard(route) {
                const name = route && route.name
                if (!name) {
                    return false
                }
                return name.trim().toLocaleLowerCase() === 'Dashboard'.toLocaleLowerCase()
            },
            pathCompile(path) {
                //  https://github.com/PanJiaChen/vue-element-admin/issues/561
                const {params} = this.$route
                var toPath = pathToRegexp.compile(path)
                return toPath(params)
            },
            handleLink(item) {
                const {redirect, path} = item
                if (redirect) {
                    this.$router.push(redirect)
                    return
                }
                this.$router.push(this.pathCompile(path))
            }
        }
    }
</script>

<style lang="scss" scoped>
    .app-breadcrumb.el-breadcrumb {
        display: inline-block;
        font-size: 14px;
        line-height: 50px;
        margin-left: 8px;

        .no-redirect {
            color: #97a8be;
            cursor: text;
        }
    }
</style>
