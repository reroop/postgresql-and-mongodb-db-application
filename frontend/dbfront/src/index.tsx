import React from 'react';
import {render} from 'react-dom'
import './index.css';
import {Navigation} from "./components";
import {Provider} from "mobx-react";
import {Countries, Home, Occupations, EmployeeStatusTypes, Employees, EmployeeDetails} from "./pages";
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
                        <Route exact path={"/employeeStatusTypes"}>
                            <EmployeeStatusTypes/>
                        </Route>
                        <Route exact path={"/employees"}>
                            <Employees/>
                        </Route>
                        <Route exact path={"/employees/:id"}>
                            <EmployeeDetails/>
                        </Route>
                        <Route path={"/*"}>
                            {'Not found!'}
                        </Route>
                    </Switch>
                </BrowserRouter>
            </Provider>
        );
    }
}

render(<App/>, document.getElementById("root"));
