import {updateDragons} from "../Util";
import DragonTable from "../component/DragonTable";
import {useEffect} from "react";
import {useDispatch, useSelector} from "react-redux";
import {Link} from "react-router-dom";

export default function MainPage() {
    const dispatch = useDispatch();
    const data = useSelector(state => state.data);

    useEffect(() => {
        const inter = setInterval(updateDragons, 5000, dispatch);
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
        <Link to="/file">
            <button className="rounded">Файловый менеджер</button>
        </Link>
    </div>;
}
