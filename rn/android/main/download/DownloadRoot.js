/**
 * Created by 包俊 on 2018/6/8.
 */
'use strict';

import React from 'react';
import {
    StyleSheet,
    Text,
    View,
    NativeModules,
    FlatList,
    Alert,
    DeviceEventEmitter
} from 'react-native';

import {check} from "../../utils/CheckVersion.js"
import FlatItem from "./DownloadUIItem.js"

let DownloadUI = NativeModules.XLDownloadUINative;
let ProgressDialogNative = NativeModules.ProgressDialogNative;
let LoadUtilNative = NativeModules.LoadUtilNative;


export default class DownloadRoot extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            data: []
        }
    }

    componentDidMount() {
        ProgressDialogNative.show('加载中')
        check()
        this._findAllInfo()
        DeviceEventEmitter.addListener('DownloadBean', event => {
            const newArr = []
            Object.keys(this.state.data).map((key, index) => {
                newArr.push(this.state.data[key]);
            });
            newArr.push(event)
            console.log(newArr)
            this.setState({data: newArr})
            // console.log(event)
            // console.log(this.state.data)
            // let temp = this.state.data
            // temp = temp.push(event)
            // console.log(temp)
            // this.setState({data: temp})
        });
    }

    render() {
        return (
            <FlatList
                data={this.state.data}
                extraData={this.state}
                keyExtractor={(item, index) => index}
                renderItem={({item}) => <FlatItem data={item}/>}
            />
        )
    }

    //获取所有下载信息
    _findAllInfo() {
        // DownloadUI.test().then(result => {
        //     this.setState(this.state.data = result)
        // }).catch(error => {
        // }).finally(() => {
        //     ProgressDialogNative.dismiss()
        //     LoadUtilNative.loadFinish()
        // })
        // DownloadUI.test1().then(NativeMap => {
        //     this.setState(this.state.test = NativeMap)
        //     console.log(NativeMap)
        // }).catch(error => {
        //
        // })
        DownloadUI.findAllInfo().then(result => {
            this.setState(this.state.data = result)
        }).catch(error => {
        }).finally(() => {
            ProgressDialogNative.dismiss()
            LoadUtilNative.loadFinish()
        })
    }
}

let styles = StyleSheet.create({
    text: {
        color: "#ffffff",
        fontSize: 14,
    }
});