import Vue from 'vue'
import VueRouter from 'vue-router'
import Browse from '../views/Browse'
import Shop from '../views/Shop.vue'
import Search from '../views/Search.vue'
import Item from '../views/Item.vue'
Vue.use(VueRouter)

  const routes = [
  {
    path: '/',
    name: 'Browse',
    component: Browse
  },
    {
      path: '/search',
      name: 'Search',
      component: Search
    },
     {
          path: '/item/:type/:id',
          name: 'Item',
          component: Item
   },
  {
    path: '/shop',
    name: 'Shop',
    component: Shop
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    // component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  }
]

const router = new VueRouter({
  routes
})

export default router
