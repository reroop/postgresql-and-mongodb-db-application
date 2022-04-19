import React from "react";
import {NavLink} from 'react-router-dom';

const Navigation = () => (
    <div className="navigation">
        <nav className="navbar navbar-expand navbar-dark bg-dark">
            <div className="container">
                <NavLink className="navbar-brand" to="/">
                    DbApplication frontend
                </NavLink>
                <div>
                    <ul className="navbar-nav ml-auto">
                        <li className="nav-item">
                            <NavLink className="nav-link" to="/">
                                Home
                            </NavLink>
                        </li>
                        <li className="nav-item">
                            <NavLink className="nav-link" to="/employees">
                                Employees
                            </NavLink>
                        </li>
                        <li className="nav-item">
                            <NavLink className="nav-link" to="/occupations">
                                Occupations
                            </NavLink>
                        </li>
                        <li className="nav-item">
                            <NavLink className="nav-link" to="/countries">
                                Countries
                            </NavLink>
                        </li>
                        <li className="nav-item">
                            <NavLink className="nav-link" to="/personStatusTypes">
                                Person status types
                            </NavLink>
                        </li>
                        <li className="nav-item">
                            <NavLink className="nav-link" to="/employeeStatusTypes">
                                Employee status types
                            </NavLink>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>
    </div>
);


export default Navigation;