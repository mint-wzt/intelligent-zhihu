<template>
    <div>
        <div class="md-title">我的收藏</div>
        <md-card style="margin-top: .5rem;" v-for="item in items" :key="item.id" class="card-margin">
            <md-card-header>
                <md-card-header-text>
                    <div class="md-title">{{item.title}}</div>
                </md-card-header-text>
            </md-card-header>
            <md-card-content>
                <div class="md-body-1">
                    {{item.content}}

                </div>
            </md-card-content>
            <md-card-actions class="md-alignment-left">
                <md-button
                        class="md-primary md-raised"
                        :to="{
                        name:'article_detail',
                        params: {
                            id : item.id,
                        }
                    }"
                >阅读全文
                </md-button>
                <md-button>{{item.commentNums}} 条评论</md-button>
                <md-button>{{item.browsedNums}}次浏览</md-button>
                <!--                <md-button>分享</md-button>-->
                <!--                <md-button>收藏</md-button>-->
                <!--                <md-button>...</md-button>-->
            </md-card-actions>
        </md-card>
    </div>
</template>

<script>
    import api from '@/api'
    import {mapState} from 'vuex'

    export default {
        name: "TheMyFavorite",
        computed: {
            ...mapState('user', ['user_id']),
        },
        data() {
            return {
                items: [],
            }
        },
        methods: {
            getFavoriteArticles() {
                api.article.getFavoriteArticles(
                    this.$http,
                    {
                        userId: this.user_id,
                    },
                    resp => {
                        if (resp.status === 200) {
                            const data = resp.data.data;
                            if (typeof data.articles !== 'undefined') {
                                data.articles.forEach(item => {
                                    this.items.push({
                                        ...item,
                                        content: item.content.substr(0, 100) + "..."
                                    })
                                })
                            }
                        }
                    }
                )
            }
        },
        mounted() {
            this.getFavoriteArticles();
        }
    }
</script>

<style scoped>

</style>