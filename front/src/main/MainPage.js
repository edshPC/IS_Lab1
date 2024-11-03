import {useRequest} from "../Util";
import DragonTable from "./DragonTable";
import {useEffect, useState} from "react";

export default function MainPage() {
    const request = useRequest();
    const [data, setData] = useState([]);

    useEffect(() => {
        request("api/public/get_all_dragons")
            .then(r => setData(r.data)).catch(console.error);
    }, []);

    const testDragon = {
        name: "Draco",
        coordinates: {
            x: Math.random() * 235, // случайное значение от 0 до 235
            y: Math.random() * 811  // случайное значение от 0 до 811
        },
        creationDate: new Date().toISOString(), // текущая дата в формате ISO
        cave: {
            depth: Math.random() * 100, // случайное значение глубины
            numberOfTreasures: Math.random() > 0.5 ? Math.floor(Math.random() * 100) + 1 : null // случайное значение сокровищ или null
        },
        age: Math.floor(Math.random() * 100) + 1, // случайный возраст от 1 до 100
        color: Math.random() > 0.5 ? 'RED' : (Math.random() > 0.5 ? 'BLUE' : 'WHITE'), // случайный цвет
        type: Math.random() > 0.5 ? (Math.random() > 0.5 ? 'WATER' : 'UNDERGROUND') : null, // случайный тип или null
        character: 'GOOD', // фиксированное значение
        head: {
            size: Math.floor(Math.random() * 10) + 1, // случайный размер головы от 1 до 10
            eyesCount: Math.floor(Math.random() * 5) + 1, // случайное количество глаз от 1 до 5
            toothCount: Math.floor(Math.random() * 20) + 1 // случайное количество зубов от 1 до 20
        },
        killer: {
            name: "John Doe",
            eyeColor: 'BLUE', // фиксированный цвет глаз
            hairColor: 'BROWN', // фиксированный цвет волос
            location: {
                x: Math.floor(Math.random() * 100), // случайное значение координаты X
                y: Math.floor(Math.random() * 100), // случайное значение координаты Y
                name: "Mystic Forest" // фиксированное название локации
            },
            height: Math.random() * 2 + 1.5, // случайный рост от 1.5 до 3 метров
            weight: Math.floor(Math.random() * 100) + 50, // случайный вес от 50 до 150 кг
            passportID: "1234567890", // фиксированный паспорт ID
            nationality: 'USA' // фиксированная национальность
        }
    };

    return <DragonTable data={[...data, testDragon]} />;
}
