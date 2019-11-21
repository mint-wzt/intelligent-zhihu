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

Vue.config.productionTip = false;

Vue.use(ElementUI);
Vue.use(vueMaterial);
Vue.use(VueRouter);
Vue.use(VueInstant);

new Vue({
  router: new VueRouter({routes: Routes}),
  render: h => h(App),
}).$mount('#app');
