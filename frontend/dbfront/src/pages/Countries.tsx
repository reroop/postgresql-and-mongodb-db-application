import React from "react";
import {inject, observer} from 'mobx-react';
import CountryStore, {Country} from "../stores/CountryStore";
import {Button, Card, Form, FormControl, InputGroup, Modal, Table} from "react-bootstrap";

interface CountriesProps {
    countryStore?: CountryStore;
}

@inject('countryStore')
@observer
class Countries extends React.Component<CountriesProps> {

    public componentDidMount() {
        this.props.countryStore?.getCountries();
    }

    public render() {
        const countries = this.props.countryStore?.countries;
        const countryStore = this.props.countryStore;

        let newCountryCode: string = '';
        let newCountryName: string = '';

        //const [newCountryName, setNewCountryName] = useState<string>('');

        const handleEditButtonClick = (country: Country) => {
            countryStore?.updateCountry(country);
        };

        const handleDeleteButtonClick = (countryCode: string) => {
            countryStore?.deleteCountry(countryCode);
        };

        const handleAddCountryClick = () => {
            const newCountry: Country = {
                riik_kood: newCountryCode,
                nimetus: newCountryName
            };
            countryStore?.addCountry(newCountry);
            newCountryCode = '';
            newCountryName = '';
        }

        return (
            <div>
                <h1 className="font-weight-heavy">Countries</h1>

                <Card>
                    <Card.Title>Add new country:</Card.Title>
                    <Card.Body>
                        <Form>
                            <Form.Group className="mb-3" controlId="addCountryCode">
                                <Form.Label>Country code:</Form.Label>
                                <Form.Control
                                    placeholder="Enter country code"
                                    onChange={(e) => newCountryCode = e.target.value}
                                />
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="addCountryName">
                                <Form.Label>Country name:</Form.Label>
                                <Form.Control
                                    placeholder="Enter country name"
                                    onChange={(e) => newCountryName = e.target.value}/>
                            </Form.Group>
                            <Button variant="success" onClick={() => handleAddCountryClick()}>Add new country</Button>
                        </Form>

                    </Card.Body>
                </Card>

                <div>
                    <h3 className="font-weight-heavy">All countries:</h3>
                    <Table striped bordered hover responsive={true} title={"Countries"}>
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
                                <td>
                                    <InputGroup className={"mb-3"}>
                                        <FormControl
                                            placeholder={"Country code"}
                                            value={country.riik_kood}
                                            onChange={(e) => country.riik_kood = e.target.value}/>
                                    </InputGroup>
                                </td>
                                <td>
                                    <InputGroup className={"mb-3"}>
                                        <FormControl
                                            placeholder={"Country name"}
                                            value={country.nimetus}
                                            onChange={(e) => country.nimetus = e.target.value}/>
                                    </InputGroup>
                                </td>
                                <td><Button variant="info"
                                            onClick={() => handleEditButtonClick(country)}>Update</Button></td>
                                <td><Button variant="danger"
                                            onClick={() => handleDeleteButtonClick(country.riik_kood)}>Delete</Button>
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