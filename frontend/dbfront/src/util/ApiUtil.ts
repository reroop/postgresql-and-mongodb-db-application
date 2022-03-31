import axios from 'axios';
import {stringify} from 'qs';

//import config from '../config'    //todo: set api url by config

const baseUrlMongoDbRef: string = "http://localhost:8080/mongoref"
const baseUrlMongoDbEmb: string = "http://localhost:8080/mongoemb"

const API = axios.create({
    baseURL: baseUrlMongoDbRef,
    timeout: 5000,
    paramsSerializer: (params) => stringify(params, {arrayFormat: 'repeat'})
});

export default API;