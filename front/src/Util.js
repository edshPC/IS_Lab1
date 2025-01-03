import store, {updateState} from "./store";
import {useDispatch, useSelector} from "react-redux";
import {useNavigate} from "react-router-dom";

const origin = "http://localhost:24770/";

export async function request(url, method = "GET", body = null, formData = null, blob = false) {
    let headers = {};
    let token = store.getState().token;
    if (token) headers['Authorization'] = `Bearer ${token}`;
    if (!formData) headers['Content-Type'] = 'application/json;charset=utf-8';
    body = body && JSON.stringify(body);

    let raw = await fetch(origin + url, {
        method,
        headers,
        body: body || formData || undefined,
    });
    if (blob) return await raw.blob();
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
            if(res.message)
                dispatch(updateState({notification: {success: true, message: res.message}}));
            return res;
        } catch (err) {
            dispatch(updateState({notification: {success: false, message: err.message}}));
            throw err;
        }
    }
}

export function useAuthorizationCheck() {
    const logged_as = useSelector(state => state.logged_as);
    const navigate = useNavigate();
    const dispatch = useDispatch();
    return () => {
        if (!logged_as) {
            dispatch(updateState({notification:
                    {success: false, message: "Нужно авторизоваться чтобы получить доступ к этой странице"}
            }));
            navigate("/");
        }
    };
}

export async function silentRequest(...args) {
    try {
        return await request(...args);
    } catch (err) {}
}

export function updateDragons(dispatch) {
    silentRequest("api/public/get-all-dragons")
        .then(r => r && dispatch(updateState({data: r.data})));
}

export function downloadFile(blob, filename) {
    const url = window.URL.createObjectURL(new Blob([blob]));
    const link = document.createElement('a');
    link.href = url;
    link.setAttribute('download', filename);
    document.body.appendChild(link);
    link.click();
    link.remove();
}
