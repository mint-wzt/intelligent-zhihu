<template>
    <div>
        <div v-if="items.length === 0">没有数据???</div>
        <el-card>
            <el-row v-if="items.length !== 0">
                <el-col>
                    <div>热榜问题</div>
                    <el-divider/>
                </el-col>
            </el-row>
            <el-row v-for="item in items"
                    :key="item.id"
            >
                <el-col>
                    <el-row>
                        <el-col :span="2">
                            <div class="HotItem-index">
                                <div class="HotItem-rank HotItem-hot">{{item.hotNums}}</div>
                            </div>
                        </el-col >
                        <el-col :span="22">
                            <div style="font-size: 18px; margin-bottom: 4px;">{{item.content}}</div>
                        </el-col>
                    </el-row>

                    <el-row style="margin-top: 12px;">
                        <el-col :offset="2">
                            <el-button type="primary" plain size="mini" @click="handleReadAll(item)">查看全文</el-button>
                            <el-button type="success" plain size="mini" @click="toAnswerQuestion(item)" >回答问题</el-button>
                            <el-button type="danger" plain size="mini" @click="followQuestion(item)" >关注问题</el-button>
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
    import qs from 'querystring'
    export default {
        name: "TheHotRank",
        data() {
            return {
                items: []
            }
        },
        computed: {
            ...mapState('user', ['user_id', 'token'])
        },
        methods: {
            getData() {
                api.article.getHotArticle(this.$http,  resp=> {
                    resp.data.data.questions.forEach(item => {
                        this.items.push({
                            ...item,
                            content: item.content.substr(0,100),
                        });
                    })
                })
            },
            handleReadAll(question) {
                this.$router.push({
                    name: 'question_detail',
                    params: {
                        question: question,
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
            followQuestion(item) {
                api.question.followQuestion(
                    this.$http,
                    qs.stringify({
                        userId: this.user_id,
                        questionId: item.id,
                    }),
                    resp => {
                        if (resp.status === 200) {
                            this.$message.success('关注问题成功')
                        } else  {
                            this.$message.error('网络错误')
                        }
                    }
                )
            }
        },
        mounted() {
            this.getData()
        }
    }
</script>

<style scoped>
.HotItem-index {
    text-align: center;
    width: 57px;
}

.HotItem-hot {
    line-height: 1.6;
    font-size: 18px;
    color: #999;
    font-weight: 600;
    font-synthesis: style;
}

.HotItem-rank {
    color: #ff9607;
}
</style>