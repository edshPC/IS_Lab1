import DragonForm from "../component/DragonForm";
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
        id: null,
        name: null,
        coordinates: { id: null, x: null, y: null },
        cave: { id: null, depth: null, numberOfTreasures: null },
        age: null,
        color: null,
        type: null,
        character: null,
        head: { id: null, size: null, eyesCount: null, toothCount: null },
        killer: {
            id: null,
            name: null,
            eyeColor: null,
            hairColor: null,
            location: { id: null, x: null, y: null, name: null },
            height: null,
            weight: null,
            passportID: null,
            nationality: null
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
