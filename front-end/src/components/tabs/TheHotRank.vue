<template>
    <div>
        <div v-if="items.length === 0">没有数据???</div>
        <el-card>
            <el-row v-if="items.length !== 0">
                <el-col>
                    <div>热榜问题</div>
                    <el-divider/>
                </el-col>
            </el-row>
            <el-row v-for="item in items"
                    :key="item.id"
            >
                <el-col>
                    <el-row>
                        <el-col>
                            <div style="font-size: 18px; margin-bottom: 4px;">{{item.content}}</div>
                        </el-col>
                    </el-row>

                    <el-row>
                        <el-col>
                            <el-button type="primary" plain size="mini" @click="handleReadAll">查看全文</el-button>
                        </el-col>
                    </el-row>

                    <el-divider/>
                </el-col>

            </el-row>
        </el-card>
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
                api.article.getHotArticle(this.$http,  resp=> {
                    resp.data.data.questions.forEach(item => {
                        this.items.push({
                            ...item,
                            content: item.content.substr(0,100),
                        });
                    })
                })
            },
            handleReadAll() {
                this.$router.push({
                    name: 'question_detail',
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