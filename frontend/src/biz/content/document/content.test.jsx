import {mount, shallow} from "enzyme/build";
import React from "react";
import {withStyles} from "@material-ui/core";
import Content from './content'
import StoreFactory from "../../../common/redux/store-factory";
import {styles} from '../../app/styles'

describe('Test Announcement Content', () => {
    const store = StoreFactory.create({
        overrideState: {
            layout: {
                open: true,
                navigator: 'document',
            },
        },
        enableLocalStorage: false,
    }).get()

    it('The structure of this content', () => {
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

        const component = shallow(<ReactDOM store={store}/>)
        expect(component).toMatchSnapshot();
    })

    it('Run react component constructor function', () => {
        const ReactDOM = withStyles(styles)(Content)

        const component = mount(<ReactDOM store={store}/>)
        console.log(component)
    })
})