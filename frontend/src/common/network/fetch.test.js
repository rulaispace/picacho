import {post}, {isMock, setMockMode} from './fetch'

describe('Test Fetch with Mock Mode', () => {
    beforeAll(() => {
        setMockMode() // set mode into mock by force.
    })

    it('should run in force mode', () => expect(isMock).toBe(true))

    it('should get the correct data when using mock mode', () => {
        const mockApi = 'mock/post/data'

        const successCallback = (value) => {
            const {value1, value2} = value
            expect(value1).toBe('value1')
            expect(value2).toBe(2)
        }

        post(mockApi, {}, successCallback, f=>f)

    })
})
