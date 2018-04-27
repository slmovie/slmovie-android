/**
 * Created by 包俊 on 2017/8/7.
 */
import React from 'react';
import {TabNavigator, NavigationActions} from 'react-navigation'
import InfoViews from "./page/InfoViews";
import DescribeViews from "./page/DescribeViews";
import UrlViews from "./page/UrlViews";
import {TabBackColor, TabSelectedTextColor, TabTextColor} from "./res/color";

let data;
export default class TabView extends React.Component {

    constructor(props) {
        super(props)
        data = this.props.movies;
    }

    render() {
        return (
            <Tabs movies={this.props.movies}/>
        )
    }
}


// 注册tabs
const Tabs = TabNavigator({
    InfoViews: {
        screen: () => {
            return (<InfoViews movies={data}/>)
        },
        navigationOptions: {  // 也可以写在组件的static navigationOptions内
            tabBarLabel: '详情'
        }
    },
    DescribeViews: {
        screen: () => {
            return (<DescribeViews movies={data}/>)
        },
        navigationOptions: {
            tabBarLabel: '简介',
        }
    },
    UrlViews: {
        screen: () => {
            return (<UrlViews movies={data}/>)
        },
        navigationOptions: {
            tabBarLabel: '资源'
        }
    }
}, {
    animationEnabled: true, // 切换页面时是否有动画效果
    tabBarPosition: 'top', // 显示在底端，android 默认是显示在页面顶端的
    swipeEnabled: true, // 是否可以左右滑动切换tab
    lazy: true,
    backBehavior: 'none', // 按 back 键是否跳转到第一个Tab(首页)， none 为不跳转
    tabBarOptions: {
        activeTintColor: TabSelectedTextColor, // 文字和图片选中颜色
        inactiveTintColor: TabTextColor, // 文字和图片未选中颜色
        indicatorStyle: {
            height: 2, // 如TabBar下面显示有一条线，可以设高度为0后隐藏
            backgroundColor: "#ffffff"
        },
        style: {
            backgroundColor: TabBackColor, // TabBar 背景色
            // height: 44
        },
        labelStyle: {
            fontSize: 13, // 文字大小
        },
    },
});
