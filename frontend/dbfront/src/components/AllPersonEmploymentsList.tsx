import React from "react";
import {inject, observer} from "mobx-react";
import EmploymentStore, {Employment} from "../stores/EmploymentStore";
import OccupationStore, {Occupation} from "../stores/OccupationStore";
import {Button, Card, Col, Container, Dropdown, Form, FormControl, Row, Table} from "react-bootstrap";

interface AllPersonEmploymentsListProps {
    person_id: string,
    employmentStore?: EmploymentStore,
    occupationStore?: OccupationStore
}

interface State {
    newEmploymentOccupation?: Occupation,
    newEmploymentStartDate?: string,
    endEmploymentOccupationCode?: number,
    endEmploymentEndDate?: string
}

interface PersonEmploymentsListEntry {
    occupation?: Occupation,
    employment: Employment
}


@inject('employmentStore', 'occupationStore')
@observer
class AllPersonEmploymentsList extends React.Component<AllPersonEmploymentsListProps, State> {

    constructor(props) {
        super(props);
        this.state = {
            newEmploymentOccupation: undefined,
            newEmploymentStartDate: undefined,
            endEmploymentOccupationCode: undefined,
            endEmploymentEndDate: new Date().toLocaleDateString("sv-SE")+'T00:00:00'
        }
    }

    public componentDidMount() {
        this.props.occupationStore?.getOccupations();
        this.props.employmentStore?.getAllEmploymentsForEmployee(this.props.person_id);
    }

    private handleAddEmploymentForPersonButtonClicked() {
        const newEmployment: Employment = {
            start_time: this.state.newEmploymentStartDate!!,
            occupation_code: this.state.newEmploymentOccupation?.occupation_code!!,
            person_id: this.props.person_id
        }

        this.props.employmentStore?.addNewEmployment(newEmployment).then(() => {
            this.setState({newEmploymentStartDate: undefined, newEmploymentOccupation: undefined});
            this.props.employmentStore?.getAllEmploymentsForEmployee(this.props.person_id);
        });
    }

    private handleEndEmployment(occupationCode: number) {
        const endEmployment: Employment = {
            end_time: this.state.endEmploymentEndDate,
            occupation_code: occupationCode,
            person_id: this.props.person_id
        };
        console.log(endEmployment);
        this.props.employmentStore?.endEmployment(endEmployment).then(() => {
            this.props.employmentStore?.getAllEmploymentsForEmployee(this.props.person_id);
            this.setState({
                endEmploymentOccupationCode: undefined,
                endEmploymentEndDate: new Date().toLocaleDateString("sv-SE")
            });
        });
    }

    render() {
        const entries: PersonEmploymentsListEntry[] = this.props.employmentStore!!.personEmploymentsWithOccupations;
        const occupations: Occupation[] = this.props.occupationStore!!.occupations;

        return (
            <div>
                <h3>All employments:</h3>

                <Card style={{ width: '36rem' }}>
                    <Card.Title>Add new employment:</Card.Title>
                    <Card.Body>
                        <Form>
                            <Form.Group className="mb-3" controlId="addEmploymentOccupationCode">
                                <Form.Label>Occupation:</Form.Label>
                                <Dropdown className="d-inline mx-2">
                                    <Dropdown.Toggle id="dropdown-autoclose-true">
                                        {this.state.newEmploymentOccupation?.name}
                                    </Dropdown.Toggle>
                                    <Dropdown.Menu>
                                        {occupations.map((occupation) => (
                                            <Dropdown.Item onClick={() => {this.setState({newEmploymentOccupation: occupation})}}>
                                                {occupation.occupation_code + ' - ' + occupation.name}
                                            </Dropdown.Item>
                                        ))}
                                    </Dropdown.Menu>
                                </Dropdown>
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="addEmploymentStartDate">
                                <Form.Label>Start date:</Form.Label>
                                <Form.Control
                                    type={"date"}
                                    onChange={(e) => {
                                        this.setState({newEmploymentStartDate: e.target.value+'T00:00:00'}) //hack for localdatetime in backend
                                    }}
                                />
                            </Form.Group>
                            <Button variant="success" onClick={() => this.handleAddEmploymentForPersonButtonClicked()}>Add new employment</Button>
                        </Form>

                    </Card.Body>
                </Card>

                <Container fluid className={'mt-5'}>
                    <Row>
                        <Col>
                            <Table striped bordered hover responsive={true} title={"List of employments:"}>
                                <thead>
                                <tr>
                                    <th>Occupation code</th>
                                    <th>Occupation name</th>
                                    <th>Occupation description</th>
                                    <th>Employment start date</th>
                                    <th>Employment end date</th>
                                    <th>End employment</th>
                                </tr>
                                </thead>
                                <tbody>
                                {entries.length === 0 && <h5>No employments for this person!</h5>}
                                {entries.map((entry) => (
                                    <tr key={entry.employment._id}>
                                        <td>
                                            {entry.occupation?.occupation_code}
                                        </td>
                                        <td>
                                            {entry.occupation?.name}
                                        </td>
                                        <td>
                                            {entry.occupation?.description}
                                        </td>
                                        <td>
                                            <FormControl
                                                type={"date"}
                                                value={new Date(entry.employment.start_time!!).toLocaleDateString("sv-SE")}/>
                                        </td>
                                        <td>
                                            {entry.employment.end_time!! ?
                                                <FormControl
                                                    type={"date"}
                                                    value={new Date(entry.employment.end_time!!).toLocaleDateString("sv-SE")}/>
                                                : <FormControl
                                                    type={"date"}
                                                    value={new Date(this.state.endEmploymentEndDate!!).toLocaleDateString("sv-SE")}
                                                    onChange={(e) => {
                                                        this.setState({
                                                            endEmploymentEndDate: e.target.value + 'T00:00:00',
                                                            endEmploymentOccupationCode: entry.employment.occupation_code
                                                        });   //hack for localdatetime in backend
                                                    }
                                                    }/>
                                            }
                                        </td>
                                        <td>
                                            {entry.employment.end_time == undefined &&
                                                <Button variant="warning"
                                                        onClick={() => this.handleEndEmployment(entry.employment.occupation_code!!)}>
                                                    End this employment
                                                </Button>
                                            }

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

export default AllPersonEmploymentsList;