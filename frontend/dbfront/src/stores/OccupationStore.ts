import {action, makeObservable, observable} from "mobx";
import API from '../util/ApiUtil';

export interface Occupation {
    _id?: string,
    amet_kood: number,
    nimetus: string,
    kirjeldus?: string
}

class OccupationStore {
    @observable public occupations: Occupation[] = [];

    constructor() {
        makeObservable(this);
    }

    @action
    public getOccupations = async () => {
        try {
            this.occupations = (await API.get('/occupations')).data;
        } catch (e) {
            this.occupations = [];
            console.error(e);
        }
    }

    @action
    public addOccupation = async (occupation: Occupation) => {
        try {
            await API.post('/occupations', {occupation}).then(this.getOccupations);
        } catch (e) {
            console.error(e);
        }
    }

    @action
    public updateOccupation = async (occupation: Occupation) => {
        try {
            await API.put('/occupations', {occupation}).then(this.getOccupations);
        } catch (e) {
            console.error(e);
        }
    }

    @action
    public deleteOccupation = async (occupationCode: number) => {
        try {
            await API.delete('/occupations/'+occupationCode).then(this.getOccupations);
        } catch (e) {
            console.error(e);
        }
    }
}

export default OccupationStore;
