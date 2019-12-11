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
                                        <el-col :span="4">
                                            <el-button @click="navigateToPersonPage">返回主页</el-button>
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
                                                    v-model="gender_"
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
                                <el-col :span="20">
                                    <el-row v-if="!showDescriptionEditor">
                                        <el-col :span="20">
                                            <span>{{description_}}</span>
                                        </el-col>
                                        <el-col :span="4">
                                            <el-button icon="el-icon-edit" @click="showDescriptionEditor=true">修改</el-button>
                                        </el-col>
                                    </el-row>

                                    <el-row v-if="showDescriptionEditor">
                                        <el-col :span="18">
                                            <el-input v-model="description_"  maxlength="30"
                                                      show-word-limit placeholder="请输入内容"></el-input>
                                        </el-col>
                                        <el-col :span="3">
                                            <el-button @click="this.updateDescription">确定</el-button>
                                        </el-col>
                                        <el-col :span="3">
                                            <el-button @click="showDescriptionEditor = false">取消</el-button>
                                        </el-col>
                                    </el-row>
                                </el-col>
                            </el-row>
                            <el-divider></el-divider>
                            <el-row>
                                <el-col :span="4">昵称</el-col>
                                <el-col :span="20">
                                    <el-row v-if="!showNicknameEditor">
                                        <el-col :span="20">
                                            <span>{{nickname_}}</span>
                                        </el-col>
                                        <el-col :span="4">
                                            <el-button icon="el-icon-edit" @click="showNicknameEditor=true">修改</el-button>
                                        </el-col>
                                    </el-row>

                                    <el-row v-if="showNicknameEditor">
                                        <el-col :span="18">
                                            <el-input v-model="nickname_"  maxlength="30"
                                                      show-word-limit placeholder="请输入内容"></el-input>
                                        </el-col>
                                        <el-col :span="3">
                                            <el-button @click="this.updateNickname">确定</el-button>
                                        </el-col>
                                        <el-col :span="3">
                                            <el-button @click="showNicknameEditor = false">取消</el-button>
                                        </el-col>
                                    </el-row>
                                </el-col>
                            </el-row>
                            <el-divider></el-divider>
                            <el-row>
                                <el-col :span="4">所在行业</el-col>
                                <el-col :span="20">
                                    <el-row v-if="!showIndustryEditor">
                                        <el-col :span="20">
                                            <span>{{industry_}}</span>
                                        </el-col>
                                        <el-col :span="4">
                                            <el-button icon="el-icon-edit" @click="showIndustryEditor=true">修改</el-button>
                                        </el-col>
                                    </el-row>

                                    <el-row v-if="showIndustryEditor">
                                        <el-col :span="18">
                                            <el-input v-model="industry_"  maxlength="30"
                                                      show-word-limit placeholder="请输入内容"></el-input>
                                        </el-col>
                                        <el-col :span="3">
                                            <el-button @click="this.updateIndustry">确定</el-button>
                                        </el-col>
                                        <el-col :span="3">
                                            <el-button @click="showIndustryEditor = false">取消</el-button>
                                        </el-col>
                                    </el-row>
                                </el-col>
                            </el-row>
                            <el-divider></el-divider>
                            <el-row>
                                <el-col :span="4">职业</el-col>
                                <el-col :span="20">
                                    <el-row v-if="!showCareerEditor">
                                        <el-col :span="20">
                                            <span>{{career_}}</span>
                                        </el-col>
                                        <el-col :span="4">
                                            <el-button icon="el-icon-edit" @click="showCareerEditor=true">修改</el-button>
                                        </el-col>
                                    </el-row>

                                    <el-row v-if="showCareerEditor">
                                        <el-col :span="18">
                                            <el-input v-model="career_"  maxlength="30"
                                                      show-word-limit placeholder="请输入内容"></el-input>
                                        </el-col>
                                        <el-col :span="3">
                                            <el-button @click="this.updateCareerInfo">确定</el-button>
                                        </el-col>
                                        <el-col :span="3">
                                            <el-button @click="showCareerEditor = false">取消</el-button>
                                        </el-col>
                                    </el-row>
                                </el-col>
                            </el-row>
                            <el-divider></el-divider>
                            <el-row>
                                <el-col :span="4">教育水平</el-col>
                                <el-col :span="20">
                                    <el-row v-if="!showEducationEditor">
                                        <el-col :span="20">
                                            <span>{{education_}}</span>
                                        </el-col>
                                        <el-col :span="4">
                                            <el-button icon="el-icon-edit" @click="showEducationEditor=true">修改</el-button>
                                        </el-col>
                                    </el-row>

                                    <el-row v-if="showEducationEditor">
                                        <el-col :span="18">
                                            <el-input v-model="education_"  maxlength="30"
                                                      show-word-limit placeholder="请输入内容"></el-input>
                                        </el-col>
                                        <el-col :span="3">
                                            <el-button @click="this.updateEducationInfo">确定</el-button>
                                        </el-col>
                                        <el-col :span="3">
                                            <el-button @click="showEducationEditor = false">取消</el-button>
                                        </el-col>
                                    </el-row>
                                </el-col>
                            </el-row>
                            <el-divider></el-divider>
                            <el-row>
                                <el-col :span="4">姓名</el-col>
                                <el-col :span="20">
                                    <el-row v-if="!showNameEditor">
                                        <el-col :span="20">
                                            <span>{{name_}}</span>
                                        </el-col>
                                        <el-col :span="4">
                                            <el-button icon="el-icon-edit" @click="showNameEditor=true">修改</el-button>
                                        </el-col>
                                    </el-row>

                                    <el-row v-if="showNameEditor">
                                        <el-col :span="18">
                                            <el-input v-model="name_"  maxlength="30"
                                                      show-word-limit placeholder="请输入内容"></el-input>
                                        </el-col>
                                        <el-col :span="3">
                                            <el-button @click="this.updateNameInfo">确定</el-button>
                                        </el-col>
                                        <el-col :span="3">
                                            <el-button @click="showNameEditor = false">取消</el-button>
                                        </el-col>
                                    </el-row>
                                </el-col>
                            </el-row>
                            <el-divider></el-divider>
                            <el-row>
                                <el-col :span="4">生日</el-col>
                                <el-col :span="20">
                                    <el-row v-if="!showBirthdayEditor">
                                        <el-col :span="20">
                                            <span>{{birthday_ ? birthday_ : '你没有填写该信息'}}</span>
                                        </el-col>
                                        <el-col :span="4">
                                            <el-button icon="el-icon-edit" @click="showBirthdayEditor=true">修改</el-button>
                                        </el-col>
                                    </el-row>

                                    <el-row v-if="showBirthdayEditor">
                                        <el-col :span="18">
                                            <md-datepicker
                                                    v-model="birthday_"
                                            />
                                        </el-col>
                                        <el-col :span="3">
                                            <el-button @click="this.updateBirthdayInfo">确定</el-button>
                                        </el-col>
                                        <el-col :span="3">
                                            <el-button @click="showBirthdayEditor = false">取消</el-button>
                                        </el-col>
                                    </el-row>
                                </el-col>
                            </el-row>
                            <el-divider></el-divider>
                            <el-row>
                                <el-col :span="4">电话</el-col>
                                <el-col :span="20">
                                    <el-row v-if="!showPhoneEditor">
                                        <el-col :span="20">
                                            <span>{{phone_}}</span>
                                        </el-col>
                                        <el-col :span="4">
                                            <el-button icon="el-icon-edit" @click="showPhoneEditor=true">修改</el-button>
                                        </el-col>
                                    </el-row>

                                    <el-row v-if="showPhoneEditor">
                                        <el-col :span="18">
                                            <el-input v-model="phone_"  maxlength="30"
                                                      show-word-limit placeholder="请输入内容"></el-input>
                                        </el-col>
                                        <el-col :span="3">
                                            <el-button @click="this.updatePhoneInfo">确定</el-button>
                                        </el-col>
                                        <el-col :span="3">
                                            <el-button @click="showPhoneEditor = false">取消</el-button>
                                        </el-col>
                                    </el-row>
                                </el-col>
                            </el-row>
                            <el-divider></el-divider>
                            <el-row>
                                <el-col :span="4">邮箱</el-col>
                                <el-col :span="20">
                                    <el-row v-if="!showEmailEditor">
                                        <el-col :span="20">
                                            <span>{{email_}}</span>
                                        </el-col>
                                        <el-col :span="4">
                                            <el-button icon="el-icon-edit" @click="showEmailEditor=true">修改</el-button>
                                        </el-col>
                                    </el-row>

                                    <el-row v-if="showEmailEditor">
                                        <el-col :span="18">
                                            <el-input v-model="email_"  maxlength="30"
                                                      show-word-limit placeholder="请输入内容"></el-input>
                                        </el-col>
                                        <el-col :span="3">
                                            <el-button @click="this.updateEmailInfo">确定</el-button>
                                        </el-col>
                                        <el-col :span="3">
                                            <el-button @click="showEmailEditor = false">取消</el-button>
                                        </el-col>
                                    </el-row>
                                </el-col>
                            </el-row>
                            <el-divider></el-divider>

                            <el-row>
                                <el-col :span="4">QQ</el-col>
                                <el-col :span="20">
                                    <el-row v-if="!showQQEditor">
                                        <el-col :span="20">
                                            <span>{{qq_}}</span>
                                        </el-col>
                                        <el-col :span="4">
                                            <el-button icon="el-icon-edit" @click="showQQEditor=true">修改</el-button>
                                        </el-col>
                                    </el-row>

                                    <el-row v-if="showQQEditor">
                                        <el-col :span="18">
                                            <el-input v-model="qq_"  maxlength="30"
                                                      show-word-limit placeholder="请输入内容"></el-input>
                                        </el-col>
                                        <el-col :span="3">
                                            <el-button @click="this.updateQQInfo">确定</el-button>
                                        </el-col>
                                        <el-col :span="3">
                                            <el-button @click="showQQEditor = false">取消</el-button>
                                        </el-col>
                                    </el-row>
                                </el-col>
                            </el-row>

                        </el-col>
                    </el-row>

                </el-card>
            </el-col>
        </el-row>

    </div>
