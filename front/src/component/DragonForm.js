import React from 'react';
import InputField, {Type} from "./InputField";
import OptionalComponent from "./OptionalForm";
import {useSelector} from "react-redux";

const COLOR_VALUES = ["GREEN", "RED", "BLUE", "YELLOW", "BROWN"];
const COUNTRY_VALUES = ["RUSSIA", "USA", "CHINA", "ITALY", "JAPAN"];
const DRAGON_TYPE_VALUES = ["WATER", "UNDERGROUND", "AIR", "FIRE"];
const DRAGON_CHARACTER_VALUES = ["CUNNING", "WISE", "CHAOTIC"];

export default function DragonForm({dragon, setDragon, onSubmit}) {
    const data = useSelector(state => state.data) || [];

    const handleChange = (e) => {
        let {name, value} = e.target;
        if (value === '') value = null;
        const keys = name.split('.');
        setDragon(prevDragon => {
            let updatedDragon = {...prevDragon};
            keys.forEach((key, index) => {
                // Если это последний ключ, устанавливаем значение
                if (index === keys.length - 1) {
                    updatedDragon[key] = value; // Устанавливаем значение для последнего ключа
                } else {
                    updatedDragon = updatedDragon[key] || {}; // Переходим на уровень ниже
                }
            });
            return keys.length > 1 ? {...prevDragon} : updatedDragon;
        });
    };
    const getHandler = name => {
        return value => handleChange({target: {name: `${name}.id`, value}})
    }

    const onGenerate = e => {
        setDragon({...dragon, ...getRandomDragon()});
    }

    return (
        <div className="container">
            <p><b>Укажите аттрибуты</b></p>
            <div className="form">
            <InputField type={Type.String} name="name" object={dragon} onChange={handleChange}
                        check={e => e && e.length > 0}/>
            <InputField type={Type.Integer} name="age" object={dragon} onChange={handleChange}
                        check={e => e > 0}/>
            <InputField type={Type.Enum} name="color" object={dragon} onChange={handleChange}
                        values={COLOR_VALUES} check={e => Boolean(e)}/>
            <InputField type={Type.Enum} name="type" object={dragon} onChange={handleChange}
                        values={DRAGON_TYPE_VALUES}/>
            <InputField type={Type.Enum} name="character" object={dragon} onChange={handleChange}
                        values={DRAGON_CHARACTER_VALUES} check={e => Boolean(e)}/>
            <OptionalComponent name="координаты"
                values={data.map(d => d.coordinates)} setId={getHandler("coordinates")}>
                <InputField type={Type.Integer} name="coordinates.x" object={dragon} onChange={handleChange}
                            check={e => e && e <= 235}/>
                <InputField type={Type.Integer} name="coordinates.y" object={dragon} onChange={handleChange}
                            check={e => e && e <= 811}/>
            </OptionalComponent>
            <OptionalComponent name="перещу"
                               values={data.map(d => d.cave)} setId={getHandler("cave")}>
                <InputField type={Type.Number} name="cave.depth" object={dragon} onChange={handleChange}
                            check={e => e > 0}/>
                <InputField type={Type.Number} name="cave.numberOfTreasures" object={dragon} onChange={handleChange}
                            check={e => e > 0}/>
            </OptionalComponent>
            <OptionalComponent name="голову"
                               values={data.map(d => d.head)} setId={getHandler("head")}>
                <InputField type={Type.Integer} name="head.size" object={dragon} onChange={handleChange}/>
                <InputField type={Type.Number} name="head.eyesCount" object={dragon} onChange={handleChange}
                            check={e => e}/>
                <InputField type={Type.Number} name="head.toothCount" object={dragon} onChange={handleChange}/>
            </OptionalComponent>
            <OptionalComponent name="убийцу"
                               values={data.map(d => d.killer)} setId={getHandler("killer")}>
                <InputField type={Type.String} name="killer.name" object={dragon} onChange={handleChange}
                            check={e => e && e.length > 0}/>
                <InputField type={Type.Enum} name="killer.eyeColor" object={dragon} onChange={handleChange}
                            values={COLOR_VALUES} check={e => Boolean(e)}/>
                <InputField type={Type.Enum} name="killer.hairColor" object={dragon} onChange={handleChange}
                            values={COLOR_VALUES}/>
                <InputField type={Type.Integer} name="killer.height" object={dragon} onChange={handleChange}
                            check={e => e > 0}/>
                <InputField type={Type.Number} name="killer.weight" object={dragon} onChange={handleChange}
                            check={e => e > 0}/>
                <InputField type={Type.String} name="killer.passportID" object={dragon} onChange={handleChange}
                            check={e => e && e.length >= 10}/>
                <InputField type={Type.Enum} name="killer.nationality" object={dragon} onChange={handleChange}
                            values={COUNTRY_VALUES}/>
                <OptionalComponent name="локацию"
                                   values={data.map(d => d.killer.location)} setId={getHandler("killer.location")}>
                    <InputField type={Type.Number} name="killer.location.x" object={dragon} onChange={handleChange}/>
                    <InputField type={Type.Integer} name="killer.location.y" object={dragon} onChange={handleChange}/>
                    <InputField type={Type.Number} name="killer.location.z" object={dragon} onChange={handleChange}/>
                    <InputField type={Type.String} name="killer.location.name" object={dragon} onChange={handleChange}
                                check={e => !e || e.length <= 401}/>
                </OptionalComponent>
            </OptionalComponent>
            <div className="justify">
                <button onClick={onSubmit} className="rounded full">Отправить</button>
                <button onClick={onGenerate} className="rounded margin">Сгенерировать</button>
            </div>

            </div>
        </div>
    );
};

