import React from 'react'
import StoreFactory from "../../common/redux/store-factory";
import {mount} from "enzyme";
import {withStyles} from "@material-ui/core";
import {styles} from ".//styles";
import Dashboard from './dashboard'

describe('Test LoginDialog', () => {
    const initStore = () => StoreFactory.create({
        enableLocalStorage: false,
    }).get()


    it('The structure of this component is stable.', () => {
        const store = initStore()
        const ReactDOM = withStyles(styles)(Dashboard)

        const component = mount(<ReactDOM store={store}/>)
        expect(component).toMatchSnapshot();
    })
})
