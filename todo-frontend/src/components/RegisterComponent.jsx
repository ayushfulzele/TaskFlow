import React, { useState } from 'react'
import { registerAPICall } from '../services/AuthService'

const RegisterComponent = () => {

    const [name,setName] =useState('')
    const [username,setUsername] =useState('')
    const [email,setEmail] =useState('')
    const [password, setPasword] =useState('')

    function handleRegistrationForm(e){
        e.preventDefault()

        const register = {name, username, email, password}
        console.log(register)

        registerAPICall(register).then((response) =>{
            console.log(response.data)
        }).catch(error => {
            console.error(error)
        })

    }

  return (
    <div className='container'><br/><br/>
        <div className='row'>
            <div className= 'col-md-6 offset-md-3'>
                <div className='card'>
                    <div className='card-header'>
                        <h2 className='text-center'>User Registration Form</h2>
                    </div>

                    <div className='card-body'>
                        <form>
                            <div className='row mb-3'>
                                <label className='col-md-3 control-label'>Name</label>
                                <div className='col-md-9'>
                                    <input
                                        type='text'
                                        name='name'
                                        placeholder='Enter name'
                                        className='form-control'            
                                        value={name}
                                        onChange={(e) => setName(e.target.value)}
                                    >
                                    </input>
                                </div>
                            </div>
                            <div className='row mb-3'>
                                <label className='col-md-3 control-label'>Username</label>
                                <div className='col-md-9'>
                                    <input
                                        type='text'
                                        name='username'
                                        placeholder='Enter username'
                                        className='form-control'
                                        value={username}
                                        onChange={(e) => setUsername(e.target.value)}
                                    >
                                    </input>
                                </div>
                            </div>
                            <div className='row mb-3'>
                                <label className='col-md-3 control-label'>Email</label>
                                <div className='col-md-9'>
                                    <input
                                        type='text'
                                        name='email'
                                        placeholder='Enter email'
                                        className='form-control'
                                        value={email}
                                        onChange={(e) => setEmail(e.target.value)}
                                    >
                                    </input>
                                </div>
                            </div>
                            <div className='row mb-3'>
                                <label className='col-md-3 control-label'>Password</label>
                                <div className='col-md-9'>
                                    <input
                                        type='password'
                                        name='password'
                                        placeholder='Enter password'
                                        className='form-control'
                                        value={password}
                                        onChange={(e) => setPasword(e.target.value)}
                                    >
                                    </input>
                                </div>
                            </div>
                            <div className='form-group mb-3'>
                                <button className='btn btn-primary' onClick={(e) => handleRegistrationForm(e)}>Submit</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
  )
}

export default RegisterComponent