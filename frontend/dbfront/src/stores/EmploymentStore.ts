import {action, makeObservable, observable} from "mobx";
import API from '../util/ApiUtil';
import OccupationStore, {Occupation} from "./OccupationStore";

export interface Employment {
    _id?: string,
    person_id: string,
    occupation_code?: number;
    start_time?: string,
    end_time?: string
}

export interface OccupationToEmployments {
    occupationCode: number,
    occupationName: string,
    numberOfEmployments: number
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

    public async mapOccupationsToEmployments():Promise<OccupationToEmployments[]> {
        await this.occupationStore.getOccupations();
        const result: OccupationToEmployments[] = [];

        for (const occupation of this.occupationStore.occupations) {
            const employmentsInOccupation: Employment[] = await this.getAllEmploymentsByOccupationCode(occupation.occupation_code);

            let entry: OccupationToEmployments = {
                occupationCode: occupation.occupation_code,
                occupationName: occupation.name,
                numberOfEmployments: employmentsInOccupation.length
            }
            result.push(entry);
        }
        return result;
    }

    @action
    public mapPersonEmploymentsToOccupations() {
        this.personEmploymentsWithOccupations = [];
        this.personEmployments.forEach(async (employment) => {
            let entry: EmploymentWithOccupation = {
                employment: employment
            }
            entry.occupation = await this.occupationStore.getOccupationByOccupationCode(employment.occupation_code!!)
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
            return (await API.get(employmentsEndPoint+'/occupationCode='+occupationCode)).data;
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
            // @ts-ignore
            window.alert(e.response.data.message);
        }
    }

    @action
    public endEmployment = async (employment: Employment) => {
        try {
            return (await API.put(employmentsEndPoint, {employment})).data;
        } catch (e) {
            console.error(e);
            // @ts-ignore
            window.alert(e.response.data.message);
        }
    }

    @action
    public endAllEmployments = async (employment: Employment) => {
        try {
            return (await API.put(employmentsEndPoint+'/endEmployments', {employment})).data;
        } catch (e) {
            console.error(e);
            // @ts-ignore
            window.alert(e.response.data.message);
        }
    }
}

export default EmploymentStore;