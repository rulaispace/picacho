import PropTypes from "prop-types";

export default function DefaultRightButtonGroup({state, classes, handlers}) {
    console.log(state)
    console.log(classes)
    console.log(handlers)

    return null
}

DefaultRightButtonGroup.propTypes = {
    state: PropTypes.object.isRequired,
    classes: PropTypes.object.isRequired,
    handlers: PropTypes.object.isRequired,
}