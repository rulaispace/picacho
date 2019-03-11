import React, { Component } from "react";
import FileSelectDialog from "./file-select-dialog";
import PropTypes from "prop-types";
import * as uuid from "uuid";
import iconNames from "../../common/config/icon-name-config";
import IconStore from "../../common/base/icon-store";
import IconButton from "@material-ui/core/es/IconButton/IconButton";
import Link from "@material-ui/core/Link";
import message from "../dialog/common-dialog-handler";
import {linkUrl, post} from "../../common/network/network";
import DefaultMainMenu from "../../biz/app/default-main-menu";
import menuNames from "../../common/config/menu-name-config";

class FileUpload extends Component {
    constructor(props) {
        super(props);
        this.classes = props.classes
        this.state = {
            label: props.label,
            readOnly: props.readOnly,
            files: props.files,
            uploadUri: props.uploadUri,
            deleteUri: props.deleteUri,
        };

        this.renderFileSelect = this.renderFileSelect.bind(this);
        this.renderUploadedFileDeleteButton = this.renderUploadedFileDeleteButton.bind(this);
        this.fileUploadedCallback = this.fileUploadedCallback.bind(this)
        this.deleteFile = this.deleteFile.bind(this)
        this.deleteSuccessfully = this.deleteSuccessfully.bind(this)
        this.deleteFailed = this.deleteFailed.bind(this)
    }

    fileUploadedCallback(payload) {
        message.tip({title: '系统提示', message: '附件上传成功！',
            agreeCallback: function(self) {
                return () => {
                    const files = self.state.files
                    files.push(payload)

                    self.setState({
                        ...self.state,
                        files: files
                    })
                }
            }(this)
        })
    }

    deleteFile(file) {
        message.alert({title: '系统提示', message: `确认要删除文件(${file.nickName})吗？`,
            agreeCallback: function(self) {
                return () => {
                    post(self.state.deleteUri,
                        {id: file.id},
                        self.deleteSuccessfully(file),
                        self.deleteFailed)
                }
            }(this)
        })
    }

    deleteSuccessfully(file) {
        const self = this
        return function() {
            const agreeCallback = function() {
                return function() {
                    let index = -1
                    while ((++index) < self.state.files.length) {
                        if (self.state.files[index].id === file.id) break
                    }
                    if (index < self.state.files.length) {
                        self.state.files.splice(index, 1)
                    }

                    self.setState({
                        ...self.state,
                        files: self.state.files
                    })
                }
            }

            message.tip({
                title: '系统提示',
                message: "文件删除成功",
                agreeCallback: agreeCallback(self)
            })
        }
    }

    deleteFailed(error) {
        message.tip({
            title: error.title,
            message: error.detail,
        })
    }

    renderFileSelect() {
        if (this.state.readOnly) return null;
        return (
            <div className={this.classes.fileUploadFileSelectDefaultRoot}>
                <FileSelectDialog
                    classes={this.classes}
                    callback={this.fileUploadedCallback}
                    uploadUri={this.state.uploadUri}
                />
            </div>
        )
    }

    renderUploadedFileDeleteButton(file) {
        if (this.state.readOnly) return null;
        return (
            <IconButton className={this.classes.fileUploadedListItemDeleteDefault} onClick={()=>{this.deleteFile(file)}}>
                <IconStore fontSize={'small'} iconKey={iconNames.close}/>
            </IconButton>
        )
    }

    render() {
        return (
            <div className={this.classes.fileUploadDefaultRoot}>
                <div className={this.classes.fileUploadDefaultLabelContainer}>
                    <label className={this.classes.fileUploadDefaultLabel}>{this.state.label}</label>
                </div>
                {this.renderFileSelect()}
                <div className={this.classes.fileUploadedListDefaultRoot}>
                    {this.state.files.map(file => {
                        return (
                            <div key={uuid.v1()} className={this.classes.fileUploadedListItemDefault}>
                                <Link target='_blank' href={linkUrl(file.fileName)} className={this.classes.fileUploadedListItemFileNameDefault}>{file.nickName}</Link>
                                {this.renderUploadedFileDeleteButton(file)}
                            </div>
                        );
                    })}
                </div>
            </div>
        );
    }
}

FileUpload.propTypes = {
    classes: PropTypes.object.isRequired,
    label: PropTypes.string.isRequired,
    readOnly: PropTypes.bool.isRequired,
    files: PropTypes.array.isRequired,
    uploadUri: PropTypes.string.isRequired,
    deleteUri: PropTypes.string.isRequired,
}

export default FileUpload;