import React from 'react'
import PropTypes from 'prop-types'
import TablePagination from "@material-ui/core/TablePagination";

export default function DefaultTablePagination({state, handlers}) {
    const {
        pagination: {
            page,
            rowsPerPage,
        },
        body,
    } = state

    return (
        <TablePagination
            rowsPerPageOptions={[5, 10, 25]}
            component="div"
            count={body.length}
            rowsPerPage={rowsPerPage}
            page={page}
            labelRowsPerPage={'每页记录数:'}
            SelectProps={{
                native: true,
            }}
            onChangePage= {handlers.pagination.changePage}
            onChangeRowsPerPage={handlers.pagination.changeRowsPerPage}
        />
    )
}

DefaultTablePagination.propTypes = {
    state: PropTypes.object.isRequired,
    handlers: PropTypes.object.isRequired,
}