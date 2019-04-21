import React from 'react'
import PropTypes from 'prop-types'
import DefaultToolbar from '../../../component/toolbar/default-toolbar'
import AppBar from '@material-ui/core/AppBar'
import iconNames from "../../../common/config/icon-name-config";
import Paper from "@material-ui/core/Paper";
import DefaultList from "../../../component/list/default-list";
import reducer from './reducer'
import ListRightButtonGroup from "./list-right-button-group";
import DefaultFormDialog from "../../../component/dialog/form-dialog";
import {post} from "../../../common/network/network";
import DefaultMainMenu from "../../app/default-main-menu";
import menuNames from "../../../common/config/menu-name-config";
import Any from '../../../common/base/any'
import buttonNames from "../../../common/config/button-name-config";
import commonNames from "../../../common/config/common-name-config";
import ListTextIcon from "./list-text-icon";

export default class Content extends React.Component {
    constructor(props) {
        super(props)

        this.classes = props.classes
        this.store = props.store

        this.nestListItemDeactivateAlert = this.nestListItemDeactivateAlert.bind(this)
        this.nestListItemProvisionEmployeeAlert = this.nestListItemProvisionEmployeeAlert.bind(this)
        this.nestListItemProvisionAdminAlert = this.nestListItemProvisionAdminAlert.bind(this)
        this.nestListItemProvisionAlert = this.nestListItemProvisionAlert.bind(this)
        this.nestListItemDeleteAlert = this.nestListItemDeleteAlert.bind(this)
        this.nestedListItemAddPerson = this.nestedListItemAddPerson.bind(this)
        this.nestedListItemAddGroup = this.nestedListItemAddGroup.bind(this)
        this.nestedListItemEdit = this.nestedListItemEdit.bind(this)
        this.nestedListItemSave = this.nestedListItemSave.bind(this)
        this.nestedListItemExpand = this.nestedListItemExpand.bind(this)
        this.nestedListItemCollapse = this.nestedListItemCollapse.bind(this)
        this.formInputChanged = this.formInputChanged.bind(this)
        this.formClose = this.formClose.bind(this)

        this.filter = this.filter.bind(this)
        this.updateFailed = this.updateFailed.bind(this)
        this.updateSuccessfully = this.updateSuccessfully.bind(this)

        this.handlers = {
            toolbar: {
                searchInputChanged: this.filter,
                rightButtonGroup: {
                    [iconNames.folder]: {
                        onClick: ()=> {
                            alert("The open folder button is clicked")
                        }
                    },
                    [iconNames.upload]: {
                        onClick: ()=> {
                            alert("The upload button is clicked")
                        },
                    }
                }
            },
            nestedList: {
                TextIconFactory: ListTextIcon,
                RightButtonGroupFactory: ListRightButtonGroup,
                rightButtonGroup: {
                    [iconNames.domainSharp]: {
                        onClick: this.nestedListItemAddGroup,
                    },
                    [iconNames.personAdd]: {
                        onClick: this.nestedListItemAddPerson,
                    },
                    [iconNames.edit]: {
                        onClick: this.nestedListItemEdit,
                    },
                    [iconNames.delete]: {
                        onClick: this.nestListItemDeleteAlert,
                    },
                    [iconNames.check]: {
                        onClick: this.nestListItemProvisionEmployeeAlert,
                    },
                    [iconNames.add]: {
                        onClick: this.nestListItemProvisionAdminAlert,
                    },
                    [iconNames.close]: {
                        onClick: this.nestListItemDeactivateAlert,
                    }
                },
                expandable: (state) => {
                    if (state == null) return false
                    if (state.data == null) return false
                    if (state.data.children == null) return false
                    if (state.data.children.length === 0) return false
                    if (state.data.type === commonNames.employee) return false

                    return true
                },
                expand: this.nestedListItemExpand,
                collapse: this.nestedListItemCollapse,
            },
            dialog: {
                services: {
                    modify: '/org/modify',
                    add: '/org/add',
                    delete: '/org/delete',
                    activate: '/org/activate',
                    deactivate: '/org/deactivate',
                },
                toolbar: {
                    leftButtonClicked: this.formClose,
                    rightButtonGroup: {
                        [buttonNames.save]: {
                            type: 'textButton',
                            text: '保存',
                            onClick: this.nestedListItemSave,
                        },
                        [buttonNames.close]: {
                            type: 'textButton',
                            text: '取消',
                            onClick: this.formClose,
                        }
                    },
                },
                form: {
                    parentName: {
                        label: (state) => (commonNames.department == Any.get(state.form, 'type') ? '上级部门' : '所属部门'),
                        disabled: true,
                    },
                    code: {
                        label: (state) => (commonNames.department == Any.get(state.form, 'type') ? '组织机构编码' : '登录账户名'),
                        disabled: function(store) {
                            return () => {
                                const formData = store.getState().organization.dialog.form;
                                return !(Any.get(formData, "state") !== commonNames.valid)
                            }
                        }(this.store),
                        className: 'formDefaultTextField3',
                        handleChange: this.formInputChanged,
                    },
                    primaryText: {
                        label: (state) => {
                            const type = Any.get(state.form, 'type')
                            return (commonNames.department === type) ? '部门名称' : '姓名'
                        },
                        className: 'formDefaultTextField3',
                        handleChange: this.formInputChanged,
                    },
                    secondaryText: {
                        label: (state) => {
                            const type = Any.get(state.form, 'type')
                            return (commonNames.department === type) ? '部门职能' : '岗位'
                        },
                        className: 'formDefaultTextField3',
                        handleChange: this.formInputChanged
                    }
                }
            },
        }
    }

