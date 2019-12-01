<template>
    <div>
        <div v-if="items.length === 0">没有数据???</div>
        <md-card v-for="item in items" :key="item.title" class="card-margin">
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
                            id : item.authorId,
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
    import api from '@/api'
    import {mapState} from 'vuex'
    export default {
        name: "TheAttentionList",
        data() {
            return {
                items: []
            }
        },
        computed: {
            ...mapState('user', ['user_id', 'token'])
        },
        methods: {
            getData() {
                api.article.getFollowArticle(this.$http, this.user_id, resp=> {
                    this.items.concat(resp.data.data.articleList);
                })
            }
        },
        mounted() {
            this.getData()
        }
    }
</script>

<style scoped>

</style>