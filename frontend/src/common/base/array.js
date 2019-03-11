export function asArray(any) {
    if (any == null) return []
    if (Array.isArray(any)) return [...any]
    return [any]
}

export function itemWithFirstAsFallback(array, index) {
    if (index < 0) return array[0]
    if (index >= array.length) return array[0]

    return array[index]
}