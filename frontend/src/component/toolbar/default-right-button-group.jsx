import React from 'react'
import IconStore from "../../common/base/icon-store";
import IconButton from "@material-ui/core/IconButton";
import PropTypes from "prop-types";
import Button from "@material-ui/core/es/Button/Button";
import commonNames from "../../common/config/common-name-config";
import Tooltip from "@material-ui/core/es/Tooltip/Tooltip";
import uuid from "uuid";

function proxy(func, args) {
    return function (e) {
        e.preventDefault()
        func(args)
    }
}

function InnerTextButton({id, handlers, state}) {
    return <Button color='inherit' onClick={proxy(handlers[id].onClick, state)}> {handlers[id].text} </Button>
}

InnerTextButton.propTypes = {
    id: PropTypes.string.isRequired,
    handlers: PropTypes.object.isRequired,
    state: PropTypes.object.isRequired,
}

function InnerIconButton({id, handlers, state}) {
    return <IconButton color={'inherit'} onClick={proxy(handlers[id].onClick, state)}> <IconStore iconKey={id}/> </IconButton>
}

InnerIconButton.propTypes = {
    id: PropTypes.string.isRequired,
    handlers: PropTypes.object.isRequired,
    state: PropTypes.object.isRequired,
}

function InnerButton({type, id, handlers, state}) {
    return type === commonNames.textButton ?
        <InnerTextButton handlers={handlers} id={id} state={state}/> :
        <InnerIconButton handlers={handlers} id={id} state={state}/>
}

InnerButton.propTypes = {
    type: PropTypes.string,
    id: PropTypes.string.isRequired,
    handlers: PropTypes.object.isRequired,
    state: PropTypes.object.isRequired,
}

export default function DefaultRightButtonGroup({state, classes, handlers}) {
    const proxy = (target, defVal) => {
        if (target == null) return defVal

        if (typeof target == 'function') return target()
        return target
    }

    if (state.feature.showRightButtonGroup) {
        return (
            <div className={classes[state.rightButtonGroup.rightButtonGroupClassName]}>
                {
                    Object.keys(handlers).map(id => {
                        if (proxy(handlers[id].visible, true)) {
                            return (
                                <Tooltip key={uuid.v1()} title={handlers[id].tip ? handlers[id].tip : ''}>
                                    <InnerButton type={handlers[id].type} id={id} handlers={handlers} state={state}/>
                                </Tooltip>
                            )
                        }
                        return null
                    })
                }
            </div>
        )
    }

    return null
}

DefaultRightButtonGroup.propTypes = {
    state: PropTypes.object.isRequired,
    classes: PropTypes.object.isRequired,
    handlers: PropTypes.object,
}