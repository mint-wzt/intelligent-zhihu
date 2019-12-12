export default  {
    login(http, params, callback) {
        http.post('/auth/login', params)
            .then(resp => callback(resp))
    },
    register(http, params, callback) {
        http.post('/auth/register', params)
            .then(resp => callback(resp))
    },
    followUser(http, params, callback) {
        http.post('/user_focus/follow', params)
            .then(resp => callback(resp))
    },
    cancelFollow(http, params, callback) {
        http.delete('/user_focus/unfollow', params)
            .then(resp => callback(resp))
    },
    getFollow(http, params, callback) {
        http.get('/user_focus/users', params)
            .then(resp => callback(resp))
    }
}