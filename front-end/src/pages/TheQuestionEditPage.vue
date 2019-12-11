<template>
    <div>
        <the-home-nav/>
        <el-row style="margin-top: 2rem;">
            <el-col :offset="3" :span="18">

                <el-row>
                    <el-col>
                        <el-card>
                            <el-form v-model="form">
                                <el-form-item>
                                    <label>问题类型</label>
                                    <el-input v-model="form.type"/>
                                </el-form-item>
                                <el-form-item>
                                    <label>问题描述</label>
                                    <el-input v-model="form.content" type="textarea" :rows="4"/>
                                </el-form-item>
                                <el-form-item>
                                    <el-button type="primary" @click="publishQuestion">发布</el-button>
                                </el-form-item>
                            </el-form>
                        </el-card>
                    </el-col>
                </el-row>
            </el-col>

        </el-row>
    </div>
</template>

<script>
    import TheHomeNav from '@/components/TheHomeNav.vue'
    import api from '@/api'
    import {mapState} from 'vuex'
    import qs from 'querystring'

    export default {
        name: "TheQuestionEditPage",
        components: {
            TheHomeNav,
        },
        computed: {
            ...mapState('user', {
                userId: state => state.user_id,
            })
        },
        data() {
            return {
                form: {
                    type: '',
                    content: '',
                }
            }
        },
        methods: {
            publishQuestion() {
                api.question.publishQuestion(
                    this.$http,
                    qs.stringify({
                        userId: this.userId,
                        content: this.form.content,
                        type: this.form.type,
                    }),
                    resp => {
                        // const data = resp.data.data;
                        if (resp.status === 200) {
                            this.$message.success('问题发布成功');
                            this.$router.push({name: 'recommend'})
                        }
                        // this.$router.push({name: 'question_detail', params: {id: data.questionId}})
                    }
                )
            }
        }
    }
</script>

<style scoped>

</style>