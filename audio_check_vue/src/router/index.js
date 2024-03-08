import Vue from 'vue'
import VueRouter from 'vue-router'
import HelloView from '@/views/HelloView.vue'
import LoginView from '@/views/LoginView.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'hello',
    component: HelloView
  },
  {
    path: '/',
    name: 'hello',
    component: LoginView
  },
]

const router = new VueRouter({
  routes
})

export default router
