import React from 'react'
import commonNames from "../../../common/config/common-name-config";
import IconStore from "../../../common/base/icon-store";
import PropTypes from "prop-types";
import iconNames from "../../../common/config/icon-name-config";
import DefaultListItem from "../../../component/list/default-list-item";

export default function ListTextIcon({state, classes, handlers}) {
    let className = 'nestedListDefaultIcon'
    if (!DefaultListItem.shouldCreateLeftButton(handlers.expandable, state))
        className = 'nestedListDefaultIconWithoutExpand'

    if (state.data.type === commonNames.department)
        return <IconStore iconKey={iconNames.domain} className={classes[className]}/>

    return <IconStore iconKey={iconNames.person} className={classes[className]}/>
}

ListTextIcon.propTypes = {
    state: PropTypes.object.isRequired,
    classes: PropTypes.object.isRequired,
    handlers: PropTypes.object.isRequired,
}