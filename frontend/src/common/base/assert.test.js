import assert from "./assert";
import uuid from 'uuid'

describe('Test assertion', () => {
    it('Test assertion with false condition', () => {
        function demo() {
            assert(false, 'Something wrong.')
        }

        expect(demo).toThrowError()
    })
    it('Test assertion with no args', () => {
        console.log(uuid.v1())
        function demo() {
            assert()
        }

        expect(demo).toThrowError('Assertion failed')
    })
})