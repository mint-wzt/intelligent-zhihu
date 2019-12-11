
const topic = {
    addTopic(http, params, callback) {
        http.post('/sys/topic/topics', params)
            .then(resp => {callback(resp)})
    },
    // updateTopic(http, params, callback) {
    //     http.post('/sys/topic/topics', params)
    //         .then(resp => {callback(resp)})
    // },
    getTopics(http, params, callback) {
        http.get('/sys/topic/topics', params)
            .then(resp => {callback(resp)})
    }
};

const user = {
    getUserByUsername(http, params, callback) {
        http.get('/sys/user/search', params)
            .then(resp => {
                callback(resp)
            })
    },
    getAllUser(http, params, callback) {
        http.get('/sys/user/users', params)
            .then(resp => {
                callback(resp)
            })
    },
    getAllAdmin(http, params, callback) {
        http.get('/sys/user/users/admin', params)
            .then(resp => {
                callback(resp)
            })
    },
    addAdminUser(http, params, callback) {
        http.post('/sys/user/users', params)
            .then(resp => {
                callback(resp);
            })
    },
    removeAdminUser(http, params, callback) {
        http.delete('/sys/user/users', {
            params: params
        })
            .then(resp => {
                callback(resp);
            })
    }
};

const article = {
    getAllArticles(http, callback) {
        http.get('/sys/article/articles')
            .then(resp => {callback(resp);})
    },
    deleteArticleById(http, params, callback) {
        http.delete('/sys/article/articles',
            {
                params: params
            }
        ).then(resp => {callback(resp);})
    }
};

export default {
    topic,
    user,
    article,
};