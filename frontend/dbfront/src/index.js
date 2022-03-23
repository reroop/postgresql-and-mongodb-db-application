import React from 'react';
import {render} from 'react-dom'
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';


import {Navigation, Footer} from "./components";
import {Countries, Home, Occupations} from "./pages";

import {BrowserRouter, Route, Switch} from "react-router-dom";

const rootElement = document.getElementById("root");
render(
  <BrowserRouter>
      <Navigation/>
      <Switch>
          <Route exact path={"/"}>
              <Home/>
          </Route>
          <Route exact path={"/countries"}>
              <Countries/>
          </Route>
          <Route exact path={"/occupations"}>
              <Occupations/>
          </Route>
          <Route path={"/*"}>
              {'not implemented yet!'}
          </Route>
      </Switch>
      <Footer/>
  </BrowserRouter>,
    rootElement
);

/*
ReactDOM.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
  document.getElementById('root')
);
 */
