import commonNames from "../config/common-name-config";

const serverAddress = function() {
    // return 'http://120.27.11.139:8080'
    return 'http://localhost:8080'
}()

export function linkUrl(fileName) {
    return `${serverAddress}/file/download/${fileName}`
}

export async function upload(api, file, success, fail) {
    try {
        const request = new XMLHttpRequest();
        request.responseType = 'json';

        const formData = new FormData();
        formData.append("file", file, file.name);

        await request.open('POST', `${serverAddress}${api}/${localStorage.getItem(commonNames.token)}`);

        request.onload = function() {
            if (request.status === 200) {
                return success(request.response.payload)
            } else {
                return fail({title: `系统错误,url=[${api}]`, detail: request.response.message})
            }
        }

        await request.send(formData);
    } catch (error) {
        return fail({title: `系统错误,url=[${api}]`, detail: error.message})
    }
}

export async function post(api, request, success, fail) {
    try {
        const body = request.token ? JSON.stringify(request) :  JSON.stringify({...request, token: localStorage.getItem(commonNames.token)})
        console.log(`#POST# API[${api}]：${body}`)

        const response = await fetch(serverAddress+api, {method: 'POST', mode:'cors', headers: {"Content-Type": "application/json"}, body: body})
        const json = await response.json()

        console.log(`#POST RESPONSE#${JSON.stringify(json)}`)

        if (json.status === 200) return success(json.payload)

        return fail({title: `系统错误,url=[${api}]`, detail: json.message})
    } catch(error) {
        return fail({title: `系统错误,url=[${api}]`, detail: error.message ? error.message : '系统出现异常，请联系管理员！'})
    }
}