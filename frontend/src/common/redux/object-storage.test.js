import ObjectStorage, {DefaultLocalReduxStateStorageKey} from "./object-storage";

describe('Test object storage', () => {
    it('create the default object storage', () => {
        const objectStorage = ObjectStorage.create()
        expect(objectStorage.key).toBe(DefaultLocalReduxStateStorageKey)
    })
    it('Clear default object storage data', () => {
        const objectStorage = ObjectStorage.create()
        objectStorage.clear()
        expect(objectStorage.read()).toBeNull()
    })
    it('Read object storage data', () => {
        const key = 'JestKey'
        const objectStorage = ObjectStorage.create(key)
        expect(objectStorage.key).toBe(key)

        objectStorage.update({property: 'value'})
        expect(objectStorage.read()).toHaveProperty('property', 'value')

        // update with null
        objectStorage.update(null)
        expect(objectStorage.isEmpty).toBeTruthy()
    })
})