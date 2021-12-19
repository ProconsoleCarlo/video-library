import React from 'react';
import './App.css';
import {PageView} from "./ui/PageView";

const App: React.FC = () => {
  return (
    <div className="App">
      <header className="App-header">
        <h1>Video library</h1>
        <PageView/>
      </header>
    </div>
  );
}

export default App;
