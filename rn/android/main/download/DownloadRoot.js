/**
 * Created by 包俊 on 2018/6/8.
 */
'use strict';

import React from 'react';
import {
    StyleSheet,
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
let DeleteDialog = NativeModules.DeleteDialogNative
let FileNative = NativeModules.FileNative

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
            // console.log(event)
            if (this.state.data.length == 0) {
                event.onlyOne = true
                var arr = this.state.data.concat(event)
                this.setState({data: arr})
            } else {
                var newArr = []
                var update = false
                Object.keys(this.state.data).map((key, index) => {
                    if (this.state.data[key].DownloadPath == event.DownloadPath) {
                        update = true
                        var bean = this.state.data[key]
                        bean.addNew = true
                        newArr = newArr.concat(bean)
                    } else {
                        newArr = newArr.concat(this.state.data[key]);
                    }
                });
                if (!update) {
                    event.addNew = true
                    newArr = newArr.concat(event)
                }
                // console.log(newArr)
                this.setState({data: newArr})
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
                renderItem={({item, index}) => <FlatItem data={item}
                                                         longPress={() => this._delete(index)}/>}
            />
        )
    }

    //获取所有下载信息
    _findAllInfo() {
        DownloadUI.findAllInfo().then(result => {
            this.setState(this.state.data = result)
        }).catch(error => {
        }).finally(() => {
            ProgressDialogNative.dismiss()
            LoadUtilNative.loadFinish()
        })
    }

    _delete(deleteIndex) {
        DeleteDialog.show((chosen) => {
            if (chosen) {
                let bean = this.state.data[deleteIndex]
                DownloadUI.delete(JSON.stringify(bean))
                FileNative.deleteFile(bean.SavePath + bean.Name)
            }
            var newArr = []
            Object.keys(this.state.data).map((key, index) => {
                if (index != deleteIndex)
                    newArr = newArr.concat(this.state.data[index])
            });
            this.setState({data: newArr})
        })
    }
}