import React, { useState, useEffect } from "react";

const EmployeeApp = () => {
    const [activeTab, setActiveTab] = useState(0);
    const [departments, setDepartments] = useState([]);
    const [empNo, setEmpNo] = useState("");
    const [employee, setEmployee] = useState(null);
    const [empMessage, setEmpMessage] = useState(""); // Tab 1: Employee Lookup

    const [deptNo, setDeptNo] = useState("");
    const [page, setPage] = useState("");
    const [deptEmployees, setDeptEmployees] = useState([]);
    const [deptMessage, setDeptMessage] = useState(""); // Tab 2: Dept Employees

    const [promotionData, setPromotionData] = useState({
        empNo: "",
        newDeptNo: "",
        newSalary: "",
        newTitle: "",
        promotionDate: ""
    });
    const [promoteMessage, setPromoteMessage] = useState(""); // Tab 3: Promote Employee

    const BASE_URL = "http://localhost:8080/employees";

    const errorMessageStyle = {
        marginTop: "10px",
        color: "#dc3545",
        fontWeight: "bold",
        fontSize: "0.95rem"
    };

    const sectionStyle = {
        width: "100%",
        padding: "20px",
        marginBottom: "20px",
        borderRadius: "8px",
        boxShadow: "0 2px 6px rgba(0,0,0,0.1)",
        backgroundColor: "#f4f4f4"
    };

    const inputStyle = {
        padding: "8px",
        margin: "5px",
        borderRadius: "4px",
        border: "1px solid #ccc",
        width: "150px"
    };

    const buttonStyle = {
        padding: "8px 15px",
        margin: "5px",
        borderRadius: "4px",
        border: "none",
        backgroundColor: "#4CAF50",
        color: "white",
        cursor: "pointer"
    };

    const tableStyle = { width: "100%", borderCollapse: "collapse", marginTop: "10px" };
    const thTdStyle = { border: "1px solid #ddd", padding: "8px", textAlign: "left" };

    const tableRowStyle = (index) => ({
        backgroundColor: index % 2 === 0 ? "#fff" : "#f9f9f9",
        transition: "background-color 0.2s",
        cursor: "default"
    });

    const handleNumericInput = (value, setter) => {
        if (/^\d*$/.test(value)) setter(value);
    };

    // Fetch Departments
    const fetchDepartments = async () => {
        const res = await fetch(`${BASE_URL}/getAllDepartments`);
        const data = await res.json();
        const sorted = data.sort((a, b) => a.deptNo.localeCompare(b.deptNo));
        setDepartments(sorted);
    };

    // Fetch departments on component mount
    useEffect(() => {
        if (departments.length === 0) {
            fetchDepartments();
        }
    }, []);

    // Employee Lookup
    const fetchEmployee = async () => {
        setEmpMessage(""); // clear previous messages
        if (!empNo) {
            setEmployee(null);
            setEmpMessage("Please enter an employee number");
            return;
        }
        try {
            const res = await fetch(`${BASE_URL}/getEmployeeRecord?empNo=${empNo}`);
            if (!res.ok) {
                const errorText = await res.text();
                setEmployee(null);
                setEmpMessage(errorText || "Employee does not exist");
                return;
            }
            const data = await res.json();
            if (typeof data === "string") {
                setEmployee(null);
                setEmpMessage(data);
            } else {
                setEmployee(data);
                setEmpMessage("");
            }
        } catch (err) {
            console.error(err);
            setEmployee(null);
            setEmpMessage("Error fetching employee: " + err.message);
        }
    };

    // Employees by Department
    const fetchDeptEmployees = async () => {
        setDeptMessage(""); // clear previous messages
        if (!deptNo) {
            setDeptEmployees([]);
            setDeptMessage("Please provide Department No.");
            return;
        }

        const backendPage = page && page > 0 ? page : 1;

        try {
            const res = await fetch(`${BASE_URL}/getAllEmployeeRecords?departmentNo=${deptNo}&page=${backendPage}`);
            if (!res.ok) {
                const errorText = await res.text();
                setDeptEmployees([]);
                setDeptMessage(errorText || "Error fetching employees");
                return;
            }

            const data = await res.json();

            if (typeof data === "string") {
                setDeptEmployees([]);
                setDeptMessage(data);
            } else if (data.length === 0) {
                setDeptEmployees([]);
                setDeptMessage("Page not available. End of records.");
            } else {
                setDeptEmployees(data);
                setDeptMessage("");
            }
        } catch (err) {
            console.error(err);
            setDeptEmployees([]);
            setDeptMessage("Error fetching employees: " + err.message);
        }
    };

    // Promote Employee
    const promoteEmployee = async () => {
        setPromoteMessage(""); // clear previous messages

        // Client-side validation - check if required fields are filled
        const empNoStr = promotionData.empNo ? promotionData.empNo.toString().trim() : "";
        const newSalaryStr = promotionData.newSalary ? promotionData.newSalary.toString().trim() : "";
        const newTitleStr = promotionData.newTitle ? promotionData.newTitle.trim() : "";
        const newDeptNoStr = promotionData.newDeptNo ? promotionData.newDeptNo.trim() : "";

        if (!empNoStr || !newSalaryStr || !newTitleStr || !newDeptNoStr) {
            setPromoteMessage("Please provide all 4 required fields: Employee No, New Salary, New Title, and New Department No");
            return;
        }

        const empNo = parseInt(empNoStr, 10);
        const newSalary = parseInt(newSalaryStr, 10);

        if (isNaN(empNo) || empNo <= 0) {
            setPromoteMessage("Employee No must be a valid positive number");
            return;
        }

        if (isNaN(newSalary) || newSalary <= 0) {
            setPromoteMessage("New Salary must be a valid positive number");
            return;
        }

        const cleanedData = {
            empNo: empNo,
            newSalary: newSalary,
            newTitle: newTitleStr,
            newDeptNo: newDeptNoStr,
            promotionDate: promotionData.promotionDate && promotionData.promotionDate.trim() !== ""
                ? promotionData.promotionDate
                : null
        };

        try {
            const res = await fetch(`${BASE_URL}/promote`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(cleanedData)
            });

            const text = await res.text();

            if (!res.ok) {
                setPromoteMessage(text);   // show backend error message
                return;
            }

            setPromoteMessage(text);       // success message
        } catch (err) {
            console.error(err);
            setPromoteMessage("Error promoting employee: " + err.message);
        }
    };


    const tabNames = ["Departments", "Employee Lookup", "Dept Employees", "Promote Employee"];

    return (
        <div style={{ padding: "20px", fontFamily: "Arial, sans-serif" }}>
            <h1 style={{ textAlign: "center" }}>Employee Management Portal</h1>

            {/* Tabs */}
            <div style={{ display: "flex", marginBottom: "20px" }}>
                {tabNames.map((tab, index) => (
                    <div
                        key={index}
                        onClick={() => setActiveTab(index)}
                        style={{
                            padding: "10px 20px",
                            cursor: "pointer",
                            borderBottom: activeTab === index ? "3px solid #4CAF50" : "3px solid transparent",
                            fontWeight: activeTab === index ? "bold" : "normal"
                        }}
                    >
                        {tab}
                    </div>
                ))}
            </div>

            {/* Departments */}
            {activeTab === 0 && (
                <div style={sectionStyle}>
                    <h2>Departments</h2>
                    <button style={buttonStyle} onClick={fetchDepartments}>Load Departments</button>
                    {departments.length > 0 && (
                        <table style={tableStyle}>
                            <thead>
                            <tr>
                                <th style={{ ...thTdStyle, fontWeight: "bold" }}>Dept No</th>
                                <th style={{ ...thTdStyle, fontWeight: "bold" }}>Dept Name</th>
                            </tr>
                            </thead>
                            <tbody>
                            {departments.map((d, idx) => (
                                <tr key={d.deptNo} style={tableRowStyle(idx)}>
                                    <td style={thTdStyle}>{d.deptNo}</td>
                                    <td style={thTdStyle}>{d.deptName}</td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    )}
                </div>
            )}

            {/* Employee Lookup */}
            {activeTab === 1 && (
                <div style={sectionStyle}>
                    <h2>Employee Lookup</h2>
                    <input
                        type="text"
                        placeholder="Employee No"
                        value={empNo}
                        onChange={(e) => handleNumericInput(e.target.value, setEmpNo)}
                        style={inputStyle}
                    />
                    <button style={buttonStyle} onClick={fetchEmployee}>Get Employee</button>
                    {empMessage && <p style={errorMessageStyle}>{empMessage}</p>}

                    {employee && (
                        <>
                            <h3>Employee Information</h3>
                            <table style={tableStyle}>
                                <tbody>
                                <tr style={tableRowStyle(0)}>
                                    <td style={{ ...thTdStyle, fontWeight: "bold" }}>Employee ID</td>
                                    <td style={thTdStyle}>{employee.empNo}</td>
                                </tr>
                                <tr style={tableRowStyle(1)}>
                                    <td style={{ ...thTdStyle, fontWeight: "bold" }}>Name</td>
                                    <td style={thTdStyle}>{employee.firstName} {employee.lastName}</td>
                                </tr>
                                <tr style={tableRowStyle(2)}>
                                    <td style={{ ...thTdStyle, fontWeight: "bold" }}>Birth Date</td>
                                    <td style={thTdStyle}>{employee.birthDate}</td>
                                </tr>
                                <tr style={tableRowStyle(3)}>
                                    <td style={{ ...thTdStyle, fontWeight: "bold" }}>Gender</td>
                                    <td style={thTdStyle}>{employee.gender}</td>
                                </tr>
                                <tr style={tableRowStyle(4)}>
                                    <td style={{ ...thTdStyle, fontWeight: "bold" }}>Hire Date</td>
                                    <td style={thTdStyle}>{employee.hireDate}</td>
                                </tr>
                                <tr style={tableRowStyle(5)}>
                                    <td style={{ ...thTdStyle, fontWeight: "bold" }}>Title</td>
                                    <td style={thTdStyle}>{employee.titleList[employee.titleList.length-1].title}</td>
                                </tr>
                                <tr style={tableRowStyle(6)}>
                                    <td style={{ ...thTdStyle, fontWeight: "bold" }}>Department</td>
                                    <td style={thTdStyle}>{employee.deptEmpList[employee.deptEmpList.length-1].deptNo}</td>
                                </tr>
                                </tbody>
                            </table>

                            <h3>Salary History</h3>
                            <table style={tableStyle}>
                                <thead>
                                <tr>
                                    <th style={{ ...thTdStyle, fontWeight: "bold" }}>From Date</th>
                                    <th style={{ ...thTdStyle, fontWeight: "bold" }}>To Date</th>
                                    <th style={{ ...thTdStyle, fontWeight: "bold" }}>Salary</th>
                                </tr>
                                </thead>
                                <tbody>
                                {employee.salaryList.map((s, idx) => (
                                    <tr key={idx} style={tableRowStyle(idx)}>
                                        <td style={thTdStyle}>{s.fromDate}</td>
                                        <td style={thTdStyle}>{s.toDate}</td>
                                        <td style={thTdStyle}>{s.salary}</td>
                                    </tr>
                                ))}
                                </tbody>
                            </table>
                        </>
                    )}
                </div>
            )}

            {/* Dept Employees */}
            {activeTab === 2 && (
                <div style={sectionStyle}>
                    <h2>Employees by Department</h2>
                    <select
                        style={inputStyle}
                        value={deptNo}
                        onChange={(e) => setDeptNo(e.target.value)}
                    >
                        <option value="">Select Department</option>
                        {departments.map(dept => (
                            <option key={dept.deptNo} value={dept.deptNo}>
                                {dept.deptNo} - {dept.deptName}
                            </option>
                        ))}
                    </select>
                    <input
                        style={{ ...inputStyle, width: "150px" }}
                        type="number"
                        placeholder="Page (default 1)"
                        min={1}
                        value={page || ""}
                        onChange={(e) => {
                            const val = e.target.value;
                            setPage(val === "" ? "" : Number(val));
                        }}
                    />
                    <button style={buttonStyle} onClick={fetchDeptEmployees}>Get Employees</button>
                    {deptMessage && <p style={errorMessageStyle}>{deptMessage}</p>}

                    {deptEmployees.length > 0 && (
                        <table style={tableStyle}>
                            <thead>
                            <tr>
                                <th style={{ ...thTdStyle, fontWeight: "bold" }}>Employee ID</th>
                                <th style={{ ...thTdStyle, fontWeight: "bold" }}>First Name</th>
                                <th style={{ ...thTdStyle, fontWeight: "bold" }}>Last Name</th>
                                <th style={{ ...thTdStyle, fontWeight: "bold" }}>Hire Date</th>
                            </tr>
                            </thead>
                            <tbody>
                            {deptEmployees.map((emp) => (
                                <tr key={emp.empNo}>
                                    <td style={thTdStyle}>{emp.empNo}</td>
                                    <td style={thTdStyle}>{emp.firstName}</td>
                                    <td style={thTdStyle}>{emp.lastName}</td>
                                    <td style={thTdStyle}>{emp.hireDate}</td>
                                </tr>
                            ))}
                            </tbody>
                        </table>
                    )}
                </div>
            )}

            {/* Promote Employee */}
            {activeTab === 3 && (
                <div style={sectionStyle}>
                    <h2>Promote Employee</h2>
                    <input
                        type="text"
                        placeholder="Employee No"
                        value={promotionData.empNo}
                        onChange={e => handleNumericInput(e.target.value, val => setPromotionData({...promotionData, empNo: val}))}
                        style={inputStyle}
                    />
                    <select
                        value={promotionData.newDeptNo}
                        onChange={e => setPromotionData({ ...promotionData, newDeptNo: e.target.value })}
                        style={inputStyle}
                    >
                        <option value="">Select Department</option>
                        {departments.map(dept => (
                            <option key={dept.deptNo} value={dept.deptNo}>
                                {dept.deptNo} - {dept.deptName}
                            </option>
                        ))}
                    </select>
                    <input
                        type="text"
                        placeholder="New Salary"
                        value={promotionData.newSalary}
                        onChange={e => handleNumericInput(e.target.value, val => setPromotionData({...promotionData, newSalary: val}))}
                        style={inputStyle}
                    />
                    <input
                        type="text"
                        placeholder="New Title"
                        value={promotionData.newTitle}
                        onChange={e => setPromotionData({ ...promotionData, newTitle: e.target.value })}
                        style={inputStyle}
                    />
                    <input
                        type="date"
                        placeholder="Promotion Date"
                        value={promotionData.promotionDate}
                        onChange={e => setPromotionData({ ...promotionData, promotionDate: e.target.value })}
                        style={inputStyle}
                    />
                    <button style={buttonStyle} onClick={promoteEmployee}>Promote</button>
                    {promoteMessage && <p style={errorMessageStyle}>{promoteMessage}</p>}
                </div>
            )}
        </div>
    );
};

export default EmployeeApp;
