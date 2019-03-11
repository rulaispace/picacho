import ReactQuill from 'react-quill'
import './quill-editor.css'
import * as React from "react";
import PropTypes from "prop-types";

class QuillEditor extends React.Component {
    constructor(props) {
        super(props)
        this.handleChange = this.handleChange.bind(this)

        this.readOnly=props.readOnly
        this.label=props.label
        this.onChange=props.onChange

        this.state={text: props.text}
    }

    handleChange(value) {
        if (this.readOnly) return ;
        this.setState({text: value})
        this.onChange(value)
    }

    render() {
        return (
            <div className='editor-root'>
                <div className='editor-label-container'>
                    <label className='editor-label'>{this.label}</label>
                </div>
                <ReactQuill readOnly={this.readOnly} value={this.state.text} onChange={this.handleChange} />
            </div>
        )
    }
}

QuillEditor.propTypes = {
    readOnly: PropTypes.bool.isRequired,
    label: PropTypes.string.isRequired,
    text: PropTypes.string.isRequired,
    onChange: PropTypes.func.isRequired,
}

export default QuillEditor