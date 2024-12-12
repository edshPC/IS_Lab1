import {useEffect, useState} from "react";
import {silentRequest, useRequest} from "../Util";
import Table from "./Table";

function ApplicationAction({application, onAction}) {
    const request = useRequest();

    const handleAccept = e => {
        request("api/admin/accept", "POST", application)
            .then(r => onAction(application)).catch(console.error);
    };

    const handleDecline = e => {
        request("api/admin/decline", "POST", application)
            .then(r => onAction(application)).catch(console.error);
    };

    return <div className="">
        <button className="rounded full" onClick={handleAccept}>Принять</button>
        <button className="rounded full" onClick={handleDecline}>Отклонить</button>
    </div>;
}

export default function AdminApplicationTable() {
    const [applications, setApplications] = useState([]);

    useEffect(() => {
        silentRequest("api/admin/get-applications")
            .then(r => r && setApplications(r.data));
    }, []);

    const removeApplication = application => {
      setApplications(applications.filter(x => x !== application));
    };

    const columns = [
        {name: 'User Name', selector: row => row.user.username, width: '160px'},
        {name: 'Creation Date', selector: row => new Date(row.creationDate).toLocaleString(), width: '160px'},
        {cell: row => <ApplicationAction application={row} onAction={removeApplication} />},
    ];

    return <Table
        columns={columns}
        data={applications}
    />;
}
