import React, {Component} from 'react';
import {withRouter} from 'react-router-dom';
import ApiService from "../services/ApiService";
import {PATIENTS_URL} from "../constants/apiUrl";
import PatientForm from "../components/PatientForm";
import {Button} from "primereact/button";
import {VIEW} from "../constants/actionMode";
import {PATIENT} from "../model/PatternModel";
import {Growl} from "primereact/growl";

class ViewPatient extends Component {

    constructor(props, context) {
        super(props, context);
        this.state = {
            id: '',
            patient: {...PATIENT},
            loading: false,
            errors: {},
        };
        this.apiService = new ApiService();
    }

    componentDidMount() {
        const state = this.props.location.state || {};
        const id = this.props.match.params.id || '';
        if (state.patient) {
            this.setState({id, patient: state.patient});
            if (state.showMessage) {
                this.showMessage(state.showMessage);
            }
        } else {
            this.setState({id, loading: true, errors: {}});
            this.apiService.get(`${PATIENTS_URL}/${id}`).then(res => {
                console.log("res "  + JSON.stringify(res.data))
                this.setState({loading: false, patient: res.data || {} });
            }).catch(e => {
                console.log('Can not get patient ' + JSON.stringify(e));
                this.props.history.replace("/404");
            });
        }
    }

    back = () => {
        this.props.history.push("/");
    };

    edit = () => {
        const { id } = this.state;
        this.props.history.push("/edit/" + id);
    };

    showMessage = (msg) => {
        this.growl.show({severity: 'success', summary: 'Success', detail: msg});
    };

    render() {
        const {patient, errors} = this.state;
        return (
            <div>
                <Growl ref={(el) => this.growl = el} />
                <PatientForm model={patient} errors={errors} onChange={this.onChange} mode={VIEW} />
                <div className="p-col-12 p-formgroup-inline" style={{padding: 20}}>
                    <div className="p-field">
                        <Button label="Back" onClick={this.back} style={{width:'100px'}} icon="pi pi-arrow-left" iconPos="left" className="p-button-raised p-button-secondary" />
                    </div>
                    <div className="p-field">
                        <Button label="Edit" onClick={this.edit} style={{width:'100px'}} icon="pi pi-pencil" iconPos="left" className="p-button-raised p-button-success" />
                    </div>
                </div>
            </div>
        );
    }
}

export default withRouter(ViewPatient);
