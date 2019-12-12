<template>
    <div>
        <el-row>
            <el-col>
                <data-tables
                        :data="tableData"
                        :action-col="actionCol"
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
    export default {
        name: "TheArticleManage",
        data () {
            return {
                tableData:[],
                titles: [
                    {prop: 'id', label: 'id'},
                    {prop: 'author', label: '作者'},
                    {prop: 'title', label: '标题'},
                    {prop: 'type', label: '标签'},
                    {prop: 'status', label: '文章状态'},
                    {prop: 'thumbs', label: '点赞数'},
                    {prop: 'browsedNums', label: '浏览量'},

                ],
                actionCol: {
                    label: '操作',
                    buttons: [
                        {
                            label: '查看',
                            props: {
                                type: 'primary',
                                size: 'small'
                            },
                            handler: row => {
                                this.$router.push({name:'article_detail', params: {id: row.id}});
                            }

                        },
                        {
                            props: {
                                type: 'danger',
                                size: 'small',
                            },
                            handler: row => {
                                this.$confirm('是否删除该文章？', '删除文章',{
                                    confirmButtonText: '确定',
                                    cancelButtonText: '取消',
                                    type: 'danger',
                                }).then(() => {
                                    this.delteArticleById(row);
                                    this.$message.success('文章删除成功');
                                }).catch(() => {
                                    this.$message.info('取消删除');
                                })
                            },
                            label: '删除'
                        }
                    ]
                }
            }
        },
        methods: {
            getAllArticles() {
                api.admin.article.getAllArticles(
                    this.$http,
                    resp => {
                        const data = resp.data.data;
                        data.articles.forEach(item => {
                            if (item.status === 1) {
                                item.status = '已发布';
                            } else if (item.status === 0) {
                                item.status = '草稿';
                            } else {
                                item.status = '审核不通过';
                            }
                        })
                        this.tableData.splice(0, 0, ...data.articles);
                    }
                )
            },
            delteArticleById(row) {
                api.admin.article.deleteArticleById(this.$http, {
                    articleId: row.id,
                },
                    resp => {
                        if (resp.status === 200) {
                            this.tableData.splice(this.tableData.indexOf(row), 1);
                        } else {
                            this.$message.error('网络错误，删除失败')
                        }
                    }
                )
            }
        },
        mounted() {
            this.getAllArticles()
        }
    }
</script>

<style scoped>
</style>