import React from 'react';
import {render} from 'react-dom'
import './index.css';
import {Navigation, Footer} from "./components";
import {Provider} from "mobx-react";
import {Countries, Home, Occupations} from "./pages";
import {BrowserRouter, Route, Switch} from "react-router-dom";
import {RootStore} from "./stores/RootStore";

interface MockProps {
}

class App extends React.Component<any, any> {
    private readonly rootStore: RootStore;

    constructor(mockProps: MockProps) {
        super(mockProps);
        this.rootStore = new RootStore();
    }

    public render() {
        return (
            <Provider {...this.rootStore}>
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
                </BrowserRouter>
            </Provider>
        );
    }
}

render(<App/>, document.getElementById("root"));
