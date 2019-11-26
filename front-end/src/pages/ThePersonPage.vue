<template>
    <div>
        <the-home-nav/>

        <div class="md-layout md-alignment-top-center" style="margin-top: 2rem;">
            <div class="md-layout-item md-size-90">
                <md-card>
                    <md-card-header>
                        <div class="md-layout">
                            <div class="md-layout-item md-size-20">
                                <el-avatar
                                        shape="square"
                                        :size="160"
                                        :src="avatar"
                                ></el-avatar>
                            </div>
                            <div class="md-layout-item md-size-80">
                                <div class="md-title">{{username}}</div>
                            </div>
                        </div>

                    </md-card-header>
                </md-card>
            </div>
        </div>

        <div class="md-layout md-alignment-top-center" style="margin-top: .5rem;">
            <div class="md-layout-item md-size-90">
                <md-card class="md-with-hover ">
                    <md-card-header>
                        <md-tabs md-sync-route>
                            <md-tab id="tab-recommend" md-label="文章" :to="{name:'myarticle'}"></md-tab>
                            <md-tab id="tab-attention" md-label="回答" :to="{name: 'attention'}"></md-tab>
                            <md-tab id="tab-hotrank" md-label="收藏" :to="{name: 'hotrank'}"></md-tab>
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
    import {mapState} from 'vuex'
    export default {
        name: "ThePersonPage",
        components: {
            TheHomeNav
        },
        computed: {
            ...mapState('user', {
                avatar: state => state.avatar_url,
                username: state => state.username,
            })
        },
        data () {
            return {
                croppaOpts: {}
            }
        },
        mounted() {
            this.$router.push({name:'myarticle'})
        }
    }
</script>

<style scoped>

</style>