import React from 'react'
import PropTypes from 'prop-types'
import Welcome from './welcome/content'
import Notification from './notification/content'
import Document from './document/content'
import Organization from './organization/content'
import Schedule from './schedule/content'
import Resource from './resource/content'
import Rule from './rule/content'
import Announcement from './announcement/content'
import Regulation from './regulation/content'
import commonNames from "../../common/config/common-name-config";

export default function Content({classes, store}) {
    const {
        layout: {
            navigator = 'welcome'
        },
    } = store.getState()

    const title = localStorage.getItem(commonNames.token) ? navigator : 'welcome'

    switch (title) {
        case 'welcome':
            return <Welcome classes={classes} store={store} />
        case 'notification':
            return <Notification classes={classes} store={store} />
        case 'document':
            return <Document classes={classes} store={store} />
        case 'organization':
            return <Organization classes={classes} store={store} />
        case 'schedule':
            return <Schedule classes={classes} store={store} />
        case 'resource':
            return <Resource classes={classes} store={store} />
        case 'rule':
            return <Rule classes={classes} store={store} />
        case 'announcement':
            return <Announcement classes={classes} store={store}/>
        case 'regulation':
            return <Regulation classes={classes} store={store}/>
        default:
            return <Welcome classes={classes} store={store} />
    }
}

Content.propTypes = {
    classes: PropTypes.object.isRequired,
    store: PropTypes.object.isRequired
}