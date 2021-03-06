import {action, makeObservable, observable} from "mobx";
import API from '../util/ApiUtil';

export interface Country {
    _id?: string,
    country_code: string | null,
    name: string | null
}

const countriesEndpoint: string = '/countries';

class CountryStore {
    @observable public countries: Country[] = [];

    constructor() {
        makeObservable(this);
    }

    @action
    public getCountries = async () => {
        try {
            this.countries = (await API.get(countriesEndpoint)).data;
        } catch (e) {
            this.countries = [];
            console.error(e);
        }
    }

    @action
    public getCountryByCountryCode = async (countryCode: string) => {
        try {
            return (await API.get(countriesEndpoint+'/'+countryCode)).data;
        } catch (e) {
            console.error(e);
        }
    }

    @action
    public addCountry = async(country: Country) => {
        try {
            await API.post(countriesEndpoint, {country}).then(this.getCountries)
        } catch (e) {
            console.error(e);
            // @ts-ignore
            window.alert(e.response.data.message);
        }
    }
}

export default CountryStore;