<template>
    <div>
        <the-home-nav/>
        <div class="md-layout md-alignment-top-center" style="margin-top: .5rem;">
            <div class="md-layout-item md-size-50">
                <md-card class="md-layout md-alignment-center-center">
                    <md-content class="md-layout-item md-size-70">
                        <md-field :class="usernameClass">
                            <label>用户名</label>
                            <md-input v-model="username" :required="true"/>
                            <span class="md-error">{{usernameTip}}</span>
                        </md-field>
                        <md-field :class="passwordClass">
                            <label>密码</label>
                            <md-input v-model="password" type="password" :required="true"/>
                            <span class="md-error">{{passwordTip}}</span>
                        </md-field>
                        <md-field :class="industryClass">
                            <label>所在行业</label>
                            <md-input v-model="industry" :required="true"/>
                            <span class="md-error">{{industryTip}}</span>
                        </md-field>
                        <md-field :class="emailClass">
                            <label>邮箱</label>
                            <md-input v-model="email" type="email" :required="true"/>
                            <span class="md-error">{{emailTip}}</span>
                        </md-field>
                        <md-field :class="educationClass">
                            <label>教育经历</label>
                            <md-input v-model="education" required/>
                            <span class="md-error">{{educationTip}}</span>
                        </md-field>
                        <md-field :class="careerClass">
                            <label>职业</label>
                            <md-input v-model="career" required/>
                            <span class="md-error">{{careerTip}}</span>
                        </md-field>
                        <div>
                            <md-radio v-model="gender" value="男">男</md-radio>
                            <md-radio v-model="gender" value="女" class="md-primary">女</md-radio>
                        </div>

                        <md-button class="md-primary md-raised" @click="postRegister">注册</md-button>
                    </md-content>
                </md-card>
            </div>
        </div>
    </div>
</template>

<script>
    import TheHomeNav from '@/components/TheHomeNav.vue'
    // import qs from 'querystring'
    import api from '@/api';

    export default {
        name: "TheRegisterPage",
        components: {
            TheHomeNav
        },
        computed: {
            usernameClass() {
                if (this.username === null || this.username.trim() === "") {
                    return "md-invalid";
                }
                return "";
            },
            usernameTip() {
                if (this.username === null || this.username.trim() === "") {
                    return "用户名不能为空";
                }
                return '';
            },
            passwordClass() {
                if (this.password === null || this.password.trim() === "") {
                    return "md-invalid";
                }
                return "";
            },
            passwordTip() {
                if (this.password === null || this.password.indexOf(" ") !== -1) {
                    return "密码不能包含空格"
                }
                if (this.password.trim() === "") {
                    return "密码不能为空，请重新输入！";
                }

                return "";
            },
            industryClass() {
                if (this.industry === null || this.industry.trim() === "") {
                    return "md-invalid";
                }
                return "";
            },
            industryTip() {
                if (this.industry === null || this.industry.trim() === "") {
                    return "这是必填字段";
                }
                return "";
            },
            emailClass() {
                if (this.email === null || this.email.trim() === "") {
                    return "md-invalid";
                }
                return "";
            },
            emailTip() {
                if (this.email === null || this.email.trim() === "") {
                    return "这是必填字段";
                }
                return "";
            },
            educationClass() {
                if (this.education === null || this.education.trim() === "") {
                    return "md-invalid";
                }
                return "";
            },
            educationTip() {
                if (this.education === null || this.education.trim() === "") {
                    return "这是必填字段";
                }
                return "";
            },
            careerClass() {
                if (this.career === null || this.career.trim() === "") {
                    return "md-invalid";
                }
                return "";
            },
            careerTip() {
                if (this.career === null || this.career.trim() === "") {
                    return "这是必填字段";
                }
                return "";
            }
        },
        data() {
            return {
                username: null,
                password: null,
                industry: null,
                career: null,
                education: null,
                gender: "男",
                email: null
            }
        },
        methods: {
            postRegister() {

                api.user.register(
                    this.$http,
                    {
                        username: this.username.trim(),
                        password: this.password.trim(),
                        industry: this.industry.trim(),
                        education: this.education.trim(),
                        email: this.email.trim(),
                        gender: this.gender,
                        career: this.career,
                    }
                    ,
                    resp => {
                        if (resp.status === 200) {
                            this.$router.push({name: 'login'})
                        } else {
                            this.$alert(
                                '有不合法的字段，请检查邮箱地址以及其他的字段是否正确',
                                '来自服务器', {
                                    type: 'warning',
                                    confirmButtonText: '确定',
                                });
                        }
                    }
                )
            }
        }
    }
</script>

<style scoped>

</style>