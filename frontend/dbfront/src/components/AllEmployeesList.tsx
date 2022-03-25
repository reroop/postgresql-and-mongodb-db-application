import React from "react";
import {inject, observer} from "mobx-react";
import EmployeeStore, {PersonAsEmployee} from "../stores/EmployeeStore";
import {Button, Col, Container, Row, Table} from "react-bootstrap";
import { withRouter } from "react-router";

interface EmployeesListProps {
    employeeStore?: EmployeeStore
}

@inject('employeeStore')
@observer
class AllEmployeesList extends React.Component<EmployeesListProps & any> {

    constructor(props) {
        super(props);
    }

    public componentDidMount() {
        //this.props.employeeStore?.getEmployees; <-- should not load, because Employees page should have already done it
    }

    private handleViewEmployeeDetailsButtonClick(person_id: string) {
        let employeeDetailsUrl: string = this.props.location.pathname + '/' +person_id;
        this.props.history.push(employeeDetailsUrl);
    }

    public render() {
        const employees: PersonAsEmployee[] = this.props.employeeStore!!.personsAsEmployees;

        return (
            <div>
                <Container fluid>
                    <Row>
                        <Col><h4>Employees:</h4></Col>

                        <Col>
                            <Row>

                                <Col>
                                    <Row>
                                        <Col><h5>Search:</h5></Col>
                                        <Col>by country</Col>
                                        <Col>by idcode</Col>
                                        <Col>by name</Col>
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
                                            {employee.personGivenName}
                                        </td>
                                        <td>
                                            {employee.personSurname}
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