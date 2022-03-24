import React from "react";
import OccupationStore, {Occupation} from "../stores/OccupationStore";
import {inject, observer} from "mobx-react";
import {Button, Card, Form, FormControl, InputGroup, Table} from "react-bootstrap";

interface OccupationsProps {
    occupationStore?: OccupationStore;
}

@inject('occupationStore')
@observer
class Occupations extends React.Component<OccupationsProps> {

    public componentDidMount() {
        this.props.occupationStore?.getOccupations();
    }

    public render() {
        const occupationStore = this.props.occupationStore!!;
        const occupations: Occupation[] = occupationStore.occupations;

        let newOccupationCode: number|null = null;
        let newOccupationName: string = '';
        let newOccupationDescription: string|null = null;

        const handleEditOccupationButtonClick = (occupation: Occupation) => {
            if (occupation.kirjeldus === '') {
                occupation.kirjeldus = undefined;
            }
            occupationStore.updateOccupation(occupation);
        };

        const handleDeleteOccupationButtonClick = (occupationCode: number) => {
            occupationStore.deleteOccupation(occupationCode);
        };

        const handleAddOccupationClick = () => {
            if (newOccupationCode === null) {
                return;
            }
            const newOccupation: Occupation = {
                amet_kood: newOccupationCode,
                nimetus: newOccupationName
            };
            if (newOccupationDescription != null) {
                newOccupation.kirjeldus = newOccupationDescription;
            }
            occupationStore.addOccupation(newOccupation);
            newOccupationCode = null;
            newOccupationName = '';
            newOccupationDescription = null;
        }


        return (
            <div>
                <h1 className="font-weight-heavy">Occupations page</h1>

                <Card>
                    <Card.Title>Add new occupation:</Card.Title>
                    <Card.Body>
                        <Form>
                            <Form.Group className="mb-3" controlId="addOccupationCode">
                                <Form.Label>Occupation code:</Form.Label>
                                <Form.Control
                                    placeholder="Enter occupation code (number)"
                                    type="number"
                                    onChange={(e) => newOccupationCode = Number(e.target.value)}
                                />
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="addOccupationName">
                                <Form.Label>Occupation name:</Form.Label>
                                <Form.Control
                                    placeholder="Enter occupation name"
                                    onChange={(e) => newOccupationName = e.target.value}/>
                            </Form.Group>
                            <Form.Group className="mb-3" controlId="addOccupationDescription">
                                <Form.Label>Occupation description:</Form.Label>
                                <Form.Control
                                    as="textarea" rows={3}
                                    placeholder="Enter occupation description (optional)"
                                    onChange={(e) => newOccupationDescription = e.target.value}/>
                            </Form.Group>
                            <Button variant="success" onClick={() => handleAddOccupationClick()}>Add new occupation</Button>
                        </Form>

                    </Card.Body>
                </Card>

                <div>
                    <h3 className="font-weight-heavy">All occupations:</h3>
                    <Table striped bordered hover responsive={true} title={"Occupations:"}>
                        <thead>
                        <tr>
                            <th>Occupation code</th>
                            <th>Name</th>
                            <th>Description</th>
                            <th>Edit occupation</th>
                            <th>Delete occupation</th>
                        </tr>
                        </thead>
                        <tbody>
                        {occupations.map((occupation) => (
                            <tr key={occupation.amet_kood}>
                                <td>
                                    {occupation.amet_kood}
                                </td>
                                <td>
                                    <InputGroup className={"mb-3"}>
                                        <FormControl
                                            placeholder={"Occupation name"}
                                            value={occupation.nimetus}
                                            onChange={(e) => occupation.nimetus = e.target.value}/>
                                    </InputGroup>
                                </td>
                                <td>
                                    <InputGroup className={"mb-3"}>
                                        <FormControl
                                            placeholder={"Occupation description"}
                                            value={occupation.kirjeldus}
                                            onChange={(e) => occupation.kirjeldus = e.target.value}/>
                                    </InputGroup>
                                </td>
                                <td><Button variant="info"
                                            onClick={() => handleEditOccupationButtonClick(occupation)}>Update</Button></td>
                                <td><Button variant="danger"
                                            onClick={() => handleDeleteOccupationButtonClick(occupation.amet_kood)}>Delete</Button>
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


export default Occupations;