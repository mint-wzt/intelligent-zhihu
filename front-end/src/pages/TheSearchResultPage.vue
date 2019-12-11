<template>
    <div>
        <the-home-nav :isSearchResultPage="true"/>
        <el-row type="flex" justify="center">
            <el-col :span="18">
                <div v-if="this.results.length === 0" style="margin-top: 10rem;">
                    <md-empty-state
                            md-rounded
                            md-icon="access_time"
                            md-label="喵喵喵？"
                            md-description="啥也没有，要不咱换个关键字试试？">
                    </md-empty-state>
                </div>
                <div v-else style="margin-top: 2rem;">
                    <el-card>
                        <el-row type="flex" justify="center">
                            <el-col :span="4">
                                <div style="font-size: 18px;">共搜到{{results.length}}条记录</div>
                            </el-col>
                        </el-row>
                        <el-row v-for="item in results" :key="item.id">
                            <el-row>
                                <el-col>
                                    <div style="font-size: 16px">{{item.title}}</div>
                                </el-col>
                                <el-col>
                                    <div style="font-size: 13px">{{item.author}}</div>
                                </el-col>
                            </el-row>
                            <el-row>
                                <el-col>
                                    <div style="font-size: 12px;">{{item.content}}</div>
                                </el-col>
                            </el-row>
                            <el-row>
                                <el-col>
                                    <el-button type="primary" @click="navigateTo(item.id)">查看全文</el-button>
                                </el-col>
                            </el-row>
                            <el-divider/>
                        </el-row>
                    </el-card>
                </div>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    import TheHomeNav from '@/components/TheHomeNav.vue'

    export default {
        name: "TheSearchResultPage",
        components: {
            TheHomeNav,
        },
        props: {
            results: {
                type: Array,
                required: true,
            }
        },
        methods: {
            navigateTo(id) {
                this.$router.push({
                    name: 'article_detail',
                    params: {
                        id: id
                    }
                })
            }
        }
    }
</script>

<style scoped>
</style>