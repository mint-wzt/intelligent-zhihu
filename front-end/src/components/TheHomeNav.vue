<template>
    <div>
        <md-toolbar class="md-transparent">
            <div class="md-toolbar-row">
                <div class="md-toolbar-section-start">
                    <h3 class="md-title">你问我答</h3>
                    <md-button :to="{name:'recommend'}">首页</md-button>
<!--                    <md-button to="#">发现</md-button>-->
<!--                    <md-button to="#">等你来答</md-button>-->
                </div>
                <vue-instant v-model="searchBoxValue"
                             :suggestions="suggestions"
                             :placeholder="placeholder"
                             :show-autocomplete="true"
                             :suggestion-attribute="suggestion_attribute"
                             type="twitter"
                />
                <div class="md-toolbar-section-end">
<!--                    <md-button>-->
<!--                        <md-icon>notifications</md-icon>-->
<!--                    </md-button>-->
<!--                    <md-button>-->
<!--                        <md-icon>sms</md-icon>-->
<!--                    </md-button>-->

                    <md-button v-if="isLogin === false" :to="{name:'login'}" class="md-raised">登录</md-button>

                    <md-menu md-align-trigger v-if="isLogin" >

                        <md-avatar md-menu-trigger>
                            <img :src="avatar" alt="avatar"/>
                        </md-avatar>
                        <md-menu-content>
                            <md-menu-item>
                                <md-button :to="{name:'myarticle'}">
                                    <md-icon>person</md-icon> 我的主页
                                </md-button>
                            </md-menu-item>
<!--                            <md-menu-item>-->
<!--                                <md-button>-->
<!--                                    <md-icon>settings</md-icon> 我的设置-->
<!--                                </md-button>-->
<!--                            </md-menu-item>-->
                            <md-menu-item v-if="isAdmin">
                                <md-button :to="{name:'admin'}">
                                    <md-icon>build</md-icon> 管理页面
                                </md-button>
                            </md-menu-item>
                            <md-menu-item>
                                <md-button @click="loginout">
                                    <md-icon>power_settings_new</md-icon> 退出登录
                                </md-button>
                            </md-menu-item>

                        </md-menu-content>
                    </md-menu>
                    <md-button class="md-primary md-raised" :to="{name:'register'}">注册</md-button>
                </div>
            </div>

        </md-toolbar>

    </div>

</template>

<script>
    import {mapGetters, mapState} from 'vuex'

    export default {
        name: "TheHomeNav",
        computed: {
            ...mapGetters('user',['isLogin', 'isAdmin']),
            ...mapState('user', {
                avatar: state => state.avatar_url,
            })
        },
        data () {
            return {
                searchBoxValue: '',
                suggestions: [
                ],
                suggestion_attribute: 'title',
                placeholder: '搜点什么',
            }
        },
        methods: {
            loginout() {
                this.$localStorage.set('state', null);
                this.$router.push({name:'login'})
            }
        },
    }
</script>

<style scoped>
.md-menu {
    margin-right: 2rem;
}
    .md-menu-content  {
        z-index: 2000;
    }
</style>