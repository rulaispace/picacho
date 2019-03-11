import ReducerBase from '../../common/redux/reducer-base'

const types = {
    navigate: 'navigate',
    open: 'menuOpen',
    agree: 'menuClose',
    clear: 'clearNavigator',
    reloading: 'dashboardReloading',
}

const reducers = [
    {
        type: types.navigate,
        action: ReducerBase.defaultAction(types.navigate),
        reduce: (state={}, payload) => (
            {
                ...state,
                navigator:payload.name,
                loading: true,
            }
        )
    }, {
        type: types.open,
        action: ReducerBase.defaultAction(types.open),
        reduce: (state={}) => ({...state, open:true})
    }, {
        type: types.close,
        action: ReducerBase.defaultAction(types.close),
        reduce: (state={}) => ({...state, open:false})
    }, {
        type: types.reloading,
        action: ReducerBase.defaultAction(types.reloading),
        reduce: (state={}, payload) => ({...state, navigator : payload.navigator})
    }, {
        type: types.loaded,
        action: ReducerBase.defaultAction(types.loaded),
        reduce: (state={}) => ({...state, loading:false})
    }/*, {
        type: types.clear,
        action: ReducerBase.defaultAction(types.clear),
        reduce: (state={}) => ({...state, navigator:null})
    }*/
]

const reducer = ReducerBase.extend({
    create: function() {
        return ReducerBase.create.call(this, types, reducers)
    },
    clear: function(state) {
        state.navigator = null
    },
    reloading: function() {
        this.store.dispatch(this.createAction(this.types.reloading, {navigator:null}))
    }
}).create()

export default reducer