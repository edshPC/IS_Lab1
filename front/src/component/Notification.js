import {useDispatch, useSelector} from "react-redux";
import {useEffect, useState} from "react";
import {updateState} from "../store";

export default function Notification() {
    const dispatch = useDispatch();
    const notification = useSelector(state => state.notification);
    const [tm, setTm] = useState();

    useEffect(() => {
        if (!notification?.message) return;
        clearTimeout(tm);
        setTm(
            setTimeout(dispatch, 5000, updateState({notification: null}))
        );
    }, [notification]);

    if (!notification?.message) return null;
    return <div className={"container box padding notification " +
        (notification.success ? "success" : "error")}>{notification.message}</div>
}
