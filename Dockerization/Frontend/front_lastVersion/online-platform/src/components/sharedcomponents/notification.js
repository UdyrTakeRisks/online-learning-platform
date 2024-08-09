import React from 'react';

function Notification({ message, type }) {
    const getNotificationStyle = () => {
        switch (type) {
            case 'success':
                return { backgroundColor: 'green', color: 'white' };
            case 'error':
                return { backgroundColor: 'red', color: 'white' };
            case 'info':
                return { backgroundColor: 'blue', color: 'white' };
            case 'warning':
                return { backgroundColor: 'orange', color: 'white' };
            default:
                return {};
        }
    };

    return (
        <div style={{ ...getNotificationStyle(), padding: '10px', borderRadius: '5px', margin: '10px 0' }}>
            {message}
        </div>
    );
}

export default Notification;
