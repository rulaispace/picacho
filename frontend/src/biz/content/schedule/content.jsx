import React from "react";
import AppBar from "@material-ui/core/AppBar";
import PropTypes from "prop-types";
import iconNames from '../../../common/config/icon-name-config'
import DefaultToolbar from "../../../component/toolbar/default-toolbar";
import DefaultTable from "../../../component/table/default-table";
import Paper from "@material-ui/core/Paper";
import commonNames from "../../../common/config/common-name-config";
import buttonNames from "../../../common/config/button-name-config";
import DefaultFormDialog from "../../../component/dialog/form-dialog";
import reducer from "./reducer";
import dateFormat from 'dateformat'
import Any from "../../../common/base/any";
import {post} from "../../../common/network/network";
import DefaultMainMenu from "../../app/default-main-menu";
import menuNames from "../../../common/config/menu-name-config";
import DefaultInputSelect from "../../../component/toolbar/default-input-select";

let preStartTime = null;
let preFinishTime = null;
let userList = null;

export default class Content extends React.Component {
    constructor(props) {
        super(props)

        this.classes = props.classes
        this.store = props.store

        this.addSchedule = this.addSchedule.bind(this);
        this.closeDialog = this.closeDialog.bind(this);
        this.formInputChanged = this.formInputChanged.bind(this);
        this.updateResourceOptions = this.updateResourceOptions.bind(this);
        this.updateFailed = this.updateFailed.bind(this);
        this.updateAccountsOptions = this.updateAccountsOptions.bind(this);
        this.loadingSelectOptions = this.loadingSelectOptions.bind(this);
        this.upsertSchedule = this.upsertSchedule.bind(this);
        this.updateSuccessfully = this.updateSuccessfully.bind(this);
        this.viewItem = this.viewItem.bind(this);
        this.saveButtonVisible = this.saveButtonVisible.bind(this);
        this.searchInputChanged = this.searchInputChanged.bind(this);
        this.loadingToolbarSelectOptions = this.loadingToolbarSelectOptions.bind(this);
        this.changeTablePage = this.changeTablePage.bind(this);
        this.changeRowsPerPage = this.changeRowsPerPage.bind(this);

        this.handlers = {
            services: {
                add: '/schedule/add',
                modify: '/schedule/modify',
                delete: '/schedule/delete',
                publish: '/schedule/release',
                callback: '/schedule/callback',
                [commonNames.idleResources]: '/schedule/query/resource/idle',
                [commonNames.idleAccounts]: '/schedule/query/account/idle',
            },
            toolbar: {
                InputFactory: DefaultInputSelect,
                value: this.store.getState().schedule.toolbar.input.value,
                options: {},
                searchInputChanged: this.searchInputChanged,
                rightButtonGroup: {[iconNames.add]: {onClick: this.addSchedule}}
            },
            table: {
                participants: {
                  label: function(ordinal) {
                      return ordinal.reduce((acc, participant) => {
                          if (userList == null) {
                              acc += " " + participant.username;
                          } else {
                              const target = userList.filter(user => {return (user.username === participant.username)})
                              if (target != null && target.length != 0) {
                                  acc += " " + target[0].name;
                              } else {
                                  acc += " " + participant.username;
                              }
                          }
                          return acc;
                      }, '');
                  }
                },
                operator: {
                    onClick: (ordinal) => {
                        switch (ordinal) {
                            case commonNames.view: return this.viewItem
                            default: return f=>f
                        }
                    },
                    label: function(ordinal) {
                        if (ordinal === commonNames.view) {
                            return "查阅";
                        }
                        return ordinal
                    }
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
                            onClick: this.upsertSchedule,
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
                            return () => !(store.getState().schedule.mode === commonNames.add || store.getState().schedule.mode === commonNames.modify)
                        }(this.store),
                        className: 'formDefaultTextField',
                        handleChange: this.formInputChanged,
                    },
                    startTime: {
                        type: commonNames.inputTypeDatePicker,
                        label: '开始时间',
                        disabled: function(store) {
                            return () => !(store.getState().schedule.mode === commonNames.add || store.getState().schedule.mode === commonNames.modify)
                        }(this.store),
                        className: 'formDefaultTextField2',
                        handleChange: this.formInputChanged,
                    },
                    finishTime: {
                        type: commonNames.inputTypeDatePicker,
                        label: '结束时间',
                        disabled: function(store) {
                            return () => !(store.getState().schedule.mode === commonNames.add || store.getState().schedule.mode === commonNames.modify)
                        }(this.store),
                        className: 'formDefaultTextField2',
                        handleChange: this.formInputChanged,
                    },
                    participants: {
                        type: commonNames.inputTypeMultiSelect,
                        options: {},
                        label: '参会人员',
                        disabled: function(store) {
                            return () => !(store.getState().schedule.mode === commonNames.add || store.getState().schedule.mode === commonNames.modify)
                        }(this.store),
                        handleChange: this.formInputChanged,
                    },
                    resources: {
                        type: commonNames.inputTypeMultiSelect,
                        options: {},
                        label: '选择资源',
                        disabled: function(store) {
                            return () => !(store.getState().schedule.mode === commonNames.add || store.getState().schedule.mode === commonNames.modify)
                        }(this.store),
                        handleChange: this.formInputChanged,
                    },
                    content: {
                        type: commonNames.richTextEditor,
                        label: '内容描述',
                        disabled: function(store) {
                            return () => !(store.getState().schedule.mode === commonNames.add || store.getState().schedule.mode === commonNames.modify)
                        }(this.store),
                        handleChange: this.formInputChanged,
                    },
                    attachments: {
                        type: commonNames.fileUploader,
                        label: '附件',
                        className: 'formDefaultTextField',
                        disabled: function(store) {
                            return () => !(store.getState().schedule.mode === commonNames.add || store.getState().schedule.mode === commonNames.modify)
                        }(this.store),
                        handleChange: this.formInputChanged,
                        uploadUri: '/schedule/attachment/upload',
                        deleteUri: '/schedule/attachment/delete',
                    },
                }
            },
        }
    }

    componentDidMount() {
        this.loadingToolbarSelectOptions();
    }

    loadingToolbarSelectOptions() {
        if (this.handlers.toolbar.value == null || this.handlers.toolbar.value === "")
            this.handlers.toolbar.value = localStorage.getItem(commonNames.username)

        post(
            "/user/query",
            {},
            function(self) {
                return function(payload) {
                    userList = payload;
                    for (const property in self.handlers.toolbar.options) {
                        delete self.handlers.toolbar.options[property];
                    }
                    for (const property in payload) {
                        self.handlers.toolbar.options[payload[property].username] = payload[property].name;
                    }
                    self.setState({});
                }
            }(this),
            this.updateFailed);
    }

    searchInputChanged(value) {
        this.store.getState().schedule.toolbar.input.value = value;
        // this.setState({});
        DefaultMainMenu.reloading(this.store, menuNames.schedule, {username: value});
    }

    addSchedule() {
        const startTime = new Date(new Date().setHours(8, 0, 0, 0));
        const finishTime = new Date(new Date().setHours(9, 0, 0, 0));

        this.store.dispatch(reducer.createAction(reducer.types.openAddDialog,
            {
                title:'',
                content:'',
                startTime: dateFormat(startTime, "yyyy-mm-dd'T'HH:MM"),
                finishTime: dateFormat(finishTime, "yyyy-mm-dd'T'HH:MM"),
                resources:[],
                participants: [],
                attachments: []
            }
        ))

        this.loadingSelectOptions();
    }

    upsertSchedule() {
        const record = DefaultFormDialog.unboxing(this.store.getState().schedule.dialog.form);
        post(this.handlers.services[this.store.getState().schedule.mode],
            {
                id: record.id,
                title: record.title,
                content: record.content,
                startTime: dateFormat(new Date(record.startTime), "yyyy-mm-dd HH:MM:ss"),
                finishTime: dateFormat(new Date(record.finishTime), "yyyy-mm-dd HH:MM:ss"),
                attachments: record.attachments,
                participants: record.participants.map(value => ({username: value})),
                resources: record.resources.map(value => ({resourceId: value})),
            },
            this.updateSuccessfully(),
            this.updateFailed)
    }

    updateSuccessfully(message) {
        const self = this
        return function(payload) {
            const agreeCallback = function() {
                return function() {
                    DefaultMainMenu.reloading(self.store, menuNames.schedule);
                }
            }

            self.store.tip({
                title: '系统提示',
                message: message ? message(payload) : "操作成功!",
                agreeCallback: agreeCallback(self)
            })
        }
    }

    closeDialog() {
        this.store.dispatch(reducer.createAction(reducer.types.closeDialog))
    }

    formInputChanged(id, value) {
        this.loadingSelectOptions();
        this.store.dispatch(reducer.createAction(reducer.types.modifyFormInput, {id, value}))
    }

    viewItem(data) {
        this.store.dispatch(reducer.createAction(reducer.types.openViewDialog, {
            title: data.title,
            startTime: dateFormat(data.startTime, "yyyy-mm-dd'T'HH:MM"),
            finishTime: dateFormat(data.finishTime, "yyyy-mm-dd'T'HH:MM"),
            attachments: data.attachments,
            participants: data.participants.reduce(
                (acc, participant) => {
                    acc.push(participant.username);
                    return acc
                }, []),
            resources: data.resources.reduce(
                (acc, resource) => {
                    acc.push(resource.resourceId);
                    return acc;
                }, []),
            content: data.content
        }))

        this.loadingSelectOptions("2099-12-31T00:00", "2099-12-31T23:59");
    }

    saveButtonVisible() {
        return this.store.getState().schedule.mode !== commonNames.view
    }

    loadingSelectOptions(defStartTime, defFinishTime) {
        const formData = this.store.getState().schedule.dialog.form
        let startTime = Any.get(formData, "startTime")
        let finishTime = Any.get(formData, "finishTime")

        if (defStartTime != null) startTime = defStartTime;
        if (defFinishTime != null) finishTime = defFinishTime;

        preStartTime = startTime
        preFinishTime = finishTime

        post(this.handlers.services[commonNames.idleResources],
            {
                startTime: dateFormat(new Date(preStartTime), "yyyy-mm-dd HH:MM:ss"),
                finishTime: dateFormat(new Date(preFinishTime), "yyyy-mm-dd HH:MM:ss")
            },
            this.updateResourceOptions,
            this.updateFailed,
        )

        post(this.handlers.services[commonNames.idleAccounts],
            {
                startTime: dateFormat(new Date(preStartTime), "yyyy-mm-dd HH:MM:ss"),
                finishTime: dateFormat(new Date(preFinishTime), "yyyy-mm-dd HH:MM:ss")
            },
            this.updateAccountsOptions,
            this.updateFailed,
        )
    }

    updateResourceOptions(payload) {
        const resourceOptions = this.handlers.dialog.form.resources.options;
        for (const property in resourceOptions) {
            delete resourceOptions[property];
        }

        for (const item in payload) {
            resourceOptions[payload[item].id] = payload[item].name
        }
    }

    updateAccountsOptions(payload) {
        const participantOptions = this.handlers.dialog.form.participants.options;
        for (const property in participantOptions) {
            delete participantOptions[property];
        }

        for (const item in payload) {
            participantOptions[payload[item].username] = payload[item].name
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
        if (this.store.getState().schedule.mode === commonNames.add
            || this.store.getState().schedule.mode === commonNames.modify
            || this.store.getState().schedule.mode === commonNames.view) {
            return <DefaultFormDialog classes={this.classes} handlers={this.handlers.dialog} state={this.store.getState().schedule.dialog}/>
        } else {
            return (
                <main className={this.classes.contentDefaultRoot}>
                    <div className={this.classes.contentDefaultAppbarSpacer}/>
                    <div className={this.classes.contentDefaultHead}>
                        <AppBar
                            className={this.classes.contentDefaultAppbar}
                            position='static'
                            color='secondary'
                            elevation={0}
                        >
                            <DefaultToolbar classes={this.classes} state={this.store.getState().schedule.toolbar}
                                            handlers={this.handlers.toolbar}/>
                        </AppBar>
                    </div>
                    <Paper className={this.classes.contentDefaultBody}>
                        <DefaultTable classes={this.classes} state={this.store.getState().schedule.table}
                                      handlers={this.handlers.table}/>
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