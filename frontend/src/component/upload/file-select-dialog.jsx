import React, { Component } from "react";
import IconButton from "@material-ui/core/IconButton";
import IconStore from "../../common/base/icon-store";
import iconNames from "../../common/config/icon-name-config";
import Tooltip from "@material-ui/core/Tooltip";
import PropTypes from "prop-types";
import InputBase from "@material-ui/core/InputBase";
import Divider from "@material-ui/core/Divider";
import {upload} from "../../common/network/network";
import message from "../dialog/common-dialog-handler";

class FileSelectDialog extends Component {
    constructor(props) {
        super(props);
        this.classes = props.classes
        this.state = props.state ? props.state : {
            disabled: false,
            file: null,
        }
        this.uploadUri = props.uploadUri
        this.callback = props.callback

        this.fileInputRef = React.createRef()

        this.openFileDialog = this.openFileDialog.bind(this)
        this.onFileAdded = this.onFileAdded.bind(this)
        this.uploadFile = this.uploadFile.bind(this)
        this.deleteSelectedFile = this.deleteSelectedFile.bind(this)
        this.uploadFileSuccessfully = this.uploadFileSuccessfully.bind(this)
    }

    uploadFile() {
        if (this.state.disabled) return ;
        if (!this.state.file) return ;
        upload(this.uploadUri, this.state.file, this.uploadFileSuccessfully, (error)=>{message.tip({title: '系统错误', message: error.detail})})
    }

    uploadFileSuccessfully(payload) {
        // 调用父类的回调函数
        this.callback(payload)

        // 清空当前数据
        this.setState({
            ...this.state,
            file: null,
        })
    }

    deleteSelectedFile() {
        if (this.state.disabled) return;
        this.setState({
            ...this.state,
            file: null,
        })
    }

    openFileDialog() {
        if (this.state.disabled) return;
        this.fileInputRef.current.click();
    }

    onFileAdded(evt) {
        if (this.state.disabled) return;
        const file = evt.target.files.item(0)
        this.setState({
            ...this.state,
            file: file,
        })
    }

    render() {
        return (
            <div className={this.classes.fileUploadFileSelectDefaultContent}>
                <Tooltip title={'选择上传文件'} className={this.classes.fileSelectOpenDialogButtonDefault}>
                    <IconButton onClick={this.openFileDialog}>
                        <IconStore iconKey={iconNames.menu}/>
                    </IconButton>
                </Tooltip>
                <InputBase disabled className={this.classes.fileSelectVisibleInputDefault} value={this.state.file ? this.state.file.name : "请选择文件..."} />
                <Tooltip title={'确认上传文件'} className={this.classes.fileSelectUploadButtonDefault}>
                    <IconButton onClick={this.uploadFile}>
                        <IconStore iconKey={iconNames.upload}/>
                    </IconButton>
                </Tooltip>
                <Divider className={this.classes.fileSelectRightButtonDivider} />
                <Tooltip title={'删除选择的文件'} className={this.classes.fileSelectUploadButtonDefault}>
                    <IconButton onClick={this.deleteSelectedFile}>
                        <IconStore iconKey={iconNames.delete}/>
                    </IconButton>
                </Tooltip>
                <input ref={this.fileInputRef} className={this.classes.fileSelectInputDefault} type="file" onChange={this.onFileAdded}/>
            </div>
        );
    }
}

FileSelectDialog.propTypes = {
    classes: PropTypes.object.isRequired,
    callback: PropTypes.func.isRequired,
    state: PropTypes.object,
    uploadUri: PropTypes.string.isRequired,
}

export default FileSelectDialog;