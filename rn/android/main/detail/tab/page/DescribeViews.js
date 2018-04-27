/**
 * Created by 包俊 on 2017/8/7.
 */
import React from 'react';
import {
    Text,
    StyleSheet,
    View,
    ScrollView,
} from 'react-native';

export default class DescribeViews extends React.Component {

    render() {
        return (
            <View style={styles.container}>
                <ScrollView showsVerticalScrollIndicator={true}>
                    <Text style={styles.text}>{this._split(this.props.movies.movies.describe)[0]}</Text>
                </ScrollView>
            </View>
        )
    }

    _split(msg) {
        return msg.split("影片短评")
    }
}

let styles = StyleSheet.create({
    container: {
        backgroundColor: "#2b2b2b",
        flex: 1,
    },
    text: {
        color: "#ffffff",
        fontSize: 14,
        margin: 15,
    },
})