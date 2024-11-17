
function DragonAttribute({name, keys = [], object}) {
    if (!object) return null;
    return <div className="container">
        <p><b>{name}</b></p>
        {keys.map((key, i) =>
            object[key] !== undefined &&
            <p key={i}>{key}: {object[key]}</p>
        )}
    </div>
}

export default function DragonRepresentation({dragon, includeMain = true}) {
    if (!dragon) return null;
    return <div className="box">
        <div className="flex">
            {includeMain && <DragonAttribute
                name="Dragon" keys={["id", "name", "age", "color", "type", "character"]} object={dragon}/>}
            <DragonAttribute name="Coordinates" keys={["id", "x", "y"]} object={dragon.coordinates}/>
            <DragonAttribute name="Cave" keys={["id", "depth", "numberOfTreasures"]} object={dragon.cave}/>
            <DragonAttribute name="Head" keys={["id", "size", "eyesCount", "toothCount"]} object={dragon.head}/>
            <DragonAttribute name="Killer" keys={["id", "name", "eyeColor", "hairColor", "height", "weight", "passportID", "nationality"]} object={dragon.killer}/>
            <DragonAttribute name="Killer Location" keys={["id", "name", "x", "y", "z"]} object={dragon.killer?.location}/>
        </div>
    </div>
}
