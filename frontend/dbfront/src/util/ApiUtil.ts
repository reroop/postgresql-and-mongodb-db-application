import axios from 'axios';
import {stringify} from 'qs';

//import config from '../config'    //todo: set api url by config

const baseUrl: string = "http://localhost:8080/mongoref"

const API = axios.create({
    baseURL: baseUrl,
    timeout: 5000,
    paramsSerializer: (params) => stringify(params, {arrayFormat: 'repeat'})
});

export default API;