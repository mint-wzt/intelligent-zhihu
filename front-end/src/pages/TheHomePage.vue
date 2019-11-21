<template>
    <div>
        <the-home-nav/>
        <div class="md-layout md-alignment-center-center">
            <div class="md-layout-item md-size-55" >
                <md-card class="md-with-hover ">
                    <md-card-header>
                        <md-tabs md-sync-route>
                            <md-tab id="tab-recommend" md-label="推荐" :to="{name:'recommend', props: {data: 'sdasd'}}"></md-tab>
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
                    <md-card-header>
                        Hello
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
                str: 'hhhhhh',
                items: [
                    {
                        id: 1,
                        title: "10分钟看懂Docker和K8S",
                        content: "小枣君： 2010年，几个搞IT的年轻人，在美国旧金山成立了一家名叫“dotCloud”的公司。 " +
                            "这家公司主要提供基于PaaS的云计算技术服务。具体来说，是和LXC有关的容器技术。 后来，dotCloud公…",
                        img: '@/assets/examples/2019-04-19 220402.jpg',
                        agreeCount: 1200,
                        comment: 49
                    } , {
                        id: 2,
                        title: "10分钟看懂Docker和K8S",
                        content: "小枣君： 2010年，几个搞IT的年轻人，在美国旧金山成立了一家名叫“dotCloud”的公司。 " +
                            "这家公司主要提供基于PaaS的云计算技术服务。具体来说，是和LXC有关的容器技术。 后来，dotCloud公…",
                        img: '@/assets/examples/2019-04-19 220402.jpg',
                        agreeCount: 1200,
                        comment: 49
                    }
                ]
            }
        }
    }
</script>

<style scoped>

</style>