import {mount} from "enzyme/build";
import React from "react";
import StoreFactory from "../../common/redux/store-factory";
import {withStyles} from "@material-ui/core";
import {styles} from "../app/styles";
import Content from './content'

describe('Test Announcement Content', () => {
    it('Setup with welcome', () => {
        const store = StoreFactory.create({
            overrideState: {
                layout: {
                    open: true,
                    navigator: 'welcome',
                },
            },
            enableLocalStorage: false,
        }).get()

        const ReactDOM = withStyles(styles)(Content)

        const component = mount(<ReactDOM store={store}/>)
        expect(component).toMatchSnapshot();
    })
    /*it('Setup with notification', () => {
        const store = StoreFactory.create({
            overrideState: {
                layout: {
                    open: true,
                    navigator: 'notification',
                },
            },
            enableLocalStorage: false,
        }).get()

        const ReactDOM = withStyles(styles)(Content)

        const component = mount(<ReactDOM store={store}/>)
        expect(component).toMatchSnapshot();
    })
    it('Setup with document', () => {
        const store = StoreFactory.create({
            overrideState: {
                layout: {
                    open: true,
                    navigator: 'document',
                },
            },
            enableLocalStorage: false,
        }).get()

        const ReactDOM = withStyles(styles)(Content)

        const component = mount(<ReactDOM store={store}/>)
        expect(component).toMatchSnapshot();
    })
    it('Setup with organization', () => {
        const store = StoreFactory.create({
            overrideState: {
                layout: {
                    open: true,
                    navigator: 'organization',
                },
            },
            enableLocalStorage: false,
        }).get()

        const ReactDOM = withStyles(styles)(Content)

        const component = mount(<ReactDOM store={store}/>)
        expect(component).toMatchSnapshot();
    })
    it('Setup with schedule', () => {
        const store = StoreFactory.create({
            overrideState: {
                layout: {
                    open: true,
                    navigator: 'schedule',
                },
            },
            enableLocalStorage: false,
        }).get()

        const ReactDOM = withStyles(styles)(Content)

        const component = mount(<ReactDOM store={store}/>)
        expect(component).toMatchSnapshot();
    })
    it('Setup with resource', () => {
        const store = StoreFactory.create({
            overrideState: {
                layout: {
                    open: true,
                    navigator: 'resource',
                },
            },
            enableLocalStorage: false,
        }).get()

        const ReactDOM = withStyles(styles)(Content)

        const component = mount(<ReactDOM store={store}/>)
        expect(component).toMatchSnapshot();
    })
    it('Setup with rule', () => {
        const store = StoreFactory.create({
            overrideState: {
                layout: {
                    open: true,
                    navigator: 'rule',
                },
            },
            enableLocalStorage: false,
        }).get()

        const ReactDOM = withStyles(styles)(Content)

        const component = mount(<ReactDOM store={store}/>)
        expect(component).toMatchSnapshot();
    })
    it('Setup with regulation', () => {
        const store = StoreFactory.create({
            overrideState: {
                layout: {
                    open: true,
                    navigator: 'regulation',
                },
            },
            enableLocalStorage: false,
        }).get()

        const ReactDOM = withStyles(styles)(Content)

        const component = mount(<ReactDOM store={store}/>)
        expect(component).toMatchSnapshot();
    })*/
})