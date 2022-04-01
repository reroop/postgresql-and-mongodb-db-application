import React from "react";
import {inject, observer} from "mobx-react";
import EmployeeStatusTypeStore, {EmployeeStatusType} from "../stores/EmployeeStatusTypeStore";
import {Button, Card, Form, Table} from "react-bootstrap";

interface EmployeeStatusTypesProps {
    employeeStatusTypeStore?: EmployeeStatusTypeStore;
}

interface State {
    newEmployeeStatusTypeCode?: number,
    newEmployeeStatusTypeName?: string,
    newEmployeeStatusTypeDescription?: string
}

@inject('employeeStatusTypeStore')
@observer
class EmployeeStatusTypes extends React.Component<EmployeeStatusTypesProps, State> {

    constructor(props) {
        super(props);
        this.state = {
            newEmployeeStatusTypeCode: undefined,
            newEmployeeStatusTypeName: '',
            newEmployeeStatusTypeDescription: undefined
        }
    }

    public componentDidMount() {
        this.props.employeeStatusTypeStore?.getEmployeeStatusTypes();
    }

    private handleAddTypeButtonClick() {
        if (this.state.newEmployeeStatusTypeCode == undefined || this.state.newEmployeeStatusTypeName == undefined) {
            this.setState({
                newEmployeeStatusTypeCode: undefined,
                newEmployeeStatusTypeName: '',
                newEmployeeStatusTypeDescription: undefined
            })
            return;
        }

        const newStatusType: EmployeeStatusType = {
            employee_status_type_code: this.state.newEmployeeStatusTypeCode,
            name: this.state.newEmployeeStatusTypeName
        };

        newStatusType.description = this.state.newEmployeeStatusTypeDescription;
        this.props.employeeStatusTypeStore!!.addEmployeeStatusType(newStatusType).then((e) => (
            this.setState({
                newEmployeeStatusTypeCode: undefined,
                newEmployeeStatusTypeName: '',
                newEmployeeStatusTypeDescription: undefined
            })
        ));

    }

    public render() {
        const employeeStatusTypeStore = this.props.employeeStatusTypeStore!!;
        const employeeStatusTypes: EmployeeStatusType[] = employeeStatusTypeStore.employeeStatusTypes;

        return (
            <div>
                <h1 className="font-weight-heavy">Employee status types page</h1>

                <Card style={{width: '48rem'}} className={'m-4'}>
                    <Card.Title>Add new employee status type:</Card.Title>
                    <Card.Body>
                        <Form>
                            <Form.Group className="mb-3" controlId="addTypeCode">
                                <Form.Label>Employee status type code:</Form.Label>
                                <Form.Control
                                    placeholder="Enter employee status type code (number)"
                                    type="number"
                                    value={this.state.newEmployeeStatusTypeCode}
                                    onChange={(e) => this.setState({newEmployeeStatusTypeCode: Number(e.target.value)})}
                                />
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="addTypeName">
                                <Form.Label>Employee status type name:</Form.Label>
                                <Form.Control
                                    placeholder="Enter employee status type name"
                                    value={this.state.newEmployeeStatusTypeName}
                                    onChange={(e) => this.setState({newEmployeeStatusTypeName: e.target.value})}/>
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="addTypeDescription">
                                <Form.Label>Employee status type description:</Form.Label>
                                <Form.Control
                                    as="textarea" rows={3}
                                    value={this.state.newEmployeeStatusTypeDescription}
                                    placeholder="Enter employee status type description (optional)"
                                    onChange={(e) => this.setState({newEmployeeStatusTypeDescription: e.target.value})}/>
                            </Form.Group>
                            <Button variant="success" onClick={() => this.handleAddTypeButtonClick()}>Add new employee status type</Button>
                        </Form>

                    </Card.Body>
                </Card>

                <div className={'m-4'}>
                    <h3 className="font-weight-heavy">All employee status types:</h3>
                    <Table striped bordered hover responsive={true} title={"Employee status types:"}>
                        <thead>
                        <tr>
                            <th>Employee status type code</th>
                            <th>Name</th>
                            <th>Description</th>
                        </tr>
                        </thead>
                        <tbody>
                        {employeeStatusTypes.map((statusType) => (
                            <tr key={statusType.employee_status_type_code}>
                                <td>
                                    {statusType.employee_status_type_code}
                                </td>
                                <td>
                                    {statusType.name}
                                </td>
                                <td>
                                    {statusType.description}
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

export default EmployeeStatusTypes;