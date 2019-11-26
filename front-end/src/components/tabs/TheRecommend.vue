<template>
    <div>
        <div v-if="items.length === 0">没有数据???</div>
        <md-card v-for="item in items" :key="item.id" class="card-margin">
            <md-card-header>
                <md-card-header-text>
                    <div class="md-title">{{item.title}}</div>
                </md-card-header-text>
            </md-card-header>
            <md-card-content>
                {{item.content}}
<!--                <the-article-show-->
<!--                        :title="item.title"-->
<!--                        :content="item.content">-->
<!--                </the-article-show>-->
                <md-button
                    :to="{
                        name:'article_detail',
                        params: {
                            id : item.id,
                            title: item.title,
                            content: item.content,
                            author: item.author
                        }
                    }"
                >阅读全文</md-button>
            </md-card-content>
            <md-card-actions>
                <md-button>赞同</md-button>
                <md-button>反对</md-button>
                <md-button>{{item.comment}} 条评论</md-button>
                <md-button>分享</md-button>
                <md-button>收藏</md-button>
                <md-button>...</md-button>

            </md-card-actions>
        </md-card>
    </div>
</template>

<script>
    export default {
        name: "TheRecommend",
        components: {
        },
        data() {
            return {
                items: [
                    {
                        id: 1,
                        title: "10分钟看懂Docker和K8S",
                        content: "小枣君： 2010年，几个搞IT的年轻人，在美国旧金山成立了一家名叫“dotCloud”的公司。 " +
                            "这家公司主要提供基于PaaS的云计算技术服务。具体来说，是和LXC有关的容器技术。 后来，dotCloud公…",
                        thumbs: 1200,
                        comment: 49,
                        author: '你的名字'
                    }, {
                        id: 2,
                        title: "10分钟看懂Docker和K8S",
                        content: "在美国旧金山成立了一家名叫“dotCloud”的公司。 " +
                            "这家公司主要提供基于PaaS的云计算技术服务。具体来说，是和LXC有关的容器技术。 后来，dotCloud公…",
                        thumbs: 1200,
                        comment: 49
                    }
                ]
            }
        }
    }
</script>

<style scoped>
    .card-margin {
        margin-bottom: 1rem;
    }
</style>