import React from "react";
import {inject, observer} from "mobx-react";
import PersonStore, {Person} from "../stores/PersonStore";
import {Button, Card, Form, FormControl, InputGroup, Table} from "react-bootstrap";

interface PersonsProps {
    personStore?: PersonStore;
}

@inject('personStore')
@observer
class Persons extends React.Component<PersonsProps> {

    public componentDidMount() {
        this.props.personStore?.getPersons();
    }

    public render() {
        const personStore = this.props.personStore!!;
        const persons: Person[] = personStore.persons;

        let newPersonNationalIdCode: string = '';
        let newPersonCountryCode: string = '';
        let newPersonEmail: string = '';
        let newPersonBirthdate: string = '';
        let newPersonFirstName: string = '';
        let newPersonLastName: string = '';
        let newPersonAddress: string = '';

        const handleEditPersonButtonClick = (person: Person) => {
            console.log(person);
            personStore.updatePerson(person);
        }

        const handleDeletePersonButtonClick = (person_id: string | undefined) => {
            console.log(person_id);
        }

        const handleAddPersonButtonClick = () => {
            console.log("add person clicked");
        }

        const tempDate: Date = new Date('1995-05-15');

        return (
            <div>
                <h1 className="font-weight-heavy">Persons page</h1>

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
                                            placeholder={"Birthdate (dd/mm/yyyy)"}
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
                                            value={new Date(person.reg_aeg).toLocaleDateString("sv-SE")}/>
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