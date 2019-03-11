import React from 'react'
import PropTypes from 'prop-types'
import List from "@material-ui/core/List";
import Collapse from "@material-ui/core/Collapse";
import DefaultListItem from "./default-list-item";
import uuid from "uuid";
import DefaultListBranch from "./default-list-branch";

export default function DefaultCollapseList({state, classes, handlers}) {
    if (state.data) {
        return (
            <Collapse in={true}>
                <List
                    component={'nav'}
                    dense={true}
                    className={classes[state.feature.listClassName]}
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
                                    <DefaultListBranch
                                        key={'children' + uuid.v1()}
                                        classes={classes}
                                        state={{
                                            ...state,
                                            data: item.children,
                                        }}
                                        handlers={handlers}
                                    />
                                ): null
                            ),
                        ]
                    }, [])}
                    {/*{state.data.map((item) => {
                        return (
                            <DefaultListItem
                                key={item.id}
                                state={{
                                    ...state,
                                    data: item,
                                }}
                                classes={classes}
                                handlers={handlers}
                            />
                        )
                    })}*/}
                </List>
            </Collapse>
        )
    }
    return null
}

DefaultCollapseList.propTypes = {
    state: PropTypes.object.isRequired,
    classes: PropTypes.object.isRequired,
    handlers: PropTypes.object.isRequired,
}