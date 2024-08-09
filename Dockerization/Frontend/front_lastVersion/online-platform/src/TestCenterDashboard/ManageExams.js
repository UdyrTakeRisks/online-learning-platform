import React, { useState } from 'react';
import axios from 'axios';

function ManageExams() {
    const [name, setName] = useState('');
    const [duration, setDuration] = useState('');
    const [availableDates, setAvailableDates] = useState('');
    const [scheduledDates, setScheduledDates] = useState('');
    const [grades, setGrades] = useState('');
    const [bio, setBio] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('/api/exams', {
                name,
                duration,
                availableDates: availableDates.split(',').map(date => date.trim()),
                scheduledDates: scheduledDates.split(',').map(date => date.trim()),
                grades,
                bio
            });
            console.log('Exam created:', response.data);

            // Clear form fields after submission
            setName('');
            setDuration('');
            setAvailableDates('');
            setScheduledDates('');
            setGrades('');
            setBio('');
        } catch (error) {
            console.error('Error creating exam:', error);
        }
    };

    return (
        <div className="card">
            <div className="card-body">
                <h5 className="card-title">Manage Exams</h5>
                <form onSubmit={handleSubmit}>
                    <div className="mb-3">
                        <label htmlFor="name" className="form-label">Name</label>
                        <input type="text" className="form-control" id="name" value={name} onChange={(e) => setName(e.target.value)} required />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="duration" className="form-label">Duration</label>
                        <input type="text" className="form-control" id="duration" value={duration} onChange={(e) => setDuration(e.target.value)} required />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="availableDates" className="form-label">Available Dates (comma-separated)</label>
                        <input type="text" className="form-control" id="availableDates" value={availableDates} onChange={(e) => setAvailableDates(e.target.value)} required />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="scheduledDates" className="form-label">Scheduled Dates (comma-separated)</label>
                        <input type="text" className="form-control" id="scheduledDates" value={scheduledDates} onChange={(e) => setScheduledDates(e.target.value)} required />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="grades" className="form-label">Grades</label>
                        <input type="text" className="form-control" id="grades" value={grades} onChange={(e) => setGrades(e.target.value)} required />
                    </div>
                    <div className="mb-3">
                        <label htmlFor="bio" className="form-label">Bio</label>
                        <textarea className="form-control" id="bio" rows="3" value={bio} onChange={(e) => setBio(e.target.value)} required></textarea>
                    </div>
                    <button type="submit" className="btn btn-primary">Create Exam</button>
                </form>
            </div>
        </div>
    );
}

export default ManageExams;