</template>

<script>
    import format from 'date-fns/format'
    import TheHomeNav from '@/components/TheHomeNav.vue'
    import {mapState} from 'vuex'
    // import {
    //     USER_SET_USER_AVATAR,
    // } from '@/store/mutations-type.js'
    export default {
        name: "ThePersonInfoEditPage",
        components: {
            TheHomeNav
        },
        computed: {
            ...mapState('user', ['user_id', 'token'])
        },
        data() {
            let dateFormat = 'yyyy-MM-dd';
            let now = new Date();
            return {
                showGenderEditor: false,
                showDescriptionEditor: false,
                showNicknameEditor: false,
                showIndustryEditor: false,
                showCareerEditor: false,
                showEducationEditor: false,
                showNameEditor: false,
                showBirthdayEditor: false,
                showPhoneEditor:false,
                showEmailEditor: false,
                showQQEditor: false,

                username_: null,
                gender_ : null,
                nickname_: null,
                industry_: null,
                career_: null,
                description_: null,
                name_: null,
                birthday_: format(now, dateFormat),
                phone_: null,
                email_: null,
                qq_: null,
                avatar_: null,
                education_: null,

            }
        },
        methods: {
            navigateToPersonPage() {
                this.$router.push({name:'myarticle'})
            },
            getUserInfo() {
                this.$http.get('/user/users/'+this.user_id,
                    {
                        headers: {
                            "Authorization": this.token,
                        }
                    }
                ).then(resp => {
                    if (resp.status === 200) {
                        const data = resp.data.data.userInfo;
                        this.username_ = data.username;
                        this.avatar_ = data.avatarUrl;
                        this.birthday_ =  format(data.birthday, "yyyy-MM-dd");
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

            updateUserInfo(callback, params) {
                this.$http.post('/user/users/',
                    params,
                    {
                        headers: {
                            "Authorization": this.token,
                        }
                    }
                ).then(resp => {
                    console.log(resp);
                    callback(resp);
                })
            },

            updateEmailInfo() {
                this.updateUserInfo((resp) => {
                    if (resp.status === 200) {
                        const {email} = resp.data.data;
                        this.email_ = email;
                        this.showEmailEditor = false;
                    }
                }, {
                    id: this.user_id,
                    email: this.email_,
                })
            },

            updateQQInfo() {
                this.updateUserInfo((resp) => {
                    if (resp.status === 200) {
                        const {qq} = resp.data.data;
                        this.qq_ = qq;
                        this.showQQEditor = false;
                    }
                }, {
                    id: this.user_id,
                    qq: this.qq_,
                })
            },

            updatePhoneInfo() {
                this.updateUserInfo((resp) => {
                    if (resp.status === 200) {
                        const {phone} = resp.data.data;
                        this.phone_ = phone;
                        this.showPhoneEditor = false;
                    }
                }, {
                    id: this.user_id,
                    phone: this.phone_,
                })
            },

            updateBirthdayInfo() {
                this.updateUserInfo((resp) => {
                    if (resp.status === 200) {
                        const {birthday} = resp.data.data;
                        this.birthday_ = birthday;
                        this.showBirthdayEditor = false;
                    }
                }, {
                    id: this.user_id,
                    birthday: this.birthday_,
                })
            },

            updateNameInfo() {
                this.updateUserInfo((resp) => {
                    if (resp.status === 200) {
                        const {name} = resp.data.data;
                        this.name_ = name;
                        this.showNameEditor = false;
                    }
                }, {
                    id: this.user_id,
                    name: this.name_,
                })
            },
            updateEducationInfo() {
                this.updateUserInfo((resp) => {
                    if (resp.status === 200) {
                        const {education} = resp.data.data;
                        this.education_ = education;
                        this.showEducationEditor = false;
                    }
                }, {
                    id: this.user_id,
                    education: this.education_,
                })
            },

            updateIndustry() {
                this.updateUserInfo((resp) => {
                    if (resp.status === 200) {
                        const {industry} = resp.data.data;
                        this.industry_ = industry;
                        this.showIndustryEditor = false;
                    }
                }, {
                    id: this.user_id,
                    industry: this.industry_,
                })
            },

            updateCareerInfo() {
                this.updateUserInfo((resp) => {
                    if (resp.status === 200) {
                        const {career} = resp.data.data;
                        this.career_ = career;
                        this.showCareerEditor = false;
                    }
                }, {
                    id: this.user_id,
                    career: this.career_,
                })
            },

            updateGender() {
                this.updateUserInfo((resp) => {
                    if (resp.status === 200) {
                        const {gender} = resp.data.data;
                        this.gender_ = gender;
                        this.showGenderEditor = false;
                    }
                }, {
                    id: this.user_id,
                    gender: this.gender_,
                });
            },

            updateDescription() {
                this.updateUserInfo((resp) => {
                    if (resp.status === 200) {
                        const {description} = resp.data.data;
                        this.description_ = description;
                        this.showDescriptionEditor = false;
                    }
                }, {
                    id: this.user_id,
                    description: this.description_,
                })
            },

            updateNickname() {
                this.updateUserInfo((resp) => {
                    if (resp.status === 200) {
                        const {nickname} = resp.data.data;
                        this.nickname_ = nickname;
                        this.showNicknameEditor = false;
                    }
                }, {
                    id: this.user_id,
                    nickName: this.nickname_,
                })
            }

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