import React from 'react'
import IconStore from "../../common/base/icon-store";
import PropTypes from "prop-types";
import InputBase from "@material-ui/core/InputBase";

export default function DefaultInput({state, classes, handlers}) {
    const {
        feature: {
            showInput,
            showInputIcon,
        },
        input: {
            iconKey: inputIconKey,
            className: inputClassName,
            iconClassName: inputIconClassName,
            inputRootClassName,
            inputInputClassName,
            placeholder: inputPlaceholder,
            defaultValue: inputDefaultValue,
            disabled: inputDisabled,
        },
    } = state

    const {
        inputRef: inputReactRef,
        searchInputChanged: inputOnChange,
    } = handlers

    if (showInput) {
        return (
            <div className={classes[inputClassName]}>
                {showInputIcon ? (
                    <div className={classes[inputIconClassName]}>
                        <IconStore iconKey={inputIconKey} />
                    </div>
                ) : null}
                <InputBase
                    placeholder={inputPlaceholder}
                    defaultValue={inputDefaultValue}
                    inputRef={inputReactRef}
                    disabled={inputDisabled}
                    onChange={
                        (e) => {
                            e.preventDefault()
                            inputOnChange(inputReactRef.current.value)
                        }
                    }
                    classes={{
                        root: classes[inputRootClassName],
                        input: classes[inputInputClassName],
                    }}
                />
            </div>
        )
    }

    return null
}

DefaultInput.propTypes = {
    state: PropTypes.object.isRequired,
    classes: PropTypes.object.isRequired,
    handlers: PropTypes.object.isRequired,
}