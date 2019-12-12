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
<!--                    <el-row>-->
<!--                        <el-col :offset="2" :span="22">-->
<!--                            <div style="font-size: 14px; margin-top: 10px;">{{question.content}}</div>-->
<!--                        </el-col>-->
<!--                    </el-row>-->

                    <el-row style="margin-top: 10px; margin-bottom: 10px;">
                        <el-col :offset="2" :span="18">
                            <el-button type="primary" @click="handleFollowQuestion">关注问题</el-button>
                        </el-col>
                    </el-row>
                </el-card>
            </el-col>
        </el-row>
        <el-row type="flex" justify="center" style="margin-top: 10px;">
            <el-col :span="20">
                <el-card style="padding: 10px;">
                    <el-row>
                        <el-col>
                            <div>
                                {{items.length}}个回答
                            </div>
                            <el-divider/>
                        </el-col>
                    </el-row>

                    <div v-for="item in items"
                         :key="item.content">
                        <el-row>
                            <el-col :offset="1">
                                <div
                                        style="font-weight: 600; font-size: 15px;line-height: 1.1;margin-bottom: 4px;"
                                >
                                    {{item.author}}
                                </div>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col>
                                <div style="font-size: 14px; color: #8590a6;margin-bottom: 4px;">{{item.thumbs}}人赞同了该答案
                                </div>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col>
                                <div style="font-size: 15px; color: #1a1a1a; line-height: 1.6; margin-bottom: 4px;">
                                    {{item.content}}
                                </div>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col>
                                <div style="font-size: 14px; color: #8590a6;margin-bottom: 4px;">发布于{{item.createDate}}
                                </div>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-button type="primary" plain size="mini" >
                                {{item.thumbs}}
                                {{item.agreeText}}
                            </el-button>
                        </el-row>
                        <el-divider/>
                    </div>

                </el-card>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    import TheHomeNav from '@/components/TheHomeNav.vue'
    import api from '@/api';
    import {mapState} from 'vuex'
    import qs from 'querystring'

    export default {
        name: "TheQuestionDetailPage",
        components: {
            TheHomeNav,
        },
        props: {
            question: {
                type: Object,
                required: true,
            },
        },
        computed: {
            ...mapState('user', {
                user_id: state => state.user_id,
            }),
            tags() {
                return this.question.type.split(',')
            },
        },
        data() {
            return {
                items: [],
            }
        },
        methods: {
            handleFollowQuestion() {
                api.question.followQuestion(
                    this.$http,
                    qs.stringify({
                        userId: this.user_id,
                        questionId: this.question.id,
                    }),
                    resp => {
                        if (resp.status === 200) {
                            this.$message.success('关注问题成功')
                        } else  {
                            this.$message.error('网络错误')
                        }
                    }
                )
            },
            // handleThumb(item) {
            // },
            search() {
                api.article.seach(this.$http,
                    {
                        keyWord: this.question.content,
                    },
                    resp => {
                        if (resp.status === 200) {
                            const data = resp.data.data;
                            if (data.articles != null && data.articles.length !== 0) {
                                data.articles.forEach(item => {
                                    this.items.push({
                                        ...item,
                                        agreeText : '点赞',
                                    })
                                })
                            } else {
                                this.$message.info("暂无回答");
                            }
                        }

                    }
                )
            },
        },
        mounted() {
            this.search();
        }
    }
</script>

<style scoped>

</style>