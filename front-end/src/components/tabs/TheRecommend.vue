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
    import {mapState} from 'vuex'
    export default {
        name: "TheRecommend",
        components: {
        },
        data() {
            return {
                items: []
            }
        },
        computed: {
            ...mapState('user', ['user_id', 'token'])
        },
        methods: {
            getRecommendArticle() {
                this.$http.get('/article/articles/recommend',
                    {
                        params: {
                            userId: this.user_id
                        },
                        headers: {
                            "Authorization": this.token,
                        }
                    }
                ).then(resp => {
                    if (resp.status === 200) {
                        this.items.push(resp.data.data.articleList);
                    }
                })
            }
        },
        mounted() {
            this.getRecommendArticle()
        }
    }
</script>

<style scoped>
    .card-margin {
        margin-bottom: 1rem;
    }
</style>