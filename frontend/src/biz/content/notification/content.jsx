import React from 'react'
import PropTypes from 'prop-types'
import AppBar from '@material-ui/core/AppBar'
import DefaultTable from '../../../component/table/default-table'
import DefaultToolbar from '../../../component/toolbar/default-toolbar'
import Paper from "@material-ui/core/Paper";
import reducer from './reducer'
import commonNames from "../../../common/config/common-name-config";
import buttonNames from "../../../common/config/button-name-config";
import DefaultFormDialog from "../../../component/dialog/form-dialog";
import {post} from "../../../common/network/network";
import DefaultMainMenu from "../../app/default-main-menu";
import menuNames from "../../../common/config/menu-name-config";

const constant = {
    announcementType: {
        notification: '通知',
        announcement: '公告',
    }
}

export default class Content extends React.Component {
    constructor(props) {
        super(props)
        this.classes = props.classes
        this.store = props.store

        this.filter = this.filter.bind(this)
        this.changeTablePage = this.changeTablePage.bind(this)
        this.changeRowsPerPage = this.changeRowsPerPage.bind(this)
        this.viewItem = this.viewItem.bind(this)
        this.querySuccessfully = this.querySuccessfully.bind(this)
        this.queryFailed = this.queryFailed.bind(this)
        this.closeDialog = this.closeDialog.bind(this)

        this.handlers = {
            services: {
                queryDetail: '/notification/query/detail',
            },
            toolbar: {
                searchInputChanged: this.filter,
            },
            table: {
                title: {
                    onClick: () => this.viewItem
                },
                type: {
                    label: (ordinal) => (constant.announcementType[ordinal])
                },
                pagination: {
                    changePage: this.changeTablePage,
                    changeRowsPerPage: this.changeRowsPerPage,
                },
            },
            dialog: {
                toolbar: {
                    leftButtonClicked: this.closeDialog,
                    rightButtonGroup: {
                    },
                },
                form: {
                    title: {
                        label: '标题',
                        disabled: function(store) {
                            return () => !(store.getState().announcement.mode === commonNames.add || store.getState().announcement.mode === commonNames.modify)
                        }(this.store),
                        className: 'formDefaultTextField',
                        handleChange: this.formInputChanged,
                    },
                    content: {
                        type: commonNames.richTextEditor,
                        label: '正文',
                        disabled: function(store) {
                            return () => !(store.getState().announcement.mode === commonNames.add || store.getState().announcement.mode === commonNames.modify)
                        }(this.store),
                        handleChange: this.formInputChanged,
                    },
                    attachments: {
                        type: commonNames.fileUploader,
                        label: '附件',
                        className: 'formDefaultTextField',
                        disabled: function(store) {
                            return () => !(store.getState().announcement.mode === commonNames.add || store.getState().announcement.mode === commonNames.modify)
                        }(this.store),
                        handleChange: this.formInputChanged,
                        uploadUri: '/announcement/attachment/upload',
                        deleteUri: '/announcement/attachment/delete',
                    },
                }
            },
        }
    }

    filter(value) {
        this.store.dispatch(reducer.createAction(reducer.types.filter, {value}))
    }

    changeTablePage(event, page) {
        this.store.dispatch(reducer.createAction(reducer.types.changePage, {page}))
    }

    changeRowsPerPage(event) {
        this.store.dispatch(reducer.createAction(reducer.types.changeRowsPerPage, {rowsPerPage: parseInt(event.target.value, 10)}))
    }

    viewItem(data) {
        post(this.handlers.services[commonNames.queryDetail],
            {
                id: data.id,
            },
            this.querySuccessfully,
            this.queryFailed
        )
    }

    closeDialog() {
        this.store.dispatch(reducer.createAction(reducer.types.closeDialog))
    }

    querySuccessfully(payload) {
        this.store.dispatch(reducer.createAction(reducer.types.openViewDialog, payload))
    }

    queryFailed(error) {
        this.store.tip({
            title: error.title,
            message: error.detail,
        })
    }

    render() {
        if (this.store.getState().notification.mode === commonNames.view) {
            return <DefaultFormDialog classes={this.classes} handlers={this.handlers.dialog} state={this.store.getState().notification.dialog}/>
        }
        else {
            return (
                <main className={this.classes.contentDefaultRoot}>
                    <div className={this.classes.contentDefaultAppbarSpacer} />
                    <div className={this.classes.contentDefaultHead}>
                        <AppBar className={this.classes.contentDefaultAppbar} position='static' color='secondary' elevation={0} >
                            <DefaultToolbar classes={this.classes} state={this.store.getState().notification.toolbar} handlers={this.handlers.toolbar}/>
                        </AppBar>
                    </div>
                    <Paper className={this.classes.contentDefaultBody}>
                        <DefaultTable classes={this.classes} state={this.store.getState().notification.table} handlers={this.handlers.table}/>
                    </Paper>
                </main>
            )
        }
    }
}

Content.propTypes = {
    classes: PropTypes.object.isRequired,
    store: PropTypes.object.isRequired,
}