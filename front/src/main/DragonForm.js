import React from 'react';

export default function DragonForm({dragon, setDragon, onSubmit}) {

    const handleChange = (e) => {
        const {name, value} = e.target;
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

    const onGenerate = e => {
        setDragon({...dragon, ...getRandomDragon()});
    }

    return (
        <div className="container">
            <div>
                <label>Name:</label>
                <input type="text" name="name" value={dragon.name} onChange={handleChange}/>
            </div>
            <div>
                <label>Coordinates X:</label>
                <input type="number" name="coordinates.x" value={dragon.coordinates.x} onChange={handleChange}/>
            </div>
            <div>
                <label>Coordinates Y:</label>
                <input type="number" name="coordinates.y" value={dragon.coordinates.y} onChange={handleChange}/>
            </div>
            <div>
                <label>Cave Depth:</label>
                <input type="number" name="cave.depth" value={dragon.cave.depth} onChange={handleChange}/>
            </div>
            <div>
                <label>Number of Treasures:</label>
                <input type="number" name="cave.numberOfTreasures" value={dragon.cave.numberOfTreasures}
                       onChange={handleChange}/>
            </div>
            <div>
                <label>Age:</label>
                <input type="number" name="age" value={dragon.age} onChange={handleChange}/>
            </div>
            <div>
                <label>Color:</label>
                <select name="color" value={dragon.color} onChange={handleChange}>
                    <option value="RED">RED</option>
                    <option value="BLUE">BLUE</option>
                    <option value="WHITE">WHITE</option>
                </select>
            </div>
            <div>
                <label>Type:</label>
                <select name="type" value={dragon.type} onChange={handleChange}>
                    <option value="">Select Type</option>
                    <option value="WATER">WATER</option>
                    <option value="UNDERGROUND">UNDERGROUND</option>
                </select>
            </div>
            <div>
                <label>Character:</label>
                <select name="character" value={dragon.character} onChange={handleChange}>
                    <option value="CUNNING">CUNNING</option>
                    <option value="CHAOTIC">CHAOTIC</option>
                    <option value="WISE">WISE</option>
                </select>
            </div>
            <div>
                <label>Head Size:</label>
                <input type="number" name="head.size" value={dragon.head.size} onChange={handleChange}/>
            </div>
            <div>
                <label>Eyes Count:</label>
                <input type="number" name="head.eyesCount" value={dragon.head.eyesCount} onChange={handleChange}/>
            </div>
            <div>
                <label>Tooth Count:</label>
                <input type="number" name="head.toothCount" value={dragon.head.toothCount} onChange={handleChange}/>
            </div>
            <div>
                <label>Killer Name:</label>
                <input type="text" name="killer.name" value={dragon.killer.name} onChange={handleChange}/>
            </div>
            <div>
                <label>Killer Eye Color:</label>
                <select name="killer.eyeColor" value={dragon.killer.eyeColor} onChange={handleChange}>
                    <option value="BLUE">BLUE</option>
                    <option value="GREEN">GREEN</option>
                    <option value="BROWN">BROWN</option>
                </select>
            </div>
            <div>
                <label>Killer Hair Color:</label>
                <input type="text" name="killer.hairColor" value={dragon.killer.hairColor} onChange={handleChange}/>
            </div>
            <div>
                <label>Killer Height:</label>
                <input type="number" name="killer.height" value={dragon.killer.height} onChange={handleChange}/>
            </div>
            <div>
                <label>Killer Weight:</label>
                <input type="number" name="killer.weight" value={dragon.killer.weight} onChange={handleChange}/>
            </div>
            <div>
                <label>Killer Passport ID:</label>
                <input type="text" name="killer.passportID" value={dragon.killer.passportID} onChange={handleChange}/>
            </div>
            <div>
                <label>Killer Nationality:</label>
                <select name="killer.nationality" value={dragon.killer.nationality} onChange={handleChange}>
                    <option value="">Select Nationality</option>
                    <option value="USA">USA</option>
                    <option value="GERMANY">GERMANY</option>
                    <option value="FRANCE">FRANCE</option>
                    <option value="VATICAN">VATICAN</option>
                </select>
            </div>
            <div>
                <label>Killer Location X:</label>
                <input type="number" name="killer.location.x" value={dragon.killer.location.x} onChange={handleChange}/>
            </div>
            <div>
                <label>Killer Location Y:</label>
                <input type="number" name="killer.location.y" value={dragon.killer.location.y} onChange={handleChange}/>
            </div>
            <div>
                <label>Killer Location Name:</label>
                <input type="text" name="killer.location.name" value={dragon.killer.location.name}
                       onChange={handleChange}/>
            </div>
            <div className="justify">
                <button onClick={onSubmit} className="rounded full">Отправить</button>
                <button onClick={onGenerate} className="rounded margin">Сгенерировать</button>
            </div>
        </div>
    );
};

function getRandomDragon() {
    return {
        name: getRandomString(),
        coordinates: {
            x: Math.random() * 235, // случайное значение от 0 до 235
            y: Math.random() * 811  // случайное значение от 0 до 811
        },
        cave: {
            depth: Math.random() * 100, // случайное значение глубины
            numberOfTreasures: Math.floor(Math.random() * 100) + 1 // случайное значение сокровищ или null
        },
        age: Math.floor(Math.random() * 100) + 1, // случайный возраст от 1 до 100
        color: Math.random() > 0.5 ? 'RED' : (Math.random() > 0.5 ? 'BLUE' : 'WHITE'), // случайный цвет
        type: Math.random() > 0.5 ? (Math.random() > 0.5 ? 'WATER' : 'UNDERGROUND') : null, // случайный тип или null
        character: 'CUNNING', // фиксированное значение
        head: {
            size: Math.floor(Math.random() * 10) + 1, // случайный размер головы от 1 до 10
            eyesCount: Math.floor(Math.random() * 5) + 1, // случайное количество глаз от 1 до 5
            toothCount: Math.floor(Math.random() * 20) + 1 // случайное количество зубов от 1 до 20
        },
        killer: {
            name: getRandomString(),
            eyeColor: 'BLUE', // фиксированный цвет глаз
            hairColor: 'BROWN', // фиксированный цвет волос
            location: {
                x: Math.floor(Math.random() * 100), // случайное значение координаты X
                y: Math.floor(Math.random() * 100), // случайное значение координаты Y
                name: getRandomString() // фиксированное название локации
            },
            height: Math.random() * 2 + 1.5, // случайный рост от 1.5 до 3 метров
            weight: Math.floor(Math.random() * 100) + 50, // случайный вес от 50 до 150 кг
            passportID: "1234567890", // фиксированный паспорт ID
            nationality: 'USA' // фиксированная национальность
        }
    };
}

function getRandomString() {
    return Math.random().toString(36).slice(2)
}
