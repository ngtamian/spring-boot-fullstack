import {Wrap,
       WrapItem,
       Button,
       Spinner, ButtonGroup, Text} from '@chakra-ui/react';


import  SidebarWithHeader from "./components/shared/SideBar.jsx"
import React, {useEffect, useState} from "react";

import { getCustomers} from './services/client.js';

import  CardWithImage from "./components/Card.jsx";

import  CreateCustomerDrawer from "./components/CreateCustomerDrawer.jsx";
import {errorNotification} from "./services/notification.js";


const  App = ()=> {

    const [customers, setCustomers] = useState([]);
    const [loading, setLoading] = useState(false);
    const [err, setError] = useState("");

    const fetchCustomers = () => {
        setLoading(true);
        getCustomers().then(res => {
            setCustomers(res.data)
        }).catch(err => {
            setError(err.response.data.message)
            errorNotification(
                err.code,
                err.response.data.message
            )
        }).finally(() => {
            setLoading(false)
        })
    }

    useEffect(()=>{
        fetchCustomers();
    },[])
    if(loading){
        //return <Button colorScheme='teal' variant='outline'>Click me</Button>
        return (<SidebarWithHeader>
                <Spinner
                    thickness='4px'
                    speed='0.65s'
                    emptyColor='gray.200'
                    color='blue.500'
                    size='xl'
                />
            </SidebarWithHeader>
        )
    }
    if(customers.length <=0){
        return (<SidebarWithHeader>
               <Text mt={5}>No customers available</Text>
            </SidebarWithHeader>
        )

    }

    return (<SidebarWithHeader>
            <CreateCustomerDrawer
                fetchCustomers={fetchCustomers}
            />
            <Wrap justify={"center"} spacing={"30px"}>
                {customers.map((customer,index)=>(
                    <WrapItem key={index}>
                        <CardWithImage
                            {...customer}
                            imageNumber={index}
                            fetchCustomers={fetchCustomers}

                        />
                    </WrapItem>
                ))}
            </Wrap>

        </SidebarWithHeader>
    )

}

export default  App;