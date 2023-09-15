import React, { useState, useEffect } from 'react';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import { createUrl, log } from '../utils/utils';
import { toast } from 'react-toastify';
import { useNavigate } from 'react-router-dom';
const Admin = () => {
    const navigate = useNavigate();
    const [carBookings, setCarBookings] = useState([]);
    const [users, setUsers] = useState([]);
    const [cars, setCars] = useState([]);
    const [imageFile, setImageFile] = useState(null);

    // const token = localStorage.getItem('token');


    useEffect(() => {
        fetchCarBookings();
        fetchUsers();
        fetchCars();

    }, []);

    // Fetch functions (similar to previous code)

    const fetchCarBookings = () => {
        const url = createUrl('/bookings')
        axios.get(url)
            .then(function (response) {
                setCarBookings(response.data);

                log(response);

            })
            .catch(function (error) {

                log(error);
            })
            .finally(function () {

            });
    };



    const fetchUsers = () => {
        const url = createUrl('/users')
        axios.get(url)
            .then(function (response) {
                setUsers(response.data);

                log(response);

            })
            .catch(function (error) {

                log(error);
            })
            .finally(function () {

            });
    };


    const fetchCars = () => {
        const url = createUrl('/cars')
        axios.get(url)
            .then(function (response) {
                setCars(response.data);

                log(response);

            })
            .catch(function (error) {

                log(error);
            })
            .finally(function () {

            });
    };

    // Delete The Salesperson
    const deleteSalesperson = (id) => {
        const url = createUrl(`/users/${id}`)
        axios.delete(url)
            .then(res => {
                log(res);
                toast.success('Salesperson Deleted SucessFully!! having id = ' + id)
                log(res.data);
                fetchUsers();
            })
            .catch(error => {
                log(error);
            })
    }

    // Delete The Car
    const deleteCar = (id) => {
        const url = createUrl(`/cars/${id}`)
        axios.delete(url)
            .then(res => {
                log(res);
                toast.success('Car Deleted SucessFully!! having id = ' + id)
                log(res.data);
                fetchCars();
            })
            .catch(error => {
                log(error);
            });
    }

    //add the car
    const addCar = () => {
        navigate('/addcar')
    }

    //Upload a image 
    const uploadImage = (carId) => {
        if (!imageFile) {
            toast.error('Please select an image to upload.');
            return;
        }
    
        const formData = new FormData();
        formData.append('imageFile', imageFile);
    
        const url = createUrl(`/cars/images/${carId}`);
        axios.post(url, formData, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        })
        .then(function (response) {
            toast.success('Image uploaded successfully!');
            setImageFile(null); // Reset the image file state
        })
        .catch(function (error) {
            log(error);
            toast.error('Error uploading image.');
        });
    };

    const back = () => {
        //localStorage.removeItem('token');
        // Redirect or handle logout logic as needed
        navigate('/')

    };

    return (
        <div className="container mt-5">
            <h1 className="text-center mb-4">Admin Dashboard</h1>

            {/* Car Bookings */}
            <div className="card mb-3">
                <div className="card-body">
                    <h5 className="card-title">Car Bookings</h5>
                    <table className="table">
                        <thead>
                            <tr>
                                <th>Name</th>
                                {/* <th>Last Name</th> */}
                                <th>Mobile</th>
                                <th>Car Brand</th>
                                <th>Car Model</th>
                                <th>Price</th>
                            </tr>
                        </thead>
                        <tbody>
                            {carBookings.map((booking) => (
                                <tr key={booking.id}>
                                    <td>{booking.user.firstName + "  "}
                                        {booking.user.lastName}</td>
                                    <td>{booking.user.phone}</td>
                                    <td>{booking.car.brandName}</td>
                                    <td>{booking.car.modelName}</td>
                                    <td>₹{booking.car.price}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                    <div className="total">
                        <b>Total Price:</b> ₹{carBookings.reduce((total, booking) => total + booking.car.price, 0)}
                    </div>
                </div>
            </div>

            {/* Users */}
            <div className="card mb-3">
                <div className="card-body">
                    <h5 className="card-title">Users</h5>
                    <table className="table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Phone</th>
                                <th>Address</th>
                            </tr>
                        </thead>
                        <tbody>
                            {users.map((user) => (
                                user.userRoles === 'USER' && (
                                    <tr key={user.id}>
                                        <td>{user.id}</td>
                                        <td>{user.firstName}</td>
                                        <td>{user.email}</td>
                                        <td>{user.phone}</td>
                                        <td>{user.address}</td>
                                    </tr>
                                )
                            ))}

                        </tbody>
                    </table>

                </div>
            </div>

            {/* Salesperson */}
            <div className="card mb-3">
                <div className="card-body">
                    <h5 className="card-title">Salesperson</h5>
                    <table className="table">
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Phone</th>
                                <th>Address</th>
                            </tr>
                        </thead>
                        <tbody>
                            {users.map((user) => (
                                user.userRoles === 'SALESPERSON' && (
                                    <tr key={user.id}>
                                        <td>{user.id}</td>
                                        <td>{user.firstName}</td>
                                        <td>{user.email}</td>
                                        <td>{user.phone}</td>
                                        <td>{user.address}</td>
                                        <td>
                                            <button className="btn btn-outline-danger btn-sm" onClick={() => { deleteSalesperson(user.id) }} >Delete</button>
                                        </td>
                                    </tr>
                                )
                            ))}

                        </tbody>
                    </table>

                </div>
            </div>

            {/* Cars */}
            <div className="card mb-3">
                <div className="card-body">
                    <h5 className="card-title">Cars with Details</h5>
                    <table className="table">
                        <thead>
                            <tr>
                                <th>Id</th>
                                <th>Brand-Name</th>
                                <th>Model-Name</th>
                                <th>year</th>
                                <th>Fuel-type</th>
                                <th>Price</th>
                                <th>Color</th>
                            </tr>
                        </thead>
                        <tbody>
                            {cars.map((car) => (
                                <tr key={car.id}>
                                    <td>{car.id}</td>
                                    <td>{car.brandName}</td>
                                    <td>{car.modelName}</td>
                                    <td>{car.year}</td>
                                    <td>{car.fuelType}</td>
                                    <td>{car.price}</td>
                                    <td>{car.carSpecification.color}</td>
                                    <td>
                                        <div className="input-group">
                                                <input
                                                    type="file"
                                                    className="custom-file-input"
                                                    id={`image-${car.id}`}
                                                    accept="image/*"
                                                    onChange={(e) => setImageFile(e.target.files[0])}
                                                />
                                                <button
                                                    className="btn btn-outline-success btn-sm"
                                                    onClick={() => uploadImage(car.id)}
                                                >
                                                    Upload Image
                                                </button>
                                        </div>
                                    </td>
                                    <td>
                                        <button className="btn btn-outline-danger btn-sm" onClick={() => { deleteCar(car.id) }} >Delete</button>
                                    </td>
                                </tr>
                            ))}

                        </tbody>
                    </table>
                    <div className="total">
                        <button className='btn btn-outline-secondary ntn-sm' onClick={addCar}>Add-Car</button>
                    </div>
                </div>
            </div>


            <button className="btn btn-outline-danger btn-sm" onClick={back}>
                Back
            </button>
        </div>
    );
};

export default Admin;