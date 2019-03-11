import React from 'react'
import PropTypes from 'prop-types'
import AppBar from '@material-ui/core/AppBar'
import DefaultToolbar from "../../../component/toolbar/default-toolbar";
import DefaultTable from "../../../component/table/default-table";
import reducer from './reducer'
import commonNames from "../../../common/config/common-name-config";
import Paper from "@material-ui/core/Paper";
import iconNames from "../../../common/config/icon-name-config";
import buttonNames from "../../../common/config/button-name-config";
import DefaultFormDialog from "../../../component/dialog/form-dialog";
import Any from "../../../common/base/any";
import {post} from "../../../common/network/network";
import DefaultMainMenu from "../../app/default-main-menu";
import menuNames from "../../../common/config/menu-name-config";

const state = {
    [commonNames.valid]: {label: '有效'},
    [commonNames.invalid]: {label: '注销'},
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
        this.deleteItem = this.deleteItem.bind(this)
        this.activateItem = this.activateItem.bind(this)
        this.deactivateItem = this.deactivateItem.bind(this)

        this.formInputChanged = this.formInputChanged.bind(this)
        this.closeDialog = this.closeDialog.bind(this)

        this.updateResource = this.updateResource.bind(this)
        this.updateSuccessfully = this.updateSuccessfully.bind(this)
        this.updateFailed = this.updateFailed.bind(this)

        this.handlers = {
            services: {
                add: '/resource/add',
                modify: '/resource/modify',
                delete: '/resource/delete',
                activate: '/resource/activate',
                deactivate: '/resource/deactivate'
            },
            toolbar: {
                searchInputChanged: this.filter,
                rightButtonGroup: {
                    [iconNames.add]: {
                        onClick: this.addItem,
                    }
                }
            },
            table: {
                operator: {
                    onClick: (ordinal) => {
                        switch (ordinal) {
                            case commonNames.modify: return this.modifyItem
                            case commonNames.delete: return this.deleteItem
                            case commonNames.activate: return this.activateItem
                            case commonNames.deactivate: return this.deactivateItem
                            default: return f=>f
                        }
                    },
                    label: (ordinal) => {
                        switch (ordinal) {
                            case commonNames.modify: return '修改'
                            case commonNames.delete: return '删除'
                            case commonNames.activate: return '激活'
                            case commonNames.deactivate: return '注销'
                            default: return f=>f
                        }
                    }
                },
                state: {
                    label: (ordinal) => state[ordinal].label,
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
                            type: 'textButton',
                            text: '保存',
                            onClick: this.updateResource,
                        },
                        [buttonNames.close]: {
                            type: 'textButton',
                            text: '取消',
                            onClick: this.closeDialog,
                        }
                    },
                },
                form: {
                    code: {
                        label: '资源编码',
                        disabled: function(store) {
                            return () => !(store.getState().resource.mode === commonNames.add)
                        }(this.store),
                        className: 'formDefaultTextField2',
                        handleChange: this.formInputChanged,
                    },
                    state: {
                        label: '状态',
                        disabled: true,
                        value: ({form}) => {
                            return state[Any.get(form, 'state')].label
                        },
                        visible: function(store) {
                            return () => !(store.getState().resource.mode === commonNames.add)
                        }(this.store),
                        className: 'formDefaultTextField2',
                        handleChange: this.formInputChanged,
                    },
                    name: {
                        label: '资源名称',
                        className: 'formDefaultTextField2',
                        handleChange: this.formInputChanged,
                    },
                    createDateTime: {
                        label: '创建时间',
                        className: 'formDefaultTextField2',
                        visible: function(store) {
                            return () => !(store.getState().resource.mode === commonNames.add)
                        }(this.store),
                        disabled: true,
                        handleChange: this.formInputChanged,
                    }
                }
            },
        }
    }

    formInputChanged(id, value) {
        this.store.dispatch(reducer.createAction(reducer.types.modifyFormInput, {id, value}))
    }

    filter(value) {
        this.store.dispatch(reducer.createAction(reducer.types.filter, {value}))
    }

    closeDialog() {
        this.store.dispatch(reducer.createAction(reducer.types.closeDialog))
    }

    addItem() {
        this.store.dispatch(reducer.createAction(reducer.types.openAddDialog, {code:'', state:'', name: '', createDateTime: ''}))
    }

    modifyItem(data) {
        this.store.dispatch(reducer.createAction(reducer.types.openEditDialog, data))
    }

    deleteItem(data) {
        const agreeCallback = function(self, code) {
            return function() {
                post(self.handlers.services[commonNames.delete],
                    {
                        code: code,
                    },
                    self.updateSuccessfully(),
                    self.updateFailed)
            }
        }

        this.store.alert({
            title: '系统提示',
            message: `确认要删除资源【${data.code}】吗？`,
            agreeCallback: agreeCallback(this, data.code)
        })
    }

    deactivateItem(data) {
        const agreeCallback = function(self, code) {
            return function() {
                post(self.handlers.services[commonNames.deactivate],
                    {
                        code: code,
                    },
                    self.updateSuccessfully(),
                    self.updateFailed)
            }
        }

        this.store.alert({
            title: '系统提示',
            message: `确认要注销资源【${data.code}】吗？`,
            agreeCallback: agreeCallback(this, data.code)
        })
    }

    activateItem(data) {
        const agreeCallback = function(self, code) {
            return function() {
                post(self.handlers.services[commonNames.activate],
                    {
                        code: code,
                    },
                    self.updateSuccessfully(),
                    self.updateFailed)
            }
        }

        this.store.alert({
            title: '系统提示',
            message: `确认要激活资源【${data.code}】吗？`,
            agreeCallback: agreeCallback(this, data.code)
        })
    }

    updateResource() {
        const record = DefaultFormDialog.unboxing(this.store.getState().resource.dialog.form)
        post(this.handlers.services[this.store.getState().resource.mode],
            {
                id: record.id,
                name: record.name,
                code: record.code,
                state: record.state,
            },
            this.updateSuccessfully(),
            this.updateFailed)
    }

    updateSuccessfully(message) {
        const self = this
        return function(payload) {
            const agreeCallback = function() {
                return function() {
                    DefaultMainMenu.reloading(self.store, menuNames.resource);
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

    changeTablePage(event, page) {
        this.store.dispatch(reducer.createAction(reducer.types.changePage, {page}))
    }

    changeRowsPerPage(event) {
        this.store.dispatch(reducer.createAction(reducer.types.changeRowsPerPage, {rowsPerPage: parseInt(event.target.value, 10)}))
    }

    render() {
        const mode = this.store.getState().resource.mode
        if (commonNames.modify === mode || commonNames.add === mode)
            return <DefaultFormDialog classes={this.classes} state={this.store.getState().resource.dialog} handlers={this.handlers.dialog}/>

        return (
            <main className={this.classes.contentDefaultRoot}>
                <div className={this.classes.contentDefaultAppbarSpacer} />
                <div className={this.classes.contentDefaultHead}>
                    <AppBar className={this.classes.contentDefaultAppbar} position='static' color='secondary' elevation={0} >
                        <DefaultToolbar classes={this.classes} state={this.store.getState().resource.toolbar} handlers={this.handlers.toolbar}/>
                    </AppBar>
                </div>

                <Paper className={this.classes.contentDefaultBody}>
                    <DefaultTable classes={this.classes} state={this.store.getState().resource.table} handlers={this.handlers.table}/>
                </Paper>
            </main>
        )
    }
}

Content.propTypes = {
    classes: PropTypes.object.isRequired,
    store: PropTypes.object.isRequired,
}