<template>
    <div>
        <the-home-nav/>
        <div class="md-layout md-alignment-top-center">
            <div class="md-layout-item md-size-90">
                <h1>{{title}}</h1>
                <h3>{{author}}</h3>
                <mavon-editor
                        defaultOpen="preview"
                        :editable="false"
                        :toolbarsFlag="false"
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

    export default {
        name: "TheArticleDetailPage",
        components: {
            TheHomeNav
        },
        props: {
            id: {
                type: String,
                required: true
            },
            title: {
                type: String,
            },
            author: {
                type: String
            },
            content: {
                type: String
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