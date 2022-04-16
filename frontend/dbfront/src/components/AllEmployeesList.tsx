import React from "react";
import {inject, observer} from "mobx-react";
import EmployeeStore, {PersonAsEmployee} from "../stores/EmployeeStore";
import {Button, Col, Container, Form, Row, Table} from "react-bootstrap";
import { withRouter } from "react-router";

interface EmployeesListProps {
    employeeStore?: EmployeeStore
}

interface State {
    allEmployees?: PersonAsEmployee[],
    employeesInList: PersonAsEmployee[]
}

@inject('employeeStore')
@observer
class AllEmployeesList extends React.Component<EmployeesListProps & any, State> {

    constructor(props) {
        super(props);
        this.state = {
            allEmployees: [],
            employeesInList: []
        }
    }

    public componentDidMount() {

        //this.props.employeeStore?.getEmployees(); //<-- should not load, because Employees page should have already done it
        this.setState({
            allEmployees: this.props.employeeStore!!.personsAsEmployees,
            employeesInList: this.props.employeeStore!!.personsAsEmployees
        })
    }

    private handleViewEmployeeDetailsButtonClick(person_id: string) {
        let employeeDetailsUrl: string = this.props.location.pathname + '/' +person_id;
        this.props.history.push(employeeDetailsUrl);
    }

    private sortByCountryCode(countryCode: string) {
        let employees: PersonAsEmployee[] = this.state.allEmployees!!;
        employees = employees?.filter( e => e.personCountryCode.toLocaleLowerCase().startsWith(countryCode.toLocaleLowerCase()))
        if (countryCode == '') {
            this.setState({employeesInList: this.state.allEmployees!!})
        }
        this.setState({employeesInList: employees})
    }

    private sortByNatIdCode(natIdCode: string) {
        let employees: PersonAsEmployee[] = this.state.allEmployees!!;
        employees = employees?.filter( e => e.personIdCode.toLocaleLowerCase().startsWith(natIdCode.toLocaleLowerCase()))
        if (natIdCode == '') {
            this.setState({employeesInList: this.state.allEmployees!!})
        }
        this.setState({employeesInList: employees})
    }

    public render() {
        //const employees: PersonAsEmployee[] = this.props.employeeStore!!.personsAsEmployees;
        const employees: PersonAsEmployee[] = this.state.employeesInList;

        return (
            <div>
                <Container fluid>
                    <Row>
                        <Col><h4>Employees:</h4></Col>

                        <Col>
                            <Row>

                                <Col>
                                    <Row>
                                        <Col><h5>Filter:</h5></Col>
                                        <Col>
                                            <Form.Group className="mb-3" controlId="filterBycountryCode">
                                                <Form.Label>By country code:</Form.Label>
                                                <Form.Control
                                                    placeholder="Country code"
                                                    maxLength={3}
                                                    onChange={(e) => this.sortByCountryCode(e.target.value)}
                                                />
                                            </Form.Group>
                                        </Col>
                                        <Col>
                                            <Form.Group className="mb-3" controlId="filterByNatIdCode">
                                                <Form.Label>By national id. code:</Form.Label>
                                                <Form.Control
                                                    placeholder="Nat. id. code"
                                                    onChange={(e) => this.sortByNatIdCode(e.target.value)}
                                                />
                                            </Form.Group>
                                        </Col>
                                    </Row>
                                </Col>

                            </Row>
                        </Col>
                    </Row>
                    <Row>
                        <Col>
                            <Table striped bordered hover responsive={true} title={"List of employees:"}>
                                <thead>
                                <tr>
                                    <th>Country code</th>
                                    <th>Nat. id. code</th>
                                    <th>Given name</th>
                                    <th>Surname</th>
                                    <th>Employee status</th>
                                    <th>Registration date</th>
                                    <th>View details</th>
                                </tr>
                                </thead>
                                <tbody>
                                {employees.map((employee) => (
                                    <tr key={employee.person_id}>
                                        <td>
                                            {employee.personCountryCode}
                                        </td>
                                        <td>
                                            {employee.personIdCode}
                                        </td>
                                        <td>
                                            {employee.personGivenName ? employee.personGivenName : '-'}
                                        </td>
                                        <td>
                                            {employee.personSurname ? employee.personSurname : '-'}
                                        </td>
                                        <td>
                                            {employee.employeeStatus}
                                        </td>
                                        <td>
                                            {employee.personRegDate}
                                        </td>
                                        <td>
                                            <Button variant="info" onClick={() => this.handleViewEmployeeDetailsButtonClick(employee.person_id)}>
                                                View
                                            </Button>
                                        </td>
                                    </tr>
                                ))}
                                </tbody>
                            </Table>
                        </Col>
                    </Row>
                </Container>
            </div>
        );
    }
}

export default withRouter(AllEmployeesList);