import './App.css';
import LoginAppMain, {fetchLoggedAs} from "./login/LoginMain";
import {createBrowserRouter, Link, Outlet, RouterProvider} from "react-router-dom";
import MainPage from "./main/MainPage";
import Header from "./main/Header";

export default function App() {

    const router = createBrowserRouter([{
        path: "/",
        element: <Header/>,
        loader: fetchLoggedAs,
        children: [{
            index: true,
            element: <MainPage/>
        }, {
            path: "login",
            element: <LoginAppMain/>,
        }]
    }]);

  return (
    <div className="App">
        <RouterProvider router={router}/>
    </div>
  );
}

