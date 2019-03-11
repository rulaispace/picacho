import Any from "./any";

const BaseHandler = Any.extend({
    create: function() {
        return this
    },
    setRef: function(refObj) {
        this.refObj = refObj
    }
})

export default BaseHandler