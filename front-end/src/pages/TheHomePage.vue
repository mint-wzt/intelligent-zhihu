<template>
    <div>
        <the-home-nav/>
        <div class="md-layout md-alignment-top-center " style="margin-top: 1.5rem;">
            <div class="md-layout-item md-size-55 content-margin" >
                <md-card class="md-with-hover ">
                    <md-card-header>
                        <md-tabs md-sync-route>
                            <md-tab id="tab-recommend" md-label="推荐" :to="{name:'recommend'}"></md-tab>
                            <md-tab id="tab-attention" md-label="关注" :to="{name: 'attention'}"></md-tab>
                            <md-tab id="tab-hotrank" md-label="热榜" :to="{name: 'hotrank'}"></md-tab>
                        </md-tabs>
                    </md-card-header>
                    <md-card-content>
                        <router-view/>
                    </md-card-content>
                </md-card>

            </div>
            <div class="md-layout-item md-size-30">
                <md-card class="">
                    <md-card-header class="md-layout">
                        <div class="md-layout-item">
                            <md-button :to="{name:'article'}">
                                <md-icon>description</md-icon>
                                写回答
                            </md-button>
                        </div>
                        <div class="md-layout-item">
                            <md-button :to="{name:'article'}" ><md-icon>edit</md-icon>写文章</md-button>
                        </div>
                        <div class="md-layout-item">
                            <md-button><md-icon>wb_incandescent</md-icon>写想法</md-button>
                        </div>
                    </md-card-header>
                </md-card>
            </div>
        </div>

    </div>
</template>

<script>
    import TheHomeNav from '@/components/TheHomeNav.vue'
    export default {
        name: "TheHomePage",
        components: {
            TheHomeNav
        },
        data () {
            return {
            }
        },
        methods: {

        },
    }
</script>

<style scoped>
.content-margin {
    margin-right: 1rem;
}
</style>