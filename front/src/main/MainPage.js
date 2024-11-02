import {request} from "../Util";

export default function MainPage() {

    request("api/get").then(console.log).catch(console.error);

    return (<div/>);
}
