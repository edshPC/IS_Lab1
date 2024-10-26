import LoginApp from "./LoginApp";
import {useState} from "react";
import {Navigate, useLoaderData} from 'react-router-dom';
import {request} from "../Util";

export default function LoginAppMain(props) {
    // let [redirect, redirectTo] = useState();
    // const loaded = useLoaderData();
    // if(loaded.success) redirect = '/main';

    const [login, setLogin] = useState('');
    const [password, setPassword] = useState('');

    const fetcher = {
        loginChangeHandle: ev => setLogin(ev.target.value),
        passwordChangeHandle: ev => setPassword(ev.target.value),
        loginHandle: () => {
            if (!login || !password) return;
            request("api/public/login")
        },
        registerHandle: () => {

        },
    };

    // if(redirect) return (<Navigate to={`.${redirect}`} relative/>);

    return (<LoginApp fetcher={fetcher}/>);
}
