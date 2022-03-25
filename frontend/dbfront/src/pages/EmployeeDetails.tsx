import React from "react";
import {inject, observer} from "mobx-react";
import { AllPersonEmploymentsList } from "../components"
import PersonStore, {Person} from "../stores/PersonStore";
import CountryStore from "../stores/CountryStore";
import EmployeeStore, {Employee} from "../stores/EmployeeStore";
import employeeStatusTypeStore from "../stores/EmployeeStatusTypeStore";
import OccupationStore from "../stores/OccupationStore";
import {withRouter} from "react-router";
import {Button, Col, Dropdown, Form, Row} from "react-bootstrap";


interface EmployeeDetailsProps {
    personStore?: PersonStore,
    countryStore?: CountryStore,
    employeeStore?: EmployeeStore,
    employeeStatusTypeStore?: employeeStatusTypeStore,
    occupationStore?: OccupationStore,
    //todo: employmentStore
}

interface State {
    person_id?: string,
    person?: Person,
    employee?: Employee
}

class EmployeeDetails extends React.Component<EmployeeDetailsProps & any, State> {

    constructor(props) {
        super(props);

        const {id} = this.props.match.params;
        this.state = {
            person_id: id.toString()
        }
    }

    public componentDidMount() {
        console.warn(this.state.person_id);
        //todo: fetch infos -> person, employee, employments
    }

    public render() {
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
                                        riik
                                    </Dropdown.Toggle>
                                    <Dropdown.Menu>
                                        <Dropdown.Item>
                                            mingi riik
                                        </Dropdown.Item>
                                    </Dropdown.Menu>
                                </Dropdown>
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="addNatIdCode">
                                <Form.Label>Nat. Id. code:</Form.Label>
                                <Form.Control
                                    placeholder="Enter nat. id. code"
                                />
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="addEmail">
                                <Form.Label>Email:</Form.Label>
                                <Form.Control
                                    placeholder="Enter email"/>
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="addBirthdate">
                                <Form.Label>Birthdate:</Form.Label>
                                <Form.Control
                                    type={"date"}
                                />
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="addFirstName">
                                <Form.Label>First name:</Form.Label>
                                <Form.Control
                                    placeholder="Enter first name (optional, at least first or last name must be set)"/>
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="addLastName">
                                <Form.Label>Last name:</Form.Label>
                                <Form.Control
                                    placeholder="Enter last name (optional, at least first or last name must be set)"/>
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="addAddress">
                                <Form.Label>Address:</Form.Label>
                                <Form.Control
                                    placeholder="Enter address (optional)"/>
                            </Form.Group>
                            <Button variant="success">Update person info</Button>
                        </Form>
                    </Col>

                    <Col sm={4} className={'ml-2'}>
                        <h4>Update employee info:</h4>

                        <Row className={'mt-4'}>
                            <Col>Employee status:</Col>
                            <Col>
                                <Dropdown className="d-inline mx-2">
                                    <Dropdown.Toggle id="dropdown-autoclose-true">
                                        staatus
                                    </Dropdown.Toggle>
                                    <Dropdown.Menu>
                                        <Dropdown.Item>
                                            mingi staatus
                                        </Dropdown.Item>
                                    </Dropdown.Menu>
                                </Dropdown>
                            </Col>
                        </Row>

                        <Row className={'mt-4'}>
                            <Col>Mentor:</Col>
                            <Col>
                                <Dropdown className="d-inline mx-2">
                                    <Dropdown.Toggle id="dropdown-autoclose-true">
                                        staatus
                                    </Dropdown.Toggle>
                                    <Dropdown.Menu>
                                        <Dropdown.Item>
                                            mingi staatus
                                        </Dropdown.Item>
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