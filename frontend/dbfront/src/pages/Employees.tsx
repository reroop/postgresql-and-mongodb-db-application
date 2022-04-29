import React from "react";
import {inject, observer} from "mobx-react";
import PersonStore, {Person} from "../stores/PersonStore";
import CountryStore, {Country} from "../stores/CountryStore";
import EmployeeStatusTypeStore, {EmployeeStatusType} from "../stores/EmployeeStatusTypeStore";
import {Button, Card, Col, Dropdown, Form, Modal, Row} from "react-bootstrap";
import EmployeeStore, {Employee, PersonAsEmployee} from "../stores/EmployeeStore";
import {AllEmployeesList} from "../components";
import PersonStatusTypeStore, {PersonStatusType} from "../stores/PersonStatusTypeStore";

interface EmployeesProps {
    personStore?: PersonStore,
    countryStore?: CountryStore,
    employeeStatusTypeStore?: EmployeeStatusTypeStore,
    employeeStore?: EmployeeStore,
    personStatusTypeStore?: PersonStatusTypeStore
}

interface State {
    showCreateNewPersonModal: boolean,
    newPersonNationalIdCode: string,
    newPersonCountryCode: string,
    newPersonStatusTypeCode: number,
    newPersonEmail: string,
    newPersonBirthdate: string,
    newPersonGivenName: string,
    newPersonSurname: string,
    newPersonAddress: string,
    newPersonTelNr: string,
    currentEmployeePersonName: string,
    currentEmployeePerson_id: string | null,
    currentEmployeeStatusTypeCode: number,
    currentEmployeeMentorId: string | null
    currentEmployeeMentorName: string
}

@inject('personStore', 'countryStore', 'employeeStatusTypeStore', 'employeeStore', 'personStatusTypeStore')
@observer
class Employees extends React.Component<EmployeesProps, State> {

