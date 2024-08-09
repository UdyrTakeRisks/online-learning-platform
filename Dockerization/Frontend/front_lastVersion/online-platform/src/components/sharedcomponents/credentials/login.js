import React, { useState } from 'react';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import { useNavigate } from 'react-router-dom';

function Login() {
    const [name, setName] = useState('');
    const [password, setPassword] = useState('');
    const [role, setRole] = useState('student'); // Default role is student
    const [message, setMessage] = useState('');
    const navigate  = useNavigate();
    const handleLogin = async () => {
        try {
            let loginEndpoint = '';
            if (role === 'instructor') {
                loginEndpoint = 'http://localhost:8080/online-platform-app-1.0-SNAPSHOT/api/instructor/login';
            } else {
                loginEndpoint = 'http://localhost:8080/online-platform-app-1.0-SNAPSHOT/api/student/login';
            }

            const response = await axios.post(loginEndpoint, { name, password });
            // Handle successful login
            setMessage(`${role.charAt(0).toUpperCase() + role.slice(1)} login successful`);
            // Redirect based on role
            if (role === 'instructor') {
                navigate('/instructor-dashboard');
            } else {
                navigate('/student-dashboard');
            }
        } catch (error) {
            console.error("Login failed", error);
            setMessage(`Login failed: ${error.message}`);
        }
    };

    return (
        <div className="container">
            <div className="row justify-content-center mt-5">
                <div className="col-md-6">
                    <div className="card">
                        <div className="card-body">
                            <h2 className="card-title">Login</h2>
                            {message && <div className="alert alert-info">{message}</div>}
                            <form>
                                <div className="form-group">
                                    <label>Name:</label>
                                    <input
                                        type="text"
                                        value={name}
                                        onChange={e => setName(e.target.value)}
                                        className="form-control"
                                        placeholder="Enter name"
                                    />
                                </div>
                                <div className="form-group">
                                    <label>Password:</label>
                                    <input
                                        type="password"
                                        value={password}
                                        onChange={e => setPassword(e.target.value)}
                                        className="form-control"
                                        placeholder="Enter password"
                                    />
                                </div>
                                <div className="form-group">
                                    <label>Role:</label>
                                    <select
                                        value={role}
                                        onChange={e => setRole(e.target.value)}
                                        className="form-control"
                                    >
                                        <option value="student">Student</option>
                                        <option value="instructor">Instructor</option>
                                    </select>
                                </div>
                                <button type="button" onClick={handleLogin} className="btn btn-primary">Login</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default Login;
