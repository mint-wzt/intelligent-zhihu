<template>
    <div>
        <the-home-nav/>
        <el-row type="flex" justify="center" style="margin-top: 5rem;">
            <el-col :span="18">
                <el-tabs type="border-card" v-model="activeName">
                    <el-tab-pane label="热门问题" name="hot">
                        <div v-for="item in items" :key="item.id">
                            <el-row>
                                <el-col :span="20">
                                    <el-link @click="handleNavigate(item)" class="title">
                                        {{item.content}}
                                    </el-link>
                                </el-col>
                                <el-col :span="4">
                                    <el-button type="primary" @click="toAnswerQuestion(item)">写回答</el-button>
                                </el-col>
                            </el-row>
                            <el-row>
                                <el-col>
                                    <div class="info">{{item.answerNums}}回答 ● {{item.followNums}}人关注</div>
                                </el-col>
                            </el-row>
                            <el-divider/>
                        </div>

                    </el-tab-pane>
                </el-tabs>
            </el-col>
        </el-row>

    </div>
</template>

<script>
    import TheHomeNav from '@/components/TheHomeNav.vue'
    import {mapState} from 'vuex'
    import api from '@/api'

    export default {
        name: "TheQuestionListPage",
        components: {
            TheHomeNav,
        },
        computed: {
            ...mapState('user', {
                nickname: state => state.nickname,
            })
        },
        data() {
            return {
                activeName: 'hot',
                items: []
            }
        },
        methods: {
            getData() {
                api.article.getHotArticle(this.$http,  resp=> {
                    resp.data.data.questions.forEach(item => {
                        this.items.push({
                            ...item
                        });
                    })
                })
            },
            handleNavigate(item) {
                this.$router.push({
                    name: 'question_detail',
                    params: {
                        question: item
                    }
                })
            },
            toAnswerQuestion(question) {
                this.$router.push({
                    name: "question_answer_edit",
                    params: {
                        question: question
                    }
                })
            },
        },
        mounted() {
            this.getData();
        }
    }
</script>

<style scoped>
    .title {
        color: #4fc3f7;
        margin-bottom: 10px;
        font-size: 16px;
    }

    .info {
        color: #bdbdbd;
        font-size: 13px;
    }
</style>