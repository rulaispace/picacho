import StoreFactory from "../../../common/redux/store-factory";
import {mount} from "enzyme/build";
import React from "react";
import Content from "./content";
import {withStyles} from "@material-ui/core";
import {styles} from "../../app/styles";

describe('Test Announcement Content', () => {
    const initStore = () => StoreFactory.create({
        overrideState: {
            announcement: {
                query: '放假',
                dataList: [
                    {
                        key: 'ANC-001',
                        name: '春节放假通知',
                        type: '变更',
                        releaseDate: '2019-01-01',
                        state: '在途',
                        operator: ['修改', '发布', '删除'],
                    },
                    {
                        key: 'ANC-002',
                        name: '内部管理会会议纪要',
                        type: '通知',
                        releaseDate: '2019-01-01',
                        state: '撤销',
                        operator: ['修改', '发布', '删除'],
                    },
                ]
            },
        },
        enableLocalStorage: false,
    }).get()
    it('The structure of this content', () => {
        const store = initStore()

        const ReactDOM = withStyles(styles)(Content)
        const component = mount(<ReactDOM store={store}/>)
        expect(component).toMatchSnapshot()
    })
})