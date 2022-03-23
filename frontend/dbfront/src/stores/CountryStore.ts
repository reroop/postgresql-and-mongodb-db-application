import {action, makeObservable, observable} from "mobx";
import API from '../util/ApiUtil';

export interface Country {
    riik_kood: string,
    nimetus: string
}

class CountryStore {
    @observable public countries: Country[];

    constructor() {
        makeObservable(this);
    }

    @action
    public getCountries = async () => {
        try {
            this.countries = (await API.get('/countries')).data;
        } catch (e) {
            this.countries = [];
            console.error(e);
        }
    }

}

export default CountryStore;