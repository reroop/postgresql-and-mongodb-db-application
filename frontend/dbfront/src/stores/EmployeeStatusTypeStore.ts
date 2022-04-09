import {action, makeObservable, observable} from "mobx";
import API from '../util/ApiUtil';

export interface EmployeeStatusType {
    _id?: string,
    employee_status_type_code: number,
    name: string,
    description?: string
}

const employeeStatusTypesEndpoint: string = '/employeeStatusTypes';

class EmployeeStatusTypeStore {
    @observable public employeeStatusTypes: EmployeeStatusType[] = [];

    constructor() {
        makeObservable(this);
    }

    @action
    public getEmployeeStatusTypes = async () => {
        try {
            this.employeeStatusTypes = (await API.get(employeeStatusTypesEndpoint)).data;
        } catch (e) {
            this.employeeStatusTypes = [];
            console.error(e);
        }
    }

    @action
    public getEmployeeStatusTypeByStatusCode = async (statusCode: number) => {
        try {
            return (await API.get(employeeStatusTypesEndpoint+'/'+statusCode)).data;
        } catch (e) {
            this.employeeStatusTypes = [];
            console.error(e);
        }
    }

    @action
    public addEmployeeStatusType = async (employeeStatusType: EmployeeStatusType) => {
        try {
           await API.post(employeeStatusTypesEndpoint,{employeeStatusType}).then(this.getEmployeeStatusTypes);
        } catch (e) {
            console.error(e);
            // @ts-ignore
            window.alert(e.response.data.message);
        }
    }
}

export default EmployeeStatusTypeStore;