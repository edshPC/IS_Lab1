
export default function ButtonCheckbox({checked, onChange}) {

    return <button className={"small-button rounded " + (checked ? "selected" : "")} onClick={onChange}/>
}
