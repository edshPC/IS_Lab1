import DragonForm from "./DragonForm";
import {useEffect, useState} from "react";
import {useAuthorizationCheck, useRequest} from "../Util";
import {useNavigate} from "react-router-dom";
import {useDispatch} from "react-redux";
import store, {updateState} from "../store";

export default function CreationPage() {
    const authorizationCheck = useAuthorizationCheck();
    useEffect(authorizationCheck, [authorizationCheck]);
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const [dragon, setDragon] = useState({
        coordinates: {},
        cave: {},
        color: 'RED',
        character: 'CUNNING',
        head: {},
        killer: {
            eyeColor: 'BLUE',
            location: {},
        }
    });

    const request = useRequest();
    const onSubmit = e => {
        request("api/add_dragon", "POST", dragon)
            .then(() => {
                let data = [...store.getState().data, dragon];
                dispatch(updateState({data}));
                navigate("/");
            }).catch(console.error);
    }

    return <DragonForm dragon={dragon} setDragon={setDragon} onSubmit={onSubmit} />
}
