import React from "react";
import {inject, observer} from "mobx-react";
import { AllPersonEmploymentsList } from "../components"
import PersonStore, {Person} from "../stores/PersonStore";
import CountryStore, {Country} from "../stores/CountryStore";
import EmployeeStore, {Employee, PersonAsEmployee} from "../stores/EmployeeStore";
import employeeStatusTypeStore, {EmployeeStatusType} from "../stores/EmployeeStatusTypeStore";
import OccupationStore from "../stores/OccupationStore";
import {withRouter} from "react-router";
import {Button, Col, Dropdown, Form, FormControl, Row} from "react-bootstrap";
import EmploymentStore, {Employment} from "../stores/EmploymentStore";
import EmployeeStatusTypeStore from "../stores/EmployeeStatusTypeStore";


interface EmployeeDetailsProps {
    personStore?: PersonStore,
    countryStore?: CountryStore,
    employeeStore?: EmployeeStore,
    employeeStatusTypeStore?: EmployeeStatusTypeStore,
    occupationStore?: OccupationStore,
    employmentStore?: EmploymentStore
}

interface State {
    person_id?: string,
    person?: Person,
    employee?: Employee,
    employeeMentor?: PersonAsEmployee,
    endEmploymentsDate: string
}

@inject('personStore', 'countryStore', 'employeeStore', 'employeeStatusTypeStore', 'occupationStore', 'employmentStore')
@observer
class EmployeeDetails extends React.Component<EmployeeDetailsProps, State> {

