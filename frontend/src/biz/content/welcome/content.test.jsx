import {mount} from "enzyme/build";
import React from "react";
import {withStyles} from "@material-ui/core";
import Content from './content'
import StoreFactory from "../../../common/redux/store-factory";
import {styles} from '../../app/styles'

describe('Test Announcement Content', () => {

    it('The structure of this content', () => {
        const welcomeStore = StoreFactory.create({
            overrideState: {
                layout: {
                    open: true,
                    navigator: 'welcome',
                },
            },
            enableLocalStorage: false,
        }).get()

        const ReactDOM = withStyles(styles)(Content)

        const component = mount(<ReactDOM store={welcomeStore}/>)
        expect(component).toMatchSnapshot();
    })
})