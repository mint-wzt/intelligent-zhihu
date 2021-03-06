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
            },
            {
                path: '/favorite',
                name: 'myfavorite',
                component: () => import('@/components/tabs/TheMyFavorite.vue')
            },
            {
                path: '/answer',
                name: 'my_answer',
                component: () => import('@/components/tabs/TheMyAnswer.vue')
            },
            {
                path: '/follow',
                name: 'my_follow',
                component: () => import('@/components/tabs/TheFollow.vue')
            }
        ]
    }, {
        path: '/admin',
        props:true,

        component: () => import('@/pages/TheAdminPage.vue'),
        children: [
            {
                path: "",
                name: 'admin',
                component: () => import('@/components/TheArticleManage.vue')
            },
            {
                path: "/user",
                name: "manage_user",
                component: () => import('@/components/TheUserManage.vue')
            },
            {
                path: "/topic",
                name: "manage_topic",
                component: () => import('@/components/TheAdminTopics.vue')
            }
        ]
    }, {
        path: "/register",
        name: "register",
        component: () => import('@/pages/TheRegisterPage.vue')
    }, {
        path: "/login",
        name: "login",
        component: () => import('@/pages/TheLoginPage.vue')
    }, {
        path: '/userinfo',
        name: 'userinfo_edit',
        component: () => import('@/pages/ThePersonInfoEditPage.vue')
    }, {
        path: '/topic',
        name: 'topics',
        component: () => import('@/pages/TheTopicPage.vue')
    }, {
        path: '/question/edit',
        name: 'question_edit',
        component: () => import('@/pages/TheQuestionEditPage.vue')
    },
    {
        path: '/question/list',
        name: 'questions_list',
        component: () => import('@/pages/TheQuestionListPage.vue')
    },
    {
        path: "/search/result",
        name: 'search_result',
        props: true,
        component: () => import('@/pages/TheSearchResultPage.vue')
    },
    {
        path: '/question/detail/',
        name: 'question_detail',
        props: true,
        component: () => import('@/pages/TheQuestionDetailPage.vue')
    },
    {
        path : '/question/answer/edit/',
        name: 'question_answer_edit',
        props: true,
        component: () => import('@/pages/TheAnswerEditPage.vue')
    }
];

export default routes



