

const articleAPI = {
    getRecommendArticle(http, /*user_id,*/ callback) {
        http.get('/home/article/recommend', {
            // params: {
            //     userId: user_id
            // }
        }).then(resp => {
            callback(resp)
        })
    },
    getFollowArticle(http, callback) {
        http.get('/home/article/follow').then(resp => {
            callback(resp)
        })
    },
    getHotArticle(http, callback) {
        http.get('home/question/hot').then(resp => {
            callback(resp)
        })
    },
    theThumbsHasMe(http, params, callback) {
        http.post('/article/thumbs/hasme', params)
            .then(resp => {
                callback(resp);
            })
    },
    thumbIt(http, params, callback) {
        http.post('/article/thumbs', params)
            .then(resp => {
                callback(resp);
            })
    },
    getArticleById(http, params, callback) {
        http.get('/article/articles', params)
            .then(resp => {
                callback(resp);
            })
    },
    getArticleByUserId(http, params, callback) {
        http.get('/article/user/articles', {
            params: params,
        })
            .then(resp => {
                callback(resp);
            })
    },
    addComment(http, params, callback) {
        http.post('/article/comment', params)
            .then(resp => {
                callback(resp);
            })
    },
    getArticleCommentByArticleId(http, params, callback) {
        http.get('/article/article/comment', {
            params: params
        })
            .then(resp => {
                callback(resp);
            })
    },
    seach(http, params, callback) {
        http.get('/home/article/search', {
            params: params
        })
            .then(resp => {
                callback(resp);
            })
    },
    getFavoriteArticles(http, params, callback) {
        http.get('/user/collection/collections', {
            params: params,
        })
            .then(resp => {
                callback(resp);
            })
    },
    addFavoriteArticle(http, params, callback) {
        http.post('/user/collection/collections', params)
            .then(resp => {
                callback(resp);
            })
    },
    removeFavoriteArticle(http, params, callback) {
        http.delete('/user/collection/collections', {
            params: params
        })
            .then(resp => {
                callback(resp);
            })
    },
    hasFavoriteIt(http, params, callback) {
        http.post('/user/collection/collections/hasme', params)
            .then(resp => {
                callback(resp);
            })
    }
};

export default articleAPI;