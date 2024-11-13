import React, {useState} from 'react';
import ButtonCheckbox from "./ButtonCheckbox";

export default function OptionalComponent({children, values = [], setId, name}) {
    const mapped = values.reduce((acc, item) => {
        acc[item.id] = item; // Устанавливаем id как ключ, а остальные поля как значение
        return acc; // Возвращаем аккумулятор для следующей итерации
    }, {});
    const [isChecked, setIsChecked] = useState(false);
    const [value, setValue] = useState({});

    const handleCheckboxChange = () => {
        let willChecked = !isChecked && values.length > 0;
        setIsChecked(willChecked);
        setId(willChecked ? null : value.id);
    };
    let choose_element = null;
    if (isChecked) {
        const handleValueChange = e => {
            let newValue = mapped[e.target.value];
            setValue(newValue);
            setId(newValue.id);
        };
        choose_element =
            <select className="input box rounded"
                    value={value.id} onChange={handleValueChange}>
                {Object.entries(mapped).map(([id, item]) =>
                    <option value={id} key={item.id}>{JSON.stringify(item)}</option>
                )}
            </select>;
    }

    return (
        <div className="container">
            <p><b>Укажите  {name}</b></p>
            <label>Использовать существующий: </label>
            <ButtonCheckbox checked={isChecked} onChange={handleCheckboxChange}/>
            <div>{isChecked ? choose_element : children}</div>
        </div>
    );
};