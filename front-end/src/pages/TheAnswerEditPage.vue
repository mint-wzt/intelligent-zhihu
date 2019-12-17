<template>
    <div>
        <the-home-nav/>

        <el-row>
            <el-col>
                <el-card>
                    <el-row>
                        <el-col :offset="2" :span="22">
                            <el-tag
                                    style="margin-right: 4px;"
                                    v-for="tag in tags"
                                    :key="tag"
                                    type="success"
                                    effect="plain"
                            >{{tag}}
                            </el-tag>
                        </el-col>
                    </el-row>

                    <el-row>
                        <el-col :offset="2" :span="18">
                            <div style="font-size: 18px; margin-top: 10px;">{{question.content}}</div>
                        </el-col>
                        <el-col :span="4">
                            <el-row type="flex" justify="center">
                                <el-col :span="20" style="color: #8590a6; font-size: 14px;">关注者</el-col>
                            </el-row>
                            <el-row type="flex" justify="center">
                                <el-col :offset="2" :span="20" style="color:#1a1a1a; font-size: 18px;">
                                    {{question.followNums}}
                                </el-col>
                            </el-row>
                        </el-col>
                    </el-row>

                    <el-row style="margin-top: 10px; margin-bottom: 10px;">
                        <el-col :offset="2" :span="18">
                            <el-button type="primary" >关注问题</el-button>
                        </el-col>
                    </el-row>
                </el-card>
            </el-col>
        </el-row>

        <el-row>
            <el-col :span="20" :offset="2"
                    class="bg-class"
                    >
                <el-form :model="form" >

                    <el-form-item>
                        <el-button type="primary" style="min-width: 400px;" @click="publishAnswer">点击发表该回答</el-button>
                    </el-form-item>

                    <el-form-item>
                        <mavon-editor
                                class="mavonEditor"
                                ref="markdownEditor"
                                v-model="form.content"
                                @imgAdd="imageAdd"
                                @imgDel="imageDelete"
                        />
                    </el-form-item>
                </el-form>

            </el-col>
        </el-row>
    </div>
</template>

<script>
    import TheHomeNav from '@/components/TheHomeNav.vue';
    import api from '@/api';
    import qs from 'querystring';
    import {mapState} from 'vuex';

    export default {
        name: "TheAnswerEditPage",
        components: {TheHomeNav},
        props: {
            question: {
                type: Object,
                required: true,
            }
        },
        computed: {
            ...mapState('user', {
                authorId: state => state.user_id,
                author: state => state.username,
            }),
            tags() {
                return this.question.type.split(',')
            }
        },
        data() {
            return {
                form: {
                    content: '',
                }
            }
        },
        methods: {
            imageAdd(pos, file) {
                this.imageFiles[pos] = file;
            },
            imageDelete(pos) {
                delete this.imageFiles[pos];
            },
            publishAnswer() {
                var fd = new FormData();
                for (var img_ in this.imageFiles) {
                    fd.append('files', this.imageFiles[img_]);
                }
                fd.append('id', this.authorId);

                api.question.uploadImage(
                    this.$http,
                    fd,
                    resp => {
                        if (resp.status === 201) {
                            const {imageUrls} = resp.data.data;
                            for (var index = 0; index < imageUrls.length; index++) {
                                this.$refs.markdownEditor.$img2Url(index + 1, imageUrls[index]);
                            }
                            api.question.publishAnswer(
                                this.$http,
                                qs.stringify({
                                    authorId: this.authorId,
                                    questionId: this.question.id,
                                    type: this.question.type,
                                    author: this.author,
                                    content: this.form.content,
                                    status: 1,
                                    isArticle: 0,
                                    title: this.question.content,
                                }),
                                resp => {
                                    if (resp.status === 200) {
                                        this.$message.success('发布成功')
                                        this.$router.push({
                                            name: "article_detail",
                                            params: {
                                                id: resp.data.data.articleInfo.id,
                                            }
                                        })

                                    } else {
                                        this.$message.error('发表失败')
                                    }
                                }
                            )
                        } else {
                            this.$message.error('上传图片超时')
                        }
                    }
                )
            }
        }
    }
</script>

<style scoped>
    .bg-class {
        background-color: rgba(255,255,255, 0.9);
        padding: 12px 8px 10px;
    }
    .mavonEditor {
        width: 100%;
        height: 100%;
        min-height: 45rem;
    }
</style>