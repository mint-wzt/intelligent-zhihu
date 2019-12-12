<template>
    <div>
        <the-home-nav/>
        <div class="md-layout md-alignment-top-center " style="margin-top: 5rem;">
            <div class="md-layout-item md-size-55 content-margin">
                <md-card>
                    <md-card-header>
                        <md-tabs md-sync-route>
                            <md-tab exact id="tab-recommend" md-label="推荐" :to="{name:'recommend'}"/>
                            <md-tab id="tab-attention" md-label="关注" :to="{name: 'attention'}"/>
                            <md-tab id="tab-hotrank" md-label="热榜" :to="{name: 'hotrank'}"/>
                        </md-tabs>
                    </md-card-header>
                    <md-card-content>
                        <router-view/>
                    </md-card-content>
                </md-card>

            </div>
            <div class="md-layout-item md-size-30">
                <md-card class="">
                    <md-card-header class="md-layout">
                        <div class="md-layout-item">
                            <md-button :to="{name:'questions_list'}">
                                <md-icon>description</md-icon>
                                写回答
                            </md-button>
                        </div>
                        <div class="md-layout-item">
                            <md-button :to="{name:'article'}">
                                <md-icon>edit</md-icon>
                                写文章
                            </md-button>
                        </div>
                        <div class="md-layout-item">
                            <md-button :to="{name:'question_edit'}">
                                <md-icon>wb_incandescent</md-icon>
                                问问题
                            </md-button>
                        </div>
                    </md-card-header>

                    <el-divider/>
                    <md-card-content>
                        <el-row type="flex" justify="space-between" style="margin: 20px;">
                            <el-col :span="18">
                                <div>
                                    <md-icon style="margin-right: 5px;">star</md-icon>
                                    <span>我的文章</span></div>
                            </el-col>
                            <el-col :span="6">
                                <el-link href="#/person/me/">{{myArticleCount}}</el-link>
                            </el-col>
                        </el-row>
                        <el-row type="flex" justify="space-between" style="margin: 20px;">
                            <el-col :span="18">
                                <div>
                                    <md-icon style="margin-right: 5px;">assignment</md-icon>
                                    <span>我的收藏</span></div>
                            </el-col>
                            <el-col :span="6">
                                <el-link href="#/favorite">{{myFavoriteCount}}</el-link>
                            </el-col>
                        </el-row>

                        <el-row type="flex" justify="space-between" style="margin: 20px;">
                            <el-col :span="18">
                                <div>
                                    <md-icon style="margin-right: 5px;">info</md-icon>
                                    <span>我的回答</span></div>
                            </el-col>
                            <el-col :span="6">
                                <el-link href="#/answer">{{myAnswerCount}}</el-link>
                            </el-col>
                        </el-row>
                    </md-card-content>
                </md-card>
            </div>
        </div>

    </div>
</template>

<script>
    import api from '@/api'
    import TheHomeNav from '@/components/TheHomeNav.vue'
    import {mapState} from 'vuex'
    export default {
        name: "TheHomePage",
        components: {
            TheHomeNav
        },
        computed: {
            ...mapState('user', ['user_id'])
        },
        data() {
            return {
                myArticleCount: 0,
                myFavoriteCount: 0,
                myAnswerCount: 0,
            }
        },
        methods: {
            getMyArticleCount() {
                api.article.getArticleByUserId(
                    this.$http,
                    {
                        userId: this.user_id,
                    },
                    resp => {
                        if (resp.status === 200) {
                            const articles = resp.data.data.articles;
                            articles.forEach(item => {
                                if (item.questionId != null && item.questionId.trim() !== "") {
                                    this.myAnswerCount += 1;
                                } else {
                                    this.myArticleCount += 1;
                                }
                            })
                        }
                    }
                )
            },
            getFavoriteCount() {
                api.article.getFavoriteArticles(
                    this.$http,
                    {
                        userId: this.user_id,
                    },
                    resp => {
                        if (resp.status === 200) {
                            this.myFavoriteCount = resp.data.data.articles.length;
                        }
                    }
                )
            }
        },
        mounted() {
            this.getMyArticleCount();
            this.getFavoriteCount();
        }
    }
</script>

<style scoped>
.content-margin {
    margin-right: 1rem;
}
</style>