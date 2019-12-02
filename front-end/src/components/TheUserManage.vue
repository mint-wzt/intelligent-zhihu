<template>
    <div>
        <el-row :gutter="6">
            <el-col>
                <h1>所有用户</h1>
            </el-col>
        </el-row>
        <el-row >
            <el-col :span="1">
                <h2>搜索</h2>
            </el-col>
            <el-col :span="6">
                <el-input v-model="filters[0].value" placeholder="用户名"/>
            </el-col>
        </el-row>
        <el-row :gutter="6">
            <el-col>
                <data-tables
                        :filters="filters"
                        :action-col="allUserActionCol"
                        :data="allUserTableData"
                        :pagination-props="{ pageSizes: [5, 10, 15] }"
                >
                    <el-table-column v-for="title in titles" :prop="title.prop" :label="title.label" :key="title.label">
                    </el-table-column>
                </data-tables>
            </el-col>
        </el-row>

        <el-row :gutter="6">
            <el-col>
                <el-dialog :visible.sync="showRoleDialog">
                    <el-form :model="roleForm">
                        <el-form-item>
                            <el-switch
                                    v-model="roleForm.role"
                                    active-text="管理员"
                                    active-value="ADMIN"
                                    inactive-value="USER"
                                    inactive-text="普通用户"
                                    active-color="#13ce66"
                                    inactive-color="#ff4949">
                            </el-switch>
                        </el-form-item>
                        <el-form-item>
                            <el-button @click="setAdmin">确定</el-button>
                            <el-button @click="showRoleDialog = false">取消</el-button>
                        </el-form-item>
                    </el-form>
                </el-dialog>
            </el-col>
        </el-row>

        <el-divider/>
        <el-row>
            <el-col>
                <h1>所有管理员</h1>
            </el-col>
        </el-row>

        <el-row :gutter="6">
            <el-col>
                <data-tables
                        :data="allAdminTableData"
                        :pagination-props="{ pageSizes: [5, 10, 15] }"
                >
                    <el-table-column v-for="title in titles" :prop="title.prop" :label="title.label" :key="title.label">
                    </el-table-column>
                </data-tables>
            </el-col>
        </el-row>

    </div>
</template>

<script>
    import api from '@/api'
    import {mapState, mapGetters} from 'vuex'
    import qs from 'querystring'
    export default {
        name: "TheUserManage",
        computed: {
            ...mapState('user', ['username']),
            ...mapGetters('user', ['isRoot'])
        },
        data () {
            return {
                allUserTableData:[],
                allAdminTableData:[],
                showRoleDialog: false,
                roleForm: {
                    role: null,
                    userId: null,
                },
                titles: [
                    {prop: 'id', label: '用户id'},
                    {prop: 'username', label: '用户名'},
                    {prop: 'roles', label: '权限'},
                    {prop: 'answers', label: '回答数'},
                    {prop: 'articles', label: '发表的文章数'},
                    {prop: 'followers', label: '关注的人数'},
                    {prop: 'fans', label: '粉丝数'},
                    {prop: 'topics', label: '关注话题数'},
                    {prop: 'followQuestions', label: '关注问题数'},
                    {prop: 'questions', label: '提出问题数'},
                ],
                allUserActionCol: {
                    label: '操作',
                    buttons: [
                        {
                            props: {
                                type: 'primary',
                                disabled: this.isRoot === false,
                            },
                            handler: row => {
                                if (row.username === this.username) {
                                    this.$message.error(`你不能设置自己的权限`)
                                } else {
                                    this.roleForm.role = row.roles.search('ROLE_ADMIN') !== -1 ? 'ADMIN' : 'USER';
                                    this.roleForm.userId = row.id;
                                    this.showRoleDialog = true;
                                }
                            },
                            label: '管理员设置'
                        }
                    ]
                },
                filters: [
                    {
                        prop: 'username',
                        value: ''
                    }
                ]
            }
        },
        methods: {
            getUserByUsername() {
                api.admin.user.getUserByUsername(
                    this.$http,
                    qs.stringify({
                        username: this.username,
                    }),
                    resp => {
                        console.log(resp);
                    }
                )
            },
            getAllUser() {
                api.admin.user.getAllUser(this.$http, null, resp => {
                    if (resp.data.data.users.length === 0) {
                        return
                    }
                    this.allUserTableData.splice(this.allUserTableData.length, 0, ...resp.data.data.users);
                })
            },
            getAllAdmin() {
                api.admin.user.getAllAdmin(this.$http, null, resp => {
                    if (resp.data.data.admins.length !== 0) {
                        this.allAdminTableData.splice(0, 0, ...resp.data.data.admins)
                    }
                })
            },
            setAdmin() {

                if (this.roleForm.role === 'ADMIN') {
                    api.admin.user.addAdminUser(
                        this.$http,
                        qs.stringify({
                            userId: this.roleForm.userId,
                            role: this.roleForm.role,
                        }),
                        resp => {
                            if (resp.status === 200) {
                                this.$message.info("设置成功")
                            }
                        }
                    )
                } else if (this.roleForm.role === 'USER'){
                    api.admin.user.removeAdminUser(
                        this.$http,
                        {
                            userId: this.roleForm.userId,
                            role: this.roleForm.role,
                        },
                        resp => {
                            if (resp.status === 200) {
                                this.$message.info("设置成功")
                            }
                        }
                    )
                }
                this.showRoleDialog = false;
            },
        },
        created() {
            this.getAllUser();
            this.getAllAdmin();
        }
    }
</script>

<style scoped>

</style>