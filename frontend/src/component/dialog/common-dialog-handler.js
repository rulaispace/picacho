import commonNames from "../../common/config/common-name-config";
import BaseHandler from "../../common/base/handler-base";

const Handler = BaseHandler.extend({
    tip: function({title, message, agreeCallback}) {
        return this.display({type: commonNames.tip, title, message, agreeCallback,})
    },
    alert: function({title, message, agreeCallback, disagreeCallback}) {
        return this.display({type: commonNames.alert, title, message, agreeCallback, disagreeCallback})
    },
    display: function({type, title, message, agreeCallback=f=>f, disagreeCallback= f=>f}) {
        this.refObj.setState({open: true, type: type, title: title, message: message,
            agreeCallback: function(refObj) {
                return function() {
                    refObj.setState({...refObj.state, open: false})
                    agreeCallback()
                }
            }(this.refObj),
            disagreeCallback: function(refObj) {
                return function() {
                    refObj.setState({...refObj.state, open: false})
                    disagreeCallback()
                }
            }(this.refObj)
        })
    }
})

export default Handler.create()