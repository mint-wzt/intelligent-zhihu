const routes = [
    {
        path: '/',
        name: 'home',
        component: () => import('@/pages/TheHomePage.vue'),
        children: [
            {
                path:'/tabs/recommend',
                name: 'recommend',
                props: { data: 'attrs' },
                component: () => import('@/components/tabs/TheRecommend.vue')
            }, {
                path: '/tabs/attention',
                name: 'attention',
                component: () => import('@/components/tabs/TheAttentionList.vue')
            }, {
                path: '/tabs/hotrank',
                name: 'hotrank',
                component: () => import('@/components/tabs/TheHotRank.vue')
            }
        ]
    }
];

export default routes



