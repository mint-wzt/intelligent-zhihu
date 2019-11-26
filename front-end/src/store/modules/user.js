import {
    USER_SET_USER_INFO,
    USER_SET_USER_TOKEN,
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
    token: null
};

const getters = {
  isLogin: state => {
      return state.token != null
  }
};

const actions = {
};

const mutations = {
    [USER_SET_USER_TOKEN](state, {token}) {
      state.token = token
    },
    [USER_SET_USER_INFO](state, payload) {
        state.user_id = payload.id;
        state.avatar_url = payload.avtar_url;
        state.username = payload.username;
        state.nickname = payload.nickname;
        state.description = payload.description ? payload.description : "";
        state.career = payload.career ? payload.career : "";
        state.education = payload.education;
        state.name = payload.name ? payload.name: "";
        state.gender = payload.gender ? payload.gender: "";
        state.birthday = payload.birthday ? payload.birthday: "";
        state.phone = payload.phone ? payload.phone: "";
        state.email = payload.email;
        state.qq = payload.qq ? payload.qq : "";
        state.role = payload.role;
    }
};

export default {
    namespaced: true,
    getters,
    state,
    mutations,
    actions
}