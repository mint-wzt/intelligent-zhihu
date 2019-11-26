<template>
    <div>
        <the-home-nav/>
        <div class="md-layout md-alignment-center-center margin-top">
            <div class="md-layout-item md-size-80 ">
                <md-field>
                    <label>标题</label>
                    <md-input v-model="title"></md-input>
                </md-field>
            </div>
            <div class="md-layout-item md-size-10">
                <md-button class="md-primary md-raised" @click="publishArticle">发布</md-button>
            </div>
        </div>
        <div class="md-layout md-alignment-center-center ">
            <div class="md-layout-item md-size-90 ">
                <md-chips v-model="types" md-placeholder="添加标签"></md-chips>
            </div>
        </div>
        <div class="md-layout md-alignment-center-center">
            <div class="md-layout-item md-size-90">
                <md-field>
                    <mavon-editor :toolbars="markdownOptions" v-model="content" class="mavonEditor"/>
                </md-field>
            </div>
        </div>
    </div>

</template>

<script>
    import TheHomeNav from '@/components/TheHomeNav.vue'
    import {mapState} from 'vuex'
    export default {
        name: "TheArticleEditPage",
        components: {
            TheHomeNav,
        },
        computed: {
            ...mapState('user', {
                authorId: state => state.user_id,
                author: state => state.username,
                token: state => state.token,

            }),
            articleType () {
                return this.types.join(",")
            }
        },
        methods: {
          publishArticle() {
              this.$http.post(
                  "/api/article/articles",
                  {
                      authorId: this.authorId,
                      type: this.articleType,
                      author: this.author,
                      content: this.content,
                      status: 1,
                      token: this.token,
                  },
                  {
                      headers: {
                          "Authorization": this.token
                      }
                  }
              ).then(resp => {
                    if (resp.status === 200) {
                        if (resp.data.code === 200) {
                            this.$route.push({
                                name:"article_detail",
                                params: {
                                    id: resp.data.data.id,
                                    author: resp.data.data.author,
                                    title: resp.data.data.title,
                                    content: resp.data.data.content,
                                }
                            })
                        }
                    }
              })

          }
        },
        data () {
            return {
                content: "",
                title: "",
                types: [],

                markdownOptions: {
                    bold: true, // 粗体
                    italic: true, // 斜体
                    header: true, // 标题
                    underline: true, // 下划线
                    strikethrough: true, // 中划线
                    mark: true, // 标记
                    superscript: true, // 上角标
                    subscript: true, // 下角标
                    quote: true, // 引用
                    ol: true, // 有序列表
                    ul: true, // 无序列表
                    link: true, // 链接
                    imagelink: true, // 图片链接
                    code: true, // code
                    table: true, // 表格
                    fullscreen: true, // 全屏编辑
                    readmodel: true, // 沉浸式阅读
                    htmlcode: true, // 展示html源码
                    help: true, // 帮助
                    /* 1.3.5 */
                    undo: true, // 上一步
                    redo: true, // 下一步
                    trash: true, // 清空
                    save: true, // 保存（触发events中的save事件）
                    /* 1.4.2 */
                    navigation: true, // 导航目录
                    /* 2.1.8 */
                    alignleft: true, // 左对齐
                    aligncenter: true, // 居中
                    alignright: true, // 右对齐
                    /* 2.2.1 */
                    subfield: true, // 单双栏模式
                    preview: true, // 预览
                },

            }
        },
    }
</script>

<style scoped>
.margin-top {
    margin-top: 2rem;
}
.mavonEditor {
    width: 100%;
    height: 100%;
    min-height: 45rem;
}
</style>