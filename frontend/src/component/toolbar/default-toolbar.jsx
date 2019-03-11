import React from 'react'
import PropTypes from 'prop-types'
import Toolbar from '@material-ui/core/Toolbar'
import Typography from '@material-ui/core/Typography';
import IconButton from '@material-ui/core/IconButton'
import IconStore from "../../common/base/icon-store";
import DefaultRightButtonGroup from "./default-right-button-group";
import DefaultInput from "./default-input";
import iconNames from "../../common/config/icon-name-config";
import {modifyWithDef} from "../../common/base/store-state-modifier";

const defaultToolbarState = {
    feature: {
        rootClassName: 'toolbarDefaultRoot',
        variant: 'dense',
        disableGutters: false,
        growClassName: 'toolbarDefaultGrow',
        showLeftButton: false,
        showTitle: false,
        showInput: true,
        showInputIcon: true,
        showInputButton: false,
        showRightButtonGroup: false,
    },
    leftButton: {
        id: iconNames.menu,
        rootClassName: 'toolbarDefaultLeftButton',
    },
    title: {
        label: 'Material-UI',
        variant: 'h6',
        rootClassName: 'toolbarDefaultTitle',
    },
    input: {
        iconKey: iconNames.search,
        className: 'toolbarDefaultInput',
        iconClassName: 'toolbarDefaultInputIcon',
        inputRootClassName: 'toolbarDefaultInputRoot',
        inputInputClassName: 'toolbarDefaultInputInput',
        disabled: false,
    },
    rightButtonGroup: {
        rootClassName: 'toolbarDefaultRightButtonGroup',
    }
}

const defaultHandlers = {
    InputFactory: DefaultInput,
    RightButtonGroupFactory: DefaultRightButtonGroup,
    inputRef: React.createRef(),
}

export default class DefaultToolbar extends React.Component {
    constructor(props) {
        super(props)

        this.classes = props.classes
        this.handlers = modifyWithDef(props.handlers, defaultHandlers)
        this.state = modifyWithDef(props.state, defaultToolbarState)
    }

    render() {
        const {
            feature: {
                variant: toolbarVariant,
                className: toolbarRoot,
                disableGutters=false,
                growClassName,
                showLeftButton,
                showTitle,
            },
            leftButton : {
                id: leftButtonIconKey,
                className: leftButtonClassName,
            },
            title: {
                label,
                variant: titleVariant,
                className: titleClassName,
            },
        } = this.state

        const {
            InputFactory,
            RightButtonGroupFactory,
            leftButtonClicked,
        } = this.handlers

        return (
            <Toolbar className={this.classes[toolbarRoot]} variant={toolbarVariant} disableGutters={disableGutters}>
                {/** 左侧按钮 **/}
                {showLeftButton ? (
                    <IconButton
                        className={this.classes[leftButtonClassName]} color='inherit'
                        aria-label='left button'
                        onClick={(e) => {
                            e.preventDefault()
                            leftButtonClicked(this.state)
                        }}
                    >
                        <IconStore iconKey={leftButtonIconKey}/>
                    </IconButton>
                ) : null}

                {/** 标题 **/}
                {showTitle ? (
                    <Typography
                        className={this.classes[titleClassName]}
                        variant={titleVariant}
                        color="inherit"
                        noWrap
                    >
                        {label}
                    </Typography>
                ) : null}
                {/** 主输入框 **/}
                <InputFactory state={this.state} classes={this.classes} handlers={this.handlers}/>

                <div className={this.classes[growClassName]} />

                {/** 右侧按钮区域 **/}
                <RightButtonGroupFactory state={this.state} classes={this.classes} handlers={this.handlers.rightButtonGroup}/>
            </Toolbar>
        )
    }
}

DefaultToolbar.propTypes = {
    classes: PropTypes.object.isRequired,
    state: PropTypes.object.isRequired,
    handlers: PropTypes.object.isRequired,
}