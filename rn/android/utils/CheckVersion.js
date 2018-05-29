/**
 * Created by 包俊 on 2017/8/16.
 */

import {WebRoot} from "../contans/WebRoot.js"
import {CheckVersion} from "../contans/HtmlCode.js"
import {
    NativeModules,
} from 'react-native';

let CheckVersionModule = NativeModules.CheckVersionNative

export async function check(moudle, version) {
    let url = WebRoot + CheckVersion + moudle + "?version=" + version
    console.log(url)
    let response = await
        fetch(url, {
            method: 'GET',
        });
    if (response.status == 200) {
        console.log(response)
        let responseJson = await
            response.json()
        console.log(responseJson)
        if (responseJson.version) {
            CheckVersionModule.Download(moudle)
        }
    }
}