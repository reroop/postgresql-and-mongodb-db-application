import {action, makeObservable, observable} from "mobx";
import API from '../util/ApiUtil';

export interface Employment {
    _id?: string,
    isik_id: string,
    amet_kood: number;
    alguse_aeg: string,
    lopu_aeg?: string
}

const employmentsEndPoint: string = '/employments';

class EmploymentStore {
    @observable public allEmployments: Employment[] = [];
    //@observable public personEmployments: Employment[] =[];

    constructor() {
        makeObservable(this);
    }

    @action
    public getAllEmployments = async () => {
        try {
            this.allEmployments = (await API.get(employmentsEndPoint)).data;
        } catch (e) {
            this.allEmployments = [];
            console.error(e);
        }
    }

    //todo: change backend to return all employments for person
    @action
    public getAllEmploymentsForEmployee = async (person_id: string) => {
        try {
            return (await API.get(employmentsEndPoint+'/personId='+person_id)).data;
        } catch (e) {
            console.error(e);
        }
    }

    @action
    public getAllEmploymentsByOccupationCode = async (occupationCode: number) => {
        try {
            return (await API.get(employmentsEndPoint+'/employments/=occupationCode='+occupationCode)).data;
        } catch (e) {
            console.error(e);
        }
    }

    @action
    public addNewEmployment = async (employment: Employment) => {
        try {
            return (await API.post(employmentsEndPoint, {employment})).data;
        } catch (e) {
            console.error(e);
        }
    }

    @action
    public endEmployment = async (person_id: string, occupationCode: number) => {
        try {
            return (await API.put(employmentsEndPoint+'/personId='+person_id+'/occupationCode='+occupationCode)).data;
        } catch (e) {
            console.error(e);
        }
    }
}

export default EmploymentStore;