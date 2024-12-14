import FileUploadForm from "../component/FileUploadForm";
import FileTable from "../component/FileTable";
import {useEffect, useState} from "react";
import {silentRequest, useAuthorizationCheck} from "../Util";
import ImportHistoryTable from "../component/ImportHistoryTable";

export default function FileManagerPage() {
    const authorizationCheck = useAuthorizationCheck();
    useEffect(authorizationCheck, [authorizationCheck]);
    const [files, setFiles] = useState([]);
    const [history, setHistory] = useState([]);

    const updateFiles = () => {
        silentRequest("api/file/get-all")
            .then(r => r && setFiles(r.data));
        silentRequest("api/file/get-history")
            .then(r => r && setHistory(r.data));
    }

    useEffect(updateFiles, []);

    return <div>
        <div className="container">
            <p>Загрузить новый файл:</p>
            <FileUploadForm onAction={updateFiles}/>
        </div>
        <div className="container">
            <p>Ваши файлы:</p>
            <FileTable files={files} onAciton={updateFiles}/>
        </div>
        <div className="container">
            <p>История создания объектов из файлов:</p>
            <ImportHistoryTable history={history}/>
        </div>
    </div>

}
