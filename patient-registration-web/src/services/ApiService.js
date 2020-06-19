import axios from 'axios';
import {API_SERVER} from "../constants/apiUrl";

class ApiService {

  constructor() {
    console.log(`ApiService: ${API_SERVER}`);

    this.handleSuccess = this.handleSuccess.bind(this);
    this.handleError = this.handleError.bind(this);

    axios.defaults.baseURL = API_SERVER;
    const instance = axios.create({
      headers: {
        post: {
          'Content-Type': 'application/json'
        },
        put: {
          'Content-Type': 'application/json'
        }
      }
    });
    instance.interceptors.response.use(this.handleSuccess, this.handleError);
    this.instance = instance;
  }

  handleSuccess(response) {
    return response;
  }

  handleError(error) {
    console.log('Handle Error ' + JSON.stringify(error));
    if(error.response){
      return Promise.reject({...error.response.data, code: error.response.status});
    } else {
      return Promise.reject(error);
    }
  }

  get(url) {
    console.log('Get url : ' + url);
    return this.instance.get(url).catch(error => {
      console.log("Have an error when perform get " + url);
      return this.handleError(error);
    });
  }

  post(url, body) {
    console.log('Post url : ' + url);
    return this.instance.post(url, body).catch(error => {
      console.log("Have an error when perform post " + url);
      return this.handleError(error);
    });
  }

  put(url, body) {
    console.log('Put url : ' + url);
    return this.instance.put(url, body).catch(error => {
      console.log("Have an error when perform put " + url);
      return this.handleError(error);
    });
  }

  delete(url) {
    console.log('Delete url : ' + url);
    return this.instance.delete(url).catch(error => {
      console.log("Have an error when perform delete " + url);
      return this.handleError(error);
    });
  }
}

export default ApiService;
