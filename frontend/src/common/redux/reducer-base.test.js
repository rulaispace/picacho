import ReducerBase from "./reducer-base";

describe('Test reducer base', () => {
    it('Test getAction when action is not set', () => {
        const action = ReducerBase.create().createAction('HelloWorldAction', {name: 'Impossible'})
        expect(action).toHaveProperty('type', 'default')
    })
    it('Test reduce when action is not set', () => {
        const state = ReducerBase.create().reduce({priority: 'great'}, {type: 'default', payload: null})
        expect(state).toHaveProperty('priority', 'great')
    })
})