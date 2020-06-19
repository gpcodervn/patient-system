import React, {Component} from 'react';
import {withRouter} from 'react-router-dom';
import ApiService from "../services/ApiService";
import {PATIENTS_URL} from "../constants/apiUrl";
import {Growl} from "primereact/growl";
import PatientForm from "../components/PatientForm";
import {Button} from "primereact/button";
import {ADD} from "../constants/actionMode";
import {PATIENT} from "../model/PatternModel";

class AddPatient extends Component {

    constructor(props, context) {
        super(props, context);
        this.state = {
            patient: {...PATIENT},
            loading: false,
            errors: {},
        };
        this.apiService = new ApiService();
    }

    back = () => {
        this.props.history.push("/");
    };

    save = () => {
        const { patient } = this.state;
        this.setState({loading: true, errors: {}});
        this.apiService.post(`${PATIENTS_URL}`, JSON.stringify(patient)).then(res => {
            this.props.history.push("/view/" + res.data.id, { patient: res.data, showMessage: 'Added patient successfully!' });
            this.setState({loading: false});
        }).catch(e => {
            console.log('Can not save patient ' + JSON.stringify(e));
            this.setState({loading: false});
            if (e.code === 400) {
                this.setState({errors: e.errors || {}});
            }
            this.showError(`Can not save patient because of "${e.message}"`);
        });
    };

    onChange = (field, value) => {
        const {patient} = this.state;
        patient[field] = value;
        this.setState({ patient });
    };

    showError = (msg) => {
        this.growl.show({severity: 'error', summary: 'Error', detail: msg});
    };

    render() {
        const {patient, errors} = this.state;
        return (
            <div>
                <Growl ref={(el) => this.growl = el} />
                <PatientForm model={patient} errors={errors} onChange={this.onChange} mode={ADD} />
                <div className="p-col-12 p-formgroup-inline" style={{padding: 20}}>
                    <div className="p-field">
                        <Button label="Back" onClick={this.back} style={{width:'100px'}} icon="pi pi-arrow-left" iconPos="left" className="p-button-raised p-button-secondary" />
                    </div>
                    <div className="p-field">
                        <Button label="Save" onClick={this.save} style={{width:'100px'}} icon="pi pi-check" iconPos="left" className="p-button-raised p-button-success" />
                    </div>
                </div>
            </div>
        );
    }
}

export default withRouter(AddPatient);
