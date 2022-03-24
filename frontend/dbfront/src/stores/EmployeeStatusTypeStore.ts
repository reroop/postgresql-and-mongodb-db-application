import {action, makeObservable, observable} from "mobx";
import API from '../util/ApiUtil';

export interface EmployeeStatusType {
    _id?: string,
    tootaja_seisundi_liik_kood: number,
    nimetus: string,
    kirjeldus?: string
}

class EmployeeStatusTypeStore {
    @observable public employeeStatusTypes: EmployeeStatusType[] = [];

    constructor() {
        makeObservable(this);
    }

    @action
    public getEmployeeStatusTypes = async () => {
        try {
            this.employeeStatusTypes = (await API.get('/employeeStatusTypes')).data;
        } catch (e) {
            this.employeeStatusTypes = [];
            console.log(e);
        }
    }

    @action
    public addEmployeeStatusType = async (employeeStatusType: EmployeeStatusType) => {
        try {
           await API.post('/employeeStatusTypes' ,{employeeStatusType}).then(this.getEmployeeStatusTypes);
        } catch (e) {
            console.log(e);
        }
    }

    @action
    public updateEmployeeStatusType = async (employeeStatusType: EmployeeStatusType) => {
        try {
            await API.put('/employeeStatusTypes' ,{employeeStatusType}).then(this.getEmployeeStatusTypes);
        } catch (e) {
            console.log(e);
        }
    }

    @action
    public deleteEmployeeStatusType = async (employeeStatusTypeCode: number) => {
        try {
            await API.delete(('/employeeStatusTypes/'+employeeStatusTypeCode)).then(this.getEmployeeStatusTypes)
        } catch (e) {
            console.log(e);
        }
    }

}

export default EmployeeStatusTypeStore;