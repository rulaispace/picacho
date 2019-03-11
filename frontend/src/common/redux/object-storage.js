import Any from "../base/any";

export const DefaultLocalReduxStateStorageKey = 'redux-store'

const ObjectStorage = Any.extend({
    create: function(key=DefaultLocalReduxStateStorageKey) {
        this.key = key
        return this
    },
    clear: function() {
        localStorage.removeItem(this.key)
    },
    read: function() {
        return this.isEmpty() ? null : JSON.parse(localStorage.getItem(this.key))
    },
    isEmpty: function() {
        return localStorage.getItem(this.key) == null
    },
    update: function(state) {
        state != null ?
            localStorage.setItem(this.key, JSON.stringify(state)) :
            this.clear()
    },
})

export default ObjectStorage
