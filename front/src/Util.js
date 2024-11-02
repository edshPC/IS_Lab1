import store from "./store";
import {useDispatch} from "react-redux";

const origin = "http://localhost:8080/";

export async function request(url, method = "GET", body = null) {
    let headers = {'Content-Type': 'application/json;charset=utf-8'};
    let token = store.getState().token;
    if (token) headers['Authorization'] = `Bearer ${token}`;

    let raw = await fetch(origin + url, {
        method,
        headers,
        //credentials: 'include',
        body: body ? JSON.stringify(body) : undefined,
    });
    let res = await raw.json();
    console.log(res);
    if (!res.success) throw new Error(res.message);
    return res;
}

export function useRequest() {
    const dispatch = useDispatch();
    return async (...args) => {
        try {
            let res = await request(...args);
            dispatch({type: 'CLEAR_ERROR'});
            return res;
        } catch (err) {
            dispatch({type: 'ERROR', payload: err.message});
            throw err;
        }
    }
}

export async function silentRequest(...args) {
    try {
        return await request(...args);
    } catch (err) {}
}
