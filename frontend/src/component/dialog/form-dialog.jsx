import React from 'react';
import PropTypes from 'prop-types';
import Dialog from '@material-ui/core/Dialog';
import AppBar from '@material-ui/core/AppBar';
import Slide from '@material-ui/core/Slide';
import DefaultToolbar from "../toolbar/default-toolbar";
import {modifyWithDef} from "../../common/base/store-state-modifier";
import DefaultFormInput from "./form-input";

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

export default DefaultFormDialog