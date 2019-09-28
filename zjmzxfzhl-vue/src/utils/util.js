import {isExternal} from '@/utils/validate'
import Layout from '@/layout'
import {getAction} from '@/api/manage'

export function getDicts(codeTypeIds){
    return getAction("/sys/codeInfo/getSysCodeInfos",{codeTypeIds})
}

export function formatDictText(dicts,values){
    if(!(Array.isArray(dicts) && dicts.length > 0)){
        return values;
    }
    if(!values){
        return ""
    }
    let valueArr = values.split(",")
    let contentArr = []
    dicts.forEach(dict => {
        for(let i=0;i<valueArr.length;i++){
            if (valueArr[i] === dict.value) {
                contentArr.push(dict.content)
                break;
            }
        }
    })
    return contentArr.toString()
}
