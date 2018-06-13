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
    NativeModules,
} from 'react-native';

let ProgressBar = require('ProgressBarAndroid');
let XLDownload = NativeModules.XLDownloadNative;

export default class DownloadUIItem extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            data: this.props.data
        }
    }

    render() {
        return (
            <View style={styles.RootView}>
                <View style={styles.infoView}>
                    <View style={styles.nameView}>
                        <Text style={styles.textName}>{this.state.data.Name}</Text>
                        <View style={styles.playView}>
                            <Image source={{uri: 'ic_download_play'}}
                                   style={{width: 30, height: 30}}/>
                            <Text>边下边播</Text>
                        </View>
                    </View>
                    <ProgressBar styleAttr="Horizontal"
                                 progress={0.1}
                                 indeterminate={false}
                                 color={"#ffffff"}/>
                    <View style={styles.sizeView}>
                        <Text style={styles.textSize}>
                            {this._convertFileSize(this.state.data.DownloadSize) + "/" + this._convertFileSize(this.state.data.TotalSize)}</Text>
                        <Text style={styles.textProgress}>
                            {this._calculateProgress(this.state.data.DownloadSize, this.state.data.TotalSize)}
                        </Text>
                    </View>
                </View>
                <View style={styles.buttonView}>
                    <TouchableWithoutFeedback onPress={() => this._download}>
                        {this._download_img()}
                    </TouchableWithoutFeedback>
                </View>
            </View>
        )
    }

    //下载开始暂停播放按钮
    _download() {

    }

    //0连接中1下载中 2下载完成 3失败
    _download_img() {
        console.log(this.state.data.DownloadStatus)
        switch (this.state.data.DownloadStatus) {
            case 0, 1:
                return (
                    <Image source={{uri: 'ic_download_stop'}}
                           style={{width: 50, height: 50}}/>
                )
                break
            case 2:
                return (
                    <Image source={{uri: 'ic_download_play'}}
                           style={{width: 50, height: 50}}/>
                )
                break
            case 3:
                return (
                    <Image source={{uri: 'ic_download_retry'}}
                           style={{width: 50, height: 50}}/>
                )
                break
        }
    }

    //计算下载百分比
    _calculateProgress(downloadSize, totalSize) {
        let str = Number(downloadSize / totalSize).toFixed(2) + "%";
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
    },
    nameView: {
        flex: 1,
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'flex-end',
    },
    sizeView: {
        flex: 1,
        flexDirection: 'row',
        justifyContent: 'space-between'
    },
    buttonView: {
        flex: 1,
        flexDirection: 'column',
        justifyContent: 'center',
        alignItems: 'center'
    },
    playView: {
        flex: 1,
        flexDirection: 'row',
        alignItems: 'center',
    },
    text: {
        color: "#ffffff",
        fontSize: 14,
    },
    textName: {
        flex: 2,
        color: "#ffffff",
        fontSize: 16,
    },
    textSize: {
        color: "#ffffff",
        fontSize: 12,
        marginBottom: 3,
    },
    textProgress: {
        color: "#ffffff",
        fontSize: 12,
        marginBottom: 3,
        alignSelf: 'flex-end',
    },
    textPlay: {
        color: "#ffffff",
        fontSize: 15,
    }
});