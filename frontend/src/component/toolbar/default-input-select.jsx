import React from 'react'
import PropTypes from "prop-types";
import FormControl from "@material-ui/core/FormControl";
import Select from "@material-ui/core/Select";
import MenuItem from "@material-ui/core/MenuItem";
import uuid from "uuid";
import withStyles from "@material-ui/core/styles/withStyles";
import InputBase from "@material-ui/core/InputBase";

const BootstrapInput = withStyles(theme => ({
    root: {
        'label + &': {
            marginTop: theme.spacing.unit * 3,
        },
    },
    input: {
        borderRadius: 4,
        position: 'relative',
        fontSize: '13px',
        color: 'white',
        width: 'auto',
        padding: '10px 26px 10px 12px',
        transition: theme.transitions.create(['border-color', 'box-shadow']),
    },
}))(InputBase);

export default function DefaultInputSelect({state, classes, handlers}) {
    if (state.feature.showInput) {
        return (
            <div className={classes[state.input.className]}>
                <FormControl className={classes[state.input.inputRootClassName]}>
                    <Select
                        displayEmpty
                        value={handlers.value}
                        onChange={
                            (e) => {
                                e.preventDefault()
                                handlers.searchInputChanged(e.target.value)
                            }
                        }
                        input={<BootstrapInput id={uuid.v1()}/>}
                        MenuProps={{PaperProps: {
                                style: {
                                    maxHeight: 48 * 4.5 + 8,
                                    minWidth: 250,
                                }
                            }}}
                    >
                        {Object.keys(handlers.options).map(value => {
                            return (
                                <MenuItem key={uuid.v1()} value={value}>
                                    {handlers.options[value]}
                                </MenuItem>
                            )
                        })}
                    </Select>
                </FormControl>
            </div>
        )
    }
    return null
}

DefaultInputSelect.propTypes = {
    state: PropTypes.object.isRequired,
    classes: PropTypes.object.isRequired,
    handlers: PropTypes.object.isRequired,
}