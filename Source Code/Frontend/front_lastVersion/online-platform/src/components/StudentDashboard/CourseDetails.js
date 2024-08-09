import React, { useState, useEffect } from 'react';
import axios from 'axios';

function CourseDetails({ courseId }) {
    const [course, setCourse] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchCourseDetails = async () => {
            try {
                const response = await axios.get(`/api/courses/${courseId}`);
                setCourse(response.data);
            } catch (err) {
                setError(err.message);
            } finally {
                setLoading(false);
            }
        };
        fetchCourseDetails();
    }, [courseId]);

    if (loading) {
        return <div className="card"><div className="card-body">Loading...</div></div>;
    }

    if (error) {
        return <div className="card"><div className="card-body">Error: {error}</div></div>;
    }

    if (!course) {
        return <div className="card"><div className="card-body">No course details available</div></div>;
    }

    return (
        <div className="card">
            <div className="card-body">
                <h3 className="card-title">Course Details</h3>
                <p><strong>Name:</strong> {course.name}</p>
                <p><strong>Duration:</strong> {course.duration}</p>
                <p><strong>Category:</strong> {course.category}</p>
                <p><strong>Rating:</strong> {course.rating}</p>
                <p><strong>Capacity:</strong> {course.capacity}</p>
                <p><strong>Enrolled Students:</strong> {course.enrolledStudents}</p>
                <p><strong>Reviews:</strong></p>
                <ul>
                    {course.reviews.map((review, index) => (
                        <li key={index}>{review}</li>
                    ))}
                </ul>
            </div>
        </div>
    );
}

export default CourseDetails;
