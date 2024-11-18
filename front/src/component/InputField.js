import React from "react";

export const Type = {
    String: "text",
    Integer: "integer",
    Number: "number",
    Enum: "enum",
};

function isInt(value) {
    return !isNaN(value) && (function(x) { return (x | 0) === x; })(parseFloat(value))
}

export default function InputField({type, object, name, onChange, values = [], check = e => true, customName}) {
    let value = object, element;
    if (name) {
        let keys = name.split('.');
        while (keys.length) value = value[keys.shift()];
    }
    let valid = check(value);

    if (type === Type.Integer) {
        type = Type.Number;
        valid &= !value || isInt(value);
    }

    if (type === Type.Enum) {
        element = <select className="input box rounded" name={name}
                          value={value || ''} onChange={onChange}>
            <option value={''} key={-1}>Select option</option>
            {values.map((item, i) => <option value={item} key={i}>{item}</option>)}
        </select>;
    } else {
        element = <input className="input box rounded" type={type} name={name}
                         value={value || ''} onChange={onChange} placeholder="-- null --"/>;
    }
    return <div className={"form-input " + (valid ? "" : "red-border")}>
        <label>{customName || name.replaceAll('.', ' ')}: </label>
        {element}
    </div>
}
