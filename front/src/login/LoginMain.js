import LoginApp from "./LoginApp";
import {useEffect, useState} from "react";
import {useNavigate} from 'react-router-dom';
import {silentRequest, useRequest} from "../Util";
import {useDispatch, useSelector} from "react-redux";
import {updateState} from "../store";

export default function LoginAppMain(props) {
    const navigate = useNavigate();
    const logged_as = useSelector(state => state.logged_as);

    useEffect(() => {
        if (logged_as) navigate("/");
    }, [logged_as, navigate]);

    const [login, setLogin] = useState('');
    const [password, setPassword] = useState('');
    const dispatch = useDispatch();
    const request = useRequest();

    const auth = (type) => {
        if (!login || !password) return;
        request("api/public/" + type, "POST", {login, password})
            .then(r => {
                dispatch(updateState({
                    token: r.token, logged_as: r.logged_as
                }));
            }).catch(console.error);
    }

    const fetcher = {
        loginChangeHandle: ev => setLogin(ev.target.value),
        passwordChangeHandle: ev => setPassword(ev.target.value),
        loginHandle: () => auth("login"),
        registerHandle: () => auth("register"),
    };

    return <LoginApp fetcher={fetcher}/>;
}

export async function fetchLoggedAs() {
    return (await silentRequest("api/public/check_auth"))?.logged_as || null;
}
