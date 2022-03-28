import {action, makeObservable, observable} from "mobx";
import API from '../util/ApiUtil';
import PersonStore, {Person} from "./PersonStore";
import CountryStore, {Country} from "./CountryStore";
import EmployeeStatusTypeStore, {EmployeeStatusType} from "./EmployeeStatusTypeStore";

export interface Employee {
    _id?: string,
    isik_id: string,
    tootaja_seisundi_liik_kood: number,
    mentor_id?: string|null
}

export interface PersonAsEmployee {
    person_id: string,
    personCountryCode: string,
    personIdCode: string,
    personGivenName?: string,
    personSurname?: string,
    employeeStatus: string,
    personRegDate?: string
    mentor_id?: string
}


const employeesEndpoint: string = '/employees';
export const EMPLOYEE_END_EMPLOYMENTS_STATUS_CODE: number = 6;

class EmployeeStore {
    @observable public employees: Employee[] = [];
    @observable public personsAsEmployees: PersonAsEmployee[] = [];
    private personStore: PersonStore = new PersonStore();
    private countryStore: CountryStore = new CountryStore();
    private employeeStatusStore: EmployeeStatusTypeStore = new EmployeeStatusTypeStore();

    constructor() {
        makeObservable(this);
    }

    public mapEmployeesToPersons() {
        this.personsAsEmployees = [];
        this.employees.forEach(async (employee) => {
            let person: Person = await this.personStore.getPersonBy_id(employee.isik_id);
            let personCountry: Country = await this.countryStore.getCountryByCountryCode(person.riik_kood);
            let employeeStatus: EmployeeStatusType = await this.employeeStatusStore.getEmployeeStatusTypeByStatusCode(employee.tootaja_seisundi_liik_kood);

            let personAsEmployee: PersonAsEmployee = {
                person_id: person._id!!,
                personCountryCode: person.riik_kood,
                personIdCode: person.isikukood,
                personGivenName: person.eesnimi,
                personSurname: person.perenimi,
                employeeStatus: employeeStatus.nimetus,
                personRegDate: person.reg_aeg,
                mentor_id: employee.mentor_id!!
            }
            this.personsAsEmployees.push(personAsEmployee);
        })
    }

    public async reloadData() {
        await this.getEmployees();
        this.mapEmployeesToPersons()
    }

    public setEmployeeStatusToEnded(person_id: string) {
        const employee: Employee = {
            isik_id: person_id,
            tootaja_seisundi_liik_kood: EMPLOYEE_END_EMPLOYMENTS_STATUS_CODE
        };
        return this.updateEmployee(employee);
    }


    @action
    public getEmployees = async () => {
        try {
            this.employees = (await API.get(employeesEndpoint)).data;
            this.mapEmployeesToPersons();
        } catch (e) {
            this.employees = [];
            this.personsAsEmployees = [];
            console.error(e);
        }
    }

    @action
    public getEmployeeByPerson_id = async (person_id: string) => {
        try {
            return (await API.get(employeesEndpoint+'/'+person_id)).data;
        } catch (e) {
            console.error(e);
        }
    }

    @action
    public addEmployee = async (employee: Employee) => {
        try {
            return (await API.post(employeesEndpoint, {employee})).data;
        } catch (e) {
            console.error(e);
        }
    }

    @action
    public updateEmployee = async (employee: Employee) => {
        try {
            return  (await API.put(employeesEndpoint, {employee})).data;
        } catch (e) {
            console.error(e);
        }
    }

    @action
    public deleteEmployee = async (person_id: string) => {
        try {
            return (await API.delete(employeesEndpoint + '/' + person_id)).data;
        } catch (e) {
            console.error(e);
        }
    }
}

export default EmployeeStore;