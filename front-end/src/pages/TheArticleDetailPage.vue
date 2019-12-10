<template>
    <div>
        <the-home-nav/>
        <el-row>
            <el-col :span="18" :offset="3">
                <el-row>
                    <el-col>
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
                        <el-button ref="article_thumb_button" type="primary" @click="thumbIt" :disabled="thumbBtn"
                                   icon="el-icon-thumb" round><span style="color: deepskyblue; margin-right: .25rem;">{{thumbs}}</span>{{thumbText}}
                        </el-button>
                        <el-button type="success" round @click="showCommentDialog = true">添加评论</el-button>
                        <el-button type="warning" round :icon="this.hasFavoriteIt_.icon" @click="handleFavoriteArticle">
                            {{this.hasFavoriteIt_.text}}
                        </el-button>

                    </el-col>
                </el-row>

                <el-dialog :visible.sync="showCommentDialog">
                    <el-form>
                        <el-form-item>
                            <label>评论内容</label>
                            <el-input type="textarea" v-model="commentForm.content"/>
                        </el-form-item>
                        <el-form-item>
                            <el-button @click="addComment">发布</el-button>
                            <el-button @click="showCommentDialog = false">取消</el-button>
                        </el-form-item>
                    </el-form>
                </el-dialog>

                <el-row>
                    <el-col>
                        <el-card v-if="comments.length !== 0">
                            <el-row type="flex" justify="center">
                                <el-col :span="5">
                                    <div style="font-size: 16px;">评论({{comments.length}}条)</div>
                                </el-col>
                            </el-row>
                            <div v-for="comment in comments" :key="comment.nickname">
                                <el-row type="flex" justify="space-between">
                                    <el-col :span="18">
                                        <div class="commentNickname">{{comment.nickName}}</div>
                                    </el-col>
                                    <el-col :span="6">
                                        <div style="font-size: 12px;">{{comment.createDate}}</div>
                                    </el-col>
                                </el-row>
                                <el-row>
                                    <el-col>
                                        <div class="commentContent">{{comment.content}}</div>
                                    </el-col>
                                </el-row>
                                <el-divider/>
                            </div>
                        </el-card>
                    </el-col>
                </el-row>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    import format from 'date-fns/format'
    import TheHomeNav from '@/components/TheHomeNav.vue'
    import {mapState} from 'vuex'
    import api from "@/api";
    import qs from 'querystring';


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
                showCommentDialog: false,
                comments: [],
                commentForm: {
                    content: '',
                },
                hasFavoriteIt_: {
                    has: false,
                    icon: '',
                    text: '',
                },
            }
        },
        computed: {
            ...mapState('user', {
                user_id: state => state.user_id,
                token: state => state.token,
            }),

        },
        methods: {
            handleFavoriteArticle() {
                if (this.hasFavoriteIt_.has) {
                    api.article.removeFavoriteArticle(
                        this.$http,
                        {
                            userId: this.user_id,
                            articleId: this.id,
                        },
                        resp => {
                            if (resp.status === 200) {
                                this.$message.success('取消收藏');
                                this.hasFavoriteIt_.has = false;
                                this.hasFavoriteIt_.text = '收藏';
                                this.hasFavoriteIt_.icon = 'el-icon-star-off';
                            }
                        }
                    )
                } else {
                    api.article.addFavoriteArticle(
                        this.$http,
                        qs.stringify({
                            userId: this.user_id,
                            articleId: this.id,
                        }),
                        resp => {
                            if (resp.status === 200) {
                                this.$message.success('收藏文章');
                                this.hasFavoriteIt_.has = true;
                                this.hasFavoriteIt_.text = '已收藏';
                                this.hasFavoriteIt_.icon = 'el-icon-star-on'
                            }
                        }
                    )
                }
            },
            hasFavoriteIt() {
                api.article.hasFavoriteIt(
                    this.$http,
                    qs.stringify({
                        userId: this.user_id,
                        articleId: this.id,
                    }),
                    resp => {
                        if (resp.status === 200) {
                            const has = resp.data.data.hasMe;
                            this.hasFavoriteIt_.has = has;
                            if (has) {
                                this.hasFavoriteIt_.text = '已收藏';
                                this.hasFavoriteIt_.icon = 'el-icon-star-on'
                            } else {
                                this.hasFavoriteIt_.text = '收藏';
                                this.hasFavoriteIt_.icon = 'el-icon-star-off'
                            }
                        }
                    }
                )
            },
            getAllComment() {
                api.article.getArticleCommentByArticleId(
                    this.$http,
                    {
                        articleId: this.id,
                    },
                    resp => {
                        if (resp.status === 200) {
                            const data = resp.data.data;
                            data.comments.forEach(item => {
                                this.comments.push(item);
                            })
                        }
                    }
                )
            },
            dataFormat(data) {
                return data.split('.')[0]
            },
            addComment() {
                api.article.addComment(
                    this.$http,
                    qs.stringify({
                        articleId: this.id,
                        userId: this.user_id,
                        nickName: this.nickname,
                        content: this.commentForm.content,
                        commentPid: this.id,
                    }),
                    resp => {
                        if (resp.status === 200) {
                            const data = resp.data.data;
                            this.comments.splice(0, 0, {
                                ...data,
                                createDate: format(data.create_at,"yyyy-MM-dd hh:mm:ss"),
                                nickName: data.nickname,
                            });
                            this.showCommentDialog = false;
                            this.commentForm.content = '';
                            this.$message.success('添加成功')
                        } else {
                            this.$message.error(JSON.stringify(resp));
                        }

                    }
                )
            },
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
                this.$http.get('/user/users/' + this.user_id,
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
            this.getAllComment();
            this.hasFavoriteIt();
        }

    }
</script>

<style scoped>
    .bottomToolbar {
        margin-top: 1rem;
        margin-bottom: 1rem;
    }

    .commentNickname {
        margin-left: 12px;
        margin-bottom: 12px;
        font-size: 16px;
    }

    .commentContent {
        margin-left: 20px;
        font-size: 13px;
    }
</style>