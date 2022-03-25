import React from "react";
import {inject, observer} from "mobx-react";
import EmploymentStore from "../stores/EmploymentStore";
import {Person} from "../stores/PersonStore";
import {Employee} from "../stores/EmployeeStore";

interface AllPersonEmploymentsListProps {
    person_id: string,
    employmentStore?: EmploymentStore
}


@inject('employmentStore')
@observer
class AllPersonEmploymentsList extends React.Component<AllPersonEmploymentsListProps> {

    constructor(props) {
        super(props);
    }

    public componentDidMount() {
        console.log("personid in employmentlist");
    }


    render() {
        return 'list of employments for: ' + this.props.person_id;
    }
}
//export default withRouter(...) <- ??
export default AllPersonEmploymentsList;