function getRandomValue(array) {
    return array[Math.floor(Math.random() * array.length)];
}

function getRandomDragon() {
    return {
        name: getRandomString(),
        coordinates: {
            x: Math.floor(Math.random() * 235), // случайное значение от 0 до 235
            y: Math.floor(Math.random() * 811)  // случайное значение от 0 до 811
        },
        cave: {
            depth: Math.floor(Math.random() * 100), // случайное значение глубины
            numberOfTreasures: Math.floor(Math.random() * 100) + 1 // случайное значение сокровищ или null
        },
        age: Math.floor(Math.random() * 100) + 1, // случайный возраст от 1 до 100
        color: getRandomValue(COLOR_VALUES), // случайный цвет
        type: getRandomValue(DRAGON_TYPE_VALUES), // случайный тип или null
        character: getRandomValue(DRAGON_CHARACTER_VALUES), // фиксированное значение
        head: {
            size: Math.floor(Math.random() * 10) + 1, // случайный размер головы от 1 до 10
            eyesCount: Math.floor(Math.random() * 5) + 1, // случайное количество глаз от 1 до 5
            toothCount: Math.floor(Math.random() * 20) + 1 // случайное количество зубов от 1 до 20
        },
        killer: {
            name: getRandomString(),
            eyeColor: getRandomValue(COLOR_VALUES), // фиксированный цвет глаз
            hairColor: getRandomValue(COLOR_VALUES), // фиксированный цвет волос
            location: {
                x: Math.floor(Math.random() * 100), // случайное значение координаты X
                y: Math.floor(Math.random() * 100), // случайное значение координаты Y
                z: Math.floor(Math.random() * 100), // случайное значение координаты Y
                name: getRandomString() // фиксированное название локации
            },
            height: Math.floor(Math.random() * 100) + 50, // случайный рост от 1.5 до 3 метров
            weight: Math.floor(Math.random() * 100) + 50, // случайный вес от 50 до 150 кг
            passportID: "1234567890", // фиксированный паспорт ID
            nationality: getRandomValue(COUNTRY_VALUES) // фиксированная национальность
        }
    };
}

function getRandomString() {
    return Math.random().toString(36).slice(2)
}
