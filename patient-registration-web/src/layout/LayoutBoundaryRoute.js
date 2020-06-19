import React from 'react';
import {Route} from 'react-router-dom';

const LayoutBoundaryRoute = ({ component: Component, layout: Layout, ...rest }) => {
    let layoutBoundary = props => (
        <Layout>
            <Component {...props} />
        </Layout>
    );

    return <Route {...rest} render={layoutBoundary} />;
};

export default LayoutBoundaryRoute;
