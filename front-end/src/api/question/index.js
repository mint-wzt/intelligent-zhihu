
export  default  {
    publishQuestion(http, params, callback) {
        http.post(
            '/question/questions',
            params,
        ).then(resp => {callback(resp);})
    },
    uploadImage(http, params, callback) {
        http.post(
            '/article/image/images',
            params,
        ).then(resp => {callback(resp);})
    },
    publishAnswer(http, params, callback) {
        http.post(
            '/article/articles',
            params,
        ).then(resp => {callback(resp);})
    },
    followQuestion(http, params, callback) {
        http.post(
            '/question/questions/follow',
            params,
        ).then(resp => {callback(resp);})
    },
    isQuetsion(item) {
        return item.questionId != null && item.questionId.trim() !== "";
    }
};