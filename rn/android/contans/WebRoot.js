/**
 * Created by 包俊 on 2017/8/15.
 */
import {Service} from "./Contans.js"

export let WebRoot = getWebRoot()

function getWebRoot() {
    if (Service == "T") {
        return "http://192.168.43.22:3000"
    } else if (Service == "P") {
        return "http://www.slys.cf"
    }
}
