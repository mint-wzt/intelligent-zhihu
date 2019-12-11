<template>
    <div>
        <the-home-nav/>
        <div class="md-layout md-alignment-top-center" style="margin-top: .5rem;">
            <div class="md-layout-item md-size-50">
                <md-card class="md-layout md-alignment-center-center">
                    <md-content class="md-layout-item md-size-70">
                        <md-field>
                            <label>用户名</label>
                            <md-input v-model="username" :required="true"></md-input>
                        </md-field>
                        <md-field>
                            <label>密码</label>
                            <md-input v-model="password" type="password" :required="true"></md-input>
                        </md-field>
                        <md-field>
                            <label>所在行业</label>
                            <md-input v-model="industry" :required="true"></md-input>
                        </md-field>
                        <md-field>
                            <label>邮箱</label>
                            <md-input v-model="email" type="email" :required="true"></md-input>
                        </md-field>
                        <md-field>
                            <label>教育经历</label>
                            <md-input v-model="career"></md-input>
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
    export default {
        name: "TheRegisterPage",
        components: {
            TheHomeNav
        },
        data () {
            return {
                username: null,
                password: null,
                industry: null,
                career: null,
                education: null,
                gender: null,
                email: null
            }
        },
        methods: {
            postRegister() {
                if(this.username===null||this.username===""||this.username===undefined||this.username.indexOf(" ") !== -1){
                    alert("用户名不能为空且不能包含空格，请重新输入！");
                    return;
                }
                if(this.password===null||this.password===""||this.password===undefined||this.password.indexOf(" ") !== -1){
                    alert("密码不能为空且不能包含空格，请重新输入！");
                    return;
                }
                if( this.industry===null||this.industry===""||this.industry===undefined){
                    alert("所在行业不能为空，请重新输入！");
                    return;
                }
                if( this.email===null||this.email===""||this.email===undefined){
                    alert("邮箱不能为空，请重新输入！");
                    return;
                }
                this.$http.post('/auth/register',
                   {
                        username: this.username,
                        password: this.password,
                        industry: this.industry,
                        career: this.career,
                        education: this.education,
                        gender: this.gender,
                        email: this.email
                   }
                ).then(resp => {
                    console.log(resp);
                    if (resp.status === 200) {
                        this.$router.push({name:'login'})
                    }
                })
            }
        }
    }
</script>

<style scoped>

</style>