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
let XLDownload = NativeModules.XLDownloadNative;

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
        this.DownloadBeanEmit = DeviceEventEmitter.addListener('DownloadBean', event => {
            if (this.state.data.sizes == 0) {
                this.setState({data: event})
            } else {
                const newArr = []
                Object.keys(this.state.data).map((key, index) => {
                    if (this.state.data[key].DownloadPath == event.DownloadPath) {
                        newArr.push(event)
                    } else {
                        newArr.push(this.state.data[key]);
                    }
                });
                this.setState({data: newArr})
            }
            if (event.IsTorrent == 0) {
                XLDownload.ed2kDownload(JSON.stringify(event))
            } else {

            }
        });
    }

    componentWillUnmount() {
        this.DownloadBeanEmit.remove()
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