const routes = [
    {
        path: '/',
        component: () => import('@/pages/TheHomePage.vue'),
        children: [
            {
                path:'',
                name: 'recommend',
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
    }, {
        path: "/article",
        name: "article",
        component: () => import('@/pages/TheArticleEditPage.vue')
    }, {
        path: "/article/detail/:id",
        name: 'article_detail',
        props: true,
        component: () => import('@/pages/TheArticleDetailPage.vue')
    }, {
        path: "/person/me",
        props: true,
        component: () => import('@/pages/ThePersonPage.vue'),
        children: [
            {
                path: "",
                name: "myarticle",
                component: () => import('@/components/tabs/TheMyArticles.vue')
            }
        ]
    }
];

export default routes



