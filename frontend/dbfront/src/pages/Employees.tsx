import React from "react";
import {inject, observer} from "mobx-react";
import PersonStore, {Person} from "../stores/PersonStore";
import CountryStore, {Country} from "../stores/CountryStore";
import EmployeeStatusTypeStore, {EmployeeStatusType} from "../stores/EmployeeStatusTypeStore";
import {Button, Card, Col, Container, Dropdown, DropdownButton, Form, Modal, Row} from "react-bootstrap";
import EmployeeStore, {Employee, PersonAsEmployee} from "../stores/EmployeeStore";
import {AllEmployeesList} from "../components";

interface EmployeesProps {
    personStore?: PersonStore,
    countryStore?: CountryStore,
    employeeStatusTypeStore?: EmployeeStatusTypeStore,
    employeeStore?: EmployeeStore
}

interface State {
    showCreateNewPersonModal: boolean,
    newPersonNationalIdCode: string,
    newPersonCountryCode: string,
    newPersonEmail: string,
    newPersonBirthdate: string,
    newPersonGivenName: string,
    newPersonSurname: string,
    newPersonAddress: string,
    currentEmployeePersonName: string,
    currentEmployeePerson_id: string|null,
    currentEmployeeStatusTypeCode: number,
    currentEmployeeMentorId: string|null
    currentEmployeeMentorName: string
}

@inject('personStore', 'countryStore', 'employeeStatusTypeStore', 'employeeStore')
@observer
class Employees extends React.Component<EmployeesProps, State> {

    constructor(props) {
        super(props);
        this.state = {
            showCreateNewPersonModal: false,
            newPersonCountryCode: '(select country)',
            newPersonNationalIdCode: '',
            newPersonEmail: '',
            newPersonBirthdate: '',
            newPersonGivenName: '',
            newPersonSurname: '',
            newPersonAddress: '',
            currentEmployeePersonName: '(choose person)',
            currentEmployeePerson_id: null,
            currentEmployeeStatusTypeCode: 1,
            currentEmployeeMentorId: null,
            currentEmployeeMentorName: '(choose mentor)'
        }
    }

    public componentDidMount() {
        this.props.countryStore?.getCountries();
        this.props.employeeStatusTypeStore?.getEmployeeStatusTypes();
        this.props.personStore?.getPersons();
        this.props.employeeStore?.getEmployees();
    }

    private resetNewPersonStateVariables() {
        this.setState({newPersonCountryCode: '(select country)'});
        this.setState({newPersonNationalIdCode: ''})
        this.setState({newPersonEmail: ''})
        this.setState({newPersonBirthdate: ''})
        this.setState({newPersonGivenName: ''})
        this.setState({newPersonSurname: ''})
        this.setState({newPersonSurname: ''})
    }

    private resetCurrentEmployeeStateVariables() {
        this.setState({currentEmployeePersonName: '(choose person)'})
        this.setState({currentEmployeePerson_id: null})
        this.setState({currentEmployeeStatusTypeCode: 1})
        this.setState({currentEmployeeMentorId: null})
        this.setState({currentEmployeeMentorName: '(choose mentor)'})
    }

