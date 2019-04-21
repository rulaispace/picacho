import React from 'react'
import PropTypes from 'prop-types'
import classNames from 'classnames'
import reducer from './reducer'
import IconStore from "../../common/base/icon-store"

import Drawer from '@material-ui/core/Drawer'
import Divider from '@material-ui/core/Divider'
import IconButton from '@material-ui/core/IconButton'
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft'
import ListItem from '@material-ui/core/ListItem'
import ListItemIcon from '@material-ui/core/ListItemIcon'
import ListItemText from '@material-ui/core/ListItemText'
import ListSubheader from '@material-ui/core/ListSubheader'
import mainMenu from "../../common/config/main-menu-config";
import {post} from '../../common/network/network'
import StoreFactory from "../../common/redux/store-factory";
import commonNames from "../../common/config/common-name-config";

function DefaultMenuList({onClick}) {
    const {header, items} = mainMenu
    return (
        <div>
            {header ? (<ListSubheader inset>{header}</ListSubheader>) : null}
            {
                items.filter(
                    ({rule}) => {
                        if (commonNames.admin === localStorage.getItem(commonNames.rule))
                            return true;
                        if (localStorage.getItem(commonNames.rule) == null)
                            return false;

                        return rule === commonNames.employee;
                    }
                ).map(({id, label, loadingUrl}) => (
                    <ListItem
                        button
                        key={id}
                        onClick={(e) => {
                            e.preventDefault()
                            onClick(id, loadingUrl)
                        }}
                    >
                        <ListItemIcon>
                            <IconStore iconKey={id}/>
                        </ListItemIcon>
                        <ListItemText  primary={label}/>
                    </ListItem>
                ))

            }
        </div>
    )
}

DefaultMenuList.propTypes = {
    state: PropTypes.object.isRequired,
    onClick: PropTypes.func.isRequired,
}

const loading = function(store) {
    return (name, loadingUrl) => {
        store.getState().layout.navigator = null;
        post(loadingUrl, {}, loadSuccess(store, name), loadFail(store))
    }
}

const loadSuccess = function(store, name) {
    return function(payload) {
        const childReducer = StoreFactory.reducer(name)
        store.dispatch(childReducer.createAction(childReducer.types.loading, payload))
        store.dispatch(reducer.createAction(reducer.types.navigate, {name}))
    }
}

const loadFail = function(store) {
    return function(err) {
        store.tip({
            title: err.title,
            message: err.detail
        })
    }
}

function DefaultMainMenu({classes, store}) {
    return (
        <Drawer
            variant='permanent'
            classes={{
                paper: classNames(classes.drawerPaper, !store.getState().layout.open && classes.drawerPaperClose),
            }}
            open={store.getState().layout.open}
        >
            <div className={classes.toolbarIcon}>
                <IconButton
                    onClick={() => {
                        store.dispatch(reducer.createAction(reducer.types.agree))
                    }}
                >
                    <ChevronLeftIcon />
                </IconButton>
            </div>
            <Divider />
            <DefaultMenuList onClick={loading(store)}/>
        </Drawer>
    )
}

DefaultMainMenu.propTypes = {
    classes: PropTypes.object.isRequired,
    store: PropTypes.object.isRequired,
}

DefaultMainMenu.reloading = function(store, name, param={}) {
    store.getState().layout.navigator = null;
    let loadingUrl = null;
    for (const property in mainMenu.items) {
        if (mainMenu.items[property].id === name) {
            loadingUrl = mainMenu.items[property].loadingUrl
            break
        }
    }

    post(loadingUrl, param, loadSuccess(store, name), loadFail(store))
}

export default DefaultMainMenu