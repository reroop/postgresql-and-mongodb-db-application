import {action, makeObservable, observable} from "mobx";
import API from '../util/ApiUtil';

export interface PersonStatusType {
    _id?: string,
    person_status_type_code: number | null,
    name: string | null,
    description?: string | null
}

const personStatusTypesEndpoint: string = '/personStatusTypes';

class PersonStatusTypeStore {
    @observable public personStatusTypes: PersonStatusType[] = [];

    constructor() {
        makeObservable(this);
    }

    @action
    public getPersonStatusTypes = async () => {
        try {
            this.personStatusTypes = (await API.get(personStatusTypesEndpoint)).data;
        } catch (e) {
            this.personStatusTypes = [];
            console.error(e);
        }
    }

    @action
    public getPersonStatusTypeByStatusCode = async (statusCode: number) => {
        try {
            return (await API.get(personStatusTypesEndpoint+'/'+statusCode)).data;
        } catch (e) {
            this.personStatusTypes = [];
            console.error(e);
        }
    }

    @action
    public addPersonStatusType = async (personStatusType: PersonStatusType) => {
        try {
            await API.post(personStatusTypesEndpoint,{personStatusType}).then(this.getPersonStatusTypes);
        } catch (e) {
            console.error(e);
            // @ts-ignore
            window.alert(e.response.data.message);
        }
    }
}

export default PersonStatusTypeStore;