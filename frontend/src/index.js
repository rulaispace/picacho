import React from "react"
import {render} from "react-dom"
import StoreFactory from "./common/redux/store-factory"
import Dashboard from './biz/app/dashboard'

const store = StoreFactory.create({enableLocalStorage: false}).get()

const refresh = () => {
    return render(
        <Dashboard store={store}/>,
        document.getElementById('root')
    )
}

store.subscribe(refresh)
refresh()