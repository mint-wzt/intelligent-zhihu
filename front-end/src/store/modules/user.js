import {
    USER_SET_USER_ID,
    USER_SET_USER_TOKEN,
    USER_SET_USER_AVATAR,
    USER_SET_USER_ROLE,
    USER_SET_USER_USERNAME,
    USER_SET_USER_NICKNAME,
} from '../mutations-type'

const state = {
    user_id: "",
    username: "",
    nickname: "",
    description: "",
    name: "",
    role: "",
    avatar_url: "",
    token: null,
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

const actions = {};

const mutations = {
    [USER_SET_USER_NICKNAME](state, {nickname}) {
        state.nickname = nickname;
    },
    [USER_SET_USER_TOKEN](state, {token}) {
        state.token = token;
    },
    [USER_SET_USER_AVATAR](state, {avatar_url}) {
        state.avatar_url = avatar_url;
    },
    [USER_SET_USER_ID](state, {user_id}) {
        state.user_id = user_id;
    },
    [USER_SET_USER_ROLE](state, {role}) {
        state.role = role;
    },
    [USER_SET_USER_USERNAME] (state, {username}) {
        state.username = username;
    }
};

export default {
    namespaced: true,
    getters,
    state,
    mutations,
    actions
}