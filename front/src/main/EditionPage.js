import DragonForm from "../component/DragonForm";
import {useEffect, useState} from "react";
import store, {updateState} from "../store";
import {useNavigate} from "react-router-dom";
import {useAuthorizationCheck, useRequest} from "../Util";
import {useDispatch, useSelector} from "react-redux";

export default function EditionPage() {
    const authorizationCheck = useAuthorizationCheck();
    useEffect(authorizationCheck, [authorizationCheck]);
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const current_dragon = useSelector(state => state.current_dragon);
    const [dragon, setDragon] = useState(JSON.parse(JSON.stringify(current_dragon)));
    const request = useRequest();

    useEffect(() => {
        if (!current_dragon) navigate("/");
    }, [current_dragon]);
    if (!current_dragon) return null;

    const onSubmit = e => {
        request("api/update-dragon", "POST", dragon)
            .then(r => {
                let data = store.getState().data
                    .map(d => d.id === dragon.id ? dragon : d);
                dispatch(updateState({current_dragon: null, data}));
            }).catch(console.error);
    }

    return <DragonForm dragon={dragon} setDragon={setDragon} onSubmit={onSubmit} subSelected />
}
