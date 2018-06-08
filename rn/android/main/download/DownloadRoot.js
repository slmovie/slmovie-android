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
} from 'react-native';

import {check} from "../../utils/CheckVersion.js"

export default class DownloadRoot extends React.Component {
    componentDidMount() {
        check()
    }

}