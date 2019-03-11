import React from 'react'
import PropTypes from 'prop-types'
import TableBody from '@material-ui/core/TableBody'
import DefaultTableRow from "./default-table-row";
import DefaultTable from './default-table'

export default function DefaultTableBody({state, classes, handlers}) {
    return (
        <TableBody>
            {
                DefaultTableBody
                    .paging(DefaultTable.filter(state), state.feature.pageable, state.pagination.page, state.pagination.rowsPerPage)
                    .map((row, index) =>(
                            <DefaultTableRow
                                key={index}
                                state={{
                                    ...state,
                                    body: null,
                                    row,
                                }}
                                classes={classes}
                                handlers={handlers}
                            />
                        )
                    )
            }
        </TableBody>
    )
}

DefaultTableBody.paging = function(rows, pageable, page, rowsPerPage) {
    if (!pageable) return rows
    return rows.slice(page * rowsPerPage, (page + 1) * rowsPerPage)
}

DefaultTableBody.propTypes = {
    state: PropTypes.object.isRequired,
    classes: PropTypes.object.isRequired,
    handlers: PropTypes.object.isRequired,
}
