

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
    }
};

export default articleAPI;