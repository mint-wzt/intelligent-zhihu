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
        USER_SET_USER_INFO,
        USER_SET_USER_TOKEN,
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
                        console.log(resp);
                        if (resp.data.code !== 200) {

                        } else {
                            this.$store.commit("user/" + USER_SET_USER_TOKEN, resp.data.data);
                            this.$store.commit("user/" + USER_SET_USER_INFO, {
                                ...resp.data.data,
                                user_id: resp.data.data.id,
                                avatar_url: resp.data.data.avtar_url
                            });
                            this.$localStorage.set('userInfo', {
                                user_id: resp.data.data.id,
                                avatar_url: resp.data.data.avtar_url,
                                username: resp.data.data.username,
                                nickname: resp.data.data.nickname,
                                description: resp.data.data.description ? resp.data.data.description : "",
                                career: resp.data.data.career ? resp.data.data.career : "",
                                education: resp.data.data.education,
                                name: resp.data.data.name ? resp.data.data.name : "",
                                gender: resp.data.data.gender ? resp.data.data.gender : "",
                                birthday: resp.data.data.birthday ? resp.data.data.birthday : "",
                                phone: resp.data.data.phone ? resp.data.data.phone : "",
                                email: resp.data.data.email,
                                qq: resp.data.data.qq ? resp.data.data.qq : "",
                                role: resp.data.data.role,
                                token: resp.data.data.token,
                            })
                            this.$router.push({name:'myarticle'})
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