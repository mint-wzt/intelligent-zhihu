<template>
    <div>
        <div class="md-title">我关注了谁</div>
        <el-card>

            <div v-for="user in this.follows" :key="user.id">
            <el-row >
                <el-col :span="4">
                    <el-avatar shape="square" :size="96" :src="user.avatarUrl"/>
                </el-col>
                <el-col :span="16">
                    <el-row><span
                            style="font-size: 18px; font-weight: bold;">{{user.nickName}}({{user.username}})</span>
                    </el-row>
                    <el-row>
                            <span class="ContentItem-status">
                                {{user.questions}} 回答 · {{user.articles}} 文章 · {{user.fans}} 关注者
                            </span>
                    </el-row>
                </el-col>
                <el-col :span="4">
                    <el-button size="small" type="danger" @click="handleUnfollow(user)" >取消关注</el-button>
                </el-col>
            </el-row>
                <el-divider/>
            </div>

        </el-card>
    </div>
</template>

<script>
    import api from '@/api';
    import {mapState} from 'vuex'
    // import qs from 'querystring'
    export default {
        name: "TheFollow",
        computed: {
            ...mapState('user', ['user_id'])
        },
        data() {
            return {
                follows: [],
            }
        },
        methods: {
            getFollows() {
                api.user.getFollow(
                    this.$http,
                    {
                        params: {
                            userId: this.user_id,
                        }
                    },
                    resp => {
                        if (resp.status === 200) {
                            resp.data.data.users.forEach(user => {
                                this.follows.push(user);
                            })
                        }
                    }
                )
            },
            handleUnfollow(user) {
                api.user.cancelFollow(
                    this.$http,
                    {
                        params: {
                            posUid: this.user_id,
                            negUid: user.id,
                        }
                    },
                    resp => {
                        if (resp.status === 200) {
                            this.$message.success('取消关注');
                        } else {
                            this.$message.error('网络错误');
                        }
                    }
                )
            }
        },
        mounted() {
            this.getFollows();
        }
    }
</script>

<style scoped>

    .ContentItem-status {
        margin-top: 10px;
        color: #8590a6;
        font-size: 14px;
    }
</style>