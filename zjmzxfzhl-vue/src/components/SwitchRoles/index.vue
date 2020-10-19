<template>
    <section v-if="roles.length>1">
        <el-select v-model="roleId" @change="changeRoles" style="width: 100%;">
            <el-option v-for="item in roles" :key="item.roleId" :label="item.roleName" :value="item.roleId"></el-option>
        </el-select>
    </section>
</template>

<script>
    export default {
        data() {
            return {
                roleId: this.$store.getters.sysRole.roleId
            }
        },
        computed: {
            roles() {
                return this.$store.getters.sysRoles
            }
        },
        methods: {
            changeRoles(roleId) {
                if (roleId === this.$store.getters.sysRole.roleId) {
                    return
                }
                this.$store.dispatch('user/changeRoles', {roleId})
            }
        }
    }
</script>
