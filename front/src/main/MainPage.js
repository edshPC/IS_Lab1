import {useRequest} from "../Util";
import DragonTable from "./DragonTable";
import {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import {updateState} from "../store";
import {Link} from "react-router-dom";

export default function MainPage() {
    const request = useRequest();
    //const [data, setData] = useState([]);
    const dispatch = useDispatch();
    const data = useSelector(state => state.data);

    useEffect(() => {
        request("api/public/get_all_dragons")
            .then(r => {
                dispatch(updateState({data: r.data}))
            }).catch(console.error);
    }, [dispatch]);

    return <div className="container">
        <DragonTable data={data} />
        <Link to="/new">
            <button className="rounded">Создать новый</button>
        </Link>
    </div>;
}
