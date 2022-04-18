import {action, makeObservable, observable} from "mobx";
import API from '../util/ApiUtil';

export interface Person {
    _id?: string,
    nat_id_code: string,
    country_code: string,
    e_mail: string,
    birth_date: string,
    reg_time?: string,
    given_name?: string|null,
    surname?: string|null,
    address?: string|null,
    tel_nr?: string|null
}

const personsEndpoint: string = '/persons';

class PersonStore {
    @observable public persons: Person[] = [];

    constructor() {
        makeObservable(this);
    }

    @action
    public getPersons = async () => {
        try {
            this.persons = (await API.get(personsEndpoint)).data;
        } catch (e) {
            this.persons = [];
            console.error(e);
        }
    }

    @action
    public getPersonBy_id = async (person_id: string) => {
        try {
            return (await API.get(personsEndpoint+'/'+person_id)).data;
        } catch (e) {
            console.error(e);
        }
    }

    @action
    public addPerson = async (person: Person) => {
        try {
            const response = await API.post(personsEndpoint, {person});
            await this.getPersons()
            return response.data;
        } catch (e) {
            console.error(e);
            // @ts-ignore
            window.alert(e.response.data.message);
            return null;
        }
    }

    @action
    public updatePerson = async (person: Person) => {
        try {
            await API.put(personsEndpoint, {person}).then(this.getPersons);
        } catch (e) {
            console.error(e);
            // @ts-ignore
            window.alert(e.response.data.message);
        }
    }
}

export default PersonStore;