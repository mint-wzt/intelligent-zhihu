<template>
    <div>
        <el-row :gutter="6">
            <el-col>
                <data-tables
                        :action-col="actionCol"
                        :data="tableData"
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
                tableData:[],
                titles: [
                    {prop: 'id', label: '用户id'},
                    {prop: 'username', label: '用户名'},
                    {prop: 'answers', label: '回答数'},
                    {prop: 'articles', label: '发表的文章数'},
                    {prop: 'followers', label: '关注的人数'},
                    {prop: 'fans', label: '粉丝数'},
                    {prop: 'topics', label: '关注话题数'},
                    {prop: 'followQuestions', label: '关注问题数'},
                    {prop: 'questions', label: '提出问题数'},
                ],
                actionCol: {
                    label: '操作',
                    buttons: [
                        {
                            props: {
                                type: 'primary',
                                disabled: this.isRoot === false,
                            },
                            handler: row => {
                                this.$message(`row: ${JSON.stringify(row)}`)
                            },
                            label: '管理员设置'
                        }
                    ]
                }
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
                    this.tableData.splice(this.tableData.length, 0, ...resp.data.data.users);
                })
            }
        },
        created() {
            this.getAllUser();
        }
    }
</script>

<style scoped>

</style>