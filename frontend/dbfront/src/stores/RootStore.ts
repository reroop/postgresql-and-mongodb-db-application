import CountryStore from "./CountryStore";
import OccupationStore from "./OccupationStore";

export class RootStore {
    public countryStore: CountryStore;
    public occupationStore: OccupationStore;

    constructor() {
        this.countryStore = new CountryStore();
        this.occupationStore = new OccupationStore();
    }
}