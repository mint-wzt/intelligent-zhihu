import {
    USER_SET_USER_INFO,
    USER_SET_USER_TOKEN,
    USER_CLEAR_ALL,
    USER_SET_USER_AVATAR,
} from '../mutations-type'

const state = {
    user_id: "",
    username: "",
    nickname: "",
    description: "",
    career: "",
    education: "",
    name: "",
    gender: "",
    birthday: "",
    phone: "",
    email: "",
    qq: "",
    role: "",
    avatar_url: "",
    token: null,
    industry: null,
};

const getters = {
    isLogin: state => {
        return state.token != null && state.token.trim().length !== 0
    },
    isAdmin: state => {
        return state.role === 'ROLE_ADMIN' || state.role === 'ROLE_ROOT'
    },
    isRoot: state => {
        return state.role === 'ROLE_ROOT'
    }

};

const actions = {
};

const mutations = {
    [USER_SET_USER_TOKEN](state, {token}) {
      state.token = token
    },
    [USER_SET_USER_AVATAR](state, {avatar_url}) {
        state.avatar_url = avatar_url;
    },
    [USER_SET_USER_INFO](state, payload) {
        state.user_id = payload.user_id ? payload.user_id: "";
        state.avatar_url = payload.avatar_url ? payload.avatar_url: "";
        state.username = payload.username ? payload.username: "";
        state.nickname = payload.nickname ? payload.nickname: "";
        state.description = payload.description ? payload.description : "";
        state.career = payload.career ? payload.career : "";
        state.education = payload.education ? payload.education : "";
        state.name = payload.name ? payload.name: "";
        state.gender = payload.gender ? payload.gender: "";
        state.birthday = payload.birthday ? payload.birthday: "";
        state.phone = payload.phone ? payload.phone: "";
        state.email = payload.email ? payload.email: "";
        state.qq = payload.qq ? payload.qq : "";
        state.role = payload.role ? payload.role : "";
    },
    [USER_CLEAR_ALL](state) {
        state.user_id =  "";
        state.avatar_url = "";
        state.username = "";
        state.nickname = "";
        state.description = "";
        state.career =  "";
        state.education =  "";
        state.name =  "";
        state.gender =  "";
        state.birthday =  "";
        state.phone =  "";
        state.email =  "";
        state.qq = "";
        state.role = "";
        state.token = null;
    }
};

export default {
    namespaced: true,
    getters,
    state,
    mutations,
    actions
}