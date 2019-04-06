import ReducerBase from "../../../common/redux/reducer-base";
import commonNames from "../../../common/config/common-name-config";

const types = {
    loading: 'loadingScheduleData',
    changePage: 'changePageOfScheduleData',
    changeRowsPerPage: 'changeRowsPerPageOfScheduleData',
    openAddDialog: 'openAddDialogOfSchedule',
    closeDialog: 'closeDialogOfSchedule',
    modifyFormInput: 'modifyFormInputOfSchedule',
    openViewDialog: 'openViewDialogOfSchedule',
}

const reducers = [
    {
        type: types.loading,
        action: ReducerBase.defaultAction(types.loading),
        reduce: (state, payload) => {
            for (const index in payload) {
                const schedule = payload[index]
                schedule.operator = [commonNames.view]
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
        type: types.changePage,
        action: ReducerBase.defaultAction(types.changePage),
        reduce: ReducerBase.defaultChangePageOfTable(),
    }, {
        type: types.changeRowsPerPage,
        action: ReducerBase.defaultAction(types.changeRowsPerPage),
        reduce: ReducerBase.defaultChangeRowsPerPageOfTable(),
    }, {
        type: types.openAddDialog,
        action: ReducerBase.defaultAction(types.openAddDialog),
        reduce: ReducerBase.defaultOpenDialog(commonNames.add),
    }, {
        type: types.closeDialog,
        action: ReducerBase.defaultAction(types.closeDialog),
        reduce: ReducerBase.defaultCloseEditDialog(),
    }, {
        type: types.modifyFormInput,
        action: ReducerBase.defaultAction(types.modifyFormInput),
        reduce: ReducerBase.defaultChangeDialogInput(),
    }, {
        type: types.openViewDialog,
        action: ReducerBase.defaultAction(types.openViewDialog),
        reduce: ReducerBase.defaultOpenDialog(commonNames.view),
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