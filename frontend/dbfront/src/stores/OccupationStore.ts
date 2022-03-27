import {action, makeObservable, observable} from "mobx";
import API from '../util/ApiUtil';

export interface Occupation {
    _id?: string,
    amet_kood: number,
    nimetus: string,
    kirjeldus?: string
}

const occupationsEndpoint: string = '/occupations';

class OccupationStore {
    @observable public occupations: Occupation[] = [];

    constructor() {
        makeObservable(this);
    }

    @action
    public getOccupations = async () => {
        try {
            this.occupations = (await API.get(occupationsEndpoint)).data;
        } catch (e) {
            this.occupations = [];
            console.error(e);
        }
    }

    @action
    public getOccupationByOccupationCode = async (occupationCode: number) => {
        try {
            return (await API.get(occupationsEndpoint+'/'+occupationCode)).data;
        } catch (e) {
            this.occupations = [];
            console.error(e);
        }
    }

    @action
    public addOccupation = async (occupation: Occupation) => {
        try {
            await API.post(occupationsEndpoint, {occupation}).then(this.getOccupations);
        } catch (e) {
            console.error(e);
        }
    }

    @action
    public updateOccupation = async (occupation: Occupation) => {
        try {
            await API.put(occupationsEndpoint, {occupation}).then(this.getOccupations);
        } catch (e) {
            console.error(e);
        }
    }

    @action
    public deleteOccupation = async (occupationCode: number) => {
        try {
            await API.delete(occupationsEndpoint+'/'+occupationCode).then(this.getOccupations);
        } catch (e) {
            console.error(e);
        }
    }
}

export default OccupationStore;
