import React from "react";
import {inject, observer} from "mobx-react";

import OccupationStore from "../stores/OccupationStore";
import EmploymentStore, {OccupationToEmployments} from "../stores/EmploymentStore";
import {Button, Table} from "react-bootstrap";

interface EmployeeDetailsProps {
    occupationStore?: OccupationStore,
    employmentStore?: EmploymentStore
}

interface State {
    listEntries?: OccupationToEmployments[]
}

@inject('employmentStore', 'occupationStore')
@observer
class OccupationsAndEmployeesReport extends React.Component<EmployeeDetailsProps, State> {

    constructor(props) {
        super(props);
        this.state = {
            listEntries: []
        }

    }

    public componentDidMount() {
        this.getEntries()
    }

    private async getEntries() {
        const listEntries: OccupationToEmployments[] = await this.props.employmentStore!!.mapOccupationsToEmployments();
        this.setState({listEntries: listEntries});
    }

    render() {
        if (this.state.listEntries == undefined) {
            return 'Loading...'
        }

        return (
            <Table striped bordered hover responsive={true} title={"List of employees:"}>
                <thead>
                <tr>
                    <th>Occupation code</th>
                    <th>Occupation name</th>
                    <th>No. of active employments</th>
                </tr>
                </thead>
                <tbody>
                {this.state.listEntries?.map((entry) => (
                    <tr key={entry.occupationCode}>
                        <td>
                            {entry.occupationCode}
                        </td>
                        <td>
                            {entry.occupationName}
                        </td>
                        <td>
                            {entry.numberOfEmployments}
                        </td>
                    </tr>
                ))}
                </tbody>
            </Table>
        );
    }
}

export default OccupationsAndEmployeesReport;