    constructor(props) {
        super(props);

        // @ts-ignore
        const {id} = this.props.match.params;
        this.state = {
            person_id: id.toString(),
            endEmploymentsDate: new Date().toLocaleDateString("sv-SE")+'T00:00:00'
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

    private handleUpdateInfoButtonClicked() {
        this.props.personStore?.updatePerson(this.state.person!!).then(() => {
            this.props.employeeStore?.updateEmployee(this.state.employee!!).then(this.refreshPage)
        });
    }

    private handleEndEmployments() {
        if (this.state.endEmploymentsDate == null) {
            return;
        }
        const endEmployments: Employment = {
            end_time: this.state.endEmploymentsDate,
            person_id: this.state.person_id!!
        }
        //todo FIX BUG: put setting employee status to ended in back together with ending all employments
        this.props.employmentStore?.endAllEmployments(endEmployments).then(() => {
            this.props.employeeStore?.setEmployeeStatusToEnded(this.state.person_id!!).then(this.refreshPage)
        });
    }

    private handleDeleteEmployee() {
        this.props.employeeStore?.deleteEmployee(this.state.person_id!!).then(this.refreshPage);
    }

    public render() {
        const countries: Country[] = this.props.countryStore!!.countries;
        const employeeStatusTypes: EmployeeStatusType[] = this.props.employeeStatusTypeStore!!.employeeStatusTypes;
        const possibleMentors: PersonAsEmployee[] = this.props.employeeStore!!.personsAsEmployees.filter(employee => employee.person_id !== this.state.person_id);

        let mentor: PersonAsEmployee|undefined = undefined;
        if (this.state.employee?.mentor_id != null) {
            mentor = this.props.employeeStore!!.personsAsEmployees.filter( (employee) => employee.person_id === this.state.employee!!.mentor_id).at(0);
        }

        if (this.state.employee?.person_id == null) {
            return (
                <div><h3 className={'m-3'}>This employee is deleted or not found. Go back to employees list.</h3></div>
            )
        }

        return (
            <div className={'ml-2'}>
                <Row className={'mt-3'}>
                    <Col><h3 className={'ml-2'}>Employee detail page:</h3></Col>
                </Row>

                <hr/>

                <Row className={'mt-3'}>
                    <Col sm={6} className={'ml-2'}>
                        <h4>Update personal info:</h4>
                        <Form>
                            <Form.Group controlId="addCountryCode" className={'mt-2'}>
                                <Form.Label>Country code:</Form.Label>
                                <Dropdown className="d-inline mx-2">
                                    <Dropdown.Toggle id="dropdown-autoclose-true">
                                        {this.state.person?.country_code}
                                    </Dropdown.Toggle>
                                    <Dropdown.Menu>
                                        {countries.map( country => (
                                            <Dropdown.Item
                                                onClick={() => {
                                                    this.setState(state => (state.person!!.country_code = country.country_code, state));
                                                }}>
                                                {country.country_code + ' - ' + country.name}
                                            </Dropdown.Item>
                                        ))}
                                    </Dropdown.Menu>
                                </Dropdown>
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="addNatIdCode">
                                <Form.Label>Nat. Id. code:</Form.Label>
                                <Form.Control
                                    value={this.state.person?.nat_id_code}
                                    placeholder="Enter nat. id. code"
                                    onChange={(e) => this.setState(state => (state.person!!.nat_id_code = e.target.value, state))}
                                />
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="addEmail">
                                <Form.Label>Email:</Form.Label>
                                <Form.Control
                                    value={this.state.person?.e_mail}
                                    placeholder="Enter email"
                                    onChange={(e) => this.setState(state => (state.person!!.e_mail = e.target.value, state))}/>
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="addBirthdate">
                                <Form.Label>Birthdate:</Form.Label>
                                <Form.Control
                                    type={"date"}
                                    value={new Date(this.state.person?.birth_date!!).toLocaleDateString("sv-SE")}
                                    onChange={(e) => this.setState(state => (state.person!!.birth_date = e.target.value+'T00:00:00', state))}
                                />
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="regDate">
                                <Form.Label>Reg. date:</Form.Label>
                                <FormControl
                                    type={"date"}
                                    value={new Date(this.state.person?.reg_time!!).toLocaleDateString("sv-SE")}/>
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="addFirstName">
                                <Form.Label>Given name:</Form.Label>
                                <Form.Control
                                    value={this.state.person?.given_name!!}
                                    placeholder="Enter first name (optional, at least first or last name must be set)"
                                    onChange={(e) => this.setState(state => (state.person!!.given_name = e.target.value == '' ? null : e.target.value, state))}/>
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="addLastName">
                                <Form.Label>Surname:</Form.Label>
                                <Form.Control
                                    value={this.state.person?.surname!!}
                                    placeholder="Enter last name (optional, at least first or last name must be set)"
                                    onChange={(e) => this.setState(state => (state.person!!.surname = e.target.value == '' ? null : e.target.value, state))}/>
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="addAddress">
                                <Form.Label>Address:</Form.Label>
                                <Form.Control
                                    value={this.state.person?.address}
                                    placeholder="Enter address (optional)"
                                    onChange={(e) => this.setState(state => (state.person!!.address = e.target.value, state))}/>
                            </Form.Group>
                        </Form>
                    </Col>

                    <Col className={'ml-2'}>
                        <h4>Update employee info:</h4>

                        <Row className={'mt-4'}>
                            <Col>Employee status:</Col>
                            <Col>
                                <Dropdown>
                                    <Dropdown.Toggle id="dropdown-autoclose-true">
                                        {this.state.employee?.employee_status_type_code}
                                    </Dropdown.Toggle>
                                    <Dropdown.Menu>
                                        {employeeStatusTypes.map(status => (
                                            <Dropdown.Item onClick={() => {
                                                this.setState(state => (state.employee!!.employee_status_type_code = status.employee_status_type_code, state));
                                            }}>
                                                {status.employee_status_type_code + ' - ' + status.name}
                                            </Dropdown.Item>
                                        ))}
                                    </Dropdown.Menu>
                                </Dropdown>
                            </Col>
                        </Row>

                        <Row className={'mt-4'}>
                            <Col>Mentor:</Col>
                            <Col>
                                <Row>
                                    <Col>
                                        <Dropdown>
                                            <Dropdown.Toggle id="dropdown-autoclose-true">
                                                {mentor != null ? mentor.personGivenName + ' ' + mentor.personSurname : 'No mentor assigned'}
                                            </Dropdown.Toggle>
                                            <Dropdown.Menu>
                                                {possibleMentors.map(mentor => (
                                                    <Dropdown.Item onClick={() => {
                                                        this.setState(state => (state.employee!!.mentor_id = mentor.person_id, state));
                                                    }}>
                                                        {mentor.personGivenName + ' ' + mentor.personSurname}
                                                    </Dropdown.Item>
                                                ))}
                                            </Dropdown.Menu>
                                        </Dropdown>
                                    </Col>
                                    <Col>
                                        <Button variant="warning" onClick={() => {
                                            this.setState(state => (state.employee!!.mentor_id = null, state))
                                        }}>Remove mentor</Button>
                                    </Col>
                                </Row>
                            </Col>
                        </Row>

                        <hr/>

                        <Row className={'mt-2'}>
                            <Col>
                                <Button variant="dark"  onClick={() => this.refreshPage()}>Undo changes and reload page</Button>
                            </Col>
                            <Col>
                                <Button className={'ml-4'} variant="success" onClick={() => this.handleUpdateInfoButtonClicked()}>Save all changes and reload page</Button>
                            </Col>
                        </Row>

                        <hr/>

                        <Row className={'mt-4'}>
                            <Col>
                                <p className="font-weight-bold font-italic">FYI:</p>
                                <p className="font-italic">
                                    "End employments" ends all employee's active employments by adding an end date to each (active) employment. You must choose the end date. Employee status is also set to "Contract ended"
                                </p>
                                <p className="font-italic">
                                    "Delete employee" deletes the employee's info (including employments) from database IF the employee is not actively employed in any occupation.
                                </p>
                                <p className="font-weight-bold">
                                    These actions are not reversible!
                                </p>
                            </Col>
                        </Row>

                        <Row className={'mt-4'}>
                            <Col>End all employments:</Col>
                            <Col>
                                <Row>
                                    <Col sm={6}>
                                        <Form.Control
                                            type={"date"}
                                            value={new Date(this.state.endEmploymentsDate!!).toLocaleDateString("sv-SE")}
                                            onChange={(e) => this.setState({endEmploymentsDate: e.target.value+'T00:00:00'})}
                                        />
                                    </Col>
                                    <Col>
                                        <Button variant="warning" onClick={() => this.handleEndEmployments()}>End employments</Button>
                                    </Col>
                                </Row>

                            </Col>
                        </Row>

                        <Row className={'mt-4'}>
                            <Col>Delete employee:</Col>
                            <Col>
                                <Row>
                                    <Col sm={6}/>
                                    <Col>
                                        <Button variant="danger" onClick={() => this.handleDeleteEmployee()}>Delete employee</Button>
                                    </Col>
                                </Row>
                            </Col>
                        </Row>

                    </Col>

                </Row>

                <hr/>

                <div className={'mt-3'}>
                    <AllPersonEmploymentsList person_id={this.state.person_id!!}/>
                </div>

            </div>
        );
    }

}

export default withRouter(EmployeeDetails);