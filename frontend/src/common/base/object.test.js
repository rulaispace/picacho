import {deepOverride, override, shouldDeepTrace} from "./object";

describe('Test object override function', () => {
    let source = {}

    beforeAll(() => {
        source = {
            prop1: 'value1'
        }
    })
    it('Just the old value', () => {
        expect(override(source, null).prop1).toBe('value1')
    })
    it('Just add new value', () => {
        expect(override(source, {
            prop2: 'value2'
        }).prop2).toBe('value2')
    })
    it('Override old value', () => {
        expect(override(source, {
            prop1: 'newValue1'
        }).prop1).toBe('newValue1')
    })
    it('String should not deep trace', () => {
        expect(shouldDeepTrace('some string')).toBeFalsy()
    })
    it('Function should not deep trace', () => {
        expect(shouldDeepTrace(() => {return null})).toBeFalsy()
    })
    it('Number should not deep trace', () => {
        expect(shouldDeepTrace(12)).toBeFalsy()
    })
    it('Boolean should not deep trace', () => {
        expect(shouldDeepTrace(true)).toBeFalsy()
    })
    it('Symbol should not deep trace', () => {
        expect(shouldDeepTrace(Symbol())).toBeFalsy()
    })
    it('Symbol iterator should not deep trace', () => {
        expect(shouldDeepTrace(Symbol.iterator)).toBeFalsy()
    })
    it('Undefined should not deep trace', () => {
        expect(shouldDeepTrace(undefined)).toBeFalsy()
    })
    it('Object should deep trace', () => {
        expect(shouldDeepTrace({})).toBeTruthy()
    })
    it('Regular expression should not deep trace', () => {
        expect(shouldDeepTrace(/abc/)).toBeFalsy()
    })
    it('Deep trace override with no need deep trace property', () => {
        const origin = {
            property1: 'value1',
        }

        const delta = {
            property1: 'value2',
        }

        expect(deepOverride(origin, delta).property1).toBe('value2')
    })
    it('Deep trace override with one element should be override deeply', () => {
        const origin = {
            child1: {
                property1: 'value1',
            }
        }

        const delta = {
            child1: {
                property1: 'value2',
            }
        }

        expect(deepOverride(origin, delta).child1.property1).toBe('value2')
    })
    it('Deep trace override with one element should be override deeply and remain other no need override property', () => {
        const origin = {
            child1: {
                property1: 'value1',
                property2: 'value2',
            }
        }
        const delta = {
            child1: {
                property1: 'newValue1',
            }
        }

        expect(deepOverride(origin, delta).child1.property2).toBe('value2')
    })
    it('Deep trace override with one element should be override deeply and copy the new unkown value of delta', () => {
        const origin = {
            child1: {
                property1: 'value1',
            }
        }
        const delta = {
            child1: {
                property1: 'newValue1',
                property2: 'newValue2'
            }
        }

        expect(deepOverride(origin, delta).child1.property2).toBe('newValue2')
    })
    it('Deep override the function property', () => {
        const origin = {
            child1: {
                property1: f=>f,
            }
        }

        const targetFunc = () => {
            return 'hello'
        }

        const delta = {
            child1: {
                property1: targetFunc,
            }
        }

        expect(deepOverride(origin, delta).child1.property1()).toBe('hello')
    })
    it('Deep override array with key when no element is the same', () => {
        const origin = {
            children: [{
                key: 'item1'
            }]
        }
        const delta = {
            children: [{
                key: 'item2'
            }]
        }
        expect(deepOverride(origin, delta).children).toHaveLength(2)
    })
    it('Deep override array with key when element is the same', () => {
        const origin = {
            children: [{
                key: 'item1',
                onClick: f=>f,
            }]
        }
        const delta = {
            children: [{
                key: 'item1',
                onClick: () => 'hello',
            }]
        }

        expect(deepOverride(origin, delta).children).toHaveLength(1)
        expect(deepOverride(origin, delta).children[0].onClick()).toBe('hello')
    })
    it('Deep override array with key when element with the same key and has more property', () => {
        const origin = {
            children: [{
                key: 'item1',
            }]
        }
        const delta = {
            children: [{
                key: 'item1',
                onClick: () => 'hello',
            }]
        }

        expect(deepOverride(origin, delta).children).toHaveLength(1)
        expect(deepOverride(origin, delta).children[0].onClick()).toBe('hello')
    })
    it('Deep override array with key when element with the same key and remain the origin property', () => {
        const origin = {
            children: [{
                key: 'item1',
                onClick: () => 'hello',
            }]
        }
        const delta = {
            children: [{
                key: 'item1',
            }]
        }

        expect(deepOverride(origin, delta).children).toHaveLength(1)
        expect(deepOverride(origin, delta).children[0].onClick()).toBe('hello')
    })
    it('Override with the delta as null', () => {
        expect(Object.keys(deepOverride({}, null)).length).toBe(0)
        expect(Object.keys(deepOverride({})).length).toBe(0)
        expect(Object.keys(deepOverride({}, undefined)).length).toBe(0)
    })
    it('Throw when delta need to be array', () => {
        const origin = [
            {name: 'zhangsan'},
            {name: 'lisi'}
        ]
        const delta = {
            children: [{
                key: 'item1',
            }]
        }

        function overrideArrayWithObject() {
            deepOverride(origin, delta)
        }

        expect(overrideArrayWithObject).toThrowError()
    })
    it('When source array element do not have the property named `key`', () => {
        const origin = [
            {name: 'zhangsan'},
            {name: 'lisi'}
        ]
        const delta = [
            {
                key: 'key1',
                name: 'wangbagaozi',
            }
        ]
        expect(deepOverride(origin, delta).length).toBe(3)
    })
    it('When delta array element do not have the property named `key`', () => {
        const origin = [
            {
                key: 'key1',
                name: 'zhangsan',
            },
            {
                key: 'key2',
                name: 'lisi'
            }
        ]
        const delta = [
            {
                name: 'wangbagaozi',
            }
        ]
        expect(deepOverride(origin, delta).length).toBe(3)
    })
})