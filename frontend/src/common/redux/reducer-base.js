import Any from "../base/any";
import commonNames from "../config/common-name-config";

const defaultTypes = {
    default: 'default',
}

const defaultReducer = {
    type: defaultTypes.default,
    action: function (payload) {
        return {
            type: defaultTypes.default,
            payload,
        }
    },
    reduce: function (state = {}) {
        return {
            ...state
        }
    }
}

const search = function(data, id) {
    if (data == null) return null

    for (const index in data) {
        const item = data[index]

        if (item.id === id) return item

        const child = search(item.children, id)
        if (child != null) return child
    }

    return null;
}

const collapseAll = function(children) {
    if (children == null) return children

    for (const property in children) {
        children[property].expanded = false;
        collapseAll(children[property].children)
    }

    return children
}

const expandWithValue = function(root, children, id, value) {
    if (!value.length) return children
    if (children == null) return children
    for (const property in children) {
        if (children[property][id].indexOf(value) !== -1) {
            expand2Target(root, null, '' + children[property].path)
        }

        expandWithValue(root, children[property].children, id, value)
    }

    return children
}

const expand2Target = function(children, path, target) {
    if (path == null) path = ''

    const key = target.slice(path.length).split('.')[0]
    if (!key.length) return children

    for (const property in children) {
        if (children[property].id == key) {
            children[property].expanded = true
            expand2Target(children[property].children, path + key + '.', target)
        }
    }

    return children
}

const decorate = function(children, path, target, parentCode, parentName) {
    if (children == null) return children

    for (const property in children) {
        children[property].parentCode = parentCode
        children[property].parentName = parentName
        children[property].path = path ? path + '.' + children[property].id : children[property].id

        // 判断是会否需要展开
         if (target.startsWith(children[property].path))
             children[property].expanded = true

        decorate(children[property].children,
            children[property].path,
            target,
            children[property].id,
            parentName + children[property].primaryText + '/',
        )
    }

    return children
}

const ReducerBase = Any.extend({
    create: function() {
        const args = Array.prototype.slice.call(arguments)
        const types = args.length > 0 ? args[0] : defaultTypes
        const reducers = args.length > 1 ? args[1] : [defaultReducer]

        this.types = types
        this.reducers = reducers

        return this
    },
    createAction: function(type, payload) {
        const specificReducer = this.reducers.filter(reducer => {return reducer.type === type})[0]
        const reducer = specificReducer ? specificReducer : defaultReducer
        return reducer.action(payload)
    },
    reduce: function(state={}, action) {
        const specificReducer = this.reducers.filter(reducer => {return reducer.type === action.type})[0]
        const reducer = specificReducer ? specificReducer : defaultReducer
        return reducer.reduce(state, action.payload)
    },
    proxy: function() {
        const self = this
        return function(state, action) {
            return self.reduce.call(self, state, action)
        }
    },
    setStore: function(store) {
        this.store = store
    },
})

ReducerBase.defaultAction = function(type) {
    return (payload) => ({type, payload})
}

ReducerBase.defaultTableReduce = function() {
    return (state, payload) => {
        state.table.body = payload
        return state
    }
}

ReducerBase.defaultTableFilterReduce = function(id) {
    return (state={}, payload) => {
        state.toolbar.input.defaultValue = payload.value
        state.table.filter[id] = payload.value

        return state
    }
}

ReducerBase.defaultChangePageOfTable = function() {
    return (state={}, payload) => {
        state.table.pagination.page = payload.page

        return state
    }
}

ReducerBase.defaultChangeRowsPerPageOfTable = function() {
    return (state={}, payload) => {
        state.table.pagination.rowsPerPage = payload.rowsPerPage

        return state
    }
}

ReducerBase.defaultNestedListReduce = function() {
    return (state, payload) => {
        const target = Any.get(state.dialog.form, commonNames.path) ? Any.get(state.dialog.form, commonNames.path) : ''
        state.mode = commonNames.display
        if (state.dialog) {
            state.dialog.open = false
            state.dialog.form = {}
        }

        // 将修改或新增的数据的路径全部展开
        state.nestedList.data = decorate([payload], null, target, '', '')
        return state
    }
}

ReducerBase.defaultListFilterReduce = function(id) {
    return (state={}, payload) => {
        // 将所有节点的expanded字段置为false
        collapseAll(state.nestedList.data)
        // 通过模糊匹配，展开所有匹配成功的节点
        expandWithValue(state.nestedList.data, state.nestedList.data, id, payload.value)
        return state
    }
}

ReducerBase.defaultExpandNestedList = function() {
    return (state={}, payload) => {
        const targetItem = search(state.nestedList.data, payload.id)
        if (targetItem) {
            targetItem.expanded = true
        }

        return state
    }
}

ReducerBase.defaultCollapseNestedList = function() {
    return (state={}, payload) => {
        const targetItem = search(state.nestedList.data, payload.id)
        targetItem.expanded = false

        return state
    }
}

ReducerBase.defaultOpenDialog = function(mode) {
    return (state={}, payload) => {
        state.mode = mode
        state.dialog.open = true
        state.dialog.form = ReducerBase.formatFormInput(state.dialog.form, payload)
        return state
    }
}

ReducerBase.formatFormInput = function(form, data) {
    for (const property in data) {
        if (!form[property]) form[property] = {}

        form[property].value = data[property]
    }
    return form
}

ReducerBase.defaultCloseEditDialog = function() {
    return (state={}) => {
        state.mode = 'main'
        state.dialog.open = false
        state.dialog.form = {}
        return state
    }
}

ReducerBase.defaultChangeDialogInput = function() {
    return (state={}, payload) => {
        state.dialog.form[payload.id].value = payload.value
        return state
    }
}

export default ReducerBase
