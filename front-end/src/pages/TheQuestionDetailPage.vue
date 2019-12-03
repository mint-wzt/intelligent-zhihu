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
                            <div style="font-size: 18px; margin-top: 10px;">{{question.title}}</div>
                        </el-col>
                        <el-col :span="4">
                            <el-row type="flex" justify="center">
                                <el-col :span="20" style="color: #8590a6; font-size: 14px;">关注者</el-col>
                            </el-row>
                            <el-row type="flex" justify="center">
                                <el-col :offset="2" :span="20" style="color:#1a1a1a; font-size: 18px;">
                                    {{question.follower}}
                                </el-col>
                            </el-row>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :offset="2" :span="22">
                            <div style="font-size: 14px; margin-top: 10px;">{{question.content}}</div>
                        </el-col>
                    </el-row>

                    <el-row style="margin-top: 10px; margin-bottom: 10px;">
                        <el-col :offset="2" :span="18">
                            <el-button type="primary" @click="handerFollowQuestion">关注问题</el-button>
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
                                >{{item.user.nickname}}
                                </div>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col>
                                <div style="font-size: 14px; color: #8590a6;margin-bottom: 4px;">{{item.agree}}人赞同了该答案
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
                                <div style="font-size: 14px; color: #8590a6;margin-bottom: 4px;">发布于{{item.createAt}}
                                </div>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-button type="primary" plain size="mini" @click="handleThumb(item)">{{item.agree}}
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

    export default {
        name: "TheQuestionDetailPage",
        components: {
            TheHomeNav,
        },
        props: {
            id: {
                type: String,
                default: '0',
            },
            question: {
                type: Object,
                default: () => {
                    return {
                        id: 0,
                        title: '如何评价基于go的前端库vugu？',
                        content: 'https://www.vugu.org/',
                        total: 9,
                        follower: 65,
                        tags: ['前端开发', 'JavaScript', 'Go 语言', 'Vue.js'],
                    };
                }
            },
            answer: {
                type: Object,
                default: null,
            }
        },
        data() {
            return {
                tags: [],

                items: [
                    {
                        user: {
                            nickname: '爱吃蘑菇的小孩'
                        },
                        content: '把 Node 里 if (!err) 的恐惧带进前端，还是没 Promise 那种？',
                        createAt: '2019-08-04',
                        agree: 1,
                        toggle: false,
                        agreeText: '赞同',
                    },
                    {
                        user: {
                            nickname: 'Richard'
                        },
                        content: '现在写前端，除了JS，就是web assembly. 基于go, rust, c#的前端开发方案都是基于web assembly. 这些方案最大的弊端是对前端没有足够吸引力，上手难度，生态等都是问题。',
                        createAt: '2019-08-03',
                        agree: 3,
                        toggle: false,
                        agreeText: '赞同',
                    },
                    {
                        user: {
                            nickname: '不工'
                        },
                        content: '自从前端有了typescript护体，其他语言在前端这块基本没戏，带gc的语言更是一点戏都没有',
                        createAt: '2019-08-04',
                        agree: 1,
                        toggle: false,
                        agreeText: '赞同',
                    },
                ],
            }
        },
        methods: {
            handerFollowQuestion() {
                this.$message.success('已关注');
            },
            handleThumb(item) {
                if (!item.toggle) {
                    item.agree += 1;
                    item.agreeText = '已赞同';
                } else {
                    item.agree -= 1;
                    item.agreeText = '赞同';
                }
                item.toggle = !item.toggle;
            }
        },
        mounted() {
            this.question.tags.forEach(item => {
                this.tags.push(item);
            });
            if (this.answer !== null) {
                this.items.splice(0, 0, this.answer);
            }
        }
    }
</script>

<style scoped>

</style>