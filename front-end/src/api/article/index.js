

const articleAPI = {
    getRecommendArticle(http, user_id, callback) {
        http.get('/home/article/recommend', {
            params: {
                userId: user_id
            }
        }).then(resp => {
            callback(resp)
        })
    },
    getFollowArticle(http, user_id, callback) {
        http.get('/home/article/follow', {
            params: {
                userId: user_id
            }
        }).then(resp => {
            callback(resp)
        })
    },
    getHotArticle(http, user_id, callback) {
        http.get('home/question/hot', {
            params: {
                userId: user_id
            }
        }).then(resp => {
            callback(resp)
        })
    },
    theThumbsHasMe(http, params, callback) {
        http.post('/article/thumbs/hasme', params)
            .then(resp => {callback(resp);})
    },
    thumbIt(http, params, callback) {
        http.post('/article/thumbs', params)
            .then(resp => {callback(resp);})
    },
    getArticleById(http, params, callback) {
        http.get('/article/articles', params)
            .then(resp => {callback(resp);})
    },

};

export default articleAPI;