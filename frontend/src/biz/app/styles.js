import {lighten} from "@material-ui/core/styles/colorManipulator"
import { fade } from '@material-ui/core/styles/colorManipulator'
import yellow from '@material-ui/core/colors/yellow'
import blue from '@material-ui/core/colors/blue'

export function styles(theme) {
    const drawerWidth = 240;

    return ({
        toolbar: {
            paddingRight: 24, // keep right padding when drawer closed
        },
        toolbarIcon: {
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'flex-end',
            padding: '0 8px',
            ...theme.mixins.toolbar,
        },
        appBar: {
            zIndex: theme.zIndex.drawer + 1,
            transition: theme.transitions.create(['width', 'margin'], {
                easing: theme.transitions.easing.sharp,
                duration: theme.transitions.duration.leavingScreen,
            }),
        },
        appBarShift: {
            marginLeft: drawerWidth,
            width: `calc(100% - ${drawerWidth}px)`,
            transition: theme.transitions.create(['width', 'margin'], {
                easing: theme.transitions.easing.sharp,
                duration: theme.transitions.duration.enteringScreen,
            }),
        },
        menuButton: {
            marginLeft: 12,
            marginRight: 36,
        },
        menuButtonHidden: {
            display: 'none',
        },
        title: {
            flexGrow: 1,
            textAlign: 'center',
        },
        drawerPaper: {
            position: 'relative',
            whiteSpace: 'nowrap',
            width: drawerWidth,
            transition: theme.transitions.create('width', {
                easing: theme.transitions.easing.sharp,
                duration: theme.transitions.duration.enteringScreen,
            }),
        },
        drawerPaperClose: {
            overflowX: 'hidden',
            transition: theme.transitions.create('width', {
                easing: theme.transitions.easing.sharp,
                duration: theme.transitions.duration.leavingScreen,
            }),
            width: theme.spacing.unit * 7,
            [theme.breakpoints.up('sm')]: {
                width: theme.spacing.unit * 9,
            },
        },
        chartContainer: {
            marginLeft: -22,
        },
        tableContainer: {
            height: 320,
        },
        h5: {
            marginBottom: theme.spacing.unit * 2,
        },
        paper: {
            margin: 'auto',
            overflow: 'hidden',
        },
        searchBar: {
            borderBottom: '1px solid rgba(0, 0, 0, 0.08)',
            width: '100%',
        },
        searchInput: {
            fontSize: theme.typography.fontSize,
        },
        block: {
            display: 'block',
        },
        addUser: {
            marginRight: theme.spacing.unit,
        },
        contentWrapper: {
            margin: '8px 8px',
        },

        // content default styles
        contentDefaultRoot: {
            flexGrow: 1,
            padding: theme.spacing.unit * 3,
            height: '100vh',
            overflow: 'auto',
        },
        contentDefaultHead: {
            display: 'flex',
        },
        contentDefaultAppbarSpacer: theme.mixins.toolbar,
        contentDefaultAppbar: {
            borderBottom: '1px solid rgba(0, 0, 0, 0.08)',
            width: '100%',
        },
        contentDefaultBody: {
            margin: '8px 8px',
        },
        contentDefaultMinIconButton: {
            minWidth: theme.spacing.unit * 4,
        },

        // toolbar
        toolbarDefaultRoot: {
            display: 'flex',
        },
        toolbarDefaultLeftButton: {
            marginLeft: theme.spacing.unit * 1,
            marginRight: theme.spacing.unit * 1,
        },
        toolbarDefaultTitle: {
            display: 'none',
            [theme.breakpoints.up('sm')]: {
                display: 'block',
            },
        },
        toolbarDefaultInput: {
            position: 'relative',
            borderRadius: theme.shape.borderRadius,
            backgroundColor: fade(theme.palette.common.white, 0.15),
            '&:hover': {
                backgroundColor: fade(theme.palette.common.white, 0.25),
            },
            marginRight: theme.spacing.unit * 2,
            marginLeft: 0,
            width: '100%',
            [theme.breakpoints.up('sm')]: {
                marginLeft: theme.spacing.unit * 3,
                width: 'auto',
            },
        },
        toolbarDefaultInputIcon: {
            width: theme.spacing.unit * 5,
            height: '100%',
            position: 'absolute',
            pointerEvents: 'none',
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',
        },
        toolbarDefaultInputRoot: {
            color: 'inherit',
            width: '100%',
        },
        toolbarDefaultInputInput: {
            paddingTop: theme.spacing.unit,
            paddingRight: theme.spacing.unit,
            paddingBottom: theme.spacing.unit,
            paddingLeft: theme.spacing.unit * 6,
            transition: theme.transitions.create('width'),
            fontSize: '13px',
            width: '100%',
            [theme.breakpoints.up('md')]: {
                width: 300,
            },
        },
        toolbarDefaultGrow: {
            flex: 1,
            display: 'block',
        },
        toolbarDefaultRightButtonGroup: {
            display: 'none',
            [theme.breakpoints.up('md')]: {
                display: 'flex',
            },
        },

        // nested list
        nestedListDefaultList: {
            width: '100%',
            backgroundColor: theme.palette.background.default,
        },
        nestedListDefaultItemLevel0: {
            padding:0,
            borderBottom: '1px solid rgba(0, 0, 0, 0.08)',
            width: '100%',
        },
        nestedListDefaultItemLevel1: {
            paddingTop: 0,
            paddingRight: 0,
            paddingBottom: 0,
            paddingLeft: theme.spacing.unit * 2,
            borderBottom: '1px solid rgba(0, 0, 0, 0.08)',
            width: '100%',
        },
        nestedListDefaultItemLevel2: {
            paddingTop: 0,
            paddingRight: 0,
            paddingBottom: 0,
            paddingLeft: theme.spacing.unit * 4,
            borderBottom: '1px solid rgba(0, 0, 0, 0.08)',
            width: '100%',
        },
        nestedListDefaultItemLevel3: {
            paddingTop: 0,
            paddingRight: 0,
            paddingBottom: 0,
            paddingLeft: theme.spacing.unit * 6,
            borderBottom: '1px solid rgba(0, 0, 0, 0.08)',
            width: '100%',
        },
        nestedListDefaultItemLevel4: {
            paddingTop: 0,
            paddingRight: 0,
            paddingBottom: 0,
            paddingLeft: theme.spacing.unit * 8,
            borderBottom: '1px solid rgba(0, 0, 0, 0.08)',
            width: '100%',
        },
        nestedListDefaultItemLevel5: {
            paddingTop: 0,
            paddingRight: 0,
            paddingBottom: 0,
            paddingLeft: theme.spacing.unit * 10,
            borderBottom: '1px solid rgba(0, 0, 0, 0.08)',
            width: '100%',
        },
        nestedListDefaultItemLevel6: {
            paddingTop: 0,
            paddingRight: 0,
            paddingBottom: 0,
            paddingLeft: theme.spacing.unit * 12,
            borderBottom: '1px solid rgba(0, 0, 0, 0.08)',
            width: '100%',
        },
        nestedListDefaultText: {
            padding:0,
            width: '100%',
        },
        nestedListDefaultIcon: {
            paddingTop: 0,
            paddingRight: theme.spacing.unit,
            paddingBottom: 0,
            paddingLeft: 0,
        },
        nestedListDefaultIconWithoutExpand: {
            paddingTop: 0,
            paddingRight: theme.spacing.unit,
            paddingBottom: 0,
            paddingLeft: 0,
            marginLeft: theme.spacing.unit * 6,
        },
        // default table
        tableDefaultRoot: {
            width: '100%',
            marginTop: theme.spacing.unit * 1,
        },
        tableDefaultContent: {
            overflowX: 'auto',
        },
        tableDefault: {
            minWidth: 600,
        },
        tableCellDefaultLinkItem: {
            paddingRight: theme.spacing.unit,
        },
        tableCellDefault: {
            // fontSize: '16px',
        },

        tableToolBarRoot: {
            paddingRight: theme.spacing.unit,
        },
        tableToolBarHighlight:
            theme.palette.type === 'light'
                ? {
                    color: theme.palette.secondary.main,
                    backgroundColor: lighten(theme.palette.secondary.light, 0.85),
                }
                : {
                    color: theme.palette.text.primary,
                    backgroundColor: theme.palette.secondary.dark,
                },
        tableToolBarSpacer: {
            flex: '1 1 100%',
        },
        tableToolBarActions: {
            color: theme.palette.text.secondary,
        },
        tableToolBarTitle: {
            flex: '0 0 auto',
        },

        // default form
        formDefaultContainer: {
            display: 'flex',
            flexWrap: 'wrap',
        },
        formDefaultTextField: {
            paddingLeft: theme.spacing.unit*1,
            paddingRight: theme.spacing.unit*1,
            width: '100%',
        },
        formDefaultInputLabel: {
            marginLeft: theme.spacing.unit*1,
        },
        formDefaultTextField2: {
            paddingLeft: theme.spacing.unit*1,
            paddingRight: theme.spacing.unit*1,
            width: '50%',
        },
        formDefaultTextField3: {
            paddingLeft: theme.spacing.unit*1,
            paddingRight: theme.spacing.unit*1,
            width: '33.3%',
        },
        formDefaultDense: {
            marginTop: 19,
        },
        formDefaultSelectMenu: {
            width: 200,
        },

        // specific configuration for schedule
        scheduleBackgroundLightYellow: {
            // fontSize: '16px',
            backgroundColor: yellow[100],
        },
        scheduleBackgroundDarkYellow: {
            // fontSize: '16px',
            backgroundColor: yellow[500],
        },
        scheduleBackgroundBlue: {
            // fontSize: '16px',
            backgroundColor: blue[500],
        },

        // default common dialog
        commonDialogDefaultRoot: {
            minWidth: 320,
        },

        // file upload
        fileUploadDefaultLabelContainer: {
            marginTop: '16px',
            marginBottom: '8px',
        },
        fileUploadDefaultLabel: {
            color: 'rgba(0, 0, 0, 0.54)',
            fontFamily: '"Roboto", "Helvetica", "Arial", sans-serif',
            fontSize: 'small',
        },
        fileUploadDefaultRoot: {
            display: 'flex',
            flexDirection: 'column',
            flex: 1,
            width: '100%',
            margin: theme.spacing.unit * 1,
        },
        fileUploadFileSelectDefaultRoot: {
            border: '1px solid #ccc',
            borderBottom: '0px',
            boxSizing: 'border-box',
            display: 'flex',
            width: '100%',
        },
        fileUploadFileSelectDefaultContent: {
            backgroundColor: '#fff',
            padding: '2px 4px',
            display: 'flex',
            alignItems: 'center',
            width: '100%',
        },
        fileUploadedListDefaultRoot: {
            border: '1px solid #ccc',
            boxSizing: 'border-box',
            display: 'flex',
            flexDirection: 'row',
            flex: 1,
            flexWrap: 'wrap',
            width: '100%',
        },
        fileUploadedListItemDefault: {
            display: 'flex',
            margin: theme.spacing.unit * 1,
        },
        fileUploadedListItemFileNameDefault: {
            display: 'flex',
            margin: theme.spacing.unit * 1,
        },
        fileUploadedListItemDeleteDefault: {
            display: 'flex',
            paddingTop: 0,
            paddingBottom: 0,
            paddingLeft: theme.spacing.unit * 1,
            paddingRight: theme.spacing.unit * 1,
        },
        fileSelectInputDefault: {
            display: 'none',
        },
        fileSelectOpenDialogButtonDefault: {
            padding: 10,
        },
        fileSelectVisibleInputDefault: {
            minWidth: '280px',
            marginLeft: 8,
            flex: 1,
        },
        fileSelectUploadButtonDefault: {
            padding: 10,
        },
        fileSelectRightButtonDivider: {
            width: 1,
            height: 28,
            margin: 4,
        }
    })
}