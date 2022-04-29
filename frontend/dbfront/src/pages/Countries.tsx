import React from "react";
import {inject, observer} from 'mobx-react';
import CountryStore, {Country} from "../stores/CountryStore";
import {Button, Card, Form, Table} from "react-bootstrap";

interface CountriesProps {
    countryStore?: CountryStore;
}

interface State {
    newCountryCode: string | null,
    newCountryName: string | null
}

@inject('countryStore')
@observer
class Countries extends React.Component<CountriesProps, State> {

    constructor(props) {
        super(props);
        this.state = {
            newCountryCode: null,
            newCountryName: null
        }
    }

    public componentDidMount() {
        this.props.countryStore?.getCountries();
    }

    private handleAddCountryClick() {
        /*
        if (this.state.newCountryCode == '' || this.state.newCountryName == '') {
            this.setState({newCountryCode: '', newCountryName: ''});
            return;
        }

         */
        const newCountry: Country = {
            country_code: this.state.newCountryCode,
            name: this.state.newCountryName
        };
        this.props.countryStore!!.addCountry(newCountry).then((e) => {
            //this.setState({newCountryCode: null, newCountryName: null});
            }
        );
    }

    public render() {
        const countryStore = this.props.countryStore!!;
        const countries: Country[] = countryStore.countries;

        return (
            <div>
                <h1 className="font-weight-heavy">Countries page</h1>

                <Card style={{width: '48rem'}} className={'m-4'}>
                    <Card.Title>Add new country:</Card.Title>
                    <Card.Body>
                        <Form>
                            <Form.Group className="mb-3" controlId="addCountryCode">
                                <Form.Label>Country code *</Form.Label>
                                <Form.Control
                                    placeholder="Enter country code"
                                    value={this.state.newCountryCode!!}
                                    onChange={(e) => this.setState({newCountryCode: e.target.value == '' ? null : e.target.value})}
                                />
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="addCountryName">
                                <Form.Label>Country name *</Form.Label>
                                <Form.Control
                                    placeholder="Enter country name"
                                    value={this.state.newCountryName!!}
                                    onChange={(e) =>  this.setState({newCountryName: e.target.value == '' ? null : e.target.value})}/>
                            </Form.Group>
                            <Button variant="success" onClick={() => this.handleAddCountryClick()}>Add new country</Button>
                        </Form>

                    </Card.Body>
                </Card>

                <div className={'m-4'}>
                    <h3 className="font-weight-heavy">All countries:</h3>
                    <Table striped bordered hover responsive={true} title={"Countries"}>
                        <thead>
                        <tr>
                            <th>Country code</th>
                            <th>Name</th>
                        </tr>
                        </thead>
                        <tbody>
                        {countries?.map((country) => (
                            <tr key={country.country_code}>
                                <td>
                                    {country.country_code}
                                </td>
                                <td>
                                    {country.name}
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </Table>
                </div>

            </div>

        );
    }
}

export default Countries;