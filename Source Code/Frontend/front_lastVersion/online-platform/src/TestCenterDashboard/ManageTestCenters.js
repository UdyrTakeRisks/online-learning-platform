import React, { useState } from 'react';
import axios from 'axios';

function ManageTestCenters() {
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [address, setAddress] = useState('');
    const [location, setLocation] = useState('');
    const [branches, setBranches] = useState([]);
    const [bio, setBio] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('/api/testcenters', {
                name,
                email,
                address,
                location,
                branches,
                bio
            });
            console.log('Test center created:', response.data);

            // Clear form fields after submission
            setName('');
            setEmail('');
            setAddress('');
            setLocation('');
            setBranches([]);
            setBio('');
        } catch (error) {
            console.error('Error creating test center:', error);
        }
    };

    return (
        <div className="card">
            <div className="card-body">
                <h5 className="card-title">Create Test Centers</h5>
                <form onSubmit={handleSubmit}>
                    <div className="mb-3">
                        <label htmlFor="name" className="form-label">Name</label>
                        <input type="text" className="form-control" id="name" value={name} onChange={(e) => setName(e.target.value)} required />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="email" className="form-label">Email</label>
                        <input type="email" className="form-control" id="email" value={email} onChange={(e) => setEmail(e.target.value)} required />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="address" className="form-label">Address</label>
                        <input type="text" className="form-control" id="address" value={address} onChange={(e) => setAddress(e.target.value)} required />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="location" className="form-label">Location</label>
                        <input type="text" className="form-control" id="location" value={location} onChange={(e) => setLocation(e.target.value)} required />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="branches" className="form-label">Branches</label>
                        <input type="text" className="form-control" id="branches" value={branches} onChange={(e) => setBranches(e.target.value.split(','))} required />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="bio" className="form-label">Bio</label>
                        <textarea className="form-control" id="bio" rows="3" value={bio} onChange={(e) => setBio(e.target.value)} required></textarea>
                    </div>
                    <button type="submit" className="btn btn-primary">Create Test Center</button>
                </form>
            </div>
        </div>
    );
}

export default ManageTestCenters;
