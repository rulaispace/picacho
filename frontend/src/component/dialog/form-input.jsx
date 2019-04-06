import React from "react";
import PropTypes from "prop-types";
import uuid from 'uuid'
import commonNames from "../../common/config/common-name-config";
import TextField from "@material-ui/core/TextField";
import QuillEditor from "../editor/quill-editor";
import FileUpload from "../upload/file-upload";
import Input from "@material-ui/core/Input";
import FormControl from "@material-ui/core/FormControl";
import InputLabel from "@material-ui/core/InputLabel";
import Select from "@material-ui/core/Select";
import MenuItem from "@material-ui/core/MenuItem";
import FormHelperText from "@material-ui/core/FormHelperText";

class DefaultFormInput extends React.Component {
    constructor(props) {
        super(props)
        this.state = props.state
        this.classes = props.classes
        this.handler = props.handler
    }

    render() {
        if (this.handler.proxy(this.handler.type) === commonNames.inputTypeSelect) {
            return (
                <FormControl
                    className={this.classes[this.handler.className]}
                    disabled={this.handler.proxy(this.handler.disabled)}
                    error={this.state.error}
                >
                    <InputLabel className={this.classes[this.handler.inputClassName]} htmlFor={"select-single-" + this.handler.proxy(this.handler.id)}>{this.handler.proxy(this.handler.label)}</InputLabel>
                    <Select
                        value={this.handler.proxy(this.handler.value, this.state.value)}
                        onChange={e=>{
                            this.handler.handleChange(this.handler.id, e.target.value)
                        }}
                        input={<Input id={"select-single-" + this.handler.proxy(this.handler.id)}/>}
                        MenuProps={{PaperProps: {
                                style: {
                                    maxHeight: 48 * 4.5 + 8,
                                    minWidth: 250,
                                }
                            }}}
                    >
                        {Object.keys(this.handler.options).map(value => {
                            return (
                                <MenuItem key={uuid.v1()} value={value}>
                                    {this.handler.options[value]}
                                </MenuItem>
                            )
                        })}
                    </Select>
                    <FormHelperText>{this.state.msg}</FormHelperText>
                </FormControl>
            )
        } else if (this.handler.proxy(this.handler.type) === commonNames.inputTypeMultiSelect) {
            return (
                <FormControl
                    className={this.classes[this.handler.className]}
                    disabled={this.handler.proxy(this.handler.disabled)}
                >
                    <InputLabel className={this.classes[this.handler.inputClassName]} htmlFor={"select-multiple-" + this.handler.proxy(this.handler.id)}>{this.handler.proxy(this.handler.label)}</InputLabel>
                    <Select
                        multiple
                        value={this.handler.proxy(this.handler.value, this.state.value)}
                        onChange={e=>{
                            this.handler.handleChange(this.handler.id, e.target.value)
                        }}
                        input={<Input id={"select-multiple-" + this.handler.proxy(this.handler.id)}/>}
                        MenuProps={{PaperProps: {
                                style: {
                                    maxHeight: 48 * 4.5 + 8,
                                    minWidth: 250,
                                }
                            }}}
                    >
                        {Object.keys(this.handler.options).map(value => {
                            return (
                                <MenuItem key={uuid.v1()} value={value}>
                                    {this.handler.options[value]}
                                </MenuItem>
                            )
                        })}
                    </Select>
                </FormControl>
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
        } else if (this.handler.proxy(this.handler.type) === commonNames.inputTypeDatePicker) {
            return (
                <TextField
                    disabled={this.handler.proxy(this.handler.disabled)}
                    id={this.handler.proxy(this.handler.id)}
                    label={this.handler.proxy(this.handler.label)}
                    type="datetime-local"
                    defaultValue={this.handler.proxy(this.handler.value, this.state.value)}
                    className={this.classes[this.handler.className]}
                    InputLabelProps={{
                        className: this.classes[this.handler.inputClassName],
                        shrink: true,
                    }}
                    onChange={(e)=>{this.handler.handleChange(this.handler.id, e.target.value)}}
                >
                </TextField>
            )
        }
        else {
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

export default DefaultFormInput