import React, {Component} from 'react';
import {Button} from "primereact/button";
import {withRouter} from 'react-router-dom';

class Error404 extends Component {

    back = () => {
        this.props.history.replace("/");
    };

    render() {
        return (
            <div className="p-fluid gp-error-404" style={{padding: 20}}>
                <h2 className="title">Resource not found</h2>
                <Button label="Home Page" onClick={this.back} style={{width:'150px'}} icon="pi pi-arrow-left" iconPos="left" className="p-button-raised p-button-success" />
            </div>
        );
    }
}

export default withRouter(Error404);
