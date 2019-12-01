<template>
    <div>
        <el-row :gutter="6" class="toolbar">
            <el-col>
                <el-button @click="addNewLine">增加话题</el-button>
            </el-col>
        </el-row>

        <el-row :gutter="6">
            <el-col>
                <data-tables
                        :data="tableData"
                        :pagination-props="{ pageSizes: [5, 10, 15] }"
                >
                    <el-table-column v-for="title in titles" :prop="title.prop" :label="title.label" :key="title.label">
                    </el-table-column>
                </data-tables>
            </el-col>
        </el-row>

        <el-dialog :visible.sync="showDialog">
            <el-form :model="form">
                <el-form-item label="话题名称">
                    <el-input v-model="form.name"></el-input>
                </el-form-item>
                <el-form-item label="话题简介">
                    <el-input v-model="form.introduction"
                              type="textarea"
                              :rows="4"
                    ></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="showDialog = false">取 消</el-button>
                <el-button type="primary" @click="updateTopic">确 定</el-button>
            </div>
        </el-dialog>

    </div>
</template>

<script>
    import {mapState} from 'vuex'
    import qs from 'querystring'
    import api from '@/api'

    export default {
        name: "TheAdminTopics",
        computed: {
            ...mapState('user', ['user_id'])
        },
        data() {
            return {
                showDialog: false,
                form: {
                    id: '',
                    name: '话题名称',
                    introduction: '话题简介',
                    isNew: false,
                },
                tableData: [],
                titles: [
                    {prop: 'id', label: '话题id'},
                    {prop: 'name', label: '话题名称'},
                    {prop: 'introduction', label: '话题简介'},
                    {prop: 'followNums', label: '关注数'},
                    {prop: 'discussNums', label: '讨论数'},
                ],
                topicId_: null,
                name_: null,
                // actionCol: {
                //     label: '操作',
                //     props: {
                //         align: 'center',
                //     },
                //     buttons: [{
                //         props: {
                //           type: 'danger',
                //           icon: 'el-icon-delete'
                //         },
                //         handler: row => {
                //             this.$confirm('确认删除该话题？', '警告', {
                //                 confirmButtonText: '确认删除',
                //                 cancelButtonText: '取消',
                //                 type: 'warning',
                //             }).then(() => {
                //
                //                 this.tableData.splice(this.tableData.indexOf(row), 1)
                //             });
                //         },
                //         label: 'delete'
                //     }]
                // }
            }
        },
        methods: {
            addNewLine() {
                this.form.name = '';
                this.form.introduction = '';
                this.showDialog = true;
            },
            updateTopic() {
                api.admin.topic.addTopic(this.$http,
                    qs.stringify({
                        name: this.form.name,
                        introduction: this.form.introduction,
                        create_uid: this.user_id,
                    })
                    , resp => {
                        if (resp.status === 200) {
                            this.showDialog = false;
                            this.$message({message: '添加成功'})
                        }
                    }
                );
            }
        },
        mounted() {
            api.admin.topic.getTopics(this.$http, null, resp => {
                const topics = resp.data.data.topics;
                this.tableData.splice(this.tableData.length, 0, ...topics);
            });
        }
    }
</script>

<style scoped>
    .toolbar {
        margin-bottom: 1rem;
    }
</style>