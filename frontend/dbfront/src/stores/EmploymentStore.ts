import {action, makeObservable, observable} from "mobx";
import API from '../util/ApiUtil';
import OccupationStore, {Occupation} from "./OccupationStore";

export interface Employment {
    _id?: string,
    isik_id: string,
    amet_kood: number;
    alguse_aeg: string,
    lopu_aeg?: string
}

export interface EmploymentWithOccupation {
    employment: Employment,
    occupation?: Occupation
}

const employmentsEndPoint: string = '/employments';

class EmploymentStore {
    @observable public allEmployments: Employment[] = [];
    @observable public personEmploymentsWithOccupations: EmploymentWithOccupation[] = [];
    @observable public personEmployments: Employment[] = [];
    private occupationStore: OccupationStore = new OccupationStore();

    constructor() {
        makeObservable(this);
    }


    public mapPersonEmploymentsToOccupations() {
        this.personEmploymentsWithOccupations = [];
        this.personEmployments.forEach(async (employment) => {
            let entry: EmploymentWithOccupation = {
                employment: employment
            }
            entry.occupation = await this.occupationStore.getOccupationByOccupationCode(employment.amet_kood)
            this.personEmploymentsWithOccupations.push(entry);
        })
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

    @action
    public getAllEmploymentsForEmployee = async (person_id: string) => {
        try {
            this.personEmployments = (await API.get(employmentsEndPoint+'/personId='+person_id)).data;
            this.mapPersonEmploymentsToOccupations();
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