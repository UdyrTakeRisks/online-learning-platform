import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import AdminDashboard from './components/AdminDashboard/AdminDashboard';
import InstructorDashboard from './components/InstructorAndStudentDashboard/instructor-dashboard';
import StudentDashboard from './components/InstructorAndStudentDashboard/student-dashboard';
import TestCenterDashboard from './TestCenterDashboard/TestCenterDashboard';
import Login from './components/sharedcomponents/credentials/login';
import Register from './components/sharedcomponents/credentials/register';

function App() {
    return (
        <Router>
            {/* Main Menu */}
            <nav>
                <ul>
                    <li><Link to="/login">Login</Link></li>
                    <li><Link to="/register">Register</Link></li>
                    <li><Link to="/admin">Admin Dashboard</Link></li>
                    <li><Link to="/instructor-dashboard">Instructor Dashboard</Link></li>
                    <li><Link to="/student-dashboard">Student Dashboard</Link></li>
                    <li><Link to="/testcenter">Test Center Dashboard</Link></li>
                </ul>
            </nav>

            {/* Routes */}
            <Routes>
                <Route path="/login" element={<Login />} />
                <Route path="/register" element={<Register />} />
                <Route path="/admin" element={<AdminDashboard />} />
                <Route path="/instructor-dashboard" element={<InstructorDashboard />} />
                <Route path="/student-dashboard" element={<StudentDashboard />} />
                <Route path="/testcenter" element={<TestCenterDashboard />} />
            </Routes>
        </Router>
    );
}

export default App;
