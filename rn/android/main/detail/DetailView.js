/**
 * Created by 包俊 on 2017/8/7.
 */
import React from 'react';
import TabView, {Tabs} from "./tab/TabViews"


export default class DetailView extends React.Component {

    render() {
        return (
            <TabView movies={this.props.movies}/>
        )
    }
}