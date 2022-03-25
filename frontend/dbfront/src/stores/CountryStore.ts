import {action, makeObservable, observable} from "mobx";
import API from '../util/ApiUtil';

export interface Country {
    _id?: string,
    riik_kood: string,
    nimetus: string
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
        }
    }

    @action
    public updateCountry = async (country: Country) => {
        try {
            await API.put(countriesEndpoint, {country}).then(this.getCountries);
        } catch (e) {
            console.error(e);
        }
    }

    @action
    public deleteCountry = async (countryCode: string) => {
        try {
            await API.delete(countriesEndpoint+countryCode).then(this.getCountries);
        } catch (e) {
            console.error(e);
        }
    }

}

export default CountryStore;