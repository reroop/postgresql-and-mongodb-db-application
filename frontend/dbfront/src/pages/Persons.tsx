import React from "react";
import {inject, observer} from "mobx-react";
import PersonStore, {Person} from "../stores/PersonStore";
import {Button, Card, Dropdown, Form, FormControl, InputGroup, Table} from "react-bootstrap";
import CountryStore, {Country} from "../stores/CountryStore";

interface PersonsProps {
    personStore?: PersonStore,
    countryStore?: CountryStore
}

interface State {
    newPersonNationalIdCode: string,
    newPersonCountryCode: string,
    newPersonEmail: string,
    newPersonBirthdate: string,
    newPersonFirstName: string,
    newPersonLastName: string,
    newPersonAddress: string
}

@inject('personStore', 'countryStore')
@observer
class Persons extends React.Component<PersonsProps, State> {

    constructor(props) {
        super(props);
        this.state = {
            newPersonCountryCode: '(select country)',
            newPersonNationalIdCode: '',
            newPersonEmail: '',
            newPersonBirthdate: '',
            newPersonFirstName: '',
            newPersonLastName: '',
            newPersonAddress: ''
        };
    }

    public componentDidMount() {
        this.props.personStore?.getPersons();
        this.props.countryStore?.getCountries();
    }

