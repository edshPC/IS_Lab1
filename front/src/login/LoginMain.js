import LoginApp from "./LoginApp";
import {useState} from "react";
import {Navigate} from 'react-router-dom';
import {silentRequest, useRequest} from "../Util";
import {useDispatch, useSelector} from "react-redux";

export default function LoginAppMain(props) {
    const [redirect, redirectTo] = useState();

    const [login, setLogin] = useState('');
    const [password, setPassword] = useState('');
    const dispatch = useDispatch();
    const logged_as = useSelector(state => state.logged_as);
    const request = useRequest();
    if (!redirect && logged_as) redirectTo("/");

    const auth = (type) => {
        if (!login || !password) return;
        request("api/public/" + type, "POST", {login, password})
            .then(r => {
                dispatch({
                    type: "SET", payload: {
                        token: r.token, logged_as: r.logged_as
                    }
                });
            }).catch(console.error);
    }

    const fetcher = {
        loginChangeHandle: ev => setLogin(ev.target.value),
        passwordChangeHandle: ev => setPassword(ev.target.value),
        loginHandle: () => auth("login"),
        registerHandle: () => auth("register"),
    };

    if (redirect) return <Navigate to={redirect}/>;

    return <LoginApp fetcher={fetcher}/>;
}

export async function fetchLoggedAs() {
    return (await silentRequest("api/public/check_auth")).logged_as;
}