    private handleAddNewEmployee() {
        if (this.state.currentEmployeePerson_id !== null) {
            const newEmployee: Employee = {
                isik_id: this.state.currentEmployeePerson_id,
                tootaja_seisundi_liik_kood: this.state.currentEmployeeStatusTypeCode
            }
            if (this.state.currentEmployeeMentorId !== null) {
                newEmployee.mentor_id = this.state.currentEmployeeMentorId;
            }
            this.props.employeeStore?.addEmployee(newEmployee).then(this.props.employeeStore?.getEmployees);
        } else {
            const newPerson: Person = {
                riik_kood: this.state.newPersonCountryCode,
                isikukood: this.state.newPersonNationalIdCode,
                e_meil: this.state.newPersonEmail,
                synni_kp: this.state.newPersonBirthdate,
            };
            if (this.state.newPersonGivenName !== '') {
                newPerson.eesnimi = this.state.newPersonGivenName;
            }
            if (this.state.newPersonSurname !== '') {
                newPerson.perenimi = this.state.newPersonSurname;
            }
            if (this.state.newPersonAddress !== '') {
                newPerson.elukoht = this.state.newPersonAddress;
            }
            const savedPersonPromise: Promise<Person> | undefined = this.props.personStore?.addPerson(newPerson);
            savedPersonPromise?.then(savedPerson => {
                const newEmployee: Employee = {
                    isik_id: savedPerson._id!!,
                    tootaja_seisundi_liik_kood: this.state.currentEmployeeStatusTypeCode
                }
                if (this.state.currentEmployeeMentorId !== null) {
                    newEmployee.mentor_id = this.state.currentEmployeeMentorId;
                }
                this.props.employeeStore?.addEmployee(newEmployee).then(this.props.employeeStore?.getEmployees);
            })
        }

        //------
        this.resetNewPersonStateVariables();
        this.resetCurrentEmployeeStateVariables();
    }

    private handleShowCreateNewPersonButtonClick() {
        this.resetNewPersonStateVariables();
        this.resetCurrentEmployeeStateVariables()
        this.setState({showCreateNewPersonModal: true});
    }

    private handleCloseAndSetCreateNewPersonButtonClick() {
        //handleAddPersonButtonClick();
        this.setState({currentEmployeePersonName: this.state.newPersonGivenName + ' ' + this.state.newPersonSurname})
        this.setState({showCreateNewPersonModal: false})
    };

    private handleCloseAndDiscardCreateNewPersonButtonClick() {
        //handleAddPersonButtonClick();
        this.setState({currentEmployeePersonName: this.state.newPersonGivenName + ' ' + this.state.newPersonSurname})
        this.setState({showCreateNewPersonModal: false})
        this.resetNewPersonStateVariables();
    };

    public render() {
        const countries: Country[] = this.props.countryStore!!.countries;
        const persons: Person[] = this.props.personStore!!.persons;
        const employeeStatusTypes: EmployeeStatusType[] = this.props.employeeStatusTypeStore!!.employeeStatusTypes;
        const personsAsEmployees: PersonAsEmployee[] = this.props.employeeStore!!.personsAsEmployees;


        return (
            <div>
                <h1>Employees page</h1>
                <hr/>

                <Container fluid>
                    <h3>Add new employee:</h3>
                    <h5 className='mt-3'>Choose person:</h5>
                    <Row>
                        <Col>
                            <Row>
                                <Col>
                                    <Button variant="primary" onClick={ () => this.handleShowCreateNewPersonButtonClick()}>
                                        Create new person
                                    </Button>
                                </Col>
                                <Modal show={this.state.showCreateNewPersonModal}>
                                    <Modal.Header>
                                        <Modal.Title>Modal heading</Modal.Title>
                                    </Modal.Header>
                                    <Modal.Body>
                                        <Card>
                                            <Card.Title>Create new person:</Card.Title>
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
                                                            }}
                                                        />
                                                    </Form.Group>
                                                    <Form.Group className="mb-3" controlId="addFirstName">
                                                        <Form.Label>Given name:</Form.Label>
                                                        <Form.Control
                                                            placeholder="Enter given name (optional, at least given name or surname must be set)"
                                                            onChange={(e) => this.setState({newPersonGivenName: e.target.value})}/>
                                                    </Form.Group>
                                                    <Form.Group className="mb-3" controlId="addLastName">
                                                        <Form.Label>Surname:</Form.Label>
                                                        <Form.Control
                                                            placeholder="Enter surname (optional, at least given name or surname must be set)"
                                                            onChange={(e) => this.setState({newPersonSurname: e.target.value})}/>
                                                    </Form.Group>
                                                    <Form.Group className="mb-3" controlId="addAddress">
                                                        <Form.Label>Address:</Form.Label>
                                                        <Form.Control
                                                            placeholder="Enter address (optional)"
                                                            onChange={(e) => this.setState({newPersonAddress: e.target.value})}/>
                                                    </Form.Group>
                                                </Form>
                                            </Card.Body>
                                        </Card>
                                    </Modal.Body>
                                    <Modal.Footer>
                                        <Button variant="secondary" onClick={() => this.handleCloseAndDiscardCreateNewPersonButtonClick()}>
                                            Discard
                                        </Button>
                                        <Button variant="primary" onClick={() => this.handleCloseAndSetCreateNewPersonButtonClick()}>
                                            Set person information
                                        </Button>
                                    </Modal.Footer>
                                </Modal>

