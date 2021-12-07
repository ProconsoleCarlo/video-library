import React from 'react';
import './App.css';
import {FilmView} from "./ui/FilmView";

const App: React.FC = () => {
  return (
    <div className="App">
      <header className="App-header">
        <h1>Video library</h1>
        <FilmView/>
      </header>
    </div>
  );
}

export default App;
