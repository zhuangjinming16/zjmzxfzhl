<template>
    <div class="app-container">
        {{ msg }}
    </div>
</template>
<script>
    import {getAction} from '@/api/manage'
    export default {
        name: 'DemoHelloworld',//此处应与菜单表 T_SYS_MENU.ROUTE_NAME 值相同
        data() {
            return {
                msg: ""
            }
        },
        created() {
            this.helloworld()
        },
        methods: {
            helloworld() {
                getAction('/demo/helloworld/helloworld').then((res) => {
                    const {msg} = res
                    this.msg = msg;
                })
            }
        }
    }
</script>