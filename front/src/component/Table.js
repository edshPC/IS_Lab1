import DataTable from 'react-data-table-component';

export default function Table({
                                  dense = true,
                                  persistTableHead = true,
                                  fixedHeader = true,
                                  noDataComponent = null,
                                  direction = 'ltr',
                                  ...props
                              }) {
    return <div className="table">
        <DataTable
            dense={dense}
            persistTableHead={persistTableHead}
            fixedHeader={fixedHeader}
            noDataComponent={noDataComponent}
            direction={direction}
            {...props}
        />
    </div>;
}
