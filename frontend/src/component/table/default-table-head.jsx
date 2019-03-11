import React from 'react'
import PropTypes from 'prop-types'
import TableHead from '@material-ui/core/TableHead'
import TableCell from '@material-ui/core/TableCell'
import TableRow from "@material-ui/core/TableRow";

export default class DefaultTableHead extends React.Component {
    constructor(props) {
        super(props)

        this.state = props.state
        this.classes = props.classes
        this.handlers = props.handlers
    }

    render() {
        const {header} = this.state
        return (
            <TableHead>
                <TableRow >
                    {header.map(
                        ({id, label, width, numeric, disablePadding})=> (
                            <TableCell
                                className={this.classes.tableCellDefault}
                                align={numeric ? 'center' : 'left'}
                                key={id}
                                padding={disablePadding ? 'none' : 'default'}
                                width={width}
                            >
                                {label}
                            </TableCell>
                        )
                    )}
                </TableRow>
            </TableHead>
        )
    }
}

DefaultTableHead.propTypes = {
    state: PropTypes.object.isRequired,
    classes: PropTypes.object.isRequired,
    handlers: PropTypes.object.isRequired,
}