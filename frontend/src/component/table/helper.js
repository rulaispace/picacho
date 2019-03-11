

export const setFilter = (headers, id, filter) =>
    headers.map(headerColumn => (
        headerColumn.id === id ? ({
                ...headerColumn,
                filter: filter
            }) : ({
            ...headerColumn
            })
        )
    )