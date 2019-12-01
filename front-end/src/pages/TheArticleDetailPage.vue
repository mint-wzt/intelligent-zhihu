<template>
    <div>
        <the-home-nav/>
        <div class="md-layout md-alignment-top-center">
            <div class="md-layout-item md-size-75">
                <div class="md-layout">
                    <div class="md-layout-item">
                        <h1>{{title}}</h1>
                    </div>
                </div>
                <div class="md-layout">
                    <div class="md-layout-item">
                        <h3>{{author}}</h3>
                    </div>
                </div>

                <mavon-editor
                        defaultOpen="preview"
                        :editable="false"
                        :toolbarsFlag="false"
                        :tabSize="4"
                        v-model="content"
                        :subfield="false"
                        :toolbars="{}"
                ></mavon-editor>
            </div>
        </div>
    </div>
</template>

<script>
    import TheHomeNav from '@/components/TheHomeNav.vue'
    import {mapState} from 'vuex'

    export default {
        name: "TheArticleDetailPage",
        components: {
            TheHomeNav
        },
        props: {
            id: {
                type: String,
            },
        },
        data() {
            return {
                title: '',
                content: '',
                author: '',
                author_id: null,
            }
        },
        computed: {
            ...mapState('user', {
                user_id: state => state.user_id,
                token: state => state.token,
            })
        },
        methods: {
            getUserInfo(callback) {
                this.$http.get('/user/users/'+this.user_id,
                    {
                        headers: {
                            "Authorization": this.token,
                        }
                    }
                ).then(resp => {
                    if (resp.status === 200) {
                        callback(resp)
                    }
                })
            }
        },
        mounted() {

            this.$http.get('/article/articles',
                {
                    params: {
                        articleId: this.id,
                    },
                    headers: {
                        "Authorization": this.token,
                    }
                }
            ).then(resp => {
                console.log(resp);
                if (resp.status === 200) {
                    const {
                        title,
                        content,
                        author,
                        author_id,
                    } = resp.data.data.articleInfo;
                    this.title = title;
                    this.content = content;
                    this.author = author;
                    this.author_id = author_id;

                    this.getUserInfo((resp) => {
                        console.log(resp)
                    })
                }
            });
            console.log(this.articleInfo)
        }

    }
</script>

<style scoped>

</style>