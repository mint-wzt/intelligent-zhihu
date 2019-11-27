<template>
    <div>
        <the-home-nav/>

        <el-row>
            <el-col :span="24" >
                <el-card class="card" :body-style="{ padding: '0px' }">
                    <img src="@/assets/examples/wutengchunzi.png" class="image">
                    <el-row>
                        <el-col :span="6">
                            <el-avatar
                                    :src="avatar_"
                                    shape="square"
                                    :size="168"
                                    class="avatar"
                            >
                            </el-avatar>
                        </el-col>
                        <el-col :span="18">
                            <el-row>
                                <el-col>
                                    <el-row style="margin-top: 10px">
                                        <el-col :span="20">
                                            <h1>{{username_}}</h1>
                                        </el-col>
                                    </el-row>
                                </el-col>
                            </el-row>

                            <el-row style="margin-top: 10px">
                                <el-col :span="4">性别</el-col>
                                <el-col :span="20">
                                    <el-row v-if="!showGenderEditor">
                                        <el-col :span="20">
                                            <span>{{gender_}}</span>
                                        </el-col>
                                        <el-col :span="4">
                                            <el-button icon="el-icon-edit" @click="showGenderEditor=true">修改</el-button>
                                        </el-col>
                                    </el-row>

                                    <el-row v-if="showGenderEditor">
                                        <el-col :span="16">
                                            <el-switch
                                                    v-model="gender_temp"
                                                    active-color="#13ce66"
                                                    inactive-color="#ff4949"
                                                    active-text="男"
                                                    active-value="男"
                                                    inactive-value="女"
                                                    inactive-text="女"
                                            ></el-switch>
                                        </el-col>
                                        <el-col :span="4">
                                            <el-button @click="this.updateGender">确定</el-button>
                                        </el-col>
                                        <el-col :span="4">
                                            <el-button @click="showGenderEditor = false">取消</el-button>
                                        </el-col>

                                    </el-row>
                                </el-col>
                            </el-row>
                            <el-divider></el-divider>
                            <el-row>
                                <el-col :span="4">个人简介</el-col>
                                <el-col :span="20">{{description_}}</el-col>
                            </el-row>
                            <el-divider></el-divider>
                            <el-row>
                                <el-col :span="4">昵称</el-col>
                                <el-col :span="20">{{nickname_}}</el-col>
                            </el-row>
                            <el-divider></el-divider>
                            <el-row>
                                <el-col :span="4">所在行业</el-col>
                                <el-col :span="20">{{industry_}}</el-col>
                            </el-row>
                            <el-divider></el-divider>
                            <el-row>
                                <el-col :span="4">职业</el-col>
                                <el-col :span="20">{{career_}}</el-col>
                            </el-row>
                            <el-divider></el-divider>
                            <el-row>
                                <el-col :span="4">教育水平</el-col>
                                <el-col :span="20">{{education_}}</el-col>
                            </el-row>
                            <el-divider></el-divider>
                            <el-row>
                                <el-col :span="4">姓名</el-col>
                                <el-col :span="20">{{name_}}</el-col>
                            </el-row>
                            <el-divider></el-divider>
                            <el-row>
                                <el-col :span="4">生日</el-col>
                                <el-col :span="20">{{birthday_}}</el-col>
                            </el-row>
                            <el-divider></el-divider>
                            <el-divider></el-divider>
                            <el-row>
                                <el-col :span="4">电话</el-col>
                                <el-col :span="20">{{phone_}}</el-col>
                            </el-row>
                            <el-divider></el-divider>
                            <el-row>
                                <el-col :span="4">邮箱</el-col>
                                <el-col :span="20">{{email_}}</el-col>
                            </el-row>
                            <el-divider></el-divider>

                            <el-row>
                                <el-col :span="4">QQ</el-col>
                                <el-col :span="20">{{qq_}}</el-col>
                            </el-row>

                        </el-col>
                    </el-row>

                </el-card>
            </el-col>
        </el-row>

    </div>
</template>

<script>
    import TheHomeNav from '@/components/TheHomeNav.vue'
    import {mapState} from 'vuex'
    // import qs from 'querystring'
    export default {
        name: "ThePersonInfoEditPage",
        components: {
            TheHomeNav
        },
        computed: {
            ...mapState('user', ['user_id', 'token'])
        },
        data() {
            return {
                showGenderEditor: false,
                gender_temp: null,

                username_: null,
                gender_ : null,
                nickname_: null,
                industry_: null,
                career_: null,
                description_: null,
                name_: null,
                birthday_: null,
                phone_: null,
                email_: null,
                qq_: null,
                avatar_: null,
                education_: null,
            }
        },
        methods: {
            getUserInfo() {
                this.$http.get('/user/users/'+this.user_id,
                    {
                        headers: {
                            "Authorization": this.token,
                        }
                    }
                ).then(resp => {
                    if (resp.status === 200) {
                        const {data} = resp.data;
                        this.username_ = data.username;
                        this.avatar_ = data.avtar_url;
                        this.birthday_ = data.birthday ? data.birthday: '你没有填写该信息';
                        this.career_ = data.career ? data.career: '你没有填写该信息';
                        this.description_ = data.description ? data.description: '';
                        this.education_ = data.education ? data.education: '你没有填写该信息';
                        this.email_ = data.email ? data.email: '你没有填写该信息';
                        this.gender_ = data.gender ? data.gender: '你没有填写该信息';
                        this.industry_ = data.industry ? data.industry: '你没有填写该信息';
                        this.name_ = data.name ? data.name: '你没有填写该信息';
                        this.nickname_ = data.nickname ? data.nickname: '你没有填写该信息';
                        this.phone_ = data.phone ? data.phone: '你没有填写该信息';
                        this.qq_ = data.qq ? data.qq: '你没有填写该信息';
                    }
                })
            },

            updateGender() {
                this.$http.post('/user/users/',
                    {
                        id: this.user_id,
                        gender: this.gender_temp
                    }, {
                        headers: {
                            "Authorization": this.token,
                        }
                    }
                ).then(resp => {
                    if (resp.status === 200) {
                        const {gender} = resp.data.data;
                        this.gender_ = this.gender_temp = gender;
                        this.showGenderEditor = false;
                    }
                })
            },

        },
        mounted() {
            this.getUserInfo()
        }
    }
</script>

<style scoped>
    .card {
        width: 1000px;
        margin: 14px auto;
        padding-bottom: 70px;
    }

    .image {
        width: 100%;
        display: block;
    }

    .avatar {
        margin-top: -25px;
        margin-left: 25px;
        z-index: 1;
        background-color: #fff;
        border: 4px solid #fff;
        border-radius: 8px;
    }

</style>