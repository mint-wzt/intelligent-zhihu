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
                                    <el-link href="#/question/detail/0" class="title">
                                        {{item.title}}
                                    </el-link>
                                </el-col>
                                <el-col :span="4">
                                    <el-button type="primary" @click="showDialogForm(item)">写回答</el-button>
                                </el-col>
                            </el-row>
                            <el-row>
                                <el-col>
                                    <div class="info">{{item.total}}个回答 ● {{item.follower}}人关注</div>
                                </el-col>
                            </el-row>
                            <el-divider/>
                        </div>

                    </el-tab-pane>
                </el-tabs>
            </el-col>
        </el-row>

        <el-dialog :visible.sync="showDialog">
            <el-form v-model="answerForm">
                <el-form-item>
                    <label>你的见解</label>
                    <el-input type="textarea" :rows="4" v-model="answerForm.content"/>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="addAnswer(curItem)">发布</el-button>
                    <el-button type="info" @click="showDialog = false">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>

<script>
    import TheHomeNav from '@/components/TheHomeNav.vue'
    import {mapState} from 'vuex'
    import format from 'date-fns/format'

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
                showDialog: false,
                answerForm: {
                    content: '',
                },
                activeName: 'hot',
                curItem: null,
                items: [
                    {
                        id: '0',
                        title: '如何评价基于go的前端库vugu？',
                        content: 'https://www.vugu.org/',
                        total: 4,
                        follower: 65,
                        tags: ['前端开发', 'JavaScript', 'Go 语言', 'Vue.js'],
                    },
                    {
                        id: '1',
                        title: '客观公正的讲大鹏教育的课程有用吗？大鹏教育学完之后能够到达那种程度？',
                        content: '',
                        total: 9,
                        follower: 22,
                        tags: ['前端开发', 'JavaScript', 'Go 语言', 'Vue.js'],
                    },
                    {
                        id: '2',
                        title: '如何看待医学期刊《柳叶刀》揭露中国人主要死因？',
                        content: '',
                        total: 90,
                        follower: 565,
                        tags: ['前端开发', 'JavaScript', 'Go 语言', 'Vue.js'],
                    },
                    {
                        id: '3',
                        title: '古希腊的循环时间观是如何转变为现代的线性时间观的呢？',
                        content: '',
                        total: 7,
                        follower: 654,
                        tags: ['前端开发', 'JavaScript', 'Go 语言', 'Vue.js'],
                    },
                    {
                        id: '4',
                        title: '2014 年初至今人民币持续贬值背后的动因？',
                        content: '',
                        total: 20,
                        follower: 265,
                        tags: ['前端开发', 'JavaScript', 'Go 语言', 'Vue.js'],
                    },
                ]
            }
        },
        methods: {
            showDialogForm(item) {
                this.curItem = item;
                this.showDialog = true;
            },
            addAnswer(question) {
                this.$router.push({
                    name: 'question_detail',
                    params: {
                        id: question.id,
                        question: question,
                        answer: {
                            user: {
                                nickname: this.nickname,
                            },
                            content: this.answerForm.content,
                            createAt: format(new Date(), 'yyyy-MM-dd'),
                            agree: 0,
                            toggle: false,
                            agreeText: '赞同',
                        }
                    }
                })
            }
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