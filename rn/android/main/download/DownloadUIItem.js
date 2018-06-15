/**
 * Created by 包俊 on 2018/6/12.
 */
'use strict';

import React from 'react';
import {
    StyleSheet,
    Text,
    View,
    Image,
    TouchableWithoutFeedback,
    NativeModules, DeviceEventEmitter,
} from 'react-native';

let ProgressBar = require('ProgressBarAndroid');
let XLDownload = NativeModules.XLDownloadNative;
let DownloadUI = NativeModules.XLDownloadUINative;

export default class DownloadUIItem extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            data: this.props.data,
            progress: 0,
            downloadStatus: 3,
        }
    }

    componentDidMount() {
        this.QuerytaskEmit = DeviceEventEmitter.addListener('Querytask', event => {
            console.log(event.DownloadStatus)
            if (this.state.data.DownloadPath == event.DownloadPath && event.DownloadStatus != 0) {
                this.setState({data: event, downloadStatus: event.DownloadStatus})
            }
        });
    }

    componentWillUnmount() {
        this.QuerytaskEmit.remove()
    }

    render() {
        return (
            <View style={styles.RootView}>
                <View style={styles.infoView}>
                    <View style={styles.nameView}>
                        <Text style={styles.textName}>{this.state.data.Name}</Text>
                    </View>
                    <ProgressBar styleAttr="Horizontal"
                                 progress={this.state.progress}
                                 indeterminate={false}
                                 color={"#ffffff"}/>
                    <View style={styles.sizeView}>
                        <Text
                            style={styles.textSize}>{this._convertFileSize(this.state.data.Speed) + "/S"}</Text>
                        <View style={styles.playView}>
                            <Image source={{uri: 'ic_download_play'}}
                                   style={{width: 13, height: 13}}/>
                            <Text style={styles.textSize}>边下边播</Text>
                        </View>
                    </View>
                    <View style={styles.sizeView}>
                        <Text style={styles.textSize}>
                            {this._convertFileSize(this.state.data.DownloadSize) + "/" + this._convertFileSize(this.state.data.TotalSize)}</Text>
                        <Text style={styles.textProgress}>
                            {this._calculateProgress(this.state.data.DownloadSize, this.state.data.TotalSize)}
                        </Text>
                    </View>
                </View>
                <TouchableWithoutFeedback onPress={() => this._download()}
                                          style={styles.touchView}>
                    {this._download_img()}
                </TouchableWithoutFeedback>
            </View>
        )
    }

    //下载开始暂停播放按钮
    _download() {
        if (this.state.downloadStatus == 0 || this.state.downloadStatus == 1) {
            XLDownload.stopTask(this.state.data.TastId)
            var bean = this.state.data
            bean.Speed = 0
            this.setState({downloadStatus: 3, data: bean})
        } else if (this.state.downloadStatus == 2) {

        } else if (this.state.downloadStatus == 3 || this.state.downloadStatus == 4) {
            if (this.state.data.IsTorrent == 0) {
                XLDownload.ed2kDownload(JSON.stringify(this.state.data))
            } else {

            }
        }
    }

    //0连接中1下载中 2下载完成 3失败
    _download_img() {
        switch (this.state.downloadStatus) {
            case 0:
                return (
                    <View style={styles.buttonView}>
                        <Image source={{uri: 'ic_download_stop'}}
                               style={{width: 50, height: 50}}/>
                        <Text style={styles.textName}>连接中</Text>
                    </View>
                )
                break
            case 1:
                return (
                    <View style={styles.buttonView}>
                        <Image source={{uri: 'ic_download_stop'}}
                               style={{width: 50, height: 50}}/>
                        <Text style={styles.textName}>下载中</Text>
                    </View>
                )
                break
            case 2:
                return (
                    <View style={styles.buttonView}>
                        <Image source={{uri: 'ic_download_play'}}
                               style={{width: 50, height: 50}}/>
                        <Text style={styles.textName}>下载完成</Text>
                    </View>
                )
                break
            case 3:
                return (
                    <View style={styles.buttonView}>
                        <Image source={{uri: 'ic_download_start'}}
                               style={{width: 50, height: 50}}/>
                        <Text style={styles.textName}>下载暂停</Text>
                    </View>
                )
                break
        }
    }

    //计算下载百分比
    _calculateProgress(downloadSize, totalSize) {
        let long = Number(downloadSize / totalSize).toFixed(2)
        // this.setState({progress: long * 100})
        let str = long + "%";
        return str
    }

    _convertFileSize(value) {
        if (null == value || value == '') {
            return "0B";
        }
        var unitArr = new Array("B", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB");
        var index = 0,
            srcsize = parseFloat(value);
        index = Math.floor(Math.log(srcsize) / Math.log(1024));
        var size = srcsize / Math.pow(1024, index);
        //  保留的小数位数
        size = size.toFixed(2);
        return size + unitArr[index];
    }
}

let styles = StyleSheet.create({
    RootView: {
        flex: 1,
        flexDirection: 'row',
        backgroundColor: '#3c3c3c',
        alignItems: 'flex-end',
        justifyContent: 'center',
        marginLeft: 10,
        marginTop: 10,
        marginRight: 10,
    },
    infoView: {
        flex: 3,
        flexDirection: 'column',
        marginLeft: 10,
        marginRight: 10,
        marginTop: 3,
        marginBottom: 10,
    },
    nameView: {
        flex: 1,
        marginBottom: 3,
    },
    sizeView: {
        flex: 1,
        flexDirection: 'row',
        justifyContent: 'space-between',
        marginTop: 3,
    },
    buttonView: {
        flex: 1,
        flexDirection: 'column',
        justifyContent: 'flex-end',
        alignItems: 'center',
        marginTop: 15,
    },
    touchView: {
        flex: 1,
        alignSelf: 'flex-end'
    },
    playView: {
        flexDirection: 'row',
        alignItems: 'center',
        alignSelf: 'flex-end',
    },
    textName: {
        flex: 4,
        color: "#ffffff",
        fontSize: 14,
    },
    textSize: {
        color: "#ffffff",
        fontSize: 12,
        marginLeft: 2,
    },
    textProgress: {
        color: "#ffffff",
        fontSize: 12,
        alignSelf: 'flex-end',
    },
    textPlay: {
        color: "#ffffff",
        fontSize: 15,
    },
    textDownload: {
        flex: 1,
        color: "#ffffff",
        fontSize: 12,
        marginTop: 8,
        alignSelf: 'flex-end',
    },
});