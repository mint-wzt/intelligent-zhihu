export default  {
    login(http, params, callback) {
        http.post('/auth/login', params)
            .then(resp => callback(resp))
    },
    register(http, params, callback) {
        http.post('/auth/register', params)
            .then(resp => callback(resp))
    }
}