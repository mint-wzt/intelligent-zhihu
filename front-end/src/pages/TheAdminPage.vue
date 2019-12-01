<template>
    <div>
        <el-container>
            <el-header>
                <md-toolbar>
                    <h3 class="md-title" style="flex: 1">你问我答后台管理系统</h3>
                    <md-button :to="{name:'recommend'}">返回首页</md-button>
                    <md-menu md-align-trigger v-if="isLogin">
                        <md-avatar md-menu-trigger>
                            <img :src="avatar" alt="avatar"/>
                        </md-avatar>
                        <md-menu-content>
                            <md-menu-item>
                                <md-button :to="{name:'myarticle'}">
                                    <md-icon>person</md-icon> 我的主页
                                </md-button>
                            </md-menu-item>
                            <md-menu-item>
                                <md-button>
                                    <md-icon>settings</md-icon> 我的设置
                                </md-button>
                            </md-menu-item>
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
                </md-toolbar>
            </el-header>
            <el-container>
                <el-aside width="10rem">
                    <el-menu
                            background-color="#545c64"
                            text-color="#fff"
                            active-text-color="#ffd04b"
                            style="min-height: 900px;"
                            default-active=""
                            :router="true"
                    >
                        <el-menu-item index="/admin" >
                            <span >文章管理</span>
                        </el-menu-item>
                        <el-menu-item index="/user">
                            <span >用户管理</span>
                        </el-menu-item>
                        <el-menu-item index="/topic">
                            <span >话题管理</span>
                        </el-menu-item>
                    </el-menu>
                </el-aside>
                <el-main>
                    <router-view/>
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>

<script>
    import {mapGetters, mapState} from 'vuex'
    // import {
    //     USER_SET_USER_TOKEN,
    // } from '@/store/mutations-type'
    export default {
        name: "TheAdminPage",
        computed: {
            ...mapGetters('user',['isLogin', 'isAdmin', 'isRoot']),
            ...mapState('user', {
                avatar: state => state.avatar_url,
            })
        },
        methods: {
            loginout() {
                this.$localStorage.set('state', null);
            }
        }
    }
</script>

<style scoped>
.el-header {
    padding: 0;
}
</style>