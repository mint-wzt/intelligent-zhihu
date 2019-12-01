<template>
    <div>
        <the-home-nav/>
        <div class="md-layout md-alignment-top-center" style="margin-top: .5rem;">
            <div class="md-layout-item md-size-50">
                <md-card class="md-layout md-alignment-center-center">
                    <md-content class="md-layout-item md-size-70">
                        <div>
                            <md-field
                                    :md-clearable="true"
                            >
                                <label>账号</label>
                                <md-input v-model="username"></md-input>
                            </md-field>
                            <md-field
                                    :md-toggle-password="true"
                            >
                                <label>密码</label>
                                <md-input v-model="password" type="password"></md-input>
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
    } from '@/store/mutations-type'

    export default {
        name: "TheLoginPage",
        components: {
            TheHomeNav
        },
        data() {
            return {
                username: null,
                password: null,
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

                            this.$localStorage.set('state', JSON.stringify(this.$store.state));

                            this.$router.push({name:'recommend'})
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