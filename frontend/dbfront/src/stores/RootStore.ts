import CountryStore from "./CountryStore";

export class RootStore {
    public countryStore: CountryStore;

    constructor() {
        this.countryStore = new CountryStore();
    }
}