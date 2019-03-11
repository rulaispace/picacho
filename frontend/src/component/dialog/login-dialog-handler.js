import BaseHandler from "../../common/base/handler-base";
import commonNames from "../../common/config/common-name-config";
import message from './common-dialog-handler'

const Handler = BaseHandler.extend({
    login: function(callback) {
        this.refObj.setState({mode: commonNames.login, username: null, password: null, loginCallback: callback})
    },
    logout: function(callback) {
        message.alert({title: '系统提示', message: '确定要退出系统吗？',
            agreeCallback: () => {
                localStorage.removeItem(commonNames.token)
                localStorage.removeItem(commonNames.username)
                localStorage.removeItem(commonNames.rule)
                callback()
            }
        })
    }
})

export default Handler.create()