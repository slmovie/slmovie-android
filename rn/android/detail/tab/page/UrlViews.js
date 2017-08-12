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
    Alert,
} from 'react-native';

let DownloadModule = NativeModules.DownloadNative
let ToastDialog = NativeModules.ToastDialogNative

export default class UrlViews extends React.Component {

    constructor(props) {
        super(props)
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
        let lists = []
        for (let i = 0; i < urls.length; i++) {
            lists.push(this._renderUrl(i, urls[i]))
        }
        return lists
    }

    _renderUrl(i, url) {
        return (
            <TouchableOpacity onPress={() => {
                this._start(url.download)
                this.setState({choosen: i})
            }} key={i}>
                <Text style={[styles.text, this._choosen(i)]}>
                    {"[" + url.fileSize + "]" + url.name}
                </Text>
            </TouchableOpacity>
        )
    }

    _choosen(i) {
        if (i === this.state.choosen) {
            return {color: "skyblue",}
        }
    }

    async _start(url) {
        let {result} = await  DownloadModule.pushDownload(url)
        if (!result) {
            // Alert.alert("提示", "启动下载器失败，下载地址已复制到剪切板，请自行粘贴下载", [{text: '确认'}])
            ToastDialog.show("提示", "启动迅雷失败，下载地址已复制到剪切板，请自行粘贴下载", ["确定"], () => {
                    ToastDialog.dismiss()
                }, () => {
                }
            )
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
        textDecorationLine: "underline"
    }
})