import Table from "./Table";
import {useDispatch} from "react-redux";
import {Link} from 'react-router-dom';
import {useRequest} from "../Util";
import store, {updateState} from "../store";
import {useState} from "react";
import InputField, {Type} from "./InputField";

function DragonAction({dragon}) {
    const dispatch = useDispatch();
    const request = useRequest();

    const handleEdit = e => {
        dispatch(updateState({current_dragon: dragon}));
    };

    const handleRemove = e => {
        request("api/remove_dragon", "POST", dragon)
            .then(r => {
                console.log(r);
                let data = store.getState().data
                    .filter(d => d.id !== dragon.id);
                dispatch(updateState({data}));
            }).catch(console.error);
    };

    return <div className="">
        <Link to="/edit">
            <button className="rounded full" onClick={handleEdit}>Изменить</button>
        </Link>
        <button className="rounded full" onClick={handleRemove}>Удалить</button>
    </div>
}

function DragonAttribute({name, keys = [], object = {}}) {
    return <div className="container">
        <p><b>{name}</b></p>
        {keys.map((key, i) => <p key={i}>{key}: {object[key]}</p>)}
    </div>
}

export default function DragonTable({data = []}) {
    const [filterText, setFilterText] = useState('');
    const [filterBy, setFilterBy] = useState({name: 'Name', selector: row => row.name});

    const columns = [
        {name: 'ID', selector: row => row.id, id: 'id', width: '70px'},
        {name: 'Name', selector: row => row.name, width: '150px'},
        // {name: 'X', selector: row => row.coordinates.x, width: '70px'},
        // {name: 'Y', selector: row => row.coordinates.y, width: '70px'},
        {name: 'Creation Date', selector: row => new Date(row.creationDate).toLocaleString(), width: '160px'},
        // {name: 'Cave Depth', selector: row => row.cave.depth},
        // {name: 'Number of Treasures', selector: row => row.cave.numberOfTreasures || 'N/A'},
        {name: 'Age', selector: row => row.age},
        {name: 'Color', selector: row => row.color},
        {name: 'Type', selector: row => row.type || 'N/A', width: '160px'},
        {name: 'Character', selector: row => row.character, width: '140px'},
        // {name: 'Head Size', selector: row => row.head.size},
        // {name: 'Eyes Count', selector: row => row.head.eyesCount},
        // {name: 'Tooth Count', selector: row => row.head.toothCount},
        // {name: 'Killer Name', selector: row => row.killer?.name || 'N/A'},
        // {name: 'Killer Eye Color', selector: row => row.killer?.eyeColor || 'N/A'},
        // {name: 'Killer Hair Color', selector: row => row.killer?.hairColor || 'N/A'},
        // {name: 'Killer Height', selector: row => row.killer?.height || 'N/A'},
        // {name: 'Killer Weight', selector: row => row.killer?.weight || 'N/A'},
        // {name: 'Killer Passport ID', selector: row => row.killer?.passportID || 'N/A'},
        // {name: 'Killer Nationality', selector: row => row.killer?.nationality || 'N/A'},
        // {name: 'Location X', selector: row => row.killer?.location?.x || 'N/A'},
        // {name: 'Location Y', selector: row => row.killer?.location?.y || 'N/A'},
        // {name: 'Location Z', selector: row => row.killer?.location?.z || 'N/A'},
        // {name: 'Location Name', selector: row => row.killer?.location?.name || 'N/A'},
        {name: 'Owner', selector: row => row.owner?.login},
        {cell: row => <DragonAction dragon={row}/>},
    ];
    columns.forEach(col => {
        col.sortable = true;
        col.width = col.width || '115px';
    });

    data = data.filter(d => String(filterBy.selector(d)).toLowerCase()
        .includes(filterText.toLowerCase()));

    const expandable = ({data}) => {
        return <div className="box">
            <div className="flex">
                <DragonAttribute name="Coordinates" keys={["id", "x", "y"]} object={data.coordinates}/>
                <DragonAttribute name="Cave" keys={["id", "depth", "numberOfTreasures"]} object={data.cave}/>
                <DragonAttribute name="Head" keys={["id", "size", "eyesCount", "toothCount"]} object={data.head}/>
                <DragonAttribute name="Killer" keys={["id", "name", "eyeColor", "hairColor", "height", "weight", "passportID", "nationality"]} object={data.killer}/>
                <DragonAttribute name="Killer Location" keys={["id", "name", "x", "y", "z"]} object={data.killer.location}/>
            </div>
            {/*<DragonAction dragon={data}/>*/}
        </div>
    };

    return <>
        <label>Filter by {filterBy.name}: </label>
        <input className="input box rounded" type="text"
               value={filterText} onChange={e => setFilterText(e.target.value)} />
        <Table
            columns={columns}
            data={data}
            dense
            persistTableHead
            noDataComponent={null}
            direction={'ltr'}
            defaultSortFieldId={'id'}
            pagination
            paginationPerPage={5}
            paginationRowsPerPageOptions={[5, 10, 20, 30]}
            fixedHeader
            responsive
            onSort={setFilterBy}
            expandableRows
            expandableRowsComponent={expandable}
        />
    </>;
}