                                <Col>
                                    <p>or choose person</p>
                                </Col>

                                <Col>
                                    <Dropdown className="d-inline mx-2">
                                        <Dropdown.Toggle id="dropdown-autoclose-true">
                                            Choose person
                                        </Dropdown.Toggle>
                                        <Dropdown.Menu>
                                            {persons.map((person) => (
                                                <Dropdown.Item onClick={() => {
                                                    this.setState({currentEmployeePersonName: person.eesnimi + ' ' + person.perenimi})
                                                    this.setState( {currentEmployeePerson_id: person._id!!})
                                                }}>
                                                    {person.eesnimi + ' ' + person.perenimi}
                                                </Dropdown.Item>
                                            ))}
                                        </Dropdown.Menu>
                                    </Dropdown>
                                </Col>
                            </Row>

                        </Col>

                        <Col>
                            <Row>
                                <Col>
                                    Current person: <h6>{this.state.currentEmployeePersonName}</h6>
                                </Col>
                            </Row>
                        </Col>
                    </Row>

                    <h5 className='mt-4'>Employee info:</h5>
                    <Row>
                        <Col>
                            <Row>
                                <Col>Employee status type:</Col>
                                <Col>
                                    <Dropdown className="d-inline mx-2">
                                        <Dropdown.Toggle id="dropdown-autoclose-true">
                                            {this.state.currentEmployeeStatusTypeCode}
                                        </Dropdown.Toggle>
                                        <Dropdown.Menu>
                                            {employeeStatusTypes.map((statusType) => (
                                                <Dropdown.Item onClick={() => {this.setState({currentEmployeeStatusTypeCode: statusType.tootaja_seisundi_liik_kood})}}>
                                                    {statusType.tootaja_seisundi_liik_kood + ' - ' + statusType.nimetus}
                                                </Dropdown.Item>
                                            ))}
                                        </Dropdown.Menu>
                                    </Dropdown>
                                </Col>
                            </Row>
                        </Col>
                        <Col>
                            <Row>
                                <Col>Employee mentor (optional):</Col>
                                <Col>
                                    <Dropdown className="d-inline mx-2">
                                        <Dropdown.Toggle id="dropdown-autoclose-true">
                                            {this.state.currentEmployeeMentorName}
                                        </Dropdown.Toggle>
                                        <Dropdown.Menu>
                                            {personsAsEmployees.map((employee) => (
                                                <Dropdown.Item onClick={() => {
                                                    this.setState({currentEmployeeMentorId: employee.person_id});
                                                    this.setState({currentEmployeeMentorName: employee.personGivenName + ' ' + employee.personSurname});
                                                }}>
                                                    {employee.personGivenName + ' ' + employee.personSurname}
                                                </Dropdown.Item>
                                            ))}
                                        </Dropdown.Menu>
                                    </Dropdown>
                                </Col>
                            </Row>
                        </Col>
                    </Row>

                    <Row className='mt-4'>
                        <Button className={'ml-5'} variant="success" onClick={() => this.handleAddNewEmployee()}>Add new employee</Button>
                    </Row>
                </Container>

                <hr/>

                <AllEmployeesList/>

            </div>
        );
    }
}

export default Employees