    public render() {
        const personStore = this.props.personStore!!;
        const persons: Person[] = personStore.persons;
        const countries: Country[] = this.props.countryStore!!.countries;

        const handleEditPersonButtonClick = (person: Person) => {
            personStore.updatePerson(person);
        }

        const handleDeletePersonButtonClick = (person_id: string | undefined) => {
            personStore.deletePerson(person_id!!);
        }

        const handleAddPersonButtonClick = () => {
            const newPerson: Person = {
                riik_kood: this.state.newPersonCountryCode,
                isikukood: this.state.newPersonNationalIdCode,
                e_meil: this.state.newPersonEmail,
                synni_kp: this.state.newPersonBirthdate,
            };
            if (this.state.newPersonFirstName !== '') {
                newPerson.eesnimi = this.state.newPersonFirstName;
            }
            if (this.state.newPersonLastName !== '') {
                newPerson.perenimi = this.state.newPersonLastName;
            }
            if (this.state.newPersonAddress !== '') {
                newPerson.elukoht = this.state.newPersonAddress;
            }

            this.setState({newPersonCountryCode: '(select country)'});
            this.setState({newPersonNationalIdCode: ''})
            this.setState({newPersonEmail: ''})
            this.setState({newPersonBirthdate: ''})
            this.setState({newPersonFirstName: ''})
            this.setState({newPersonLastName: ''})
            this.setState({newPersonLastName: ''})
            personStore.addPerson(newPerson);
        }

        return (
            <div>
                <h1 className="font-weight-heavy">Persons page</h1>

                <Card>
                    <Card.Title>Add new person:</Card.Title>
                    <Card.Body>
                        <Form>
                            <Form.Group className="mb-3" controlId="addCountryCode">
                                <Form.Label>Country code:</Form.Label>
                                <Dropdown className="d-inline mx-2">
                                    <Dropdown.Toggle id="dropdown-autoclose-true">
                                        {this.state.newPersonCountryCode}
                                    </Dropdown.Toggle>
                                    <Dropdown.Menu>
                                        {countries.map((country) => (
                                            <Dropdown.Item onClick={() => {this.setState({newPersonCountryCode: country.riik_kood})}}>
                                                {country.riik_kood + ' - ' + country.nimetus}
                                            </Dropdown.Item>
                                        ))}
                                    </Dropdown.Menu>
                                </Dropdown>
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="addNatIdCode">
                                <Form.Label>Nat. Id. code:</Form.Label>
                                <Form.Control
                                    placeholder="Enter nat. id. code"
                                    onChange={(e) => this.setState({newPersonNationalIdCode: e.target.value})}/>
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="addEmail">
                                <Form.Label>Email:</Form.Label>
                                <Form.Control
                                    placeholder="Enter email"
                                    onChange={(e) => this.setState({newPersonEmail: e.target.value})}/>
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="addBirthdate">
                                <Form.Label>Birthdate:</Form.Label>
                                <Form.Control
                                    type={"date"}
                                    onChange={(e) => {
                                        this.setState({newPersonBirthdate: e.target.value+'T00:00:00'}) //hack for localdatetime in backend
                                        //newPersonBirthdate = e.target.value+'T00:00:00';   //hack for localdatetime in backend
                                    }}
                                />
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="addFirstName">
                                <Form.Label>First name:</Form.Label>
                                <Form.Control
                                    placeholder="Enter first name (optional, at least first or last name must be set)"
                                    onChange={(e) => this.setState({newPersonFirstName: e.target.value})}/>
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="addLastName">
                                <Form.Label>Last name:</Form.Label>
                                <Form.Control
                                    placeholder="Enter last name (optional, at least first or last name must be set)"
                                    onChange={(e) => this.setState({newPersonLastName: e.target.value})}/>
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="addAddress">
                                <Form.Label>Address:</Form.Label>
                                <Form.Control
                                    placeholder="Enter address (optional)"
                                    onChange={(e) => this.setState({newPersonAddress: e.target.value})}/>
                            </Form.Group>
                            <Button variant="success" onClick={() => handleAddPersonButtonClick()}>Add new person</Button>
                        </Form>
                    </Card.Body>
                </Card>

                <div>
                    <h3 className="font-weight-heavy">All persons:</h3>
                    <Table striped bordered hover responsive={true} title={"Persons:"}>
                        <thead>
                        <tr>
                            <th>Person_id</th>
                            <th>Country code</th>
                            <th>National identification code</th>
                            <th>E-mail</th>
                            <th>Birthdate (dd/mm/yyyy)</th>
                            <th>Reg. date (yyyy-mm-dd)</th>
                            <th>First name</th>
                            <th>Last name</th>
                            <th>Address</th>
                            <th>Edit</th>
                            <th>Delete</th>
                        </tr>
                        </thead>
                        <tbody>
                        {persons.map((person) => (
                            <tr key={person._id}>
                                <td>
                                    {person._id}
                                </td>
                                <td>
                                    <InputGroup className={"mb-3"}>
                                        <FormControl
                                            placeholder={"Country code"}
                                            value={person.riik_kood}
                                            onChange={(e) => person.riik_kood = e.target.value}/>
                                    </InputGroup>
                                </td>
                                <td>
                                    <InputGroup className={"mb-3"}>
                                        <FormControl
                                            placeholder={"National identification code"}
                                            value={person.isikukood}
                                            onChange={(e) => person.isikukood = e.target.value}/>
                                    </InputGroup>
                                </td>
                                <td>
                                    <InputGroup className={"mb-3"}>
                                        <FormControl
                                            placeholder={"E-mail"}
                                            value={person.e_meil}
                                            onChange={(e) => person.e_meil = e.target.value}/>
                                    </InputGroup>
                                </td>
                                <td>
                                    <InputGroup className={"mb-3"}>
                                        <FormControl
                                            //placeholder={"Birthdate (dd/mm/yyyy)"}
                                            value={new Date(person.synni_kp).toLocaleDateString("sv-SE")}
                                            type={"date"}
                                            onChange={(e) => {
                                                person.synni_kp = e.target.value+'T00:00:00';   //hack for localdatetime in backend
                                            }}/>
                                    </InputGroup>
                                </td>
                                <td>
                                    <InputGroup className={"mb-3"}>
                                        <FormControl
                                            placeholder={"Registration date (yyyy-mm-dd)"}
                                            type={"date"}
                                            value={new Date(person.reg_aeg!!).toLocaleDateString("sv-SE")}/>
                                    </InputGroup>
                                </td>
                                <td>
                                    <InputGroup className={"mb-3"}>
                                        <FormControl
                                            placeholder={"First name"}
                                            value={person.eesnimi}
                                            onChange={(e) => person.eesnimi = e.target.value}/>
                                    </InputGroup>
                                </td>
                                <td>
                                    <InputGroup className={"mb-3"}>
                                        <FormControl
                                            placeholder={"Last name"}
                                            value={person.perenimi}
                                            onChange={(e) => person.perenimi = e.target.value}/>
                                    </InputGroup>
                                </td>
                                <td>
                                    <InputGroup className={"mb-3"}>
                                        <FormControl
                                            placeholder={"Address"}
                                            value={person.elukoht}
                                            onChange={(e) => person.elukoht = e.target.value}/>
                                    </InputGroup>
                                </td>
                                <td><Button variant="info"
                                            onClick={() => handleEditPersonButtonClick(person)}>Update</Button></td>
                                <td><Button variant="danger"
                                            onClick={() => handleDeletePersonButtonClick(person._id)}>Delete</Button>
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

export default Persons;