import {Link, Outlet} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {useEffect} from "react";
import {fetchLoggedAs} from "../login/LoginMain";
import {updateState} from "../store";
import Notification from "../component/Notification";
import {updateDragons} from "../Util";

function Header() {
    const dispatch = useDispatch();
    const logged_as = useSelector(state => state.logged_as);

    useEffect(() => {
        fetchLoggedAs().then(logged_as => dispatch(updateState({logged_as})));
        updateDragons(dispatch);
    }, [dispatch]);

    const logout = () => dispatch({type: "CLEAR_AUTH"});

    return (<>
        <Notification />
        <header className="container" id="header">
            <Link to="/">
                <div>
                    <p>Лабораторная работа #1 по ИС, Вариант 130343</p>
                    <p>Выполнил Щербинин Эдуард P3314</p>
                </div>
            </Link>
            <Link to="/admin">
                <button className="rounded">Админ панель</button>
            </Link>
            <div>
                {logged_as ? <>
                    <p>Привет, {logged_as.login}</p>
                    <button className="rounded full" onClick={logout}>Выйти</button>
                </> : <>
                    <p>Вы не авторизованы</p>
                    <Link to="/login">
                        <button className="rounded full">Войти</button>
                    </Link>
                </>}
            </div>
        </header>
        <div className="content">
            <Outlet/>
        </div>
    </>);
}

export default Header;