<template>
    <div>
        <the-home-nav/>

        <md-dialog
                :md-active.sync="showUpdateAvatar"
        >
            <md-card-content>
                <croppa v-model="croppa"
                        :width="168"
                        :height="168"
                        :prevent-white-space="true">
                </croppa>
            </md-card-content>
            <md-card-actions>
                <md-button @click="uploadAvatar">确定</md-button>
                <md-button  @click="showUpdateAvatar = false">取消</md-button>
            </md-card-actions>
        </md-dialog>
        <div class="md-layout md-alignment-top-center" style="margin-top: 2rem;">
            <div class="md-layout-item md-size-90">
                <md-card>
                    <md-card-media-cover>
                        <img src="@/assets/examples/wutengchunzi.png" alt="" />
                    </md-card-media-cover>
                    <md-card-header>
                        <div class="md-layout">
                            <div class="md-layout-item md-size-20">
                                <el-avatar
                                        class="avatar_img"
                                    :src="avatar"
                                    shape="square"
                                >
                                </el-avatar>
                            </div>

                            <div class="md-layout-item md-size-80">

                                <div class="md-layout md-alignment-top-center">
                                    <div class="md-layout-item ">
                                        <div class="md-title">{{username}}</div>
                                    </div>
                                    <div class="md-layout-item">
                                        <div class="md-body-2">{{motto}}</div>
                                    </div>
                                </div>
                            </div>

                        </div>

                    </md-card-header>
                    <md-card-actions>
                        <el-button @click="showUpdateAvatar = true" type="primary" size="larger" style="margin-right: 6px;">修改头像</el-button>
                        <md-button class="md-primary md-raised" :to="{name:'userinfo_edit'}">修改个人资料</md-button>
                    </md-card-actions>
                </md-card>
            </div>
        </div>

        <div class="md-layout md-alignment-top-center" style="margin-top: .5rem; margin-bottom: 2rem;">
            <div class="md-layout-item md-size-90">
                <md-card class="md-with-hover ">
                    <md-card-header>
                        <md-tabs md-sync-route>
                            <md-tab id="tab-myarticle" md-label="文章" :to="{name:'myarticle'}"/>
                            <md-tab id="tab-my_answer" md-label="回答" :to="{name: 'my_answer'}"/>
                            <md-tab id="tab-myfavorite" md-label="收藏" :to="{name: 'myfavorite'}"/>
                            <md-tab id="tab-my_follow" md-label="关注" :to="{name: 'my_follow'}"/>
                        </md-tabs>
                    </md-card-header>
                    <md-card-content>
                        <router-view/>
                    </md-card-content>
                </md-card>
            </div>
        </div>
    </div>
</template>

<script>
    import TheHomeNav from '@/components/TheHomeNav.vue'
    import {mapState,} from 'vuex'
    import {
        USER_SET_USER_AVATAR,
    } from '@/store/mutations-type'
    export default {
        name: "ThePersonPage",
        components: {
            TheHomeNav
        },
        computed: {
            ...mapState('user', [
                'username', 'user_id', 'token',
                'motto', 'industry', 'education',
            ]),
            ...mapState('user', {
                avatar: state => state.avatar_url,
            })
        },
        data () {
            return {
                croppa: {},
                showUpdateAvatar: false,
            }
        },
        methods: {
            uploadAvatar() {
                this.croppa.generateBlob(blob => {
                    var fd = new FormData();
                    fd.append('file', blob, this.username + 'avatar.jpg');
                    fd.append('id', this.user_id);
                    this.$http.post('/user/users/avatar',
                        fd, {
                            headers: {
                                "Authorization": this.token,
                                'content-type': 'application/form-data'
                            }
                        }
                    ).then(resp => {
                        if (resp.status === 201) {
                            this.$store.commit('user/'+USER_SET_USER_AVATAR, {avatar_url:resp.data.data.avatarUrl});
                            this.showUpdateAvatar = false;
                        }
                    })
                }, 'image/jpeg')
            }
        },
    }
</script>

<style scoped>
.avatar_img {
    width: 8rem;
    height: 8rem;
    position: relative;
    margin-top: -50px;
    margin-left: 25px;
    z-index: 9999;
    background-color: #fff;
    border: 4px solid #fff;
    border-radius: 8px;
}
</style>