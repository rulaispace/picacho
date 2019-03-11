import React from 'react'
import PropTypes from 'prop-types'
import List from '@material-ui/core/List'
import DefaultListItem from './default-list-item'
import DefaultCollapseList from './default-collapse-list'
import uuid from "uuid";

export default function DefaultListBranch({classes, state, handlers}) {
    return (
        <List
            dense
            className={classes[state.feature.rootClassName]}
            component={'nav'}
        >
            {state.data.reduce((components, item)=>{
                return [
                    ...components,
                    (<DefaultListItem
                        key={'node' + uuid.v1()}
                        state={{
                            ...state,
                            data: item,
                        }}
                        classes={classes}
                        handlers={handlers}
                    />),
                    (
                        item.expanded ? (
                            <DefaultCollapseList
                                key={'children' + uuid.v1()}
                                classes={classes}
                                state={{
                                    ...state,
                                    data: item.children
                                }}
                                handlers={handlers}
                            />
                        ): null
                    ),
                ]
            }, [])}
        </List>
    )
}

DefaultListBranch.propTypes = {
    classes: PropTypes.object.isRequired,
    state: PropTypes.object.isRequired,
    handlers: PropTypes.object.isRequired,
}