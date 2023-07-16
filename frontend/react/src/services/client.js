import axios from "axios";

const  getAuthConfig = () =>  ({

        headers : {
            Authorization: `Bearer ${localStorage.getItem("access_token")}`
        }
    }
)

export  const  getCustomers = async () => {
    try {
        return await axios.get(`${import.meta.env.VITE_API_BASE_URL}/api/v1/customers`,
            getAuthConfig()) ;
    }catch (e) {
        throw e;
    }
}

export  const  saveCustomer = async (customer) => {
    try {
        return await axios.post(`${import.meta.env.VITE_API_BASE_URL}/api/v1/customers`,
            customer
            ) ;
    }catch (e) {
        throw e;
    }
}


export  const  deleteCustomer = async (id) => {
    try {
        console.log("deleteCustomer id : "+id);
        return await axios.delete(
            `${import.meta.env.VITE_API_BASE_URL}/api/v1/customers/${id}`,
            getAuthConfig()
        )
    }catch (e) {
        throw e;
    }
}

export  const  updateCustomer = async (id,update) => {
    console.log("updateCustomer id : "+id);
    try {
        return await axios.put(`${import.meta.env.VITE_API_BASE_URL}/api/v1/customers/${id}`,
            update,
            getAuthConfig()
        ) ;
    }catch (e) {
        throw e;
    }
}

export  const  login = async (usernameAndpawwsord) => {
    try {
        console.log("login");
        return await axios.post(
            `${import.meta.env.VITE_API_BASE_URL}/api/v1/auth/login`,
            usernameAndpawwsord
        )
    }catch (e) {
        throw e;
    }
}