<template>
    <div>
        <the-home-nav/>
        <div class="md-layout md-alignment-top-center">
            <div class="md-layout-item md-size-90">
                <h1>{{articleInfo.title}}</h1>
                <h3>{{articleInfo.author}}</h3>
                <mavon-editor
                        defaultOpen="preview"
                        :editable="false"
                        :toolbarsFlag="false"
                        :tabSize="4"
                        v-model="articleInfo.content"
                        :subfield="false"
                        :toolbars="{}"
                ></mavon-editor>
            </div>
        </div>
    </div>
</template>

<script>
    import TheHomeNav from '@/components/TheHomeNav.vue'

    export default {
        name: "TheArticleDetailPage",
        components: {
            TheHomeNav
        },
        props: {
            articleInfo: {
                type: Object,
            }
        },
        data () {
            return {
            }
        }
    }
</script>

<style scoped>

</style>