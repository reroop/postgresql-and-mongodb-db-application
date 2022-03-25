import CountryStore from "./CountryStore";
import OccupationStore from "./OccupationStore";
import EmployeeStatusTypeStore from "./EmployeeStatusTypeStore";
import PersonStore from "./PersonStore";
import EmployeeStore from "./EmployeeStore";
import EmploymentStore from "./EmploymentStore";

export class RootStore {
    public countryStore: CountryStore;
    public occupationStore: OccupationStore;
    public employeeStatusTypeStore: EmployeeStatusTypeStore;
    public personStore: PersonStore;
    public employeeStore: EmployeeStore;
    public employmentStore: EmploymentStore;

    constructor() {
        this.countryStore = new CountryStore();
        this.occupationStore = new OccupationStore();
        this.employeeStatusTypeStore = new EmployeeStatusTypeStore();
        this.personStore = new PersonStore();
        this.employeeStore = new EmployeeStore();
        this.employmentStore = new EmploymentStore();
    }
}