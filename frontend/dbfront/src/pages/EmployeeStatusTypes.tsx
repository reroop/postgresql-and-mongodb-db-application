import React from "react";
import {inject, observer} from "mobx-react";
import EmployeeStatusTypeStore, {EmployeeStatusType} from "../stores/EmployeeStatusTypeStore";
import {Button, Card, Form, FormControl, InputGroup, Table} from "react-bootstrap";

interface EmployeeStatusTypesProps {
    employeeStatusTypeStore?: EmployeeStatusTypeStore;
}

@inject('employeeStatusTypeStore')
@observer
class EmployeeStatusTypes extends React.Component<EmployeeStatusTypesProps> {

    public componentDidMount() {
        this.props.employeeStatusTypeStore?.getEmployeeStatusTypes();
    }

    public render() {
        const employeeStatusTypeStore = this.props.employeeStatusTypeStore!!;
        const employeeStatusTypes: EmployeeStatusType[] = employeeStatusTypeStore.employeeStatusTypes;

        let newEmployeeStatusTypeCode: number|null = null;
        let newEmployeeStatusTypeName: string = '';
        let newEmployeeStatusTypeDescription: string|null = null;

        const handleEditTypeButtonClick = (statusType: EmployeeStatusType) => {
            if (statusType.kirjeldus === '') {
                statusType.kirjeldus = undefined;
            }
            employeeStatusTypeStore.updateEmployeeStatusType(statusType);
        };

        const handleDeleteTypeButtonClick = (typeCode: number) => {
            employeeStatusTypeStore.deleteEmployeeStatusType(typeCode);
        }

        const handleAddTypeButtonClick = () => {
            if (newEmployeeStatusTypeCode === null) {
                return;
            }

            const newStatusType: EmployeeStatusType = {
                tootaja_seisundi_liik_kood: newEmployeeStatusTypeCode,
                nimetus: newEmployeeStatusTypeName
            };
            if (newEmployeeStatusTypeDescription != null) {
                newStatusType.kirjeldus = newEmployeeStatusTypeDescription;
            }
            employeeStatusTypeStore.addEmployeeStatusType(newStatusType);
            newEmployeeStatusTypeCode = null;
            newEmployeeStatusTypeName = '';
            newEmployeeStatusTypeDescription = null;
        }

        return (
            <div>
                <h1 className="font-weight-heavy">Employee status types page</h1>

                <Card>
                    <Card.Title>Add new employee status type:</Card.Title>
                    <Card.Body>
                        <Form>
                            <Form.Group className="mb-3" controlId="addTypeCode">
                                <Form.Label>Employee status type code:</Form.Label>
                                <Form.Control
                                    placeholder="Enter employee status type code (number)"
                                    type="number"
                                    onChange={(e) => newEmployeeStatusTypeCode = Number(e.target.value)}
                                />
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="addTypeName">
                                <Form.Label>Employee status type name:</Form.Label>
                                <Form.Control
                                    placeholder="Enter employee status type name"
                                    onChange={(e) => newEmployeeStatusTypeName = e.target.value}/>
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="addTypeDescription">
                                <Form.Label>Employee status type description:</Form.Label>
                                <Form.Control
                                    as="textarea" rows={3}
                                    placeholder="Enter employee status type description (optional)"
                                    onChange={(e) => newEmployeeStatusTypeDescription = e.target.value}/>
                            </Form.Group>
                            <Button variant="success" onClick={() => handleAddTypeButtonClick()}>Add new employee status type</Button>
                        </Form>

                    </Card.Body>
                </Card>

                <div>
                    <h3 className="font-weight-heavy">All employee status types:</h3>
                    <Table striped bordered hover responsive={true} title={"Employee status types:"}>
                        <thead>
                        <tr>
                            <th>Employee status type code</th>
                            <th>Name</th>
                            <th>Description</th>
                            <th>Edit employee status type</th>
                            <th>Delete employee status type</th>
                        </tr>
                        </thead>
                        <tbody>
                        {employeeStatusTypes.map((statusType) => (
                            <tr key={statusType.tootaja_seisundi_liik_kood}>
                                <td>
                                    {statusType.tootaja_seisundi_liik_kood}
                                </td>
                                <td>
                                    <InputGroup className={"mb-3"}>
                                        <FormControl
                                            placeholder={"Employee status type name"}
                                            value={statusType.nimetus}
                                            onChange={(e) => statusType.nimetus = e.target.value}/>
                                    </InputGroup>
                                </td>
                                <td>
                                    <InputGroup className={"mb-3"}>
                                        <FormControl
                                            placeholder={"Employee status type description"}
                                            value={statusType.kirjeldus}
                                            onChange={(e) => statusType.kirjeldus = e.target.value}/>
                                    </InputGroup>
                                </td>
                                <td><Button variant="info"
                                            onClick={() => handleEditTypeButtonClick(statusType)}>Update</Button></td>
                                <td><Button variant="danger"
                                            onClick={() => handleDeleteTypeButtonClick(statusType.tootaja_seisundi_liik_kood)}>Delete</Button>
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