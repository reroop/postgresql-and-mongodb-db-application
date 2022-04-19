import React from "react";
import {inject, observer} from "mobx-react";
import PersonStatusTypeStore, {PersonStatusType} from "../stores/PersonStatusTypeStore";
import personStatusTypeStore from "../stores/PersonStatusTypeStore";
import {Button, Card, Form, Table} from "react-bootstrap";

interface PersonStatusTypesProps {
    personStatusTypeStore?: PersonStatusTypeStore;
}

interface State {
    newPersonStatusTypeCode: number | null,
    newPersonStatusTypeName: string | null,
    newPersonStatusTypeDescription?: string | null
}

@inject('personStatusTypeStore')
@observer
class PersonStatusTypes extends React.Component<PersonStatusTypesProps, State> {

    constructor(props) {
        super(props);
        this.state = {
            newPersonStatusTypeCode: null,
            newPersonStatusTypeName: null,
            newPersonStatusTypeDescription: null
        }
    }

    public componentDidMount() {
        this.props.personStatusTypeStore?.getPersonStatusTypes();
    }

    private handleAddTypeButtonClick() {
        const newStatusType: PersonStatusType = {
            person_status_type_code: this.state.newPersonStatusTypeCode,
            name: this.state.newPersonStatusTypeName,
            description: this.state.newPersonStatusTypeDescription
        };

        this.props.personStatusTypeStore!!.addPersonStatusType(newStatusType).then((e) => (
            this.setState({
                newPersonStatusTypeName: null,
                newPersonStatusTypeDescription: null
            })
        ));
    }

    public render() {
        const personStatusTypes: PersonStatusType[] = this.props.personStatusTypeStore!!.personStatusTypes;

        return (
            <div>
                <h1 className="font-weight-heavy">Person status types page</h1>

                <Card style={{width: '48rem'}} className={'m-4'}>
                    <Card.Title>Add new person status type:</Card.Title>
                    <Card.Body>
                        <Form>
                            <Form.Group className="mb-3" controlId="addTypeCode">
                                <Form.Label>Person status type code:</Form.Label>
                                <Form.Control
                                    placeholder="Enter person status type code (number)"
                                    type="number"
                                    value={this.state.newPersonStatusTypeCode!!}
                                    onChange={(e) => this.setState({newPersonStatusTypeCode: Number(e.target.value)})}
                                />
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="addTypeName">
                                <Form.Label>Person status type name:</Form.Label>
                                <Form.Control
                                    placeholder="Enter person status type name"
                                    value={this.state.newPersonStatusTypeName!!}
                                    onChange={(e) => this.setState({newPersonStatusTypeName: e.target.value == '' ? null : e.target.value})}/>
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="addTypeDescription">
                                <Form.Label>Person status type description:</Form.Label>
                                <Form.Control
                                    as="textarea" rows={3}
                                    value={this.state.newPersonStatusTypeDescription!!}
                                    placeholder="Enter person status type description (optional)"
                                    onChange={(e) => this.setState({newPersonStatusTypeDescription: e.target.value == '' ? null : e.target.value})}/>
                            </Form.Group>
                            <Button variant="success" onClick={() => this.handleAddTypeButtonClick()}>Add new person status type</Button>
                        </Form>

                    </Card.Body>
                </Card>

                <div className={'m-4'}>
                    <h3 className="font-weight-heavy">All person status types:</h3>
                    <Table striped bordered hover responsive={true} title={"Person status types:"}>
                        <thead>
                        <tr>
                            <th>Person status type code</th>
                            <th>Name</th>
                            <th>Description</th>
                        </tr>
                        </thead>
                        <tbody>
                        {personStatusTypes.map((statusType) => (
                            <tr key={statusType.person_status_type_code}>
                                <td>
                                    {statusType.person_status_type_code}
                                </td>
                                <td>
                                    {statusType.name}
                                </td>
                                <td>
                                    {statusType.description ? statusType.description : '[no description]'}
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

export default PersonStatusTypes;