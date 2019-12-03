import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import 'material-design-icons/iconfont/material-icons.css'
import vueMaterial from 'vue-material'
import 'vue-material/dist/vue-material.min.css'
import 'vue-material/dist/theme/default.css'
import Routes from './route'
import VueRouter from 'vue-router'
import 'vue-instant/dist/vue-instant.css'
import VueInstant from 'vue-instant/dist/vue-instant.common'
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
import axios from 'axios'
import store from './store'
import VueLocalStorage from "vue-localstorage";
import 'vue-croppa/dist/vue-croppa.css'
import Croppa from "vue-croppa";
import VueDataTables from 'vue-data-tables'

import {
    USER_SET_USER_ID,
    USER_SET_USER_TOKEN,
    USER_SET_USER_AVATAR,
    USER_SET_USER_ROLE,
    USER_SET_USER_USERNAME,
    USER_SET_USER_NICKNAME,
} from '@/store/mutations-type'

Vue.config.productionTip = false;

Vue.use(ElementUI);
Vue.use(VueDataTables);
Vue.use(vueMaterial);
Vue.use(VueRouter);
Vue.use(VueInstant);
Vue.use(mavonEditor);
Vue.use(VueLocalStorage);
Vue.use(Croppa);

const service = axios.create({
    baseURL: 'http://localhost:8081',
    timeout: 3000,
});

service.interceptors.request.use(
    config => {
        if (store.state.user.token !== undefined) {
            config.headers['Authorization'] = store.state.user.token
        }
        return config
    },
    error => {
        console.log(error);
        return Promise.reject(error)
    }
);

service.interceptors.response.use(
    resp => {
        console.log(resp);
        return resp;
    },
    error => {
        console.log(error);
        return error
    }
);

Vue.prototype.$http = service;

new Vue({
    localStorage: {
        userInfo: {
            type: Object,
        }
    },
    router: new VueRouter({routes: Routes}),
    store,
    render: h => h(App),
    created() {
        const state = this.$localStorage.get('state', null);
        if (state !== null) {
            const {user} = JSON.parse(state);
            this.$store.commit('user/' + USER_SET_USER_ID, user);
            this.$store.commit('user/' + USER_SET_USER_TOKEN, user);
            this.$store.commit('user/' + USER_SET_USER_AVATAR, user);
            this.$store.commit('user/' + USER_SET_USER_ROLE, user);
            this.$store.commit('user/' + USER_SET_USER_USERNAME, user);
            this.$store.commit("user/" + USER_SET_USER_NICKNAME, user);
        }
    }
}).$mount('#app');
