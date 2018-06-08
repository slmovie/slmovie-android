/**
 * Created by 包俊 on 2018/6/5.
 */
'use strict';
import React from 'react';
import {
    AppRegistry,
} from 'react-native';
import DetailActivity from './detail/DetailRoot.js'
import DownloadActivity from './download/DownloadRoot.js'

AppRegistry.registerComponent('DetailActivity', () => DetailActivity);
AppRegistry.registerComponent('DownloadActivity', () => DownloadActivity)