/**
 * Created by 包俊 on 2017/8/7.
 */
import React from 'react';
import {
    StyleSheet,
    Text,
    View,
    Image,
    ScrollView,
} from 'react-native';

export default class InfoViews extends React.Component {
    render() {
        return (
            <View style={styles.container}>
                <ScrollView showsVerticalScrollIndicator={true}>
                    <View style={styles.scrollView}>
                        <Image source={{uri: this.props.movies.movies.post}} style={styles.post}/>
                        {this._renderInfos(this.props.movies.movies.detail)}
                    </View>
                </ScrollView>
            </View>
        )
    }

    _renderInfos(infos) {
        let lists = []
        for (let i = 0; i < infos.length; i++) {
            lists.push(this._renderText(i, infos[i]))
        }
        return (
            <View style={styles.info}>
                {lists}
            </View>
        )
    }

    _renderText(i, info) {
        return (
            <Text style={styles.text} key={i}>{info}</Text>
        )
    }
}

let styles = StyleSheet.create({
    container: {
        backgroundColor: "#000000",
    },
    text: {
        color: "#ffffff",
        fontSize: 14,
    },
    post: {
        width: 160,
        height: 221,
        marginTop: 20,
    },
    scrollView: {
        flex: 1,
        justifyContent: 'center',
        alignItems: "center",
    },
    info: {
        marginTop: 20,
        marginLeft: 30,
        marginRight: 30
    }
})
