import {useAuthorizationCheck, useRequest} from "../Util";
import {useEffect, useState} from "react";
import DragonRepresentation from "../component/DragonRepresentation";
import KillerForm from "../component/KillerForm";

export default function SpecialPage() {
    const authorizationCheck = useAuthorizationCheck();
    useEffect(authorizationCheck, [authorizationCheck]);
    const request = useRequest();

    const [avgAge, setAvgAge] = useState('-');
    const getAvgAge = () => request('api/average-age')
        .then(r => setAvgAge(r.data)).catch(console.error);

    const [minCoordsDragon, setMinCoordsDragon] = useState();
    const getMinCoordsDragon = () => request('api/minimum-coordinates')
        .then(r => setMinCoordsDragon(r.data)).catch(console.error);

    const [maxHeadDragon, setMaxHeadDragon] = useState();
    const getMaxHeadDragon = () => request('api/maximum-head')
        .then(r => setMaxHeadDragon(r.data)).catch(console.error);

    const [deepestCaveDragon, setDeepestCaveDragon] = useState();
    const getDeepestCaveDragon = () => request('api/deepest-cave')
        .then(r => setDeepestCaveDragon(r.data)).catch(console.error);

    const [killerTeam, setKillerTeam] = useState([]);
    const [killer, setKiller] = useState({location: {}});
    const changeKiller = e => {
        let {name, value} = e.target;
        if (value === '') value = null;
        const keys = name.split('.');
        if (keys.length === 2) {
            setKiller({ ...killer, [keys[1]]: value });
        } else if (keys.length === 3) {
            setKiller({ ...killer, [keys[1]]: { ...killer[keys[1]], [keys[2]]: value } });
        }
    };
    const addKiller = () => {
      setKillerTeam([...killerTeam, killer]);
      setKiller({location: {}});
    };
    const addKillerTeam = () => {
      request('api/create-killers', 'POST', killerTeam)
          .then(r => setKillerTeam([])).catch(console.error);
    };

    return <div className="">
        <div className="container">
            <p>Средний возраст драконов: {avgAge}</p>
            <button className="rounded margin" onClick={getAvgAge}>Определить</button>
        </div>
        <div className="container">
            <p>Дракон с наименьшими координатами</p>
            <DragonRepresentation dragon={minCoordsDragon}/>
            <button className="rounded margin" onClick={getMinCoordsDragon}>Определить</button>
        </div>
        <div className="container">
            <p>Дракон с самой большой головой</p>
            <DragonRepresentation dragon={maxHeadDragon}/>
            <button className="rounded margin" onClick={getMaxHeadDragon}>Определить</button>
        </div>
        <div className="container">
            <p>Дракон из самой глубокой пещеры</p>
            <DragonRepresentation dragon={deepestCaveDragon}/>
            <button className="rounded margin" onClick={getDeepestCaveDragon}>Определить</button>
        </div>
        <div className="container">
            <p>Создать команду убийц драконов</p>
            {killerTeam.map((killer, i) =>
                <DragonRepresentation key={i} dragon={{killer}} includeMain={false}/>)}
            <KillerForm object={{killer}} onChange={changeKiller} />
            <button className="rounded margin" onClick={addKiller}>Добавить</button>
            <button className="rounded margin" onClick={addKillerTeam}>Отправить</button>
        </div>
    </div>;
}
