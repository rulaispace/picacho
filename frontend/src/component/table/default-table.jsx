import React from 'react'
import PropTypes from 'prop-types'
import Table from '@material-ui/core/Table'
import DefaultTableHead from "./default-table-head";
import DefaultTableBody from "./default-table-body";
import DefaultTablePagination from "./default-table-pagination";
import {modifyWithDef} from "../../common/base/store-state-modifier";

const defaultState = {
    // default value for all switch
    feature: {
        rootClassName: 'tableDefaultRoot',
        contentClassName: 'tableDefaultContent',
        tableClassName: 'tableDefault',
        pageable: false,
        sortable: false,
        withFilter: false,
    },
    // default value for paginate
    pagination: {
        page: 0,
        rowsPerPage: 5,
    }
}

const defaultHandlers = {
    cellStyles: () => 'tableCellDefault',
}

const defaultHeaderColState = {
    numeric: false,
    disablePadding: false,
    linkable: false,
}


function modifyState(state) {
    let result = modifyWithDef(state, defaultState)
    result.header = result.header.map(col => modifyWithDef(col, defaultHeaderColState))
    return result;
}

export default function DefaultTable({classes, state, handlers}) {
    const myState = modifyState(state)
    const myHandlers = modifyWithDef(handlers, defaultHandlers)

    return (
        <div className={classes[myState.feature.rootClassName]}>
            <div className={classes[myState.feature.contentClassName]}>
                <Table className={classes[myState.feature.tableClassName]}>
                    <DefaultTableHead state={myState} classes={classes} handlers={myHandlers}/>
                    <DefaultTableBody state={myState} classes={classes} handlers={myHandlers}/>
                </Table>
            </div>
            {
                state.feature.pageable ? (
                    <DefaultTablePagination state={myState} classes={classes} handlers={myHandlers}/>
                ) : null
            }
        </div>
    )
}

DefaultTable.filter = function(state) {
    return state.feature.withFilter ?
        (
            state.body.filter(
                function(row) {
                    for (const property in row) {
                        if (state.filter[property]) {
                            return row[property].indexOf(state.filter[property]) !== -1
                        }
                    }
                    return true
                }
            )
        )
        : state.body
}

DefaultTable.propTypes = {
    state: PropTypes.object.isRequired,
    classes: PropTypes.object.isRequired,
    handlers: PropTypes.object.isRequired,
}