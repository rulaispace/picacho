import React from 'react'
import PropTypes from 'prop-types'
import AppBar from '@material-ui/core/AppBar'
import DefaultToolbar from '../../../component/toolbar/default-toolbar'
import DefaultTable from '../../../component/table/default-table'
import Paper from '@material-ui/core/Paper'
import reducer from './reducer'
import commonNames from "../../../common/config/common-name-config";
import {post} from "../../../common/network/network";
import DefaultMainMenu from "../../app/default-main-menu";
import menuNames from "../../../common/config/menu-name-config";

export default class Content extends React.Component {
    constructor(props) {
        super(props)

        this.classes = props.classes
        this.store = props.store

        this.filter = this.filter.bind(this)
        this.changeTablePage = this.changeTablePage.bind(this)
        this.changeRowsPerPage = this.changeRowsPerPage.bind(this)
        this.operation = this.operation.bind(this)
        this.activate = this.activate.bind(this)
        this.deactivate = this.deactivate.bind(this)
        this.resetPassword = this.resetPassword.bind(this)
        this.updateSuccessfully = this.updateSuccessfully.bind(this)
        this.updateFailed = this.updateFailed.bind(this)

        this.handlers = {
            toolbar: {
                searchInputChanged: this.filter,
            },
            table: {
                services: {
                    resetPassword: '/user/resetPassword',
                    activate: '/org/activate',
                    deactivate: '/org/deactivate',
                },
                rule: {
                    label: function(ordinal) {
                        if (ordinal === commonNames.admin) return '管理员'
                        if (ordinal === commonNames.employee) return '普通用户'
                    }
                },
                operator: {
                    onClick: this.operation,
                    label: function(ordinal) {
                        if (ordinal === commonNames.resetPassword) {
                            return '重置密码'
                        }
                        if (ordinal === commonNames.deactivate) {
                            return '注销'
                        }
                        if (ordinal === commonNames.activate) {
                            return '激活'
                        }

                        return ordinal
                    }
                },
                state: {
                    label: function(ordinal) {
                        if (ordinal === commonNames.valid)
                            return '有效'
                        if (ordinal === commonNames.invalid)
                            return '注销'

                        return ordinal
                    },
                },
                pagination: {
                    changePage: this.changeTablePage,
                    changeRowsPerPage: this.changeRowsPerPage,
                },
            }
        }
    }

    operation(ordinal) {
        if (ordinal === commonNames.activate) return this.activate
        if (ordinal === commonNames.deactivate) return this.deactivate
        if (ordinal === commonNames.resetPassword) return this.resetPassword
        return f=>f
    }

    activate(data) {
        this.current = data

        const message = function(payload) {
            return `用户【${payload.name}】已激活，登录账号：${payload.username}，初始密码：${payload.password}，请将初始密码发送用户并提醒其登录系统！`
        }

        const agreeCallback = function(self, username) {
            return function() {
                post(self.handlers.table.services[commonNames.activate],
                    {
                        username,
                    },
                    self.updateSuccessfully(message),
                    self.updateFailed)
            }
        }

        this.store.alert({
            title: '系统提示',
            message: `确认要激活用户【${data.name}】的系统使用权限吗？`,
            agreeCallback: agreeCallback(this, data.username)
        })
    }

    deactivate(data) {
        this.current = data

        const agreeCallback = function(self, username) {
            return function() {
                post(self.handlers.table.services[commonNames.deactivate],
                    {username},
                    self.updateSuccessfully(),
                    self.updateFailed)
            }
        }

        this.store.alert({
            title: '系统提示',
            message: `确认要注销用户【${data.name}】的系统使用权限吗？`,
            agreeCallback: agreeCallback(this, data.username)
        })
    }

    resetPassword(data) {
        this.current = data

        const message = function(payload) {
            return `用户【${payload.name}】已激活，登录账号：${payload.username}，初始密码：${payload.password}，请将初始密码发送用户并提醒其登录系统！`
        }

        const agreeCallback = function(self, username) {
            return function() {
                post(self.handlers.table.services[commonNames.resetPassword],
                    {
                        username
                    },
                    self.updateSuccessfully(message),
                    self.updateFailed)
            }
        }

        this.store.alert({
            title: '系统提示',
            message: `确认要重置用户【${data.name}】的登录密码吗？`,
            agreeCallback: agreeCallback(this, data.username)
        })
    }

    updateSuccessfully(message) {
        const self = this
        return function(payload) {
            const agreeCallback = function(self) {
                return function() {
                    DefaultMainMenu.reloading(self.store, menuNames.rule);
                }
            }
            self.store.tip({
                title: '系统提示',
                message: message ? message(payload) : "操作成功！",
                agreeCallback: agreeCallback(self)
            })
        }
    }

    updateFailed(err) {
        this.store.tip({
            title: err.title,
            message: err.detail,
        })
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
        return (
            <main className={this.classes.contentDefaultRoot}>
                <div className={this.classes.contentDefaultAppbarSpacer} />
                <div className={this.classes.contentDefaultHead}>
                    <AppBar className={this.classes.contentDefaultAppbar} position='static' color='secondary' elevation={0} >
                        <DefaultToolbar classes={this.classes} state={this.store.getState().rule.toolbar} handlers={this.handlers.toolbar}/>
                    </AppBar>
                </div>
                <Paper className={this.classes.contentDefaultBody}>
                    <DefaultTable classes={this.classes} state={this.store.getState().rule.table} handlers={this.handlers.table}/>
                </Paper>
            </main>
        )
    }
}

Content.propTypes = {
    classes: PropTypes.object.isRequired,
    store: PropTypes.object.isRequired,
}