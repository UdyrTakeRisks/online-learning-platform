import React from 'react';
import ReactDOM from 'react-dom';
import App from './App'; // Import the main App component

ReactDOM.render(
    <React.StrictMode>
        <App /> {/* Render the App component */}
    </React.StrictMode>,
    document.getElementById('root') // Mount the App component to the HTML root element
);
