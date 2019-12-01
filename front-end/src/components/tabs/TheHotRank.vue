<template>
    <div>
        <div v-if="items.length === 0">没有数据???</div>
        <md-card v-for="item in items" :key="item.questionId" class="card-margin">
            <md-card-content>
                {{item.content}}
                <md-button
                        :to="{
                        name:'article_detail',
                        params: {
                            id : item.questionId,
                            content: item.content,
                        }
                    }"
                >阅读全文</md-button>
            </md-card-content>
        </md-card>
    </div>
</template>

<script>
    import api from '@/api'
    import {mapState} from 'vuex'
    export default {
        name: "TheHotRank",
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
                api.article.getHotArticle(this.$http, this.user_id, resp=> {
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