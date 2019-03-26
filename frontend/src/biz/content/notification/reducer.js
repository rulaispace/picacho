import ReducerBase from "../../../common/redux/reducer-base";
import commonNames from "../../../common/config/common-name-config";

const types = {
    loading: 'loadingNotificationData',
    filter: 'filterNotificationData',
    changePage: 'changePageOfNotificationData',
    changeRowsPerPage: 'changeRowsPerPageOfNotificationData',
    openViewDialog: 'openViewDialogOfNotification',
    closeDialog: 'closeDialogOfNotification'
}

const reducers = [
    {
        type: types.loading,
        action: ReducerBase.defaultAction(types.loading),
        reduce: ReducerBase.defaultTableReduce(),
    }, {
        type: types.filter,
        action: ReducerBase.defaultAction(types.filter),
        reduce: ReducerBase.defaultTableFilterReduce('title'),
    }, {
        type: types.changePage,
        action: ReducerBase.defaultAction(types.changePage),
        reduce: ReducerBase.defaultChangePageOfTable(),
    }, {
        type: types.changeRowsPerPage,
        action: ReducerBase.defaultAction(types.changeRowsPerPage),
        reduce: ReducerBase.defaultChangeRowsPerPageOfTable(),
    }, {
        type: types.openViewDialog,
        action: ReducerBase.defaultAction(types.openViewDialog),
        reduce: ReducerBase.defaultOpenDialog(commonNames.view),
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