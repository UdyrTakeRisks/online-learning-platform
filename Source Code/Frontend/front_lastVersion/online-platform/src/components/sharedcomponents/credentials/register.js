import React, { useState } from 'react';

function Register() {
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [affiliation, setAffiliation] = useState('');
    const [bio, setBio] = useState('');
    const [role, setRole] = useState('student');
    const [message, setMessage] = useState('');


    const handleRegister = async (e) => {
        e.preventDefault();
        const user = { name, email, password, affiliation, bio };
        let url = role === "instructor" ? "http://localhost:8080/online-platform-app-1.0-SNAPSHOT/api/instructor/register" : "http://localhost:8080/online-platform-app-1.0-SNAPSHOT/api/student/register";

        try {
            const response = await fetch(url, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(user)
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }

            const data = await response.json();
            setMessage(`${role.charAt(0).toUpperCase() + role.slice(1)} registration successful: ${data.message}`);
        } catch (error) {
            console.error("Registration failed", error);
            setMessage(`Registration failed: ${error.message}`);
        }
    };


    return (
        <div className="container mt-5">
            <h2>Register</h2>
            {message && <div className="alert alert-info">{message}</div>}
            <form onSubmit={handleRegister}>
                <div className="form-group">
                    <select value={role} onChange={e => setRole(e.target.value)} className="form-control">
                        <option value="student">Student</option>
                        <option value="instructor">Instructor</option>
                    </select>
                </div>
                <div className="form-group">
                    <input type="text" value={name} onChange={e => setName(e.target.value)} className="form-control" placeholder="Name" />
                </div>
                <div className="form-group">
                    <input type="email" value={email} onChange={e => setEmail(e.target.value)} className="form-control" placeholder="Email" />
                </div>
                <div className="form-group">
                    <input type="password" value={password} onChange={e => setPassword(e.target.value)} className="form-control" placeholder="Password" />
                </div>
                <div className="form-group">
                    <input type="text" value={affiliation} onChange={e => setAffiliation(e.target.value)} className="form-control" placeholder="Affiliation" />
                </div>
                <div className="form-group">
                    <input type="text" value={bio} onChange={e => setBio(e.target.value)} className="form-control" placeholder="Bio" />
                </div>
                <button type="submit" className="btn btn-primary">Register</button>
            </form>
        </div>
    );
}

export default Register;
