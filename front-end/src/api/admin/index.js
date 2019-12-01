
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
    }
};

export default {
    topic,
    user
};