import './App.css';
import LoginAppMain, {fetchLoggedAs} from "./login/LoginMain";
import {createBrowserRouter, Link, Outlet, RouterProvider} from "react-router-dom";
import MainPage from "./main/MainPage";
import Header from "./main/Header";
import CreationPage from "./main/CreationPage";
import EditionPage from "./main/EditionPage";
import SpecialPage from "./main/SpecialPage";

export default function App() {

    const router = createBrowserRouter([{
        path: "/",
        element: <Header/>,
        children: [{
            index: true,
            element: <MainPage/>
        }, {
            path: "login",
            element: <LoginAppMain/>
        }, {
            path: "new",
            element: <CreationPage/>
        }, {
            path: "edit",
            element: <EditionPage/>
        }, {
            path: "special",
            element: <SpecialPage/>
        }]
    }]);

  return (
    <div className="App">
        <RouterProvider router={router}/>
    </div>
  );
}

