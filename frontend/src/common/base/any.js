const Any = {}

Any.extend = function(extension) {
    const hasOwnProperty = Object.hasOwnProperty
    const object = Object.create(this)

    for (const property in extension) {
        if (hasOwnProperty.call(extension, property) ||
            typeof object[property] === 'undefined')
            object[property] = extension[property]
    }

    return object
}

Any.get = function(obj, key) {
    if (obj == null) return obj
    if (obj[key] == null) return null

    if (typeof obj[key] === 'object') return obj[key].value
    return obj[key]
}

Any.asArray = function(any) {
    if (any == null) return []
    if (Array.isArray(any)) return [...any]
    return [any]
}

export default Any