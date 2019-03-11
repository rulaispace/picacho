import React from 'react'
import PropTypes from 'prop-types'
import classNames from 'classnames'
import AppBar from '@material-ui/core/AppBar'
import Toolbar from '@material-ui/core/Toolbar'
import Typography from '@material-ui/core/Typography'
import IconButton from '@material-ui/core/IconButton'
import MenuIcon from '@material-ui/icons/Menu'
import LockIcon from '@material-ui/icons/Lock'
import PersonIcon from '@material-ui/icons/Person'
import reducer from './reducer';
import menuItems from "../../common/config/menu-items-config";
import account from '../../component/dialog/login-dialog-handler'
import commonNames from "../../common/config/common-name-config";

function RightIcons({token, logout, login}) {
    return token ? (<IconButton color='inherit' onClick={logout}><PersonIcon /></IconButton>)
        : (<IconButton color='inherit' onClick={login}><LockIcon /></IconButton>)
}

RightIcons.propTypes = {
    token: PropTypes.string,
    logout: PropTypes.func.isRequired,
    login: PropTypes.func.isRequired,
}

export default function TopBar({classes, store}) {
    let subTitle = localStorage[commonNames.token] ?
        ([...menuItems.employee.items, ...menuItems.administrator.items].reduce(
            (title, {id, label}) => {
                return title ? title : ((id===store.getState().layout.navigator) ? label : null)
            }, null
        ))
        : '首页'

    let title = `企业管理系统 / ${subTitle ? subTitle : '首页'}`

    return (
        <AppBar
            position='absolute'
            className={classNames(classes.appBar, store.getState().layout.open && classes.appBarShift)}
        >
            <Toolbar disableGutters={!store.getState().layout.open} className={classes.toolbar}>
                <IconButton
                    color='inherit'
                    aria-label='Open drawer'
                    onClick={() => {
                        store.dispatch(reducer.createAction(reducer.types.open))
                    }}
                    className={classNames(
                        classes.menuButton,
                        store.getState().layout.open && classes.menuButtonHidden,
                    )}
                >
                    <MenuIcon />
                </IconButton>
                <Typography
                    component='h1'
                    variant='h6'
                    color='inherit'
                    noWrap
                    className={classes.title}
                >
                    {title}
                </Typography>
                <RightIcons
                    token={localStorage[commonNames.token]}
                    login={(e) => {
                        e.preventDefault()
                        account.login(function(){
                            reducer.reloading()
                        })
                    }}
                    logout={(e) => {
                        e.preventDefault()
                        account.logout(function(){
                            reducer.reloading()
                        })
                    }}
                />
            </Toolbar>
        </AppBar>
    )
}

TopBar.propTypes = {
    classes: PropTypes.object.isRequired,
    store: PropTypes.object.isRequired,
}