import InputField, {Type} from "./InputField";
import OptionalComponent from "./OptionalForm";
import React from "react";
import {COLOR_VALUES, COUNTRY_VALUES} from "./DragonForm";
import {useSelector} from "react-redux";

export default function KillerForm({object, onChange, subSelected}) {
    const data = useSelector(state => state.data) || [];

    return <div>
        <InputField type={Type.String} name="killer.name" object={object} onChange={onChange}
                    check={e => e && e.length > 0}/>
        <InputField type={Type.Enum} name="killer.eyeColor" object={object} onChange={onChange}
                    values={COLOR_VALUES} check={e => Boolean(e)}/>
        <InputField type={Type.Enum} name="killer.hairColor" object={object} onChange={onChange}
                    values={COLOR_VALUES}/>
        <InputField type={Type.Integer} name="killer.height" object={object} onChange={onChange}
                    check={e => e > 0}/>
        <InputField type={Type.Number} name="killer.weight" object={object} onChange={onChange}
                    check={e => e > 0}/>
        <InputField type={Type.String} name="killer.passportID" object={object} onChange={onChange}
                    check={e => e && e.length >= 10}/>
        <InputField type={Type.Enum} name="killer.nationality" object={object} onChange={onChange}
                    values={COUNTRY_VALUES}/>
        <OptionalComponent name="локацию" initial={object.killer?.location} subSelected={subSelected}
                           values={data.map(d => d.killer?.location)} onChange={value => onChange({target: {name: "killer.location", value}})}>
            <InputField type={Type.Number} name="killer.location.x" object={object} onChange={onChange}/>
            <InputField type={Type.Integer} name="killer.location.y" object={object} onChange={onChange}/>
            <InputField type={Type.Number} name="killer.location.z" object={object} onChange={onChange}/>
            <InputField type={Type.String} name="killer.location.name" object={object} onChange={onChange}
                        check={e => !e || e.length <= 401}/>
        </OptionalComponent>
    </div>
}
