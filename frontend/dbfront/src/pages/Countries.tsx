import React from "react";
import {inject, observer} from 'mobx-react';
import CountryStore, {Country} from "../stores/CountryStore";
import {Button, Table} from "react-bootstrap";

interface CountriesProps {
    countryStore?: CountryStore;
}

@inject('countryStore')
@observer
class Countries extends React.Component<CountriesProps> {
    public componentDidMount() {
        this.props.countryStore?.getCountries();
    }

    private renderCountries() {
        const countries = this.props.countryStore?.countries;

        const handleEditButtonClick = (country: Country) => {
            console.log("editCountryButton clicked");
            console.log(country.riik_kood + ", " + country.nimetus);
            //return true;
        };

        const handleDeleteButtonClick = (country: Country) => {
            console.log("deleteCountryButton clicked");
            console.log(country.riik_kood + ", " + country.nimetus);
            //return true;
        };

        return(
            <div>
                <h1 className="font-weight-light">Countries</h1>

                <Table striped bordered hover responsive={true}>
                    <thead>
                    <tr>
                        <th>Country code</th>
                        <th>Name</th>
                        <th>Edit country</th>
                        <th>Delete country</th>
                    </tr>
                    </thead>
                    <tbody>
                    {countries?.map((country) => (
                        <tr key={country.riik_kood}>
                            <td>{country.riik_kood}</td>
                            <td>{country.nimetus}</td>
                            <td><Button variant="info" onClick={ () => handleEditButtonClick(country)}>Edit</Button></td>
                            <td><Button variant="danger" onClick={ () => handleDeleteButtonClick(country)}>Delete</Button></td>
                        </tr>
                    ))}
                    </tbody>
                </Table>
            </div>

        );
    }


    public render() {
        return this.renderCountries();
    }
}


const CountriesX = () => (
    <div className="countries">
        <div className="container">
            <div className="row align-items-center my-5">
                <div className="col-lg-5">
                    <h1 className="font-weight-light">Countries</h1>
                    <p>
                        Here should be stuff for crud-ing countries
                    </p>
                </div>
            </div>
        </div>
    </div>
);

export default Countries;