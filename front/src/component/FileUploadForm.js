import {useRef, useState} from "react";
import {useRequest} from "../Util";

export default function FileUploadForm({onAction}) {
    const [file, setFile] = useState(null);
    const fileInputRef = useRef(null);
    const request = useRequest();

    const handleFileChange = (event) => {
        setFile(event.target.files[0]);
    };

    const handleUpload = () => {
        if (!file) return;
        const formData = new FormData();
        formData.append('file', file);
        request("api/file/upload", "POST", null, formData)
            .then(r => {
                onAction(file);
                setFile(null);
                fileInputRef.current.value = '';
            }).catch(console.error);
    };

    return <div>
        <input type="file" ref={fileInputRef} onChange={handleFileChange}/>
        <button onClick={handleUpload}>Загрузить файл</button>
    </div>

}
