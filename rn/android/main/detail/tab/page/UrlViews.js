/**
 * Created by 包俊 on 2017/8/7.
 */
import React from 'react';
import {
    Text,
    StyleSheet,
    View,
    ScrollView,
    TouchableOpacity,
    NativeModules,
} from 'react-native';

let ToastDialog = NativeModules.ToastDialogNative;
let XLDownload = NativeModules.XLDownloadNative;
let ProgressDialogNative = NativeModules.ProgressDialogNative;

export default class UrlViews extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            choosen: -1,
        }
    }

    render() {
        return (
            <View style={styles.container}>
                <ScrollView showsVerticalScrollIndicator={true}>
                    <View style={styles.scrollView}>
                        {this._renderUrls(this.props.movies.movies.files)}
                    </View>
                </ScrollView>
            </View>
        )
    }

    _renderUrls(urls) {
        let lists = [];
        for (let i = 0; i < urls.length; i++) {
            lists.push(this._renderUrl(i, urls[i]))
        }
        return lists
    }

    _renderUrl(i, url) {
        return (
            <TouchableOpacity onPress={() => {
                this._start(url);
                this.setState({choosen: i})
            }} key={i}>
                <Text style={[styles.text, this._choosen(i)]}>
                    {this._getDownloadUrl(url)}
                </Text>
            </TouchableOpacity>
        )
    }

    _choosen(i) {
        if (i === this.state.choosen) {
            return {color: "skyblue",}
        }
    }

    _start(url) {
        let str = JSON.stringify(url)
        if (url.download.indexOf("ed2k://") != -1) {
            XLDownload.ed2kDownload(str)
        } else if (url.download.indexOf("magnet:?") != -1) {
            XLDownload.scanTorrent(str)
        } else {
            XLDownload.sysDownload(url.download)
        }
    }

    //地址拼接
    _getDownloadUrl(url) {
        if (url.fileSize == '') {
            return url.name
        } else {
            return "[" + url.fileSize + "]" + url.name
        }
    }
}

let styles = StyleSheet.create({
    container: {
        backgroundColor: "#2b2b2b",
        flex: 1,
    },
    text: {
        color: "#ffffff",
        fontSize: 15,
        marginTop: 15,
        textDecorationLine: "underline",
        marginLeft: 8,
        marginRight: 8,
    }
});