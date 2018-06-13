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
    PanResponder,
} from 'react-native';

export default class InfoViews extends React.Component {

    constructor(props) {
        super(props)
    }

    componentWillMount() {
        this._panResponder = PanResponder.create({
            onStartShouldSetPanResponder: (evt, gestureState) => false,
            onPanResponderMove: (evt, gestureState) => {
                console.log(gestureState.dx)

            },
            onPanResponderRelease: (evt, gestureState) => {
                // console.log(gestureState.dx)
                // if (gestureState.dx > 100) {
                //     console.log("finish")
                // } else {
                //     console.log("needFinish: false")
                // }
            },
        })
    }

    render() {
        return (
            <View style={styles.container} {...this._panResponder.panHandlers}>
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
        let lists = [];
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
        backgroundColor: "#2b2b2b",
        flex: 1,
    },
    text: {
        color: "#ffffff",
        fontSize:14,
    }
    ,
    post: {
        width: 160,
        height:
            221,
        marginTop:
            20,
    }
    ,
    scrollView: {
        flex: 1,
        justifyContent:
            'center',
        alignItems:
            "center",
    }
    ,
    info: {
        marginTop: 20,
        marginLeft:
            30,
        marginRight:
            30
    }
});
