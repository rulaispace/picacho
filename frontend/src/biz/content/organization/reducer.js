import ReducerBase from "../../../common/redux/reducer-base";
import commonNames from "../../../common/config/common-name-config";

const openAddDialog = function(type) {
    return function(state={}, payload) {
        state.mode = commonNames.add
        state.dialog.open = true
        state.dialog.form = ReducerBase.formatFormInput(state.dialog.form, {
            parentCode: payload.id,
            parentName: payload.parentName + payload.primaryText + '/',
            path: payload.path + '.N',
            id: '',
            code: '',
            primaryText: '',
            secondaryText: '',
            type: type,
        })
        return state
    }
}

const types = {
    loading: 'loadingOrganizationData',
    filter: 'filterOrganization',
    expand: 'expandOfOrganization',
    collapse: 'collapseOfOrganization',
    openEditDialog: 'openEditDialogOfOrganization',
    openAddGroupDialog: 'openAddGroupDialogOfOrganization',
    openAddPersonDialog: 'openAddPersonDialogOfOrganization',
    modifyFormInput: 'modifyFormInputOfOrganization',
    closeEditDialog: 'closeEditDialogOfOrganization',
}

const reducers = [
    {
        type: types.loading,
        action: ReducerBase.defaultAction(types.loading),
        reduce: ReducerBase.defaultNestedListReduce(),
    }, {
        type: types.filter,
        action: ReducerBase.defaultAction(types.filter),
        reduce: ReducerBase.defaultListFilterReduce(commonNames.primaryText),
    }, {
        type: types.expand,
        action: ReducerBase.defaultAction(types.expand),
        reduce: ReducerBase.defaultExpandNestedList(),
    }, {
        type: types.collapse,
        action: ReducerBase.defaultAction(types.collapse),
        reduce: ReducerBase.defaultCollapseNestedList(),
    }, {
        type: types.openEditDialog,
        action: ReducerBase.defaultAction(types.openEditDialog),
        reduce: ReducerBase.defaultOpenDialog(commonNames.modify),
    }, {
        type: types.openAddGroupDialog,
        action: ReducerBase.defaultAction(types.openAddGroupDialog),
        reduce: openAddDialog(commonNames.department),
    }, {
        type: types.openAddPersonDialog,
        action: ReducerBase.defaultAction(types.openAddPersonDialog),
        reduce: openAddDialog(commonNames.employee),
    }, {
        type: types.closeEditDialog,
        action: ReducerBase.defaultAction(types.closeEditDialog),
        reduce: ReducerBase.defaultCloseEditDialog(),
    }, {
        type: types.modifyFormInput,
        action: ReducerBase.defaultAction(types.modifyFormInput),
        reduce: ReducerBase.defaultChangeDialogInput(),
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