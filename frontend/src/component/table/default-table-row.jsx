import React from 'react'
import PropTypes from 'prop-types'
import TableRow from '@material-ui/core/TableRow'
import DefaultTableCell from "./default-table-cell";

export default function DefaultTableRow({state, classes, handlers}) {
    return (
        <TableRow>
            {state.header.map((col, i) => (
                <DefaultTableCell
                    key={i}
                    state={{
                        ...state,
                        header: null,
                        col,
                    }}
                    classes={classes}
                    handlers={handlers}
                />
            ))}
        </TableRow>
    )
}

DefaultTableRow.propTypes = {
    state: PropTypes.object.isRequired,
    classes: PropTypes.object.isRequired,
    handlers: PropTypes.object.isRequired,
}
