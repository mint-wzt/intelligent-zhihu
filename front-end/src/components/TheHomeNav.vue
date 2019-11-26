<template>
    <div>
        <md-toolbar class="md-transparent">
            <div class="md-toolbar-row">
                <div class="md-toolbar-section-start">
                    <h3 class="md-title">你问我答</h3>
                    <md-button :to="{name:'home'}">首页</md-button>
                    <md-button to="#">发现</md-button>
                    <md-button to="#">等你来答</md-button>
                </div>
                <vue-instant v-model="searchBoxValue"
                             :suggestions="suggestions"
                             :placeholder="placeholder"
                             :show-autocomplete="true"
                             :suggestion-attribute="suggestion_attribute"
                             type="twitter"
                />
                <div class="md-toolbar-section-end">
                    <md-button>
                        <md-icon>notifications</md-icon>
                    </md-button>
                    <md-button>
                        <md-icon>sms</md-icon>
                    </md-button>

                    <md-button v-if="isLogin === false" @click="showDialog = true">登录</md-button>
                    <md-dialog :md-active.sync="showDialog" style="min-width: 30rem;">
                        <div class="md-layout md-alignment-center-center" style="margin-top: 2rem;">
                            <div class="md-layout-item md-size-80">
                                <md-dialog-title>欢迎登录</md-dialog-title>
                            </div>
                        </div>
                        <md-dialog-content>
                            <div class="md-layout md-alignment-top-center">
                                <div class="md-layout-item md-size-80">
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
                            </div>
                            <div class="md-layout md-alignment-top-center">
                                <div class="md-layout-item md-size-75">
                                    <md-button class="md-primary md-raised" @click="login">登录</md-button>
                                </div>
                            </div>
                            <div class="md-layout md-alignment-top-center">
                                <div class="md-layout-item md-size-80">
                                    <md-snackbar
                                            md-position="center"
                                            :md-duration="5000"
                                            :md-active.sync="showSnackbar"
                                    >
                                        {{error}}
                                    </md-snackbar>
                                </div>
                            </div>

                        </md-dialog-content>
                    </md-dialog>
                    <md-menu md-align-trigger v-if="isLogin">

                        <md-avatar md-menu-trigger>
                            <img :src="avatar" alt="avatar"/>
                        </md-avatar>
                        <md-menu-content>
                            <md-menu-item>
                                <md-button>
                                    <md-icon>person</md-icon> 我的主页
                                </md-button>
                            </md-menu-item>
                            <md-menu-item>
                                <md-button>
                                    <md-icon>settings</md-icon> 我的设置
                                </md-button>
                            </md-menu-item>
                            <md-menu-item>
                                <md-button>
                                    <md-icon>power_settings_new</md-icon> 退出登录
                                </md-button>
                            </md-menu-item>
                        </md-menu-content>
                    </md-menu>
                </div>
            </div>

        </md-toolbar>

    </div>

</template>

<script>
    import {mapGetters, mapState} from 'vuex'
    import {
        USER_SET_USER_INFO,
        USER_SET_USER_TOKEN,
    } from '@/store/mutations-type'

    export default {
        name: "TheHomeNav",
        computed: {
            ...mapGetters('user',['isLogin']),
            ...mapState('user', {
                avatar: state => state.avatar_url,
            })
        },
        data () {
            return {
                searchBoxValue: '',
                suggestions: [
                    {title: "hello"},
                    {title: "world"},
                    {title: 'hi'},
                    {title: 'hii'},
                    {title: 'hiiii'}
                ],
                suggestion_attribute: 'title',
                placeholder: '搜点什么',
                showDialog: false,
                rememberMe: true,
                username: "",
                password: "",
                error: "",
                showSnackbar: false,
                err: null
            }
        },
        methods: {

            login () {
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
                            this.error = resp.data.message;
                            this.showSnackbar = true;
                        } else {
                            this.$store.commit("user/"+USER_SET_USER_TOKEN, resp.data.data);
                            this.$store.commit("user/"+USER_SET_USER_INFO, resp.data.data);
                            this.showSnackbar = false;
                        }
                    }
                }).catch(function (error) {
                    console.log(error)
                })
            }
        }
    }
</script>

<style scoped>

</style>