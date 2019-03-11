import React from 'react'
import PropTypes from 'prop-types'
import Typography from '@material-ui/core/Typography'

export default function Content({classes}) {
    return (
        <main className={classes.contentDefaultRoot}>
            <div className={classes.contentDefaultAppbarSpacer} />
            <Typography variant='h4' gutterBottom component='h2' align={'center'}>
                欢迎使用企业管理系统！
            </Typography>
        </main>
    )
}

Content.propTypes = {
    classes: PropTypes.object.isRequired
}