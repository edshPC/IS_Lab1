import DataTable from 'react-data-table-component';

export default function DragonTable(props) {

    const columns = [
        { name: 'Name', selector: row => row.name },
        { name: 'X', selector: row => row.coordinates.x },
        { name: 'Y', selector: row => row.coordinates.y },
        { name: 'Creation Date', selector: row => row.creationDate },
        { name: 'Cave Depth', selector: row => row.cave.depth },
        { name: 'Number of Treasures', selector: row => row.cave.numberOfTreasures || 'N/A' },
        { name: 'Age', selector: row => row.age },
        { name: 'Color', selector: row => row.color },
        { name: 'Type', selector: row => row.type || 'N/A' },
        { name: 'Character', selector: row => row.character },
        { name: 'Head Size', selector: row => row.head.size },
        { name: 'Eyes Count', selector: row => row.head.eyesCount },
        { name: 'Tooth Count', selector: row => row.head.toothCount },
        { name: 'Killer Name', selector: row => row.killer?.name || 'N/A' },
        { name: 'Killer Eye Color', selector: row => row.killer?.eyeColor || 'N/A' },
        { name: 'Killer Hair Color', selector: row => row.killer?.hairColor || 'N/A' },
        { name: 'Killer Height', selector: row => row.killer?.height || 'N/A' },
        { name: 'Killer Weight', selector: row => row.killer?.weight || 'N/A' },
        { name: 'Killer Passport ID', selector: row => row.killer?.passportID || 'N/A' },
        { name: 'Killer Nationality', selector: row => row.killer?.nationality || 'N/A' },
        { name: 'Location X', selector: row => row.killer?.location?.x || 'N/A' },
        { name: 'Location Y', selector: row => row.killer?.location?.y || 'N/A' },
        { name: 'Location Name', selector: row => row.killer?.location?.name || 'N/A' },
    ];

    return <DataTable columns={columns} data={props.data} />;
}
