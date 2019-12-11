
export  default  {
    publishQuestion(http, params, callback) {
        http.post(
            '/question/questions',
            params,
        ).then(resp => {callback(resp);})
    }
};