<template>
    <div>
        <the-home-nav/>
        <div class="md-layout md-alignment-center-center margin-top">
            <div class="md-layout-item md-size-80 ">
                <md-field>
                    <label>标题</label>
                    <md-input v-model="title"/>
                </md-field>
            </div>
            <div class="md-layout-item md-size-10">
                <md-button class="md-primary md-raised" @click="publishArticle">发布</md-button>
            </div>
        </div>
        <div class="md-layout md-alignment-center-center ">
            <div class="md-layout-item md-size-90 ">
                <md-chips v-model="types" md-placeholder="添加标签"/>
            </div>
        </div>
        <div class="md-layout md-alignment-center-center">
            <div class="md-layout-item md-size-90">
                <md-field>
                    <mavon-editor
                            ref="markdownEditor"
                            :toolbars="markdownOptions"
                            v-model="content"
                            @imgAdd="imageAdd"
                            @imgDel="imageDelete"
                            class="mavonEditor"/>
                </md-field>
            </div>
        </div>
    </div>

</template>

<script>
    import TheHomeNav from '@/components/TheHomeNav.vue'
    import {mapState} from 'vuex'
    import qs from 'querystring'
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
            articleType() {
                return this.types.join(",")
            }
        },
        methods: {
            imageAdd(pos, file) {
                this.imageFiles[pos] = file;
            },
            imageDelete(pos) {
                delete this.imageFiles[pos];
            },

            uploadImage(callback) {
                var fd = new FormData();
                for (var img_ in this.imageFiles) {
                    fd.append('files', this.imageFiles[img_]);
                }
                fd.append('id', this.authorId);
                this.$http.post('/article/image/images',
                    fd, {
                        headers: {
                            "Authorization": this.token,
                            'Content-Type': 'multipart/form-data'
                        }
                    }
                ).then(resp => {
                    if (resp.status === 201) {
                        const {imageUrls} = resp.data.data;
                        for (var index = 0; index < imageUrls.length; index++) {
                            this.$refs.markdownEditor.$img2Url(index + 1, imageUrls[index]);
                        }

                        callback()
                    }
                })
            },

            publishArticle() {

                if(this.title===null||this.title===undefined||this.title===""){
                    alert("文章标题不能为空！");
                }
                this.uploadImage(()=> {
                    this.$http.post(
                        "/article/articles",
                        qs.stringify(
                            {
                                authorId: this.authorId,
                                type: this.articleType,
                                author: this.author,
                                content: this.content,
                                status: 1,
                                isArticle: 1,
                                title: this.title,
                            }
                        ),
                        {
                            headers: {
                                "Authorization": this.token,
                                'content-type': 'application/x-www-form-urlencoded'
                            }
                        }
                    ).then(resp => {
                        if (resp.status === 200) {
                            if (resp.data.code === 200) {
                                this.$router.push({
                                    name: "article_detail",
                                    params: {
                                        id: resp.data.data.articleInfo.id,
                                    }
                                })
                            }
                        }
                    }).catch(err => {
                        console.log(err)
                    })
                });
            }
        },
        data() {
            return {
                content: "",
                title: "",
                types: [],
                imageFiles: {},

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