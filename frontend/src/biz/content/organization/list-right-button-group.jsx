import PropTypes from "prop-types";
import iconNames from "../../../common/config/icon-name-config";
import React from "react";
import IconButton from "@material-ui/core/IconButton";
import IconStore from "../../../common/base/icon-store";
import ListItemSecondaryAction from "@material-ui/core/ListItemSecondaryAction";
import commonNames from "../../../common/config/common-name-config";
import Tooltip from "@material-ui/core/Tooltip";

export default function ListRightButtonGroup({state, classes, handlers}) {
    if (state.type === commonNames.department) {
        return (
            <ListItemSecondaryAction>
                <Tooltip title="添加下属组织">
                    <IconButton onClick={(e) => {
                        e.preventDefault()
                        handlers[iconNames.domainSharp].onClick(state)
                    }}>
                        <IconStore iconKey={iconNames.domainSharp} fontSize={'small'}/>
                    </IconButton>
                </Tooltip>
                <Tooltip title="添加成员">
                    <IconButton onClick={(e) => {
                        e.preventDefault()
                        handlers[iconNames.personAdd].onClick(state)
                    }}>
                        <IconStore iconKey={iconNames.personAdd} fontSize={'small'}/>
                    </IconButton>
                </Tooltip>
                <Tooltip title="编辑">
                    <IconButton onClick={(e) => {
                        e.preventDefault()
                        handlers[iconNames.edit].onClick(state)
                    }}>
                        <IconStore iconKey={iconNames.edit} fontSize={'small'}/>
                    </IconButton>
                </Tooltip>
                <Tooltip title="删除">
                    <IconButton onClick={(e) => {
                        e.preventDefault()
                        handlers[iconNames.delete].onClick(state)
                    }}>
                        <IconStore iconKey={iconNames.delete} fontSize={'small'}/>
                    </IconButton>
                </Tooltip>
            </ListItemSecondaryAction>
        )
    }

    return (
        <ListItemSecondaryAction>
            {state.state === commonNames.valid ? (
                <Tooltip title="注销用户">
                    <IconButton onClick={(e) => {
                        e.preventDefault()
                        handlers[iconNames.close].onClick(state)
                    }}>
                        <IconStore iconKey={iconNames.close} fontSize={'small'}/>
                    </IconButton>
                </Tooltip>
            ) : null}
            {state.state !== commonNames.valid ? (
                <Tooltip title="激活普通用户权限">
                    <IconButton onClick={(e) => {
                        e.preventDefault()
                        handlers[iconNames.check].onClick(state)
                    }}>
                        <IconStore iconKey={iconNames.check} fontSize={'small'}/>
                    </IconButton>
                </Tooltip>
            ) : null}
            {state.state !== commonNames.valid ? (
                <Tooltip title="激活管理员权限">
                    <IconButton onClick={(e) => {
                        e.preventDefault()
                        handlers[iconNames.add].onClick(state)
                    }}>
                        <IconStore iconKey={iconNames.add} fontSize={'small'}/>
                    </IconButton>
                </Tooltip>
            ) : null}
            <Tooltip title="编辑">
                <IconButton onClick={(e) => {
                    e.preventDefault()
                    handlers[iconNames.edit].onClick(state)
                }}>
                    <IconStore iconKey={iconNames.edit} fontSize={'small'}/>
                </IconButton>
            </Tooltip>
            <Tooltip title="删除">
                <IconButton onClick={(e) => {
                    e.preventDefault()
                    handlers[iconNames.delete].onClick(state)
                }}>
                    <IconStore iconKey={iconNames.delete} fontSize={'small'}/>
                </IconButton>
            </Tooltip>
        </ListItemSecondaryAction>
    )
}

ListRightButtonGroup.propTypes = {
    state: PropTypes.object.isRequired,
    classes: PropTypes.object.isRequired,
    handlers: PropTypes.object.isRequired,
}