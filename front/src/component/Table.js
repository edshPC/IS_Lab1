import DataTable from 'react-data-table-component';

export default function Table(props) {

    return <div className="table">
        <DataTable {...props} />
    </div>;
}
