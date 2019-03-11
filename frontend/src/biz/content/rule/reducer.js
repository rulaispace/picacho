import ReducerBase from "../../../common/redux/reducer-base";
import commonNames from "../../../common/config/common-name-config";

const types = {
    loading: 'loadingUserData',
    filter: 'filterUserData',
    changePage: 'changePageOfUserData',
    changeRowsPerPage: 'changeRowsPerPageOfUserData'
}

const reducers = [
    {
        type: types.loading,
        action: ReducerBase.defaultAction(types.loading),
        reduce: (state, payload) => {
            for (const index in payload) {
                const row = payload[index]
                if (row.state === commonNames.valid) {
                    row.operator = [commonNames.deactivate, commonNames.resetPassword]
                }

                if (row.state === commonNames.invalid) {
                    row.operator = [commonNames.activate]
                }
            }

            state.table.body = payload
            return state
        },
    }, {
        type: types.filter,
        action: ReducerBase.defaultAction(types.filter),
        reduce: ReducerBase.defaultTableFilterReduce('name'),
    }, {
        type: types.changePage,
        action: ReducerBase.defaultAction(types.changePage),
        reduce: ReducerBase.defaultChangePageOfTable(),
    }, {
        type: types.changeRowsPerPage,
        action: ReducerBase.defaultAction(types.changeRowsPerPage),
        reduce: ReducerBase.defaultChangeRowsPerPageOfTable(),
    }
]

const reducer = ReducerBase.extend({
    create: function() {
        return ReducerBase.create.call(this, types, reducers)
    },
    clear: function(state) {
        console.log(state)
    }
}).create()

export default reducer