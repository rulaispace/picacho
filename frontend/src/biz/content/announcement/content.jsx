import React from 'react'
import PropTypes from 'prop-types'
import AppBar from '@material-ui/core/AppBar'
import DefaultToolbar from "../../../component/toolbar/default-toolbar";
import DefaultTable from "../../../component/table/default-table";
import Paper from "@material-ui/core/Paper";
import reducer from './reducer'
import commonNames from "../../../common/config/common-name-config";
import iconNames from "../../../common/config/icon-name-config";
import DefaultFormDialog from "../../../component/dialog/form-dialog";
import buttonNames from "../../../common/config/button-name-config";
import {post} from "../../../common/network/network";
import DefaultMainMenu from "../../app/default-main-menu";
import menuNames from "../../../common/config/menu-name-config";


const constant = {
    announcementType: {
        notification: '通知',
        announcement: '公告',
    },
    announcementState: {
        inEdit: '编辑中',
        released: '已发布',
        invalid: '已撤销',
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
        this.addItem = this.addItem.bind(this)
        this.modifyItem = this.modifyItem.bind(this)
        this.closeDialog = this.closeDialog.bind(this)
        this.updateAnnouncement = this.updateAnnouncement.bind(this)
        this.formInputChanged = this.formInputChanged.bind(this)
        this.updateSuccessfully = this.updateSuccessfully.bind(this)
        this.updateFailed = this.updateFailed.bind(this)
        this.publishItem = this.publishItem.bind(this)
        this.deleteItem = this.deleteItem.bind(this)
        this.viewItem = this.viewItem.bind(this)
        this.saveButtonVisible = this.saveButtonVisible.bind(this)
        this.callbackItem = this.callbackItem.bind(this)

        this.handlers = {
            services: {
                add: '/announcement/add',
                modify: '/announcement/modify',
                delete: '/announcement/delete',
                publish: '/announcement/release',
                callback: '/announcement/callback',
            },
            toolbar: {
                searchInputChanged: this.filter,
                rightButtonGroup: {[iconNames.add]: {onClick: this.addItem}}
            },
            table: {
                operator: {
                    onClick: (ordinal) => {
                        switch (ordinal) {
                            case commonNames.modify: return this.modifyItem
                            case commonNames.publish: return this.publishItem
                            case commonNames.delete: return this.deleteItem
                            case commonNames.view: return this.viewItem
                            case commonNames.callback: return this.callbackItem
                            default: return f=>f
                        }
                    },
                    label: function(ordinal) {
                        if (ordinal === commonNames.modify) {
                            return '修改'
                        }
                        if (ordinal === commonNames.publish) {
                            return '发布'
                        }
                        if (ordinal === commonNames.delete) {
                            return '删除'
                        }
                        if (ordinal === commonNames.view) {
                            return '查阅'
                        }
                        if (ordinal === commonNames.callback) {
                            return '撤回'
                        }
                        return ordinal
                    }
                },
                type: {
                    label: (ordinal) => (constant.announcementType[ordinal])
                },
                state: {
                    label: (ordinal) => (constant.announcementState[ordinal])
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
                        [buttonNames.save]: {
                            visible: this.saveButtonVisible,
                            type: 'textButton',
                            text: '保存',
                            onClick: this.updateAnnouncement,
                        },
                        [buttonNames.close]: {
                            type: 'textButton',
                            text: '取消',
                            onClick: this.closeDialog,
                        }
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
                    state: {
                        type: commonNames.inputTypeSelect,
                        options: constant.announcementState,
                        label: '状态',
                        disabled: true,
                        className: 'formDefaultTextField2',
                        handleChange: this.formInputChanged,
                    },
                    type: {
                        type: commonNames.inputTypeSelect,
                        options: constant.announcementType,
                        label: '类型',
                        disabled: function(store) {
                            return () => !(store.getState().announcement.mode === commonNames.add || store.getState().announcement.mode === commonNames.modify)
                        }(this.store),
                        className: 'formDefaultTextField2',
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

    addItem() {
        this.store.dispatch(reducer.createAction(reducer.types.openAddDialog, {title:'', type:'notification', state: 'inEdit', content: '', attachments: []}))
    }

    modifyItem(data) {
        this.store.dispatch(reducer.createAction(reducer.types.openEditDialog, data))
    }

    publishItem(data) {
        const agreeCallback = function(self, id) {
            return function() {
                post(self.handlers.services[commonNames.publish],
                    {
                        id: id,
                    },
                    self.updateSuccessfully(),
                    self.updateFailed)
            }
        }

        this.store.alert({
            title: '系统提示',
            message: `确认要发布公告【${data.title}】吗？`,
            agreeCallback: agreeCallback(this, data.id)
        })
    }

    deleteItem(data) {
        const agreeCallback = function(self, id) {
            return function() {
                post(self.handlers.services[commonNames.delete],
                    {
                        id: id,
                    },
                    self.updateSuccessfully(),
                    self.updateFailed)
            }
        }

        this.store.alert({
            title: '系统提示',
            message: `确认要删除公告【${data.title}】吗？`,
            agreeCallback: agreeCallback(this, data.id)
        })
    }

    viewItem(data) {
        this.store.dispatch(reducer.createAction(reducer.types.openViewDialog, data))
    }

    callbackItem(data) {
        const agreeCallback = function(self, id) {
            return function() {
                post(self.handlers.services[commonNames.callback],
                    {
                        id: id,
                    },
                    self.updateSuccessfully(),
                    self.updateFailed)
            }
        }

        this.store.alert({
            title: '系统提示',
            message: `确认要撤回公告【${data.title}】吗？`,
            agreeCallback: agreeCallback(this, data.id)
        })
    }

    saveButtonVisible() {
        console.log(this.store.getState().announcement.mode)
        return this.store.getState().announcement.mode !== commonNames.view
    }

    closeDialog() {
        this.store.dispatch(reducer.createAction(reducer.types.closeDialog))
    }

    updateAnnouncement() {
        const record = DefaultFormDialog.unboxing(this.store.getState().announcement.dialog.form)
        post(this.handlers.services[this.store.getState().announcement.mode],
            {
                id: record.id,
                title: record.title,
                content: record.content,
                type: record.type,
                state: record.state,
                attachments: record.attachments
            },
            this.updateSuccessfully(),
            this.updateFailed)
    }

    updateSuccessfully(message) {
        const self = this
        return function(payload) {
            const agreeCallback = function() {
                return function() {
                    DefaultMainMenu.reloading(self.store, menuNames.announcement);
                }
            }

            self.store.tip({
                title: '系统提示',
                message: message ? message(payload) : "操作成功!",
                agreeCallback: agreeCallback(self)
            })
        }
    }

    updateFailed(error) {
        this.store.tip({
            title: error.title,
            message: error.detail,
        })
    }

    formInputChanged(id, value) {
        this.store.dispatch(reducer.createAction(reducer.types.modifyFormInput, {id, value}))
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

    render() {
        if (this.store.getState().announcement.mode === commonNames.add
            || this.store.getState().announcement.mode === commonNames.modify
            || this.store.getState().announcement.mode === commonNames.view)
            return (
                <DefaultFormDialog classes={this.classes} handlers={this.handlers.dialog} state={this.store.getState().announcement.dialog}/>
            )
        else
            return (
                <main className={this.classes.contentDefaultRoot}>
                    <div className={this.classes.contentDefaultAppbarSpacer} />
                    <div className={this.classes.contentDefaultHead}>
                        <AppBar className={this.classes.contentDefaultAppbar} position='static' color='secondary' elevation={0} >
                            <DefaultToolbar classes={this.classes} state={this.store.getState().announcement.toolbar} handlers={this.handlers.toolbar}/>
                        </AppBar>
                    </div>
                    <Paper className={this.classes.contentDefaultBody}>
                        <DefaultTable classes={this.classes} state={this.store.getState().announcement.table} handlers={this.handlers.table}/>
                    </Paper>
                </main>
            )
    }
}

Content.propTypes = {
    classes: PropTypes.object.isRequired,
    store: PropTypes.object.isRequired,
}