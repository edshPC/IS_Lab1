import Table from "./Table";
import {downloadFile, useRequest} from "../Util";

function FileAction({file, onAction}) {
    const request = useRequest();

    const handleDownload = e => {
        request(`api/file/download/${file.name}`, "GET", null, null, true)
            .then(blob => downloadFile(blob, file.name)).catch(console.error);
    };

    const handleDelete = e => {
        request("api/file/delete", "DELETE", file)
            .then(r => onAction(file)).catch(console.error);
    };

    return <div className="">
        <button className="rounded full" onClick={handleDownload}>Скачать</button>
        <button className="rounded full" onClick={handleDelete}>Удалить</button>
    </div>;
}

export default function FileTable({files, onAciton}) {

    const columns = [
        {name: 'Name', selector: row => row.name, width: '200px'},
        {name: 'Size', selector: row => `${row.size} B`, width: '120px'},
        {name: 'Last Modified', selector: row => new Date(row.lastModified).toLocaleString(), width: '160px'},
        {cell: row => <FileAction file={row} onAction={onAciton} />},
    ];

    return <Table
        columns={columns}
        data={files}
    />;

}