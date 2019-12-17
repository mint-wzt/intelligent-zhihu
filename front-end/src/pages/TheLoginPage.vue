<template>
    <div class="background">
        <the-home-nav/>
        <div class="md-layout md-alignment-top-center " style="margin-top: .5rem;">
            <div class="md-layout-item md-size-50">
                <md-card class="md-layout md-alignment-center-center">
                    <md-content class="md-layout-item md-size-70">
                        <div>
                            <md-field
                                    :md-clearable="true"
                                    :class="usernameClass"
                            >
                                <label>账号</label>
                                <md-input v-model="username" required/>
                                <span class="md-error">{{usernameTip}}</span>
                            </md-field>
                            <md-field
                                    :md-toggle-password="true"
                                    :class="passwordClass"
                            >
                                <label>密码</label>
                                <md-input v-model="password" type="password"  required/>
                                <span class="md-error">{{passwordTip}}</span>
                            </md-field>
                            <md-switch v-model="rememberMe">记住我</md-switch>
                        </div>
                        <div class="md-layout md-alignment-top-center">
                            <div class="md-layout-item ">
                                <md-button class="md-primary md-raised" @click="login">登录</md-button>
                            </div>
                        </div>
                    </md-content>
                </md-card>
            </div>
        </div>
    </div>
</template>

<script>
    import TheHomeNav from '@/components/TheHomeNav.vue'
    import {
        USER_SET_USER_ID,
        USER_SET_USER_TOKEN,
        USER_SET_USER_AVATAR,
        USER_SET_USER_ROLE,
        USER_SET_USER_USERNAME,
        USER_SET_USER_NICKNAME,
    } from '@/store/mutations-type'

    export default {
        name: "TheLoginPage",
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
                    return "密码不能为空！";
                }

                return "";
            },
        },
        data() {
            return {
                username: null,
                password: '',
                rememberMe: true,

            }
        },
        methods: {
            login() {
                this.$http.post(
                    "/auth/login",
                    {
                        username: this.username,
                        password: this.password,
                        rememberMe: this.rememberMe
                    },
                ).then(resp => {
                    if (resp.status === 200) {
                        if (resp.data.code !== 200) {
                            this.$alert(resp.data.message, '来自服务器')
                        } else {
                            const data = resp.data.data.userInfo;
                            this.$store.commit("user/" + USER_SET_USER_TOKEN, {
                                token: resp.data.data.token
                            });
                            this.$store.commit("user/" + USER_SET_USER_ROLE, {role: data.roles});
                            this.$store.commit("user/" + USER_SET_USER_USERNAME, {
                                username: data.username
                            });
                            this.$store.commit("user/" + USER_SET_USER_ID, {
                                user_id: data.id
                            });
                            this.$store.commit("user/" + USER_SET_USER_AVATAR, {
                                avatar_url: data.avatarUrl,
                            });
                            this.$store.commit("user/" + USER_SET_USER_NICKNAME, {
                                nickname: data.nickName,
                            });

                            this.$localStorage.set('state', JSON.stringify(this.$store.state));

                            this.$router.push({name: 'recommend'})
                        }
                    }
                }).catch(function (error) {
                    console.log(error)
                })
            },
        }
    }
</script>

<style scoped>

</style>