    constructor(props) {
        super(props);
        this.state = {
            showCreateNewPersonModal: false,
            newPersonCountryCode: '(select country)',
            newPersonStatusTypeCode: 1,
            newPersonNationalIdCode: '',
            newPersonEmail: '',
            newPersonBirthdate: '',
            newPersonGivenName: '',
            newPersonSurname: '',
            newPersonAddress: '',
            newPersonTelNr: '',
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
        this.props.personStatusTypeStore?.getPersonStatusTypes();
        this.props.personStore?.getPersons();
        this.props.employeeStore?.getEmployees();
    }

    private resetNewPersonStateVariables() {
        this.setState({
            newPersonCountryCode: '(select country)',
            newPersonStatusTypeCode: 1,
            newPersonNationalIdCode: '',
            newPersonEmail: '',
            newPersonBirthdate: '',
            newPersonGivenName: '',
            newPersonSurname: '',
            newPersonAddress: '',
            newPersonTelNr: ''
        });
    }

    private resetCurrentEmployeeStateVariables() {
        this.setState({
            currentEmployeePersonName: '(choose person)',
            currentEmployeePerson_id: null,
            currentEmployeeStatusTypeCode: 1,
            currentEmployeeMentorId: null,
            currentEmployeeMentorName: '(choose mentor)'
        })
    }

    private handleAddNewEmployee() {
        if (this.state.currentEmployeePerson_id !== null) {
            const newEmployee: Employee = {
                person_id: this.state.currentEmployeePerson_id,
                employee_status_type_code: this.state.currentEmployeeStatusTypeCode
            }
            if (this.state.currentEmployeeMentorId !== null) {
                newEmployee.mentor_id = this.state.currentEmployeeMentorId;
            }
            this.props.employeeStore?.addEmployee(newEmployee).then(r => {
                this.props.employeeStore?.getEmployees();
                this.resetNewPersonStateVariables();
                this.resetCurrentEmployeeStateVariables();
            });
        } else {
            const newPerson: Person = {
                country_code: this.state.newPersonCountryCode,
                nat_id_code: this.state.newPersonNationalIdCode,
                person_status_type_code: this.state.newPersonStatusTypeCode,
                e_mail: this.state.newPersonEmail,
                birth_date: this.state.newPersonBirthdate,
            };
            if (this.state.newPersonGivenName !== '') {
                newPerson.given_name = this.state.newPersonGivenName;
            }
            if (this.state.newPersonSurname !== '') {
                newPerson.surname = this.state.newPersonSurname;
            }
            if (this.state.newPersonAddress !== '') {
                newPerson.address = this.state.newPersonAddress;
            }
            if (this.state.newPersonTelNr !== '') {
                newPerson.tel_nr = this.state.newPersonTelNr;
            }
            const savedPersonPromise: Promise<Person> | undefined = this.props.personStore?.addPerson(newPerson);
            savedPersonPromise?.then(savedPerson => {
                const newEmployee: Employee = {
                    person_id: savedPerson._id!!,
                    employee_status_type_code: this.state.currentEmployeeStatusTypeCode
                }
                if (this.state.currentEmployeeMentorId !== null) {
                    newEmployee.mentor_id = this.state.currentEmployeeMentorId;
                }
                this.props.employeeStore?.addEmployee(newEmployee).then(r => {
                    this.props.employeeStore?.getEmployees();
                    this.resetNewPersonStateVariables();
                    this.resetCurrentEmployeeStateVariables();
                })
            })
        }

    }

    private handleShowCreateNewPersonButtonClick() {
        this.resetNewPersonStateVariables();
        this.resetCurrentEmployeeStateVariables()
        this.setState({showCreateNewPersonModal: true});
    }

    private handleCloseAndSetCreateNewPersonButtonClick() {
        this.setState({
            currentEmployeePersonName: this.state.newPersonGivenName + ' ' + this.state.newPersonSurname,
            showCreateNewPersonModal: false
        })
    };

    private handleCloseAndDiscardCreateNewPersonButtonClick() {
        this.setState({
            currentEmployeePersonName: this.state.newPersonGivenName + ' ' + this.state.newPersonSurname,
            showCreateNewPersonModal: false
        })
        this.resetNewPersonStateVariables();
    };

    public render() {
        const countries: Country[] = this.props.countryStore!!.countries;
        const persons: Person[] = this.props.personStore!!.persons;
        const employeeStatusTypes: EmployeeStatusType[] = this.props.employeeStatusTypeStore!!.employeeStatusTypes;
        const personsAsEmployees: PersonAsEmployee[] = this.props.employeeStore!!.personsAsEmployees;
        const personStatusTypes: PersonStatusType[] = this.props.personStatusTypeStore!!.personStatusTypes;

        return (
            <div>
                <h1>Employees page</h1>
                <hr/>

                <Row className={'ml-2'}>
                    <Col>
                        <Card style={{width: '48rem'}}>
                            <Card.Title>Add new employee:</Card.Title>
                            <Card.Body>
                                <h5 className='mt-1'>Choose person:</h5>
                                <Row className={'mt-3'}>
                                    <Col>
                                        <Row>
                                            <Col>
                                                <Button variant="primary"
                                                        onClick={() => this.handleShowCreateNewPersonButtonClick()}>
                                                    Create new person
                                                </Button>
                                            </Col>
                                            <Modal show={this.state.showCreateNewPersonModal}>
                                                <Modal.Header>
                                                    <Modal.Title>Create new person</Modal.Title>
                                                </Modal.Header>
                                                <Modal.Body>
                                                    <Card>
                                                        <Card.Title>Enter info:</Card.Title>
                                                        <Card.Body>
                                                            <Form>
                                                                <Form.Group className="mb-3" controlId="addCountryCode">
                                                                    <Form.Label>Country code *</Form.Label>
                                                                    <Dropdown className="d-inline mx-2">
                                                                        <Dropdown.Toggle id="dropdown-autoclose-true">
                                                                            {this.state.newPersonCountryCode}
                                                                        </Dropdown.Toggle>
                                                                        <Dropdown.Menu>
                                                                            {countries.map((country) => (
                                                                                <Dropdown.Item onClick={() => {
                                                                                    this.setState({newPersonCountryCode: country.country_code!!})
                                                                                }}>
                                                                                    {country.country_code + ' - ' + country.name}
                                                                                </Dropdown.Item>
                                                                            ))}
                                                                        </Dropdown.Menu>
                                                                    </Dropdown>
                                                                </Form.Group>
                                                                <Form.Group controlId="addPersonStatusTypeCode" className={'mt-2'}>
                                                                    <Form.Label>Person status type *</Form.Label>
                                                                    <Dropdown className="d-inline mx-2">
                                                                        <Dropdown.Toggle id="dropdown-autoclose-true">
                                                                            {this.state.newPersonStatusTypeCode}
                                                                        </Dropdown.Toggle>
                                                                        <Dropdown.Menu>
                                                                            {personStatusTypes.map( personStatusType => (
                                                                                <Dropdown.Item
                                                                                    onClick={() => {
                                                                                        this.setState({newPersonStatusTypeCode: personStatusType.person_status_type_code!!});
                                                                                    }}>
                                                                                    {personStatusType.person_status_type_code + ' - ' + personStatusType.name}
                                                                                </Dropdown.Item>
                                                                            ))}
                                                                        </Dropdown.Menu>
                                                                    </Dropdown>
                                                                </Form.Group>
                                                                <Form.Group className="mb-3 mt-3" controlId="addNatIdCode">
                                                                    <Form.Label>Nat. Id. code *</Form.Label>
                                                                    <Form.Control
                                                                        placeholder="Enter nat. id. code"
                                                                        onChange={(e) => this.setState({newPersonNationalIdCode: e.target.value})}/>
                                                                </Form.Group>
                                                                <Form.Group className="mb-3" controlId="addBirthdate">
                                                                    <Form.Label>Birthdate *</Form.Label>
                                                                    <Form.Control
                                                                        type={"date"}
                                                                        onChange={(e) => {
                                                                            this.setState({newPersonBirthdate: e.target.value + 'T00:00:00'}) //hack for localdatetime in backend
                                                                        }}
                                                                    />
                                                                </Form.Group>
                                                                <Form.Group className="mb-3" controlId="addEmail">
                                                                    <Form.Label>Email *</Form.Label>
                                                                    <Form.Control
                                                                        placeholder="Enter email"
                                                                        onChange={(e) => this.setState({newPersonEmail: e.target.value})}/>
                                                                </Form.Group>
                                                                <Form.Group className="mb-3" controlId="addFirstName">
                                                                    <Form.Label>Given name</Form.Label>
                                                                    <Form.Control
                                                                        placeholder="Enter given name (optional, at least given name or surname must be set)"
                                                                        onChange={(e) => this.setState({newPersonGivenName: e.target.value})}/>
                                                                </Form.Group>
                                                                <Form.Group className="mb-3" controlId="addLastName">
                                                                    <Form.Label>Surname</Form.Label>
                                                                    <Form.Control
                                                                        placeholder="Enter surname (optional, at least given name or surname must be set)"
                                                                        onChange={(e) => this.setState({newPersonSurname: e.target.value})}/>
                                                                </Form.Group>
                                                                <Form.Group className="mb-3" controlId="addAddress">
                                                                    <Form.Label>Address</Form.Label>
                                                                    <Form.Control
                                                                        placeholder="Enter address (optional)"
                                                                        onChange={(e) => this.setState({newPersonAddress: e.target.value})}/>
                                                                </Form.Group>
                                                                <Form.Group className="mb-3" controlId="addTelNr">
                                                                    <Form.Label>Tel. nr.</Form.Label>
                                                                    <Form.Control
                                                                        placeholder="Enter tel. nr. (optional, 7-20 characters)"
                                                                        onChange={(e) => this.setState({newPersonTelNr: e.target.value})}/>
                                                                </Form.Group>
                                                            </Form>
                                                        </Card.Body>
                                                    </Card>
                                                </Modal.Body>
                                                <Modal.Footer>
                                                    <Button variant="secondary"
                                                            onClick={() => this.handleCloseAndDiscardCreateNewPersonButtonClick()}>
                                                        Discard
                                                    </Button>
                                                    <Button variant="primary"
                                                            onClick={() => this.handleCloseAndSetCreateNewPersonButtonClick()}>
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
                                                                // this.setState({currentEmployeePersonName: person.given_name + ' ' + person.surname})
                                                                this.setState({currentEmployeePersonName: (person.given_name ? person.given_name : '[no given name]') + ' ' + (person.surname ? person.surname : '[no surname]')})
                                                                this.setState({currentEmployeePerson_id: person._id!!})
                                                            }}>
                                                                {(person.given_name ? person.given_name : '[no given name]') + ' ' + (person.surname ? person.surname : '[no surname]') + ' (' + person.country_code + ' - ' + person.nat_id_code + ')'}
                                                            </Dropdown.Item>
                                                        ))}
                                                    </Dropdown.Menu>
                                                </Dropdown>
                                            </Col>
                                        </Row>

                                    </Col>
                                </Row>

                                <h5 className='mt-5'>Employee info:</h5>
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
                                                            <Dropdown.Item onClick={() => {
                                                                this.setState({currentEmployeeStatusTypeCode: statusType.employee_status_type_code!!})
                                                            }}>
                                                                {statusType.employee_status_type_code + ' - ' + statusType.name}
                                                            </Dropdown.Item>
                                                        ))}
                                                    </Dropdown.Menu>
                                                </Dropdown>
                                            </Col>
                                        </Row>
                                    </Col>
                                </Row>

                                <Row className={'mt-2'}>
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
                                                        // this.setState({currentEmployeeMentorName: employee.personGivenName + ' ' + employee.personSurname});
                                                        this.setState({currentEmployeeMentorName: (employee.personGivenName ? employee.personGivenName : '[no given name]') + ' ' + (employee.personSurname ? employee.personSurname : '[no surname]')});
                                                    }}>
                                                        {(employee.personGivenName ? employee.personGivenName : '[no given name]')
                                                            + ' ' + (employee.personSurname ? employee.personSurname : '[no surname]')
                                                            + ' (' + employee.personCountryCode
                                                            + ' - ' + employee.personIdCode + ')'}
                                                    </Dropdown.Item>
                                                ))}
                                            </Dropdown.Menu>
                                        </Dropdown>
                                    </Col>
                                </Row>
                                <Row className='mt-5'>
                                    <Col/>
                                    <Col>
                                        <Row>
                                            <Col>
                                                Current person: <h6>{this.state.currentEmployeePersonName}</h6>
                                            </Col>
                                            <Col>
                                                <Button className={'ml-5'} variant="success"
                                                        onClick={() => this.handleAddNewEmployee()}>Add new employee</Button>
                                            </Col>
                                        </Row>
                                    </Col>
                                </Row>
                            </Card.Body>
                        </Card>
                    </Col>
                    <Col/>
                </Row>

                <hr/>

                <AllEmployeesList/>

            </div>
        );
    }
}

export default Employees