<template>
    <div>
        <div v-if="items.length === 0">没有数据???</div>
        <el-card>
            <el-row v-for="item in items"
                :key="item.id"
            >
                <el-col>
                    <el-row>
                        <el-col>
                            <div style="font-size: 18px; margin-bottom: 4px;">{{item.title}}</div>
                        </el-col>
                    </el-row>

                    <el-row>
                        <el-col>
                            <div style="margin-bottom: 4px; font-size: 16px">{{item.author}}</div>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col>
                            <div style="font-size: 14px; margin-bottom: 4px;">{{item.content}}</div>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col>
                            <el-button type="primary" plain size="mini" @click="handleReadAll(item)">查看全文</el-button>
                        </el-col>
                    </el-row>
                    <el-divider/>
                </el-col>

            </el-row>
        </el-card>
    </div>
</template>

<script>
    import api from '@/api'
    import {mapState} from 'vuex'
    export default {
        name: "TheRecommend",
        components: {
        },
        data() {
            return {
                items: []
            }
        },
        computed: {
            ...mapState('user', ['user_id', 'token'])
        },
        methods: {
            handleReadAll(item) {
                this.$router.push({
                    name: 'article_detail',
                    params: {
                        id: item.id,
                    }
                })
            },
            getRecommendArticle() {
                api.article.getRecommendArticle(this.$http, /*this.user_id,*/ resp=> {
                    resp.data.data.articles.forEach(item => {
                        this.items.push({
                            ...item,
                            content: item.content.substr(0,100),
                        });
                    })
                })
            }
        },
        mounted() {
            this.getRecommendArticle()
        }
    }
</script>

<style scoped>
    .card-margin {
        margin-bottom: 1rem;
    }
</style>