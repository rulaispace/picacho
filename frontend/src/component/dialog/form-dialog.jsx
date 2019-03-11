import React from 'react';
import PropTypes from 'prop-types';
import Dialog from '@material-ui/core/Dialog';
import AppBar from '@material-ui/core/AppBar';
import Slide from '@material-ui/core/Slide';
import DefaultToolbar from "../toolbar/default-toolbar";
import {modifyWithDef} from "../../common/base/store-state-modifier";
import uuid from 'uuid'
import commonNames from "../../common/config/common-name-config";
import MenuItem from "@material-ui/core/es/MenuItem/MenuItem";
import TextField from "@material-ui/core/TextField";
import QuillEditor from "../editor/quill-editor";
import FileUpload from "../upload/file-upload";

const defaultInputHandler = {
    visible: true,
    className: 'formDefaultTextField',
    inputClassName: 'formDefaultInputLabel',
    selectMenuClassName: 'formDefaultSelectMenu',
    disabled: false,
}

const defaultInputState = {
    error: false,
    msg: '',
}

class DefaultFormDialog extends React.Component {
    constructor(props) {
        super(props)

        this.proxy = this.proxy.bind(this)

        this.classes = props.classes

        defaultInputHandler.proxy = this.proxy
        for (const property in props.handlers.form) {
            props.handlers.form[property] = modifyWithDef(props.handlers.form[property], defaultInputHandler)
            props.state.form[property] = modifyWithDef(props.state.form[property], defaultInputState)
        }
        this.handlers = props.handlers
        this.state = props.state
    }

    proxy(target, defValue) {
        if (typeof target == 'function') return target(this.state)
        if (target == null) return defValue

        return target
    }

    render() {
        return (
            <Dialog fullScreen open={this.state.open} TransitionComponent={Transition}>
                <AppBar className={this.classes.contentDefaultAppbar} position='static' color='secondary' elevation={0}>
                    <DefaultToolbar classes={this.classes} state={this.state.toolbar} handlers={this.handlers.toolbar}/>
                </AppBar>
                <form className={this.classes.formDefaultContainer} noValidate autoComplete="off">
                    {
                        Object.keys(this.handlers.form).filter(id => (this.handlers.form[id].proxy(this.handlers.form[id].visible))).map(id => {
                            return (
                                <DefaultFormInput
                                    key={id}
                                    classes={this.classes}
                                    state={this.state.form[id]}
                                    handler={{
                                        ...this.handlers.form[id],
                                        id
                                    }}
                                />
                            )
                        })
                    }
                </form>
            </Dialog>
        );
    }
}

DefaultFormDialog.propTypes = {
    classes: PropTypes.object.isRequired,
    handlers: PropTypes.object.isRequired,
    state: PropTypes.object.isRequired,
}

DefaultFormDialog.unboxing = function(state) {
    const result = {}
    for (const property in state) {
        result[property] = state[property].value
    }
    return result
}

function Transition(props) {
    return <Slide direction="up" {...props} />;
}

class DefaultFormInput extends React.Component {
    constructor(props) {
        super(props)
        this.state = props.state
        this.classes = props.classes
        this.handler = props.handler

        this.selectionItems = this.selectionItems.bind(this)
    }

    selectionItems(options) {
        if (!options) return null
        console.log(options)
        return Object.keys(options).forEach(value => {
            alert(value)
            return <MenuItem key={uuid.v1()} value={value}>{options[value]}</MenuItem>
        })
    }

    render() {
        if (this.handler.proxy(this.handler.type) === commonNames.inputTypeSelect) {
            return (
                <TextField
                    className={this.classes[this.handler.className]}
                    InputLabelProps={{
                        className: this.classes[this.handler.inputClassName]
                    }}
                    margin='dense'
                    disabled={this.handler.proxy(this.handler.disabled)}
                    error={this.state.error}
                    label={this.handler.proxy(this.handler.label)}
                    value={this.handler.proxy(this.handler.value, this.state.value)}
                    onChange={e=>{this.handler.handleChange(this.handler.id, e.target.value)}}
                    helperText={this.state.msg}
                    select={this.handler.proxy(this.handler.type) === commonNames.inputTypeSelect}
                    SelectProps={{
                        native: true,
                        MenuProps: {
                            className: this.classes[this.handler.selectMenuClassName]
                        }
                    }}
                >
                    {Object.keys(this.handler.options).map(value => {
                        return (
                            <option key={uuid.v1()} value={value}>{this.handler.options[value]}</option>
                        )
                    })}
                </TextField>
            )
        } else if (this.handler.proxy(this.handler.type) === commonNames.richTextEditor) {
            return (
                <QuillEditor
                    readOnly={this.handler.proxy(this.handler.disabled)}
                    label={this.handler.proxy(this.handler.label)}
                    text={this.handler.proxy(this.handler.value, this.state.value)}
                    onChange={(value)=>{this.handler.handleChange(this.handler.id, value)}}
                />
            )
        } else if (this.handler.proxy(this.handler.type) === commonNames.fileUploader) {
            return (
                <FileUpload
                    classes={this.classes}
                    readOnly={this.handler.proxy(this.handler.disabled)}
                    label={this.handler.proxy(this.handler.label)}
                    files={this.handler.proxy(this.handler.value, this.state.value)}
                    uploadUri={this.handler.proxy(this.handler.uploadUri)}
                    deleteUri={this.handler.proxy(this.handler.deleteUri)}
                />
            )
        } else {
            return (
                <TextField
                    className={this.classes[this.handler.className]}
                    InputLabelProps={{
                        className: this.classes[this.handler.inputClassName]
                    }}
                    margin='dense'
                    disabled={this.handler.proxy(this.handler.disabled)}
                    error={this.state.error}
                    label={this.handler.proxy(this.handler.label)}
                    value={this.handler.proxy(this.handler.value, this.state.value)}
                    onChange={e=>{this.handler.handleChange(this.handler.id, e.target.value)}}
                    helperText={this.state.msg}
                >
                </TextField>
            )
        }
    }
}

DefaultFormInput.propTypes = {
    classes: PropTypes.object.isRequired,
    state: PropTypes.object.isRequired,
    handler: PropTypes.object.isRequired,
}

export default DefaultFormDialog