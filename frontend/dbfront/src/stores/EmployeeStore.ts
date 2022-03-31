import {action, makeObservable, observable} from "mobx";
import API from '../util/ApiUtil';
import PersonStore, {Person} from "./PersonStore";
import CountryStore, {Country} from "./CountryStore";
import EmployeeStatusTypeStore, {EmployeeStatusType} from "./EmployeeStatusTypeStore";

export interface Employee {
    _id?: string,
    person_id: string,
    employee_status_type_code: number,
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
export const EMPLOYEE_END_EMPLOYMENTS_STATUS_CODE: number = 5;      //CHANGE ONLY IF CHANGE IS DONE IN DATABASE ASWELL

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
            let person: Person = await this.personStore.getPersonBy_id(employee.person_id);
            let personCountry: Country = await this.countryStore.getCountryByCountryCode(person.country_code);
            let employeeStatus: EmployeeStatusType = await this.employeeStatusStore.getEmployeeStatusTypeByStatusCode(employee.employee_status_type_code);

            let personAsEmployee: PersonAsEmployee = {
                person_id: person._id!!,
                personCountryCode: person.country_code,
                personIdCode: person.nat_id_code,
                personGivenName: person.given_name,
                personSurname: person.surname,
                employeeStatus: employeeStatus.name,
                personRegDate: person.reg_time,
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
            person_id: person_id,
            employee_status_type_code: EMPLOYEE_END_EMPLOYMENTS_STATUS_CODE
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