    filter(value) {
        this.store.dispatch(reducer.createAction(reducer.types.filter, {value}))
    }

    nestListItemDeactivateAlert(data) {
        this.current = data

        const agreeCallback = function(self, code) {
            return function() {
                post(self.handlers.dialog.services[commonNames.deactivate],
                    {
                        username: code,
                    },
                    self.updateSuccessfully(),
                    self.updateFailed)
            }
        }

        this.store.alert({
            title: '系统提示',
            message: `确认要注销用户【${data.primaryText}】的系统使用权限吗？`,
            agreeCallback: agreeCallback(this, data.code)
        })
    }
    nestListItemProvisionEmployeeAlert(data) {
        this.nestListItemProvisionAlert(data, commonNames.employee)
    }
    nestListItemProvisionAdminAlert(data) {
        this.nestListItemProvisionAlert(data, commonNames.admin)
    }
    nestListItemProvisionAlert(data, rule) {
        this.current = data

        const message = function(payload) {
            return `用户【${payload.name}】已开通【${rule===commonNames.employee?'普通用户':'管理员'}】权限，
            登录账号：${payload.username}，初始密码：${payload.password}，请将初始密码发送用户并提醒其登录系统！`
        }

        const agreeCallback = function(self, code) {
            return function() {
                post(self.handlers.dialog.services[commonNames.activate],
                    {
                        username: code,
                        rule: rule,
                    },
                    self.updateSuccessfully(message),
                    self.updateFailed)
            }
        }

        this.store.alert({
            title: '系统提示',
            message: `确认要为用户【${data.primaryText}】开通${rule===commonNames.employee ? '普通用户' : '管理员'}权限吗？`,
            agreeCallback: agreeCallback(this, data.code)
        })
    }

    nestListItemDeleteAlert(data) {
        this.current = data

        const agreeCallback = function(self, code) {
            return function() {
                post(self.handlers.dialog.services[commonNames.delete],
                    {
                        code: code,
                    },
                    self.updateSuccessfully(),
                    self.updateFailed)
            }
        }

        this.store.alert({
            title: '系统提示',
            message: `确认要删除节点【${data.primaryText}】吗？`,
            agreeCallback: agreeCallback(this, data.code)
        })
    }



    nestedListItemAddPerson(data) {
        const {id, code, path, level, type, primaryText, secondaryText, parentCode, parentName, state} = data
        this.store.dispatch(reducer.createAction(reducer.types.openAddPersonDialog, {id, code, path, level, type, primaryText, secondaryText, parentCode, parentName, state}))
    }

    nestedListItemAddGroup(data) {
        const {id, code, path, level, type, primaryText, secondaryText, parentCode, parentName, state} = data
        this.store.dispatch(reducer.createAction(reducer.types.openAddGroupDialog, {id, code, path, level, type, primaryText, secondaryText, parentCode, parentName, state}))
    }

    nestedListItemEdit(data) {
        const {id, code, path, level, type, primaryText, secondaryText, parentCode, parentName, state} = data
        this.store.dispatch(reducer.createAction(reducer.types.openEditDialog, {id, code, path, level, type, primaryText, secondaryText, parentCode, parentName, state}))
    }

    nestedListItemSave() {
        const record = DefaultFormDialog.unboxing(this.store.getState().organization.dialog.form)
        post(this.handlers.dialog.services[this.store.getState().organization.mode],
            {
                id: record.id,
                code: record.code,
                parentCode: record.parentCode,
                name: record.primaryText,
                type: record.type,
                description: record.secondaryText
            },
            this.updateSuccessfully(),
            this.updateFailed)
    }

    updateSuccessfully(message) {
        const self = this
        return function(payload) {
            const agreeCallback = function() {
                return function() {
                    DefaultMainMenu.reloading(self.store, menuNames.org);
                }
            }

            if (self.current) self.store.getState().organization.dialog.form.path = self.current.path
            self.current = null

            self.store.tip({
                title: '系统提示',
                message: message ? message(payload) : "操作成功!",
                agreeCallback: agreeCallback(self)
            })
        }
    }

    updateFailed(err) {
        this.store.tip({
            title: err.title,
            message: err.detail,
        })
        this.current = null;
    }

    nestedListItemExpand(data) {
        this.store.dispatch(reducer.createAction(reducer.types.expand, data))
    }

    nestedListItemCollapse(data) {
        this.store.dispatch(reducer.createAction(reducer.types.collapse, data))
    }

    formInputChanged(id, value) {
        this.store.dispatch(reducer.createAction(reducer.types.modifyFormInput, {id, value}))
    }

    formClose() {
        this.store.dispatch(reducer.createAction(reducer.types.closeEditDialog))
    }

    render() {
        const mode = this.store.getState().organization.mode
        if (commonNames.modify === mode || commonNames.add === mode)
            return <DefaultFormDialog classes={this.classes} state={this.store.getState().organization.dialog} handlers={this.handlers.dialog}/>

        return (
            <main className={this.classes.contentDefaultRoot}>
                <div className={this.classes.contentDefaultAppbarSpacer} />
                <div className={this.classes.contentDefaultHead}>
                    <AppBar className={this.classes.contentDefaultAppbar} position='static' color='secondary' elevation={0} >
                        <DefaultToolbar classes={this.classes} state={this.store.getState().organization.toolbar} handlers={this.handlers.toolbar}/>
                    </AppBar>
                </div>
                <Paper className={this.classes.contentDefaultBody}>
                    <DefaultList classes={this.classes} state={this.store.getState().organization.nestedList} handlers={this.handlers.nestedList}/>
                </Paper>
            </main>
        )
    }
}

Content.propTypes = {
    classes: PropTypes.object.isRequired,
    store: PropTypes.object.isRequired,
}