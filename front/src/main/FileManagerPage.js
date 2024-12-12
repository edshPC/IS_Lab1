import FileUploadForm from "../component/FileUploadForm";
import FileTable from "../component/FileTable";
import {useEffect, useState} from "react";
import {silentRequest, useAuthorizationCheck} from "../Util";

export default function FileManagerPage() {
    const authorizationCheck = useAuthorizationCheck();
    useEffect(authorizationCheck, [authorizationCheck]);
    const [files, setFiles] = useState([]);

    const updateFiles = () => {
        silentRequest("api/file/get-all")
            .then(r => r && setFiles(r.data));
    }

    useEffect(updateFiles, []);

    return <div>
        <div className="container">
            <p>Ваши файлы:</p>
            <FileTable files={files} onAciton={updateFiles}/>
        </div>
        <div className="container">
            <FileUploadForm onAction={updateFiles}/>
        </div>
    </div>

}
