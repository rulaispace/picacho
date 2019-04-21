import React from 'react'
import Dialog from '@material-ui/core/Dialog'
import DialogTitle from '@material-ui/core/DialogTitle'
import DialogContent from '@material-ui/core/DialogContent'
import DialogActions from '@material-ui/core/DialogActions'
import Button from '@material-ui/core/Button'
import PropTypes from 'prop-types'
import handler from './login-dialog-handler'
import TextField from "@material-ui/core/TextField";
import commonNames from "../../common/config/common-name-config";
import {post} from "../../common/network/network";
import message from "./common-dialog-handler";

function Login({usernameRef, passwordRef, loginCallback, closeCallback}) {
    const eventHandler = function(callback) {
        return function(e) {
            e.preventDefault()
            callback()
        }
    }

    return (
        <div>
            <Dialog open onClose={eventHandler(closeCallback)} aria-labelledby='login-dialog-title'>
                <DialogTitle id='login-dialog-title'>登录系统</DialogTitle>
                <DialogContent>
                    <TextField autoFocus margin='dense' id='username' label='用户名' type='text' inputRef={usernameRef} fullWidth required/>
                    <TextField margin='dense' id='password' label='密码' inputRef={passwordRef} type='password' fullWidth required/>
                </DialogContent>
                <DialogActions>
                    <Button id='login-button' color='primary' onClick={eventHandler(loginCallback)}> 登录 </Button>
                    <Button id='close-button' color='primary' onClick={eventHandler(closeCallback)}> 取消 </Button>
                </DialogActions>
            </Dialog>
        </div>
    )
}

Login.propTypes = {
    usernameRef: PropTypes.object.isRequired,
    passwordRef: PropTypes.object.isRequired,
    loginCallback: PropTypes.func.isRequired,
    closeCallback: PropTypes.func.isRequired,
}

function ResetPassword({originRef, newRef, newConfirmRef, closeCallback, submitCallback}) {
    const eventHandler = function(callback) {
        return function(e) {
            e.preventDefault()
            callback()
        }
    }
    return (
        <div>
            <Dialog open onClose={eventHandler(closeCallback)} maria-labelledby='reset-password-dialog-title'>
                <DialogTitle id='reset-password-dialog-title'>修改初始密码</DialogTitle>
                <DialogContent>
                    <TextField autoFocus margin='dense' id='origin-password' label='原始密码' type='password' inputRef={originRef} fullWidth required/>
                    <TextField margin='dense' id='new-password' label='新密码' type='password' inputRef={newRef} fullWidth required/>
                    <TextField margin='dense' id='confirm-password' label='再次输入新密码' type='password' inputRef={newConfirmRef} fullWidth required/>
                </DialogContent>
                <DialogActions>
                    <Button id='commit-button' color='primary' onClick={eventHandler(submitCallback)}> 修改密码 </Button>
                    <Button id='commit-button' color='primary' onClick={eventHandler(closeCallback)}> 取消 </Button>
                </DialogActions>
            </Dialog>
        </div>
    )
}

ResetPassword.propTypes = {
    originRef: PropTypes.object.isRequired,
    newRef: PropTypes.object.isRequired,
    newConfirmRef: PropTypes.object.isRequired,
    submitCallback: PropTypes.func.isRequired,
    closeCallback: PropTypes.func.isRequired
}

class LoginDialog extends React.Component {
    constructor(props) {
        super(props)

        this.usernameRef = React.createRef()
        this.passwordRef = React.createRef()
        this.originRef = React.createRef()
        this.newRef = React.createRef()
        this.newConfirmRef = React.createRef()

        this.loginCallback = this.loginCallback.bind(this)
        this.closeCallback = this.closeCallback.bind(this)
        this.submitCallback = this.submitCallback.bind(this)
        this.loginSuccess = this.loginSuccess.bind(this)
        this.showResetDialog = this.showResetDialog.bind(this)
        this.storeAndReturn = this.storeAndReturn.bind(this)
        this.passwordChanged = this.passwordChanged.bind(this)
        this.submitFailed = this.submitFailed.bind(this)

        // mode： login rest password close
        this.state = {
            mode: commonNames.close,
            username: '',
            password: '',
            loginCallback: f=>f,
        }
        this.classes = props.classes

        handler.setRef(this)
    }

    loginCallback() {
        post('/login', {username: this.usernameRef.current.value, password: this.passwordRef.current.value}, this.loginSuccess, this.submitFailed)
    }

    closeCallback() {
        this.setState({...this.state, mode: commonNames.close})
    }

    loginSuccess(payload) {
        if (payload.shouldChangePassword) {
            this.showResetDialog(payload)
        } else {
            this.storeAndReturn(payload)
        }
    }

    showResetDialog(payload) {
        message.alert({title: '系统提示', message: '首次登陆系统需要修改密码，确认修改密码吗？',
            agreeCallback: function(self) {
                return () => {
                    self.setState({...self.state, ...payload, mode: commonNames.resetPassword})
                }
            }(this)
        })
    }

    submitCallback() {
        const origin = this.originRef.current.value
        const newPassword = this.newRef.current.value
        const confirmPassword = this.newConfirmRef.current.value

        if (newPassword !== confirmPassword) {
            message.tip({title: '系统提示', message: '两次输入的密码不一致，请重新设置'})
            return ;
        }

        post('/user/changePassword', {username: this.state.username, oldPassword: origin, newPassword: newPassword, token: this.state.token}, this.passwordChanged, this.submitFailed)
    }

    storeAndReturn(payload) {
        localStorage.setItem(commonNames.token, payload[commonNames.token])
        localStorage.setItem(commonNames.username, payload[commonNames.username])
        localStorage.setItem(commonNames.rule, payload[commonNames.rule])
        this.closeCallback()

        this.state.loginCallback()
    }

    passwordChanged() {
        message.alert({title: '系统提示', message: '密码修改成功，请重新登录！',
            agreeCallback: function(self) {
                return () => {
                    self.setState({...self.state, mode: commonNames.login, username: null, password: null})
                }
            }(this)
        })
    }

    submitFailed(error) {
        message.tip({title: error.title, message: error.detail})
    }

    render() {
        if (this.state.mode === commonNames.close) return null
        if (this.state.mode === commonNames.login) {
            return <Login usernameRef={this.usernameRef} passwordRef={this.passwordRef} loginCallback={this.loginCallback} closeCallback={this.closeCallback}/>
        }

        return <ResetPassword originRef={this.originRef} newRef={this.newRef} newConfirmRef={this.newConfirmRef}  submitCallback={this.submitCallback} closeCallback={this.closeCallback}/>
    }
}

LoginDialog.propTypes = {
    classes: PropTypes.object.isRequired,
}

export default LoginDialog