import React from 'react'
import PropTypes from 'prop-types'
import IconButton from '@material-ui/core/IconButton'
import ListItem from "@material-ui/core/ListItem";
import ListItemText from "@material-ui/core/ListItemText";
import IconStore from "../../common/base/icon-store";

export default function DefaultListItem({state, classes, handlers}) {
    return (
        <ListItem
            key={state.data.id}
            className={classes[`nestedListDefaultItemLevel${state.data.level}`]}
        >
            {/** 根据当前状态，选择列表前面按钮的图标以及回调函数 **/}
            {
                DefaultListItem.shouldCreateLeftButton(handlers.expandable, state) ? (state.data.expanded ? (
                    <IconButton onClick={(e) => {
                        e.preventDefault()
                        handlers.collapse(state.data)
                    }}>
                        <IconStore iconKey={state.feature.expandedIconKey} />
                    </IconButton>
                ) : (
                    <IconButton onClick={(e) => {
                        e.preventDefault()
                        handlers.expand(state.data)
                    }}>
                        <IconStore iconKey={state.feature.collapsedIconKey} />
                    </IconButton>
                )) : null

            }
            {/** 创建文本前的图标 **/}
            <handlers.TextIconFactory state={state} classes={classes} handlers={handlers}/>
            {/** 创建文本 **/}
            <ListItemText
                className={classes[state.feature.textClassName]}
                primary= {state.data.primaryText}
                secondary={state.data.secondaryText}
            />
            {/** 创建右侧按钮区 **/}
            <handlers.RightButtonGroupFactory state={state.data} classes={classes} handlers={handlers.rightButtonGroup}/>
        </ListItem>
    )
}

DefaultListItem.propTypes = {
    state: PropTypes.object.isRequired,
    classes: PropTypes.object.isRequired,
    handlers: PropTypes.object.isRequired,
}

DefaultListItem.shouldCreateLeftButton = function(expandable, state) {
    if (typeof expandable === 'function')
        return expandable(state)

    return expandable
}