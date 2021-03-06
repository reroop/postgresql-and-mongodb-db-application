import {action, makeObservable, observable} from "mobx";
import API from '../util/ApiUtil';

export interface Occupation {
    _id?: string,
    occupation_code: number,
    name: string,
    description?: string
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
            return (await API.post(occupationsEndpoint, {occupation})).data;
        } catch (e) {
            console.error(e);
            // @ts-ignore
            window.alert(e.response.data.message);
        }
    }
}

export default OccupationStore;
