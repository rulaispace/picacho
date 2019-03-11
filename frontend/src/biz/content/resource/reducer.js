import ReducerBase from "../../../common/redux/reducer-base";
import commonNames from "../../../common/config/common-name-config";

const types = {
    loading: 'loadingResourceData',
    filter: 'filterResourceData',
    changePage: 'changePageOfResourceData',
    modifyFormInput: 'modifyFormInputOfResource',
    changeRowsPerPage: 'changeRowsPerPageOfResourceData',
    openEditDialog: 'openEditDialogOfResource',
    openAddDialog: 'openAddDialogOfResource',
    closeDialog: 'closeDialogOfResource',
}

const reducers = [
    {
        type: types.loading,
        action: ReducerBase.defaultAction(types.loading),
        reduce: (state, payload) => {
            for (const index in payload) {
                const resource = payload[index]
                if (resource.state === commonNames.valid) {
                    resource.operator = [commonNames.deactivate, commonNames.modify, commonNames.delete]
                } else {
                    resource.operator = [commonNames.activate]
                }
            }

            state.table.body = payload

            state.mode = commonNames.display
            if (state.dialog) {
                state.dialog.open = false
                state.dialog.form = {}
            }

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
    }, {
        type: types.modifyFormInput,
        action: ReducerBase.defaultAction(types.modifyFormInput),
        reduce: ReducerBase.defaultChangeDialogInput(),
    }, {
        type: types.openEditDialog,
        action: ReducerBase.defaultAction(types.openEditDialog),
        reduce: ReducerBase.defaultOpenDialog(commonNames.modify),
    }, {
        type: types.openAddDialog,
        action: ReducerBase.defaultAction(types.openAddDialog),
        reduce: ReducerBase.defaultOpenDialog(commonNames.add),
    }, {
        type: types.closeDialog,
        action: ReducerBase.defaultAction(types.closeDialog),
        reduce: ReducerBase.defaultCloseEditDialog(),
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