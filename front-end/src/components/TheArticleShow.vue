<template>
    <div>
        <md-button @click="showDialog = true">阅读全文</md-button>
        <md-dialog :md-active.sync="showDialog">
            <md-dialog-title>{{title}}</md-dialog-title>
            <div>
                {{content}}
            </div>
            <md-dialog-actions>
                <md-button @click="showDialog = false">关闭</md-button>
            </md-dialog-actions>
        </md-dialog>
    </div>
</template>

<script>
    export default {
        name: "TheArticleShow",
        props: {
            title: {
                type: String,
                require: true
            },
            content: {
                type: String,
                require: true
            }
        },
        data () {
            return {
                showDialog: false
            }
        }
    }
</script>

<style scoped>

</style>