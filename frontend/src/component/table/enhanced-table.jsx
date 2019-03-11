import React from 'react'
import PropTypes from 'prop-types'
import Table from '@material-ui/core/Table'
import TableBody from '@material-ui/core/TableBody'
import TableCell from '@material-ui/core/TableCell'
import TablePagination from '@material-ui/core/TablePagination'
import TableRow from '@material-ui/core/TableRow'
import Checkbox from '@material-ui/core/Checkbox'
import EnhancedTableToolbar from "./enhanced-table-toolbar"
import EnhancedTableHead from "./enhanced-table-header"
import Button from "@material-ui/core/es/Button/Button"
import Link from '@material-ui/core/Link'

function desc(a, b, orderBy) {
    if (b[orderBy] < a[orderBy]) {
        return -1
    }
    if (b[orderBy] > a[orderBy]) {
        return 1
    }
    return 0
}

function stableSort(array, cmp) {
    const stabilizedThis = array.map((el, index) => [el, index])
    stabilizedThis.sort((a, b) => {
        const order = cmp(a[0], b[0])
        if (order !== 0) return order
        return a[1] - b[1]
    })
    return stabilizedThis.map(el => el[0])
}

function getSorting(order, orderBy) {
    return order === 'desc' ? (a, b) => desc(a, b, orderBy) : (a, b) => -desc(a, b, orderBy)
}

class EnhancedTable extends React.Component {
    constructor(props) {
        super(props)

        const {tableData} = props

        this.state = {
            ...tableData,
        }

        this.handleRequestSort = this.handleRequestSort.bind(this)
        this.handleSelectAllClick = this.handleSelectAllClick.bind(this)
        this.handleClick = this.handleClick.bind(this)
        this.handleChangePage = this.handleChangePage.bind(this)
        this.handleChangeRowsPerPage = this.handleChangeRowsPerPage.bind(this)
        this.isSelected = this.isSelected.bind(this)
    }

    handleRequestSort(event, property) {
        const orderBy = property
        let order = 'desc'

        if (this.state.orderBy === property && this.state.order === 'desc') {
            order = 'asc'
        }

        this.setState({ order, orderBy })
    }

    handleSelectAllClick(event) {
        if (event.target.checked) {
            this.setState(state => ({ selected: state.data.map(n => n.id) }))
            return
        }
        this.setState({ selected: [] })
    }

    handleClick(event, id) {
        const { selected } = this.state
        const selectedIndex = selected.indexOf(id)
        let newSelected = []

        if (selectedIndex === -1) {
            newSelected = newSelected.concat(selected, id)
        } else if (selectedIndex === 0) {
            newSelected = newSelected.concat(selected.slice(1))
        } else if (selectedIndex === selected.length - 1) {
            newSelected = newSelected.concat(selected.slice(0, -1))
        } else if (selectedIndex > 0) {
            newSelected = newSelected.concat(
                selected.slice(0, selectedIndex),
                selected.slice(selectedIndex + 1),
            )
        }

        this.setState({ selected: newSelected })
    }

    handleChangePage(event, page) {
        this.setState({ page })
    }

    handleChangeRowsPerPage(event) {
        this.setState({ rowsPerPage: event.target.value })
    }

    isSelected(id) {
        return this.state.selected.indexOf(id) !== -1
    }

    render() {
        const { classes } = this.props
        const { header, data, order, orderBy, selected, rowsPerPage, page, needToolBar, selectable } = this.state
        const emptyRows = rowsPerPage - Math.min(rowsPerPage, data.length - page * rowsPerPage)

        return (
            <div className={classes.tableRoot}>
                {needToolBar ? <EnhancedTableToolbar numSelected={selected.length} classes={classes} /> : null}
                <div className={classes.tableContent}>
                    <Table className={classes.table} aria-labelledby="tableTitle">
                        <EnhancedTableHead
                            header={header}
                            numSelected={selected.length}
                            order={order}
                            orderBy={orderBy}
                            onSelectAllClick={this.handleSelectAllClick}
                            onRequestSort={this.handleRequestSort}
                            rowCount={data.length}
                        />
                        <TableBody>
                            {stableSort(data, getSorting(order, orderBy))
                                .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                                .map(n => {
                                    const isSelected = this.isSelected(n.id)
                                    return (
                                        <TableRow
                                            hover
                                            onClick={event => this.handleClick(event, n.id)}
                                            role="checkbox"
                                            aria-checked={isSelected}
                                            tabIndex={-1}
                                            key={n.id}
                                            selected={isSelected}
                                        >
                                            {selectable ? (
                                                <TableCell padding="checkbox">
                                                    <Checkbox checked={isSelected} />
                                                </TableCell>
                                            ) : null}

                                            <TableCell component="th" scope="row" padding="none">
                                                <Link onClick={() => alert('I am clicked.')}>
                                                    {n.name}
                                                </Link>
                                            </TableCell>
                                            <TableCell align="right">
                                                <Button>
                                                    {n.calories}
                                                </Button>
                                            </TableCell>
                                            <TableCell align="right">{n.carbs}</TableCell>
                                            <TableCell align="right">{n.protein}</TableCell>
                                        </TableRow>
                                    )
                                })}
                            {emptyRows > 0 && (
                                <TableRow style={{ height: 49 * emptyRows }}>
                                    <TableCell colSpan={6} />
                                </TableRow>
                            )}
                        </TableBody>
                    </Table>
                </div>
                <TablePagination
                    rowsPerPageOptions={[5, 10, 25]}
                    component="div"
                    count={data.length}
                    rowsPerPage={rowsPerPage}
                    page={page}
                    labelRowsPerPage={'每页行数: '}
                    backIconButtonProps={{
                        'aria-label': 'Previous Page',
                    }}
                    nextIconButtonProps={{
                        'aria-label': 'Next Page',
                    }}
                    onChangePage={this.handleChangePage}
                    onChangeRowsPerPage={this.handleChangeRowsPerPage}
                />
            </div>
        )
    }
}

EnhancedTable.propTypes = {
    classes: PropTypes.object.isRequired,
    tableData: PropTypes.object.isRequired,
}

export default EnhancedTable