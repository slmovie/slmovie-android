'use strict';

import React from 'react';
import {
    AppRegistry,
    StyleSheet,
    Text,
    View,
    NativeModules,
} from 'react-native';

let DetailModule = NativeModules.DetailNative
let SnackbarModule = NativeModules.SnackbarNative
import CommonUtils from '../../utils/CommonUtils.js'
import DetailView from "./DetailView.js";
import {version} from "./Version.js"
import {check} from "../../utils/CheckVersion.js"

class HelloWorld extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            movies: {}
        }
    }

    componentDidMount() {
        this._getMovie()
        check("detail", version)
    }

    render() {
        if (CommonUtils.isEmptyObj(this.state.movies)) {
            return (
                <View/>
            )
        } else if (this.state.movies.status.code === "0") {
            SnackbarModule.showSnackbar(this.state.movies.status.error, SnackbarModule.INDEFINITE, () => {
                this._getMovie()
            })
            return (
                <View style={styles.container}>
                    <Text style={styles.hello}>请求失败，请刷新</Text>
                </View>
            )
        } else {
            return (
                <DetailView movies={this.state.movies}/>
            )
        }
    }

    //获取电影信息
    async _getMovie() {
        try {
            let {details} = await DetailModule.getMovie()
            this.setState({movies: JSON.parse(details)})
        } catch (e) {
            console.error(e)
        }
    }
}


let styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        backgroundColor: 'black',
    },
    hello: {
        fontSize: 20,
        textAlign: 'center',
        margin: 10,
    },
});

AppRegistry.registerComponent('DetailActivity', () => HelloWorld);