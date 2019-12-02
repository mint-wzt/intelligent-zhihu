<template>
    <div>
        <the-home-nav/>
        <el-row >
            <el-col :span="18" :offset="3" >
                <el-row >
                    <el-col >
                        <h1>{{title}}</h1>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col>
                        <h3>作者：<span>{{author}}</span></h3>
                    </el-col>
                </el-row>

                <mavon-editor
                        defaultOpen="preview"
                        :editable="false"
                        :toolbarsFlag="false"
                        :tabSize="4"
                        v-model="content"
                        :subfield="false"
                        :toolbars="{}"
                />

                <el-row class="bottomToolbar">
                    <el-col>
                        <el-button ref="article_thumb_button" type="primary" @click="thumbIt" :disabled="thumbBtn" icon="el-icon-thumb" round><span style="color: deepskyblue; margin-right: .25rem;">{{thumbs}}</span>{{thumbText}}</el-button>
                        <el-button type="success" round>添加评论</el-button>
                    </el-col>
                </el-row>

                <el-row>
                    <el-col>

                    </el-col>
                </el-row>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    import TheHomeNav from '@/components/TheHomeNav.vue'
    import {mapState} from 'vuex'
    import api from "@/api";
    import qs from 'querystring'

    export default {
        name: "TheArticleDetailPage",
        components: {
            TheHomeNav
        },
        props: {
            id: {
                type: String,
            },
        },
        data() {
            return {
                title: '',
                content: '',
                author: '',
                author_id: null,
                thumbs: 0,
                thumbText: '点赞',
                thumbBtn: false,
            }
        },
        computed: {
            ...mapState('user', {
                user_id: state => state.user_id,
                token: state => state.token,
            })
        },
        methods: {
            thumbIt() {
                api.article.thumbIt(
                    this.$http,
                    qs.stringify({
                        userId: this.user_id,
                        articleId: this.id,
                    }),
                    resp => {
                        if (resp.status === 200) {
                            this.thumbs += 1;
                            this.thumbText = '已赞'
                        }
                    }
                )
            },
            getUserInfo(callback) {
                this.$http.get('/user/users/'+this.user_id,
                    {
                        headers: {
                            "Authorization": this.token,
                        }
                    }
                ).then(resp => {
                    if (resp.status === 200) {
                        callback(resp)
                    }
                })
            },
            getArticleById() {
                api.article.getArticleById(
                    this.$http,
                    {
                        params: {
                            articleId: this.id,
                        }
                    },
                    resp => {
                        if (resp.status === 200) {
                            const {
                                title,
                                content,
                                author,
                                author_id,
                                thumbs
                            } = resp.data.data.articleInfo;
                            this.title = title;
                            this.content = content;
                            this.author = author;
                            this.author_id = author_id;
                            this.thumbs = thumbs;
                        }
                    }
                );
                api.article.theThumbsHasMe(
                    this.$http,
                    qs.stringify({
                        userId: this.user_id,
                        articleId: this.id,
                    }),
                    resp => {
                        const data = resp.data.data;
                        if (data.hasme) {
                            this.thumbText = '已赞';
                            this.thumbBtn = true;
                        }
                    }
                );
            }
        },
        mounted() {
            this.getArticleById();
        }

    }
</script>

<style scoped>
.bottomToolbar {
    margin-top: 1rem;
    margin-bottom: 10rem;
}
</style>