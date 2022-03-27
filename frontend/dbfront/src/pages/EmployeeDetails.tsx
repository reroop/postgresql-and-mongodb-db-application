import React from "react";
import {inject, observer} from "mobx-react";
import { useParams } from "react-router-dom";
import { AllPersonEmploymentsList } from "../components"
import PersonStore, {Person} from "../stores/PersonStore";
import CountryStore, {Country} from "../stores/CountryStore";
import EmployeeStore, {Employee, PersonAsEmployee} from "../stores/EmployeeStore";
import employeeStatusTypeStore, {EmployeeStatusType} from "../stores/EmployeeStatusTypeStore";
import OccupationStore from "../stores/OccupationStore";
import {withRouter} from "react-router";
import {Button, Col, Dropdown, Form, FormControl, Row} from "react-bootstrap";
import EmploymentStore from "../stores/EmploymentStore";
import {createDeflateRaw} from "zlib";


interface EmployeeDetailsProps {
    personStore?: PersonStore,
    countryStore?: CountryStore,
    employeeStore?: EmployeeStore,
    employeeStatusTypeStore?: employeeStatusTypeStore,
    occupationStore?: OccupationStore,
    employmentStore?: EmploymentStore
}

interface State {
    person_id?: string,
    person?: Person,
    employee?: Employee
}

@inject('personStore', 'countryStore', 'employeeStore', 'employeeStatusTypeStore', 'occupationStore')
@observer
class EmployeeDetails extends React.Component<EmployeeDetailsProps, State> {

    constructor(props) {
        super(props);

        // @ts-ignore
        const {id} = this.props.match.params;
        this.state = {
            person_id: id.toString()
        }
    }

    public componentDidMount() {
        this.props.countryStore?.getCountries();
        this.props.employeeStatusTypeStore?.getEmployeeStatusTypes();
        this.props.employeeStore?.getEmployees();
        this.props.personStore?.getPersonBy_id(this.state.person_id!!).then((response) => {
            this.setState({person: response});
        });
        this.props.employeeStore?.getEmployeeByPerson_id(this.state.person_id!!).then((response) => {
            this.setState({employee: response});
        });
    }

    private refreshPage() {
        window.location.reload();
    }

    public render() {
        const countries: Country[] = this.props.countryStore!!.countries;
        const employeeStatusTypes: EmployeeStatusType[] = this.props.employeeStatusTypeStore!!.employeeStatusTypes;
        const possibleMentors: PersonAsEmployee[] = this.props.employeeStore!!.personsAsEmployees.filter(employee => employee.person_id !== this.state.person_id);

        console.warn(possibleMentors);

        return (
            <div>
                <h3 className={'ml-2'}>Person detail page:</h3>
                <Row className={'mt-3'}>
                    <Col sm={6} className={'ml-2'}>
                        <h4>Update person info:</h4>
                        <Form>
                            <Form.Group controlId="addCountryCode" className={'mt-2'}>
                                <Form.Label>Country code:</Form.Label>
                                <Dropdown className="d-inline mx-2">
                                    <Dropdown.Toggle id="dropdown-autoclose-true">
                                        {this.state.person?.riik_kood}
                                    </Dropdown.Toggle>
                                    <Dropdown.Menu>
                                        {countries.map( country => (
                                            <Dropdown.Item>
                                                {country.riik_kood + ' - ' + country.nimetus}
                                            </Dropdown.Item>
                                        ))}
                                    </Dropdown.Menu>
                                </Dropdown>
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="addNatIdCode">
                                <Form.Label>Nat. Id. code:</Form.Label>
                                <Form.Control
                                    value={this.state.person?.isikukood}
                                    placeholder="Enter nat. id. code"
                                />
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="addEmail">
                                <Form.Label>Email:</Form.Label>
                                <Form.Control
                                    value={this.state.person?.e_meil}
                                    placeholder="Enter email"/>
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="addBirthdate">
                                <Form.Label>Birthdate:</Form.Label>
                                <Form.Control
                                    type={"date"}
                                    value={new Date(this.state.person?.synni_kp!!).toLocaleDateString("sv-SE")}
                                />
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="regDate">
                                <Form.Label>Reg. date:</Form.Label>
                                <FormControl
                                    type={"date"}
                                    value={new Date(this.state.person?.reg_aeg!!).toLocaleDateString("sv-SE")}/>
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="addFirstName">
                                <Form.Label>First name:</Form.Label>
                                <Form.Control
                                    value={this.state.person?.eesnimi}
                                    placeholder="Enter first name (optional, at least first or last name must be set)"/>
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="addLastName">
                                <Form.Label>Last name:</Form.Label>
                                <Form.Control
                                    value={this.state.person?.perenimi}
                                    placeholder="Enter last name (optional, at least first or last name must be set)"/>
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="addAddress">
                                <Form.Label>Address:</Form.Label>
                                <Form.Control
                                    value={this.state.person?.elukoht}
                                    placeholder="Enter address (optional)"/>
                            </Form.Group>
                            <Button variant="success">Update person info</Button>
                            <Button variant="warning" className={'ml-2'} onClick={() => this.refreshPage()}>Undo changes</Button>
                        </Form>
                    </Col>

                    <Col sm={4} className={'ml-2'}>
                        <h4>Update employee info:</h4>

                        <Row className={'mt-4'}>
                            <Col>Employee status:</Col>
                            <Col>
                                <Dropdown className="d-inline mx-2">
                                    <Dropdown.Toggle id="dropdown-autoclose-true">
                                        {this.state.employee?.tootaja_seisundi_liik_kood}
                                    </Dropdown.Toggle>
                                    <Dropdown.Menu>
                                        {employeeStatusTypes.map(status => (
                                            <Dropdown.Item>
                                                {status.tootaja_seisundi_liik_kood + ' - ' + status.nimetus}
                                            </Dropdown.Item>
                                        ))}
                                    </Dropdown.Menu>
                                </Dropdown>
                            </Col>
                        </Row>

                        <Row className={'mt-4'}>
                            <Col>Mentor:</Col>
                            <Col>
                                <Dropdown className="d-inline mx-2">
                                    <Dropdown.Toggle id="dropdown-autoclose-true">
                                        {this.state.employee?.mentor_id}
                                    </Dropdown.Toggle>
                                    <Dropdown.Menu>
                                        {possibleMentors.map(mentor => (
                                            <Dropdown.Item>
                                                {mentor.personGivenName + ' ' + mentor.personSurname}
                                            </Dropdown.Item>
                                        ))}
                                    </Dropdown.Menu>
                                </Dropdown>
                            </Col>
                        </Row>

                        <Row className={'mt-4'}>
                            <Col>End all occupations:</Col>
                            <Col>
                                <Button variant="warning" onClick={() => console.log("end all occupations clicked")}>End occupations</Button>

                            </Col>
                        </Row>

                        <Row className={'mt-4'}>
                            <Col>Delete employee:</Col>
                            <Col>
                                <Button variant="danger" onClick={() => console.log("delete employee clicked")}>Delete employee</Button>
                            </Col>
                        </Row>

                    </Col>

                </Row>

                <hr/>

                <AllPersonEmploymentsList  person_id={this.state.person_id!!}/>
            </div>
        );
    }

}

export default withRouter(EmployeeDetails);