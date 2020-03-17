<template>
    <el-dropdown trigger="click" @command="handleSetSize">
        <div>
            <svg-icon class-name="size-icon" icon-class="size"/>
        </div>
        <el-dropdown-menu slot="dropdown">
            <el-dropdown-item v-for="item of sizeOptions" :key="item.value" :disabled="size===item.value"
                              :command="item.value">
                {{item.label }}
            </el-dropdown-item>
        </el-dropdown-menu>
    </el-dropdown>
</template>

<script>
    import {Message} from 'element-ui'

    export default {
        data() {
            return {
                sizeOptions: [
                    {label: '超小', value: 'mini'},
                    {label: '小号', value: 'small'},
                    {label: '大号', value: 'medium'}
                ]
            }
        },
        computed: {
            size() {
                return this.$store.getters.size
            }
        },
        methods: {
            handleSetSize(size) {
                this.$ELEMENT.size = size
                this.$store.dispatch('app/setSize', size)
                this.refreshView()
                Message.success('操作成功')
            },
            refreshView() {
                // In order to make the cached page re-rendered
                this.$store.dispatch('tagsView/delAllCachedViews', this.$route)

                const {fullPath} = this.$route

                this.$nextTick(() => {
                    this.$router.replace({
                        path: '/redirect' + fullPath
                    })
                })
            }
        }

    }
</script>
