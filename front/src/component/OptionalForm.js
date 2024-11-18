import React, {useEffect, useState} from 'react';
import ButtonCheckbox from "./ButtonCheckbox";

export default function OptionalComponent({
                                              children,
                                              values = [],
                                              onChange,
                                              name,
                                              initial,
                                              subSelected = false,
                                              nullable = false
                                          }) {
    const mapped = values.reduce((acc, item) => {
        if (item) acc[item.id] = item; // Устанавливаем id как ключ, а остальные поля как значение
        return acc; // Возвращаем аккумулятор для следующей итерации
    }, {});
    const firstValid = values.find(x => x);
    const [isExistingUsed, setIsExistingUsed] = useState(subSelected);
    const [isNull, setIsNull] = useState(!initial);
    const [value, setValue] = useState(initial || firstValid || {});

    useEffect(() => {
        if (isNull) onChange(null);
        else if (isExistingUsed) onChange(value.id ? value : firstValid);
        else onChange({...value, id: null});
    }, [isExistingUsed, value, isNull]);

    const handleNullChange = () => setIsNull(!isNull);
    const handleCheckboxChange = () => setIsExistingUsed(!isExistingUsed && firstValid);
    const handleValueChange = e => setValue(mapped[e.target.value]);

    const set_null_element = nullable &&
        <div>
            <label>Установить NULL: </label>
            <ButtonCheckbox checked={isNull} onChange={handleNullChange}/>
        </div>;

    const choose_element = isExistingUsed &&
        <select className="input box rounded"
                value={value.id || firstValid.id} onChange={handleValueChange}>
            {Object.entries(mapped).map(([id, item]) =>
                <option value={id} key={id}>{JSON.stringify(item)}</option>
            )}
        </select>;

    const form_element = !isNull &&
        <>
            <label>Использовать существующий: </label>
            <ButtonCheckbox checked={isExistingUsed} onChange={handleCheckboxChange}/>
            <div>{isExistingUsed ? choose_element : initial && children}</div>
        </>;

    return (
        <div className="container">
            <p><b>Укажите {name}</b></p>
            {set_null_element}
            {form_element}
        </div>
    );
};
