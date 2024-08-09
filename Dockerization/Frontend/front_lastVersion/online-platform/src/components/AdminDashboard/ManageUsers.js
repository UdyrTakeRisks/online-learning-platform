import React, { useState, useEffect } from 'react';
import axios from 'axios';

function ManageUsers() {
    const [users, setUsers] = useState([]);

    useEffect(() => {
        axios.get('/api/users')
            .then(response => setUsers(response.data))
            .catch(error => console.error("Error fetching users", error));
    }, []);

    const handleDeleteUser = (userId) => {
        axios.delete(`/api/users/${userId}`)
            .then(() => setUsers(users.filter(user => user.id !== userId)))
            .catch(error => console.error("Error deleting user", error));
    };

    return (
        <div className="card">
            <div className="card-body">
                <h5 className="card-title">Users</h5>
                <ul className="list-group">
                    {users.map(user => (
                        <li key={user.id} className="list-group-item d-flex justify-content-between align-items-center">
                            {user.name} - {user.email}
                            <button className="btn btn-danger btn-sm" onClick={() => handleDeleteUser(user.id)}>Delete</button>
                        </li>
                    ))}
                </ul>
            </div>
        </div>
    );
}

export default ManageUsers;
