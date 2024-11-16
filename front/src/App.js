import './App.css';
import LoginAppMain from "./login/LoginMain";
import {createBrowserRouter, RouterProvider} from "react-router-dom";
import MainPage from "./main/MainPage";
import Header from "./main/Header";
import CreationPage from "./main/CreationPage";
import EditionPage from "./main/EditionPage";
import SpecialPage from "./main/SpecialPage";
import AdminPage from "./main/AdminPage";

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
        }, {
            path: "admin",
            element: <AdminPage/>
        }]
    }]);

  return (
    <div className="App">
        <RouterProvider router={router}/>
    </div>
  );
}

