import React, {Component} from 'react';
import {withRouter} from 'react-router-dom';
import ApiService from "../services/ApiService";
import {PATIENTS_URL} from "../constants/apiUrl";
import {Growl} from "primereact/growl";
import PatientForm from "../components/PatientForm";
import {Button} from "primereact/button";
import {EDIT} from "../constants/actionMode";
import {PATIENT} from "../model/PatternModel";

class EditPatient extends Component {

    constructor(props, context) {
        super(props, context);
        this.state = {
            id: this.props.match.params.id || '',
            patient: {...PATIENT},
            loading: false,
            errors: {},
        };
        this.apiService = new ApiService();
    }

    componentDidMount() {
        const { id } = this.state;
        this.setState({loading: true, errors: {}});
        this.apiService.get(`${PATIENTS_URL}/${id}`).then(res => {
            console.log("res "  + JSON.stringify(res))
            this.setState({loading: false, patient: res.data || {} });
        }).catch(e => {
            console.log('Can not get patient ' + JSON.stringify(e));
            this.props.history.replace("/404");
        });
    }

    back = () => {
        this.props.history.push("/");
    };

    update = () => {
        const { id, patient } = this.state;
        this.setState({loading: true, errors: {}});
        this.apiService.put(`${PATIENTS_URL}/${id}`, JSON.stringify(patient)).then(res => {
            this.props.history.push("/view/" + res.data.id, { patient: res.data, showMessage: 'Updated patient successfully!' });
            this.setState({loading: false});
        }).catch(e => {
            console.log('Can not update patient ' + JSON.stringify(e));
            this.setState({loading: false});
            if (e.code === 400) {
                this.setState({errors: e.errors || {}});
            }
            this.showError(`Can not update patient because of "${e.message}"`);
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
                <PatientForm model={patient} errors={errors} onChange={this.onChange} mode={EDIT} />
                <div className="p-col-12 p-formgroup-inline" style={{padding: 20}}>
                    <div className="p-field">
                        <Button label="Back" onClick={this.back} style={{width:'100px'}} icon="pi pi-arrow-left" iconPos="left" className="p-button-raised p-button-secondary" />
                    </div>
                    <div className="p-field">
                        <Button label="Update" onClick={this.update} style={{width:'100px'}} icon="pi pi-check" iconPos="left" className="p-button-raised p-button-success" />
                    </div>
                </div>
            </div>
        );
    }
}

export default withRouter(EditPatient);
