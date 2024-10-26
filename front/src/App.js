import './App.css';
import LoginAppMain from "./login/LoginMain";

export default function App() {
  return (
    <div className="App">
      <header className="container" id="header">
        <p>Лабораторная работа #1 по ИС</p>
        <p>Выполнил Щербинин Эдуард P3314</p>
        <p>Вариант 130343</p>
      </header>
      <LoginAppMain/>
    </div>
  );
}

