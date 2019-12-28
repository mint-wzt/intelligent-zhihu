<template>
    <div>
        <the-home-nav/>
        <el-row style="margin-top: 2rem;">
            <el-col :offset="3" :span="18">

                <el-row>
                    <el-col>
                        <el-card>
                            <el-form :model="questionForm" ref="questionForm" :rules="rules" >
                                <el-form-item prop="tags">
                                    <label>问题类型</label>
                                    <div></div>
                                    <el-tag
                                            :key="tag"
                                            v-for="tag in questionForm.tags"
                                            closable
                                            :disable-transitions="false"
                                            @close="handleClose(tag)">
                                        {{tag}}
                                    </el-tag>
                                    <el-input
                                            class="input-new-tag"
                                            v-if="inputVisible"
                                            v-model="inputValue"
                                            ref="saveTagInput"
                                            size="small"
                                            @keyup.enter.native="handleInputConfirm"
                                            @blur="handleInputConfirm"
                                    />
                                    <el-button v-else class="button-new-tag" size="small" @click="showInput">+ New Tag</el-button>
                                </el-form-item>
                                <el-form-item prop="content">
                                    <label>问题描述</label>
                                    <el-input v-model="questionForm.content" type="textarea" :rows="4"/>
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
                inputVisible: false,
                inputValue: '',
                questionForm: {
                    tags: [],
                    content: '',
                },
                rules: {
                    content: [
                        {required: true, message: '问题内容不能为空', trigger: 'blur'},
                        {min: 1, message: '问题内容不能为空', trigger: 'blur'}
                    ],
                    tags: [
                        {type: 'array', required: true, message: '请至少输入一个标签表明其类型', trigger: 'change'},
                        {type: 'array', min: 1, message: '请至少输入一个标签表明其类型', trigger: 'blur'}
                    ]
                }
            }
        },
        methods: {
            publishQuestion() {

                this.$refs["questionForm"].validate((valid) => {
                    if (valid) {
                        api.question.publishQuestion(
                            this.$http,
                            qs.stringify({
                                userId: this.userId,
                                content: this.questionForm.content,
                                type: this.questionForm.tags.join(','),
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
                });


            },
            handleClose(tag) {
                this.questionForm.tags.splice(this.questionForm.tags.indexOf(tag), 1)
            },
            handleInputConfirm() {
                let inputValue = this.inputValue;
                if (inputValue) {
                    this.questionForm.tags.push(inputValue);
                }
                this.inputVisible = false;
                this.inputValue = '';
            },
            showInput() {
                this.inputVisible = true;
                this.$nextTick(() => {
                    this.$refs.saveTagInput.$refs.input.focus();
                });
            },
        }
    }
</script>

<style scoped>
    .el-tag {
        margin: 6px;
    }
    .input-new-tag {
        width: 120px;
        margin-left: 10px;
        vertical-align: bottom;
    }
</style>