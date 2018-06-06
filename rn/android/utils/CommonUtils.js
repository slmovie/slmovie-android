/**
 * Created by 包俊 on 2017/8/7.
 */
exports.isEmptyObj = function (obj) {
    for (let n in obj) {
        return false
    }
    return true
};