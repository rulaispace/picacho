import React from 'react'
import PropTypes from 'prop-types'
import AppBar from '@material-ui/core/AppBar'
import DefaultToolbar from "../../../component/toolbar/default-toolbar";
import DefaultTable from "../../../component/table/default-table";
import Paper from "@material-ui/core/Paper";
import reducer from './reducer'
import commonNames from "../../../common/config/common-name-config";

export default class Content extends React.Component {
    constructor(props) {
        super(props)

        this.store = props.store
        this.classes = props.classes

        this.filter = this.filter.bind(this)
        this.changeTablePage = this.changeTablePage.bind(this)
        this.changeRowsPerPage = this.changeRowsPerPage.bind(this)

        this.handlers = {
            toolbar: {
                searchInputChanged: this.filter,
            },
            table: {
                title: {
                    onClick: function(ordinal) {
                        return function() {
                            alert(`title is clicked: ${ordinal}`)
                        }
                    }
                },
                operator: {
                    onClick: function(ordinal) {
                        if (ordinal === commonNames.download) {
                            return function() {
                                alert('Reset password is clicked')
                            }
                        }

                        return f=>f
                    },
                    label: function(ordinal) {
                        if (ordinal === commonNames.download)
                            return '下载'
                        return ordinal
                    }
                },
                pagination: {
                    changePage: this.changeTablePage,
                    changeRowsPerPage: this.changeRowsPerPage,
                }
            }
        }
    }

    changeTablePage(event, page) {
        this.store.dispatch(reducer.createAction(reducer.types.changePage, {page}))
    }

    changeRowsPerPage(event) {
        this.store.dispatch(reducer.createAction(reducer.types.changeRowsPerPage, {rowsPerPage: parseInt(event.target.value, 10)}))
    }

    filter(value) {
        this.store.dispatch(reducer.createAction(reducer.types.filter, {value}))
    }

    render() {
        return (
            <main className={this.classes.contentDefaultRoot}>
                <div className={this.classes.contentDefaultAppbarSpacer} />
                <div className={this.classes.contentDefaultHead}>
                    <AppBar className={this.classes.contentDefaultAppbar} position='static' color='secondary' elevation={0} >
                        <DefaultToolbar classes={this.classes} state={this.store.getState().document.toolbar} handlers={this.handlers.toolbar}/>
                    </AppBar>
                </div>
                <Paper className={this.classes.contentDefaultBody}>
                    <DefaultTable classes={this.classes} state={this.store.getState().document.table} handlers={this.handlers.table}/>
                </Paper>
            </main>
        )
    }
}

Content.propTypes = {
    classes: PropTypes.object.isRequired,
    store: PropTypes.object.isRequired,
}