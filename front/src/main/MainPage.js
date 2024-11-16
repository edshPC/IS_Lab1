import {silentRequest} from "../Util";
import DragonTable from "../component/DragonTable";
import {useEffect} from "react";
import {useDispatch, useSelector} from "react-redux";
import {updateState} from "../store";
import {Link} from "react-router-dom";

export default function MainPage() {
    const dispatch = useDispatch();
    const data = useSelector(state => state.data);

    const updateDragons = () => {
        silentRequest("api/public/get_all_dragons")
            .then(r => {
                let data = r?.data || [];
                dispatch(updateState({data}))
            });
    };
    useEffect(() => {
        updateDragons();
        const inter = setInterval(updateDragons, 5000);
        return () => clearInterval(inter);
    }, []);

    return <div className="container">
        <DragonTable data={data}/>
        <Link to="/new">
            <button className="rounded">Создать новый</button>
        </Link>
        <Link to="/special">
            <button className="rounded margin">Спец операции</button>
        </Link>
    </div>;
}
