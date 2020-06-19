import React, {Component} from 'react';
import {Dialog} from "primereact/dialog";
import PropTypes from "prop-types";
import {Button} from "primereact/button";

class ConfirmDialog extends Component {

    onHide = () => {
        this.props.onHide();
    };

    onOk = () => {
        this.props.onOk();
    };

    renderFooter(name) {
        return (
            <div>
                <Button label="OK" icon="pi pi-check" onClick={this.onOk} />
                <Button label="Cancel" icon="pi pi-times" onClick={this.onHide} className="p-button-secondary"/>
            </div>
        );
    }

    render() {
        const { visible, message } = this.props;
        return (
            <Dialog header="Confirm" visible={visible} style={{width: '420px'}}
                    onHide={this.onHide}
                    footer={this.renderFooter('displayBasic')}>
                <p>{message}</p>
            </Dialog>
        );
    }
}

ConfirmDialog.propTypes = {
    onHide: PropTypes.func.isRequired,
    onOk: PropTypes.func.isRequired,
    message: PropTypes.string,
    visible: PropTypes.bool.isRequired
};

ConfirmDialog.defaultProps = {
    visible: false,
    message: 'Are you sure you want to delete this patient?',
};

export default ConfirmDialog;
