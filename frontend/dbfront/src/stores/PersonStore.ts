import {action, makeObservable, observable} from "mobx";
import API from '../util/ApiUtil';

export interface Person {
    _id?: string,
    isikukood: string,
    riik_kood: string,
    e_meil: string,
    synni_kp: string,
    reg_aeg?: string,
    eesnimi?: string,
    perenimi?: string,
    elukoht?: string
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
            return null;
        }
    }

    @action
    public updatePerson = async (person: Person) => {
        try {
            await API.put(personsEndpoint, {person}).then(this.getPersons);
        } catch (e) {
            console.error(e);
        }
    }

    @action deletePerson = async (person_id: string) => {
        try {
            await API.delete(personsEndpoint + '/' + person_id).then(this.getPersons);
        } catch (e) {
            console.error(e);
        }
    }

}

export default PersonStore;