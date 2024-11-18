import store, {updateState} from "./store";
import {useDispatch, useSelector} from "react-redux";
import {useNavigate} from "react-router-dom";

const origin = "http://localhost:24770/";

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
    silentRequest("api/public/get_all_dragons")
        .then(r => r && dispatch(updateState({data: r.data})));
}
