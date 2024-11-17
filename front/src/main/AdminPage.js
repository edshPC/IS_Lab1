import {useSelector} from "react-redux";
import AdminApplicationTable from "../component/AdminApplicationTable";
import {useAuthorizationCheck, useRequest} from "../Util";
import {useEffect} from "react";
import ChangeHistoryTable from "../component/ChangeHistoryTable";

export default function AdminPage() {
    const authorizationCheck = useAuthorizationCheck();
    useEffect(authorizationCheck, [authorizationCheck]);
    const logged_as = useSelector(state => state.logged_as);
    const request = useRequest();

    const handleApply = () => {
        request("api/admin/apply", "POST")
            .then(res => {}).catch(console.error);
    };

    if (logged_as?.permission !== 'ADMIN') {
        return <div className="container">
            <p>Вы не являетесь администратором.</p>
            <p>Но вы можете подать заявку:</p>
            <button className="rounded full padding" onClick={handleApply}>Подать заявку</button>
        </div>;
    }

    return <div>
        <div className="container">
            <p>Заявки на администраторов</p>
            <AdminApplicationTable/>
        </div>
        <div className="container">
            <p>Последняя история изменений</p>
            <ChangeHistoryTable/>
        </div>
    </div>;
}
