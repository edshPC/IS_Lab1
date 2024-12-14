import Table from "./Table";

export default function ImportHistoryTable({history}) {

    const columns = [
        {name: 'File Name', selector: row => row.fileName, width: '120px'},
        {name: 'Status', selector: row => row.status, width: '120px'},
        {name: 'Entities', selector: row => row.entities, width: '120px'},
        {name: 'Imported At', selector: row => new Date(row.importedAt).toLocaleString(), width: '160px'},
        {name: 'Imported By', selector: row => row.importedBy.username, width: '160px'},
    ];

    return <Table
        columns={columns}
        data={history}
    />;
}
