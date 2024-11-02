import {Link, Outlet} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {useEffect} from "react";
import {fetchLoggedAs} from "../login/LoginMain";

function Header() {
    const dispatch = useDispatch();
    const error = useSelector(state => state.error);
    const logged_as = useSelector(state => state.logged_as);

    useEffect(() => {
        fetchLoggedAs().then(logged_as => dispatch({type: 'SET', payload: {logged_as}}));
    }, [dispatch]);

    const logout = () => dispatch({type: "CLEAR_AUTH"});

    return (<>
        <header className="container" id="header">
            <div>
                <p>Лабораторная работа #1 по ИС, Вариант 130343</p>
                <p>Выполнил Щербинин Эдуард P3314</p>
            </div>
            <div>
                {logged_as ? <>
                    <p>Привет, {logged_as}</p>
                    <button className="rounded full" onClick={logout}>Выйти</button>
                </> : <>
                    <p>Вы не авторизованы</p>
                    <Link to={"/login"}>
                        <button className="rounded full">Войти</button>
                    </Link>
                </>}
            </div>
        </header>
        <Outlet/>
        {error ? <div className="container error">Error: {error}</div> : <></>}
    </>);
}

export default Header;